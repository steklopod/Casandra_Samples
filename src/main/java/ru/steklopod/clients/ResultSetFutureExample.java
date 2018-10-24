

package ru.steklopod.clients;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;

import java.util.ArrayList;
import java.util.List;

public class ResultSetFutureExample implements Host.StateListener {
	
	public ResultSetFutureExample() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getHostString(Host host) {
		return new StringBuilder("Data Center: " + host.getDatacenter() +
				" Rack: " + host.getRack() + 
				" Host: " + host.getAddress()).toString() +
				" Version: " + host.getCassandraVersion() +
				" State: " + host.getState();
	}
	
	@Override
	public void onUp(Host host) {
		System.out.printf("Node is up: %s\n", getHostString(host));
	}	
	
	@Override
	public void onAdd(Host host) {
		System.out.printf("Node added: %s\n", getHostString(host));
	}

	@Override
	public void onDown(Host host) {
		System.out.printf("Node is down: %s\n", getHostString(host));
	}

	@Override
	public void onRemove(Host host) {
		System.out.printf("Node removed: %s\n", getHostString(host));
	}

	@Override
	public void onRegister(Cluster cluster) {
		// do nothing
	}

	@Override
	public void onUnregister(Cluster cluster) {
		// do nothing		
	}
	
	public static void main(String[] args) {
		
		List<Host.StateListener> list = new ArrayList<Host.StateListener>();
		list.add(new ResultSetFutureExample());
		Cluster cluster = Cluster.builder().withoutMetrics().addContactPoint("127.0.0.1")
				//.withCredentials("jeff", "i6XJsj!k#9")
				.withInitialListeners(list)
				.build();
		
		cluster.init();
		
	}
}
