package me.twhuang.utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 实现数据服务端消费者的注册操作
 * @Date: 2021-04-29 17:13
 * @Author: tw.huang
 * @Version: v1.0.0
 **/
public final class ConsumerRegister {

    private static final Logger logger = Logger.getLogger(ConsumerRegister.class);

    /***
     * 注册数据服务操作
     */
    public static List<Consumer> register(String packagePath) throws ClassNotFoundException, IOException {
        // 扫描所有的带有Handler的注解类
        List<Class<?>> dataHandlerClass = new PackageScanner() {
            @Override
            public boolean checkAdd(Class<?> cls) {
                // 为该处理类
                return cls.isAnnotationPresent(DataHandler.class);
            }
        }.scan(packagePath);
        List<Consumer> consumerList = new ArrayList<>();
        logger.info(" >> scan handler class start....");
        if (null != dataHandlerClass && dataHandlerClass.size() > 0) {
            for (Class<?> clzz : dataHandlerClass) {
                DataHandler handler = clzz.getAnnotation(DataHandler.class);
                if (handler != null) {
                    // 获取对应的名称
                    String consumerName = handler.name();
                    // 获取对应的方法名称
                    Method[] methods = clzz.getDeclaredMethods();
                    if (methods.length > 0) {
                        List<MethodHandler> methodHandlers = new ArrayList<>();
                        for (Method method : methods) {
                            if (method.isAnnotationPresent(DataHandler.class)) {
                                logger.info(" >> register data handler method:" + method);
                                DataHandler methodHandler = method.getDeclaredAnnotation(DataHandler.class);
                                MethodHandler methodH = new MethodHandler();
                                methodH.setName(methodHandler.name());
                                methodH.setMethod(method);
                                methodHandlers.add(methodH);
                            }
                        }

                        if (methodHandlers.size() > 0) {
                            Consumer consumer = new Consumer();
                            consumer.setConsumerName(consumerName);
                            consumer.setConsumerClass(clzz);
                            consumer.setHandlerMethods(methodHandlers);
                            consumerList.add(consumer);
                        }
                    }
                }
            }

        }
        logger.info(" >> scan handler class end....");
        return consumerList;
    }

}
