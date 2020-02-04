package com.krisna.practice.moviecatalogue.ui.favorite;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.krisna.practice.moviecatalogue.R;
import com.krisna.practice.moviecatalogue.adapter.SectionPagerFavoriteAdapter;

public class FavoriteFragment extends Fragment {

    private FavoriteViewModel mViewModel;

    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favorite_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SectionPagerFavoriteAdapter sectionsPagerFavoriteAdapter = new SectionPagerFavoriteAdapter(getContext(), getActivity().getSupportFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.view_pager_favorite);
        viewPager.setAdapter(sectionsPagerFavoriteAdapter);
        TabLayout tabs = view.findViewById(R.id.tabs_favorite);
        tabs.setupWithViewPager(viewPager);
    }
}
