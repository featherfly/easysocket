
package cn.featherfly.network.netty;

import cn.featherfly.network.NetworkAddress;

/**
 * <p>
 * ClientConnectEvent
 * </p>
 * 
 * @author zhongj
 */
public class NettyClientReceivableEvent<R>
        implements cn.featherfly.network.ClientReceivableEvent<R> {

    private NetworkAddress remoteAddress;

    private R receive;

    /**
     * @param remoteAddress
     */
    protected NettyClientReceivableEvent(NetworkAddress remoteAddress, R receive) {
        super();
        this.remoteAddress = remoteAddress;
        this.receive = receive;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NetworkAddress getRemoteAddress() {
        return remoteAddress;
    }

    /**
     * 设置remoteAddress
     * 
     * @param remoteAddress
     *            remoteAddress
     */
    public void setRemoteAddress(NetworkAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public R getReceive() {
        return receive;
    }
}
