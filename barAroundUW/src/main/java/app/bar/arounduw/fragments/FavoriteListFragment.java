package app.bar.arounduw.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import app.bar.arounduw.R;
import app.bar.arounduw.adapter.BarListAdapter;
import app.bar.arounduw.database.BarDB;
import app.bar.arounduw.model.Bar;
import app.bar.arounduw.utils.AppUtility;

/**
 * The Class FavoriteListFragment represents the fragment for the favorite list.
 *
 * @author Ankit Sabhaya, Luke Lamberson
 * @version 1.0.1
 */
public class FavoriteListFragment extends Fragment {

    /** The view. */
    View view = null;

    /** The list. */
    ListView list;

    /** The bar db. */
    BarDB db;

    /**
     * On create view.
     *
     * @param inflater the inflater
     * @param container the container
     * @param savedInstanceState the saved instance state
     * @return the view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_fragment, container, false);

        db = new BarDB(getActivity());
        list = (ListView) view.findViewById(R.id.list);

        ArrayList<Bar> bars = db.getFavoriteBars();

        if (bars.size() > 0) {
            list.setAdapter(new BarListAdapter(getActivity(), bars));
        } else {
            AppUtility.showDialog(getActivity(), "You have no favorite bars.");
        }

        return view;
    }

    /**
     * On resume.
     */
    @Override
    public void onResume() {
        super.onResume();
    }
}
