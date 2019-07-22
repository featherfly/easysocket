
package cn.featherfly.network;

/**
 * <p>
 * ClientEvent
 * </p>
 * 
 * @author zhongj
 */
public interface ClientReceivableEvent<R> extends ClientEvent {

    R getReceive();

}
