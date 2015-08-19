package app.bar.arounduw.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

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

/**
 * The Class AppUtility provides different utility functions for the app.
 *
 * @author Ankit Sabhaya, Luke Lamberson
 * @version 1.0.1
 */
public class AppUtility {

    /** The constant name name. */
    private static final String NAME = "name";

    /** The constant address name. */
    private static final String ADDRESS = "address";

    /** The constant phone number name. */
    private static final String PHONE_NUMBER = "phone";

    /** The constant about name. */
    private static final String ABOUT = "about";

    /** The constant image name. */
    private static final String IMAGE = "image";

    /** The constant longitude name. */
    private static final String LONGITUDE = "longitude";

    /** The constant latitude name. */
    private static final String LATITUDE = "latitude";

    /** The constant tag name. */
    private static final String TAG = "AppUtility";


    /**
     * Instantiates a new app utility.
     */
    public AppUtility() {
    }

    //Get array list of bars
    /**
     * Gets the bars from the context and the url.
     *
     * @param context the context
     * @param url the url
     * @return the bars
     */
    public static ArrayList<Bar> getBars(Context context, String url) {
        ArrayList bars = new ArrayList<>();
        new RequestTask().execute(url);
        while (bars == null) {
            //count-down hack for async
        }
        return bars;
    }
    /**
     * This method sets the bars.
     *
     * @param result the result
     * @return the array list
     */
    public static ArrayList<Bar> setBars(String result) {
        ArrayList<Bar> bars = new ArrayList<>();
        try {
            JSONArray barsArray = new JSONArray(result);

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
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bars;
    }

    /**
     * This method gets the large image from asset.
     *
     * @param context the context
     * @param imageName the image name
     * @return the large image from asset
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

    /**
     * This method gets the thumbnail from asset.
     *
     * @param context the context
     * @param imageName the image name
     * @return the thumbnail from asset
     */
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

    /**
     * This method gets the input sample size.
     *
     * @param options the options
     * @param outputWidth the output width
     * @param outputHeight the output height
     * @return the input sample size
     */
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

    /**
     * This method shows the dialog.
     *
     * @param context the context
     * @param message the message
     */
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

    /**
     * The Class RequestTask represents an
     * asynchronous task for a request.
     */
    public static class RequestTask extends AsyncTask<String, String, String> {

        /**
         * Do in background.
         *
         * @param uri the uri
         * @return the string
         */
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
                Log.d(TAG, "ClientProtocolException=" + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "IOException=" + e.getMessage());
            }
            return responseString;
        }

        /**
         * On post execute.
         *
         * @param result the result
         */
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Do anything with response..
            setBars(result);

        }
    }

}
