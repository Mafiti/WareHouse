package yantai.yidian.warehouse.productIn;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/31 0031.
 */

public class LocationInfo implements Parcelable {
    private int loc_id;
    private String Location;
    private int Capacity;
    private int Inventory;
    private int RemainingSpace;

    public LocationInfo(){};

    public LocationInfo(int loc_id,String location, int capacity, int inventory, int remainingSpace){
        this.loc_id = loc_id;
        this.Location=location;
        this.Capacity=capacity;
        this.Inventory=inventory;
        this.RemainingSpace=remainingSpace;
    };

    public void setLoc_id(int loc_id) {
        this.loc_id = loc_id;
    }

    public int getLoc_id() {
        return loc_id;
    }

    public void setCapacity(int capacity) {
        Capacity = capacity;
    }

    public int getCapacity() {
        return Capacity;
    }

    public void setInventory(int inventory) {
        Inventory = inventory;
    }

    public int getInventory() {
        return Inventory;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public int getRemainingSpace() {
        return RemainingSpace;
    }

    public void setRemainingSpace(int remainingSpace) {
        RemainingSpace = remainingSpace;
    }

    public String getLocation() {
        return Location;
    }

    public int describeContents() {// 覆写的方法
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {// 覆写的方法
        parcel.writeInt(loc_id);
        parcel.writeString(Location);
        parcel.writeInt(Capacity);
        parcel.writeInt(RemainingSpace);
        parcel.writeInt(Inventory);

    }

    public static final Parcelable.Creator<LocationInfo> CREATOR = new Creator<LocationInfo>() {
        public LocationInfo createFromParcel(Parcel source) {
            LocationInfo locationInfo = new LocationInfo();
            locationInfo.loc_id = source.readInt();
            locationInfo.Location =source.readString();
            locationInfo.Capacity = source.readInt();
            locationInfo.RemainingSpace = source.readInt();
            locationInfo.Inventory = source.readInt();
            return locationInfo;
        }

        public LocationInfo[] newArray(int size) {
            return new LocationInfo[size];
        }
    };
}
