package com.cvmaker.utils;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.cvmaker.R;

import java.io.ByteArrayOutputStream;
import java.util.Locale;
import java.util.Objects;

import static com.cvmaker.utils.LogUtils.LOGE;

/**
 * Created by Aya Mohamed on 11/17/2019.
 */
public final class CommonUtils {

    private static AlertDialog alertDialog;

    //method to convert the selected image to base64 encoded string

    public static void downloadFile(Context context, String DownloadUrl, String fileName) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(DownloadUrl));
        request.setDescription(fileName);   //appears the same in Notification bar while downloading
        request.setTitle(fileName);
        request.setVisibleInDownloadsUi(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            //request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        //request1.setDestinationInExternalFilesDir(context.getApplicationContext(), "/", fileName);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                fileName);

        DownloadManager manager1 = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Objects.requireNonNull(manager1).enqueue(request);
        if (DownloadManager.STATUS_SUCCESSFUL == 8) {
            //DownloadSuccess();
            Log.w("download", "success");
            //CommonUtils.showAlterDialogStyled(context, context.getString(R.string.download_done), AppConstants.SUCCESS_MESSAGE);

        }
    }

    public static String ConvertBitmapToString(Bitmap bitmap) {
        String encodedImage = "";
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        return encodedImage;
    }

    public static void changeLanguage(Activity activity, String currentLang) {
        Resources res = activity.getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLocale(new Locale(currentLang.toLowerCase()));
            // API 17+ only.
            // Use conf.locale = new Locale(...) if targeting lower versions
        } else {
            conf.locale = new Locale(currentLang.toLowerCase());
        }
        res.updateConfiguration(conf, dm);

        activity.getApplication().getBaseContext().getResources().updateConfiguration(conf,
                activity.getBaseContext().getResources().getDisplayMetrics());
    }


    public static void showAlterDialogStyled(Activity context, String message, String messageType) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context, android.R.style.Theme_Black_NoTitleBar);
        LayoutInflater inflater = context.getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.alert_dialog, null);
        dialogView.findViewById(R.id.tv_title).setVisibility(View.GONE);
        ((TextView) dialogView.findViewById(R.id.tv_message)).setText(message);
        if (messageType.equals(AppConstants.ERROR_MESSAGE)) {
            /*((ImageView) dialogView.findViewById(R.id.iv_alert))
                    .setImageDrawable(context.getResources().getDrawable(R.drawable.error));*/
        } else if (messageType.equals(AppConstants.SUCCESS_MESSAGE) || messageType.equals(AppConstants.BACK)) {
            /*((ImageView) dialogView.findViewById(R.id.iv_alert))
                    .setImageDrawable(context.getResources().getDrawable(R.drawable.success));*/
            ((TextView) dialogView.findViewById(R.id.btn_dismiss)).setText(context.getResources().getString(R.string.ok));
            dialogView.findViewById(R.id.tv_message).setVisibility(View.VISIBLE);
        }
        dialogView.findViewById(R.id.btn_dismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        ViewGroup.LayoutParams params = alertDialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        alertDialog.getWindow().setAttributes((WindowManager.LayoutParams) params);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        alertDialog.show();
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        LOGE("MIMETYPE: " + getMimeType(context, contentUri));
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static String getMimeType(Context context, Uri uri) {
        String mimeType = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }

    private CommonUtils() {
        // This utility class is not publicly instantiable
    }

}
