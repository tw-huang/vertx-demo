package me.twhuang;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import me.twhuang.config.ConfigManager;
import me.twhuang.data.DataVerticle;
import me.twhuang.rest.RestVerticle;
import me.twhuang.utils.DataEventBusMsg;
import me.twhuang.utils.DataEventBusMsgCover;
import org.apache.log4j.Logger;

/**
 * @Description:
 * @Date: 2021-04-28 10:24
 * @Author: tw.huang
 * @Version: v1.0.0
 **/
public class MainVerticle extends AbstractVerticle {

    private static final Logger logger = Logger.getLogger(MainVerticle.class);
    private static JsonObject configObject = null;

    public static void main(String[] args) {
        try {
            configObject = new JsonObject();
            //直接启动的时候添加参数操作
            configObject.put("http.port", 8080);
            configObject.put("url",
                    "jdbc:mysql://106.53.155.82:3306/vertx?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=false");
            configObject.put("driver_class", "com.mysql.jdbc.Driver");
            configObject.put("username", "root");
            configObject.put("password", "");
            Vertx.vertx().deployVerticle(MainVerticle.class.getName());
        } catch (Exception e) {
            logger.error("启动服务失败!");
        }
    }

    @Override
    public void start() throws Exception {
        try {
            // 配置信息程序若使用jar包启动则需要添加配置信息
            ConfigManager.init(configObject == null ? this.config() : configObject);

            // 发布RestVerticle 代表Rest服务的Verticle
            vertx.deployVerticle(RestVerticle.class.getName());

            // 发布DataVerticle 代表数据服务的Verticle 使用工作者模式进行发布
            vertx.deployVerticle(DataVerticle.class.getName(), new DeploymentOptions().setWorker(true));

            // 设置EventBus 通信编码
            vertx.eventBus().registerDefaultCodec(DataEventBusMsg.class, new DataEventBusMsgCover());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("启动服务失败!");
        }
    }
}
