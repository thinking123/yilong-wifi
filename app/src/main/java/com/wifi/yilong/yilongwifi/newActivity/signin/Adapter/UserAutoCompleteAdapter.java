package com.wifi.yilong.yilongwifi.newActivity.signin.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.wifi.yilong.yilongwifi.Http.rest.model.User;
import com.wifi.yilong.yilongwifi.Infrastructure.Utils;
import com.wifi.yilong.yilongwifi.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/2/16.
 */

public class UserAutoCompleteAdapter extends BaseAdapter implements Filterable {


    private Context mContext;
    private List<User> mResultList = new ArrayList<User>();

    public UserAutoCompleteAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mResultList.size();
    }

    @Override
    public User getItem(int position) {
        return mResultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.autocompletetext_view_row,
                    parent, false);
        }

        viewHolder = new ViewHolder(convertView);

//        ((TextView) convertView.findViewById(R.id.autocompletetext_view_row_name)).setText(getItem(position).name);
//        ((TextView) convertView.findViewById(R.id.autocompletetext_view_row_account)).setText(getItem(position).email);
        viewHolder.autocompletetextViewRowName.setText(getItem(position).name);
        viewHolder.autocompletetextViewRowAccount.setText(getItem(position).email);
        viewHolder.autocompletetextViewRowDelete.setTag(position);
        viewHolder.autocompletetextViewRowDelete.setOnClickListener(v -> {
          Utils.Tip(mContext , "test");
            deleteUserStorage(v);
        });
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    List<User> users = findUsers(mContext, constraint.toString());

                    // Assign the data to the FilterResults
                    filterResults.values = users;
                    filterResults.count = users.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    mResultList = (List<User>) results.values;
                    notifyDataSetChanged();
                    ;
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };

        return filter;
    }

    private List<User> findUsers(Context context, String account) {
        return new Select()
                .from(User.class)
                .where("email LIKE ?",  new String[]{'%' + account + '%'})
                .orderBy("name ASC").execute();
    }

    @OnClick(R.id.autocompletetext_view_row_delete)
    public void deleteUserStorage(View button){
        int pos = (int)button.getTag();
        if(pos >= 0 && pos < mResultList.size()){
            new AlertDialog.Builder(mContext)
                    .setMessage("Do you want delete user message")
                    .setTitle("Delete")
                    .setPositiveButton("Yse" , (dialog , id)->{
                        User user = mResultList.get(pos);
                        new Delete().from(User.class).where("userId = ?" , user.id).execute();
                        Utils.Tip(mContext , "delete successful");
                    })
                    .setNegativeButton("No" ,null)
            .show();

        }
    }
    static class ViewHolder {
        @BindView(R.id.autocompletetext_view_row_name)
        TextView autocompletetextViewRowName;
        @BindView(R.id.autocompletetext_view_row_account)
        TextView autocompletetextViewRowAccount;
        @BindView(R.id.autocompletetext_view_row_delete)
        Button autocompletetextViewRowDelete;

        ViewHolder(View view) {
            //binding view
            ButterKnife.bind(this, view);
        }
    }
}
