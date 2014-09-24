import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

//Organizes term objects in arrayList of terms.
public class WebPages {
	private ArrayList<Term> termIndex;
	
	//initializes a new index of Terms
	public WebPages()
	{
		termIndex = new ArrayList<Term>();
	}
	

	 /*parse file referenced by filename into individual words
	  *adds those words and their counts to the termIndex.
	 */
	public void addPage(String filename)
	{
		//<.*?>match a set of closed html brackets with unlimited content as a delimiter
		Pattern tagPattern = Pattern.compile("<.*?>");
		
		//"[^a-zA-z0-9']" sets delimiter as any character NOT listed.
		Pattern wordPattern = Pattern.compile("[^a-zA-z0-9']+");
		
		//String containing file stripped of HTML tags
		String strippedString = "";
		
		String tempWord = "";
		Term tempTerm = null;
				
		try
		{	
			Scanner inStream = new Scanner (new File(filename));
			inStream.useDelimiter(tagPattern);
			/*Strip code segments from file and create new temporary string*/
			if(!inStream.hasNext())
			{
				System.err.println("Error: Empty File");
			}
			while(inStream.hasNext())
			{
				strippedString += (inStream.next() + "");
			}
			inStream.close();
			
			/* Parse strippedString for words. Add each to termIndex. */
			Scanner cleanScan = new Scanner(strippedString);
			cleanScan.useDelimiter(wordPattern);
			while(cleanScan.hasNext())
			{
				tempWord = cleanScan.next().toLowerCase();
				
				//find if word exists or not
				//determine index of Term or insertion point
				tempTerm = new Term(tempWord);
				int i = findClosest(new Term(tempWord), 0, termIndex.size() - 1 );
				
				//increment or create new term as necessary
				if( termIndex.get(i).equals(tempTerm))
				{
					termIndex.get(i).incFrequency(filename);
				}
				else
				{	
					termIndex.add(i, tempTerm);
				}
			}
			cleanScan.close();
			
		}
		catch(IOException e)
		{
			System.err.println("Error: " + e);
			System.err.println("Input file inaccessible.");
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.err.println("Error: " + e);
			System.err.println("No input arguments were supplied.");
		}		
	}
	
	//print terms, 1 line per term
	public void printTerms()
	{
		for(Term term : termIndex)
		{
			System.out.println(term.toString());
		}
	}
	
	/*Sort in order of highest to lowest rate of occurrence
	 * remove n highest occurrences
	 * Sort in alphabetical order by term
	 */
	public void pruneStopWords(int n)
	{
		mergeSortNum();
		for(int i = 0 ; i < n ; i++)
		{
			termIndex.remove(i);
		}
		mergeSortAlpha();
	}
	
	/* Search for word in termIndex.
	 * If exists:
		* return array with document names from Term for "word"
	 * else return null
	 */
	public String[] whichPages(String word)
	{
		Term tempTerm = new Term(word);
		int i = findClosest(tempTerm, 0, termIndex.size()-1);
		if(termIndex.get(i).equals(tempTerm))
		{
			return termIndex.get(i).docList.toArray(new String[0]);
		}
		return null;
	}
	
	//Binary Search.
	//Returns the index of the value if found
	//Returns the index where the term should be inserted if not found
	private int findClosest(Term tempTerm, int first, int last)
	{
		//If list is empty, first index is 0
		if(termIndex.isEmpty())
		{
			return 0;
		}
		else
		{
			int mid = (first+last)/2;
			int test = tempTerm.compareTo(termIndex.get(mid));
			if(test == 0)
			{
				return mid;
			}
			//Recursive case: sub-array size > 1
			else if (first < last)
			{
				if(test < 0)
				{
					return findClosest(tempTerm, first, mid-1);
				}
				else
				{
					return findClosest(tempTerm, mid+1, last);
				}
			}
			//Base case: sub-array size <= 1
			else
			{
				//If value to be inserted is less than mid, return mid
				//If value to be inserted is greater than mid, return mid+1
				//Ensures point of insertion will be returned
				if(test < 0)
				{
					return mid;
				}
				else
				{
					return mid+1;
				}
			}
		}
	}
	
	/*TODO: Implement mergeSortAlpha and mergeSortNum
	 *sort ascending alphabetically by name using mergesort algorithm
	 */
	private void mergeSortAlpha()
	{
		
	}
	
	/*TODO: Implement mergeSortNum
	 * sort ascending by total frequency using mergesort algorithm
	 */
	private void mergeSortNum()
	{
		
	}
	
}
