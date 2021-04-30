package me.twhuang.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description: TODO
 * @Date: 2021-04-29 17:13
 * @Author: tw.huang
 * @Version: v1.0.0
 **/
@Getter
@Setter
public class DataEventBusMsg {

    /**
     * 数据请求ID
     */
    private String dataReqId;

    /***
     * 数据请求内容或者返回内容
     */
    private Object dataBody;

}
