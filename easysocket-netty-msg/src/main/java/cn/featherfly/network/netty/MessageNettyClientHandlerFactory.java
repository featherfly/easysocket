
package cn.featherfly.network.netty;

import cn.featherfly.network.netty.msg.ClientMsg;
import cn.featherfly.network.netty.msg.Msg;

/**
 * <p>
 * MessageNettyClientHandlerFactory
 * </p>
 * 
 * @author zhongj
 */
public class MessageNettyClientHandlerFactory implements
        NettyClientHandlerFactory<MessageNettyClientHandler, MessageNettyClient, ClientMsg, Msg> {

    private ClientIdGenerator clientIdGenerator;

    private MessageNettyClient clinet;

    /**
     * 
     * @param clinet
     */
    public MessageNettyClientHandlerFactory(MessageNettyClient clinet) {
        this(clinet, null);
    }

    /**
     * 
     * @param clinet
     * @param clientIdGenerator
     */
    public MessageNettyClientHandlerFactory(MessageNettyClient clinet,
            ClientIdGenerator clientIdGenerator) {
        super();
        if (clientIdGenerator == null) {
            clientIdGenerator = new ClientIdCpuIdGenerator();
        }
        this.clientIdGenerator = clientIdGenerator;
        this.clinet = clinet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessageNettyClientHandler create() {
        return new MessageNettyClientHandler(clientIdGenerator, clinet);
    }

    /**
     * 返回clinet
     * 
     * @return clinet
     */
    public MessageNettyClient getClinet() {
        return clinet;
    }

    /**
     * 设置clinet
     * 
     * @param clinet
     *            clinet
     */
    public void setClinet(MessageNettyClient clinet) {
        this.clinet = clinet;
    }

}
