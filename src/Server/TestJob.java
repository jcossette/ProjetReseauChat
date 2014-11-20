package Server;

/**
 * Created by Julien Cossette on 11/20/2014.
 */
public class TestJob extends Job{
    public void execute(){
        testMethod();
    }

    public synchronized void testMethod(){
        int testruns = 5;
        while(testruns > 0){
            try{
                wait(1000);
                System.out.println("Allo");
            }catch(InterruptedException ie){
            }
            testruns--;
        }
        run = false;
    }
}
