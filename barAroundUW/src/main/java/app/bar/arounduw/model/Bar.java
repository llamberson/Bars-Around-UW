package app.bar.arounduw.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Bar implements Parcelable {

	public String NAME = "";
	public String ADDRESS = "";
	public String PHONE_NUMBER = "";
	public String ABOUT = "";
	public String IMAGE_NAME = "";
	public double LONGITUDE = 0.0;
	public double LATITUDE = 0.0;
	
    public Bar () {}
    
    public Bar (Parcel in) {
        super(); 
        readFromParcel(in);
    }
    
    public void readFromParcel(Parcel parcel) {
    	NAME = parcel.readString();
    	ADDRESS = parcel.readString();
    	PHONE_NUMBER = parcel.readString();
    	ABOUT = parcel.readString();
    	IMAGE_NAME = parcel.readString();
    	LONGITUDE = parcel.readDouble();
    	LATITUDE = parcel.readDouble();
    }
    
    public static final Parcelable.Creator<Bar> CREATOR = new Parcelable.Creator<Bar>() {
        public Bar createFromParcel(Parcel parcel) {
            return new Bar (parcel);
        }
        public Bar [] newArray(int size) {
            return new Bar[size];
        }
    };
    
	@Override
	public int describeContents() {
		return 0;
	}
	
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
