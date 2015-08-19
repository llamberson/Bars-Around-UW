package androidTest.app;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import app.bar.arounduw.MenuActivity;

/**
 * There are no javadoc comments for these test methods, as their names
 * are self-explanatory.
 *
 * @author Luke Lamberson, Ankit Sabhaya
 * @version 1.0.0
 */
public class MenuActivityTest extends ActivityInstrumentationTestCase2<MenuActivity> {
    private Solo solo;

    public MenuActivityTest(){
        super(MenuActivity.class);
    }


    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testSeattleBars() {
        solo.clickOnButton("UW Seattle Bars");
        solo.clickInList(0, 0);
        boolean textFound = solo.searchText("Seattle");
        assertTrue("Top of list select, Seattle Bars", textFound);
    }

    public void testTacomaBars() {
        solo.clickOnButton("UW Tacoma Bars");
        solo.clickInList(0, 0);
        boolean textFound = solo.searchText("Tacoma");
        assertTrue("Top of list select, Tacoma Bars", textFound);
    }

    public void testBothellBars() {
        solo.clickOnButton("UW Bothell Bars");
        solo.clickInList(0, 0);
        boolean textFound = solo.searchText("Bothel");
        assertTrue("Top of list select, Bothell Bars", textFound);
    }

    @Override
    public void tearDown() throws Exception {
        //tearDown() is run after a test case has finished.
        //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
        solo.finishOpenedActivities();
    }
}
