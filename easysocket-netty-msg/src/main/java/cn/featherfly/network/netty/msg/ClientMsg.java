
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
     * @param token token
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
     * @param secrecy secrecy
     */
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
