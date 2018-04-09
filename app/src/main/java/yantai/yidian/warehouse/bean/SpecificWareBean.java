package yantai.yidian.warehouse.bean;

/**
 * Created by BaiTao on 2018/4/1.
 */

public class SpecificWareBean {

    public String ware_id;//仓库编号
    public String ware_name;//仓库名称
    private boolean isSelected = false;


    public SpecificWareBean() {
    }

    public SpecificWareBean(String ware_id, String ware_name) {
        this.ware_id = ware_id;
        this.ware_name = ware_name;
    }

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
}
