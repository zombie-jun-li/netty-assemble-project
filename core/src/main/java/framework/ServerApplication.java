package framework;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * Created by jun.
 */
@SuppressWarnings("PMD.AccessorClassGeneration")
public class ServerApplication {
    private Integer port;

    private final ServerBootstrap serverBootstrap = new ServerBootstrap();

    private final ChannelHandler[] channelHandlers;

    private EventLoopGroup parentGroup;

    private EventLoopGroup childGroup;

    private ServerApplication(ServerApplicationBuilder serverApplicationBuilder) {
        this.port = serverApplicationBuilder.port;
        this.channelHandlers = serverApplicationBuilder.channelHandlers;
        this.parentGroup = serverApplicationBuilder.parentGroup;
        this.childGroup = serverApplicationBuilder.childGroup;
    }

    private void init() {
        if (null == port) {
            port = 8080;
        }
        if (null == parentGroup) {
            parentGroup = new NioEventLoopGroup();
        }
        if (null == childGroup) {
            childGroup = new NioEventLoopGroup();
        }
        serverBootstrap.group(parentGroup, childGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(configChannelInitializer())
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
    }

    private ChannelHandler configChannelInitializer() {
        return new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new OutboundServerHandler());
                ch.pipeline().addLast(new HttpResponseEncoder());

                ch.pipeline().addLast(new HttpRequestDecoder());
                ch.pipeline().addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
                ch.pipeline().addLast(new InboundServerHandler());
                if (null != channelHandlers) {
                    ch.pipeline().addLast(channelHandlers);
                }
            }
        };
    }

    public void run() {
        try {
            init();

            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();

            channelFuture.channel().closeFuture().sync();
        } catch (Throwable ex) {
            throw new IllegalStateException(ex);
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }

    public static class ServerApplicationBuilder {
        private EventLoopGroup parentGroup;
        private EventLoopGroup childGroup;
        private ChannelHandler[] channelHandlers;
        private Integer port;

        public ServerApplicationBuilder setParentGroup(EventLoopGroup parentGroup) {
            this.parentGroup = parentGroup;
            return this;
        }

        public ServerApplicationBuilder setChildGroup(EventLoopGroup childGroup) {
            this.childGroup = childGroup;
            return this;
        }

        public ServerApplicationBuilder setChannelHandlers(ChannelHandler ...channelHandlers) {
            this.channelHandlers = channelHandlers;
            return this;
        }

        public ServerApplicationBuilder setPort(Integer port) {
            this.port = port;
            return this;
        }

        private ServerApplication build() {
            return new ServerApplication(this);
        }

        public void run() {
            build().run();
        }
    }

}
