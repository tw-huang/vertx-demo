package me.twhuang.data;

import io.vertx.core.eventbus.Message;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLConnection;
import me.twhuang.utils.BusMsg;
import me.twhuang.utils.DataEventBusMsg;
import me.twhuang.utils.DataHandler;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 * @Date: 2021-04-30 16:20
 * @Author: tw.huang
 * @Version: v1.0.0
 **/
@DataHandler(name = "good")
public class GoodServiceImpl implements GoodService {

    private static final Logger logger = Logger.getLogger(GoodServiceImpl.class);

    @DataHandler(name = "saveGood")
    @Override
    public void saveGood(Message<Object> handler, DataEventBusMsg message) {
        BasicDataAccess.getJdbcClient().getConnection(res -> {

        });
    }

    @DataHandler(name = "deleteGood")
    @Override
    public void deleteGood(Message<Object> handler, DataEventBusMsg message) {
        BasicDataAccess.getJdbcClient().getConnection(res -> {

        });
    }

    @DataHandler(name = "updateGood")
    @Override
    public void updateGood(Message<Object> handler, DataEventBusMsg message) {
        BasicDataAccess.getJdbcClient().getConnection(res -> {

        });
    }

    @DataHandler(name = "queryGood")
    @Override
    public void queryGood(Message<Object> handler, DataEventBusMsg message) {
        BasicDataAccess.getJdbcClient().getConnection(res -> {
           DataEventBusMsg replayMessage = new DataEventBusMsg();
            replayMessage.setDataReqId(message.getDataReqId());
            BusMsg msg = new BusMsg();
            if (res.succeeded()) {
                SQLConnection connection = res.result();
                connection.query("select * from good ", q -> {
                    if (q.succeeded()) {
                        ResultSet rs = q.result();
                        List<String> Column = new ArrayList<>();
                        Column.add("goodId");
                        Column.add("goodName");
                        Column.add("goodCount");
                        Column.add("goodMoney");
                        rs.setColumnNames(Column);
                        msg.setMsg("success");
                        msg.setCode(200);
                        msg.setObject(rs.getRows());
                    } else {
                        msg.setCode(500);
                        msg.setMsg("failure");
                    }
                    replayMessage.setDataBody(msg);
                    handler.reply(replayMessage);
                    connection.close();
                });
            } else {
                msg.setCode(500);
                msg.setMsg("failure");
                logger.info("查询数据失败:" + res.cause().getMessage());
                replayMessage.setDataBody(msg);
                handler.reply(replayMessage);
            }
        });
    }
}
