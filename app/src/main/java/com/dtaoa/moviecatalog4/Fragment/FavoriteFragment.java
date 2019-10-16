package com.dtaoa.moviecatalog4.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dtaoa.moviecatalog4.Adapter.ViewPagerAdapter;
import com.dtaoa.moviecatalog4.R;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        //add fragment
        adapter.AddFragment(new FavMovieFragment(), getString(R.string.lbl_tab_movie));
        adapter.AddFragment(new FavTvFragment(), getString(R.string.lbl_tab_tv_show));

        viewPager.setAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = view.findViewById(R.id.view_pager_fav);
        setupViewPager(viewPager);
        tabLayout = view.findViewById(R.id.tab_layout_fav);
        tabLayout.setupWithViewPager(viewPager);
    }
}
