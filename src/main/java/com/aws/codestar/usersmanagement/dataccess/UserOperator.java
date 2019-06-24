package com.aws.codestar.usersmanagement.dataccess;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteItemResult;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.aws.codestar.usersmanagement.models.*;

public class UserOperator {

	AmazonDynamoDB dynamoDB = null;
	String tableName = null;

	public UserOperator() {

		if (dynamoDB == null)
			dynamoDB = AmazonDynamoDBClientBuilder.defaultClient();
		this.tableName = System.getenv("TABLE_NAME");
		if (this.tableName == null)
			throw new RuntimeException("Initialization Error: Dynamo DB table name is empty!");
	}

	public void createUser(String user, String password, String email) {

	}

	public int createUser(RegistrationRequest request) {
		int retValue = 500;
		PutItemRequest putRequest = new PutItemRequest();
		putRequest.setTableName(this.tableName);
		HashMap map = new HashMap();
		map.put("userName", new AttributeValue(request.getUserName()));
		map.put("password", new AttributeValue(request.getPassword()));
		map.put("emailId", new AttributeValue(request.getEmailId()));
		putRequest.setItem(map);
		System.out.println("About to insert to Dynamo");
		PutItemResult res = dynamoDB.putItem(putRequest);
		System.out.println("Successfully inserted");
		retValue = 200;
		return retValue;
	}

	public int deleteUser(TerminationRequest request) {
		int retValue = 500;
		DeleteItemRequest delRequest = new DeleteItemRequest();
		Map<String, AttributeValue> deleteMap = new HashMap<String, AttributeValue>();
		deleteMap.put("userName", new AttributeValue(fetchUser(request.getEmailId())));
		delRequest.setKey(deleteMap);
		delRequest.setTableName(this.tableName);
		DeleteItemResult returnResponse = dynamoDB.deleteItem(delRequest);
		retValue = 200;
		return retValue;
	}

	private String fetchUser(String email) {
		DynamoDB ddb = new DynamoDB(dynamoDB);
		Table table = ddb.getTable(this.tableName);
		Index index = table.getIndex(System.getenv("GSI_USERS_TABLE"));
		QuerySpec spec = new QuerySpec().withKeyConditionExpression("emailId = :email")
				.withValueMap(new ValueMap().withString(":email", email));
		ItemCollection<QueryOutcome> items = index.query(spec);
		Iterator<Item> iter = items.iterator();
		while (iter.hasNext()) {
			Item item = iter.next();
			return (String) item.get("userName");
		}
		return null;
	}

}
