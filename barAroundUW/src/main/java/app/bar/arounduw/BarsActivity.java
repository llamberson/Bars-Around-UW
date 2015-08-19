package app.bar.arounduw;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.common.view.SlidingTabLayout;

import app.bar.arounduw.fragments.ListFragment;
import app.bar.arounduw.fragments.MapFragment;


public class BarsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bars);

        setUpUI();
    }


    public void setUpUI() {
        ViewPager mpager = (ViewPager) findViewById(R.id.pager);
        mpager.setAdapter(new TabsAdapter(getSupportFragmentManager()));

        SlidingTabLayout mTabs = (SlidingTabLayout) findViewById(R.id.tab);
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

        @Override
        public int getCount() {
            return 2;
        }
    }
}
