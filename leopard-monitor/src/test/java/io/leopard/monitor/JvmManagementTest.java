package io.leopard.monitor;

import org.junit.Test;

public class JvmManagementTest {

	@Test
	public void JvmManagement() {
		new JvmManagement();
	}

	@Test
	public void getPeakThreadCount() {
		JvmManagement.getPeakThreadCount();
	}

	@Test
	public void getTotalStartedThreadCount() {
		JvmManagement.getTotalStartedThreadCount();
	}

	@Test
	public void getDaemonThreadCount() {
		JvmManagement.getDaemonThreadCount();
	}

	@Test
	public void findDeadlockedThreads() {
		JvmManagement.findDeadlockedThreads();
	}

	@Test
	public void findDeadlockedThreadCount() {
		JvmManagement.findDeadlockedThreadCount();
	}

	@Test
	public void getThreadCount() {
		JvmManagement.getThreadCount();
	}

	@Test
	public void getMaxHeapMemorySize() {
		JvmManagement.getMaxHeapMemorySize();
	}

	@Test
	public void getUsedHeapMemorySize() {
		JvmManagement.getUsedHeapMemorySize();
	}

	@Test
	public void getInitHeapMemorySize() {
		JvmManagement.getInitHeapMemorySize();
	}

	@Test
	public void getUsedNonHeapMemorySize() {
		JvmManagement.getUsedNonHeapMemorySize();
	}

	@Test
	public void getInitNonHeapMemorySize() {
		JvmManagement.getInitNonHeapMemorySize();
	}

	@Test
	public void getProcessId() {
		JvmManagement.getProcessId();
	}

	@Test
	public void getStartTime() {
		JvmManagement.getStartTime();
	}

	@Test
	public void getSystemLoadAverage() {
		JvmManagement.getSystemLoadAverage();
	}

	@Test
	public void getProcessCpuTime() {
		JvmManagement.getProcessCpuTime();
	}

	@Test
	public void getFreePhysicalMemorySize() {
		JvmManagement.getFreePhysicalMemorySize();
	}

	@Test
	public void getTotalSwapSpaceSize() {
		JvmManagement.getTotalSwapSpaceSize();
	}

	@Test
	public void getTotalPhysicalMemorySize() {
		JvmManagement.getTotalPhysicalMemorySize();
	}

	@Test
	public void printMemoryPool() {
		JvmManagement.printMemoryPool();
	}

	@Test
	public void printGC() {
		JvmManagement.printGC();
	}

	@Test
	public void printAllInfo() {
		JvmManagement.printAllInfo();
	}

}