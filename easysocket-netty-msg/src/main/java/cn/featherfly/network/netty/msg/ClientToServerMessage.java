
package cn.featherfly.network.netty.msg;

/**
 * <p>
 * ClientToClientMsg
 * </p>
 *
 * @author zhongj
 */
public interface ClientToServerMessage extends Message {

    /**
     * 返回token
     *
     * @return token
     */
    String getToken();

    /**
     * 设置token
     *
     * @param token token
     */
    void setToken(String token);

    /**
     * 返回secrecy
     *
     * @return secrecy
     */
    Integer getSecrecy();

    void setSecrecy(Integer secrecy);

    String getClientId();

    void setClientId(String clientId);
}
