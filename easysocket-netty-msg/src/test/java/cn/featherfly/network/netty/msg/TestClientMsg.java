
package cn.featherfly.network.netty.msg;

/**
 * <p>
 * TestClientMsg
 * </p>
 *
 * @author zhongj
 */
public class TestClientMsg extends ClientMsg {

    private String message;

    /**
     * 返回message
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置message
     *
     * @param message message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "TestClientMsg [message=" + message + ", token=" + token + ", secrecy=" + secrecy + ", id=" + id + "]";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getClientId() {
        // YUFEI_TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setClientId(String clientId) {
        // YUFEI_TODO Auto-generated method stub

    }

}
