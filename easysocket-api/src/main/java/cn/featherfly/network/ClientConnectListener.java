
package cn.featherfly.network;

/**
 * <p>
 * ClientReceiveListener
 * </p>
 * 
 * @author zhongj
 */
@FunctionalInterface
public interface ClientConnectListener<R> {

    void onConnect(ClientConnectEvent event);

}
