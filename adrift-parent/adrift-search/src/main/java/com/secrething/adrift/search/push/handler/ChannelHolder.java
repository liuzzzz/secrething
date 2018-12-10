package com.secrething.adrift.search.push.handler;

import com.secrething.adrift.search.core.Routing;
import com.secrething.adrift.search.push.MessageBuilder;
import com.secrething.adrift.search.push.protocol.Constants;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * Created by liuzz on 2018-12-08 15:29.
 */
public class ChannelHolder {
    private static final Logger logger = LoggerFactory.getLogger(ChannelHolder.class);
    private static final AttributeKey<String> SEARCH_KEY = AttributeKey.valueOf("search_key");
    private static final AttributeKey<String> REMOTE_ADDRESS = AttributeKey.valueOf("remote_addr");
    private static ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock(true);
    private static ConcurrentMap<String, ChannelGroup> channelGroup = new ConcurrentHashMap<>();
    private static ConcurrentMap<Channel, ChannelWrapper> channels = new ConcurrentHashMap<>();

    public static boolean cacheChannel(Channel channel, String searchKey, String remoteAddr) {
        channel.attr(SEARCH_KEY).set(searchKey);
        channel.attr(REMOTE_ADDRESS).set(remoteAddr);
        rwLock.writeLock().lock();
        try {
            ChannelGroup group = channelGroup.get(searchKey);
            if (null == group) {
                group = new ChannelGroup();
                group.setSearchKey(searchKey);
                channelGroup.put(searchKey, group);

            }
            ChannelWrapper wrapper = channels.get(channel);
            if (null == wrapper) {
                wrapper = new ChannelWrapper();
                wrapper.setChannel(channel);
                wrapper.setRemoteAddr(remoteAddr);
                channels.put(channel, wrapper);
            }
            group.getChannelWrappers().putIfAbsent(wrapper, Constants.HOLDER);
            return true;
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    /**
     * 从缓存中移除Channel，并且关闭Channel
     *
     * @param channel
     */
    public static void removeChannel(Channel channel) {
        Attribute<String> ab = channel.attr(SEARCH_KEY);
        String searchKey = ab.get();
        channel.close();
        Optional<ChannelWrapper> wrapper = Optional.ofNullable(channels.remove(channel));
        wrapper.ifPresent((w) -> {
            Optional<ChannelGroup> group = Optional.ofNullable(channelGroup.get(searchKey));
            group.ifPresent((g) -> {
                g.getChannelWrappers().remove(w);
            });
        });

    }

    /**
     * 广播搜索结果
     *
     * @param message
     */
    public static void broadcastMess(String searchKey, List<Object> message) {
        Optional<ChannelGroup> group = Optional.ofNullable(channelGroup.get(searchKey));
        group.ifPresent((g) -> {
            g.getChannelWrappers().keySet().forEach(w -> {
                w.getChannel().writeAndFlush(new TextWebSocketFrame(MessageBuilder.buildRoutingsMsg(searchKey, message)));
            });
        });
    }

    public static void updateLastLiveTime(Channel channel) {
        if (null != channel) {
            Optional<ChannelWrapper> wrapper = Optional.ofNullable(channels.get(channel));
            wrapper.ifPresent((w) -> {
                w.setLastLiveTime(System.currentTimeMillis());
            });
        }

    }

    /**
     * 扫描并关闭失效的Channel
     */
    public static void scanNotActiveChannel() {
        rwLock.writeLock().lock();
        try {
            for (ChannelGroup group : channelGroup.values()) {
                ConcurrentMap<ChannelWrapper, Object> map = group.getChannelWrappers().keySet().stream().filter(w -> {
                    if (w.getChannel().isActive()) {
                        long lastLive = w.getLastLiveTime();
                        long diff = System.currentTimeMillis() - lastLive;
                        if (diff > 10000) {
                            w.getChannel().close();
                            channels.remove(w.getChannel());
                            return false;
                        }
                    }
                    return true;
                }).collect(Collectors.toConcurrentMap(k -> k, k -> Constants.HOLDER));
                group.setChannelWrappers(map);
            }
            channelGroup = channelGroup.entrySet().stream().filter(e -> {
                return e.getValue().getChannelWrappers().size() > 0;
            }).collect(Collectors.toConcurrentMap(e -> e.getKey(), e -> e.getValue()));
        } finally {
            rwLock.writeLock().unlock();
        }

    }

    @AllArgsConstructor
    @NoArgsConstructor
    private static final class ChannelGroup {
        private String searchKey;
        private ConcurrentMap<ChannelWrapper, Object> channelWrappers = new ConcurrentHashMap<>();

        public String getSearchKey() {
            return searchKey;
        }

        public void setSearchKey(String searchKey) {
            this.searchKey = searchKey;
        }

        public ConcurrentMap<ChannelWrapper, Object> getChannelWrappers() {
            return channelWrappers;
        }

        public void setChannelWrappers(ConcurrentMap<ChannelWrapper, Object> channelWrappers) {
            this.channelWrappers = channelWrappers;
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    private static final class ChannelWrapper {
        private String remoteAddr;
        private long lastLiveTime = System.currentTimeMillis();
        private Channel channel;

        public String getRemoteAddr() {
            return remoteAddr;
        }

        public void setRemoteAddr(String remoteAddr) {
            this.remoteAddr = remoteAddr;
        }

        public long getLastLiveTime() {
            return lastLiveTime;
        }

        public void setLastLiveTime(long lastLiveTime) {
            this.lastLiveTime = lastLiveTime;
        }

        public Channel getChannel() {
            return channel;
        }

        public void setChannel(Channel channel) {
            this.channel = channel;
        }
    }
}
