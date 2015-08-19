package app.bar.arounduw.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The Class Bar represents a bar.
 *
 * @author Ankit Sabhaya, Luke Lamberson
 * @version 1.0.1
 */
public class Bar implements Parcelable {

    /** The name. */
    public String NAME = "";

    /** The address. */
    public String ADDRESS = "";

    /** The phone number. */
    public String PHONE_NUMBER = "";

    /** The "about" details. */
    public String ABOUT = "";

    /** The image name. */
    public String IMAGE_NAME = "";

    /** The longitude. */
    public double LONGITUDE = 0.0;

    /** The latitude. */
    public double LATITUDE = 0.0;

    /**
     * Instantiates a new bar.
     */
    public Bar() {
    }

    /**
     * Instantiates a new bar.
     *
     * @param in the parcel
     */
    public Bar(Parcel in) {
        super();
        readFromParcel(in);
    }

    /**
     * Read from parcel.
     *
     * @param parcel the parcel
     */
    public void readFromParcel(Parcel parcel) {
        NAME = parcel.readString();
        ADDRESS = parcel.readString();
        PHONE_NUMBER = parcel.readString();
        ABOUT = parcel.readString();
        IMAGE_NAME = parcel.readString();
        LONGITUDE = parcel.readDouble();
        LATITUDE = parcel.readDouble();
    }

    /** The Constant bar creator. */
    public static final Parcelable.Creator<Bar> CREATOR = new Parcelable.Creator<Bar>() {
        public Bar createFromParcel(Parcel parcel) {
            return new Bar(parcel);
        }

        public Bar[] newArray(int size) {
            return new Bar[size];
        }
    };

    /**
     * Describe contents.
     *
     * @return the number of contents
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Write to parcel.
     *
     * @param parcel the parcel
     * @param flags the flags
     */

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(NAME);
        parcel.writeString(ADDRESS);
        parcel.writeString(PHONE_NUMBER);
        parcel.writeString(ABOUT);
        parcel.writeString(IMAGE_NAME);
        parcel.writeDouble(LONGITUDE);
        parcel.writeDouble(LATITUDE);
    }
}
