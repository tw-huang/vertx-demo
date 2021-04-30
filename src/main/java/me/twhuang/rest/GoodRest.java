package me.twhuang.rest;

import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import me.twhuang.utils.BusMsg;
import me.twhuang.utils.DataEventBusMsg;
import me.twhuang.utils.Result;
import org.apache.log4j.Logger;


/**
 * @Description: Good 接口
 * @Date: 2021-04-29 17:36
 * @Author: tw.huang
 * @Version: v1.0.0
 **/
public class GoodRest {

    private static Logger logger = Logger.getLogger(GoodRest.class);

    private Vertx vertx;

    public GoodRest(Vertx vertx) {
        this.vertx = vertx;
    }

    /***
     * 查询商品列表操作
     *
     * @param route
     */
    public void queryGood(RoutingContext route) {
        //请求数据服务操作
        DataEventBusMsg message = new DataEventBusMsg();
        message.setDataReqId("queryGood");
        this.vertx.eventBus().send("good", message, replay -> {
            if (replay.succeeded()) {
                // 取回的消息
                DataEventBusMsg replyMessage = (DataEventBusMsg) replay.result().body();
                BusMsg msg = (BusMsg) replyMessage.getDataBody();
                if (msg.getCode() == 200) {
                    Object object = msg.getObject();
                    logger.info("Accept data: " + Json.encodePrettily(object));
                    route.response().putHeader("content-type", "application/json;charset=UTF-8")
                            .end(Json.encodePrettily(Result.success(object)));
                }
            } else {
                route.response().putHeader("content-type", "application/json;charset=UTF-8")
                        .end(Json.encodePrettily(Result.failure()));
            }
        });
    }

    /**
     * 保存商品操作
     *
     * @param route
     */
    public void saveGood(RoutingContext route) {
        // 接受参数操作
        JsonObject bodyAsJson = route.getBodyAsJson();
        // 判断参数类型进行更新或者新增操作
        logger.info("保存数据:" + bodyAsJson);
//        // 通过eventbus进行数据通信操作
//        if (null == bodyAsJson || params.trim().equals("")) {
//            throw new RuntimeException("参数不能为空!");
//        }
//        JsonObject saveEntity = new JsonObject(params);
//        // 组装参数传递到EventBus上进行数据的请求操作
//        DataEventBusMsg message = new DataEventBusMsg();
//        message.setDataBody(saveEntity);
//        if (saveEntity.getString("goodId") != null && !saveEntity.getString("goodId").isEmpty()) {
//            // 更新操作
//            message.setDataReqId("saveGood");
//        } else {
//            // 保存操作
//            message.setDataReqId("updateGood");
//        }
//
//        // 发送数据请求
//        vertx.eventBus().send("good", message, replyHandler -> {
//            DataEventBusMsg rp = (DataEventBusMsg) replyHandler.result().body();
//            BusMsg busMessage = (BusMsg) rp.getDataBody();
//            if (busMessage.getCode() == 200) {
//                route.response().putHeader("content-type", "application/json;charset=UTF-8")
//                        .end(Json.encodePrettily("{'succes':'200!'}"));
//            } else {
//                route.response().putHeader("content-type", "application/json;charset=UTF-8")
//                        .end(Json.encodePrettily("{'succes':'500!'}"));
//            }
//        });

    }

    /***
     * 删除商品操作
     *
     * @param context
     */
    public void deleteGood(RoutingContext context) {

        String goodId = context.request().getParam("goodId");
        if (goodId == null || "".equals(goodId.trim())) {
            throw new RuntimeException("参数不能为空!");
        }
        DataEventBusMsg message = new DataEventBusMsg();
        message.setDataReqId("deleteGood");
        message.setDataBody(goodId);
        vertx.eventBus().send("good", message, replyHandler -> {
            DataEventBusMsg rp = (DataEventBusMsg) replyHandler.result().body();
            BusMsg busMessage = (BusMsg) rp.getDataBody();
            if (busMessage.getCode() == 200) {
                context.response().putHeader("content-type", "application/json;charset=UTF-8")
                        .end(Json.encodePrettily("{'succes':'200!'}"));
            } else {
                context.response().putHeader("content-type", "application/json;charset=UTF-8")
                        .end(Json.encodePrettily("{'succes':'500!'}"));
            }
        });

    }
}
