/*----------------------------------------------------------
 * Authors: Irfan Lakha & Minesh Varu
 * ID: 110418360 & 110814300
 * Email: lakha8360@mylaurier.ca & varu4300@mylaurier.ca
 * Program: Client.java
 * Description: Client GUI, connects with the server and can
 *              perform SET, GET and REMOVE commands
 *----------------------------------------------------------
 */
import java.util.ArrayList;

public class Dictionary {

	private String word;
	private ArrayList <String> Synonyms = new ArrayList <String> ();
	private static ArrayList <Dictionary> Library = new ArrayList <Dictionary> ();
	
	
	// constructor for the Synonyms dictionary
	// Input: Takes in two strings - Word1 and Word2
	public Dictionary(String word1, String word2) {
		this.word = word1;
		this.Synonyms.add(word2);
	}
	
	public static ArrayList<String> getRequest (String word1){
		ArrayList <String> synonymList = new ArrayList <String>();
		
		if (Library.size() > 0){
		for (int i = 0; i < Library.size(); i++){
			// Check if the Dictionary element exists at index i
			if (Library.get(i).word.equals(word1)){
				synonymList = Library.get(i).Synonyms;
			} // else keep on looping
		}
		}
		
		return synonymList;
	}
	
	public static String setRequest (String word1, String word2){
		String Response = "The words already exist in the Synonyms dictionary";
		boolean first = false, second = false, firstExists = false, secondExists = false;
		int check = -1, check2 = -1;
		
		//check if library is empty
		if (Library.isEmpty() == false){
			//if not empty, loop through library
			for (int i = 0; i < Library.size(); i++){
				//check if a dictionary item exists where dictionary.word == word1
				if (Library.get(i).word.equals(word1)){
					//if there is a dictionary item where dictionary.word == word1;
					//loop through the synonyms list of the dictionary item and raise 
					//a flag that the item already exists. 
					first = true;
					for (int x = 0; x < Library.get(i).Synonyms.size(); x++){
						//check if word2 already exists in the synonyms list
						if (Library.get(i).Synonyms.get(x).equals(word2)){
							//raise a flag that the item already exists in Library
							firstExists = true;
						}
						//if it doesn't exist, add it
					} if (firstExists == false){
						Library.get(i).Synonyms.add(word2);
						check = i;
					}
						
				} if (Library.get(i).word.equals(word2)){
					//if there is a dictionary item where dictionary.word == word2;
					//loop through the synonyms list of the dictionary item and raise 
					//a flag that the item already exists. 
					second = true;
					//loop through the synonyms list to check if word1 exists in the Synonyms list
					for (int y = 0; y < Library.get(i).Synonyms.size(); y++){
						if (Library.get(i).Synonyms.get(y).equals(word1)){
							secondExists = true;
						}
						//if it doesn't exist in the list, add it 
					} if (secondExists == false) {
						Library.get(i).Synonyms.add(word1);
						check2 = i;
					}
					
				} else {
					for (int z = 0; z < Library.get(i).Synonyms.size(); z++){
						if (Library.get(i).Synonyms.get(z).equals(word2)){
							if (check != i){
								Library.get(i).Synonyms.add(word1);
							}
						}if (Library.get(i).Synonyms.get(z).equals(word1)){
							if (check2 != i){
								Library.get(i).Synonyms.add(word2);
							}
						}
					}
				}
			}
		}

		if (first == false){
			Dictionary a = new Dictionary (word1, word2);
			Library.add(a);
			Response = "The words (" + word1 + ", " + word2 + ") have been added to the Synonyms dictionary";
		}
		
		if (second == false){
			Dictionary b = new Dictionary (word2, word1);
			Library.add(b);
			Response = "The words (" + word1 + ", " + word2 + ") have been added to the Synonyms dictionary";
		}
		
		return Response;
	}
	
	public static String removeRequest (String word1){
		String response = word1 + "is not in the Synonyms dictionary";
		
		if (Library.size() > 0){
		for (int i = 0; i < Library.size(); i++){
			if (Library.get(i).word.equals(word1)){
				Library.remove(i);
				response = "removed " + word1 + "from the synonyms dictionary";
			} else {
				for (int x = 0; x < Library.get(i).Synonyms.size(); x++){
					if (Library.get(i).Synonyms.get(x).equals(word1)){
						Library.get(i).Synonyms.remove(x);
						response = "removed " + word1 + "from the synonyms dictionary";
					}
				} 
			}
		}
		}
		return response;
	}
}
