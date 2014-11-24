package io.leopard.data.env;

import java.io.IOException;
import java.util.Properties;

public interface ProjectInfoDao {

	String getCode();

	Properties load() throws IOException;
}
