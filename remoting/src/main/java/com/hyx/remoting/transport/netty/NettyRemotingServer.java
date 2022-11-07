package com.hyx.remoting.transport.netty;

import com.hyx.remoting.RemotingServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * Netty Server.
 *
 * @author hyx
 **/

@Slf4j
public class NettyRemotingServer implements RemotingServer {
    
    private final Integer port = 9876;
    
    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    
    private Channel channel;
    
    @Override
    public void start() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port))
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
        
                    }
                });
        ChannelFuture future = null;
        try {
            future = serverBootstrap.bind().sync();
            InetSocketAddress addr = (InetSocketAddress) future.channel().localAddress();
            channel = future.channel();
            log.info("[NettyRemotingServer][服务端已启动{}]", addr);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void shutdown() {
        if (channel != null) {
            channel.close();
        }
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
