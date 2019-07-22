
package cn.featherfly.network.netty.msg;

/**
 * <p>
 * CommandData
 * </p>
 *
 * @author zhongj
 */
public class ResponseMsg extends BasicMsg {

    public enum State {
        CONNECTING_SERVER, CONNECTING_CLIENT,
    }

    private boolean success;

    private State state;

    /**
     * 返回success
     *
     * @return success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * 设置success
     *
     * @param success
     *            success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * 返回state
     *
     * @return state
     */
    public State getState() {
        return state;
    }

    /**
     * 设置state
     *
     * @param state
     *            state
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ResponseMsg [success=" + success + ", message=" + message
                + ", state=" + state + ", sender=" + sender + ", id=" + id
                + "]";
    }
}
