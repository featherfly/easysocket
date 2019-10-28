
package cn.featherfly.network.netty.codec;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.featherfly.common.lang.ClassUtils;
import cn.featherfly.common.lang.LangUtils;
import cn.featherfly.common.lang.NumberUtils;
import cn.featherfly.network.CodecException;
import cn.featherfly.network.CodecExceptionCode;
import cn.featherfly.network.codec.MessageStructure;
import cn.featherfly.network.serialization.MessageTypeRegister;
import cn.featherfly.network.serialization.Serializer;
import cn.featherfly.network.serialization.SerializerRegister;
import cn.featherfly.network.serialization.Serializers;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/***
 * <p>
 * *MsgDecoder*
 * </p>
 * **
 *
 * @author zhongj
 */
public class SerializableDecoder extends ByteToMessageDecoder {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final int HEAD_LENGTH = Integer.BYTES;

    private SerializerRegister serializerRegister;

    private MessageTypeRegister messageTypeRegister;

    /**
    *
    */
    public SerializableDecoder() {
        this(null, null);
    }

    /**
     * @param serializerRegister
     */
    public SerializableDecoder(SerializerRegister serializerRegister) {
        this(serializerRegister, null);
    }

    /**
     * @param messageTypeRegister
     */
    public SerializableDecoder(MessageTypeRegister messageTypeRegister) {
        this(null, messageTypeRegister);
    }

    /**
     * @param serializerRegister
     * @param messageTypeRegister
     */
    public SerializableDecoder(SerializerRegister serializerRegister, MessageTypeRegister messageTypeRegister) {
        super();
        if (serializerRegister == null) {
            serializerRegister = new SerializerRegister();
            serializerRegister.register(Serializers.JSON);
            serializerRegister.register(Serializers.PROTOBUF);
            serializerRegister.register(Serializers.KRYO);
        }
        if (messageTypeRegister == null) {
            new MessageTypeRegister();
        }
        this.messageTypeRegister = messageTypeRegister;
        this.serializerRegister = serializerRegister;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 这个HEAD_LENGTH是我们用于表示头长度的字节数。
        if (in.readableBytes() < HEAD_LENGTH) {
            // 由于Encoder中我们传的是一个int类型的值，所以这里HEAD_LENGTH的值为4.
            return;
        }
        in.markReaderIndex(); // 我们标记一下当前的readIndex的位置
        int dataLength = in.readInt(); // 读取传送过来的消息的长度。ByteBuf
        // 的readInt()方法会让他的readIndex增加4
        if (dataLength < 0) { // 我们读到的消息体长度为0，这是不应该出现的情况，这里出现这情况，关闭连接。
            ctx.close();
        }

        // 读到的消息体长度如果小于我们传送过来的消息长度，则resetReaderIndex.
        if (in.readableBytes() < dataLength) {
            // 这个配合markReaderIndex使用的。把readIndex重置到mark的地方
            in.resetReaderIndex();
            return;
        }
        byte[] body = new byte[dataLength]; // 传输正常
        in.readBytes(body);
        Object o = convertToObject(body); // 将byte数据转化为我们需要的对象
        out.add(o);
    }

    private Object convertToObject(byte[] body) {
        // 读取序列化占用的一个字节
        byte serializerKey = body[0];
        byte structureKey = body[1];
        Integer sk = new Integer(structureKey);
        MessageStructure structure = LangUtils.toEnum(MessageStructure.class, sk);
        if (structure == null) {
            throw new CodecException(CodecExceptionCode.createNotRegisteredMessageStructureKeyCode(sk));
        }
        switch (structure) {
            case TypeName:
                return convertWithTypeName(body, serializerKey);
            case TypeByteRegister:
                return convertWithMessageTypeByteKeyRegister(body, serializerKey);
            case TypeShortRegister:
                return convertWithMessageTypeShortKeyRegister(body, serializerKey);
            case TypeIntRegister:
                return convertWithMessageTypeIntKeyRegister(body, serializerKey);
            default:
                throw new CodecException(
                        CodecExceptionCode.createNotImplementsMessageStructureDeserializeCode(structure.toString()));
        }
    }

    private Object convertWithTypeName(byte[] body, byte serializerKey) {
        // 读取className长度占用的4个字节
        int start = 2;
        int end = start + Integer.BYTES;
        byte[] typeLen = ArrayUtils.subarray(body, start, end);
        int typeNameLength = NumberUtils.toInt(typeLen);
        // 读取className占用的字节
        byte[] typeNameBytes = ArrayUtils.subarray(body, end, end + typeNameLength);
        String className = new String(typeNameBytes);
        // 读取对象序列化的字节
        byte[] data = ArrayUtils.subarray(body, end + typeNameLength, body.length);
        Serializer serializer = serializerRegister.getSerializer(serializerKey);
        logger.debug("decode {} with {} from structure {}", className, serializer.getClass().getName(),
                MessageStructure.TypeName);
        return serializer.deserialize(data, ClassUtils.forName(className));
    }

    private Object convertWithMessageTypeByteKeyRegister(byte[] body, byte serializerKey) {
        int start = 2;
        int end = start + Byte.BYTES;
        short typeKey = body[start];
        byte[] data = ArrayUtils.subarray(body, end, body.length);
        return convertWithMessageTypeRegister(data, serializerKey, typeKey, MessageStructure.TypeByteRegister);
    }

    private Object convertWithMessageTypeShortKeyRegister(byte[] body, byte serializerKey) {
        int start = 2;
        int end = start + Short.BYTES;
        byte[] typeBytes = ArrayUtils.subarray(body, start, end);
        short typeKey = NumberUtils.toShort(typeBytes);
        byte[] data = ArrayUtils.subarray(body, end, body.length);
        return convertWithMessageTypeRegister(data, serializerKey, typeKey, MessageStructure.TypeShortRegister);
    }

    private Object convertWithMessageTypeIntKeyRegister(byte[] body, byte serializerKey) {
        int start = 2;
        int end = start + Integer.BYTES;
        byte[] typeBytes = ArrayUtils.subarray(body, start, end);
        int typeKey = NumberUtils.toInt(typeBytes);
        byte[] data = ArrayUtils.subarray(body, end, body.length);
        return convertWithMessageTypeRegister(data, serializerKey, typeKey, MessageStructure.TypeIntRegister);
    }

    private Object convertWithMessageTypeRegister(byte[] data, byte serializerKey, int typeKey,
            MessageStructure messageStructure) {
        Class<?> type = messageTypeRegister.getMessageType(typeKey);
        if (type == null) {
            throw new CodecException(CodecExceptionCode.createNotRegisteredMessageTypeKeyCode(typeKey));
        }
        Serializer serializer = serializerRegister.getSerializer(serializerKey);
        logger.debug("decode {} with {} from structure {} key {} ", type.getName(), serializer.getClass().getName(),
                messageStructure, typeKey);
        return serializer.deserialize(data, type);
    }

}
