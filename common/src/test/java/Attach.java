/*
import com.sun.tools.attach.VirtualMachine;
import org.junit.Test;

*/
/**
 * Created by liuzz on 2018-12-19 11:36.
 *//*

public class Attach{
    private static final Object LOCK = new Object();
    public static class MonitorThread extends Thread{

        public void run(){
            System.out.println("monitor started");
            while(true){
                synchronized(LOCK){
                    try{
                        LOCK.wait();
                        System.out.println("gc start...");
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }
            }


        }
    }
    public void sigal(){
        LOCK.notify();
    }
    public native void transThis();
    public static void main(String[] args){
        String targetProcessId = args[0];
        //String agentPath = "/Users/liuzz58/Desktop/code/agent/agent.so";
        String agentPath = args[1];
        new MonitorThread().start();
        VirtualMachine vm = null;
        try{
            vm = VirtualMachine.attach(targetProcessId);
            vm.loadAgentPath(agentPath, null);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }finally{
            if (null != vm) {
                try{
                    vm.detach();
                }catch(Exception ee){
                    //skip
                }
            }
        }


    }


}
*/
