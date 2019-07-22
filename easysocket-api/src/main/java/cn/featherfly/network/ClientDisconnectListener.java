
package cn.featherfly.network;

/**
 * <p>
 * ClientDisconnectListener
 * </p>
 * 
 * @author zhongj
 */
@FunctionalInterface
public interface ClientDisconnectListener {

    void onDisconnect(ClientDisconnectEvent event);

}
