package com.example.beginneractivity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;


public class MainActivity extends AppCompatActivity implements  beginSearchFragment.OnFragmentInteractionListener,
                                                                searchListFragment.OnFragmentInteractionListener,
                                                                fullDescFragment.OnFragmentInteractionListener
{
    ActionBar toolbar;
    FragmentManager fragmentManager;
    DatabaseReference ref;
    static LinkedList<SearchItem> nonPromoItems = new LinkedList<>();
    static LinkedList<String> categories = new LinkedList<>();
    static LinkedList<String> locations = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        ref = FirebaseDatabase.getInstance().getReference();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nonPromoItems.clear();
                categories.clear();
                locations.clear();

                for(DataSnapshot snapshot : dataSnapshot.child("non-promo").getChildren())
                    nonPromoItems.add(snapshot.getValue(SearchItem.class));

                for(SearchItem item : nonPromoItems)
                    if(!categories.contains(item.getDishCat()))
                        categories.add(item.getDishCat());

                for(SearchItem item : nonPromoItems)
                    if(!locations.contains(item.resturantLocation))
                        locations.add(item.getResturantLocation());

                    notifyDatasetChanged();

                ProgressBar initalProgressBar = findViewById(R.id.pb_initial_loading);

                if(initalProgressBar.getVisibility() == View.VISIBLE)
                {
                    initalProgressBar.setVisibility(View.INVISIBLE);
                    findViewById(R.id.rl_main_view).setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        toolbar = getSupportActionBar();
        BottomNavigationView bottomNavigation = findViewById(R.id.navigationView);
        bottomNavigation.setItemBackground(getDrawable(R.drawable.navigationlayout));

        BottomNavigationView.OnNavigationItemSelectedListener navigationListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                switch (menuItem.getItemId())
                {
                    case R.id.navigation_search:
                    {
                        beginSearchFragment fragment = new beginSearchFragment();
                        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                        fragmentTransaction.replace(R.id.frame_layout, fragment, "StartingFragment");
                        fragmentTransaction.commit();

                        return true;
                    }

                    case R.id.navigation_login:
                    {
                        ///

                        return true;
                    }

                    case R.id.navigation_history:
                    {
                        ////

                        return true;
                    }

                    case R.id.navigation_help:
                    {

                        ///

                        return true;
                    }

                    default:
                        return false;
                }
            }
        };

        bottomNavigation.setOnNavigationItemSelectedListener(navigationListener);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        beginSearchFragment fragment = new beginSearchFragment();
        fragmentTransaction.add(R.id.frame_layout, fragment, "StartingFragment");
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentChange(String tag, Uri uri) {
        if(tag.equals("[LAUNCH] searchListFragment"))
        {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            searchListFragment fragment = new searchListFragment();

            fragmentTransaction.replace(R.id.frame_layout, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onFragmentInteractionWithItem(SearchItem item)
    {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fullDescFragment fragment = fullDescFragment.newInstance(item);

        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentSearchButtonClick(LinkedList<SearchItem> itemsToPass){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        searchListFragment fragment = searchListFragment.newInstance(itemsToPass);

        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(String tag, Uri uri)
    {
        ///;
    }

    public void notifyDatasetChanged()
    {
        beginSearchFragment fragment = (beginSearchFragment) fragmentManager.findFragmentByTag("StartingFragment");
        fragment.refresh();
    }

}
