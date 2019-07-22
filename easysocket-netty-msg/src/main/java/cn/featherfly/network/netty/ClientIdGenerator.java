
package cn.featherfly.network.netty;

/**
 * <p>
 * ClientIdGenerator
 * </p>
 * 
 * @author zhongj
 */
public interface ClientIdGenerator {
    /**
     * <p>
     * 获取客户端id
     * </p>
     * 
     * @return ClientId
     */
    String getClientId();

}