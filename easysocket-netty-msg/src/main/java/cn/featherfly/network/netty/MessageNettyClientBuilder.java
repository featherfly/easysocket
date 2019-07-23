
package cn.featherfly.network.netty;

import cn.featherfly.network.NetworkAddress;
import cn.featherfly.network.serialization.MessageTypeRegister;

/**
 * <p>
 * NettyClientBuilder
 * </p>
 *
 * @author zhongj
 */
public class MessageNettyClientBuilder {

    private MessageNettyClientHandlerFactory handlerFactory;

    private MessageNettyBootstrapFacotry bootstrapFactory;

    private NetworkAddress networkAddress;

    private MessageTypeRegister messageTypeRegister;

    /**
     * @param networkAddress
     */
    public MessageNettyClientBuilder(NetworkAddress networkAddress) {
        super();
        this.networkAddress = networkAddress;
    }

    /**
     * 设置handler
     *
     * @param handler handler
     */
    public MessageNettyClientBuilder handler(MessageNettyClientHandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
        return this;
    }

    public MessageNettyClientBuilder messageTypeRegister(MessageTypeRegister messageTypeRegister) {
        this.messageTypeRegister = messageTypeRegister;
        return this;
    }

    /**
     * 设置facotry
     *
     * @param facotry facotry
     */
    public MessageNettyClientBuilder bootstrap(MessageNettyBootstrapFacotry bootstrapFactory) {
        this.bootstrapFactory = bootstrapFactory;
        return this;
    }

    /**
     * 设置networkAddress
     *
     * @param networkAddress networkAddress
     */
    public MessageNettyClientBuilder networkAddress(NetworkAddress networkAddress) {
        this.networkAddress = networkAddress;
        return this;
    }

    public MessageNettyClient build() {
        if (bootstrapFactory == null) {
            bootstrapFactory = new MessageNettyBootstrapFacotry(null, messageTypeRegister);
        }

        MessageNettyClient client = new MessageNettyClient(networkAddress, bootstrapFactory);

        if (handlerFactory == null) {
            handlerFactory = new MessageNettyClientHandlerFactory(client);
        }

        bootstrapFactory.setHandlerFactory(handlerFactory);
        return client;
    }

}
