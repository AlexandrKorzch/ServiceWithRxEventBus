package microsoft.aspnet.signalr.servicewithrxbus.rxbus;

/**
 * Created by akorzh on 14.03.2017.
 */

public class MainBus extends RxEventBus {

    private static MainBus instance;

    public static MainBus getInstance() {
        if (instance == null)
            instance = new MainBus();
        return instance;
    }

    private MainBus() {}
}