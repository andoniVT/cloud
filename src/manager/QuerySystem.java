package manager;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

import manager.Conexion;
import manager.StopWords;
import manager.Utils;
public class QuerySystem 
{
	public static int size_vectors = 5;
	
	public static void main(String[] args) throws Exception 
	{
		String word = "ciencia de la computacion casita papel";		
		ArrayList<String> result = StopWords.removeStopWords(word);		
		Conexion.init();
			
		Vector vector_query = new Vector() , indexes = new Vector() , frequencies = new Vector();
		
		int word_index=0;
		double word_frequency=0;
		
		for(int i=0; i<result.size();i++)
		{
			Vector wordData = Conexion.getWordData(result.get(i));
			if (wordData.size()!=0)
			{
				word_index = Integer.parseInt((String)wordData.elementAt(0));
				word_frequency = Double.parseDouble((String) wordData.elementAt(1));
				indexes.add(word_index);
				frequencies.add(word_frequency);
			}							    					
		}
		
		vector_query = Utils.buildVector(indexes, frequencies, size_vectors);
		//System.out.print("Result: \n");
		//System.out.print(vector_query);
		//System.out.print("OK");
		
		Map<String , Vector > allVectors = Conexion.getAllVectors();
				
		Map<String,Double> distances = Utils.compareVectors(vector_query , allVectors);
		System.out.println(allVectors);		
		System.out.println(distances);
		
		Vector results = Conexion.queryDocs(distances, 2);
		System.out.println(results);
		
	}
}
