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

public class SemSym {
    private String type;
    private boolean function;
    private boolean struct;
    private List <String> formalsListVals;
    private HashMap <String, SemSym> structMems;	
    public SemSym(String type, boolean function, boolean struct, 
	HashMap <String, SemSym> structMems) throws Exception{
	
	if (type == null) {
		throw new Exception("Type is null");
	}
	if (formalsListVals.isEmpty()) {
		throw new Exception("FormalsListVals is empty");
	}
	if (structMems.isEmpty()) {
		throw new Exception("structMems is empty");
	}
        this.type = type;
	this.function = function;
	this.struct = struct;
	this.structMems = structMems;
    }
    
    public String getType() {
        return type;
    }
	
    public boolean isFunction() {
	return function;
    }
	
    public boolean isStruct() {
	return struct;
    }
    
    public HashMap<String, SemSym> getStructMems() {
	return structMems;
    }
	
    public String toString() {
        return type;
    }
}