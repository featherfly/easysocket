
package cn.featherfly.network;

/**
 * <p>
 * ClientReceiveListener
 * </p>
 * 
 * @author zhongj
 */
@FunctionalInterface
public interface ClientReceiveListener<R> {

    void onReceive(ClientReceivableEvent<R> event);

}
