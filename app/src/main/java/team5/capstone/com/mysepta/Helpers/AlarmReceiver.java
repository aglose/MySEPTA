package team5.capstone.com.mysepta.Helpers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;

import team5.capstone.com.mysepta.MainActivity;
import team5.capstone.com.mysepta.R;

/**
 * Created by tiestodoe on 11/16/15.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String line = intent.getStringExtra("line");

        NotificationManagerCompat manager = NotificationManagerCompat.from(context);


        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("Subway Trip for " + line)
                        .setContentText("Arrives in 2 minutes!")
                        .setAutoCancel(true);

        NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int mNotificationId = 001;

        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}