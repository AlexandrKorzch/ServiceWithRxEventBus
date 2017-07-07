package microsoft.aspnet.signalr.servicewithrxbus.rxbus;

import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by akorzh on 14.03.2017.
 */

public class RxEventBus {

    private final Subject<Object, Object> bus = new SerializedSubject<>(PublishSubject.create());

    public void post(Object event) {
        bus.onNext(event);
    }

    public Observable<Object> getBusObservable() {
        return bus
                .onBackpressureDrop()
                /*.subscribeOn(Schedulers.newThread())*/
                ;
    }
}
