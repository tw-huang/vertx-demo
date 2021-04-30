package me.twhuang.data;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import me.twhuang.utils.*;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @Description: TODO
 * @Date: 2021-04-30 15:26
 * @Author: tw.huang
 * @Version: v1.0.0
 **/
public class DataVerticle extends AbstractVerticle {

    private static Logger logger = Logger.getLogger(DataVerticle.class);

    /**
     * 初始化数据库JDBC连接池,注册数据消费端,接受数据,产生响应
     */
    @Override
    public void start(Future<Void> startFuture) throws Exception {

        // 初始化数据连接对象
        BasicDataAccess.initAsync(vertx, config(), rep -> {
            if (rep.succeeded()) {
                logger.info(" >> init jdbc connection succeeded!");
            }
        });


        // 注册消费服务端,扫描系统下的所有DataHandler,进行注册操作
        List<Consumer> consumers = ConsumerRegister
                .register(config().getString("dataHandlerPackage", "me.twhuang.data"));
        if (consumers.size() > 0) {
            for (Consumer consumer : consumers) {
                vertx.eventBus().consumer(consumer.getConsumerName(), res -> {
                    DataEventBusMsg message = (DataEventBusMsg) res.body();
                    String key = message.getDataReqId();
                    MethodHandler h = null;
                    for (MethodHandler handler : consumer.getHandlerMethods()) {
                        if (handler.getName().equals(key)) {
                            h = handler;
                            break;
                        }
                    }
                    // 没有找到该方法
                    if (h == null) {
                        DataEventBusMsg msg = new DataEventBusMsg();
                        msg.setDataReqId(consumer.getConsumerName());
                        msg.setDataBody(JsonObject.mapFrom(Result.failure(500, "error")));
                    } else {
                        try {
                            // 执行方法调用操作
                            h.getMethod().invoke(consumer.getConsumerClass().newInstance(),
                                    res, message);
                        } catch (Exception e) {
                            e.printStackTrace();
                            DataEventBusMsg busMsg = new DataEventBusMsg();
                            busMsg.setDataReqId(consumer.getConsumerName());
                            busMsg.setDataBody(JsonObject.mapFrom(Result.failure(500, "error")));
                        }
                    }
                });
            }
        }
    }
}
