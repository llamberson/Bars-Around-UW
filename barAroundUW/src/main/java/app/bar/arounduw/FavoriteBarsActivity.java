package app.bar.arounduw;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import com.example.android.common.view.SlidingTabLayout;

import app.bar.arounduw.fragments.FavoriteListFragment;
import app.bar.arounduw.fragments.FavoriteMapFragment;


public class FavoriteBarsActivity extends ActionBarActivity {

    private ViewPager mpager;
    private SlidingTabLayout mTabs;

    Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bars);

        setUpUI();
    }


    public void setUpUI() {

        mpager = (ViewPager) findViewById(R.id.pager);
        mpager.setAdapter(new TabsAdapter(getSupportFragmentManager()));

        mTabs = (SlidingTabLayout) findViewById(R.id.tab);
        mTabs.setBackgroundColor(getResources().getColor(R.color.sky_blue));
        mTabs.setViewPager(mpager);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public class TabsAdapter extends FragmentPagerAdapter {

        String[] tabs = {"List", "MAP"};

        public TabsAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        @Override
        public Fragment getItem(int index) {

            switch (index) {
                case 0:
                    return new FavoriteListFragment();
                case 1:
                    return new FavoriteMapFragment();
            }

            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
