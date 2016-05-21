package android.dcsdealerperu.com.dealerperu.Fragment;


import android.dcsdealerperu.com.dealerperu.Adapter.TabsAdapter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.dcsdealerperu.com.dealerperu.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHomeRep extends Fragment {


    public FragmentHomeRep() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home_rep, container, false);


        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tablayout);

        TabsAdapter tabsAdapter = new TabsAdapter(getChildFragmentManager());
        tabsAdapter.addFragment(new FragmentHomeRepartidor(), "Dashboard");
        tabsAdapter.addFragment(new FragmentRuteroRepartidor(), "Rutero");

        viewPager.setAdapter(tabsAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

}
