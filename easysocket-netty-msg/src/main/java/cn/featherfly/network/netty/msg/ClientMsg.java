
package cn.featherfly.network.netty.msg;

/**
 * <p>
 * ClientMsg
 * </p>
 *
 * @author zhongj
 */
public abstract class ClientMsg extends Msg implements ClientToServerMessage {

    protected String token;

    protected Integer secrecy;

    protected String clientId;

    /**
     * 返回clientId
     *
     * @return clientId
     */
    @Override
    public String getClientId() {
        return clientId;
    }

    /**
     * 设置clientId
     *
     * @param clientId clientId
     */
    @Override
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * 返回token
     *
     * @return token
     */
    @Override
    public String getToken() {
        return token;
    }

    /**
     * 设置token
     *
     * @param token token
     */
    @Override
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 返回secrecy
     *
     * @return secrecy
     */
    @Override
    public Integer getSecrecy() {
        return secrecy;
    }

    /**
     * 设置secrecy
     *
     * @param secrecy secrecy
     */
    @Override
    public void setSecrecy(Integer secrecy) {
        this.secrecy = secrecy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ClientMsg [token=" + token + ", secrecy=" + secrecy + ", id=" + id + "]";
    }
}
