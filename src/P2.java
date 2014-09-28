import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class P2 {
	public static void main(String[] args) throws FileNotFoundException {
		// Creating the webpages object
		WebPages newWebPage = new WebPages();
		
		//Creating the file from the arguments to read ing
		File inFile = new File(args[0]);
		
		//Creating the scaner to read in the file
		Scanner inStream = new Scanner (inFile);
		//System.out.println(inStream.next());
		
		//Boolean to determine if the file is still taking in page names
		boolean fileNamesStop = false;
		
		//This method reads in input pages until it reaches the word "*EOFs*" in the input file
		while (fileNamesStop == false)
		{
			String currentInput= inStream.next();
			if(currentInput.contentEquals("*EOFs*"))
			{
				fileNamesStop = true;
			}
			else
			{
				inStream.nextLine();
				newWebPage.addPage(currentInput);
			}	
		}
		
		//This creates an int with the number of words to prune
		int stopWordsNumber = inStream.nextInt();
		
		//This prints out the terms in the page
		newWebPage.printTerms();
		
		//This prunes the number of stop words previously determined
		newWebPage.pruneStopWords(stopWordsNumber);
		
		newWebPage.printTerms();
		
		//This is an array list to add all the numbers to check the pages for
		ArrayList<String> wordChecker = new ArrayList<String>();
		
		//This goes through and adds each search word to the array list
		while (inStream.hasNext())
		{
			String wordsToCheck = inStream.next();
			//System.out.println(wordsToCheck);
			wordChecker.add(wordsToCheck);
		}
		
		//This goes through and checks each word in the webpages
		for (int i = 0; i<wordChecker.size(); i++)
		{
			
			String[] temp = newWebPage.whichPages(wordChecker.get(i));
			if(temp == null)
			{
				System.out.println("Word not found");
			}
			else
			{
				System.out.print(wordChecker.get(i) + " in pages: ");
				for(int j = 0 ; j < temp.length ; j++)
				{
					if (j == temp.length-1)
					{
						System.out.print(temp[j]);
					}
					else
					{
						System.out.print(temp[j] + ", ");
					}
				}
				System.out.println();
			}
		}
		
		//This closes the input file
		inStream.close();
	}
}
