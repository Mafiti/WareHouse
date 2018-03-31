package yantai.yidian.warehouse.bean;


/**
 * Created by 李非 on 2018/3/30.
 */

public class ProductBean {
    private String box_num; //箱码
    private String product_line;    //产线
    private String classs_time;     //班次
    private Integer item_number;    //个数
    private String  product_time;   //生产批次
    private String  location;       //库位

    public String getBox_num() {
        return box_num;
    }
    public void setBox_num(String box_num) {
        this.box_num = box_num;
    }

    public String getProduct_line() {
        return product_line;
    }

    public void setProduct_line(String product_line) {
        this.product_line = product_line;
    }

    public String getClasss_time() {
        return classs_time;
    }

    public void setClasss_time(String classs_time) {
        this.classs_time = classs_time;
    }

    public Integer getItem_number() {
        return item_number;
    }

    public void setItem_number(Integer item_number) {
        this.item_number = item_number;
    }

    public String getProduct_time() {
        return product_time;
    }

    public void setProduct_time(String product_time) {
        this.product_time = product_time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
