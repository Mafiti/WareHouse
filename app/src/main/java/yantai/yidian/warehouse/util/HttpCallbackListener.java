package yantai.yidian.warehouse.util;

/**
 * Created by 李非 on 2018/4/3.
 */

public interface HttpCallbackListener {     //Java的回调机制接口
    int onFinish(String response);     //表示服务器成功响应请求时调用，response是服务器返回的数据
    void onError(Exception e);          //表示网络错误时调用，返回错误详细信息
}
