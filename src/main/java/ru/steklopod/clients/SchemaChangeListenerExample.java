

package ru.steklopod.clients;

import com.datastax.driver.core.*;

import java.util.ArrayList;
import java.util.List;

public class SchemaChangeListenerExample implements SchemaChangeListener {

		
		public SchemaChangeListenerExample() {
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
		
		
		public static void main(String[] args) {
			
			List<Host.StateListener> list = new ArrayList<Host.StateListener>();
			list.add(new ConnectionListenerExample());
			Cluster cluster = Cluster.builder().withoutMetrics().addContactPoint("127.0.0.1")
					//.withCredentials("Dima", "i6XJsj!k#9")
					.withInitialListeners(list)
					.build();
			
			cluster.init();
			
		}

		@Override
		public void onAggregateAdded(AggregateMetadata arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAggregateChanged(AggregateMetadata arg0,
                                       AggregateMetadata arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAggregateRemoved(AggregateMetadata arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onFunctionAdded(FunctionMetadata arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onFunctionChanged(FunctionMetadata arg0,
                                      FunctionMetadata arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onFunctionRemoved(FunctionMetadata arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onKeyspaceAdded(KeyspaceMetadata arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onKeyspaceChanged(KeyspaceMetadata arg0,
                                      KeyspaceMetadata arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onKeyspaceRemoved(KeyspaceMetadata arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onMaterializedViewAdded(MaterializedViewMetadata arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onMaterializedViewChanged(MaterializedViewMetadata arg0,
                                              MaterializedViewMetadata arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onMaterializedViewRemoved(MaterializedViewMetadata arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onRegister(Cluster arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTableAdded(TableMetadata arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTableChanged(TableMetadata arg0, TableMetadata arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTableRemoved(TableMetadata arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onUnregister(Cluster arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onUserTypeAdded(UserType arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onUserTypeChanged(UserType arg0, UserType arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onUserTypeRemoved(UserType arg0) {
			// TODO Auto-generated method stub
			
		}


}
