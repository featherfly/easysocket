
package cn.featherfly.network.serialization;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * MessageRegister
 * </p>
 * 
 * @author zhongj
 */
public class MessageTypeRegister {

    private Map<Short, Class<?>> messageTypes = new HashMap<>();

    public MessageTypeRegister register(Class<?> type, short key) {
        if (messageTypes.containsKey(key)) {
            throw new RuntimeException(
                    "key" + key + "已被" + messageTypes.get(key) + "注册");
        }
        messageTypes.put(key, type);
        return this;
    }

    public MessageTypeRegister unregister(Class<?> type) {
        if (messageTypes.containsValue(type)) {
            messageTypes.remove(getKey(type));
        }
        return this;
    }

    public Class<?> getMessageType(Short key) {
        return messageTypes.get(key);
    }

    public Short getKey(Class<?> type) {
        if (messageTypes.containsValue(type)) {
            for (Map.Entry<Short, Class<?>> entry : messageTypes.entrySet()) {
                Class<?> value = entry.getValue();
                if (value == type) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }

}
