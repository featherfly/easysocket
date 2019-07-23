
package cn.featherfly.network;

/**
 * <p>
 * ClientEvent
 * </p>
 *
 * @author zhongj
 */
public interface ServerReceivableEvent<R, RES> {

    R getReceive();

    RES getResponse();

    String getClientId();

    String getRemoteAddress();
}
