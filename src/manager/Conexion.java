package manager;
import java.text.SimpleDateFormat;
import java.util.Date; 
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import java.util.Vector;
import java.awt.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
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

import manager.Utils;
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
	    	  //getAllVectors();
	    	  ArrayList<String> query = new ArrayList<String>() ;
	    	  query.add("otra");
	    	  query.add("ejemplo");
	    	  getWordsFrecGenetic(query);
	    	      	  
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
	
	public static Map<String , Vector > getAllVectors()
	{
		Map<String , Vector > theResults = new HashMap<String, Vector>();
		String tableName = "Document";
		Vector <Vector> results = new Vector();
		ScanRequest scanRequest = new ScanRequest(tableName);
		ScanResult result = client.scan(scanRequest);
		//java.util.List<AttributeValue> myList = null;
		String myList = "";
		String myId ="";
		Vector Ids = new Vector();
		for(Map<String,AttributeValue> item: result.getItems())
		{							
			myId = item.get("DocumentID").getS().toString();
			Ids.add(myId);
			
			myList = item.get("DocumentVector").getS().toString();
			Vector partial = new Vector();			
			StringTokenizer st =new StringTokenizer(myList);
			while (st.hasMoreElements())
		       {
		    	   String number =(String) st.nextElement();
		    	   double numValue = Double.parseDouble(number); 
		    	   partial.add(numValue);
		       }						
			results.add(partial);			
		}		
		for(int i=0; i<Ids.size();i++)
		{
			String key = (String) Ids.elementAt(i); 
			Vector value = results.elementAt(i);	
			theResults.put(key, value);			
		}		
		System.out.println(theResults);					
		return theResults;
	}
	
	public static Vector queryDocs(Map<String,Double> distances, int N)
	{
		int aux = 0;		
		Iterator it = distances.keySet().iterator();
		Vector keys = new Vector();
		   while(it.hasNext() & aux<N)
		   {		   
			   String key = (String) it.next();
			   keys.add(key); 
			   aux++;
		   }		   
		   System.out.println(keys);
		Vector results = new Vector();
		
		for(int i=0; i<keys.size();i++)
		{
			Map<String,AttributeValue> key = new HashMap<String,AttributeValue>();
			key.put("DocumentID", new AttributeValue().withS((String) keys.elementAt(i)));
			GetItemRequest getItemRequest = new GetItemRequest()
				.withTableName("Document")
				.withKey(key)
				.withProjectionExpression("DocumentName");
			GetItemResult result = client.getItem(getItemRequest);
			for(Map.Entry<String, AttributeValue> item : result.getItem().entrySet())
			{
				AttributeValue value = item.getValue();
				String valor = value.getS().toString();
				results.add(valor);				
			}
		}
		//System.out.println(results);
		return results;		
	}
	
	public static Vector<Vector> getWordsFrecGenetic(ArrayList<String> query)
	{		
		String query_words = "";
		Vector queryWords = new Vector();
		for(int i=0; i<2; i++)
		{
			Map<String,AttributeValue> key = new HashMap<String,AttributeValue>();
			key.put("palabra", new AttributeValue().withS(query.get(i)));
			GetItemRequest getItemRequest = new GetItemRequest()
				.withTableName("ResultadoGenetico")
				.withKey(key)
				.withProjectionExpression("cadpalabra");
			GetItemResult result = client.getItem(getItemRequest);
			if (!result.toString().equals("{}"))
			{
				for(Map.Entry<String, AttributeValue> item: result.getItem().entrySet())
				{
					AttributeValue value = item.getValue();
					String words = value.getS();
					StringTokenizer st =new StringTokenizer(words);
					while(st.hasMoreElements())
					{
						String palabrita = (String) st.nextElement();
						queryWords.add(palabrita);
					}														
				}	
			}
			else
			{
				System.out.println("No hay");
			}				
		}	
		Vector<Vector> allFrecResults = new Vector();
		if(queryWords.size()!=0)
		{
			queryWords = Utils.removeDuplicateVector(queryWords);		
			Vector reducidos = new Vector();
			int size = 8 - query.size();
			for(int i=0; i<size; i++)
			{
				reducidos.add(queryWords.elementAt(i));
			}
			//System.out.println(reducidos);		
			
			for(int i=0; i<reducidos.size();i++)
			{
				Vector vec = getWordData((String) reducidos.elementAt(i));
				if(vec.size()!=0)
					allFrecResults.add(vec);			
			}	
		}
		else
		{
			System.out.println("NO existe");
		}
		//System.out.println(allFrecResults);						
		return allFrecResults;							
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
