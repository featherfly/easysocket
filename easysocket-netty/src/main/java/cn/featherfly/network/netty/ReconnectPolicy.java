
package cn.featherfly.network.netty;

import cn.featherfly.network.NetworkAddress;

/**
 * <p>
 * ReConnectPolicy
 * </p>
 * 
 * @author zhongj
 */
public interface ReconnectPolicy {

    long getDelay(long reconnectTimes);

    boolean isReconnectable(long reconnectTimes);

    NetworkAddress getRemoteAddress();
}
