
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

    private boolean connectSuccess;

    /**
     * @param remoteAddress
     */
    protected NettyClientConnectEvent(NetworkAddress remoteAddress,
            boolean connectSuccess) {
        super();
        this.remoteAddress = remoteAddress;
        this.connectSuccess = connectSuccess;
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
     * 
     * {@inheritDoc}
     */
    @Override
    public boolean isConnectSuccess() {
        return connectSuccess;
    }

    /**
     * 设置connectSuccess
     * 
     * @param connectSuccess
     *            connectSuccess
     */
    public void setConnectSuccess(boolean connectSuccess) {
        this.connectSuccess = connectSuccess;
    }
}
