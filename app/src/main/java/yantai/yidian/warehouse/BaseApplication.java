package yantai.yidian.warehouse;

import android.app.Application;

/**
 * Created by Mondschein on 26/03/2018.
 */

public class BaseApplication extends Application {

    //用户名
    private String userName;
    //密码
    private String userPassword;
    //URL
    private String URL;
    //仓库名称
    private String warehouseName;
    //库位名称
    private String wareLocateName;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public void setWareLocateName(String wareLocateName) {
        this.wareLocateName = wareLocateName;
    }

    public String getURL() {
        return URL;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public String getWareLocateName() {
        return wareLocateName;
    }
}
