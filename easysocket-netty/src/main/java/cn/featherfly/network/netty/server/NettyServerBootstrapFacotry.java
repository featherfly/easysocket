
package cn.featherfly.network.netty.server;

import io.netty.bootstrap.ServerBootstrap;

/**
 * <p>
 * ConnectionFacotry
 * </p>
 *
 * @author zhongj
 */
public interface NettyServerBootstrapFacotry {

    ServerBootstrap create();
}
