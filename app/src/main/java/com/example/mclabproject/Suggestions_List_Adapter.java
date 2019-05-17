package com.example.mclabproject;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.annotations.NotNull;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Suggestions_List_Adapter extends RecyclerView.Adapter<Suggestions_List_Adapter.CustomViewHolder> {
    Context context;

    ArrayList<String> dishName;
    ArrayList<String> dishResturant;
    ArrayList<String> dishPrice;
    ArrayList<String> dishDescription;
    ArrayList<String>image;
    LayoutInflater inflater;
    descFragment.RecyclerItemClick itemClickListener;

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        public ImageView img;
        public TextView name, resturant, price;

        public CustomViewHolder(View v)
        {
            super(v);
            img = v.findViewById(R.id.image);
            this.name = v.findViewById(R.id.dishname);
            this.price = v.findViewById(R.id.dishPrice);
            (v.findViewById(R.id.cv_suggestion_list)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.OnItemClickListener(getAdapterPosition());
                }
            });
        }
    }

    public Suggestions_List_Adapter(Context a, ArrayList<String>imageViews , ArrayList<String> dishName, ArrayList<String> dishResturant,
                                    ArrayList<String>dishDescription, ArrayList<String> dishPrice, descFragment.RecyclerItemClick itemClickListener)
    {
        this.context = a ;

        this.dishName = dishName;
        this.dishResturant = dishResturant;
        this.dishPrice = dishPrice;
        this.dishDescription=dishDescription;
        this.image = imageViews;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public Suggestions_List_Adapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View v =  layoutInflater.inflate(R.layout.suggestion_list_layout, parent, false);

        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NotNull CustomViewHolder holder, int position) {
        holder.name.setText(dishName.get(position));
        holder.price.setText(dishPrice.get(position) + "$");
        Picasso.get().load(Uri.parse(image.get(position))).fit().into(holder.img);
    }

    @Override
    public int getItemCount() {return dishName.size();}

    public long getItemId(int i) {
        return 0;
    }


}


