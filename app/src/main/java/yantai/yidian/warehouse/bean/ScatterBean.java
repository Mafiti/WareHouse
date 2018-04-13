package yantai.yidian.warehouse.bean;

import java.io.Serializable;

/**
 * Created by BaiTao on 2018/4/10.
 */

public class ScatterBean implements Serializable {

    private String productId;//产品品号
    private String location;//存放库位
    private String num;//散件数量
    private String weight;//散件重量

    public ScatterBean(String productId, String location, String num, String weight) {
        this.productId = productId;
        this.location = location;
        this.num = num;
        this.weight = weight;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
