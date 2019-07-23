
package cn.featherfly.network;

/**
 * <p>
 * Server
 * </p>
 *
 * @param <S>   推送数据
 * @param <R>   接收数据
 * @param <RES> 反馈数据类型，即接收到消息后的反馈数据类型（包括推送后客服端反馈和服务端接收到消息反馈客户端）
 * @param <CID> 客户端ID类型
 * @author zhongj
 */
public interface Server<S, R, RES> {

    void start();

    void stop();

    <C extends Server<S, R, RES>> C onClientConnect(ClientConnectListener listener);

    <C extends Server<S, R, RES>> C onReceive(ServerReceiveListener<R, RES> listener);

    <C extends Server<S, R, RES>> C onClientDisconnect(ClientDisconnectListener listener);

    //    <C extends Client<S, R>> C onReceive(ClientReceiveListener<R> listener);

    // <C extends Client<S, R>> C onConnect(ClientConnectListener<R> listener);
    //
    // <C extends Client<S, R>> C onDisconnect(ClientDisconnectListener
    // listener);
    //
    void send(String clientId, S sending);

    void sendAll(S sending);
}
