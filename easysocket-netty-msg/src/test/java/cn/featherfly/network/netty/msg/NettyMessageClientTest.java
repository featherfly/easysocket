
package cn.featherfly.network.netty.msg;

import cn.featherfly.network.NetworkAddress;
import cn.featherfly.network.netty.ClientIdCpuIdGenerator;
import cn.featherfly.network.netty.MessageNettyClient;
import cn.featherfly.network.netty.MessageNettyClientBuilder;
import cn.featherfly.network.netty.MessageNettyClientHandler;
import cn.featherfly.network.netty.SimpleNettyBootstrapFacotry;
import cn.featherfly.network.netty.codec.SerializableDecoder;
import cn.featherfly.network.netty.codec.SerializableEncoder;
import cn.featherfly.network.serialization.MessageTypeRegister;

/**
 * <p>
 * NettyMessageClientTest
 * </p>
 * 
 * @author zhongj
 */
public class NettyMessageClientTest {

    public static void main(String[] args) {

        MessageTypeRegister register = new MessageTypeRegister();
        register.register(TestClientMsg.class, (short) 0);
        register.register(ClientRegistMsg.class, (short) 1);
        register.register(ResponseMsg.class, (short) 2);

        MessageNettyClientHandler handler = new MessageNettyClientHandler(
                new ClientIdCpuIdGenerator());
        SimpleNettyBootstrapFacotry facotry = new SimpleNettyBootstrapFacotry(
                handler, new SerializableDecoder(register),
                new SerializableEncoder(register));

        MessageNettyClient client = new MessageNettyClientBuilder(
                new NetworkAddress("127.0.0.1", NettyMessageServerTest.port))
                        .facotry(facotry).handler(handler).build();

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

        }).connect().whenComplete((r, t) -> {
            System.out.println("connected success " + r.getRemoteAddress());
            int i = 0;
            while (i++ < 10) {
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

        }).exceptionally(t -> {
            System.out.println("connected error " + t.getLocalizedMessage());
            t.printStackTrace();
            return null;
        });

        System.out.println("exit");
    }
}
