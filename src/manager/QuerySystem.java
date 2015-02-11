package manager;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

import manager.Conexion;
import manager.StopWords;
import manager.Utils;


public class QuerySystem 
{	
	public static int size_vectors = 1488;
	
	public static void main(String[] args) throws Exception 
	{
		String query = "ciencia de la computacion";		
		String results = querySystem(query , 3 , false);
		System.out.println("Query: " + query);
		System.out.println("Documentos recuperados: " + results);
		System.out.println(results);
	}
	
	public static String querySystem(String query , int retrieve , boolean genetic) throws Exception
	{
		//String word = "ciencia de la computacion casita papel";		
		ArrayList<String> result = StopWords.removeStopWords(query);		
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
		
		if(genetic)
		{
			System.out.println("Genetic");
			Vector wordsGenetic =  Conexion.getWordsFrecGenetic(result);
			if(wordsGenetic.size()!=0)
			{
				for(int i=0; i<wordsGenetic.size();i++)
				{
					indexes.add(((Vector) wordsGenetic.elementAt(i)).elementAt(0));
					frequencies.add(((Vector) wordsGenetic.elementAt(i)).elementAt(1));
					vector_query = Utils.buildVector(indexes, frequencies, size_vectors);
				}		
			}											
		}
		else
		{
			vector_query = Utils.buildVector(indexes, frequencies, size_vectors);	
		}
						
		
		Map<String , Vector > allVectors = Conexion.getAllVectors();				
		Map<String,Double> distances = Utils.compareVectors(vector_query , allVectors);
		System.out.println(allVectors);		
		System.out.println(distances);
		
		Vector results = Conexion.queryDocs(distances, retrieve);
		//System.out.println(results);
		return results.toString();
	}
}
