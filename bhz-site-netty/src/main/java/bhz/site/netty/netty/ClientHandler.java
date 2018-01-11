package bhz.site.netty.netty;


import bhz.dat.protocol.ResponseData;
import bhz.site.netty.service.DatCheckDataService;
import bhz.site.netty.utils.SpringUtil;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;


public class ClientHandler extends ChannelHandlerAdapter{
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		try {
			//通用类:ContextLoader

			DatCheckDataService datCheckDataService = (DatCheckDataService) SpringUtil.getBean("datCheckDataService");
			ResponseData resp = (ResponseData)msg;
			if(resp.getSuccess()){
				datCheckDataService.updateSync(resp.getCheckNo());
				System.out.println("checkNo: " + resp.getCheckNo() + " sync success! ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

	
}
