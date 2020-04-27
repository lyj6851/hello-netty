package com.myscl.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

/**
 * 处理消息的handler
 * <p>
 * <p>
 * TextWebSocketFrame 是在netty中,用于专门为websockt处理文本的对象，frame是消息的载体
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    //用户记录和管理所有客户端的channel
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String content = msg.text();
        System.out.println("接受到数据: " + content);
//        for (Channel channel : clients) {
//            channel.writeAndFlush(new TextWebSocketFrame(
//                    "[服务器在]" + LocalDateTime.now() + "，接收到消息为：" + content));
//        }
        //下面的方面和上面的一致
        clients.writeAndFlush(new TextWebSocketFrame(
                "[服务器在]" + LocalDateTime.now() + "，接收到消息为：" + content));
    }

    /**
     * 当客户端链接服务之后（打开连接）
     * 获取客户端channel,并且放到channelGroup中进行管理
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        clients.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //当触发handlerRemoved,ChannelGroup会自动移客户端的channel
        //channels.remove(ctx.channel());

        System.out.println("客户端断开,channel对应的长id:" + ctx.channel().id().asLongText());
        System.out.println("客户端断开,channel对应的短id:" + ctx.channel().id().asShortText());

    }
}
