package app.bar.arounduw;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;

import app.bar.arounduw.fragments.ListFragment;

public class MenuActivity extends Activity {

	Typeface tf;

	SharedPreferences sharedpreferences;
	boolean logged_in_via_facebook = false;

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


	public void setFacebookLogOutListener(){
		//If user logged in via facebook -------------------------
		//Listen for logout event. -------------------------------
		if (logged_in_via_facebook){
			AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
				@Override
				protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
					if (currentAccessToken == null){
						//User has logged out ---------------
						finish();
					}
				}
			};
		}

	}


	public void setUpUI(){
		((TextView)findViewById(R.id.title)).setTypeface(tf);
		//If user logged in as a guest -------------------------
		//Remove login-button
		if (!logged_in_via_facebook){
			((LoginButton)findViewById(R.id.login_button)).setVisibility(View.INVISIBLE);
		}
	}

	public void openBar1(View v){
		Intent i = new Intent(this, BarsActivity.class);
		i.putExtra("bars", "http://cssgate.insttech.washington.edu/~lukecl/Android/bar1.php");
		//i.putExtra("bars", 1);
		startActivity(i);
	}
	public void openBar2(View v){
		Intent i = new Intent(this, BarsActivity.class);
		i.putExtra("bars", "http://cssgate.insttech.washington.edu/~lukecl/Android/bar2.php");
		//i.putExtra("bars", 2);
		startActivity(i);
	}
	public void openBar3(View v){
		Intent i = new Intent(this, BarsActivity.class);
		i.putExtra("bars", "http://cssgate.insttech.washington.edu/~lukecl/Android/bar3.php");
		//i.putExtra("bars", 3);
		startActivity(i);
	}

	public void openFavorites(View v){
		startActivity(new Intent(this, FavoriteBarsActivity.class));
	}

	@Override
	protected void onResume() {
		super.onResume();
		setUpUI();
		setFacebookLogOutListener();
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
	public void onBackPressed() {
		super.onDestroy();

		if (logged_in_via_facebook){
			this.moveTaskToBack(true);
		}else{
			finish();
		}

	}
}
