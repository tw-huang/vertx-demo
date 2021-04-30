package me.twhuang.utils;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

import java.io.*;

/**
 * @Description: 数据消息编码解码器
 * @Date: 2021-04-29 17:20
 * @Author: tw.huang
 * @Version: v1.0.0
 **/
public class DataEventBusMsgCover implements MessageCodec<DataEventBusMsg, DataEventBusMsg> {

    @Override
    public void encodeToWire(Buffer buffer, DataEventBusMsg msg) {
        final ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o;
        try {
            o = new ObjectOutputStream(b);
            o.writeObject(msg);
            o.close();
            buffer.appendBytes(b.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public DataEventBusMsg decodeFromWire(int pos, Buffer buffer) {

        final ByteArrayInputStream b = new ByteArrayInputStream(buffer.getBytes());
        ObjectInputStream o;
        DataEventBusMsg msg = null;
        try {
            o = new ObjectInputStream(b);
            msg = (DataEventBusMsg) o.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return msg;
    }

    @Override
    public DataEventBusMsg transform(DataEventBusMsg msg) {
        return msg;
    }

    @Override
    public String name() {
        return "DataMessage";
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }

}
