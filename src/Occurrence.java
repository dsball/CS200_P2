public class Occurrence {
	private String docName;	//Stores name of document
	private int termFrequency;	//number of times term has occurred
	
	//stores the docName and initializes the termFrequency to 1.
	public Occurrence(String name)
	{
		docName = name;
		termFrequency = 1;
	}
	//Increments term frequency
	public void incFrequency()
	{
		++termFrequency;
	}
	@Override
	public boolean equals(Object testName)
	{
		if(testName instanceof Occurrence)
		{
			if(this.docName.equals(((Occurrence)testName).docName))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		return "DOCUMENT: " + docName + " | FREQUENCY IN DOCUMENT: " + termFrequency;
	}
}
