
package cn.featherfly.network.netty.msg;

import cn.featherfly.common.lang.RandomUtils;
import cn.featherfly.network.netty.server.MessageNettyServer;
import cn.featherfly.network.netty.server.MessageNettyServerBuilder;

/**
 * <p>
 * NettyMessageClientTest
 * </p>
 *
 * @author zhongj
 */
public class NettyMessageServerTest {

    public static int port = 33334;

    public static void main(String[] args) {

        MessageNettyServerBuilder builder = new MessageNettyServerBuilder(port);
        builder.messageTypeRegister(NettyMessageClientTest.register);
        MessageNettyServer server = builder.build();

        server.onReceive(event -> {
            ResponseMsg response = event.getResponse();
            if (response != null) {
                return response;
            }
            response = new ResponseMsg();
            Msg msg = event.getReceive();
            System.out.println("receive -> " + msg.toString());
            response.setSuccess(RandomUtils.getRandomBoolean());
            if (response.isSuccess()) {
                response.setMessage("成功");
            } else {
                response.setMessage("失败");
            }
            return response;
        }).start();
    }
}
