package com.myscl.nettyhello;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 客户端发送请求，服务端返回hello netty
 */
public class HelloServer {

    public static void main(String[] args) throws Exception {

        //主线程组，接受客户链接不做任何事情
        EventLoopGroup boosGroup = new NioEventLoopGroup();

        //从线程组 老板线程组会把任务丢给从线程组去完成任务
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            /**
             * netty 服务创建，ServerBootstrap是个启动类
             */
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //设置主从线程组
            serverBootstrap.group(boosGroup, workGroup)
                    //设置nio双向通道
                    .channel(NioServerSocketChannel.class)
                    //子处理器，用户处理workGroup
                    .childHandler(new ServerHelloInitializer());


            /**
             * 启动sever 设置端口8088 同时启动方式为同步
             */
            ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();

            /**
             * 监听关闭channel，设置同步方式
             */
            channelFuture.channel().closeFuture().sync();


        } finally {
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }


    }

}
