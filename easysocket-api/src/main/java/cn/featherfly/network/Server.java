
package cn.featherfly.network;

/**
 * <p>
 * Server
 * </p>
 *
 * @param <S>   推送数据
 * @param <R>   接收数据
 * @param <RES> 反馈数据类型，即接收到消息后的反馈数据类型（包括推送后客服端反馈和服务端接收到消息反馈客户端）
 * @author zhongj
 */
public interface Server<S, R, RES> {
    /**
     * start
     */
    void start();

    void stop();

    <C extends Server<S, R, RES>> C onClientConnect(ClientConnectListener listener);

    <C extends Server<S, R, RES>> C onReceive(ServerReceiveListener<R, RES> listener);

    <C extends Server<S, R, RES>> C onClientDisconnect(ClientDisconnectListener listener);

    void send(String clientId, S sending);

    void sendAll(S sending);
}
