
package cn.featherfly.network.netty;

import java.util.concurrent.TimeUnit;

import cn.featherfly.network.netty.codec.SerializableDecoder;
import cn.featherfly.network.netty.codec.SerializableEncoder;
import cn.featherfly.network.serialization.MessageTypeRegister;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * <p>
 * SimpleNettyBootstrapFacotry
 * </p>
 *
 * @author zhongj
 */
public class SimpleNettyBootstrapFacotry<H extends NettyClientHandler<C, S, R>, C extends NettyClient<S, R>, S, R>
        extends NettyBootstrapConfig implements NettyBootstrapFacotry {

    private NettyClientHandlerFactory<H, C, S, R> handlerFactory;

    /**
     * @param handlerFactory 处理业务的handler创建工厂
     */
    public SimpleNettyBootstrapFacotry(NettyClientHandlerFactory<H, C, S, R> handlerFactory) {
        this(handlerFactory, null);
    }

    /**
     * @param handlerFactory      处理业务的handler创建工厂
     * @param messageTypeRegister 消息类型注册器
     */
    public SimpleNettyBootstrapFacotry(NettyClientHandlerFactory<H, C, S, R> handlerFactory,
            MessageTypeRegister messageTypeRegister) {
        super();
        this.handlerFactory = handlerFactory;
        this.messageTypeRegister = messageTypeRegister;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Bootstrap create() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group).option(ChannelOption.SO_KEEPALIVE, soKeepAlive)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeoutMillis) // 超时时间
                .option(ChannelOption.TCP_NODELAY, tcpNoDelay).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        // 请求日志
                        pipeline.addLast(new LoggingHandler(LogLevel.INFO)) // 日志打印
                                .addLast("decoder", new SerializableDecoder(messageTypeRegister))
                                .addLast("encoder", new SerializableEncoder(messageTypeRegister))
                                .addLast("ping", new IdleStateHandler(60, 20, 60 * 10, TimeUnit.SECONDS))
                                .addLast("handler", handlerFactory.create()); // 客户端的逻辑

                    }
                });
        return b;
    }

    /**
     * 返回handlerFactory
     * 
     * @return handlerFactory
     */
    public NettyClientHandlerFactory<H, C, S, R> getHandlerFactory() {
        return handlerFactory;
    }

    /**
     * 设置handlerFactory
     * 
     * @param handlerFactory handlerFactory
     */
    public void setHandlerFactory(NettyClientHandlerFactory<H, C, S, R> handlerFactory) {
        this.handlerFactory = handlerFactory;
    }
}
