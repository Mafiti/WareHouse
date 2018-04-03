package yantai.yidian.warehouse.util;

public interface WareApi {

    String METHOD_POST = "POST";
    String METHOD_GET = "GET";
    String URL_HOST = "http://127.0.0.1:8080/mes/mobile/";

    String URL_mBillInLoc = "http://10.0.2.2:8080/mes/mobile/mBillInLoc?sessionid=92D84AAD121190462E763B7D773F144C";
    String URL_LOG = "http://10.0.2.2:8080/mes/mobile/mLogin?userid=admin&password=f";

    String mBillInLoc = "mBillInLoc";

    String URL_HOST = "http://172.26.161.41:8080/mes/mobile/";

    String URL_LOGIN = URL_HOST + "mLogin";
    String URL_PARAMOBTAIN = URL_HOST +"mPara";

}
