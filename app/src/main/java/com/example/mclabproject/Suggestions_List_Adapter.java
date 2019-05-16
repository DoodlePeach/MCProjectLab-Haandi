package com.example.mclabproject;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Suggestions_List_Adapter extends BaseAdapter {


    Context context;

    ArrayList<String> dishName;
    ArrayList<String> dishResturant;
    ArrayList<String> dishPrice;
    ArrayList<String> dishDescription;
    ArrayList<String>image;
    LayoutInflater inflater;

    public Suggestions_List_Adapter(Context a, ArrayList<String>imageViews , ArrayList<String> dishName, ArrayList<String> dishResturant,
                             ArrayList<String>dishDescription, ArrayList<String> dishPrice, Handler handler)
    {
        this.context = a ;

        this.dishName = dishName;
        this.dishResturant = dishResturant;
        this.dishPrice = dishPrice;
        this.dishDescription=dishDescription;
        this.image = imageViews;
    }

    public int getCount()
    {
        return dishName.size();
    }

    public Object getItem(int abc)
    {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View item_view = inflater.inflate(R.layout.suggestion_list_layout,viewGroup,false);

        final ImageView imageView = item_view.findViewById(R.id.image);
        TextView name = item_view.findViewById(R.id.dishname);
        TextView resturant = item_view.findViewById(R.id.dishResturant);
        TextView price = item_view.findViewById(R.id.dishPrice);

        Picasso.get().load(Uri.parse(image.get(i))).fit().into(imageView);
        name.setText(dishName.get(i));
        resturant.setText(dishResturant.get(i));
        price.setText("$"+dishPrice.get(i));

        return item_view;
    }



}


