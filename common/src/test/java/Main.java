import org.junit.Test;

/**
 * Created by liuzz on 2018-12-19 11:35.
 */
public class Main {
    @Test
    public void start(){
        String uname = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
        for (; ; ) {
            System.out.println("Main gc ..."+uname);
            System.gc();
            try{
                Thread.sleep(1000);
            }catch(Exception e){
                //skip
            }

        }
    }
}
