
package cn.featherfly.network.netty.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;

/**
 * <p>
 * NettyChannelMap
 * </p>
 *
 * @author zhongj
 */
public class NettyChannelsHolder {

    private static final Map<Object, NettyChannelsHolder> HOLDERS = new ConcurrentHashMap<>();

    private Map<String, Channel> clientIdChannelMap = new ConcurrentHashMap<>();

    private NettyChannelsHolder() {

    }

    public static synchronized NettyChannelsHolder getInstance(Object holder) {
        NettyChannelsHolder channelsHolder = HOLDERS.get(holder);
        if (channelsHolder == null) {
            channelsHolder = new NettyChannelsHolder();
            HOLDERS.put(holder, channelsHolder);
        }
        return channelsHolder;
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
            for (Map.Entry<String, Channel> entry : clientIdChannelMap.entrySet()) {
                key = entry.getKey();
                value = entry.getValue();
                if (value == channel) {
                    break;
                }
            }
            clientIdChannelMap.remove(key);
        }
    }

    public void pushToAllClient(Object msg) {
        if (clientIdChannelMap.size() == 0) {
            return;
        }
        for (Channel channel : clientIdChannelMap.values()) {
            channel.writeAndFlush(msg);
        }
    }

    public boolean pushToClient(Object msg, String clientId) {
        Channel channel = getChannel(clientId);
        if (channel != null) {
            channel.writeAndFlush(msg);
            return true;
        }
        return false;
    }

}
