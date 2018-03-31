package yantai.yidian.warehouse.productIn;

/**
 * Created by Administrator on 2018/3/31 0031.
 */

public class LocationInfo {
    private String Location;
    private String Capacity;
    private String Inventory;
    private String RemainingSpace;

    public LocationInfo(){};

    public LocationInfo(String location, String capacity, String inventory, String remainingSpace){
        this.Location=location;
        this.Capacity=capacity;
        this.Inventory=inventory;
        this.RemainingSpace=remainingSpace;
    };


    public String getLacation() {
        return Location;
    }

    public void setLacation(String lacation) {
        Location = lacation;
    }

    public String getInventory() {
        return Inventory;
    }

    public void setInventory(String inventory) {
        Inventory = inventory;
    }

    public String getCapacity() {
        return Capacity;
    }

    public void setCapacity(String capacity) {
        Capacity = capacity;
    }

    public String getRemainingSpace() {
        return RemainingSpace;
    }

    public void setRemainingSpace(String remainingSpace) {
        RemainingSpace = remainingSpace;
    }
}
