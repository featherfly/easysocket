
package cn.featherfly.network;

/**
 * <p>
 * NetworkAddress
 * </p>
 * 
 * @author zhongj
 */
public class NetworkAddress {

    private String host;

    private int port;

    /**
     */
    public NetworkAddress() {
    }

    /**
     * @param host
     * @param port
     */
    public NetworkAddress(String host, int port) {
        super();
        this.host = host;
        this.port = port;
    }

    /**
     * 返回host
     * 
     * @return host
     */
    public String getHost() {
        return host;
    }

    /**
     * 设置host
     * 
     * @param host
     *            host
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * 返回port
     * 
     * @return port
     */
    public int getPort() {
        return port;
    }

    /**
     * 设置port
     * 
     * @param port
     *            port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return host + ":" + port;
    }
}
