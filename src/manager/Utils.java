package manager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Vector;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class Utils 
{
	public static void main(String[] args) 
	{
		/*Vector query = new Vector() , v1 = new Vector(), v2 = new Vector() , v3=new Vector();
		query.add(0.0) ; query.add(0.25); query.add(0.6); query.add(0.0);query.add(0.0);
		
		v1.add(0.0); v1.add(0.0); v1.add(0.25); v1.add(0.3); v1.add(0.0);
		v2.add(0.7); v2.add(0.0); v2.add(0.2); v2.add(0.1); v2.add(0.0);
		v3.add(0.04); v3.add(0.3); v3.add(0.0); v3.add(0.6); v3.add(0.1);
		
		Map<String,Vector> allVectors = new HashMap<String, Vector>();
		allVectors.put("2", v1); allVectors.put("1", v2); allVectors.put("3", v3);
		
		Map<String,Double> res = compareVectors(query , allVectors);
		System.out.println(res);*/
		
		Vector v1 = new Vector() , v2 = new Vector();
		v1.add(0.04); v1.add(0.3);v1.add(0.0);v1.add(0.6);v1.add(0.1);
		v2.add(0.7); v2.add(0.3);v2.add(0.0);v2.add(0.0);v2.add(0.1);
		//System.out.println(cosineDistance(v1 , v2));
		
		removeDuplicate("mom madam mom madam son");
		
		
	}
	
	public static double cosineDistance(Vector v1 , Vector v2)
	{
		double numerador = 0 , denominador, d1=0 , d2=0;
		for(int i=0; i<v1.size(); i++)		 						
				numerador += (double) v1.elementAt(i) * (double) v2.elementAt(i);						
		for(int i=0; i<v1.size(); i++)
		{			
			d1 += (double) v1.elementAt(i) * (double)v1.elementAt(i);
			d2 += (double) v2.elementAt(i) * (double)v2.elementAt(i);
		}
		denominador = Math.sqrt(d1) * Math.sqrt(d2);		
		return numerador/denominador;	 
	}
	
	public static Vector buildVector(Vector indexes , Vector frequencies , int size)
	{
		Vector result = new Vector();			
		for(int i=0; i<size; i++)
		{
		  result.add(0.0);
		}
		for(int i=0; i<indexes.size();i++)
		{
			int index = (int)indexes.elementAt(i);
			double frequency = (double)frequencies.elementAt(i); 
			result.set(index,frequency);			
		}
		return result;
		//System.out.print(result);		
	}
	
	public static Map<String,Double> compareVectors(Vector query , Map<String,Vector> allVectors)
	{	   
		Iterator it = allVectors.keySet().iterator();
	   Map<String,Vector> allVectorss = new HashMap<String, Vector>();
	   Map<String,Double> allDistances = new HashMap<String, Double>();	 
	   while(it.hasNext())
	   {		   
		   String key = (String) it.next();
		   Vector value = allVectors.get(key);		   
		   double distance =  cosineDistance(query , value);		   
		   allDistances.put(key, distance);		   
	   }	   
	   Map sortedMap = sortByValue(allDistances);
	   //System.out.println(sortedMap);
	   return sortedMap;
	}
	
	public static Map sortByValue(Map unsortedMap) 
	{
		Map sortedMap = new TreeMap(new ValueComparator(unsortedMap));
		sortedMap.putAll(unsortedMap);
		return sortedMap;
	}
	
	public static String removeDuplicate(String word)
	{
		int i=0,j=0,n;
		String array_word[];
		String final_string="";
		array_word=word.split(" ");
		n=array_word.length;

		for(i=0;i<n;i++)
		{
			for(j=i+1;j<n;j++)
				{
					if(array_word[i].equalsIgnoreCase(array_word[j]) && !array_word[i].equalsIgnoreCase("**"))
					{
						array_word[j]="**";  //replace repeated words by  **
					}
				}
		}

		for(i=0;i<n;i++)
		{
			if(array_word[i]!="**")
				{
					final_string=final_string+array_word[i]+" ";
				}
		}		
		//System.out.println(final_string);
		return final_string;
	}
	
	public static Vector removeDuplicateVector(Vector v)
	{
		for(int i=0;i<v.size();i++)
        {
            for(int j=0;j<v.size();j++)
            {
              if(i!=j)
                  {
            	  	if(v.elementAt(i).equals(v.elementAt(j)))
                        {
            	  			v.removeElementAt(j);
                        }

                   }
            }

        }
		return v;
	}
	
}

class ValueComparator implements Comparator {
	 
	Map map;
 
	public ValueComparator(Map map) {
		this.map = map;
	}
 
	public int compare(Object keyA, Object keyB) {
		Comparable valueA = (Comparable) map.get(keyA);
		Comparable valueB = (Comparable) map.get(keyB);
		return valueB.compareTo(valueA);
	}
}
