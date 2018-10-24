

package ru.steklopod.performance;

import com.datastax.driver.core.*;

import java.text.SimpleDateFormat;

public class QueryTraceExample {
	
	public static void main(String[] args) {
		
		Cluster cluster = Cluster.builder().withoutMetrics().addContactPoint("127.0.0.1")
				//.withCredentials("jeff", "i6XJsj!k#9")
				.build();
		
		// create session on the "hotel" keyspace
		Session session = cluster.connect("hotel");
		
		// create parameterized SELECT statement
		SimpleStatement hotelSelect = new SimpleStatement(
				"SELECT * FROM hotels WHERE id=AZ123");
		hotelSelect.enableTracing();
		
		ResultSet hotelSelectResult = session.execute(hotelSelect);
		
		// result metadata
		System.out.println(hotelSelectResult);
		System.out.println(hotelSelectResult.wasApplied());
		System.out.println(hotelSelectResult.getExecutionInfo());
		System.out.println(hotelSelectResult.getExecutionInfo().getIncomingPayload());
		System.out.println(hotelSelectResult.getExecutionInfo().getQueryTrace());
		
		// print results
		for (Row row : hotelSelectResult) {
			System.out.format("id: %s, name: %s, phone: %s\n\n", row.getUUID("id"), row.getString("name"), row.getString("phone"));
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
		QueryTrace queryTrace = hotelSelectResult.getExecutionInfo().getQueryTrace();
		System.out.printf("Trace id: %s\n\n", queryTrace.getTraceId());
		System.out.printf("%-42s | %-12s | %-10s \n", "activity",
		   "timestamp", "source");
		System.out.println("-------------------------------------------+--------------+------------");
		      
		for (QueryTrace.Event event : queryTrace.getEvents()) {
		  System.out.printf("%42s | %12s | %10s\n",     
		     event.getDescription(),
		     dateFormat.format((event.getTimestamp())),
		     event.getSource());
		}

		// close and exit
		cluster.close();
		System.exit(0);
	}
		
		
}

