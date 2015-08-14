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
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import app.bar.arounduw.utils.AppUtility;

public class LoginActivity extends Activity {

    private CallbackManager facebookCallbackManager;
    Typeface tf;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Initiate facebook sdk
        FacebookSdk.sdkInitialize(getApplicationContext());
        facebookCallbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);
        tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/COURBD.TTF");

        //create the shared preferences for storing user login status --------------
        editor = getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE).edit();

        setUpUI();
    }

    public void setUpUI() {

        ((TextView) findViewById(R.id.title)).setTypeface(tf);

        ((LoginButton) findViewById(R.id.login_button)).registerCallback(facebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Navigate to menu activity ---------------
                openMenu();
            }

            @Override
            public void onCancel() {
                //do nothing.
            }

            @Override
            public void onError(FacebookException e) {
                AppUtility.showDialog(LoginActivity.this, "Oops app encountered an error");
            }
        });
    }

    public void openMenu(View v) {
        //save login status-----------
        editor.putBoolean("LOGGED_IN_VIA_FACEBOOK", false);
        editor.apply();
        //open menu activity --------------------
        startActivity(new Intent(this, MenuActivity.class));
    }

    public void openMenu() {
        //save login status-----------
        editor.putBoolean("LOGGED_IN_VIA_FACEBOOK", true);
        editor.apply();
        //open menu activity --------------------
        startActivity(new Intent(this, MenuActivity.class));
    }

    /*Method to Check if user is logged in*/
    public boolean isUserLoggedIn() {
        Profile profile = Profile.getCurrentProfile();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        return profile != null && accessToken != null;
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Check if user is logged in ---------------
        if (isUserLoggedIn()) {
            //Navigate to menu activity ---------------
            openMenu();
        }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
