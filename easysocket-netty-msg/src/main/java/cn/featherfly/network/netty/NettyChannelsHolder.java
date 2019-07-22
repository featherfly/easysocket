
package cn.featherfly.network.netty;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.featherfly.network.netty.msg.Msg;
import io.netty.channel.Channel;

/**
 * <p>
 * NettyChannelMap
 * </p>
 *
 * @author zhongj
 */
public class NettyChannelsHolder {

    private Map<String, Channel> clientIdChannelMap = new ConcurrentHashMap<>();

    private static NettyChannelsHolder instance = new NettyChannelsHolder();

    private NettyChannelsHolder() {

    }

    public static NettyChannelsHolder getInstance() {
        return instance;
    }

    public Channel addChannel(String clientId, Channel channel) {
        return clientIdChannelMap.put(clientId, channel);
    }

    public Channel getChannel(String clientId) {
        return clientIdChannelMap.get(clientId);
    }

    public Channel removeChannel(String clientId) {
        return clientIdChannelMap.remove(clientId);
    }

    public void removeChannel(Channel channel) {
        if (clientIdChannelMap.containsValue(channel)) {// 查看是否包含
            String key = null;
            Channel value = null;
            for (Map.Entry<String, Channel> entry : clientIdChannelMap
                    .entrySet()) {
                key = entry.getKey();
                value = entry.getValue();
                if (value == channel) {
                    break;
                }
            }
            clientIdChannelMap.remove(key);
        }
    }

    public void pushToAllClient(Msg pushData) {
        if (clientIdChannelMap.size() == 0) {
            return;
        }
        for (Channel channel : clientIdChannelMap.values()) {
            channel.writeAndFlush(pushData);
        }
    }

    public void pushToClient(Msg pushData, String clientId) {
        if (clientIdChannelMap.size() == 0) {
            return;
        }
        getChannel(clientId).writeAndFlush(pushData);
    }

}
