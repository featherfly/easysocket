
package cn.featherfly.network.netty;

import io.netty.bootstrap.Bootstrap;

/**
 * <p>
 * ConnectionFacotry
 * </p>
 * 
 * @author zhongj
 */
public interface NettyBootstrapFacotry {

    Bootstrap create();
}
