package io.leopard.web.security;

public interface CsrfDao {

	// public static final String publicKey = "csrfTokenKeyxx123";

	void checkToken(String user, String token);
}
