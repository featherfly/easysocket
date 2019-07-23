package cn.featherfly.network.netty.msg;

import cn.featherfly.network.ClientConnectListener;
import cn.featherfly.network.ClientDisconnectListener;
import cn.featherfly.network.Server;
import cn.featherfly.network.ServerReceiveListener;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EquipmentServer implements Server<Msg, Msg, ResponseMsg> {

    private int port;

    /**
     * @param port
     */
    public EquipmentServer(int port) {
        super();
        this.port = port;
    }

    @Override
    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new EquipmentServerInitializer());

            try {
                // 服务器绑定端口监听
                ChannelFuture f = b.bind(port).sync();
                // 监听服务器关闭监听
                f.channel().closeFuture().sync();
                // 可以简写为
                /* b.bind(portNumber).sync().channel().closeFuture().sync(); */
            } catch (InterruptedException e) {
                // YUFEI_TODO 异常处理
                e.printStackTrace();
            }

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        // YUFEI_TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <C extends Server<Msg, Msg, ResponseMsg>> C onClientConnect(ClientConnectListener listener) {
        // YUFEI_TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <C extends Server<Msg, Msg, ResponseMsg>> C onReceive(ServerReceiveListener<Msg, ResponseMsg> listener) {
        // YUFEI_TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <C extends Server<Msg, Msg, ResponseMsg>> C onClientDisconnect(ClientDisconnectListener listener) {
        // YUFEI_TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(String clientId, Msg sending) {
        // YUFEI_TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendAll(Msg sending) {
        // YUFEI_TODO Auto-generated method stub

    }

    public static void main(String[] args) {
        EquipmentServer server = new EquipmentServer(NettyMessageServerTest.port);
        server.start();
    }
}