package com.mindvalley.mindvalleyexercise.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.util.Log;

import com.mindvalley.mindvalleyexercise.modules.common.DialogListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * This class is used to perform commonly used functions throughout the application. Functions like - 1)Show/Dismiss Loader 2)Show/Dismiss Snackbar
 *
 * @author AjinkyaD
 * @version 1.0
 */

public class Utilities {
    private static AlertDialog.Builder alertDialogBuilder;
    private static AlertDialog alertDialog;

    /**
     * This function is used to detect whether the phone is in Airplane Mode And WIFI is turned off
     *
     * @param context
     * @return
     */
    public static boolean isAirplaneModeWithNoWIFI(Context context) {
        return isAirplaneModeOn(context) && !isWifiEnabled(context);
    }

    static boolean isWifiEnabled(Context context) {
        WifiManager mng = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return mng.isWifiEnabled();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Gets the state of Airplane Mode.
     *
     * @param context - The current app context
     * @return true if enabled.
     */
    private static boolean isAirplaneModeOn(Context context) {
        return Settings.Global.getInt(context.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }

    /**
     * This function is used to display the Alert Dialog Box (Multiple Option) to the User.
     * <p>
     * This function also handles the operations users can perform on the Alert Dialog.
     *
     * @param currentContext - The Context
     * @param dialogID       - The Unique Dialog ID for current screen.
     * @param dialogListener - The Dialog Event Listener
     * @param params         - The Alert Dialog Display Parameters (Heading,Detail Message,Action Texts)
     */
    public static void showAlertDialogMultipleOptions(Context currentContext, final int dialogID, final DialogListener dialogListener, String... params) {


        alertDialogBuilder = new AlertDialog.Builder(currentContext);
        alertDialogBuilder.setTitle(params[0]);
        alertDialogBuilder.setMessage(params[1]);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(params[2], new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (dialogListener != null) {
                    dialogListener.onPositiveAction(dialogID, null);
                }
            }
        });
        alertDialogBuilder.setNegativeButton(params[3], new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (dialogListener != null) {
                    dialogListener.onNegativeAction(dialogID, null);
                }
            }
        });
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * This function is used to convert the Calendar date to output Format.
     *
     * @param outputFormat - The desired ouput format of the date
     * @param inputDate    - The Date in Calendar Object
     * @return - The formatted date
     */
    public static String formattedDateFromDate(Date inputDate, String outputFormat) {

        String outputDate = "";

        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, Locale.getDefault());

        try {
            outputDate = df_output.format(inputDate);
        } catch (Exception e) {
            Log.e("formattedDateFromCal", "Exception in formattedDateFromCalendar(): " + e.getMessage());
        }
        return outputDate;

    }

    /**
     * This function is used to convert the String formatted date into Calendar instance.
     *
     * @param inputFormat - The Input Date format
     * @param inputDate   -The input Date in String
     * @return - The Calendar instance
     */
    public static Date getDateFromString(String inputFormat, String inputDate) {

        Date parsed = null;

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
        } catch (Exception e) {
            Log.e("formattedDateFromString", "Exception in formattedDateFromString(): " + e.getMessage());
        }
        return parsed;

    }
}
