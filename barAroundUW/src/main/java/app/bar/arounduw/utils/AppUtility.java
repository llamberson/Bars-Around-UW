package app.bar.arounduw.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import app.bar.arounduw.model.Bar;

public class AppUtility {

    private static final String NAME = "name";
    private static final String ADDRESS = "address";
    private static final String PHONE_NUMBER = "phone";
    private static final String ABOUT = "about";
    private static final String IMAGE = "image";
    private static final String LONGITUDE = "longitude";
    private static final String LATITUDE = "latitude";

    private static ArrayList bars;


    public AppUtility() {
    }

    //Get array list of bars
    public static ArrayList<Bar> getBars(Context context, String url) {
        bars = new ArrayList<>();
        new RequestTask().execute(url);
        while(bars == null) {

        }

        //make call to the network, and get web service data with ASYNC call


        //loads bars from local storage
        //String bars_json_data = loadBarsFromAsset(context, index);

            /*
            try {

                //adjust how you parse json object and array
                JSONArray barsArray = new JSONArray(returnValue);

                //JSONObject jsonObject = new JSONObject (bars_json_data);
                //JSONArray barsArray = jsonObject.getJSONArray(returnValue);

                for (int a = 0; a < barsArray.length(); a++) {

                    JSONObject barObject = barsArray.getJSONObject(a);

                    Bar bar = new Bar();

                    bar.NAME = barObject.getString(NAME);
                    bar.ADDRESS = barObject.getString(ADDRESS);
                    bar.PHONE_NUMBER = barObject.getString(PHONE_NUMBER);
                    bar.ABOUT = barObject.getString(ABOUT);
                    bar.LONGITUDE = barObject.getDouble(LONGITUDE);
                    bar.LATITUDE = barObject.getDouble(LATITUDE);
                    bar.IMAGE_NAME = barObject.getString(IMAGE); // moved from above long/lat


                    bars.add(bar);

                    //add bars to local database in PHASE 2
                    //create helper method
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        */
        return  bars;
    }

    public static ArrayList<Bar> setBars(String result){
        ArrayList<Bar> bars = new ArrayList<Bar>();
        try {

            //adjust how you parse json object and array
            JSONArray barsArray = new JSONArray(result);

            //JSONObject jsonObject = new JSONObject (bars_json_data);
            //JSONArray barsArray = jsonObject.getJSONArray(returnValue);

            for (int a = 0; a < barsArray.length(); a++) {

                JSONObject barObject = barsArray.getJSONObject(a);

                Bar bar = new Bar();

                bar.NAME = barObject.getString(NAME);
                bar.ADDRESS = barObject.getString(ADDRESS);
                bar.PHONE_NUMBER = barObject.getString(PHONE_NUMBER);
                bar.ABOUT = barObject.getString(ABOUT);
                bar.LONGITUDE = barObject.getDouble(LONGITUDE);
                bar.LATITUDE = barObject.getDouble(LATITUDE);
                bar.IMAGE_NAME = barObject.getString(IMAGE); // moved from above long/lat


                bars.add(bar);

                //add bars to local database in PHASE 2
                //create helper method
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return bars;
    }


    /**
     * //Retrieve bars data from asset
     * private static String loadBarsFromAsset(Context context, String index){
     * <p/>
     * InputStream inputstream = null;
     * <p/>
     * try{
     * switch (index){
     * case 1: inputstream = context.getAssets().open("bars/bar_1.xml"); break;
     * case 2: inputstream = context.getAssets().open("bars/bar_2.xml"); break;
     * case 3: inputstream = context.getAssets().open("bars/bar_3.xml"); break;
     * }
     * <p/>
     * int size = inputstream.available();
     * byte[] buffer = new byte[size];
     * inputstream.read(buffer);
     * inputstream.close();
     * String text = new String(buffer);
     * <p/>
     * return text;
     * <p/>
     * } catch (IOException e){
     * throw new RuntimeException(e);
     * }
     * }
     */


    public static Bitmap getLargeImageFromAsset(Context context, String imageName) {

        Bitmap b = null;
        int width = 320;
        int height = 320;

        try {

            InputStream inputstream = context.getAssets().open("images/" + imageName);
            BufferedInputStream buffer = new BufferedInputStream(inputstream);

            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(buffer, null, options);
            buffer.reset();

            options.inSampleSize = getInSampleSize(options, width, height);
            options.inJustDecodeBounds = false;

            b = BitmapFactory.decodeStream(buffer, null, options);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return b;
    }

    public static Bitmap getThumbnailFromAsset(Context context, String imageName) {

        Bitmap b = null;
        int width = 60;
        int height = 60;

        try {

            InputStream inputstream = context.getAssets().open("images/" + imageName);
            BufferedInputStream buffer = new BufferedInputStream(inputstream);

            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(buffer, null, options);
            buffer.reset();

            options.inSampleSize = getInSampleSize(options, width, height);
            options.inJustDecodeBounds = false;

            b = BitmapFactory.decodeStream(buffer, null, options);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return b;
    }

    private static int getInSampleSize(BitmapFactory.Options options, int outputWidth, int outputHeight) {

        //Actual image height & width ---------
        final int actualHeight = options.outHeight;
        final int actualWidth = options.outWidth;
        int inSampleSize = 1;

        if (actualWidth > outputWidth || actualHeight > outputHeight) {

            final int height = actualHeight / 2;
            final int width = actualWidth / 2;

            while ((width / inSampleSize) > outputWidth && (height / inSampleSize) > outputHeight) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static void showDialog(Context context, String message) {
        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setMessage(message)
                .setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

    //ADD ASYNC CALL TO WEB SERVICE
    public static class RequestTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            try {
                response = httpclient.execute(new HttpGet(uri[0]));
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    responseString = out.toString();
                    out.close();

                } else {
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Do anything with response..
            setBars(result);

        }
    }

}
