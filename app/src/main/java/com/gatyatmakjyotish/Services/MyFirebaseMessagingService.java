package com.gatyatmakjyotish.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import android.util.Log;


import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.ui.activity.NotificationActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Map;
import static android.app.Notification.BADGE_ICON_SMALL;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMessagingServ";

    String CHANNEL_ID = "channel5";

    int count=0;
    Bitmap image;
    RemoteMessage rm;

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("NEW_TOKEN", s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            this.rm=remoteMessage;
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        try
        {
            if(!String.valueOf(String.valueOf(remoteMessage.getNotification().getImageUrl())).equalsIgnoreCase(""))
            {
                generateNotification(remoteMessage.getNotification()
                        .getBody(), remoteMessage.getNotification().getTitle(),String.valueOf(remoteMessage.getNotification().getImageUrl()));
            }
            else
            {
                generateNotificationWithoutImage(remoteMessage.getNotification()
                        .getBody(), remoteMessage.getNotification().getTitle());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    /*private void sendNotification(RemoteMessage remoteMessage) {
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.logo))
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }*/

    private void sendNotification(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        String title = "";
        String message = "";

        String strNotificationContent = data.get("data");

        try{
            JSONObject jsonObject = new JSONObject(strNotificationContent);
            title = jsonObject.getString("title");
            message = jsonObject.getString("description");
        }catch(Exception e){

        }

        Intent intent = new Intent(this, NotificationActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("message", message);

        //PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), NotificationActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            /*case REQUEST_RIDE_ID:
                String rideData = data.get(RIDE_DATA_KEY);
                SentRequestResponse.RequestedRidesInfo requestedRidesInfo = gson.fromJson(rideData, SentRequestResponse.RequestedRidesInfo.class);
                pendingIntent = PendingIntent.getActivity(getApplicationContext(), REQUEST_RIDE_ID,
                        new Intent(getApplicationContext(), RequestAcceptActivity.class).putExtra(RIDE_DATA,
                                requestedRidesInfo), PendingIntent.FLAG_UPDATE_CURRENT);
                break;
            case ACCEPT_RIDE_ID:
                pendingIntent = PendingIntent.getActivity(getApplicationContext(), ACCEPT_RIDE_ID,
                        new Intent(getApplicationContext(), MyRidesActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
                break;
            case REJECT_RIDE_ID:
                pendingIntent = PendingIntent.getActivity(getApplicationContext(), REJECT_RIDE_ID,
                        new Intent(getApplicationContext(), MyRidesActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
                break;
            case START_RIDE_ID:
                pendingIntent = PendingIntent.getActivity(getApplicationContext(), START_RIDE_ID,
                        new Intent(getApplicationContext(), MyRidesActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
                break;
            case COMPLETE_RIDE_ID:
                pendingIntent = PendingIntent.getActivity(getApplicationContext(), COMPLETE_RIDE_ID,
                        new Intent(getApplicationContext(), MyRidesActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
                break;
            default:
                pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                        new Intent(getApplicationContext(), Splash.class), PendingIntent.FLAG_UPDATE_CURRENT);
                break;*/

        // intent.putExtra("key", "value");
        //PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 100, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel(getApplicationContext());
        Notification notif = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notif = new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                    .setContentIntent(pendingIntent)
                    // .setStyle(new Notification.BigPictureStyle().bigPicture(result))
                    .build();
        }


        notif.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(1, notif);

       /* NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "My notificaitons")
                .setSmallIcon(R.drawable.gatyak)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(1, mBuilder.build());*/
    }
    private void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "gatyatmak";
            String description = "notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            channel.setShowBadge(false);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void generateNotification(String message, String title,String url) {
//        if(!url.equalsIgnoreCase(""))
//        {
//            try {
//                URL urls = new URL(url);
//                image = BitmapFactory.decodeStream(urls.openConnection().getInputStream());
//            } catch(IOException e) {
//                System.out.println(e);
//            }
//        }
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher2);
        count++;
        Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        intent.putExtra("title",title);
        intent.putExtra("message", message);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setSmallIcon(R.drawable.ic_test); //a resource for your custom small icon
        notificationBuilder.setLargeIcon(bitmap); //a resource for your custom small icon
        notificationBuilder.setContentTitle(title) ;//the "title" value you sent in your notification
        notificationBuilder.setContentText(message); //ditto
        notificationBuilder.setAutoCancel(true) ; //dismisses the notification on click
        notificationBuilder.setSound(defaultSoundUri);
        notificationBuilder.setAutoCancel( true ) ;
        notificationBuilder.setBadgeIconType( BADGE_ICON_SMALL ) ;
        notificationBuilder.setNumber( count ) ;

        //Setting up Notification channels for android O and above
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("3", "CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            notificationBuilder.setChannelId("3");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        PendingIntent contentIntent = PendingIntent.getActivity(this, 3, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(3, notificationBuilder.build());
    }

    private void generateNotificationWithoutImage(String message, String title) {
        count++;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher2);
        Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("message", message);
        intent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setSmallIcon(R.drawable.ic_test); //a resource for your custom small icon
        notificationBuilder.setLargeIcon(bitmap); //a resource for your custom small icon
        notificationBuilder.setContentTitle(title) ;//the "title" value you sent in your notification
        notificationBuilder.setContentText(message); //ditto
        notificationBuilder.setAutoCancel(true) ; //dismisses the notification on click
        notificationBuilder.setSound(defaultSoundUri);
        notificationBuilder.setAutoCancel( true ) ;
        notificationBuilder.setBadgeIconType( BADGE_ICON_SMALL ) ;
        notificationBuilder.setNumber( count ) ;
        //Setting up Notification channels for android O and above
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("3", "CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            notificationBuilder.setChannelId("3");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        PendingIntent contentIntent = PendingIntent.getActivity(this, 3, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(3, notificationBuilder.build());
    }


}