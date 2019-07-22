
package cn.featherfly.network.netty;

import cn.featherfly.network.NetworkAddress;

/**
 * <p>
 * ClientDisconnectEvent
 * </p>
 * 
 * @author zhongj
 */
public class NettyClientConnectEvent
        implements cn.featherfly.network.ClientConnectEvent {

    private NetworkAddress remoteAddress;

    /**
     * @param remoteAddress
     */
    protected NettyClientConnectEvent(NetworkAddress remoteAddress) {
        super();
        this.remoteAddress = remoteAddress;
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
}
