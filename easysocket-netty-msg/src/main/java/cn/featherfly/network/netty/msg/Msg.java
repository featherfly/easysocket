
package cn.featherfly.network.netty.msg;

/**
 * <p>
 * Msg
 * </p>
 *
 * @author zhongj
 */
public abstract class Msg implements Message {

    protected String id;

    /**
     * 返回id
     *
     * @return id
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }

}
