
package cn.featherfly.network;

/**
 * <p>
 * Server
 * </p>
 * 
 * @author zhongj
 */
public interface Server<S, R, CID> {

    // <C extends Client<S, R>> C onReceive(ClientReceiveListener<R> listener);
    //
    // <C extends Client<S, R>> C onConnect(ClientConnectListener<R> listener);
    //
    // <C extends Client<S, R>> C onDisconnect(ClientDisconnectListener
    // listener);
    //
    // CompletionStage<ClientReceivableEvent<R>> send(CID clientId, S sending);
    //
    // CompletionStage<ClientReceivableEvent<R>> sendAll(S sending);
}
