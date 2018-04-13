package yantai.yidian.warehouse.util;

import java.net.URL;

/**
 * Created by BaiTao on 2018/4/2.
 */

public interface WareApi {

    String METHOD_POST = "POST";
    String METHOD_GET = "GET";

    String URL_HOST = "http://10.0.2.2:8080/mes/mobile/";

    String URL_LOGIN = URL_HOST + "mLogin?";
    String URL_PARAMOBTAIN = URL_HOST +"mPara";

    String URL_mBillInLoc = "http://10.0.2.2:8080/mes/mobile/mBillInLoc?sessionid=92D84AAD121190462E763B7D773F144C";
    String URL_LOG = "http://10.0.2.2:8080/mes/mobile/mLogin?userid=admin&password=f";

    String PARAM_userid = "userid";
    String PAREM_password = "password";

    String URL_mBillPurchaseDetail = URL_HOST + "mBillPurchaseDetail?";

}
