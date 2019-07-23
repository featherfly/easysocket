
package cn.featherfly.network.netty;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.featherfly.common.lang.UUIDGenerator;
import cn.featherfly.network.netty.msg.Msg;
import cn.featherfly.network.netty.msg.ResponseMsg;
import io.netty.channel.Channel;

/**
 * <p>
 * MessageManager
 * </p>
 *
 * @author zhongj
 */
public class MessageManager {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final Map<Channel, MessageManager> HANDLER_MESSAGE_MANAGERS = new HashMap<>();

    private final Map<String, CompletableFuture<ResponseMsg>> messages = new ConcurrentHashMap<>();

    // TODO 后续messages使用缓存存储，可以设置过期时间，用于解决发送消息异常没有反馈导致不会被消费

    private MessageManager() {

    }

    public static MessageManager getMessageManager(Channel handler) {
        MessageManager manager = HANDLER_MESSAGE_MANAGERS.get(handler);
        synchronized (HANDLER_MESSAGE_MANAGERS) {
            if (manager == null) {
                manager = new MessageManager();
                HANDLER_MESSAGE_MANAGERS.put(handler, manager);
            }
        }
        return manager;
    }

    public static MessageManager removeMessageManager(Channel handler) {
        MessageManager manager = HANDLER_MESSAGE_MANAGERS.get(handler);
        synchronized (HANDLER_MESSAGE_MANAGERS) {
            if (manager == null) {
                manager = new MessageManager();
                HANDLER_MESSAGE_MANAGERS.put(handler, manager);
            }
        }
        return manager;
    }

    public CompletionStage<ResponseMsg> putSendMessage(Msg msg) {
        if (msg == null) {
            return null;
        }
        CompletableFuture<ResponseMsg> future = new CompletableFuture<>();
        msg.setId(UUIDGenerator.generateUUID22Letters());
        messages.put(msg.getId(), future);
        return future;
    }

    public void receive(ResponseMsg msg) {
        if (msg.getId() != null) {
            CompletableFuture<ResponseMsg> future = messages.get(msg.getId());
            if (future != null) {
                future.complete(msg);
                messages.remove(msg.getId());
            }
        }
    }
}
