package abhishek.redvelvet.com.callmanager.view_model.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import abhishek.redvelvet.com.callmanager.R;
import abhishek.redvelvet.com.callmanager.model.adapters.PageAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    BottomNavigationView bottomNavigationView;
    View view;
    ViewPager viewPager;
    PageAdapter adapter;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_main, container, false);

        bottomNavigationView = (BottomNavigationView)view.findViewById(R.id.navigation);
        viewPager = (ViewPager)view.findViewById(R.id.viewPager);


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        //---------------navigation view-----------------------
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {

                switch (item.getItemId()){
                    case R.id.navigation_call:{
                        viewPager.setCurrentItem(0);
                        return true;
                    }
                    case R.id.navigation_recent_call:{
                        viewPager.setCurrentItem(1  );
                        return true;
                    }
                }

                return false;
            }
        });
        //-----------------------------------------------------
        adapter = new PageAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        //-----------------------------------------------------
    }
}
