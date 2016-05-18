package com.ppk.ribbon.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractServerList;
import com.netflix.loadbalancer.Server;
import com.ppk.service.consul.DiscoverService;

public class ConsulDiscoveredServerList extends AbstractServerList<Server> {

	String					clientName;

	private DiscoverService	discoverService;

	public ConsulDiscoveredServerList(DiscoverService discoverService) {
		this.discoverService = discoverService;
	}

	@Override
	public List<Server> getInitialListOfServers() {
		return obtainServersViaDiscovery();
	}

	@Override
	public List<Server> getUpdatedListOfServers() {
		return obtainServersViaDiscovery();
	}

	@Override
	public void initWithNiwsConfig(IClientConfig clientConfig) {
		clientName = clientConfig.getClientName();
	}

	private List<Server> obtainServersViaDiscovery() {
		String servers = discoverService.discoverService(clientName);
		List<Server> serverList = new ArrayList<>();
		if (StringUtils.isEmpty(servers)) {
			return serverList;
		}
		for (String service : StringUtils.split(servers, ",")) {
			Server instance = new Server(service);
			instance.setAlive(true);
			serverList.add(instance);
		}
		return serverList;
	}

}
