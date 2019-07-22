
package cn.featherfly.network.netty;

import java.util.concurrent.TimeUnit;

import cn.featherfly.network.netty.codec.SerializableDecoder;
import cn.featherfly.network.netty.codec.SerializableEncoder;
import cn.featherfly.network.serialization.MessageTypeRegister;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
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
public class SimpleNettyBootstrapFacotry implements NettyBootstrapFacotry {

    private ChannelHandler clientHandler;

    private int connectTimeoutMillis = 10 * 1000;

    private boolean soKeepAlive = true;

    private boolean tcpNoDelay = true;

    private MessageTypeRegister messageTypeRegister;

    /**
     * @param clientHandler
     *            处理业务的handler
     */
    public SimpleNettyBootstrapFacotry(ChannelHandler clientHandler) {
        super();
        this.clientHandler = clientHandler;
    }

    /**
     * @param clientHandler
     * @param decoder
     * @param encoder
     */
    public SimpleNettyBootstrapFacotry(ChannelHandler clientHandler,
            MessageTypeRegister messageTypeRegister) {
        super();
        this.clientHandler = clientHandler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Bootstrap create() {

        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group).option(ChannelOption.SO_KEEPALIVE, soKeepAlive)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,
                        connectTimeoutMillis) // 超时时间
                .option(ChannelOption.TCP_NODELAY, tcpNoDelay)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        // 请求日志
                        pipeline.addLast(new LoggingHandler(LogLevel.INFO)) // 日志打印
                                .addLast("decoder",
                                        new SerializableDecoder(
                                                messageTypeRegister))
                                .addLast("encoder",
                                        new SerializableEncoder(
                                                messageTypeRegister))
                                .addLast("ping",
                                        new IdleStateHandler(60, 20, 60 * 10,
                                                TimeUnit.SECONDS))
                                .addLast("handler", clientHandler); // 客户端的逻辑

                    }
                });
        return b;
    }

    /**
     * 返回connectTimeoutMillis
     * 
     * @return connectTimeoutMillis
     */
    public int getConnectTimeoutMillis() {
        return connectTimeoutMillis;
    }

    /**
     * 设置connectTimeoutMillis
     * 
     * @param connectTimeoutMillis
     *            connectTimeoutMillis
     */
    public void setConnectTimeoutMillis(int connectTimeoutMillis) {
        this.connectTimeoutMillis = connectTimeoutMillis;
    }

    /**
     * 返回clientHandler
     * 
     * @return clientHandler
     */
    public ChannelHandler getClientHandler() {
        return clientHandler;
    }

    /**
     * 设置clientHandler
     * 
     * @param clientHandler
     *            clientHandler
     */
    public void setClientHandler(ChannelHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    /**
     * 返回soKeepAlive
     * 
     * @return soKeepAlive
     */
    public boolean isSoKeepAlive() {
        return soKeepAlive;
    }

    /**
     * 设置soKeepAlive
     * 
     * @param soKeepAlive
     *            soKeepAlive
     */
    public void setSoKeepAlive(boolean soKeepAlive) {
        this.soKeepAlive = soKeepAlive;
    }

    /**
     * 返回tcpNoDelay
     * 
     * @return tcpNoDelay
     */
    public boolean isTcpNoDelay() {
        return tcpNoDelay;
    }

    /**
     * 设置tcpNoDelay
     * 
     * @param tcpNoDelay
     *            tcpNoDelay
     */
    public void setTcpNoDelay(boolean tcpNoDelay) {
        this.tcpNoDelay = tcpNoDelay;
    }

    /**
     * 返回messageTypeRegister
     * 
     * @return messageTypeRegister
     */
    public MessageTypeRegister getMessageTypeRegister() {
        return messageTypeRegister;
    }

    /**
     * 设置messageTypeRegister
     * 
     * @param messageTypeRegister
     *            messageTypeRegister
     */
    public void setMessageTypeRegister(
            MessageTypeRegister messageTypeRegister) {
        this.messageTypeRegister = messageTypeRegister;
    }
}
