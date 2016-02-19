package com.example.shay.soundcloudexample.project.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.WindowManager;

import com.example.shay.soundcloudexample.R;

/**
 * Created by shay on 18/02/2016.
 */
public class DialogHelper {

    public static void showErrorDialog(final Activity activity, String error) {
        showErrorDialog(activity, false,error);
    }

    public static void showErrorDialog(final Activity activity,
                                           final boolean withDissmisActivity,final String error) {
        if (activity == null) {
            return;
        }
        try {



            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    new ContextThemeWrapper(activity,
                            android.R.style.Theme_Holo_Light_Dialog));
            alertDialogBuilder.setMessage(error);
            alertDialogBuilder.setPositiveButton(
                    getConfirmStringButton(activity),
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            if (withDissmisActivity) {
                                activity.finish();
                            }
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setCancelable(false);
            alertDialog.getWindow().setType(
                    WindowManager.LayoutParams.TYPE_SYSTEM_ERROR);
            alertDialog.show();
        } catch (Exception e) {
            FileUtils.saveException(e, LoggingHelper.getMethodName());
        }
    }

    private static String getConfirmStringButton(Context context) {


        String accept;
        accept = context.getResources().getString(R.string.ok);


        return accept;
    }
}
