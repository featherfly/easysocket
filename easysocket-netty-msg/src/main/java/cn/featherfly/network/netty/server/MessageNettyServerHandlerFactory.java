
package cn.featherfly.network.netty.server;

import cn.featherfly.network.netty.msg.Msg;
import cn.featherfly.network.netty.msg.ResponseMsg;

/**
 * <p>
 * MessageNettyServerHandlerFactory
 * </p>
 *
 * @author zhongj
 */
public class MessageNettyServerHandlerFactory implements NettyServerHandlerFactory<Msg, Msg, ResponseMsg> {

    private MessageNettyServer nettyServer;

    /**
     */
    public MessageNettyServerHandlerFactory() {
    }

    /**
     * @param nettyServer
     */
    public MessageNettyServerHandlerFactory(MessageNettyServer nettyServer) {
        super();
        this.nettyServer = nettyServer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NettyServerHandler<Msg, Msg, ResponseMsg> create() {
        return new MessageNettyServerHandler(nettyServer);
    }

    /**
     * 返回nettyserver
     *
     * @return nettyserver
     */
    public MessageNettyServer getNettyServer() {
        return nettyServer;
    }

    /**
     * 设置nettyserver
     *
     * @param nettyServer nettyserver
     */
    public void setNettyServer(MessageNettyServer nettyServer) {
        this.nettyServer = nettyServer;
    }
}
