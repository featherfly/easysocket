package cn.featherfly.network.netty.msg;

import cn.featherfly.network.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EquipmentServer implements Server<Msg, ResponseMsg, String> {

    private int port;

    /**
     * @param port
     */
    public EquipmentServer(int port) {
        super();
        this.port = port;
    }

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
}