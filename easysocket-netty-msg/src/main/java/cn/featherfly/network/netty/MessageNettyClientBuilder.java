
package cn.featherfly.network.netty;

import cn.featherfly.network.NetworkAddress;

/**
 * <p>
 * NettyClientBuilder
 * </p>
 * 
 * @author zhongj
 */
public class MessageNettyClientBuilder {

    private MessageNettyClientHandler handler = new MessageNettyClientHandler(
            new ClientIdCpuIdGenerator());

    private NettyBootstrapFacotry facotry = new SimpleNettyBootstrapFacotry(
            handler);

    private NetworkAddress networkAddress;

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
     * @param handler
     *            handler
     */
    public MessageNettyClientBuilder handler(
            MessageNettyClientHandler handler) {
        this.handler = handler;
        return this;
    }

    /**
     * 设置facotry
     * 
     * @param facotry
     *            facotry
     */
    public MessageNettyClientBuilder facotry(NettyBootstrapFacotry facotry) {
        this.facotry = facotry;
        return this;
    }

    /**
     * 设置networkAddress
     * 
     * @param networkAddress
     *            networkAddress
     */
    public MessageNettyClientBuilder networkAddress(
            NetworkAddress networkAddress) {
        this.networkAddress = networkAddress;
        return this;
    }

    public MessageNettyClient build() {
        MessageNettyClient client = new MessageNettyClient(networkAddress,
                facotry);
        handler.setNettyClient(client);
        return client;
    }

}
