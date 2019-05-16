package com.example.mclabproject;
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
import android.view.Window;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedList;


public class MainActivity extends AppCompatActivity implements  searchFragment.OnFragmentInteractionListener,
                                                                searchListFragment.OnFragmentInteractionListener,
                                                                descFragment.OnFragmentInteractionListener,
                                                                LoginFragment.OnFragmentInteractionListener,
                                                                signupFragment.OnSignupButtonClicked,
                                                                promosFragment.OnUserDemandPromos,
                                                                priceTakerDialog.OnCorrectValuesEntered
{
    ActionBar toolbar;
    FragmentManager fragmentManager;
    DatabaseReference ref;
    User loggedInUser = null;
    static LinkedList<SearchItem> nonPromoItems = new LinkedList<>();
    static LinkedList<String> categories = new LinkedList<>();
    static LinkedList<String> locations = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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
                        searchFragment fragment = new searchFragment();
                        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                        fragmentTransaction.replace(R.id.frame_layout, fragment, "StartingFragment");
                        fragmentTransaction.commit();

                        return true;
                    }

                    case R.id.navigation_login:
                    {
                        LoginFragment fragment = new LoginFragment();
                        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                        fragmentTransaction.replace(R.id.frame_layout, fragment);
                        fragmentTransaction.commit();

                        return true;
                    }

                    case R.id.navigation_history:
                    {
                        promosFragment fragment = promosFragment.newInstance(loggedInUser);
                        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                        fragmentTransaction.replace(R.id.frame_layout, fragment);
                        fragmentTransaction.commit();

                        return true;
                    }

                    case R.id.navigation_help:
                    {

                        helpFragment fragment = new helpFragment();
                        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                        fragmentTransaction.replace(R.id.frame_layout, fragment);
                        fragmentTransaction.commit();

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

        searchFragment fragment = new searchFragment();
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

        else if(tag.equals("[LAUNCH] signupFragment"))
        {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            signupFragment fragment = new signupFragment();

            fragmentTransaction.replace(R.id.frame_layout, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onFragmentInteraction(SearchItem item, LinkedList<SearchItem> items)
    {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        descFragment fragment = descFragment.newInstance(item, items);

        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteractionWithItem(SearchItem item, LinkedList<SearchItem> allOfTheList)
    {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        descFragment fragment = descFragment.newInstance(item, allOfTheList);

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

    public void notifyDatasetChanged()
    {
        searchFragment fragment = (searchFragment) fragmentManager.findFragmentByTag("StartingFragment");

        if(fragment != null)
            fragment.refresh();
    }

    @Override
    public void onLoginButtonPressed(final User info, final OnTaskDone listner)
    {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap : dataSnapshot.child("accounts").getChildren())
                {
                    User toCheckUser = snap.child("detail").getValue(User.class);

                    if(toCheckUser.equals(info))
                    {
                        listner.takeRelevantActions(true);
                        loggedInUser = toCheckUser;
                        return;
                    }
                }

                listner.takeRelevantActions(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listner.connectionError();
            }
        });
    }

    @Override
    public void onSignupButtonClicked(final User info, final OnTaskDone taskDone) {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap : dataSnapshot.child("accounts").getChildren())
                {
                    User toCheck = snap.child("detail").getValue(User.class);

                    if(toCheck.equals(info))
                    {
                        taskDone.takeRelevantActions(true);
                        return;
                    }
                }

                taskDone.takeRelevantActions(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                     taskDone.connectionError();
            }
        });
    }

    @Override
    public void makeUser(User info)
    {
        DatabaseReference accountsRef = ref.child("accounts");
        DatabaseReference newAccount = accountsRef.push();
        DatabaseReference defaultPromoReference = newAccount.child("promo").push();

        HashMap<String, String> hashOfUserInfo = new HashMap<>();
        hashOfUserInfo.put("umail", info.getUmail());
        hashOfUserInfo.put("upass", info.getUpass());
        hashOfUserInfo.put("uname", info.getUname());

        HashMap<String, String> hashOfDefaultPromo = new HashMap<>();
        hashOfDefaultPromo.put("imageSrc", "https://www.askideas.com/media/13/Welcome-Glad-Youre-Here.jpg");
        hashOfDefaultPromo.put("promoDesc", "We're really glad you're here :)");
        hashOfDefaultPromo.put("promoCode", "Welcome");

        defaultPromoReference.setValue(hashOfDefaultPromo);
        newAccount.child("detail").setValue(hashOfUserInfo);
    }

    public void onDemandedListFetched(final User user, final promosFragment.OnDataFetched dataFetched)
    {
        LinkedList<Promos> promos = new LinkedList<>();
        DatabaseReference accountsRef = ref.child("accounts");

        accountsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap : dataSnapshot.getChildren())
                {
                    User tempUser = snap.child("detail").getValue(User.class);

                    if(tempUser.equals(user))
                    {
                        DatabaseReference refOfDesiredChild = ref.child("accounts").child(snap.getKey()).child("promo");

                        refOfDesiredChild.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                LinkedList<Promos> fetchedPromos = new LinkedList<>();

                                for(DataSnapshot snap : dataSnapshot.getChildren())
                                {
                                    Promos promo = snap.getValue(Promos.class);
                                    fetchedPromos.add(promo);
                                }

                                dataFetched.callbackDataFetched(fetchedPromos);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setRangesGiven(Integer startRange, Integer endRange)
    {
        ///;
    }

    public LinkedList<Promos> onDemandedListFetched(final User user)
    {
        LinkedList<Promos> promos = new LinkedList<>();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap : dataSnapshot.getChildren())
                {
                    User tempUser = snap.getValue(User.class);

                    if(tempUser.equals(user))
                    {
                        DatabaseReference refOfDesiredChild = ref.child("accounts").child(snap.getKey()).child("promos");

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return promos;
    }
}
