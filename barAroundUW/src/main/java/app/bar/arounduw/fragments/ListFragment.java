package app.bar.arounduw.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import app.bar.arounduw.R;
import app.bar.arounduw.adapter.BarListAdapter;
import app.bar.arounduw.utils.AppUtility;


public class ListFragment extends Fragment{

    View view = null;
    ListView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_fragment, container, false);

        //change getInt to getString
        String url = getArguments().getString("bars");
        //int index = getArguments().getInt("bars");

        list = (ListView) view.findViewById(R.id.list);

        list.setAdapter(new BarListAdapter (getActivity(),  AppUtility.getBars(getActivity(), url)));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
