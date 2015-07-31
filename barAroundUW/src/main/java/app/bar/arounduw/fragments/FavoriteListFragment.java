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


public class FavoriteListFragment extends Fragment{

	View view = null;
	ListView list;
	BarDB db;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_fragment, container, false);
        
        db= new BarDB(getActivity());
		list = (ListView) view.findViewById(R.id.list);
		
		ArrayList<Bar> bars = db.getFavoriteBars();
		
		if (bars.size()>0){
			list.setAdapter(new BarListAdapter (getActivity(),  bars));
		}else{
			AppUtility.showDialog(getActivity(), "You have no favorite bars.");
		}
		
        return view;
    }
    
    @Override
    public void onResume() {
        super.onResume();		
    }
}
