package cn.featherfly.network.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.featherfly.network.Client.State;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

/**
 * 负责监听启动时连接状态，如果连接失败重新连接功能
 */
public class ConnectStateListener implements ChannelFutureListener {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private NettyClient<?, ?> nettyClient;

    /**
     */
    public ConnectStateListener(NettyClient<?, ?> nettyClient) {
        super();
        this.nettyClient = nettyClient;
    }

    @Override
    public void operationComplete(ChannelFuture channelFuture)
            throws Exception {
        if (channelFuture.isSuccess()) {
            logger.debug("服务端[{}]链接成功...",
                    channelFuture.channel().remoteAddress());
            NettyClientConnectEvent event = new NettyClientConnectEvent(
                    nettyClient.getRemoteAddress(), true);
            nettyClient.fireConnect(event, channelFuture.channel());
        } else {
            logger.debug("服务端[{}]链接失败...",
                    channelFuture.channel().remoteAddress());
            NettyClientConnectEvent event = new NettyClientConnectEvent(
                    nettyClient.getRemoteAddress(), false);
            nettyClient.fireConnect(event, channelFuture.channel());

            if (nettyClient.getState() == State.CONNECTING
                    || nettyClient.getState() == State.CONNECTED) {
                nettyClient.setState(State.PREPARATION);
            }
            System.out.println(nettyClient.getState());
            nettyClient.reconnect();
        }
    }
}