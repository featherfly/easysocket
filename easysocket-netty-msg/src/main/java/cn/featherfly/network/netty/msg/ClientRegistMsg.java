
package cn.featherfly.network.netty.msg;

/**
 * <p>
 * ClientRegistMsg
 * </p>
 *
 * @author zhongj
 */
public class ClientRegistMsg extends ClientMsg {

    protected String clientId;

    /**
     * 返回clientId
     * 
     * @return clientId
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * 设置clientId
     * 
     * @param clientId clientId
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ClientRegistMsg [token=" + token + ", secrecy=" + secrecy + ", clientId=" + clientId + ", id=" + id
                + "]";
    }
}
