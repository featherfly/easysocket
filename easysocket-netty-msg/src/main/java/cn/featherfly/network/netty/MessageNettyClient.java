
package cn.featherfly.network.netty;

import java.util.concurrent.CompletionStage;

import cn.featherfly.network.ClientReceivableEvent;
import cn.featherfly.network.MessageClient;
import cn.featherfly.network.NetworkAddress;
import cn.featherfly.network.NetworkException;
import cn.featherfly.network.NetworkExceptionCode;
import cn.featherfly.network.netty.msg.ClientMsg;
import cn.featherfly.network.netty.msg.Msg;
import cn.featherfly.network.netty.msg.ResponseMsg;
import io.netty.channel.Channel;

/**
 * <p>
 * NettyMsgClient
 * </p>
 *
 * @author zhongj
 */
public class MessageNettyClient extends NettyClient<ClientMsg, Msg> implements MessageClient {

    private MessageManager messageManager;

    /**
     * @param networkAddress
     * @param facotry
     */
    MessageNettyClient(NetworkAddress networkAddress, NettyBootstrapFacotry facotry) {
        super(networkAddress, facotry);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletionStage<ResponseMsg> sendAndReceive(ClientMsg sending) {
        if (state != State.CONNECTED) {
            throw new NetworkException(NetworkExceptionCode.createNotConnectedCode(remoteAddress));
        }
        logger.debug("send {} -> {}", sending.getClass().getName(), sending.toString());
        channel.writeAndFlush(sending);
        return messageManager.putSendMessage(sending);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void fireConnect(NettyClientConnectEvent event, Channel channel) {
        messageManager = MessageManager.getMessageManager(channel);
        super.fireConnect(event, channel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void fireReceive(ClientReceivableEvent<Msg> event) {
        if (event.getReceive().getId() != null && event.getReceive() instanceof ResponseMsg) {
            messageManager.receive((ResponseMsg) event.getReceive());
        }
        super.fireReceive(event);
    }
}
