
package cn.featherfly.network.netty;

import cn.featherfly.network.serialization.MessageTypeRegister;
import io.netty.handler.logging.LogLevel;

/**
 * <p>
 * SimpleNettyBootstrapFacotry
 * </p>
 *
 * @author zhongj
 */
public class NettyBootstrapConfig {

    protected int connectTimeoutMillis = 10 * 1000;

    protected boolean soKeepAlive = true;

    protected boolean tcpNoDelay = true;

    protected LogLevel logLevel = LogLevel.INFO;

    protected MessageTypeRegister messageTypeRegister;

    /**
     * @param handlerFactory 处理业务的handler创建工厂
     */
    public NettyBootstrapConfig() {
    }

    /**
     * 返回connectTimeoutMillis
     *
     * @return connectTimeoutMillis
     */
    public int getConnectTimeoutMillis() {
        return connectTimeoutMillis;
    }

    /**
     * 设置connectTimeoutMillis
     *
     * @param connectTimeoutMillis connectTimeoutMillis
     */
    public void setConnectTimeoutMillis(int connectTimeoutMillis) {
        this.connectTimeoutMillis = connectTimeoutMillis;
    }

    /**
     * 返回soKeepAlive
     *
     * @return soKeepAlive
     */
    public boolean isSoKeepAlive() {
        return soKeepAlive;
    }

    /**
     * 设置soKeepAlive
     *
     * @param soKeepAlive soKeepAlive
     */
    public void setSoKeepAlive(boolean soKeepAlive) {
        this.soKeepAlive = soKeepAlive;
    }

    /**
     * 返回tcpNoDelay
     *
     * @return tcpNoDelay
     */
    public boolean isTcpNoDelay() {
        return tcpNoDelay;
    }

    /**
     * 设置tcpNoDelay
     *
     * @param tcpNoDelay tcpNoDelay
     */
    public void setTcpNoDelay(boolean tcpNoDelay) {
        this.tcpNoDelay = tcpNoDelay;
    }

    /**
     * 返回logLevel
     *
     * @return logLevel
     */
    public LogLevel getLogLevel() {
        return logLevel;
    }

    /**
     * 设置logLevel
     *
     * @param logLevel logLevel
     */
    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    /**
     * 返回messageTypeRegister
     *
     * @return messageTypeRegister
     */
    public MessageTypeRegister getMessageTypeRegister() {
        return messageTypeRegister;
    }

    /**
     * 设置messageTypeRegister
     *
     * @param messageTypeRegister messageTypeRegister
     */
    public void setMessageTypeRegister(MessageTypeRegister messageTypeRegister) {
        this.messageTypeRegister = messageTypeRegister;
    }
}
