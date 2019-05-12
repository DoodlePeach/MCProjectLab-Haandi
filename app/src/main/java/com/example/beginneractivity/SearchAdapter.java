package com.example.beginneractivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SearchAdapter extends ArrayAdapter <SearchItem>
{
    Context context;
    int resource;

    SearchAdapter(Context context, int resource, List<SearchItem> items)
    {
        super(context, resource, items);
        this.resource = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(resource, null);
        }

        SearchItem rawRowData = getItem(position);

        ImageView dishPic = convertView.findViewById(R.id.dish_img);
        TextView dishName = convertView.findViewById(R.id.dish_name);
        TextView dishDesc = convertView.findViewById(R.id.dish_desc);
        TextView dishPrice = convertView.findViewById(R.id.dish_price);

        dishPic.setImageResource(rawRowData.imageSrc);
        dishName.setText(rawRowData.dishName);
        dishDesc.setText(rawRowData.dishDescription);
        dishPrice.setText(Integer.toString(rawRowData.dishPrice) + "$");

        return convertView;
    }
}
