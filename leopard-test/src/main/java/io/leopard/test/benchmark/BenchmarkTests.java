package io.leopard.test.benchmark;

import io.leopard.test.IntegrationTests;
import io.leopard.test.internal.BenchmarkTestContextLoader;

import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(loader = BenchmarkTestContextLoader.class)
public class BenchmarkTests extends IntegrationTests {

}
