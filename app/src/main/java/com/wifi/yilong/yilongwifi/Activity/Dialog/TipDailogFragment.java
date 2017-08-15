package com.wifi.yilong.yilongwifi.Activity.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wifi.yilong.yilongwifi.Infrastructure.AppConstant;
import com.wifi.yilong.yilongwifi.R;

/**
 * Created by Administrator on 2017/1/25.
 */

public class TipDailogFragment extends DialogFragment{

    public  static TipDailogFragment newInstance(int title , int msg){
        TipDailogFragment fg = new TipDailogFragment();
        Bundle args = new Bundle();
        args.putInt(AppConstant.Dialog.TITLE , title);
        args.putInt(AppConstant.Dialog.MESSAGE , msg);
        fg.setArguments(args);

        return fg;
    }

    private int title;
    private int msg;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null){
            title = args.getInt(AppConstant.Dialog.TITLE);
            msg = args.getInt(AppConstant.Dialog.MESSAGE);
        }
    }

    private void setResult(int resCode){
        if(getTargetFragment() == null){
            return;
        }

//        Intent i = new Intent();
//        i.putExtra("")

        getTargetFragment().onActivityResult(getTargetRequestCode() , resCode , null);
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        b.setTitle(title);
        b.setMessage(msg);
        b.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResult(Activity.RESULT_OK);
            }
        });
        b.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResult(Activity.RESULT_CANCELED);
            }
        });

        return b.create();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
