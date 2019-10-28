
package cn.featherfly.network.serialization;

import java.util.HashMap;
import java.util.Map;

import cn.featherfly.network.NetworkException;
import cn.featherfly.network.NetworkExceptionCode;
import cn.featherfly.network.codec.MessageStructure;

/**
 * <p>
 * MessageTypeRegister
 * </p>
 * .
 *
 * @author zhongj
 */
public class MessageTypeRegister {

    private MessageStructure messageStructure;

    private Map<Integer, Class<?>> messageTypes = new HashMap<>();

    /**
     *
     */
    public MessageTypeRegister() {
        this(MessageStructure.TypeByteRegister);
    }

    /**
     * @param messageStructure messageStructure
     */
    public MessageTypeRegister(MessageStructure messageStructure) {
        super();
        if (messageStructure == null) {
            this.messageStructure = MessageStructure.TypeByteRegister;
        } else if (messageStructure == MessageStructure.TypeName) {
            throw new NetworkException(NetworkExceptionCode.createMessageTypeRegisterErrorCode());
        }
        this.messageStructure = messageStructure;
    }

    /**
     * 返回structure
     *
     * @return structure
     */
    public MessageStructure getMessageStructure() {
        return messageStructure;
    }

    /**
     * Register.
     *
     * @param type the type
     * @param key  the key
     * @return the message type register
     */
    public MessageTypeRegister register(Class<?> type, int key) {
        if (messageTypes.containsKey(key)) {
            throw new RuntimeException("key" + key + "已被" + messageTypes.get(key) + "注册");
        }
        messageTypes.put(key, type);
        return this;
    }

    /**
     * Register.
     *
     * @param type the type
     * @param key  the key
     * @return the message type register
     */
    public MessageTypeRegister register(Class<?> type, short key) {
        return register(type, new Integer(key));
    }

    /**
     * Register.
     *
     * @param type the type
     * @param key  the key
     * @return the message type register
     */
    public MessageTypeRegister register(Class<?> type, byte key) {
        return register(type, new Integer(key));
    }

    /**
     * Unregister.
     *
     * @param type the type
     * @return the message type register
     */
    public MessageTypeRegister unregister(Class<?> type) {
        if (messageTypes.containsValue(type)) {
            messageTypes.remove(getKey(type));
        }
        return this;
    }

    /**
     * Gets the message type.
     *
     * @param key the key
     * @return the message type
     */
    public Class<?> getMessageType(int key) {
        return messageTypes.get(key);
    }

    /**
     * Gets the message type.
     *
     * @param key the key
     * @return the message type
     */
    public Class<?> getMessageType(short key) {
        return messageTypes.get(new Integer(key));
    }

    /**
     * Gets the message type.
     *
     * @param key the key
     * @return the message type
     */
    public Class<?> getMessageType(byte key) {
        return messageTypes.get(new Integer(key));
    }

    /**
     * Gets the key.
     *
     * @param type the type
     * @return the key
     */
    public Integer getKey(Class<?> type) {
        if (messageTypes.containsValue(type)) {
            for (Map.Entry<Integer, Class<?>> entry : messageTypes.entrySet()) {
                Class<?> value = entry.getValue();
                if (value == type) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }

}
