
package ru.steklopod.integration;

import com.datastax.spark.connector.japi.CassandraJavaUtil;
import com.datastax.spark.connector.japi.rdd.CassandraTableScanJavaRDD;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

public class SparkConnectorExample {
	
	public static void main(String[] args) {
		
		SparkConf sparkConf = new SparkConf()
			.setAppName("Spark Connector Example")
			.set("spark.cassandra.connection.host", "localhost")
			.setMaster("local")
			// optionally
			.set("cassandra.username", "cassandra")     
			.set("cassandra.password", "cassandra");

		JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);
		
	    CassandraTableScanJavaRDD<String> usersRdd =
	        CassandraJavaUtil.javaFunctions(sparkContext)
	            .cassandraTable("my_keyspace", "user", 
	            		CassandraJavaUtil.mapColumnTo(String.class))
	           .select("last_name");
	    //usersRdd.collect();
	    
	    System.out.println("Number of users: " + usersRdd.count());
	}

}
