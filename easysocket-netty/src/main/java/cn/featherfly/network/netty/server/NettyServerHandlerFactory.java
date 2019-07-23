
package cn.featherfly.network.netty.server;

/**
 * <p>
 * NettyServerHandlerFactory
 * </p>
 *
 * @author zhongj
 */
public interface NettyServerHandlerFactory<S, R, RES> {

    NettyServerHandler<S, R, RES> create();
}
