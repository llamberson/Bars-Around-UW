package androidTest.app.model;

import android.os.Parcel;

import junit.framework.TestCase;

import app.bar.arounduw.model.Bar;

/**
 * @author Luke Lamberson, Ankit Sabhaya
 * There are no javadoc comments for these test methods, as their names
 * are self-explanatory.
 */
public class BarTest extends TestCase{

    public void testConstructor() {
        Bar bar = new Bar();
        assertNotNull(bar);
    }

    public void testBarFields(){
        Bar bar = new Bar();

        bar.NAME = "test name";
        bar.ADDRESS="test address";
        bar.PHONE_NUMBER="test phone";
        bar.ABOUT = "test about";
        bar.IMAGE_NAME="test img";
        bar.LONGITUDE = 0.0;
        bar.LATITUDE = 0.0;

        assertEquals(bar.NAME, "test name");
        assertEquals(bar.ADDRESS, "test address");
        assertEquals(bar.PHONE_NUMBER, "test phone");
        assertEquals(bar.ABOUT, "test about");
        assertEquals(bar.IMAGE_NAME, "test img");
        assertEquals(bar.LONGITUDE, 0.0);
        assertEquals(bar.LATITUDE, 0.0);
    }

    public void testEmptyParcel(){
        Parcel parcel = Parcel.obtain();
        assertNotNull(parcel);
    }
}
