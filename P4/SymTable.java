///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            P4.java
// Files:            SemSym.java, SymTable.java, ast.java, ErrMsg.java, test.cf, 
//						nameErrors.cf, cimple.cup, cimple.grammar, cimple.jlex,
//						DuplicateSymException.java, EmptySymTableException.java
//						Makefile, test.out, nameErrors.out

// Semester:         CS536 Fall 2017
//
// Author:           Damon Francisco
// Email:            dfrancisco@wisc.edu
// CS Login:         damon
// Lecturer's Name:  Aws Albarghouthi
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
// Pair Partner:     Avery Chen
// Email:            agchen@wisc.edu
// CS Login:         avery
// Lecturer's Name:  Aws Albarghouthi
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//
// Online sources:   Piazza, Stack Overflow
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.*;

public class SymTable {
    private List<HashMap<String, SemSym>> list;
    
    public SymTable() {
        list = new LinkedList<HashMap<String, SemSym>>();
        list.add(new HashMap<String, SemSym>());
    }
    
    public void addDecl(String name, SemSym sym) 
    throws DuplicateSymException, EmptySymTableException {
        if (name == null || sym == null)
            throw new NullPointerException();
        
        if (list.isEmpty())
            throw new EmptySymTableException();
        
        HashMap<String, SemSym> symTab = list.get(0);
        if (symTab.containsKey(name))
            throw new DuplicateSymException();
        
        symTab.put(name, sym);
    }
    
    public void addScope() {
        list.add(0, new HashMap<String, SemSym>());
    }
    
    public SemSym lookupLocal(String name) {
        if (list.isEmpty())
            return null;
        
        HashMap<String, SemSym> symTab = list.get(0); 
        return symTab.get(name);
    }
    
    public SemSym lookupGlobal(String name) {
        if (list.isEmpty())
            return null;
        
        for (HashMap<String, SemSym> symTab : list) {
            SemSym sym = symTab.get(name);
            if (sym != null)
                return sym;
        }
        return null;
    }
    
    public void removeScope() throws EmptySymTableException {
        if (list.isEmpty())
            throw new EmptySymTableException();
        list.remove(0);
    }
    
    public void print() {
        System.out.print("\nSym Table\n");
        for (HashMap<String, SemSym> symTab : list) {
            System.out.println(symTab.toString());
        }
        System.out.println();
    }
}
