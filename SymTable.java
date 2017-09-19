import java.util.*;


//Class for the symbol table
public class SymTable 
{
	private List<HashMap<String, Sym>> symList = new LinkedList<HashMap<String, Sym>>();
	
	/**
	 * Constructor, initializes the SymTable's List field to contain
	 * a single, empty HashMap
	 */
	public SymTable()
	{
		symList.add(new HashMap<String, Sym>());
	}
	
	/**
	 * Adding the first HashMap in the list.
	 * If either parameter is null, throws exception
	 * Checks for an empty list as well as duplicates
	 *
	 * @param String name: name of the variable
	 * @param Sym sym: type of the identifier
	 */
	public void addDecl(String name, Sym sym) throws DuplicateSymException, EmptySymTableException
	{
		if(name == null || sym == null)
		{
			throw new NullPointerException();
		}
		
		if (symList.isEmpty())
		{
			throw new EmptySymTableException();
		}
		
		HashMap<String, Sym> currList = symList.get(0);
		if (currList.containsKey(name))
		{
			throw new DuplicateSymException();
		}
		
		currList.put(name, sym);
	}
	
	/**
	 * Adds a new empty HashMap to the front of the list
	 * Checks if list is empty first or not
	 *)
	 */
	public void addScope()
	{
		//Check if symList is empty
		if (symList.isEmpty())
		{
			symList.add(new HashMap<String, Sym>());
		}
		
		//If symList is not empty, then place new element at index 0
		else
		{
			symList.add(0, new HashMap<String, Sym>());
		}
	}
	
	/**
	 * Looks up certain names as the key in the first HashMap
	 *
	 * @param String name: name of the variable used to search the list
	 * @return Returns associated sym (identifier) or null
	 */
	public Sym lookupLocal(String name) throws EmptySymTableException
	{
		if (symList.isEmpty())
		{
			throw new EmptySymTableException();
		}
		
		//Focuses only on the first one
		if (symList.get(0).containsKey(name))
		{
			return symList.get(0).get(name);
		}
		
		else
		{
			return null;
		}
	}
	
	/**
	 * Looks up certain names as the key in any HashMap
	 *
	 * @param String name: variable used to search the lists
	 * @return Returns first associated sym (identifier) or null
	 */
	public Sym lookupGlobal(String name) throws EmptySymTableException
	{
		if (symList.isEmpty())
		{
			throw new EmptySymTableException();
		}
		
		//Loops through the entire list
		for (int i = 0; i < symList.size(); i++)
		{
			if (symList.get(i).containsKey(name))
			{
				return symList.get(i).get(name); 
			}
		}
		
		return null;
	}
	
	/**
	 * Removes the HashMap from the front of the list
	 * Checks if the list is empty first or not, throws exception if so
	 *
	 */
	public void removeScope() throws EmptySymTableException
	{
		if (symList.isEmpty())
		{
			throw new EmptySymTableException();
		}
		
		symList.remove(0);
	}
	
	/**
	 * Debugging method, prints out each HashMap as a String
	 *
	 */
	public void print()
	{
		 System.out.print("\nSym Table\n");
		 
	     //Looping through each HashMap
		 for (int i = 0; i < symList.size(); i++) 
	     {
	         System.out.println(symList.get(i).toString());
	     }
	     
	     System.out.print("\n");
	}
	
}