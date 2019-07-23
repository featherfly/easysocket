
package cn.featherfly.network.netty.msg;

/**
 * <p>
 * Msg
 * </p>
 *
 * @author zhongj
 */
public class BasicMsg extends Msg {

    protected String message;

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
        return "BasicMsg [message=" + message + ", id=" + id + "]";
    }
}
