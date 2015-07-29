package app.bar.arounduw.fragments;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.bar.arounduw.BarDetailsActivity;
import app.bar.arounduw.R;
import app.bar.arounduw.model.Bar;
import app.bar.arounduw.utils.AppUtility;


public class MapFragment extends Fragment{

    View view = null;
    private MapView mapview;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.map_fragment, container, false);
        setUpMap(savedInstanceState);
        return view;
    }

    private void setUpMap(Bundle savedInstanceState){

        int index = getArguments().getInt("bars");
        final ArrayList<Bar> bars = AppUtility.getBars(getActivity(), index);

        //Initiate Google maps
        mapview = (MapView) view.findViewById(R.id.map);
        mapview.onCreate(savedInstanceState);
        mapview.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mapview.getMap();

        if (googleMap != null) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.getUiSettings().setCompassEnabled(true);
            googleMap.getUiSettings().setAllGesturesEnabled(true);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(bars.get(0).LATITUDE, bars.get(0).LONGITUDE), 10));

            //Set Bar markers on the map.
            for (int i=0; i<bars.size(); i++){

                MarkerOptions marker = new MarkerOptions()
                        .position(new LatLng(bars.get(i).LATITUDE, bars.get(i).LONGITUDE))
                        .title(bars.get(i).NAME);
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                marker.snippet(bars.get(i).ABOUT);

                //Add marker to google map
                googleMap.addMarker(marker);
            }

            googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener(){
                @Override
                public void onInfoWindowClick(Marker marker) {

                    String marker_name = marker.getId();//m1, m2, m3 ...m22
                    //extract number
                    int position = Integer.parseInt(marker_name.substring(1, marker_name.length()));

                    Intent intent = new Intent(getActivity(), BarDetailsActivity.class);
                    intent.putExtra("bar", bars.get(position));
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapview!=null){
            mapview.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapview!=null){
            mapview.onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapview!=null){
            mapview.onDestroy();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapview!=null){
            mapview.onLowMemory();
        }
    }
}
