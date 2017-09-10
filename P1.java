
public class P1 
{
	public static void main(String[] args)
	{
		test_Sym();
		test_addDecl();
		test_addScope();
		test_lookupLocal();
		test_lookupGlobal();
		test_removeScope();
		test_print();
	}
	
	//Testing Sym class
	private static void test_Sym()
	{
		String[] tempList = {"int", "boolean", "char"};
		
		for(int i = 0; i < tempList.length; i++)
		{
			Sym tempSym = new Sym(new String(tempList[i]));
			
			String tempType = tempSym.getType();
			if(!tempType.equals(tempList[i]))
			{
				System.out.println("getType() returns wrong value");
			}
			
			tempType = tempSym.toString();
			if(!tempType.equals(tempList[i]))
			{
				System.out.println("toString() returns wrong value");
			}
		}
	}
	
	private static void test_addDecl()
	{
		
	}
	
	private static void test_addScope()
	{
		
	}
	
	private static void test_lookupLocal()
	{
		
	}
	
	private static void test_lookupGlobal()
	{
		
	}
	
	private static void test_removeScope()
	{
		
	}
	
	private static void test_print()
	{
		SymTable temp = new SymTable();
		
		try
		{
			temp.print();
		}
		
		catch (Exception e)
		{
			System.out.println("print() did not work");
		}
		
	}
}
 