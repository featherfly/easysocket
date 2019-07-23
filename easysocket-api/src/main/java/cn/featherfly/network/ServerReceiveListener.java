
package cn.featherfly.network;

/**
 * <p>
 * ClientReceiveListener
 * </p>
 *
 * @author zhongj
 */
@FunctionalInterface
public interface ServerReceiveListener<R, RES> {

    RES onReceive(ServerReceivableEvent<R, RES> event);

}
