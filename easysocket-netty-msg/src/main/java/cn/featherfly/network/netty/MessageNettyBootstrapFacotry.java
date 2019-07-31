
package cn.featherfly.network.netty;

import cn.featherfly.network.netty.msg.ClientMsg;
import cn.featherfly.network.netty.msg.Msg;
import cn.featherfly.network.serialization.MessageTypeRegister;

/**
 * <p>
 * SimpleNettyBootstrapFacotry
 * </p>
 *
 * @author zhongj
 */
public class MessageNettyBootstrapFacotry
        extends SimpleNettyBootstrapFacotry<MessageNettyClientHandler, MessageNettyClient, ClientMsg, Msg> {

    /**
     * @param handlerFacotry 处理业务的handler创建工厂
     */
    public MessageNettyBootstrapFacotry(MessageNettyClientHandlerFactory handlerFacotry) {
        this(handlerFacotry, null);
    }

    /**
     * @param handlerFacotry      处理业务的handler创建工厂
     * @param messageTypeRegister 消息类型注册器
     */
    public MessageNettyBootstrapFacotry(MessageNettyClientHandlerFactory handlerFacotry,
            MessageTypeRegister messageTypeRegister) {
        super(handlerFacotry, messageTypeRegister);
    }
}
