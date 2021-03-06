package com.example.mclabproject;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;

public class PromosAdapter extends BaseAdapter {
    private Context context;
    private int resId;
    private LinkedList<Promos> promos;

    PromosAdapter(Context context, int resId, LinkedList<Promos> promos)
    {
        //super(context, resId, promos);
        //super(context, resId);

        this.context = context;
        this.resId = resId;
        this.promos = promos;
    }

    public int getCount()
    {
        return promos.size();
    }

    public Object getItem(int abc)
    {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.promos_layout, viewGroup, false);

        ImageView img = view.findViewById(R.id.img_promo);
        TextView desc = view.findViewById(R.id.tv_promo_desc);
        TextView number = view.findViewById(R.id.tv_promo_number);

        Picasso.get().load(Uri.parse(promos.get(i).getImageSrc())).fit().into(img);
        desc.setText(promos.get(i).getPromoDesc());
        number.setText(promos.get(i).getPromoCode());

        return view;
    }
}
