package cn.featherfly.network.netty.msg;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import cn.featherfly.common.lang.RandomUtils;
import cn.featherfly.network.netty.MessageManager;
import cn.featherfly.network.netty.server.NettyChannelsHolder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageNettyServerHandler extends SimpleChannelInboundHandler<Msg> {

    private NettyChannelsHolder channelHolder = NettyChannelsHolder.getInstance(this);

    /**
     */
    public MessageNettyServerHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
        // 收到消息直接打印输出
        System.out
                .println(this.getClass().getName() + "  " + ctx.channel().remoteAddress() + " Say: " + msg.toString());
        ResponseMsg response = null;
        if (msg instanceof ResponseMsg) {
            // 表示是反馈消息
            MessageManager.getMessageManager(ctx.channel()).receive((ResponseMsg) msg);
        } else if (msg instanceof ClientRegistMsg) {
            // 客户端注册消息
            ClientRegistMsg clientRegistMsg = (ClientRegistMsg) msg;
            Channel channel = channelHolder.addChannel(clientRegistMsg.getClientId(), ctx.channel());
            if (channel != null) {
                String message = String.format(
                        "clientId[%s] registed channel[%s] again, close the previous channel[%s]",
                        clientRegistMsg.getClientId(), ctx.channel().remoteAddress().toString(),
                        channel.remoteAddress().toString());
                System.out.println(message);
                BasicMsg basicMsg = new BasicMsg();
                basicMsg.setMessage(message);
                channel.writeAndFlush(basicMsg).sync();
                channel.close();
            }
        } else {
            // TODO 后续其他数据类型
            String errorMsg = "未知数据类型";
            response = new ResponseMsg();
            response.setSuccess(false);
            response.setMessage(errorMsg);
            System.err.println("未知数据类型" + msg);
        }

        if (response == null) {
            response = createResponse();
            response.setSuccess(RandomUtils.getRandomBoolean());
            if (response.isSuccess()) {
                response.setMessage("成功");
            } else {
                response.setMessage("失败");
            }
        }
        response.setId(msg.getId());
        ctx.writeAndFlush(response);
    }

    /*
     * 覆盖 channelActive 方法 在channel被启用的时候触发 (在建立连接的时候) channelActive 和
     * channelInActive 在后面的内容中讲述，这里先不做详细的描述
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("RamoteAddress : " + ctx.channel().remoteAddress() + " active !");

        super.channelActive(ctx);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 移除客户端channel
        channelHolder.removeChannel(ctx.channel());
        System.out.println("RamoteAddress : " + ctx.channel().remoteAddress() + " inactive !");
        super.channelInactive(ctx);
    }

    public CompletionStage<ResponseMsg> push(String clientId, Msg data) {
        Channel channel = channelHolder.getChannel(clientId);
        CompletableFuture<ResponseMsg> future = new CompletableFuture<>();
        if (channel == null) {
            ResponseMsg response = createResponse();
            response.setSuccess(false);
            response.setMessage("没有找到注册的客户端号[" + clientId + "]");
            future.complete(response);
        } else {
            channel.writeAndFlush(data);
            return MessageManager.getMessageManager(channel).putSendMessage(data);

        }
        return future;
    }

    private ResponseMsg createResponse() {
        ResponseMsg response = new ResponseMsg();
        //        response.setSender(Sender.SERVER);
        return response;
    }
}