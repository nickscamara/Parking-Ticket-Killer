package com.example.nick.ptk;

/**
 * Created by cc1057 on 2/11/18.
 */

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by cc1057 on 2/2/18.
 */

public class listviewadapter extends BaseAdapter {

    Context context;
    Activity activity;
    String[] Stringlist = new String[30];
    String[] Stringlist1 = new String[30];



    public listviewadapter(Context context,Activity activity) {
        //super(context, R.layout.single_list_app_item, utilsArrayList);
        this.context = context;
        this.activity = activity;
        for(int i = 0; i<30;i++)
        {
            Stringlist[i] = (i + 1)*5+" parkings        ";
            Stringlist1[i]= " "+(i + 1)*5*20;
        }

    }

    @Override
    public int getCount() {
        return Stringlist.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View recentlyplaynode, @NonNull ViewGroup parent) {


        ViewHolder viewHolder;

        final View result;

        if (recentlyplaynode == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            recentlyplaynode = inflater.inflate(R.layout.listnode, parent, false);
            viewHolder.text = (TextView) recentlyplaynode.findViewById(R.id.text);
            viewHolder.text1 = (TextView) recentlyplaynode.findViewById(R.id.text1);
            viewHolder.listimage = recentlyplaynode.findViewById(R.id.listimage);
            viewHolder.listnode = recentlyplaynode.findViewById(R.id.listnode);


            result = recentlyplaynode;

            recentlyplaynode.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) recentlyplaynode.getTag();
            result = recentlyplaynode;
        }

        viewHolder.text.setText(Stringlist[position]);
        viewHolder.listimage.setBackgroundResource(R.drawable.parking_points);
        viewHolder.text1.setText(Stringlist1[position]);
        return result;
    }

    private static class ViewHolder {

        TextView text;
        TextView text1;
        ImageView listimage;
        ConstraintLayout listnode;


    }


}