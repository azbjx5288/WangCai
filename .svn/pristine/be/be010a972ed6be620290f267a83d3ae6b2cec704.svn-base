package com.wangcai.lottery.base.net;

/**
 * Created by Alashi on 2015/12/24.
 */
public class RestResponse<T> {
    /**Json对象字符串，返回数据内容*/
    private T data;
    /**接口结果编码，200表示成功，非200表示失败。具体含义参见“附录——接口错误代码编码表”*/
    private int errno;
    /**失败提示文言，成功置零*/
    private String error;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
