import java.util.*;
import java.util.Map.Entry;
import java.io.*;

public class ProviderDB
{
	private static TreeMap<String, Person> directory;		//tree based on provider name
	private static TreeMap<Integer, Person>	directory1;		//tree based on provider number
	private static Scanner in = null;
	 
	public ProviderDB()
	{
		directory = new TreeMap<String, Person>();
		directory1 = new TreeMap<Integer, Person>();
	}
	public ProviderDB(String fileName) throws FileNotFoundException
	{
		File inFile = new File(fileName);
		try {
			in = new Scanner(inFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw e;//.printStackTrace();
		}
		
		in.useDelimiter(";");
		directory = new TreeMap<String, Person>();
		directory1 = new TreeMap<Integer, Person>();
		
		while(in.hasNext())
		{	
			Person temp = new Providers();
			String name = in.next();
			String ID = in.next();
			String address = in.next();
			String city = in.next();
			String state = in.next();
			String zip = in.next();
			
			temp.SetName(name);
			Integer Integerid = new Integer(ID);
			temp.SetID(Integerid);
			temp.SetAddress(address);
			temp.SetCity(city);
			temp.SetState(state);
			Integer Integerzip = new Integer(zip);
			temp.SetZip(Integerzip);
			
			
			directory.put(temp.GetName(), temp);
			directory1.put(temp.GetID(), temp);
			in.nextLine();
		}
		in.close();
	}

	//used by reportGenerator
	public Stack<Providers> stackProviders()
	{
		Stack<Providers> myStack = new Stack<Providers>();
		for(Map.Entry<Integer, Person> entry : directory1.entrySet()){
			Providers toPush = new Providers(entry.getValue());
			myStack.push(toPush);
		}
		

		System.out.println(myStack);
		return myStack;
	}

	//adds new provider to both maps
	public int addPro(Person temp)
	{
		directory.put(temp.NameGetter(), temp);
		directory1.put(temp.IDGetter(), temp);
					
		if(directory.containsKey(temp.NameGetter()))
			if(directory1.containsKey(temp.IDGetter()))
				return 1;
	
		return 0;
	}
	
	//remove provider from both maps based on name
	public boolean removeProByName(String key)
	{
		if(directory.containsKey(key))
			return directory1.remove(key, directory.remove(key).IDGetter());
		
		return false;
	}
	
	//removes by from both maps based on provider number
	public boolean removeByProNum(int key)
	{
		if(directory1.containsKey(key))	
			return directory1.remove(key, directory.remove(key).IDGetter());
		
		return false;
	}
	
	//display list of provider in alphabetical order	
	public void displayAllByName()
	{	
		for(Entry<String, Person> entry : directory.entrySet())
			entry.getValue().DisplayInformation();
	}
	
	//searches map by provider number
	public int searchByProNum(int found)
	{
		//check that the key exist if so respond positively
		if(directory1.containsKey(found))
			return 1;
		
		return 0;
	}
	
	//search by name and copies to person brought in
	public Person searchByNameAndCopy(String key, Person temp)
	{
		if(directory.containsKey(key))
			return temp = new Providers(directory.get(key));
		
		return null;
	}
	
	//search by number and copies to a person brought in
	public Person searchByNumAndCopy(int key, Person temp)
	{
		if(directory1.containsKey(key))
			return temp = new Providers(directory1.get(key));
		
		return null;
	}
	
	//searches by number and displays
	public int searchByNumAndDisplay(int key)
	{
		if(directory1.containsKey(key))
		{	
			directory1.get(key).DisplayInformation();
			return 1;
		}
		return 0;
	}
	
	//searches map by provider name, then displays
	public int searchProNameAndDisplay(String found)
	{
		if(directory.containsKey(found))
		{
			directory.get(found).DisplayInformation();
			return 1;
		}
		return 0;
	}
	
	public void saveOutForEmail()
	{
		PrintWriter writer = null;
		
		try {
			writer = new PrintWriter("ProviderDB Email.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Entry<String, Person> entry : directory.entrySet())
		{
			entry.getValue().saveOutForEmail(writer);
			writer.write("\n");
		}

		writer.close();
	}
	
	public void saveOutForSystem()
	{
		PrintWriter writer = null;
		
		try {
			writer = new PrintWriter("ProviderDB.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Entry<String, Person> entry : directory.entrySet())
		{
			entry.getValue().saveOutForSystem(writer);
			writer.write("\n");
		}

		writer.close();
	}
	
	/*public static void main(String[] args)
	{
		/*Person temp = new Providers("Jo", 1, "Address", "City", "State", 321);
		Person temp1 = new Providers("Roo", 12, "Address", "City", "State", 123);
		Person temp2 = new Providers("Key", 32, "Address", "City", "State", 234);
		Person temp3 = new Providers("Tay", 4, "Address", "City", "State", 567);
		
		ProviderDB directory;
		try {
			directory = new ProviderDB("ProviderDB.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		directory.displayAllByName();
		
	/*	directory.addPro(temp);
		directory.addPro(temp1);
		directory.addPro(temp2);
		directory.addPro(temp3);
		
		directory.saveOutForSystem();
			
	}*/
	
}
