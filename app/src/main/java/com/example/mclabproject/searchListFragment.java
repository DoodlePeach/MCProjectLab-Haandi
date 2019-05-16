package com.example.mclabproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


public class searchListFragment extends Fragment implements priceTakerDialog.OnCorrectValuesEntered {
    private OnFragmentInteractionListener mListener;
    LinkedList<SearchItem> resList = new LinkedList<>();
    View rootView;
    ListView listView;
    SearchView searchView;
    Spinner spinner;
    LinkedList<SearchItem> pass_Preferences;
    LinkedList<SearchItem> comes_from_saad;
    searchListFragment context;
    Integer lastSelectedPos = null;
    Integer startRange = 0, endRange = 0;
    ArrayAdapter<String> spinner_adapter;

    // For All Item and Images

    ArrayList<String> dishName;
    ArrayList<String> dishResturant;
    ArrayList<String> dishPrice;
    ArrayList<String> dishDescription;
    ArrayList<String> images;
    ListViewAdapter all_adapter;

    ////////////////////////////////////

    ArrayList<String> less_dishName;
    ArrayList<String> less_dishResturant;
    ArrayList<String> less_dishPrice;
    ArrayList<String> less_dishDescription;
    ArrayList<String> less_images;
    ListViewAdapter less_adapter;
    ArrayList<Integer> positionchecker;

    RelativeLayout rl_price_limit;
    Handler handler = new Handler();
    /////////////

    int saadVariable;
    int current_list_check = 0;
    int search = 0 ; // 0 : Name 1:Resturant 2:Price

    public searchListFragment() {
        // Required empty public constructor
    }

    public static searchListFragment newInstance(LinkedList<SearchItem> itemsToPass) {
        searchListFragment fragment = new searchListFragment();
        Bundle args = new Bundle();
        args.putSerializable("passed-items", itemsToPass);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    @SuppressWarnings("unsafe")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            resList = (LinkedList<SearchItem>) bundle.getSerializable("passed-items");
        }

        context = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View v = rootView = inflater.inflate(R.layout.fragment_search_list, container, false);

        ListView lv = v.findViewById(R.id.lv_show_results);

        listView = v.findViewById(R.id.list_View);
        searchView = v.findViewById(R.id.sr);
        spinner = v.findViewById(R.id.spinner);

