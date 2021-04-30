package me.twhuang.config;

import io.vertx.core.json.JsonObject;
import org.apache.log4j.Logger;

/**
 * @Description: 读取配置管理
 * @Date: 2021-04-29 17:08
 * @Author: tw.huang
 * @Version: v1.0.0
 **/
public class ConfigManager {

    private static final Logger logger = Logger.getLogger(ConfigManager.class);

    private static ConfigManager manager = null;

    private JsonObject configObject;

    private ConfigManager(JsonObject configObject) {
        this.configObject = configObject;
    }

    public static void init(JsonObject jsonObject) {
        if (null == manager) {
            synchronized (ConfigManager.class) {
                if (null == manager) {
                    manager = new ConfigManager(jsonObject);
                    logger.info(">> read vert.x config :" + jsonObject + " info done !");
                }
            }
        }
    }

    public static ConfigManager getInstance() {
        return manager;
    }

    public JsonObject getConfigObject() {
        return configObject;
    }

}
