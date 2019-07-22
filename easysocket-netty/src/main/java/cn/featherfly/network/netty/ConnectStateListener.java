package cn.featherfly.network.netty;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.featherfly.network.NetworkException;
import cn.featherfly.network.NetworkExceptionCode;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

/**
 * 负责监听启动时连接状态，如果连接失败重新连接功能
 */
public class ConnectStateListener implements ChannelFutureListener {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private NettyClient<?, ?> nettyClient;

    private CompletableFuture<cn.featherfly.network.ClientConnectEvent> connectFuture;

    /**
     */
    public ConnectStateListener(NettyClient<?, ?> nettyClient,
            CompletableFuture<cn.featherfly.network.ClientConnectEvent> connectFuture) {
        super();
        this.nettyClient = nettyClient;
        this.connectFuture = connectFuture;
    }

    @Override
    public void operationComplete(ChannelFuture channelFuture)
            throws Exception {
        if (channelFuture.isSuccess()) {
            logger.debug("服务端[{}]链接成功...",
                    channelFuture.channel().remoteAddress());
            NettyClientConnectEvent event = new NettyClientConnectEvent(
                    nettyClient.getRemoteAddress());
            nettyClient.fireConnect(event, channelFuture.channel());
            connectFuture.complete(event);
        } else {
            logger.debug("服务端[{}]链接失败...",
                    channelFuture.channel().remoteAddress());
            connectFuture.completeExceptionally(new NetworkException(
                    NetworkExceptionCode.createConnectFailureCode(
                            nettyClient.getRemoteAddress())));
            nettyClient.reconnect();
        }
    }
}