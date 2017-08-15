package com.wifi.yilong.yilongwifi.Activity.Dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/1/22.
 */

public class ProgressDialogFragment extends DialogFragment {
    public static final String  ProgressDialogFragmentTitle = "Title";
    public static final String  ProgressDialogFragmentMessage = "Message";
    public static final String ProgressDialogTag = "ProgressDialog";

    public static ProgressDialogFragment show(FragmentActivity c , int title , int msg){
        FragmentManager fm = c.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fg = fm.findFragmentByTag(ProgressDialogFragment.ProgressDialogTag);
        if(fg != null){
            ft.remove(fg);
        }
        ft.addToBackStack(null);

        ProgressDialogFragment pdfg = ProgressDialogFragment.newInstance(title,
                msg);
        pdfg.show(ft , ProgressDialogFragment.ProgressDialogTag);

        return pdfg;
    }
    private static ProgressDialogFragment newInstance(int title , int msg){
        ProgressDialogFragment fg = new ProgressDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ProgressDialogFragmentTitle , title);
        args.putInt(ProgressDialogFragmentMessage , msg);
        fg.setArguments(args);

        return fg;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt(ProgressDialogFragmentTitle);
        int msg = getArguments().getInt(ProgressDialogFragmentMessage);
        ProgressDialog dg = new ProgressDialog(getActivity());
        dg.setTitle(title);
        dg.setMessage(getString(msg));
        dg.setIndeterminate(true);
        dg.setProgressStyle(ProgressDialog.STYLE_SPINNER);


        return dg;
    }
}
