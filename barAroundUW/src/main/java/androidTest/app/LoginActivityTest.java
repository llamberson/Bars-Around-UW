package androidTest.app;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import app.bar.arounduw.LoginActivity;

/**
 *
 * There are no javadoc comments for these test methods, as their names
 * are self-explanatory.
 *
 * @author Luke Lamberson, Ankit Sabhaya
 * @version 1.0.0
 */
public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity>{
    private Solo solo;

    public LoginActivityTest(){
        super(LoginActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testDataShowsUp() {
        solo.unlockScreen();
        boolean textFound = solo.searchText("Bars Around UW");
        assertTrue("Login screen achieved", textFound);
    }

    public void testGuestLoginButton() {
        solo.clickOnButton("Log in as Guest");
        boolean buttonFound = solo.searchButton("UW Seattle Bars");
        assertTrue("Logged in as Guest", buttonFound);
    }

    public void testFBLoginButton(){
        solo.clickOnButton("Log in with Facebook");
        boolean textFound = solo.searchText("facebook");
        assertTrue("select Facebook menu option", textFound);
    }

    @Override
    public void tearDown() throws Exception {
        //tearDown() is run after a test case has finished.
        //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
        solo.finishOpenedActivities();
    }

}
