
package cn.featherfly.network;

/**
 * <p>
 * Client
 * </p>
 * 
 * @author zhongj
 */
public interface Client<S, R> {

    public static enum State {
        PREPARATION, CONNECTING, CONNECTED, DISCONEECTED;
    }

    void connect();

    void send(S sending);

    void disconnect();

    <C extends Client<S, R>> C onReceive(ClientReceiveListener<R> listener);

    <C extends Client<S, R>> C onConnect(ClientConnectListener<R> listener);

    <C extends Client<S, R>> C onDisconnect(ClientDisconnectListener listener);

    State getState();
}
