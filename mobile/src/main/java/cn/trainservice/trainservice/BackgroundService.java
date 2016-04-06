package cn.trainservice.trainservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class BackgroundService extends Service {
    public BackgroundService() {

    }

    @Override
    public void onCreate(){
        super.onCreate();
        createNotify("西安站到了", "下一站：商丘");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public  void createNotify(String title, String nextStation){

        NotificationManager nm = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setContentTitle(title)
                .setContentText(nextStation)
                .setSmallIcon(R.drawable.ic_launcher).setContentIntent(pi)
                .build();
        notification.flags = Notification.FLAG_NO_CLEAR;
        nm.notify(0, notification);

    }
}
