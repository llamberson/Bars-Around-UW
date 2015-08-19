package app.bar.arounduw.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import app.bar.arounduw.BarDetailsActivity;
import app.bar.arounduw.R;
import app.bar.arounduw.database.BarDB;
import app.bar.arounduw.model.Bar;
import app.bar.arounduw.utils.AppUtility;

/**
 * The Class BarListAdapter represents the base adapter for the bar list.
 *
 * @author Ankit Sabhaya, Luke Lamberson
 * @version 1.0.1
 */
public class BarListAdapter extends BaseAdapter {

    /** The context. */
    private final Context context;

    /** The bars. */
    ArrayList<Bar> bars;

    /** The bar db. */
    BarDB db;

    /** The flag if logged in via facebook. */
    boolean logged_in_via_facebook;

    /** The shared preferences. */
    SharedPreferences sharedpreferences;

    /**
     * This method instantiates a new bar list adapter.
     *
     * @param context the context
     * @param bars the bars
     */
    @SuppressLint("InflateParams")
    public BarListAdapter(Context context, ArrayList<Bar> bars) {

        this.context = context;
        this.bars = bars;
        db = new BarDB(context);

        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
        logged_in_via_facebook = sharedpreferences.getBoolean("LOGGED_IN_VIA_FACEBOOK", false);
    }

    /**
     * Gets the count of the bars.
     *
     * @return the countof the bars
     */
    public int getCount() {
        return bars.size();
    }

    /**
     * Gets the item.
     *
     * @param position the position
     * @return the item
     */
    @Override
    public Object getItem(int position) {
        return null;
    }

    /**
     * Gets the item id.
     *
     * @param position the position
     * @return the item id
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * This method gets the view.
     *
     * @param position the position
     * @param convertView the convert view
     * @param parent the parent
     * @return the view
     */

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.bars_item, parent, false);
        final Bar bar = bars.get(position);

        ((TextView) rowView.findViewById(R.id.bar_name)).setText(bar.NAME);
        ((TextView) rowView.findViewById(R.id.bar_details)).setText(bar.ABOUT);
        ((ImageView) rowView.findViewById(R.id.thumbnail)).setImageBitmap(AppUtility.getThumbnailFromAsset(context, bar.IMAGE_NAME));


        //Create favorite/not_favorite bitmaps ----------------------------------
        final Bitmap favorite = BitmapFactory.decodeResource(context.getResources(), R.drawable.favorite);
        final Bitmap favorite_not = BitmapFactory.decodeResource(context.getResources(), R.drawable.favorite_not);

        //check if user logged into facebook or not, and display favorites
        if (!logged_in_via_facebook) {
            rowView.findViewById(R.id.favorite).setVisibility(View.INVISIBLE);
        } else {
            //Set favorite views -----------------------
            if (isFavoriteBar(bar)) {
                ((ImageView) rowView.findViewById(R.id.favorite)).setImageBitmap(favorite);
            } else {
                ((ImageView) rowView.findViewById(R.id.favorite)).setImageBitmap(favorite_not);
            }
        }

        (rowView.findViewById(R.id.favorite)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (isFavoriteBar(bar)) {
                    ((ImageView) rowView.findViewById(R.id.favorite)).setImageBitmap(favorite_not);
                    db.deleteFavoriteBar(bar);
                } else {
                    ((ImageView) rowView.findViewById(R.id.favorite)).setImageBitmap(favorite);
                    db.insertFavoriteBar(bar);
                }
            }
        });

        rowView.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BarDetailsActivity.class);
                intent.putExtra("bar", bar);
                context.startActivity(intent);
            }
        });

        return rowView;
    }

    //Determine if it's a favorite bar -----

    /**
     * This method checks if bar is favorite bar.
     *
     * @param bar the bar
     * @return true if bar is favorite bar
     */
    private boolean isFavoriteBar(Bar bar) {

        ArrayList<Bar> bars = db.getFavoriteBars();

        for (int i = 0; i < bars.size(); i++) {

            Bar b = bars.get(i);

            if (b.NAME.equalsIgnoreCase(bar.NAME) &&
                    b.ADDRESS.equalsIgnoreCase(bar.ADDRESS) &&
                    b.PHONE_NUMBER.equalsIgnoreCase(bar.PHONE_NUMBER) &&
                    b.LONGITUDE == bar.LONGITUDE &&
                    b.LATITUDE == bar.LATITUDE) {

                return true;
            }
        }
        return false;
    }
}