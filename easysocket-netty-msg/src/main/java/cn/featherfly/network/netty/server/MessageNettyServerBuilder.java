
package cn.featherfly.network.netty.server;

import cn.featherfly.network.serialization.MessageTypeRegister;
import io.netty.handler.logging.LogLevel;

/**
 * <p>
 * MessageNettyServerBuilder
 * </p>
 *
 * @author zhongj
 */
public class MessageNettyServerBuilder {

    private MessageNettyServerHandlerFactory handlerFactory;

    private int port;

    private MessageTypeRegister messageTypeRegister;

    private LogLevel logLevel;

    public MessageNettyServerBuilder handlerFactory(MessageNettyServerHandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
        return this;
    }

    public MessageNettyServerBuilder logLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    public MessageNettyServerBuilder messageTypeRegister(MessageTypeRegister messageTypeRegister) {
        this.messageTypeRegister = messageTypeRegister;
        return this;
    }

    public MessageNettyServerBuilder port(int port) {
        this.port = port;
        return this;
    }

    /**
     * @param port
     */
    public MessageNettyServerBuilder(int port) {
        super();
        this.port = port;
    }

    public MessageNettyServer build() {
        if (handlerFactory == null) {
            handlerFactory = new MessageNettyServerHandlerFactory();
        }

        if (logLevel == null) {
            logLevel = LogLevel.INFO;
        }

        MessageNettyServer server = new MessageNettyServer(port, handlerFactory);
        handlerFactory.setNettyServer(server);
        server.setMessageTypeRegister(messageTypeRegister);
        server.setLogLevel(logLevel);
        return server;
    }
}
