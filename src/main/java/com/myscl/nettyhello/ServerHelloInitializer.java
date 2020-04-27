package com.myscl.nettyhello;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 初始化器，channel注册后，会执行里面相应的初始化方法
 */
public class ServerHelloInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        /**
         * 通过SocketChannel获取相应的管道
         */
        ChannelPipeline pipeline = channel.pipeline();
        /**
         * 通过管道，添加handler方法
         * HttpServerCodec netty 提供的助手类，可以理解为拦截器
         * 当请求到达服务端，做解码，响应返回给客户端最编码
         */
        pipeline.addLast("HttpServerCodec", new HttpServerCodec());


        /**
         * 添加自定handler,返回hello ，netty
         */
        pipeline.addLast("CoustomHandler", new CoustomHandler());
    }
}
