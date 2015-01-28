package com.JazzDevStudio.LacunaExpress.MISCClasses;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.JazzDevStudio.LacunaExpress.R;
import com.JazzDevStudio.LacunaExpress.SelectMessageActivity2;
import com.JazzDevStudio.LacunaExpress.Splash;

//This class is used to handle adding and removing of notifications in the notification bar (on top)
public class Notifications {

	/*
	Add the notification.
	@Params:
	1) Context
	2) The notification manager being used
	3) The unique integer that is being used to identify this particular notification
	4) The body of the message within the notification that will be displayed
	5) The title of the notification that will be displayed
	6) The class that you want opened when the user clicks the notification bar. Leave OFF the .class
	specification. NOTE: If you want it to open up a class specifically, you will need to code into
	the if else statement below which house the notification builder
	7) The username being passed. If this is not needed for your notification, you may pass null
	8) The Mail tag_chosen being passed. If this is not needed for your notification, you may pass null
	 */
	public static void addNotification(Context context,
	                                   NotificationManager manager,
	                                   int uniqueID,
	                                   String body,
	                                   String title,
	                                   String class_to_open,
	                                   String username,
	                                   String tag_chosen) {

		if (class_to_open.equalsIgnoreCase("SelectMessageActivity2")){
			NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
					.setSmallIcon(R.drawable.message_notification_icon)
					.setContentTitle(title)
					.setContentText(body);

			//Change what you want opened here in the second Parameter
			Intent notificationIntent = new Intent(context, SelectMessageActivity2.class);
			notificationIntent.putExtra("chosen_account_string", username);
			notificationIntent.putExtra("tag_chosen", tag_chosen);
			PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

			builder.setContentIntent(intent);

			//Add as notification
			manager.notify(uniqueID, builder.build());
		} else {
			NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
					.setSmallIcon(R.drawable.notification_logo_small)
					.setContentTitle(title)
					.setContentText(body);

			//Change what you want opened here in the second Parameter
			Intent notificationIntent = new Intent(context, Splash.class);
			PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

			builder.setContentIntent(intent);

			//Add as notification
			manager.notify(uniqueID, builder.build());
		}
	}

	public static void removeNotification(NotificationManager manager, int uniqueID){
		manager.cancel(uniqueID);
	}
}
