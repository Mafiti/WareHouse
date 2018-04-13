package yantai.yidian.warehouse.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by BaiTao on 2018/4/1.
 */

public class SpecificWareBean implements Parcelable {

    public String ware_id;//仓库编号
    public String ware_name;//仓库名称
    private boolean isSelected = false;


    public SpecificWareBean() {
    }

    public SpecificWareBean(String ware_id, String ware_name) {
        this.ware_id = ware_id;
        this.ware_name = ware_name;
    }

    protected SpecificWareBean(Parcel in) {
        ware_id = in.readString();
        ware_name = in.readString();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<SpecificWareBean> CREATOR = new Creator<SpecificWareBean>() {
        @Override
        public SpecificWareBean createFromParcel(Parcel in) {
            return new SpecificWareBean(in);
        }

        @Override
        public SpecificWareBean[] newArray(int size) {
            return new SpecificWareBean[size];
        }
    };

    public String getWare_id() {
        return ware_id;
    }

    public void setWare_id(String ware_id) {
        this.ware_id = ware_id;
    }

    public String getWare_name() {
        return ware_name;
    }

    public void setWare_name(String ware_name) {
        this.ware_name = ware_name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ware_id);
        parcel.writeString(ware_name);
        parcel.writeByte((byte) (isSelected ? 1 : 0));
    }
}
