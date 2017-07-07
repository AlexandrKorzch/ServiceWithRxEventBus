package microsoft.aspnet.signalr.servicewithrxbus.rxbus.event;

/**
 * Created by akorzh on 07.07.2017.
 */

public class ToServiceEvent {

    private String message;

    public ToServiceEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
