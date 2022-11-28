/**
 * <p>
 *
 * </p>
 *
 * @author: zj970
 * @create: 2022/11/28
 * @FileName: IDownloadListener
 */

package com.zj970.servicebestpractice.service;

/**
 * <p>
 * 用于对下载过程中的各种状态进行监听和回调
 * </p>
 * @author: zj970
 * @date: 2022/11/28
 */
public interface IDownloadListener {
    /**
     * 用于通知当前的下载进度
     * @param progress
     */
    void onProgress(int progress);

    /**
     * 用于通知下载成功事件
     */
    void onSuccess();

    /**
     * 用于通知下载失败事件
     */
    void onFailed();

    /**
     * 用于通知下载暂时事件
     */
    void onPaused();

    /**
     * 用于通知下载取消事件
     */
    void onCanceled();
}