        String [] spinnerlist = {"Name","Resturant","Price"};
        spinner_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, spinnerlist);
        spinner.setAdapter(spinner_adapter);

        dishName = new ArrayList<String>();
        dishResturant = new ArrayList<String>();
        dishPrice = new ArrayList<String>();
        dishDescription = new ArrayList<String>();
        images = new ArrayList<String>();

        set_Initial_Data(resList);

        all_adapter = new ListViewAdapter(getContext(),images,dishName,dishResturant,dishDescription,dishPrice, handler);
        listView.setAdapter(all_adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                if(current_list_check==0) {
                    saadVariable = i;
                    pass_Preferences = new LinkedList<SearchItem>();

                    for(int j=0;j<comes_from_saad.size();j++)
                    {
                        pass_Preferences.add(comes_from_saad.get(j));
                    }

                }
                else if(current_list_check==1)
                    saadVariable = positionchecker.get(i);

                onButtonPressed(resList.get(saadVariable), pass_Preferences);



            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                if(s.equals(""))
                {
                    listView.setAdapter(all_adapter);
                    current_list_check = 0 ;
                }
                else
                {
                    // Use For Seperating those Items which Contains com.example.beginneractivity.User Given String
                    set_less_Data();
                    /////////////// Searching Criteria By Name , Resturant, Price
                    if(search==0)search_By_Dishname(s);
                    else if(search==1) search_By_dishResturant(s);

                    // Changing View using Filtered Data
                    set_less_adapter();
                }

                return false;
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                search = i ;
                lastSelectedPos = i;

                if(i==2)
                {
                    v.findViewById(R.id.cv_search_view).setVisibility(View.INVISIBLE);

                    priceTakerDialog dialog = new priceTakerDialog();
                    dialog.setTargetFragment(context, 1);
                    dialog.show(getFragmentManager(), "DialogBox");
                }
                else
                    v.findViewById(R.id.cv_search_view).setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return v;
    }

    public void onButtonPressed(String tag, Uri uri) {
        if (mListener != null) {
            mListener.onFragmentChange(tag, uri);
        }
    }

    public void onButtonPressed(SearchItem item, LinkedList<SearchItem> fullItems) {
        if (mListener != null) {
            mListener.onFragmentInteractionWithItem(item, fullItems);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCorrectValuesEntered");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentChange(String tag, Uri uri);
        void onFragmentInteractionWithItem(SearchItem item, LinkedList<SearchItem> fullItems);
    }

    public void setOnFragmentInteractionListner(OnFragmentInteractionListener listener)
    {
        this.mListener = listener;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == 1)
        {
            if (requestCode == 1)
            {
                Bundle bundle = intent.getExtras();

                startRange = bundle.getInt("startRange");
                endRange = bundle.getInt("endRange");

                set_less_Data();
                search_By_Price();
                set_less_adapter();
            }
        }

    }

    public void search_By_Dishname(String s)
    {
        s = s.toLowerCase();
        String use ;

        for(int i=0;i<dishName.size();i++)
        {
            use = dishName.get(i).toLowerCase();
            if(use.contains(s))
                Additems(i);
        }

    }

    public void search_By_dishResturant(String s)
    {
        s = s.toLowerCase();
        String use ;

        for(int i=0;i<dishResturant.size();i++)
        {
            use = dishResturant.get(i).toLowerCase();

            if(use.contains(s))
                Additems(i);

        }

    }

    public void search_By_Price()
    {

        int use = 0;

        for (int i = 0; i < dishPrice.size(); i++) {
            use = Integer.parseInt(dishPrice.get(i));
            if (use >= startRange && use <= endRange)
                Additems(i);
        }

    }

    // Adding Filtered Items one by one in Filtered list

    public void Additems(int i)
    {
        less_dishName.add(dishName.get(i));
        less_dishResturant.add(dishResturant.get(i));
        less_dishPrice.add(dishPrice.get(i));
        less_dishDescription.add(dishDescription.get(i));
        less_images.add(images.get(i));
        positionchecker.add(i);
        pass_Preferences.add(comes_from_saad.get(i));

    }

    // Initialize Your Data

    public void set_Initial_Data(LinkedList<SearchItem>list)  //       *********************************Doubt may be
    {
        comes_from_saad = new LinkedList<SearchItem>();
        for(int i=0;i<list.size();i++) {

            images.add(list.get(i).imageSrc);
            dishName.add(list.get(i).dishName);
            dishResturant.add(list.get(i).dishResturant);
            dishDescription.add(list.get(i).dishDescription);
            dishPrice.add(Integer.toString(list.get(i).dishPrice));
            comes_from_saad.add(list.get(i));

        }
    }
    public void set_less_adapter()
    {
        less_adapter = new ListViewAdapter(getContext(),less_images,less_dishName,less_dishResturant,less_dishDescription,less_dishPrice, handler);
        listView.setAdapter(less_adapter);
        // Use for Click on List and Match to the Original List Position
        current_list_check = 1 ;

    }


    public void set_less_Data()
    {
        less_dishName = new ArrayList<String>();
        less_dishResturant = new ArrayList<String>();
        less_dishPrice = new ArrayList<String>();
        less_dishDescription= new ArrayList<String>();
        less_images = new ArrayList<String>();
        positionchecker = new ArrayList<Integer>();
        pass_Preferences = new LinkedList<SearchItem>();
   }

    @Override
    public void setRangesGiven(Integer startRange, Integer endRange)
    {
        this.startRange = startRange;
        this.endRange = endRange;

        set_less_Data();
        search_By_Price();
        set_less_adapter();
    }
}
