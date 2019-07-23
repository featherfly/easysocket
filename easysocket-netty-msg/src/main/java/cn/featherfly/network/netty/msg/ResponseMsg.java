
package cn.featherfly.network.netty.msg;

/**
 * <p>
 * CommandData
 * </p>
 *
 * @author zhongj
 */
public class ResponseMsg extends BasicMsg {

    private boolean success;

    private String code;

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
     * @param success success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * 返回code
     *
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置code
     *
     * @param code code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ResponseMsg [success=" + success + ", code=" + code + ", message=" + message + ", id=" + id + "]";
    }
}
