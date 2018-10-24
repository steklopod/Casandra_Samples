

package ru.steklopod.readwrite;

import com.datastax.driver.core.*;

public class PagingExample {
	
	public static void main(String[] args) {
		
		Cluster cluster = Cluster.builder().withoutMetrics().addContactPoint("127.0.0.1")
				//.withCredentials("Dima", "i6XJsj!k#9")
				.build();
		
		// create session on the "hotel" keyspace
		Session session = cluster.connect("hotel");
		
		// create parameterized INSERT statement
		SimpleStatement hotelInsert = new SimpleStatement(
				"INSERT INTO hotels (id, name, phone) VALUES (?, ?, ?)",
				"AZ123", "Super Hotel at WestWorld", "1-888-999-9999");
		
		ResultSet hotelInsertResult = session.execute(hotelInsert);
		
		System.out.println(hotelInsertResult);
		System.out.println(hotelInsertResult.wasApplied());
		System.out.println(hotelInsertResult.getExecutionInfo());
		System.out.println(hotelInsertResult.getExecutionInfo().getIncomingPayload());
		
		// create parameterized SELECT statement
		SimpleStatement hotelSelect = new SimpleStatement(
				"SELECT * FROM hotels WHERE id=?", "AZ123");	
		
		ResultSet hotelSelectResult = session.execute(hotelSelect);
		
		// result metadata
		System.out.println(hotelSelectResult);
		System.out.println(hotelSelectResult.wasApplied());
		System.out.println(hotelSelectResult.getExecutionInfo());
		System.out.println(hotelSelectResult.getExecutionInfo().getIncomingPayload());
		System.out.println(hotelSelectResult.getExecutionInfo().getQueryTrace());
		
		// print results
		for (Row row : hotelSelectResult) {
			System.out.format("id: %s, name: %s, phone: %s\n\n", row.getString("id"), row.getString("name"), row.getString("phone"));
		}
		
		// close and exit
		cluster.close();
		System.exit(0);
	}
		
		
}
