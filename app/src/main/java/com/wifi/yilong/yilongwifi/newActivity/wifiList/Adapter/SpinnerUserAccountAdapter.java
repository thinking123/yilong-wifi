package com.wifi.yilong.yilongwifi.newActivity.wifiList.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.wifi.yilong.yilongwifi.Http.rest.model.User;
import com.wifi.yilong.yilongwifi.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/18.
 */

public class SpinnerUserAccountAdapter extends ArrayAdapter<User> {


    private Context mContext;
    private List<User> mUsers;

        private String mCurrentId;
    public SpinnerUserAccountAdapter(Context context , String currentId ,  List<User> users) {
        super(context , 0 , users);
        mContext = context;
        mUsers = new Select().all().from(User.class).execute();
        mCurrentId =currentId;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return mUsers.size();
    }

    @Override
    public User getItem(int position) {
        return mUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.row_spinner_user_account, parent, false);

            ViewHolder viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        User user = mUsers.get(position);
        viewHolder.rowSpinnerUserAccount.setText(user.email);
        viewHolder.rowSpinnerUserName.setText(user.name);
//        if(mCurrentId == user.id){
            viewHolder.rowSpinnerUserAccountContainer.setBackgroundResource(R.drawable.spinner_select_row_background);
//        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.row_spinner_user_account)
        TextView rowSpinnerUserAccount;
        @BindView(R.id.row_spinner_user_name)
        TextView rowSpinnerUserName;
        @BindView(R.id.row_spinner_user_account_container)
        LinearLayout rowSpinnerUserAccountContainer;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
