package yantai.yidian.warehouse.bean;


/**
 * Created by 李非 on 2018/3/30.
 * 产品类，包含产品的一些基本属性
 */

public class ProductBean {
    private int    box_id;    //箱ID
    private String box_num; //箱码
    private int    box_type;    //箱类型
    private int product_noid;   //产品品号

    private int dev_id;    //产线
    private String classs_time;     //班次
    private Integer item_number;    //个数
    private String  batch;   //生产批次
    private String  location;       //库位

    public int getBox_id() {
        return box_id;
    }

    public int getDev_id() {
        return dev_id;
    }

    public void setDev_id(int dev_id) {
        this.dev_id = dev_id;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public void setBox_id(int box_id) {
        this.box_id = box_id;
    }

    public int getBox_type() {
        return box_type;
    }

    public void setBox_type(int box_type) {
        this.box_type = box_type;
    }

    public int getProduct_noid() {
        return product_noid;
    }

    public void setProduct_noid(int product_noid) {
        this.product_noid = product_noid;
    }

    public String getBox_num() {
        return box_num;
    }
    public void setBox_num(String box_num) {
        this.box_num = box_num;
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




    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
