package geekCode03.testGateWay.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import java.util.List;

public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {
	
	private List<String> proxyServer;
	
	public HttpInboundInitializer(List<String> proxyServer) {
		this.proxyServer = proxyServer;
	}
	
	@Override
	public void initChannel(SocketChannel ch) {
		//一个Channel就是一个联络Socket的通道，通过Channel，你可以对Socket进行各种操作。
		// 每个Channel都有一个属于自己的Pipeline
		ChannelPipeline p = ch.pipeline();
//		if (sslCtx != null) {
//			p.addLast(sslCtx.newHandler(ch.alloc()));
//		}
		p.addLast(new HttpServerCodec());
		//p.addLast(new HttpServerExpectContinueHandler());
		p.addLast(new HttpObjectAggregator(1024 * 1024));
		p.addLast(new HttpInboundHandler(this.proxyServer));
	}
}
