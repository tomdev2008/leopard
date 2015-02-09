package io.leopard.web4j.trynb;

import java.io.IOException;
import java.util.List;

import io.leopard.web4j.trynb.model.ErrorConfig;

public interface ErrorPageDao {

	List<ErrorConfig> list();
}
