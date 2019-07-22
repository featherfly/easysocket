
package cn.featherfly.network;

/**
 * <p>
 * ClientConnectEvent
 * </p>
 * 
 * @author zhongj
 */
public interface ClientConnectEvent extends ClientEvent {
    /**
     * 返回connectSuccess
     * 
     * @return connectSuccess
     */
    boolean isConnectSuccess();
}
