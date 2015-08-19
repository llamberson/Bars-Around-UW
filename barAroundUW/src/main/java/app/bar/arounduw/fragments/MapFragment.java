package app.bar.arounduw.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import app.bar.arounduw.BarDetailsActivity;
import app.bar.arounduw.R;
import app.bar.arounduw.model.Bar;
import app.bar.arounduw.utils.AppUtility;

/**
 * The Class MapFragment represents the fragment for the map.
 *
 * @author Ankit Sabhaya, Luke Lamberson
 * @version 1.0.1
 */
public class MapFragment extends Fragment {

    /** The view. */
    View view = null;

    /** The map view. */
    private MapView mapview;

    /** The google map. */
    private GoogleMap googleMap;

    /** The map fragment */
    private static final String TAG = "MapFragment";

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
        view = inflater.inflate(R.layout.map_fragment, container, false);
        setUpMap(savedInstanceState);
        return view;
    }

    /**
     * Sets the map.
     *
     * @param savedInstanceState the new map
     */
    private void setUpMap(Bundle savedInstanceState) {

        String url = getArguments().getString("bars");
        //final ArrayList<Bar> bars = AppUtility.getBars(getActivity(), url);

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
            new SetBars(url).execute();
        }

    }

    /**
     * The Class SetBars represents an asynchronous task to set the bars.
     */
    public class SetBars extends AsyncTask<String, Integer, String> {

        /** The dialog. */
        ProgressDialog dialog = null;

        /** The bars. */
        ArrayList<Bar> bars;

        /** The url. */
        String url;

        /**
         * Instantiates a new sets the bars.
         *
         * @param url the url
         */
        public SetBars(String url) {
            this.url = url;
        }

        /**
         * On pre execute.
         */
        @SuppressLint("InflateParams")
        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(getActivity(), "", "Preparing Bars ...", true, false);
        }

        /**
         * Do in background.
         *
         * @param params the params
         * @return the string
         */
        @Override
        protected String doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            try {
                response = httpclient.execute(new HttpGet(url));
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    responseString = out.toString();
                    out.close();

                } else {
                    //Closes the connection.fo
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                Log.d(TAG, "ClientProtocolException=" + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "IOException=" + e.getMessage());
            }
            //return responseString;

            bars = AppUtility.setBars(responseString);

            return "";
        }

        /**
         * On post execute.
         *
         * @param content the content
         */
        @Override
        protected void onPostExecute(String content) {
            super.onPostExecute(content);
            dialog.dismiss();

            if (bars.size() > 0) {
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.getUiSettings().setCompassEnabled(true);
                googleMap.getUiSettings().setAllGesturesEnabled(true);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(bars.get(1).LATITUDE, bars.get(1).LONGITUDE), 13));

                //Set Bar markers on the map.
                for (int i = 0; i < bars.size(); i++) {

                    MarkerOptions marker = new MarkerOptions()
                            .position(new LatLng(bars.get(i).LATITUDE, bars.get(i).LONGITUDE))
                            .title(bars.get(i).NAME);
                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                    marker.snippet(bars.get(i).ABOUT);

                    //Add marker to google map
                    googleMap.addMarker(marker);
                }

                googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
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
    }


    /**
     * On resume.
     */
    @Override
    public void onResume() {
        super.onResume();
        if (mapview != null) {
            mapview.onResume();
        }
    }

    /**
     * On pause.
     */
    @Override
    public void onPause() {
        super.onPause();
        if (mapview != null) {
            mapview.onPause();
        }
    }

    /**
     * On destroy.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapview != null) {
            mapview.onDestroy();
        }
    }

    /**
     * On low memory.
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapview != null) {
            mapview.onLowMemory();
        }
    }
}
