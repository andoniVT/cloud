package manager;
import java.text.SimpleDateFormat;
import java.util.Date; 
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;
import java.awt.List;
import java.lang.Math;
import java.util.ArrayList;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

public class Conexion 
{
	static AmazonDynamoDBClient client;
	
	public static void main(String[] args) throws Exception 
	{
		init();
	       try
	       {
	    	  String id = "200";
	    	  String tableName = "Document";
	    	  //getVector(id , tableName);
	    	  //getFrecuency(id , tableName);
	    	  //getFrecuencies(tableName);
	    	  //getWordData("celular");
	    	  getAllVectors();
	    	      	  
	       }
	       catch(AmazonServiceException ase)
	       {
	    	   System.err.println(ase.getMessage());  
	       }                
	}
	
	public static void init() throws Exception 
	{
		AWSCredentials credentials = null;
		try 
		{
           credentials = new ProfileCredentialsProvider("default").getCredentials();
        } 
		catch (Exception e) 
		{
           throw new AmazonClientException(
                   "Cannot load the credentials from the credential profiles file. " +
                   "Please make sure that your credentials file is at the correct " +
                   "location (/home/andoni/.aws/credentials), and is in valid format.",
                   e);
       }
		client = new AmazonDynamoDBClient(credentials);		
		Region usWest2 = Region.getRegion(Regions.SA_EAST_1);
		client.setRegion(usWest2);
	}
	
	
	public static Vector getWordData(String word)
	{				
		Vector finalResult = new Vector();
		Map<String,AttributeValue> key = new HashMap<String,AttributeValue>();
		key.put("WordName", new AttributeValue().withS(word));
		GetItemRequest getItemRequest = new GetItemRequest()
		.withTableName("Words")
		.withKey(key)
		.withProjectionExpression("WordIndex , WordTFIDF");		
		GetItemResult result = client.getItem(getItemRequest);		
		if (!result.toString().equals("{}"))
		{			
			for(Map.Entry<String, AttributeValue> item : result.getItem().entrySet())		
			{			
				AttributeValue value = item.getValue();					
				finalResult.add(value.getN());							
			}				
		}
		else
		{			
			System.out.println("No existe!");
		}		
		//System.out.println(finalResult);
		return finalResult;
	}
	
	public static Vector <Vector> getAllVectors()
	{
		String tableName = "Document";
		Vector <Vector> results = new Vector();
		ScanRequest scanRequest = new ScanRequest(tableName);
		ScanResult result = client.scan(scanRequest);
		java.util.List<AttributeValue> myList = null;
		for(Map<String,AttributeValue> item: result.getItems())
		{							
			myList = item.get("DocumentVector").getL();
			Vector partial = new Vector();
			for(int i=0; i<myList.size();i++)
			{				
				double val = Double.parseDouble(myList.get(i).getN().toString());  
				partial.add(val);
			}			
			results.add(partial);									
		}
		//System.out.println(results);
		return results;
	}
	
	public static Vector getVector(String id , String tableName)
	{
		Map<String,AttributeValue> key = new HashMap<String,AttributeValue>();
		key.put("DocumentID", new AttributeValue().withS(id));
		GetItemRequest getItemRequest = new GetItemRequest()
		.withTableName(tableName)
		.withKey(key)
		.withProjectionExpression("DocumentVector");				
		GetItemResult result = client.getItem(getItemRequest);		
		java.util.List<AttributeValue> myList = null;
		for(Map.Entry<String, AttributeValue> item : result.getItem().entrySet())
		{
			AttributeValue value = item.getValue();
			myList = value.getL();
		}		
		Vector vectorValue = new Vector();
		for(int i=0; i<myList.size();i++)
		{
			double value = Double.parseDouble(myList.get(i).getN());
			vectorValue.add(value);			
		}
		System.out.println(vectorValue);
		return vectorValue;
	}	
}
