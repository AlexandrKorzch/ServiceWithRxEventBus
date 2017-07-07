package microsoft.aspnet.signalr.servicewithrxbus.rxbus.event;

/**
 * Created by akorzh on 07.07.2017.
 */

public class FromServiceEvent {

    private String message;

    public FromServiceEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
