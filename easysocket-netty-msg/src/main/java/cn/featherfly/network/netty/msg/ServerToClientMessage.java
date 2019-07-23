
package cn.featherfly.network.netty.msg;

/**
 * <p>
 * ClientToClientMsg
 * </p>
 *
 * @author zhongj
 */
public interface ServerToClientMessage extends Message {

    String getToClientId();

    void setToClientId();

}
