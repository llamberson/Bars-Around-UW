package app.bar.arounduw;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.util.ArrayList;

import app.bar.arounduw.database.BarDB;
import app.bar.arounduw.model.Bar;
import app.bar.arounduw.utils.AppUtility;

public class BarDetailsActivity extends Activity {

    BarDB db;
    Typeface tf;
    Bar bar;

    SharedPreferences sharedpreferences;
    boolean logged_in_via_facebook = false;

    private CallbackManager facebookCallbackManager;
    ShareDialog barShareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_details);

        //Get intent --------------------------------
        this.bar = getIntent().getParcelableExtra("bar");

        db = new BarDB(this);
        tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/COURBD.TTF");

        //create the shared preferences for storing user login status --------------
        sharedpreferences = getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);
        logged_in_via_facebook = sharedpreferences.getBoolean("LOGGED_IN_VIA_FACEBOOK", false);

        setUpUI();
    }

    public void shareBar(View v) {

        if (ShareDialog.canShow(ShareLinkContent.class)) {

            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setContentTitle("Hello Facebook")
                    .setContentDescription("The 'Hello Facebook' sample  showcases simple Facebook integration")
                    .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=app.bar.arounduw"))
                    .build();

            //share bar image ------------------------
            /*
			Bitmap image = AppUtility.getThumbnailFromAsset(this, bar.IMAGE_NAME);
			
			SharePhoto photo = new SharePhoto.Builder()
					               .setBitmap(image)
					               .build();
			SharePhotoContent content = new SharePhotoContent.Builder()
					                        .addPhoto(photo)
					                        .build();*/

            barShareDialog.show(content);
        }

    }

    public void callBar(View v) {
        String number = "tel:" + bar.PHONE_NUMBER;
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
        startActivity(callIntent);
    }

    public void updateFavoriteBar(View v) {

        //Create favorite/not_favorite bitmaps ----------------------------------
        final Bitmap favorite = BitmapFactory.decodeResource(getResources(), R.drawable.favorite);
        final Bitmap favorite_not = BitmapFactory.decodeResource(getResources(), R.drawable.favorite_not);

        if (isFavoriteBar(bar)) {
            ((ImageView) findViewById(R.id.favorite)).setImageBitmap(favorite_not);
            db.deleteFavoriteBar(bar);
        } else {
            ((ImageView) findViewById(R.id.favorite)).setImageBitmap(favorite);
            db.insertFavoriteBar(bar);
        }
    }

    public void setUpUI() {

        //If user logged in as a guest -------------------------
        //Remove share button & favorites button
        if (!logged_in_via_facebook) {
            findViewById(R.id.share).setVisibility(View.INVISIBLE);
            findViewById(R.id.favorite).setVisibility(View.INVISIBLE);
        }

        ((TextView) findViewById(R.id.bar_name)).setText(bar.NAME);
        ((TextView) findViewById(R.id.bar_details)).setText(bar.ABOUT);
        ((TextView) findViewById(R.id.bar_address)).setText(bar.ADDRESS);
        ((ImageView) findViewById(R.id.image)).setImageBitmap(AppUtility.getLargeImageFromAsset(this, bar.IMAGE_NAME));

        //Create favorite/not_favorite bitmaps ----------------------------------
        final Bitmap favorite = BitmapFactory.decodeResource(getResources(), R.drawable.favorite);
        final Bitmap favorite_not = BitmapFactory.decodeResource(getResources(), R.drawable.favorite_not);

        //Set favorite views -----------------------
        if (isFavoriteBar(bar)) {
            ((ImageView) findViewById(R.id.favorite)).setImageBitmap(favorite);
        } else {
            ((ImageView) findViewById(R.id.favorite)).setImageBitmap(favorite_not);
        }


        //setup share dialog ----------------------------
        FacebookSdk.sdkInitialize(this);
        facebookCallbackManager = CallbackManager.Factory.create();
        barShareDialog = new ShareDialog(this);

        barShareDialog.registerCallback(facebookCallbackManager, new FacebookCallback<Sharer.Result>() {

            @Override
            public void onSuccess(Sharer.Result result) {
                // TODO Auto-generated method stub
                //AppUtility.showDialog(BarDetailsActivity.this, "Bar Shared Successfully");
            }

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub
                //AppUtility.showDialog(BarDetailsActivity.this, "Bar Share cancelled");
            }

            @Override
            public void onError(FacebookException error) {
                // TODO Auto-generated method stub
                //AppUtility.showDialog(BarDetailsActivity.this, "Oops app encountered an error");
            }
        });
    }

    //Determine if it's a favorite bar -----
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

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
