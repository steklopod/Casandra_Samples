

package ru.steklopod.readwrite;

import com.datastax.driver.core.*;

public class DeleteExample {
	
	public static void main(String[] args) {
		
		Cluster cluster = Cluster.builder().withoutMetrics().addContactPoint("127.0.0.1")
				//.withCredentials("jeff", "i6XJsj!k#9")
				.build();
		
		// create session on the "hotel" keyspace
		Session session = cluster.connect("hotel");
		
		// create a Hotel ID
		String id="AZ123";
		
		// create parameterized INSERT statement
		SimpleStatement hotelInsert = new SimpleStatement(
				"INSERT INTO hotels (id, name, phone) VALUES (?, ?, ?)",
				id, "Super Hotel at WestWorld", "1-888-999-9999");
		
		session.execute(hotelInsert);
		
		// create parameterized DELETE statement
		SimpleStatement hotelDelete = new SimpleStatement(
				"DELETE FROM hotels WHERE id=?", id);
		
		ResultSet hotelDeleteResult = session.execute(hotelDelete);
		
		// result metadata
		System.out.println(hotelDeleteResult);
		System.out.println(hotelDeleteResult.wasApplied());
		System.out.println(hotelDeleteResult.getExecutionInfo());
        System.out.println("num results: " + hotelDeleteResult.all().size());
		
		// print results
		for (Row row : hotelDeleteResult) {
			System.out.format("id: %s, name: %s, phone: %s\n\n", row.getString("id"), 
				row.getString("name"), row.getString("phone"));
		}
		
		// close and exit
		cluster.close();
		System.exit(0);
	}
		
		
}
