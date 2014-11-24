package io.leopard.netty.mock;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelProgressivePromise;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.net.SocketAddress;

import org.apache.commons.lang.NotImplementedException;

public class ChannelImpl implements Channel {
	private String remoteAddress;

	public ChannelImpl(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	@Override
	public <T> Attribute<T> attr(AttributeKey<T> key) {
		throw new NotImplementedException();
	}

	@Override
	public int compareTo(Channel o) {
		throw new NotImplementedException();
	}

	@Override
	public EventLoop eventLoop() {
		throw new NotImplementedException();
	}

	@Override
	public Channel parent() {
		throw new NotImplementedException();
	}

	@Override
	public ChannelConfig config() {
		throw new NotImplementedException();
	}

	@Override
	public boolean isOpen() {
		throw new NotImplementedException();
	}

	@Override
	public boolean isRegistered() {
		throw new NotImplementedException();
	}

	@Override
	public boolean isActive() {
		throw new NotImplementedException();
	}

	@Override
	public ChannelMetadata metadata() {
		throw new NotImplementedException();
	}

	@Override
	public SocketAddress localAddress() {
		throw new NotImplementedException();
	}

	@Override
	public SocketAddress remoteAddress() {
		return new SocketAddressImpl(this.remoteAddress);
	}

	@Override
	public ChannelFuture closeFuture() {
		throw new NotImplementedException();
	}

	@Override
	public boolean isWritable() {
		throw new NotImplementedException();
	}

	@Override
	public Unsafe unsafe() {
		throw new NotImplementedException();
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
	public Channel read() {
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
	public Channel flush() {
		throw new NotImplementedException();
	}

	@Override
	public ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
		throw new NotImplementedException();
	}

	@Override
	public ChannelFuture writeAndFlush(Object msg) {
		throw new NotImplementedException();

	}

}
