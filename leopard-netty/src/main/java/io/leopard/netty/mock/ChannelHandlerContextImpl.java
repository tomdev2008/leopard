package io.leopard.netty.mock;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelProgressivePromise;
import io.netty.channel.ChannelPromise;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.EventExecutor;

import java.net.SocketAddress;

import org.apache.commons.lang.NotImplementedException;

public class ChannelHandlerContextImpl implements ChannelHandlerContext {

	private String remoteAddress;

	public ChannelHandlerContextImpl(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	@Override
	public <T> Attribute<T> attr(AttributeKey<T> key) {
		throw new NotImplementedException();
	}

	@Override
	public Channel channel() {
		return new ChannelImpl(this.remoteAddress);
	}

	@Override
	public EventExecutor executor() {
		throw new NotImplementedException();
	}

	@Override
	public String name() {
		throw new NotImplementedException();
	}

	@Override
	public ChannelHandler handler() {
		throw new NotImplementedException();
	}

	@Override
	public boolean isRemoved() {
		throw new NotImplementedException();
	}

	@Override
	public ChannelHandlerContext fireChannelRegistered() {
		throw new NotImplementedException();
	}

	@Override
	public ChannelHandlerContext fireChannelUnregistered() {
		throw new NotImplementedException();
	}

	@Override
	public ChannelHandlerContext fireChannelActive() {
		// throw new NotImplementedException();
		return null;
	}

	@Override
	public ChannelHandlerContext fireChannelInactive() {
		throw new NotImplementedException();
	}

	@Override
	public ChannelHandlerContext fireExceptionCaught(Throwable cause) {
		throw new NotImplementedException();
	}

	@Override
	public ChannelHandlerContext fireUserEventTriggered(Object event) {
		throw new NotImplementedException();
	}

	@Override
	public ChannelHandlerContext fireChannelRead(Object msg) {
		throw new NotImplementedException();
	}

	@Override
	public ChannelHandlerContext fireChannelReadComplete() {
		throw new NotImplementedException();
	}

	@Override
	public ChannelHandlerContext fireChannelWritabilityChanged() {
		throw new NotImplementedException();
	}

	@Override
	public ChannelFuture bind(SocketAddress localAddress) {
		throw new NotImplementedException();
	}

	@Override
	public ChannelFuture connect(SocketAddress remoteAddress) {
		throw new NotImplementedException();
	}

	@Override
	public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
		throw new NotImplementedException();
	}

	@Override
	public ChannelFuture disconnect() {
		throw new NotImplementedException();
	}

	@Override
	public ChannelFuture close() {
		throw new NotImplementedException();
	}

	@Override
	public ChannelFuture deregister() {
		throw new NotImplementedException();
	}

	@Override
	public ChannelFuture bind(SocketAddress localAddress, ChannelPromise promise) {
		throw new NotImplementedException();
	}

	@Override
	public ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
		throw new NotImplementedException();
	}

	@Override
	public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
		throw new NotImplementedException();
	}

	@Override
	public ChannelFuture disconnect(ChannelPromise promise) {
		throw new NotImplementedException();
	}

	@Override
	public ChannelFuture close(ChannelPromise promise) {
		throw new NotImplementedException();
	}

	@Override
	public ChannelFuture deregister(ChannelPromise promise) {
		throw new NotImplementedException();
	}

	@Override
	public ChannelHandlerContext read() {
		throw new NotImplementedException();
	}

	@Override
	public ChannelFuture write(Object msg) {
		throw new NotImplementedException();
	}

	@Override
	public ChannelFuture write(Object msg, ChannelPromise promise) {
		throw new NotImplementedException();
	}

	@Override
	public ChannelHandlerContext flush() {
		throw new NotImplementedException();
	}

	@Override
	public ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
		throw new NotImplementedException();
	}

	@Override
	public ChannelFuture writeAndFlush(Object msg) {
		System.err.println("writeAndFlush " + msg);
		return null;
	}

	@Override
	public ChannelPipeline pipeline() {
		throw new NotImplementedException();
	}

	@Override
	public ByteBufAllocator alloc() {
		throw new NotImplementedException();
	}

	@Override
	public ChannelPromise newPromise() {
		throw new NotImplementedException();
	}

	@Override
	public ChannelProgressivePromise newProgressivePromise() {
		throw new NotImplementedException();
	}

	@Override
	public ChannelFuture newSucceededFuture() {
		throw new NotImplementedException();
	}

	@Override
	public ChannelFuture newFailedFuture(Throwable cause) {
		throw new NotImplementedException();
	}

	@Override
	public ChannelPromise voidPromise() {
		throw new NotImplementedException();
	}

}
