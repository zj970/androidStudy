/**
 * <p>
 *
 * </p>
 *
 * @author: zj970
 * @create: 2022/12/14
 * @FileName: IHttpCallbackListener
 */


package com.zj970.advanced;

/**
 * <p>
 *
 * </p>
 * @author: zj970
 * @date: 2022/12/14
 */
public interface IHttpCallbackListener {
    /**
     * 监听网络回调成功
     *
     * @param response 请求体
     */
    void onFinish(String response);

    /**
     * 监听网络回调失败
     * @param e 异常
     */
    void onError(Exception e);
}
