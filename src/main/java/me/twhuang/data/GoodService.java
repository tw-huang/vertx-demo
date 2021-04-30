package me.twhuang.data;

import io.vertx.core.eventbus.Message;
import me.twhuang.utils.DataEventBusMsg;

/**
 * ���ݷ���ӿ�
 */
public interface GoodService {

    /***
     * �������
     */
	void saveGood(final Message<Object> handler, final DataEventBusMsg message);

    /***
     * ɾ�����ݲ���
     *
     */
	void deleteGood(final Message<Object> handler, final DataEventBusMsg message);

    /***
     * ���²���
     *
     */
	void updateGood(final Message<Object> handler, final DataEventBusMsg message);

    /***
     * ��ѯ��Ʒ����
     */
	void queryGood(final Message<Object> handler, final DataEventBusMsg message);
}
