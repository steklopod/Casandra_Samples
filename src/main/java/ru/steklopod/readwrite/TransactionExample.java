

package ru.steklopod.readwrite;

import com.datastax.driver.core.*;

public class TransactionExample {
	
	public static void main(String[] args) {
		
		Cluster cluster = Cluster.builder().withoutMetrics().addContactPoint("127.0.0.1")
				//.withCredentials("Dima", "i6XJsj!k#9")
				.build();
		
		// create session on the "hotel" keyspace
		Session session = cluster.connect("hotel");
		
		// create parameterized INSERT statement
		SimpleStatement hotelInsert = new SimpleStatement(
				"INSERT INTO hotels (id, name, phone) VALUES (?, ?, ?) IF NOT EXISTS",
				"AZ123", "Super Hotel at WestWorld", "1-888-999-9999");
		
		ResultSet hotelInsertResult = session.execute(hotelInsert);
		
		// result metadata
		System.out.println(hotelInsertResult);
		System.out.println(hotelInsertResult.wasApplied());
		System.out.println(hotelInsertResult.getExecutionInfo());
		System.out.println(hotelInsertResult.getExecutionInfo().getQueryTrace());
		
		// print results
		for (Row row : hotelInsertResult) {
			System.out.format("id: %s, name: %s, phone: %s\n\n", row.getString("id"), row.getString("name"), row.getString("phone"));
		}
		
		// close and exit
		cluster.close();
		System.exit(0);
	}
		
		
}
