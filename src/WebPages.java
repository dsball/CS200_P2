//-------------------------------------------------------------------------------------------------
//mergeSortAlpha() and mergeSortNum() are implementations from
//Data Abstraction and Problem Solving with Java, 3rd edition, 2011, Frank Carrano, Janet Prichard.
//pp 529-531.
//-------------------------------------------------------------------------------------------------

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
				if(termIndex.isEmpty())
				{
					termIndex.add(tempTerm);
				}
				else if(i > termIndex.size()-1)
				{
					termIndex.add(tempTerm);
				}
				else if(termIndex.get(i).equals(tempTerm))
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
		mergeSortNum(0, termIndex.size());
		for(int i = 0 ; i < n ; i++)
		{
			termIndex.remove(i);
		}
		int low = 0;
		int high = termIndex.size();
		mergeSortAlpha(low, high);
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
	private void mergeSortAlpha(int low, int high)
	{
		//Check to see if low is smaller than high, if it is not
		//the array is sorted
		if (low<high)
		{
			//This finds the middle of the arraylist
			int middle = (low + high) / 2;
			//Sort the left side of the arraylist
			mergeSortAlpha(low, middle);
			//Sort the right side of he arraylist
			mergeSortAlpha(middle+1, high);
			//Combine the two halves
			mergeAlpha(low, middle, high);
		}
	}
	
	private void mergeAlpha(int low, int mid, int high)
	{
		ArrayList<Term> temporaryStorageForMergeSort = new ArrayList<Term>();
		//Copy all the terms into the helper array
		for (int i = low; i < high; i++)
		{
			if (temporaryStorageForMergeSort.size()==0)
			{
				temporaryStorageForMergeSort.add(termIndex.get(i));
			}
			else
			{
				temporaryStorageForMergeSort.set(i, termIndex.get(i));
			}
		}
		int i = low;
		int j = mid+1;
		int k = low;
		
		//Copy smaller values to the left and right side back
		//into the original array
		while (i<= mid && j<= high)
		{
			if (j==high)
			{
				termIndex.add(k, temporaryStorageForMergeSort.get(i));
			}
			else
			{
				if (temporaryStorageForMergeSort.get(i).compareTo(temporaryStorageForMergeSort.get(j))<=0)
				{
					termIndex.set(k, temporaryStorageForMergeSort.get(i));
					i++;
				}
				else
				{
					termIndex.set(k, temporaryStorageForMergeSort.get(j));
					j++;
				}
			}
			k++;
		}
		
		
		//Copy the rest of the left side of the array into the original
		//arraylist
		while (i <= mid)
		{
			termIndex.set(k, temporaryStorageForMergeSort.get(i));
			k++;
			i++;
		}
	}
	
	/*TODO: Implement mergeSortNum
	 * sort ascending by total frequency using mergesort algorithm
	 */
	private void mergeSortNum(int low, int high)
	{
		//Check to see if low is smaller than high, if it is not
		//the array is sorted
		if (low<high)
		{
			//This finds the middle of the arraylist
			int middle = (low + high) / 2;
			//Sort the left side of the arraylist
			mergeSortNum(low, middle);
			//Sort the right side of he arraylist
			mergeSortNum(middle+1, high);
			//Combine the two halves
			mergeNum(low, middle, high);
		}
	}
	
	private void mergeNum(int low, int mid, int high)
	{
		ArrayList<Term> temporaryStorageForMergeSort = new ArrayList<Term>();
		int lowA1 = low;
		int highA1 = mid;
		int lowA2 = mid+1;
		int highA2 = high;
		int i = lowA1;
		
		while((lowA1<=highA1)&&(lowA2<=highA2))
		{
			if(termIndex.get(lowA1).compareNum(lowA2)<0)
			{
				
			}
				
		}
		
	}
	
}
