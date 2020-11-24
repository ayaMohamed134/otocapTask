package com.cvmaker.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.cvmaker.R;


/**
 * Created by Aya Mohamed on 11/13/2019.
 */
public final class AppUtils {
    private AppUtils() {
        // This class is not publicly instantiable
    }

    public static void openPlayStoreForApp(Context context) {
        final String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(context
                            .getResources()
                            .getString(R.string.app_market_link) + appPackageName)));
        } catch (android.content.ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(context
                            .getResources()
                            .getString(R.string.app_google_play_store_link) + appPackageName)));
        }
    }

    public static void shareApp(Activity context) {
        final String appPackageName = context.getPackageName();
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.app_name) + ":");
            String sAux = context.getString(R.string.download_app) +"\n";
            sAux = sAux + context
                    .getResources()
                    .getString(R.string.app_google_play_store_link) + appPackageName + "\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(Intent.createChooser(i, "choose one"));
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }
    }

    public static void makeCall(Activity context, String phone) {
        String mobileNum = "tel:" + phone;
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(mobileNum));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        context.startActivity(intent);
    }

    public static void openBrowser(Activity activity, String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        activity.startActivity(i);
    }

    public static void sendEmail(Activity context, String email) {
        /* Create the Intent */
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        /* Fill it with Data */
        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        /* Send it off to the Activity-Chooser */
        context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }
}
