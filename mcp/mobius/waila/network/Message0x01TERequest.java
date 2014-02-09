package mcp.mobius.waila.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class Message0x01TERequest extends SimpleChannelInboundHandler<Message0x01TERequest> implements IWailaMessage {
	
	public int dim;
	public int posX;
	public int posY;
	public int posZ;	
	
	public Message0x01TERequest(){}	
	
	public Message0x01TERequest(TileEntity ent){
		this.dim  = ent.getWorldObj().provider.dimensionId;
		this.posX = ent.xCoord;
		this.posY = ent.yCoord;
		this.posZ = ent.zCoord;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, IWailaMessage msg, ByteBuf target) throws Exception {
		target.writeInt(dim);
		target.writeInt(posX);
		target.writeInt(posY);
		target.writeInt(posZ);		
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf dat, IWailaMessage rawmsg) {
		Message0x01TERequest msg = (Message0x01TERequest)rawmsg;
		msg.dim  = dat.readInt();
		msg.posX = dat.readInt();
		msg.posY = dat.readInt();
		msg.posZ = dat.readInt();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message0x01TERequest msg) throws Exception {
		ctx.writeAndFlush(new Message0x02TENBTData()).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
	}
}