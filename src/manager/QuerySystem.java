package manager;
import java.util.ArrayList;
import java.util.Vector;

import manager.Conexion;
import manager.StopWords;

public class QuerySystem 
{
	public static void main(String[] args) throws Exception 
	{
		String word = "ciencia de la computacion casita";		
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
		System.out.print(indexes);
		System.out.print("\n");
		System.out.print(frequencies);
		
		
		
		
		
	}
}
