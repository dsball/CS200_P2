import java.util.LinkedList;

public class Term {
	String name;
	int docFrequency;
	int totalFrequency;
	LinkedList<Occurrence> docList = new LinkedList<Occurrence>();
	

	 //stores the word (name) and initializes the docFrequency to 0
	public Term(String name)
	{
		this.name = name.toLowerCase();
		docFrequency = 0;
		//totalFrequency = 0;
	}
	
	public int compareNum(Term other)
	{
		return (this.totalFrequency - other.totalFrequency);
	}
	
	/* increments totalFrequency and either 
	 * a) creates a new Occurrence object if there is not one with document as its docName 
	 * and increments docFrequency OR
	 * b) increments the frequency of Occurrence with document as its docName.
	 */
	public void incFrequency(String document)
	{
		totalFrequency++;
		
		Occurrence temp = new Occurrence(document);
		//Case: Occurrence exists w/ docName in docList
		//increments the frequency of Occurrence
		if(docList.contains(temp))
		{
			(docList.get(docList.indexOf(temp))).incFrequency();
		}
		//Case: Occurrence DNE w/ docName in docList
		//creates a new Occurrence object 
		//increments docFrequency
		else
		{
			docList.add(temp);
		}
	}
	
	@Override
	//Compares object name to test name, equals if same word(lower case)
	public boolean equals(Object testTerm)
	{
		if(testTerm instanceof Term)
		{
			if(this.name.equals(((Term)testTerm).name))
			{
				return true;
			}
		}
		else if(testTerm instanceof String)
		{
			if(this.name.equals((String)testTerm))
			{
				return true;
			}
		}
		return false;
	}	
	@Override
	public String toString()
	{
		return (name + "  " + docFrequency);
	}

	public String getName()
	{
		return name;
	}

	public int compareTo(Term other) 
	{
		return this.name.compareTo(other.name);
	}
}
