package me.twhuang.data;

import io.vertx.core.eventbus.Message;
import me.twhuang.utils.DataEventBusMsg;

/**
 * 数据服务接口
 */
public interface GoodService {

    /***
     * 保存操作
     */
	void saveGood(final Message<Object> handler, final DataEventBusMsg message);

    /***
     * 删除数据操作
     *
     */
	void deleteGood(final Message<Object> handler, final DataEventBusMsg message);

    /***
     * 更新操作
     *
     */
	void updateGood(final Message<Object> handler, final DataEventBusMsg message);

    /***
     * 查询商品操作
     */
	void queryGood(final Message<Object> handler, final DataEventBusMsg message);
}
