
package cn.featherfly.network.netty.msg;

import cn.featherfly.network.NetworkAddress;
import cn.featherfly.network.netty.MessageNettyClient;
import cn.featherfly.network.netty.MessageNettyClientBuilder;
import cn.featherfly.network.serialization.MessageTypeRegister;

/**
 * <p>
 * NettyMessageClientTest
 * </p>
 *
 * @author zhongj
 */
public class NettyMessageClientTest {
    static MessageTypeRegister register;
    static {
        register = new MessageTypeRegister();
        register.register(TestClientMsg.class, (short) 0);
        register.register(ClientRegistMsg.class, (short) 1);
        register.register(ResponseMsg.class, (short) 2);
        register.register(BasicMsg.class, (short) 3);
    }

    public static void main(String[] args) {

        // MessageNettyBootstrapFacotry bootstrapFacotry = new
        // MessageNettyBootstrapFacotry();
        // MessageNettyClientHandlerFactory handlerFactory = new
        // MessageNettyClientHandlerFactory(
        // null);
        MessageNettyClient client = new MessageNettyClientBuilder(
                new NetworkAddress("127.0.0.1", NettyMessageServerTest.port)).messageTypeRegister(register).build();

        // MessageNettyClient client = new MessageNettyClientBuilder(
        // new NetworkAddress("127.0.0.1", NettyMessageServerTest.port))
        // .build();
        client.onReceive(l -> {
            System.out.println("recevie " + l.getReceive());
            if (!(l.getReceive() instanceof ResponseMsg)) {
                // 不是客户端附送消息后的服务器端反馈消息，建议直接在发送消息回调中处理
            }
            // 其他的服务器推送信息
            if (l.getReceive() instanceof TestClientMsg) {

            }
            //

        }).onConnect(l -> {
            System.out.println("onConnect");
            if (l.isConnectSuccess()) {
                System.out.println("connected success " + l.getRemoteAddress());

                int i = 0;
                while (i < 2) {
                    i++;
                    TestClientMsg msg = new TestClientMsg();
                    msg.setMessage("测试测试_" + i);
                    client.sendAndReceive(msg).whenComplete((res, e) -> {
                        System.out.println("send success and recevie" + res);
                    }).exceptionally(e -> {
                        System.out.println("send error " + e.getLocalizedMessage());
                        e.printStackTrace();
                        return null;
                    });
                }
            } else {
                System.out.println("connected error " + l.getRemoteAddress());
            }
        }).connect();

        boolean b = true;
        while (b) {

        }
        System.out.println("exit");
    }
}
