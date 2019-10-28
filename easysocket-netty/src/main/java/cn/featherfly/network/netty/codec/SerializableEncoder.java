
package cn.featherfly.network.netty.codec;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.featherfly.common.lang.NumberUtils;
import cn.featherfly.network.codec.MessageStructure;
import cn.featherfly.network.serialization.MessageTypeRegister;
import cn.featherfly.network.serialization.Serializer;
import cn.featherfly.network.serialization.Serializers;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/***
 * <p>
 * MsgEncoder
 * </p>
 *
 * @author zhongj
 */
public class SerializableEncoder extends MessageToByteEncoder<Object> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Serializer serializer;

    private byte serializerKey;

    private MessageTypeRegister messageTypeRegister;

    /**
     */
    public SerializableEncoder() {
        serializer = Serializers.KRYO.getSerializer();
        serializerKey = Serializers.KRYO.getKey();
    }

    /**
     * @param messageTypeRegister
     */
    public SerializableEncoder(MessageTypeRegister messageTypeRegister) {
        super();
        this.messageTypeRegister = messageTypeRegister;
        serializer = Serializers.KRYO.getSerializer();
        serializerKey = Serializers.KRYO.getKey();
    }

    /**
     * @param serializer
     * @param serializerKey
     */
    public SerializableEncoder(Serializer serializer, byte serializerKey) {
        super();
        this.serializer = serializer;
        this.serializerKey = serializerKey;
    }

    /**
     * @param serializer
     * @param serializerKey
     * @param messageTypeRegister
     */
    public SerializableEncoder(Serializer serializer, byte serializerKey, MessageTypeRegister messageTypeRegister) {
        super();
        this.serializer = serializer;
        this.serializerKey = serializerKey;
        this.messageTypeRegister = messageTypeRegister;
    }

    /**
     * 返回serializer
     *
     * @return serializer
     */
    public Serializer getSerializer() {
        return serializer;
    }

    /**
     * 设置serializer
     *
     * @param serializer serializer
     */
    public void setSerializer(Serializer serializer) {
        this.serializer = serializer;
    }

    /**
     * 返回serializerKey
     *
     * @return serializerKey
     */
    public byte getSerializerKey() {
        return serializerKey;
    }

    /**
     * 设置serializerKey
     *
     * @param serializerKey serializerKey
     */
    public void setSerializerKey(byte serializerKey) {
        this.serializerKey = serializerKey;
    }

    /**
     * 返回messageTypeRegister
     *
     * @return messageTypeRegister
     */
    public MessageTypeRegister getMessageTypeRegister() {
        return messageTypeRegister;
    }

    /**
     * 设置messageTypeRegister
     *
     * @param messageTypeRegister messageTypeRegister
     */
    public void setMessageTypeRegister(MessageTypeRegister messageTypeRegister) {
        this.messageTypeRegister = messageTypeRegister;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        if (messageTypeRegister == null) {
            encode(msg, out);
        } else {
            encodeWithMessageTypeRegister(msg, out);
        }
    }

    private void encode(Object msg, ByteBuf out) {
        logger.debug("encode {} with {} to structure {}", msg.getClass().getName(), serializer.getClass().getName(),
                MessageStructure.TypeName);
        byte[] typeBytes = msg.getClass().getName().getBytes();
        byte[] typeLengthBytes = NumberUtils.toByteArray(typeBytes.length);
        byte[] dataBytes = serializer.serialize(msg);

        byte[] body = new byte[] { serializerKey, (byte) MessageStructure.TypeName.ordinal() };
        body = ArrayUtils.addAll(body, typeLengthBytes);
        body = ArrayUtils.addAll(body, typeBytes);
        body = ArrayUtils.addAll(body, dataBytes);

        int dataLength = body.length; // 读取消息的长度
        out.writeInt(dataLength); // 先将消息长度写入，也就是消息头
        out.writeBytes(body); // 消息体中包含我们要发送的数据
    }

    private void encodeWithMessageTypeRegister(Object msg, ByteBuf out) {
        Integer key = messageTypeRegister.getKey(msg.getClass());
        if (key == null) {
            throw new RuntimeException("未注册消息类型" + msg.getClass());
        }
        logger.debug("encode {} with {} to structure {} key {} ", msg.getClass().getName(),
                serializer.getClass().getName(), messageTypeRegister.getMessageStructure(), key);

        byte[] typeBytes = null;
        switch (messageTypeRegister.getMessageStructure()) {
            case TypeByteRegister:
                typeBytes = new byte[] { (byte) key.intValue() };
                break;
            case TypeShortRegister:
                typeBytes = NumberUtils.toByteArray((short) key.intValue());
                break;
            case TypeIntRegister:
                typeBytes = NumberUtils.toByteArray(key);
                break;
            default:
                throw new RuntimeException("未实现消息结构" + messageTypeRegister.getMessageStructure() + "的反序列化");
        }

        byte[] dataBytes = serializer.serialize(msg);
        byte[] body = new byte[] { serializerKey, (byte) messageTypeRegister.getMessageStructure().ordinal() };
        body = ArrayUtils.addAll(body, typeBytes);
        body = ArrayUtils.addAll(body, dataBytes);

        int dataLength = body.length; // 读取消息的长度
        out.writeInt(dataLength); // 先将消息长度写入，也就是消息头
        out.writeBytes(body); // 消息体中包含我们要发送的数据
    }
}
