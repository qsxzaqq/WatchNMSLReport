package cc.i9mc.watchnmslreport.netty;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


public class NioClient {
    private volatile boolean isChannelPrepared = false;
    private Channel channel;
    
    public void bind() {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {

                        @Override
                        protected void initChannel(Channel ch) {
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new StringEncoder());
                        }
                    });


            ChannelFuture f = b.connect("172.17.0.3", 8889);

            f.addListener(future -> {
                if (future.isSuccess()) {
                    isChannelPrepared = true;
                    channel = f.channel();
                    System.out.println("与服务器连接建立成功...");
                } else {
                    isChannelPrepared = false;
                    System.out.println("与服务器连接建立失败...");
                }
            });
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            isChannelPrepared = false;
            System.out.println("与服务器连接出现异常...");
        } finally {
            group.shutdownGracefully();

            System.gc();
            reConnect();
        }
    }

    private void reConnect() {
        try {
            System.out.println("与服务器连接已断开, 5秒后重连...");
            Thread.sleep(5000);
            bind();
        } catch (Exception e) {
            isChannelPrepared = false;
            e.printStackTrace();
        }
    }

    public void writeAndFlush(String data) {
        if (isChannelPrepared) {
            channel.writeAndFlush(data);
            System.out.println("发送成功!");
        } else {
            System.out.println("与服务器连接断开,无法发送数据...");
        }
    }
}
