
package cn.featherfly.network.netty.msg;

/**
 * <p>
 * ClientRegistMsg
 * </p>
 *
 * @author zhongj
 */
public class ClientRegistMsg extends ClientMsg {

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ClientRegistMsg [token=" + token + ", secrecy=" + secrecy
                + ", clientId=" + clientId + ", sender=" + sender + ", id=" + id
                + "]";
    }
}
