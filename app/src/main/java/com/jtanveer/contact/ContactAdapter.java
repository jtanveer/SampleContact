package com.jtanveer.contact;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jtanveer on 5/27/15.
 */
public class ContactAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    ArrayList<Bundle> mData;

    public ContactAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mData = new ArrayList<>();
    }

    public void setData(ArrayList<Bundle> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void clearData() {
        mData = new ArrayList<Bundle>();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Bundle getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position + 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.contact_row, parent, false);
            holder = new ViewHolder();
            holder.photo = (ImageView) convertView.findViewById(R.id.imageView);
            holder.name = (TextView) convertView.findViewById(R.id.textView);
            holder.number = (TextView) convertView.findViewById(R.id.textView2);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Bundle item = getItem(position);
        try {
            Uri uri = Uri.parse(item.getString("photo"));
            if (uri != null)
                holder.photo.setImageURI(uri);
        } catch (Exception e) {
//            e.printStackTrace();
            holder.photo.setImageResource(R.mipmap.ic_launcher);
        }
        holder.name.setText(item.getString("name"));
        holder.number.setText(item.getString("phone"));

        return convertView;
    }

    class ViewHolder {
        ImageView photo;
        TextView name;
        TextView number;
    }
}
