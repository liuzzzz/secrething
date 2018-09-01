#Interfaces

###Client
######connect(String address)
######disconnect()
######getChannel()

###Server
######start()
######bind(int port)
######getChannels()
######getChannel(String address)

###Channel
######send(Object msg)

###ProxyFactory
######getProxy()

###Invocation
######invoke()
