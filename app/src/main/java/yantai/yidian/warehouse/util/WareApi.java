package yantai.yidian.warehouse.util;


/**
 * Created by BaiTao on 2018/4/2.
 */

public interface WareApi {

    String METHOD_POST = "POST";
    String METHOD_GET = "GET";

    String URL = "http://10.0.2.2:8080";

    String URL_HOST = URL+"/mes/mobile/";

    String URL_LOGIN = URL_HOST + "mLogin?";
    String URL_PARAMOBTAIN = URL_HOST +"mPara?";

    String URL_mBillInLoc = URL_HOST+"mBillInLoc?";
    String URL_mScanBox = URL_HOST +"mScanBox?";
    String URL_mBillSubmit = URL_HOST +"mBillSubmit?";
    String URL_mBillPartDetail = URL_HOST + "mBillPartDetail?";

    String PARAM_userid = "userid";
    String PAREM_password = "password";

}
