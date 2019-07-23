package cn.featherfly.network.netty;

import cn.featherfly.network.netty.msg.ClientMsg;
import cn.featherfly.network.netty.msg.ClientRegistMsg;
import cn.featherfly.network.netty.msg.Msg;
import io.netty.channel.ChannelHandlerContext;

public class MessageNettyClientHandler extends NettyClientHandler<MessageNettyClient, ClientMsg, Msg> {

    private ClientIdGenerator clientIdGenerator;

    /**
     * @param clientIdGenerator
     */
    public MessageNettyClientHandler(ClientIdGenerator clientIdGenerator) {
        super();
        this.clientIdGenerator = clientIdGenerator;
    }

    /**
     * @param clientIdGenerator
     * @param nettyClient
     */
    public MessageNettyClientHandler(ClientIdGenerator clientIdGenerator, MessageNettyClient nettyClient) {
        super(nettyClient);
        this.clientIdGenerator = clientIdGenerator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ClientRegistMsg clientRegistMsg = new ClientRegistMsg();
        clientRegistMsg.setClientId(clientIdGenerator.getClientId());
        ctx.writeAndFlush(clientRegistMsg);
    }
}