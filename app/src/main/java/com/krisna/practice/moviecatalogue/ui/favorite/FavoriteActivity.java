package com.krisna.practice.moviecatalogue.ui.favorite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.krisna.practice.moviecatalogue.R;
import com.krisna.practice.moviecatalogue.adapter.SectionPagerFavoriteAdapter;

public class FavoriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        SectionPagerFavoriteAdapter sectionsPagerFavoriteAdapter = new SectionPagerFavoriteAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager_favorite);
        viewPager.setAdapter(sectionsPagerFavoriteAdapter);
        TabLayout tabs = findViewById(R.id.tabs_favorite);
        tabs.setupWithViewPager(viewPager);

        setTitle(R.string.favorite_list);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
