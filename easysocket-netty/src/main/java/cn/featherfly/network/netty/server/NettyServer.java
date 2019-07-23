
package cn.featherfly.network.netty.server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.featherfly.network.ClientConnectListener;
import cn.featherfly.network.ClientDisconnectEvent;
import cn.featherfly.network.ClientDisconnectListener;
import cn.featherfly.network.NetworkException;
import cn.featherfly.network.NetworkExceptionCode;
import cn.featherfly.network.Server;
import cn.featherfly.network.ServerReceivableEvent;
import cn.featherfly.network.ServerReceiveListener;
import cn.featherfly.network.netty.NettyBootstrapConfig;
import cn.featherfly.network.netty.NettyClientConnectEvent;
import cn.featherfly.network.netty.codec.SerializableDecoder;
import cn.featherfly.network.netty.codec.SerializableEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * <p>
 * MessageNettyServer
 * </p>
 *
 * @author zhongj
 */
public class NettyServer<S, R, RES> extends NettyBootstrapConfig implements Server<S, R, RES> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected int port;

    protected ServerBootstrap bootstrap;

    protected List<ClientConnectListener> connectListeners = new ArrayList<>();

    protected List<ClientDisconnectListener> disconnectListeners = new ArrayList<>();

    protected ServerReceiveListener<R, RES> receiveListener;

    protected NettyChannelsHolder channelsHolder = NettyChannelsHolder.getInstance(this);

    protected NettyServerHandlerFactory<S, R, RES> handlerFactory;

    /**
     * @param port
     * @param handlerFactory
     */
    public NettyServer(int port, NettyServerHandlerFactory<S, R, RES> handlerFactory) {
        super();
        this.port = port;
        this.handlerFactory = handlerFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        try {
            EventLoopGroup bossGroup = new NioEventLoopGroup();
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    // 请求日志
                    pipeline.addLast(new LoggingHandler(logLevel)) // 日志打印
                            .addLast("decoder", new SerializableDecoder(messageTypeRegister))
                            .addLast("encoder", new SerializableEncoder(messageTypeRegister))
                            .addLast("ping", new IdleStateHandler(60, 20, 60 * 10, TimeUnit.SECONDS))
                            .addLast("handler", handlerFactory.create()); // 客户端的逻辑

                }
            });
            // 服务器绑定端口监听
            //            ChannelFuture f = bootstrap.bind(port).sync();
            //            f.channel().closeFuture().sync();
            // 监听服务器关闭监听
            bootstrap.bind(port).sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        } finally {
            bootstrap.config().group().shutdownGracefully();
            bootstrap.config().childGroup().shutdownGracefully();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        bootstrap.config().group().shutdownGracefully();
        bootstrap.config().childGroup().shutdownGracefully();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <C extends Server<S, R, RES>> C onClientConnect(ClientConnectListener listener) {
        this.connectListeners.add(listener);
        return (C) this;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <C extends Server<S, R, RES>> C onReceive(ServerReceiveListener<R, RES> listener) {
        receiveListener = listener;
        return (C) this;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <C extends Server<S, R, RES>> C onClientDisconnect(ClientDisconnectListener listener) {
        this.disconnectListeners.add(listener);
        return (C) this;
    }

    void fireConnect(NettyClientConnectEvent event, Channel channel) {
        connectListeners.forEach(l -> {
            channelsHolder.addChannel(channel.id().asShortText(), channel);
            l.onConnect(event);
        });
    }

    void fireDisconnect(ClientDisconnectEvent event, Channel channel) {
        this.disconnectListeners.forEach(l -> {
            channelsHolder.removeChannel(channel);
            l.onDisconnect(event);
        });
    }

    void fireReceive(ServerReceivableEvent<R, RES> event, Channel channel) {
        if (receiveListener != null) {
            RES response = receiveListener.onReceive(event);
            // 如果接到消息需要转发，逻辑在这里处理
            if (response != null) {
                channel.writeAndFlush(response);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(String clientId, S sending) {
        if (!channelsHolder.pushToClient(sending, clientId)) {
            throw new NetworkException(NetworkExceptionCode.createNoRegisteredClientCode(clientId));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendAll(S sending) {
        channelsHolder.pushToAllClient(sending);
    }
}
