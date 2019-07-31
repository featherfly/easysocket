
package cn.featherfly.network.netty.server;

import cn.featherfly.network.ServerReceivableEvent;

/**
 * <p>
 * NettyServerReceiveableEvent
 * </p>
 *
 * @author zhongj
 */
public class NettyServerReceiveableEvent<R, RES> implements ServerReceivableEvent<R, RES> {

    private String remoteAddress;

    private R receive;

    private String clientId;

    private RES response;

    /**
     */
    public NettyServerReceiveableEvent() {
        super();
    }

    /**
     * @param remoteAddress
     * @param receive
     * @param clientId
     */
    public NettyServerReceiveableEvent(String remoteAddress, R receive, String clientId) {
        super();
        this.remoteAddress = remoteAddress;
        this.receive = receive;
        this.clientId = clientId;
    }

    /**
     * 设置remoteAddress
     *
     * @param remoteAddress remoteAddress
     */
    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRemoteAddress() {
        return remoteAddress;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public R getReceive() {
        return receive;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getClientId() {
        return clientId;
    }

    /**
     * 设置receive
     *
     * @param receive receive
     */
    public void setReceive(R receive) {
        this.receive = receive;
    }

    /**
     * 设置clientId
     *
     * @param clientId clientId
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * 返回response
     *
     * @return response
     */
    @Override
    public RES getResponse() {
        return response;
    }

    /**
     * 设置response
     *
     * @param response response
     */
    public void setResponse(RES response) {
        this.response = response;
    }

}
