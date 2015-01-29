package io.leopard.data4j.mongo;

import io.leopard.burrow.refect.FieldUtil;

import java.lang.reflect.Field;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.mongodb.gridfs.GridFS;

public class MongoImpl implements Mongo {

	private String server;
	private String database;
	private String collectionName;

	private MongoClient client;
	private DBCollection collection;
	private DB db;

	public MongoImpl() {

	}

	public MongoImpl(String server, String database, String collectionName) {
		this.server = server;
		this.database = database;
		this.collectionName = collectionName;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public void init() {
		String[] list = server.split(":");
		String host = list[0];
		int port = Integer.parseInt(list[1]);
		try {
			client = new MongoClient(host, port);
		}
		catch (UnknownHostException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		this.db = client.getDB(this.database);
		this.collection = db.getCollection(collectionName);
	}

	public void destroy() {
		this.client.close();
	}

	@Override
	public boolean insert(Object bean) {
		List<Field> fieldList = FieldUtil.listFields(bean);
		DBObject obj = new BasicDBObject();
		for (Field field : fieldList) {
			String name = field.getName();
			field.setAccessible(true);

			Object value;
			try {
				value = field.get(bean);
			}
			catch (Exception e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			obj.put(name, value);
		}

		this.collection.insert(obj);
		return true;
	}

	@Override
	public <T> T query() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean drop() {
		this.collection.drop();
		return true;
	}

	@Override
	public <T> List<T> find(IFind find, Class<T> clazz) {
		DBCursor cursor = find.find(collection);
		return this.toBeanList(cursor, clazz);
	}

	@Override
	public <T> List<T> find(DBObject ref, Class<T> clazz, int start, int size) {
		DBCursor cursor = this.collection.find(ref).skip(start).limit(size);
		return this.toBeanList(cursor, clazz);
	}

	public <T> List<T> toBeanList(DBCursor cursor, Class<T> clazz) {
		List<T> list = new ArrayList<T>();
		while (cursor.hasNext()) {
			DBObject user = cursor.next();
			T bean;
			try {
				bean = clazz.newInstance();
			}
			catch (Exception e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			@SuppressWarnings({ "unchecked" })
			Map<String, Object> map = user.toMap();
			Set<Entry<String, Object>> set = map.entrySet();

			for (Entry<String, Object> entry : set) {
				String fieldName = entry.getKey();
				Object value = entry.getValue();
				if ("_id".equals(fieldName)) {
					continue;
				}
				else if ("location".equals(fieldName)) {
					BasicDBList dbList = (BasicDBList) value;
					double x = (Double) dbList.get(0);
					double y = (Double) dbList.get(1);
					double[] geo = new double[] { x, y };
					value = geo;
				}

				try {
					Field field = clazz.getDeclaredField(fieldName);
					field.setAccessible(true);
					field.set(bean, value);
				}
				catch (Exception e) {
					System.out.println("value:" + value + " " + value.getClass().getName());
					throw new RuntimeException(e.getMessage(), e);
				}
				// System.out.println("entry:" + entry.getKey());
			}

			list.add(bean);
			// System.out.println(user.toString());
		}

		return list;
	}

	@Override
	public boolean truncate() {
		WriteResult resulst = this.collection.remove(new BasicDBObject());
		return resulst.getN() > 0;
	}

	@Override
	public boolean remove(DBObject ref) {
		WriteResult resulst = this.collection.remove(ref);
		return resulst.getN() > 0;
	}

	@Override
	public boolean remove(String fieldName, Object value) {
		return this.remove(new BasicDBObject(fieldName, value));
	}

	@Override
	public boolean update(DBObject q, DBObject o) {
		WriteResult resulst = this.collection.update(q, o);
		// System.out.println("getN:" + resulst.getN());
		return resulst.getN() > 0;
	}

	@Override
	public GridFS getGridFS(String type) {
		return new GridFS(db, "fs");
	}

}
