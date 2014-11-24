package io.leopard.context;

import org.springframework.beans.factory.annotation.Autowired;

public class NewsServiceImpl implements NewsService {

	@Autowired(required = false)
	protected NewsDao newsDao;
	@Autowired(required = false)
	protected UserDao userDao;

	@Override
	public boolean add() {
		System.err.println("newsService newsDao:" + newsDao + " userDao:" + userDao);
		return false;
	}
}
