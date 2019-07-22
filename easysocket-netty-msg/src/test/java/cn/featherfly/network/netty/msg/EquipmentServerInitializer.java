package cn.featherfly.network.netty.msg;

import cn.featherfly.network.netty.codec.SerializableDecoder;
import cn.featherfly.network.netty.codec.SerializableEncoder;
import cn.featherfly.network.serialization.MessageTypeRegister;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class EquipmentServerInitializer
        extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        MessageTypeRegister register = new MessageTypeRegister();
        register.register(TestClientMsg.class, (short) 0);
        register.register(ClientRegistMsg.class, (short) 1);
        register.register(ResponseMsg.class, (short) 2);

        pipeline.addLast("decoder", new SerializableDecoder(register));
        pipeline.addLast("encoder", new SerializableEncoder(register));
        pipeline.addLast("handler", new MessageNettyServerHandler());
    }
}