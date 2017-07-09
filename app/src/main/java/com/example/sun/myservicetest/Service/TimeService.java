package com.example.sun.myservicetest.Service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.IBinder;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class TimeService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_RECV_MSG = "com.example.sun.myservicetest.intent.action.RECEIVE_MESSAGE";
    private static final String ACTION_OTHER_MSG = "com.example.sun.myservicetest.intent.action.OTHER_MESSAGE";

    // TODO: Rename parameters
    private static final String MESSAGE_IN = "message_input";
    private static final String MESSAGE_OUT = "message_output";

    private static final String Tag = "TimeService";
    public TimeService() {
        super("TimeService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(Tag, "onBind()");
        return super.onBind(intent);
    }

    @Override
    public void onCreate() {
        Log.d(Tag, "onCreate()");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d(Tag, "onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.d(Tag, "onStart()");
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(Tag, "onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void setIntentRedelivery(boolean enabled) {
        Log.d(Tag, "setIntentRedelivery()");
        super.setIntentRedelivery(enabled);
    }
    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, TimeService.class);
        intent.setAction(ACTION_RECV_MSG);
        intent.putExtra(MESSAGE_IN, param1);
        intent.putExtra(MESSAGE_OUT, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, TimeService.class);
        intent.setAction(ACTION_OTHER_MSG);
        intent.putExtra(MESSAGE_IN, param1);
        intent.putExtra(MESSAGE_OUT, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(Tag,"onHandleIntent！");
        if (intent != null) {
            String action = "";
            action = intent.getAction();
            System.out.println(intent.getAction());
            Log.d(Tag,ACTION_RECV_MSG);
            if (ACTION_RECV_MSG.equals(action)) {
                Log.d(Tag,"Time Service Starting！");
                final String msgRecv = intent.getStringExtra(MESSAGE_IN);
                Log.d(Tag,"MESSAGE_IN:"+msgRecv);
                String resultTxt = null;
                for (int i = 60; i >= 0; i--) {
                    resultTxt = String.valueOf(i);
                    Intent broadcastIntent = new Intent();
                    broadcastIntent.setAction(ACTION_RECV_MSG);
                    broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    if(i==0)
                    {
                        resultTxt = "准备";
                        broadcastIntent.putExtra("isStartTime", true);
                    }
                    else
                        broadcastIntent.putExtra("isStartTime", false);
                    broadcastIntent.putExtra(MESSAGE_OUT, resultTxt);
                    sendBroadcast(broadcastIntent);
                    SystemClock.sleep(1000);
                }
            }
        }
    }
}
