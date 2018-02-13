package com.codeoreo.sample;

import java.io.IOException;
import java.util.Scanner;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

public class SampleMongoDBOperations {
	
	private final static String HOST = "localhost";
	private final static int PORT = 27017;
	private final static String DBNAME = "sampledb";
	public static void main(String[] args) {
		
		
		MongoClient client = new MongoClient(HOST, PORT);
		
		MongoDatabase database = client.getDatabase(DBNAME);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		System.out.println("This is a sample MongoDB test. Current Database name : sampledb");
		System.out.println("Following are the operations that you can perform");
		
		int choice = 1;
		Scanner sc = new Scanner(System.in);
		while(choice != 0)
		{
			showMainMenu(sc);
			choice = sc.nextInt();
			if(choice == 0)
			{
				continue;
			}
			switch(choice)
			{
			case 1:
				showAllCollections(database);
				break;
			case 2:
				showDataOfCollection(database, sc);
				break;
			case 3:
				insertDataInUserTable(database, sc);
			}
		}
		
		
	}
	
	private static void insertDataInUserTable(MongoDatabase db, Scanner sc) {
		
		System.out.println("'users' collection consist of 'name', 'age', and 'address' ");
		System.out.println("Please provide name : ");
		String name = sc.nextLine();
		while(name.equals(""))
		{
			name = sc.nextLine();
		}
		
		System.out.println("Please provide age : ");
		String age = sc.nextLine();
		while(age.equals(""))
		{
			age = sc.nextLine();
		}
		
		System.out.println("Please provide address : ");
		String addr = sc.nextLine();
		while(addr.equals(""))
		{
			addr = sc.nextLine();
		}
		
		BasicDBObjectBuilder builder = BasicDBObjectBuilder.start("name", name).add("age", Integer.parseInt(age)).add("address", addr);
		BasicDBObject dbobj = new BasicDBObject("name", name);
		dbobj.append("age", age);
		dbobj.append("address", addr);
		
		
		
		
		Document doc = new Document("name", name);
		doc.append("age", age);
		doc.append("address", addr);
	
		MongoCollection<Document> collection = db.getCollection("users");
		collection.insertOne(doc);
		
	}

	private static void showMainMenu(Scanner sc)
	{
		System.out.println("1. Show all Collections(Tables) available in 'sampledb'");
		System.out.println("2. Show all data in a perticular Collection(table)");
		System.out.println("3. Insert data in User table");
		System.out.println("0. Exit");
		System.out.print("Enter your choice : ");
	}
	
	private static void showAllCollections(MongoDatabase db)
	{
		MongoIterable<String> allCollectionNames = db.listCollectionNames();
		System.out.println("Collection available in Database "+db.getName()+" : ");
		for(String name : allCollectionNames)
		{
			System.out.println(name);
		}
	}
	
	private static void showDataOfCollection(MongoDatabase db , Scanner sc)
	{
		System.out.println("Please enter Collection(table) name:");
		
		
		String nextLine = sc.nextLine();
		while(nextLine.equals(""))
		{
			nextLine = sc.nextLine();
		}
		
		MongoCollection<Document> collection = db.getCollection(nextLine);
		FindIterable<Document> documents = collection.find();
		System.out.println("Data inside "+nextLine+" : ");
		for(Document doc : documents)
		{
			System.out.println(doc.toJson());
		}
		
	}
	

}
