package com.example.beginneractivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {


    Context context;

    ArrayList<String> dishName;
    ArrayList<String> dishResturant;
    ArrayList<String> dishPrice;
    ArrayList<String> dishDescription;
    ArrayList<Integer>image;

    LayoutInflater inflater;

    public ListViewAdapter(Context a, ArrayList<Integer>imageViews , ArrayList<String> dishName,ArrayList<String> dishResturant,
                           ArrayList<String>dishDescription, ArrayList<String> dishPrice)
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

    public View getView(int i, View view, ViewGroup viewGroup) {

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View item_view = inflater.inflate(R.layout.listlayout,viewGroup,false);

        ImageView imageView = item_view.findViewById(R.id.image);
        TextView name = item_view.findViewById(R.id.dishname);
        TextView resturant = item_view.findViewById(R.id.dishResturant);
        TextView description = item_view.findViewById(R.id.disDiscription);
        TextView price = item_view.findViewById(R.id.dishPrice);


        imageView.setImageResource(image.get(i));
        name.setText(dishName.get(i));
        resturant.setText(dishResturant.get(i));
        description.setText(dishDescription.get(i));
        price.setText(dishPrice.get(i));

        return item_view;
    }


}


