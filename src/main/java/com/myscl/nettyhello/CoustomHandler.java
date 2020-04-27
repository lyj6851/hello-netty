package com.myscl.nettyhello;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * 创建自定义处理器
 * <p>
 * <p>
 * <p>
 * SimpleChannelInboundHandler对于请求来，就是相当于【入站，入境】
 */
public class CoustomHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject httpObject) throws Exception {
        //获取channel
        Channel channel = ctx.channel();

        if (httpObject instanceof HttpRequest) {
            //获取远程客户端地址
            System.out.println(channel.remoteAddress());

            //定义发送的消息
            ByteBuf content = Unpooled.copiedBuffer("hello netty", CharsetUtil.UTF_8);

            //构建一个http response
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);

            //为响应添加数据类型和长度
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            //把响应刷到客户端
            ctx.writeAndFlush(response);
        }


    }
}
