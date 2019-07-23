
package cn.featherfly.network;

import java.util.concurrent.CompletionStage;

import cn.featherfly.network.netty.msg.ClientMsg;
import cn.featherfly.network.netty.msg.Msg;
import cn.featherfly.network.netty.msg.ResponseMsg;

/**
 * <p>
 * MessageClient
 * </p>
 *
 * @author zhongj
 */
public interface MessageClient extends Client<ClientMsg, Msg> {
    /**
     * sendAndReceive
     * 
     * @param sending
     * @return response
     */
    CompletionStage<ResponseMsg> sendAndReceive(ClientMsg sending);
}
