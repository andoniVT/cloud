package manager;

import java.util.ArrayList;
import java.util.Arrays;

public class StopWords 
{

	
	public static String[] stopWordsofwordnet = 
		{
		"de", "la", "que", "el", "en", "y", "a", "los", "del", "se", "las", "por", "un", "para", "con", "no", 
		"una", "su", "al", "lo", "como", "mas", "pero", "sus", "le", "ya", "o", "este", "si", "porque", "esta",
		"entre", "cuando", "muy", "sin", "sobre", "tambien", "me", "hasta", "hay", "donde", "quien", "desde", 
		"todo", "nos", "durante", "todos", "uno", "les", "ni", "contra", "otros", "ese", "eso", "ante", 
		"ellos", "e", "esto", "mi", "antes", "algunos", "que", "unos", "yo", "otro", "otras", "otra", "el", 
		"tanto", "esa", "estos", "mucho", "quienes", "nada", "muchos", "cual", "poco", "ella", "estar", 
		"estas", "algunas", "algo", "nosotros", "mi", "mis", "tu", "te", "ti", "tu", "tus", "ellas", 
		"nosotras", "vosostros", "vosostras", "os", "mio", "mia", "mios", "mias", "tuyo", "tuya", "tuyos",
		"tuyas", "suyo", "suya", "suyos", "suyas", "nuestro", "nuestra", "nuestros", "nuestras", "vuestro", 
		"vuestra", "vuestros", "vuestras", "esos", "esas", "estoy", "estas", "esta", "estamos", "estais",
		"estan", "este", "estes", "estemos", "esteis", "esten", "estare", "estaras", "estara", "estaremos", 
		"estareis", "estaran", "estaria", "estarias", "estariamos", "estariais", "estarian", "estaba", 
		"estabas", "estabamos", "estabais", "estaban", "estuve", "estuviste", "estuvo", "estuvimos", 
		"estuvisteis", "estuvieron", "estuviera", "estuvieras", "estuvieramos", "estuvierais", "estuvieran", 
		"estuviese", "estuvieses", "estuviesemos", "estuvieseis", "estuviesen", "estando", "estado", "estada",
		"estados", "estadas", "estad", "he", "has", "ha", "hemos", "habeis", "han", "haya", "hayas", "hayamos",
		"hayais", "hayan", "habre", "habras", "habra", "habremos", "habreis", "habran", "habria", "habrias", 
		"habriamos", "habriais", "habrian", "habia", "habias", "habiamos", "habiais", "habian", "hube", 
		"hubiste", "hubo", "hubimos", "hubisteis", "hubieron", "hubiera", "hubieras", "hubieramos", 
		"hubierais", "hubieran", "hubiese", "hubieses", "hubiesemos", "hubieseis", "hubiesen", 
		"habiendo", "habido", "habida", "habidos", "habidas", "soy", "eres", "es", "somos", "sois", 
		"son", "sea", "seas", "seamos", "seais", "sean", "sere", "seras", "sera", "seremos", 
		"sereis", "seran", "seria", "serias", "seriamos", "seriais", "serian", "era", "eras", 
		"eramos", "erais", "eran", "fui", "fuiste", "fue", "fuimos", "fuisteis", "fueron", "fuera", 
		"fueras", "fueramos", "fuerais", "fueran", "fuese", "fueses", "fuesemos", "fueseis",
		"fuesen", "sintiendo", "sentido", "sentida", "sentidos", "sentidas", "siente", "sentid", 
		"tengo", "tienes", "tiene", "tenemos", "teneis", "tienen", "tenga", "tengas", "tengamos", 
		"tengais", "tengan", "tendre", "tendras", "tendra", "tendremos", "tendreis", "tendran", 
		"tendria", "tendrias", "tendriamos", "tendriais", "tendrian", "tenia", "tenias", "teniamos", 
		"teniais", "tenian", "tuve", "tuviste", "tuvo", "tuvimos", "tuvisteis", "tuvieron", 
		"tuviera", "tuvieras", "tuvieramos", "tuvierais", "tuvieran", "tuviese", "tuvieses", 
		"tuviesemos", "tuvieseis", "tuviesen", "teniendo", "tenido", "tenida", "tenidos", "tenidas", 
		"tened", "si" , "]", "[", "}", "{", "<", ">", "?", "\"", "\\", "/", ")", "(" , "." , "," , 
		";" , ":" , "_" , "-", "=" , "0" , "1" , "2", "3", "4", "5", "6", "7", "8", "9" 				
		};
	
	public static ArrayList<String> removeStopWords(String texto)
	{
		ArrayList<String> wordsList = new ArrayList<String>();
		texto=texto.trim().replaceAll("\\s+", " ");
		String[] words = texto.split(" ");		
		for (String word : words) 
		{
			wordsList.add(word);
		}		
		for (int i = 0; i < wordsList.size(); i++) 
		{
			for (int j = 0; j < stopWordsofwordnet.length; j++) 
			{
				if (stopWordsofwordnet[j].contains(wordsList.get(i))) 
				{
					wordsList.remove(i);
				}
			}
		}
		
		/*
		String result="";
		for (String str : wordsList) 
		{
			result = result + str + " ";
		}		
		return result;*/
		return wordsList;
	}
	
	public static void main(String[] args) 
	{
		//String s = "This String Contains the stop words like is am are so this program will remove all of them at your will thanks to you for watching this tutorial";
		String s = "ciencia de la computacion";
		ArrayList<String> result = removeStopWords(s);
		System.out.print(result);		
	}	
}