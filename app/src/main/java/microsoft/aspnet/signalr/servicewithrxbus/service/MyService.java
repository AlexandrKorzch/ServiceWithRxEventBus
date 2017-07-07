package microsoft.aspnet.signalr.servicewithrxbus.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import microsoft.aspnet.signalr.servicewithrxbus.rxbus.MainBus;
import microsoft.aspnet.signalr.servicewithrxbus.rxbus.event.FromServiceEvent;
import microsoft.aspnet.signalr.servicewithrxbus.rxbus.event.ToServiceEvent;
import rx.functions.Action1;

/**
 * Created by akorzh on 07.07.2017.
 */

public class MyService extends Service {

    private static final String TAG = "RxActivityService";
    private FromServiceEvent mFromServiceEvent;

    private int iteration;

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: service");
        super.onCreate();
        mFromServiceEvent = new FromServiceEvent("");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        startSendingEvents();
        subscribeToActivity();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return null;
    }

    private void startSendingEvents() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (iteration = 0; iteration < 100; iteration++) {
                    try {
                        Thread.sleep(2000);
                        mFromServiceEvent.setMessage(String.valueOf(iteration));
                        MainBus.getInstance().post(mFromServiceEvent);
                        Log.d(TAG, "run: post from service");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void subscribeToActivity() {
        MainBus.getInstance()
                .getBusObservable()
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        if (o instanceof ToServiceEvent) {
                            Log.d(TAG, "call: from activity");
                            iteration = 0;
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: service");
    }
}
