package me.twhuang.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 消息
 * @Date: 2021-04-29 17:39
 * @Author: tw.huang
 * @Version: v1.0.0
 **/
@Setter
@Getter
public class BusMsg {

    private Integer code;

    private String msg;

    private Object object;
}
