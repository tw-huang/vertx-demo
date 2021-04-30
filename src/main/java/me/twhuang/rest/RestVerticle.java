package me.twhuang.rest;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import me.twhuang.config.ConfigManager;
import org.apache.log4j.Logger;

/**
 * @Description: rest 服务入口
 * @Date: 2021-04-29 17:33
 * @Author: tw.huang
 * @Version: v1.0.0
 **/
public class RestVerticle extends AbstractVerticle {

    private static Logger logger = Logger.getLogger(RestVerticle.class);


    @Override
    public void start(Future<Void> startFuture) throws Exception {
        Router router = Router.router(vertx);

        // 映射body请求体
        router.route().handler(BodyHandler.create());

        // 拦截所有请求
        router.route("/good*").handler(routingContext -> {
            // 实际上这里我们可以进行一些过滤操作,
            logger.info(routingContext.request().absoluteURI());
            routingContext.next();
        });

        // 处理静态资源，例如您的登录页
        router.route("/curd/*").handler(StaticHandler.create("web"));

        // 定义Rest接口,也可以通过注解扫描的方式进行注册操作
        router.get("/good/queryGood").handler(new GoodRest(vertx)::queryGood);
        router.post("/good/saveGood").handler(new GoodRest(vertx)::saveGood);
        router.delete("/good/deleteGood/:goodId").handler(new GoodRest(vertx)::deleteGood);

        // 启动Http服务
        // 创建HTTP服务接受请求

        // 配置信息
        JsonObject configObject = ConfigManager.getInstance().getConfigObject();
        logger.info("configObject: " + configObject.toString());
        this.vertx.createHttpServer().requestHandler(router::accept).listen(configObject.getInteger("http.port", 8080),
                res -> {
                    if (res.succeeded()) {
                        startFuture.succeeded();
                        logger.info(" >> start rest service  on port " + configObject.getInteger("http.port", 8080));
                    } else {
                        startFuture.fail(res.cause());
                        logger.error(">> start rest service failed !");
                    }
                });
    }
}
