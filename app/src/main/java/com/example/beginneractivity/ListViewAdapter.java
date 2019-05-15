package com.example.beginneractivity;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.IpSecManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;
import android.os.Handler;;import com.squareup.picasso.Picasso;

public class ListViewAdapter extends BaseAdapter {
    Context context;

    ArrayList<String> dishName;
    ArrayList<String> dishResturant;
    ArrayList<String> dishPrice;
    ArrayList<String> dishDescription;
    ArrayList<String>image;
    LayoutInflater inflater;

    public ListViewAdapter(Context a, ArrayList<String>imageViews , ArrayList<String> dishName,ArrayList<String> dishResturant,
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

        View item_view = inflater.inflate(R.layout.listlayout,viewGroup,false);

        final ImageView imageView = item_view.findViewById(R.id.image);
        TextView name = item_view.findViewById(R.id.dishname);
        TextView resturant = item_view.findViewById(R.id.dishResturant);
        TextView description = item_view.findViewById(R.id.disDiscription);
        TextView price = item_view.findViewById(R.id.dishPrice);

        Picasso.get().load(Uri.parse(image.get(i))).fit().into(imageView);
        name.setText(dishName.get(i));
        resturant.setText(dishResturant.get(i));
        description.setText(dishDescription.get(i));
        price.setText(dishPrice.get(i));

        return item_view;
    }
}


