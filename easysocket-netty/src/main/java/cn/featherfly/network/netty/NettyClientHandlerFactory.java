
package cn.featherfly.network.netty;

/**
 * <p>
 * NettyClientHandlerFactory
 * </p>
 * 
 * @author zhongj
 */
public interface NettyClientHandlerFactory<
        N extends NettyClientHandler<C, S, R>, C extends NettyClient<S, R>, S,
        R> {

    N create();
}
