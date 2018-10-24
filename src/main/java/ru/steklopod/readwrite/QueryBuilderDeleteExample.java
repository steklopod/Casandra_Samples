

package ru.steklopod.readwrite;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.BuiltStatement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;

public class QueryBuilderDeleteExample {
	
	public static void main(String[] args) {
		
		Cluster cluster = Cluster.builder().withoutMetrics().addContactPoint("127.0.0.1")
				//.withCredentials("Dima", "i6XJsj!k#9").
				.build();
		
		// create session on the "hotel" keyspace
		Session session = cluster.connect("hotel");
		
		// create a Hotel ID
		String id="AZ123";
		
		// build an INSERT statement
		BuiltStatement hotelInsertBuilt = QueryBuilder.insertInto("hotels").
				value("id", id).
				value("name", "Super Hotel at WestWorld").
				value("phone", "1-888-999-9999");
		
		ResultSet hotelInsertResult = session.execute(hotelInsertBuilt);
		
		System.out.println(hotelInsertResult);
		System.out.println(hotelInsertResult.wasApplied());
		System.out.println(hotelInsertResult.getExecutionInfo());
		System.out.println(hotelInsertResult.getExecutionInfo().getIncomingPayload());
		
		// build a SELECT statement
		BuiltStatement hotelSelectBuilt = QueryBuilder.select().all().
				from("hotels").where(eq("id", id));
		
		ResultSet hotelSelectResult = session.execute(hotelSelectBuilt);
		
		// result metadata
		System.out.println(hotelSelectResult);
		System.out.println(hotelSelectResult.wasApplied());
		System.out.println(hotelSelectResult.getExecutionInfo());
		System.out.println(hotelSelectResult.getExecutionInfo().getIncomingPayload());
		
		// print results
		for (Row row : hotelSelectResult) {
			System.out.format("id: %s, name: %s, phone: %s\n", row.getString("id"), 
				row.getString("name"), row.getString("phone"));
		}
		
		// build a DELETE statement
		BuiltStatement hotelDeleteBuilt = QueryBuilder.delete().all().
				from("hotels").where(eq("id", id));
		
		ResultSet hotelDeleteResult = session.execute(hotelDeleteBuilt);
		
		// result metadata
		System.out.println(hotelSelectResult);
		System.out.println(hotelSelectResult.wasApplied());
		System.out.println(hotelSelectResult.getExecutionInfo());
		System.out.println(hotelSelectResult.getExecutionInfo().getIncomingPayload());		
		
		// close and exit
		cluster.close();
		System.exit(0);
	}
		
		
}
