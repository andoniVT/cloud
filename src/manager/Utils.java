package manager;

import java.util.Vector;

public class Utils 
{

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

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

}
