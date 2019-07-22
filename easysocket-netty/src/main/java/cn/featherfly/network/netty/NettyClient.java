
package cn.featherfly.network.netty;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.featherfly.network.Client;
import cn.featherfly.network.ClientConnectListener;
import cn.featherfly.network.ClientDisconnectEvent;
import cn.featherfly.network.ClientDisconnectListener;
import cn.featherfly.network.ClientReceivableEvent;
import cn.featherfly.network.ClientReceiveListener;
import cn.featherfly.network.NetworkAddress;
import cn.featherfly.network.NetworkException;
import cn.featherfly.network.NetworkExceptionCode;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

/**
 * <p>
 * NettyClient
 * </p>
 * 
 * @author zhongj
 */
public class NettyClient<S, R> implements Client<S, R> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected NetworkAddress remoteAddress;

    protected NettyBootstrapFacotry facotry;

    protected ReconnectPolicy reConnectPolicy;

    protected Bootstrap bootstrap;

    protected List<ClientConnectListener<R>> connectListeners = new ArrayList<>();

    protected List<ClientDisconnectListener> disconnectListeners = new ArrayList<>();

    protected List<ClientReceiveListener<R>> receiveListeners = new ArrayList<>();

    protected CompletableFuture<cn.featherfly.network.ClientConnectEvent> connectFuture;

    protected long reconnectTimes = 0;

    protected boolean autoReconnect = true;

    protected State state = State.PREPARATION;

    protected Channel channel;

    protected boolean disconnect;

    // 每一个客户端对象单独维护自己的重连接线程
    private ScheduledExecutorService scheduledExecutorService = Executors
            .newSingleThreadScheduledExecutor();

    /**
     * @param networkAddress
     *            networkAddress
     */
    public NettyClient(NetworkAddress networkAddress,
            NettyBootstrapFacotry facotry) {
        super();
        this.remoteAddress = networkAddress;
        this.facotry = facotry;
    }

    void reconnect() {
        // 表示手动关闭，不再重连
        if (disconnect) {
            return;
        }
        // 表示不用自动重连
        if (!autoReconnect) {
            return;
        }
        // 表示连接中或者已连接成功
        if (state == State.CONNECTING || state == State.CONNECTED) {
            return;
        }
        state = State.CONNECTING;

        reconnectTimes++;
        TimeUnit delayUnit = TimeUnit.SECONDS;
        long delay = 2L;
        if (reConnectPolicy != null
                && reConnectPolicy.isReconnectable(reconnectTimes)) {
            delay = reConnectPolicy.getDelay(reconnectTimes);
        }
        logger.debug("Reconnect to server {} after {} {}", remoteAddress, delay,
                delayUnit);
        scheduledExecutorService.schedule(() -> {
            // log.error("服务端链接不上，开始重连操作...");
            System.err.println(
                    "Thread.activeCount() ... " + Thread.activeCount());
            logger.debug("start reconnecting to server {}", remoteAddress);
            connect();
        }, delay, delayUnit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized CompletionStage<cn.featherfly.network.ClientConnectEvent> connect() {
        // 如果已经发起连接或者已经连接上，则直接返回connectFuture;
        if (state == State.CONNECTING || state == State.CONNECTED) {
            return connectFuture;
        }
        state = State.CONNECTING;
        connectFuture = new CompletableFuture<>();
        new Thread(() -> {
            try {
                bootstrap = facotry.create();
                // 连接服务端
                ChannelFuture channelFuture = bootstrap.connect(
                        remoteAddress.getHost(), remoteAddress.getPort());

                channelFuture.addListener(
                        new ConnectStateListener(this, connectFuture));
                // Channel channel = f.sync().channel();
                channelFuture.channel().closeFuture().sync();

            } catch (InterruptedException e) {
                logger.debug(e.getMessage(), e);
            } finally {
                // The connection is closed automatically on shutdown.
                logger.debug("group.shutdownGracefully()");
                state = State.DISCONEECTED;
                bootstrap.config().group().shutdownGracefully();
            }
        }).start();
        return connectFuture;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletionStage<ClientDisconnectEvent> disconnect() {
        disconnect = true;
        CompletableFuture<ClientDisconnectEvent> future = new CompletableFuture<>();
        bootstrap.config().group().shutdownGracefully().addListener(f -> {
            cn.featherfly.network.netty.NettyClientDisconnectEvent event = new cn.featherfly.network.netty.NettyClientDisconnectEvent(
                    remoteAddress);
            future.complete(event);
            fireDisconnect(event);
        });
        return future;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(S sending) {
        if (state != State.CONNECTED) {
            throw new NetworkException(
                    NetworkExceptionCode.createNotConnectedCode(remoteAddress));
        }
        logger.debug("send {} -> {}", sending.getClass().getName(),
                sending.toString());
        channel.writeAndFlush(sending);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <C extends Client<S, R>> C onReceive(
            ClientReceiveListener<R> listener) {
        this.receiveListeners.add(listener);
        return (C) this;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <C extends Client<S, R>> C onConnect(
            ClientConnectListener<R> listener) {
        this.connectListeners.add(listener);
        return (C) this;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <C extends Client<S, R>> C onDisconnect(
            ClientDisconnectListener listener) {
        this.disconnectListeners.add(listener);
        return (C) this;
    }

    void fireConnect(NettyClientConnectEvent event, Channel channel) {
        state = State.CONNECTED;
        this.channel = channel;
        this.reconnectTimes = 0;
        this.connectListeners.forEach(l -> {
            l.onConnect(event);
        });
    }

    void fireDisconnect(ClientDisconnectEvent event) {
        state = State.DISCONEECTED;
        this.disconnectListeners.forEach(l -> {
            l.onDisconnect(event);
        });
    }

    void fireReceive(ClientReceivableEvent<R> event) {
        this.receiveListeners.forEach(l -> {
            l.onReceive(event);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public State getState() {
        return state;
    }

    void setState(State state) {
        this.state = state;
    }

    /**
     * 返回networkAddress
     * 
     * @return networkAddress
     */
    public NetworkAddress getRemoteAddress() {
        return remoteAddress;
    }

    /**
     * 返回autoReconnect
     * 
     * @return autoReconnect
     */
    public boolean isAutoReconnect() {
        return autoReconnect;
    }

    /**
     * 设置autoReconnect
     * 
     * @param autoReconnect
     *            autoReconnect
     */
    public void setAutoReconnect(boolean autoReconnect) {
        this.autoReconnect = autoReconnect;
    }
}
