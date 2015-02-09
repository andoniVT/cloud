package manager;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Vector;

public class Utils 
{
	public static void main(String[] args) 
	{
		Vector query = new Vector() , allVectors = new Vector(), v1 = new Vector(), v2 = new Vector() , v3=new Vector();
		query.add(0.25); query.add(0.6);
		
		v1.add(0.0); v1.add(0.0); v1.add(0.25); v1.add(0.3); v1.add(0.0);
		v2.add(0.7); v2.add(0.0); v2.add(0.2); v2.add(0.1); v2.add(0.0);
		v3.add(0.04); v3.add(0.3); v3.add(0.0); v3.add(0.6); v3.add(0.1);
		
		allVectors.add(v1); allVectors.add(v2); allVectors.add(v3);
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
		return 1 - (numerador/denominador);	 
	}
	
	public static Vector buildVector(Vector indexes , Vector frequencies , int size)
	{
		Vector result = new Vector();			
		for(int i=0; i<size; i++)
		{
		  result.add(0);
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
	
	public static void compareVectors(Vector query , Vector<Vector> allVectors)
	{
	   Vector result;	
	}
	
	public static String deDup(String s) {
	    return new LinkedHashSet<String>(Arrays.asList(s.split("-"))).toString().replaceAll("(^\\[|\\]$)", "").replace(", ", "-");
	}
}
