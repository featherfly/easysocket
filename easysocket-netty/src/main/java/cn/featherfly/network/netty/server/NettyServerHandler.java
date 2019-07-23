package cn.featherfly.network.netty.server;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.featherfly.network.NetworkAddress;
import cn.featherfly.network.netty.NettyClientConnectEvent;
import cn.featherfly.network.netty.NettyClientDisconnectEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyServerHandler<S, R, RES> extends SimpleChannelInboundHandler<R> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private NettyServer<S, R, RES> nettyServer;

    /**
     */
    public NettyServerHandler() {
    }

    /**
     * @param nettyServer
     */
    public NettyServerHandler(NettyServer<S, R, RES> nettyServer) {
        this.nettyServer = nettyServer;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, R msg) throws Exception {
        // 收到消息直接打印输出
        logger.debug("handler {} read remote {} send [{}]  {} ", this.getClass().getName(),
                ctx.channel().remoteAddress(), msg.getClass().getName(), msg.toString());
        NettyServerReceiveableEvent<R, RES> event = new NettyServerReceiveableEvent<>();
        event.setClientId(ctx.channel().id().asShortText());
        event.setReceive(msg);
        event.setRemoteAddress(ctx.channel().remoteAddress().toString());
        nettyServer.fireReceive(event, ctx.channel());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("RamoteAddress : {} connect to this server success !", ctx.channel().remoteAddress());

        NetworkAddress remoteAddress = new NetworkAddress();
        NettyClientConnectEvent event = new NettyClientConnectEvent(remoteAddress, true);

        if (ctx.channel().remoteAddress() instanceof InetSocketAddress) {
            InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
            remoteAddress.setPort(address.getPort());
            remoteAddress.setHost(address.getHostString());
        }

        nettyServer.fireConnect(event, ctx.channel());
        super.channelActive(ctx);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("RamoteAddress : {} disconnect to this server !", ctx.channel().remoteAddress());

        NetworkAddress remoteAddress = new NetworkAddress();
        NettyClientDisconnectEvent event = new NettyClientDisconnectEvent(remoteAddress);

        if (ctx.channel().remoteAddress() instanceof InetSocketAddress) {
            InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
            remoteAddress.setPort(address.getPort());
            remoteAddress.setHost(address.getHostString());
        }
        nettyServer.fireDisconnect(event, ctx.channel());
        super.channelInactive(ctx);
    }

    /**
     * 返回nettyServer
     *
     * @return nettyServer
     */
    public NettyServer<S, R, RES> getNettyServer() {
        return nettyServer;
    }

    /**
     * 设置nettyServer
     *
     * @param nettyServer nettyServer
     */
    public void setNettyServer(NettyServer<S, R, RES> nettyServer) {
        this.nettyServer = nettyServer;
    }
}