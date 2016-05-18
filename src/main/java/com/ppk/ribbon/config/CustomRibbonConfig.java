package com.ppk.ribbon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ServerList;
import com.ppk.service.consul.DiscoverService;

public class CustomRibbonConfig {
	private DiscoverService discoverService;

	@Autowired
	public CustomRibbonConfig(DiscoverService discoverService) {
		this.discoverService = discoverService;
	}

	@Bean
	public ServerList<?> cwlServerList(IClientConfig config) {
		ConsulDiscoveredServerList serverList = new ConsulDiscoveredServerList(discoverService);
		serverList.initWithNiwsConfig(config);
		return serverList;
	}
}
