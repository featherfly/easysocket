
package cn.featherfly.network.netty;

import cn.featherfly.network.NetworkAddress;

/**
 * <p>
 * ClientDisconnectEvent
 * </p>
 *
 * @author zhongj
 */
public class NettyClientDisconnectEvent implements cn.featherfly.network.ClientDisconnectEvent {

    private NetworkAddress remoteAddress;

    /**
     * @param remoteAddress
     */
    public NettyClientDisconnectEvent(NetworkAddress remoteAddress) {
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
     * @param remoteAddress remoteAddress
     */
    public void setRemoteAddress(NetworkAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }
}
