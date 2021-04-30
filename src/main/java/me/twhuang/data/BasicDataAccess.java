package me.twhuang.data;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import me.twhuang.config.ConfigManager;

/**
 * @Description: 数据访问
 * @Date: 2021-04-30 15:26
 * @Author: tw.huang
 * @Version: v1.0.0
 **/
public class BasicDataAccess {

    private static JDBCClient client = null;

    /***
     * 异步方式初始连接
     *
     * @param vertx
     * @param config
     * @return
     */
    public static void initAsync(Vertx vertx, JsonObject config, Handler<AsyncResult<Integer>> handler) {
        handler.handle(Future.succeededFuture(init(vertx, config)));
    }

    private static int init(Vertx vertx, JsonObject config) {
        if (client == null) {
            synchronized (BasicDataAccess.class) {
                if (client == null) {
                    // 读取配置加载JDBC所需配置信息
                    JsonObject configObject = ConfigManager.getInstance().getConfigObject();
                    // 根据配置信息创建JDBC连接客服端
                    JsonObject jdbcConfig = new JsonObject().put("url", configObject.getString("url"))
                            .put("driver_class", configObject.getString("driver_class"))
                            .put("user", configObject.getString("username"))
                            .put("password", configObject.getString("password"));
                    client = JDBCClient.createShared(vertx, jdbcConfig);
                }
            }
        }
        return 0;
    }

    public static JDBCClient getJdbcClient() {
        return client;
    }
}
