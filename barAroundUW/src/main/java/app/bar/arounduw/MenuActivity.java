package app.bar.arounduw;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;

/**
 * The Class MenuActivity represents the activity for the menu.
 *
 * @author Ankit Sabhaya, Luke Lamberson
 * @version 1.0.1
 */
public class MenuActivity extends Activity {

    /** The typeface. */
    Typeface tf;

    /** The shared preferences. */
    SharedPreferences sharedpreferences;

    /** The logged_in_via_facebook. */
    boolean logged_in_via_facebook = false;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Initiate facebook sdk
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_menu);
        tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/COURBD.TTF");

        //create the shared preferences for storing user login status --------------
        sharedpreferences = getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);
        logged_in_via_facebook = sharedpreferences.getBoolean("LOGGED_IN_VIA_FACEBOOK", false);
    }


    /**
     * Sets the facebook log out listener.
     */
    public void setFacebookLogOutListener() {
        //If user logged in via facebook -------------------------
        //Listen for logout event. -------------------------------
        if (logged_in_via_facebook) {
            AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
                @Override
                protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                    if (currentAccessToken == null) {
                        //User has logged out ---------------
                        finish();
                    }
                }
            };
        }

    }

    /**
     * Sets the up ui.
     */
    public void setUpUI() {
        ((TextView) findViewById(R.id.title)).setTypeface(tf);
        //If user logged in as a guest -------------------------
        //Remove login-button & remove favorites button
        if (!logged_in_via_facebook) {
            (findViewById(R.id.login_button)).setVisibility(View.INVISIBLE);
            (findViewById((R.id.my_favorites))).setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Open bar 1.
     *
     * @param v the view
     */
    public void openBar1(View v) {
        Intent i = new Intent(this, BarsActivity.class);
        i.putExtra("bars", "http://cssgate.insttech.washington.edu/~lukecl/Android/bar1.php");
        startActivity(i);
    }

    /**
     * Open bar 2.
     *
     * @param v the view
     */
    public void openBar2(View v) {
        Intent i = new Intent(this, BarsActivity.class);
        i.putExtra("bars", "http://cssgate.insttech.washington.edu/~lukecl/Android/bar2.php");
        startActivity(i);
    }

    /**
     * Open bar 3.
     *
     * @param v the view
     */
    public void openBar3(View v) {
        Intent i = new Intent(this, BarsActivity.class);
        i.putExtra("bars", "http://cssgate.insttech.washington.edu/~lukecl/Android/bar3.php");
        startActivity(i);
    }

    /**
     * Open favorites.
     *
     * @param v the v
     */
    public void openFavorites(View v) {
        startActivity(new Intent(this, FavoriteBarsActivity.class));
    }

    /**
     * On resume.
     */
    @Override
    protected void onResume() {
        super.onResume();
        setUpUI();
        setFacebookLogOutListener();
    }

    /**
     * On pause.
     */
    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * On destroy.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * On back pressed.
     */
    @Override
    public void onBackPressed() {
        super.onDestroy();

        if (logged_in_via_facebook) {
            this.moveTaskToBack(true);
        } else {
            finish();
        }

    }
}
