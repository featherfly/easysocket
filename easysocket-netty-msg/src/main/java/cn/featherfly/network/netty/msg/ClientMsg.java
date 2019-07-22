
package cn.featherfly.network.netty.msg;

/**
 * <p>
 * ClientMsg
 * </p>
 *
 * @author zhongj
 */
public abstract class ClientMsg extends Msg {

    protected String token;

    protected Integer secrecy;

    protected String clientId;

    /**
     * 返回token
     *
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置token
     *
     * @param token
     *            token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 返回secrecy
     *
     * @return secrecy
     */
    public Integer getSecrecy() {
        return secrecy;
    }

    /**
     * 设置secrecy
     *
     * @param secrecy
     *            secrecy
     */
    public void setSecrecy(Integer secrecy) {
        this.secrecy = secrecy;
    }

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
     * @param clientId
     *            clientId
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ClientMsg [token=" + token + ", secrecy=" + secrecy
                + ", clientId=" + clientId + ", sender=" + sender + ", id=" + id
                + "]";
    }
}
