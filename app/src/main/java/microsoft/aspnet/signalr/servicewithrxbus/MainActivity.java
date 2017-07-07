package microsoft.aspnet.signalr.servicewithrxbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import microsoft.aspnet.signalr.servicewithrxbus.rxbus.MainBus;
import microsoft.aspnet.signalr.servicewithrxbus.rxbus.event.FromServiceEvent;
import microsoft.aspnet.signalr.servicewithrxbus.rxbus.event.ToServiceEvent;
import microsoft.aspnet.signalr.servicewithrxbus.service.MyService;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "RxActivityService";

    private Intent intent;
    private TextView tvIter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        startService();
        subscribeToService();
    }

    private void initViews() {
        tvIter = (TextView) findViewById(R.id.tv_iter);
        findViewById(R.id.btn_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainBus.getInstance().post(new ToServiceEvent(""));
                Log.d(TAG, "onClick: post from activity");
            }
        });
    }

    private void startService() {
        intent = new Intent(this, MyService.class);
        startService(intent);
    }

    private void subscribeToService() {
        MainBus.getInstance()
                .getBusObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        if(o instanceof FromServiceEvent){
                            Log.d(TAG, "call: from service");
                            tvIter.setText(((FromServiceEvent)o).getMessage());
                        }
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);
        Log.d(TAG, "onDestroy: activity");
    }
}
