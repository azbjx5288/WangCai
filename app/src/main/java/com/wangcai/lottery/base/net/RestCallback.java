package com.wangcai.lottery.base.net;

public interface RestCallback<T> {
    /**
     * 若返回false，做默认处理；若返回true，表示已经处理
     */
    boolean onRestComplete(RestRequest request, RestResponse<T> response);

    /**
     * 若返回false，做默认处理；若返回true，表示已经处理
     */
    boolean onRestError(RestRequest request, int errCode, String errDesc);

    void onRestStateChanged(RestRequest request, @RestRequest.RestState int state);
}
