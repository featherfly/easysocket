package cn.featherfly.network.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class NettyClientHandler<C extends NettyClient<S, R>, S, R>
        extends SimpleChannelInboundHandler<R> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected C nettyClient;

    private boolean disconnected = false;

    /**
     */
    public NettyClientHandler() {
    }

    /**
     * @param nettyClient
     *            nettyClient
     */
    public NettyClientHandler(C nettyClient) {
        super();
        this.nettyClient = nettyClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, R msg)
            throws Exception {
        logger.debug("recevie {}, {}", msg.getClass().getName(),
                msg.toString());
        NettyClientReceivableEvent<R> event = new NettyClientReceivableEvent<>(
                nettyClient.getRemoteAddress(), msg);
        this.nettyClient.fireReceive(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.debug("connect to {} success", ctx.channel().remoteAddress());
        this.disconnected = false;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("connection[{}] disconnected",
                ctx.channel().remoteAddress());
        super.channelInactive(ctx);
        this.disconnected = true;
        // 使用过程中断线重连
        nettyClient.fireDisconnect(
                new NettyClientDisconnectEvent(nettyClient.getRemoteAddress()));
        nettyClient.reconnect();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                logger.debug("长期没收到服务器[{}]推送数据", ctx.channel().remoteAddress());
                // 可以选择重新连接
            } else if (event.state().equals(IdleState.WRITER_IDLE)) {
                // String s = "ping$_";
                // ctx.channel().writeAndFlush(s);
                // logger.debug("心跳发送成功!");
                logger.debug("长期没有发送数据到[{}]", ctx.channel().remoteAddress());
            } else if (event.state().equals(IdleState.ALL_IDLE)) {
                logger.debug("长期没有发送数据到[{}]也没有收到数据",
                        ctx.channel().remoteAddress());
            }
        }
    }

    /**
     * 返回disconnected
     *
     * @return disconnected
     */
    public boolean isDisconnected() {
        return disconnected;
    }

    /**
     * 返回nettyClient
     * 
     * @return nettyClient
     */
    public C getNettyClient() {
        return nettyClient;
    }

    /**
     * 设置nettyClient
     * 
     * @param nettyClient
     *            nettyClient
     */
    public void setNettyClient(C nettyClient) {
        this.nettyClient = nettyClient;
    }
}