
package cn.featherfly.network.netty.server;

import cn.featherfly.network.netty.msg.Msg;
import cn.featherfly.network.netty.msg.ResponseMsg;
import io.netty.channel.ChannelHandlerContext;

/**
 * <p>
 * MessageNettyServerHandler
 * </p>
 *
 * @author zhongj
 */
public class MessageNettyServerHandler extends NettyServerHandler<Msg, Msg, ResponseMsg> {

    /**
     *
     */
    public MessageNettyServerHandler() {
        super();
    }

    /**
     * @param nettyServer
     */
    public MessageNettyServerHandler(MessageNettyServer nettyServer) {
        super(nettyServer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
        super.channelRead0(ctx, msg);
    }

}
