
package cn.featherfly.network.netty.server;

import java.util.Map;
import java.util.concurrent.CompletionStage;

import cn.featherfly.common.exception.UnsupportedException;
import cn.featherfly.network.MessageServer;
import cn.featherfly.network.ServerReceivableEvent;
import cn.featherfly.network.netty.MessageManager;
import cn.featherfly.network.netty.NettyClientConnectEvent;
import cn.featherfly.network.netty.msg.BasicMsg;
import cn.featherfly.network.netty.msg.ClientRegistMsg;
import cn.featherfly.network.netty.msg.Msg;
import cn.featherfly.network.netty.msg.ResponseMsg;
import cn.featherfly.network.netty.msg.ServerToClientMessage;
import io.netty.channel.Channel;

/**
 * <p>
 * MessageNettyServer
 * </p>
 *
 * @author zhongj
 */
public class MessageNettyServer extends NettyServer<Msg, Msg, ResponseMsg> implements MessageServer {

    /**
     * @param port
     * @param handlerFactory
     */
    public MessageNettyServer(int port, MessageNettyServerHandlerFactory handlerFactory) {
        super(port, handlerFactory);
    }

    @Override
    void fireConnect(NettyClientConnectEvent event, Channel channel) {
        connectListeners.forEach(l -> {
            l.onConnect(event);
        });
    }

    @Override
    void fireReceive(ServerReceivableEvent<Msg, ResponseMsg> evt, Channel channel) {
        NettyServerReceiveableEvent<Msg, ResponseMsg> event = (NettyServerReceiveableEvent<Msg, ResponseMsg>) evt;
        if (event != null && event.getReceive() != null) {
            if (event.getReceive().getId() != null && event.getReceive() instanceof ResponseMsg) {
                MessageManager.getMessageManager(channel).receive((ResponseMsg) event.getReceive());
            }
            if (event.getReceive() instanceof ClientRegistMsg) {
                ClientRegistMsg clientRegistMsg = (ClientRegistMsg) event.getReceive();
                Channel previousChannel = channelsHolder.addChannel(clientRegistMsg.getClientId(), channel);
                if (previousChannel != null) {
                    String message = String.format(
                            "clientId[%s] registed channel[%s] again, close the previous channel[%s]",
                            clientRegistMsg.getClientId(), channel.remoteAddress().toString(),
                            previousChannel.remoteAddress().toString());
                    BasicMsg basicMsg = new BasicMsg();
                    basicMsg.setMessage(message);
                    try {
                        previousChannel.writeAndFlush(basicMsg).sync();
                    } catch (InterruptedException e) {
                        logger.error(e.getLocalizedMessage(), e);
                    }
                    previousChannel.close();
                    MessageManager.removeMessageManager(previousChannel);
                }
                ResponseMsg response = new ResponseMsg();
                response.setSuccess(true);
                response.setMessage("客户端注册成功");
                event.setResponse(response);
            } else if (event.getReceive() instanceof ServerToClientMessage) {
                ServerToClientMessage serverToClientMessage = (ServerToClientMessage) event.getReceive();
                sendAndReceive(serverToClientMessage.getToClientId(), event.getReceive()).whenComplete((r, t) -> {
                    // TODO 这里可以做同步等待
                });
                ResponseMsg response = new ResponseMsg();
                response.setSuccess(true);
                response.setMessage("消息已经转发");
                event.setResponse(response);
            }
        }
        super.fireReceive(event, channel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletionStage<ResponseMsg> sendAndReceive(String clientId, Msg sending) {
        Channel channel = channelsHolder.getChannel(clientId);
        return MessageManager.getMessageManager(channel).putSendMessage(sending);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletionStage<Map<String, ResponseMsg>> sendAllAndReceive(Msg sending) {
        // FIXME 未实现
        throw new UnsupportedException();
    }

}
