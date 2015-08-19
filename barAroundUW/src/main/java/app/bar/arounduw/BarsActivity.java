package app.bar.arounduw;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.common.view.SlidingTabLayout;

import app.bar.arounduw.fragments.ListFragment;
import app.bar.arounduw.fragments.MapFragment;

/**
 * The Class BarsActivity represents the activity for the bars.
 *
 * @author Ankit Sabhaya, Luke Lamberson
 * @version 1.0.1
 */
public class BarsActivity extends AppCompatActivity {


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
     * This method sets the up ui.
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
         * This method instantiates a new tabs adapter.
         *
         * @param fm the fragment manager
         */
        public TabsAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        /**
         * This gets the page title.
         *
         * @param position the position
         * @return the page title
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        /**
         * This gets the item.
         *
         * @param index the index
         * @return the item
         */
        @Override
        public Fragment getItem(int index) {

            switch (index) {
                case 0:
                    Bundle b1 = new Bundle();
                    b1.putString("bars", getIntent().getStringExtra("bars"));
                    ListFragment f1 = new ListFragment();
                    f1.setArguments(b1);
                    return f1;
                case 1:
                    Bundle b2 = new Bundle();
                    b2.putString("bars", getIntent().getStringExtra("bars"));
                    MapFragment f2 = new MapFragment();
                    f2.setArguments(b2);
                    return f2;
            }

            return null;
        }

        /**
         * This gets the count.
         *
         * @return the count
         */
        @Override
        public int getCount() {
            return 2;
        }
    }
}
