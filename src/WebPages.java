import java.util.ArrayList;
import java.util.regex.Pattern;

//Organizes term objects in arrayList of terms.
public class WebPages {
	private ArrayList<Term> termIndex;
	private Pattern tagPattern = Pattern.compile("<.*?>");
	private Pattern wordPattern = Pattern.compile("[^a-zA-z0-9']+");
	
	//initializes a new index of Terms
	public WebPages()
	{
		termIndex = new ArrayList<Term>();
	}
	
	/*TODO: public void addPage(String filename)
	 * reads in the page in filename, divides it into words as before and adds those words and their counts to the termIndex.
	 */
	public void addPage(String filename)
	{
		String strippedString = "";
		
		
		/*
		 * 1: parse page for individual words
		 * 		Store in alphabetical order
		 * 2: for each word, add as occurrence
		 */
		
		
	}
	
	//print 1 line per term in format: <Term><space><space><frequency>
	public void printTerms()
	{
		for(Term term : termIndex)
		{
			System.out.println(term.name + "  " + term.totalFrequency);
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
		int i = findTerm(word);
		if(i >= 0)
		{
			return termIndex.get(i).docList.toArray(new String[0]);
		}
		
		return null;
	}
	
	/*Returns index of term if found
	 *Returns -1 if not found
	 */
	private int findTerm(String word)
	{
		Term testTerm = new Term(word);
		for(int i = 0 ; i < termIndex.size(); i++)
		{
			if(termIndex.get(i).equals(testTerm))
			{
				return i;
			}
		}
		return -1;
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
