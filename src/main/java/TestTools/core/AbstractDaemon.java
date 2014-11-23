package TestTools.core;

/**
 * Created by def on 23.11.14.
 */
public abstract class AbstractDaemon implements Runnable {

    private Integer period;
    private Boolean run;

    public AbstractDaemon(Integer period){
        this.period = period;
        run = true;
    }

    public void stop(){
        run = false;
    }

    protected abstract void process();

    public void run() {
        while (run) {
            try {
                process();
                Thread.sleep(period * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
