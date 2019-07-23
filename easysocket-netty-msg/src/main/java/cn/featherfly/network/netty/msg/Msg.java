
package cn.featherfly.network.netty.msg;

/**
 * <p>
 * Msg
 * </p>
 *
 * @author zhongj
 */
public abstract class Msg {

    protected String id;

    /**
     * 返回id
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

}
