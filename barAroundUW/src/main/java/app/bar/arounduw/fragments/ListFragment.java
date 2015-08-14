package app.bar.arounduw.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

import app.bar.arounduw.R;
import app.bar.arounduw.adapter.BarListAdapter;
import app.bar.arounduw.model.Bar;
import app.bar.arounduw.utils.AppUtility;


public class ListFragment extends Fragment{

    View view = null;
    ListView list;
    private static final String TAG = "ListFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_fragment, container, false);

        String url = getArguments().getString("bars");

        list = (ListView) view.findViewById(R.id.list);

        new SetBars(url).execute();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    public class SetBars extends AsyncTask<String, Integer, String> {

        ProgressDialog dialog = null;
        ArrayList<Bar> bars;
        String url;

        public SetBars(String url){
            this.url = url;
        }

        @SuppressLint("InflateParams")
        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(getActivity(), "", "Preparing Bars ...", true, false);
        }

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

            bars = AppUtility.setBars(responseString);

            return "";
        }

        @Override
        protected void onPostExecute(String content) {
            super.onPostExecute(content);
            dialog.dismiss();

            list.setAdapter(new BarListAdapter(getActivity(), bars));
        }
    }
}
