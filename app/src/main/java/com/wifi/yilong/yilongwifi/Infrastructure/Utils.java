package com.wifi.yilong.yilongwifi.Infrastructure;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wifi.yilong.yilongwifi.R;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/1/23.
 */

public class Utils {
    public static void Tip(Context c , int msg){
        Toast.makeText(c , msg , Toast.LENGTH_LONG).show();
    }
    public static void Tip(Context c , String msg){
        Toast.makeText(c , msg , Toast.LENGTH_LONG).show();
    }

    public static void TipSnackBar(View parent ,String msg ,  View.OnClickListener onClickListener , String actionTitle){
       Snackbar snackbar =  Snackbar.make(parent ,
                msg ,
                Snackbar.LENGTH_INDEFINITE);

        if(onClickListener != null){
            snackbar.setAction(R.string.dialog_ok, onClickListener);
        }

        snackbar.show();
    }
    public static void TipSnackBar(View parent ,int msg ,  View.OnClickListener onClickListener , int actionTitle){
        Snackbar snackbar =  Snackbar.make(parent ,
                msg ,
                Snackbar.LENGTH_INDEFINITE);

        if(onClickListener != null){
            snackbar.setAction(R.string.dialog_ok, onClickListener);
        }

        snackbar.show();
    }
    public static boolean IsStringEmpty(String src){
        return src != null && !src.isEmpty();
    }

    public static Date parseDate(String str){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date date = format.parse(str);
            return date;
        } catch (Exception e) {
            MyLog.Debug(e.getMessage());
        }

        return new Date();
    }

    public static String DateToString(Date date){
        SimpleDateFormat simpleDate =  new SimpleDateFormat("dd/MM/yyyy");

        return  simpleDate.format(date);
    }
    public static void HiddenKeyboard(Activity c){
        View view = c.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)c.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static boolean IsJsonEmpty(JSONObject js){
        return js != null && js.length() > 0;
    }

    @SuppressWarnings("deprecation")
    public static boolean IsNetworkAvailable(Context c){
        ConnectivityManager connectivityManager =
                (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivityManager.getBackgroundDataSetting() && connectivityManager.getActiveNetworkInfo() != null;
    }

    public static ProgressDialog getProgressDialog(Activity activity){
        ProgressDialog progressDialog = new ProgressDialog(activity);
//        progressDialog.setMessage("Its loading....");
        progressDialog.setTitle("正在加载...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        return progressDialog;
    }
    public static Dialog getCustomProgressDialog(Activity activity , String msg , View.OnClickListener onClickListener){
        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_custom_progressdialog);
        //set dialog background color to transprent
        dialog.getWindow().getDecorView().getBackground().setAlpha(0);

        Button button = (Button)dialog.findViewById(R.id.dialog_custom_progressdialog_close_button);
        button.setOnClickListener(v -> {
            if(onClickListener != null){
                onClickListener.onClick(v);
            }
            dialog.dismiss();
        });
        TextView textView = (TextView)dialog.findViewById(R.id.dialog_custom_progressdialog_msg);
        if(TextUtils.isEmpty(msg)){
            textView.setText("载入中...");
        }else {
            textView.setText(msg);
        }
        dialog.setCancelable(false);
        return dialog;

    }

    public static Dialog getAlertDialog(Activity activity ,
                                        int title ,
                                        int msg ,
                                        int iconId ,
                                        int yesTitleId ,
                                        DialogInterface.OnClickListener onClickListenerYes ,
                                        boolean isShowNoButton,
                                        int noTitleId ,
                                        DialogInterface.OnClickListener onClickListenerNo ){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        if(title != 0){
            builder.setTitle(title);
        }else{
            builder.setTitle(R.string.dialog_alert_dialog_title);
        }

        if(msg != 0){
            builder.setMessage(msg);
        }else {
            builder.setMessage(R.string.dialog_alert_dialog_msg);
        }

        if(iconId != 0){
            builder.setIcon(iconId);
        }else{
            builder.setIcon(R.drawable.logout);
        }
        if(isShowNoButton){
            builder.setNegativeButton(noTitleId != 0 ? noTitleId : R.string.dialog_cancel , (dialog , which)->{
                if(onClickListenerNo != null){
                    onClickListenerNo.onClick(dialog ,which);
                }
                dialog.dismiss();
            });
        }


        builder.setPositiveButton(yesTitleId != 0 ? yesTitleId : R.string.dialog_ok , (dialog , which)->{
            if(onClickListenerYes != null){
                onClickListenerYes.onClick(dialog ,which);
            }
            dialog.dismiss();
        });

        Dialog dialog = builder.create();
        dialog.setCancelable(false);

        return  dialog;
    }

}
