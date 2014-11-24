package io.leopard.data.mongo;

import io.leopard.data.mongo.MongoImpl.IFind;

import java.util.List;

import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;

public interface Mongo {

	boolean insert(Object bean);

	<T> T query();

	boolean remove(DBObject ref);

	boolean remove(String fieldName, Object value);

	<T> List<T> find(DBObject ref, Class<T> clazz, int start, int size);

	boolean drop();

	boolean truncate();

	<T> List<T> find(IFind find, Class<T> clazz);

	boolean update(DBObject q, DBObject o);
	
	GridFS getGridFS(String type);

}
