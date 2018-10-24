
package ru.steklopod.readwrite;

import com.datastax.driver.core.*;

import java.text.SimpleDateFormat;

// used for query tracing, if desired

public class BatchStatementExample {
	
	public static void main(String[] args) {
		
		Cluster cluster = Cluster.builder().withoutMetrics().addContactPoint("127.0.0.1")
				//.withCredentials("Dima", "i6XJsj!k#9")
				.build();
		
		// create session on the "hotel" keyspace
		Session session = cluster.connect("hotel");
		
		// create a Hotel ID
		String id="AZ123";
		
		// create parameterized INSERT statement
		SimpleStatement hotelInsert = new SimpleStatement(
				"INSERT INTO hotels (id, name, phone) VALUES (?, ?, ?)",
				id, "Super Hotel at WestWorld", "1-888-999-9999");
		SimpleStatement hotelsByPoiInsert = new SimpleStatement(
				"INSERT INTO hotels_by_poi (poi_name, id, name, phone) VALUES (?, ?, ?, ?)",
				"WestWorld", id, "Super Hotel at WestWorld", "1-888-999-9999");
		
		BatchStatement hotelBatch = new BatchStatement();
		hotelBatch.add(hotelsByPoiInsert);
		hotelBatch.add(hotelInsert);
		
		ResultSet hotelInsertResult = session.execute(hotelBatch);
		
		System.out.println(hotelInsertResult);
		System.out.println(hotelInsertResult.wasApplied());
		System.out.println(hotelInsertResult.getExecutionInfo());
		System.out.println(hotelInsertResult.getExecutionInfo().getIncomingPayload());
		
		// create parameterized SELECT statement
		SimpleStatement hotelSelect = new SimpleStatement(
				"SELECT * FROM hotels WHERE id=?", id);
		
		// Optional - remove if not interested in tracing behavior (see Chapter 12)
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
			System.out.format("id: %s, name: %s, phone: %s\n\n", row.getString("id"), 
				row.getString("name"), row.getString("phone"));
		}
		
		// Optional - remove if not interested in tracing behavior (see Chapter 12)
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
