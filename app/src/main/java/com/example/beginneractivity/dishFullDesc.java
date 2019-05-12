package com.example.beginneractivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class dishFullDesc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resturant_detail_layout);

        Intent intent = getIntent();
        SearchItem clickedObj = (SearchItem) intent.getSerializableExtra("ClickedObj");

        ImageView imageView = findViewById(R.id.dish_img);
        TextView dishDesc = findViewById(R.id.dish_name);
        TextView dishPrice = findViewById(R.id.dish_price);
        TextView dishResturant = findViewById(R.id.dish_resturant);
        RatingBar ratingBar = findViewById(R.id.ratingBar);

        imageView.setImageResource(clickedObj.imageSrc);
        dishDesc.setText(clickedObj.dishDescription);
        dishPrice.setText(clickedObj.dishPrice + "$");
        dishResturant.setText(clickedObj.dishResturant);
        ratingBar.setNumStars(4);

    }
}
