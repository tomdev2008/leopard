package io.leopard.web.mvc.controller;

import io.leopard.data.env.AppProperties;
import io.leopard.web4j.view.TextView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Leopard当前状态信息.
 * 
 * @author 阿海
 * 
 */
@Controller
public class LeopardStatusController {

	@Autowired
	private LeopardStatusService leopardStatusService;

	@RequestMapping(value = "/leopard/status.do")
	public TextView status() {

		StringBuilder sb = new StringBuilder();
		sb.append("###############Leopard配置###########\n");
		// sb.append("是否启用分布式session:" + SessionServiceImpl.isEnableDistributedSession() + "\n");
		sb.append("是否启用方法级别的性能监控:" + leopardStatusService.isEnablePerformanceMonitor() + "\n");
		sb.append("当前使用的配置文件:" + AppProperties.getCurrentConfigFile().getName() + "\n");

		return new TextView(sb.toString());
	}

}
