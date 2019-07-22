
package cn.featherfly.network;

/**
 * <p>
 * ClientListener
 * </p>
 * 
 * @author zhongj
 */
public interface ClientListener<S, R> extends ClientDisconnectListener,
        ClientConnectListener<R>, ClientReceiveListener<R> {

}
