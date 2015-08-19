package app.bar.arounduw;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import com.example.android.common.view.SlidingTabLayout;

import app.bar.arounduw.fragments.FavoriteListFragment;
import app.bar.arounduw.fragments.FavoriteMapFragment;

/**
 * The Class FavoriteBarsActivity represents the activity for the favorite bars.
 *
 * @author Ankit Sabhaya, Luke Lamberson
 * @version 1.0.1
 */
public class FavoriteBarsActivity extends ActionBarActivity {

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bars);

        setUpUI();
    }

    /**
     * This sets the up ui.
     */
    public void setUpUI() {

        ViewPager mpager = (ViewPager) findViewById(R.id.pager);
        mpager.setAdapter(new TabsAdapter(getSupportFragmentManager()));

        SlidingTabLayout mTabs = (SlidingTabLayout) findViewById(R.id.tab);
        mTabs.setBackgroundColor(getResources().getColor(R.color.sky_blue));
        mTabs.setViewPager(mpager);

    }

    /**
     * On resume.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * On pause.
     */
    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * On destroy.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    /**
     * The Class TabsAdapter represents the fragment pager adapter for the tabs.
     */
    public class TabsAdapter extends FragmentPagerAdapter {

        /** The tabs. */
        String[] tabs = {"List", "MAP"};

        /**
         * Instantiates a new tabs adapter.
         *
         * @param fm the fragment manager
         */
        public TabsAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        /**
         * Gets the page title.
         *
         * @param position the position
         * @return the page title
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        /**
         * Gets the item.
         *
         * @param index the index
         * @return the item
         */
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

        /**
         * Gets the count.
         *
         * @return the count
         */
        @Override
        public int getCount() {
            return 2;
        }
    }
}
