
package cn.featherfly.network.netty.msg;

/**
 * <p>
 * Msg
 * </p>
 *
 * @author zhongj
 */
public abstract class Msg {

    public enum Sender {
        CLIENT, CONSOLE, SERVER
    }

    protected Sender sender;

    protected String id;

    /**
     * 返回sender
     *
     * @return sender
     */
    public Sender getSender() {
        return sender;
    }

    /**
     * 设置sender
     *
     * @param sender sender
     */
    public void setSender(Sender sender) {
        this.sender = sender;
    }

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
