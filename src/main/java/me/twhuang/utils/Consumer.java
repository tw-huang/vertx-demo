package me.twhuang.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Description: 消费者结构
 * @Date: 2021-04-29 17:13
 * @Author: tw.huang
 * @Version: v1.0.0
 **/
@Getter
@Setter
public class Consumer {

    private String consumerName;

    private Class<?> consumerClass;

    private List<MethodHandler> handlerMethods;

}
