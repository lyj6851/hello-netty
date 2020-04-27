package com.myscl.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WsServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        //通过SocketChannel获取相应的管道
        ChannelPipeline pipeline = channel.pipeline();

        //websocket 基于Http协议，需要http编解码器
        pipeline.addLast(new HttpServerCodec());

        //对写大数据流支持
        pipeline.addLast(new ChunkedWriteHandler());

        /**
         * 对HttpMessage进行聚合，聚合成 FullHttpRequest 或 FullHttpReponse
         * 几乎在netty编程中都会使用到的handler
         */
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));
        //====================以上是用户http协议的支持================================


        /**
         * websocket服务处理的协议，用于指定给客户端连接访问的路由： /ws
         * 本handler会帮你处理一些复杂繁重的工作
         * 会帮你处理握手动作 handsharking（close,ping.pong） ping+pong=心跳
         * 对于socket来讲，都是以frame方式传输，不同数据类型对应的frame也不同
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        //自定义handler处理客户端传送过来的消息处理
        pipeline.addLast(new ChatHandler());
    }
}
