
package cn.featherfly.network.netty.msg;

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
        EquipmentServer server = new EquipmentServer(port);
        server.start();
    }
}
