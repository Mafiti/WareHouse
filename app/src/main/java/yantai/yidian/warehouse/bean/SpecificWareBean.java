package yantai.yidian.warehouse.bean;

/**
 * Created by BaiTao on 2018/4/1.
 */

public class SpecificWareBean {

    public String ware_name;//仓库名称
    public String organization;//所属组织
    public String use_tag;//使用标志

    public SpecificWareBean() {
    }

    public SpecificWareBean(String ware_name, String organization, String use_tag) {
        this.ware_name = ware_name;
        this.organization = organization;
        this.use_tag = use_tag;
    }

    public void setWare_name(String ware_name) {
        this.ware_name = ware_name;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setUse_tag(String use_tag) {
        this.use_tag = use_tag;
    }

    public String getWare_name() {
        return ware_name;
    }

    public String getOrganization() {
        return organization;
    }

    public String getUse_tag() {
        return use_tag;
    }
}
