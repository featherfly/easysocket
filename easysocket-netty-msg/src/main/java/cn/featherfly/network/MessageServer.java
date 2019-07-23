
package cn.featherfly.network;

import java.util.Map;
import java.util.concurrent.CompletionStage;

import cn.featherfly.network.netty.msg.Msg;
import cn.featherfly.network.netty.msg.ResponseMsg;

/**
 * <p>
 * MessageClient
 * </p>
 *
 * @author zhongj
 */
public interface MessageServer extends Server<Msg, Msg, ResponseMsg> {

    /**
     * sendAndReceive
     *
     * @param clientId
     * @param sending
     * @return ResponseMsg
     */
    CompletionStage<ResponseMsg> sendAndReceive(String clientId, Msg sending);

    /**
     * sendAndReceive,返回的Map是clientId为key,对应clientId推送信息的反馈消息为value
     *
     * @param sending
     * @return Map<ClientId, ResponseMsg>
     */
    CompletionStage<Map<String, ResponseMsg>> sendAllAndReceive(Msg sending);

}
