package yantai.yidian.warehouse.util;

import java.net.URL;

/**
 * Created by BaiTao on 2018/4/2.
 */

public interface WareApi {

    String METHOD_POST = "POST";
    String METHOD_GET = "GET";

    String URL_HOST = "http://172.26.161.41:8080/mes/mobile/";

    String URL_LOGIN = URL_HOST + "mLogin";
    String URL_PARAMOBTAIN = URL_HOST +"mPara";

}
