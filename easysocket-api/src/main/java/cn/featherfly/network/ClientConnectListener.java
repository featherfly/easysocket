
package cn.featherfly.network;

/**
 * <p>
 * ClientReceiveListener
 * </p>
 *
 * @author zhongj
 */
@FunctionalInterface
public interface ClientConnectListener {

    void onConnect(ClientConnectEvent event);

}
