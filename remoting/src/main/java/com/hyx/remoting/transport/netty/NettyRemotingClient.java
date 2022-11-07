package com.hyx.remoting.transport.netty;

import com.hyx.remoting.RemotingClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Netty Client.
 *
 * @author hyx
 **/

@Slf4j
public class NettyRemotingClient implements RemotingClient {
    
    private final Bootstrap bootstrap;
    
    private final EventLoopGroup eventExecutors;
    
    private final ConcurrentHashMap<String, ChannelFuture> channelTables;
    
    public NettyRemotingClient() {
        this.bootstrap = new Bootstrap();
        this.eventExecutors = new NioEventLoopGroup();
        this.channelTables = new ConcurrentHashMap<>();
    }
    
    @Override
    public void start() {
        this.bootstrap.group(this.eventExecutors)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
        
                    }
                });
    }
    
    private Channel createChannel(final String addr) {
        String[] hostAndPort = addr.split(":");
        ChannelFuture channelFuture = bootstrap.connect(hostAndPort[0], Integer.parseInt(hostAndPort[1]));
        log.info("客户端与[{}]创建连接成功！", addr);
        this.channelTables.put(addr, channelFuture);
        return channelFuture.channel();
    }
    
    private void shutdownChannel(final String addr) {
        ChannelFuture future = this.channelTables.get(addr);
        if (future != null) {
            Channel channel = future.channel();
            channel.close();
            log.info("客户端与[{}]连接已断开！", addr);
        } else {
            log.info("无[{}]连接！", addr);
        }
    }
    
    @Override
    public void shutdown() {
    
    }
    
}
