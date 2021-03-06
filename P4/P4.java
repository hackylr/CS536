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

import java.io.*;
import java_cup.runtime.*;

/**
 * Main program to test the cimple parser.
 *
 * There should be 2 command-line arguments:
 *    1. the file to be parsed
 *    2. the output file into which the AST built by the parser should be
 *       unparsed
 * The program opens the two files, creates a scanner and a parser, and
 * calls the parser.  If the parse is successful, the AST is unparsed.
 */

public class P4 {
    public static void main(String[] args)
        throws IOException // may be thrown by the scanner
    {
        // check for command-line args
        if (args.length != 2) {
            System.err.println("please supply name of file to be parsed " +
			                   "and name of file for unparsed version.");
            System.exit(-1);
        }

        // open input file
        FileReader inFile = null;
        try {
            inFile = new FileReader(args[0]);
        } catch (FileNotFoundException ex) {
            System.err.println("File " + args[0] + " not found.");
            System.exit(-1);
        }

        // open output file
        PrintWriter outFile = null;
        try {
            outFile = new PrintWriter(args[1]);
        } catch (FileNotFoundException ex) {
            System.err.println("File " + args[1] +
                               " could not be opened for writing.");
            System.exit(-1);
        }

        parser P = new parser(new Yylex(inFile));

        Symbol root = null; // the parser will return a Symbol whose value
                            // field is the translation of the root nonterminal
                            // (i.e., of the nonterminal "program")

        try {
            root = P.parse(); // do the parse
            System.out.println ("program parsed correctly.");
        } catch (Exception ex){
            System.err.println("Exception occured during parse: " + ex);
            System.exit(-1);
        }

	// begin program analysis
	SymTable symTab = new SymTable();
	((ASTnode)root.value).nameAnalysis(symTab);	
	// TODO: Handle errors
        if (ErrMsg.error) {
	    System.err.println("At least one error occurred during name analysis");
	    // Don't unparse if there are errors
	    System.exit(-1);
	}
	else {
	   System.out.println("No errors after performing name analysis");
	}	
        ((ASTnode)root.value).unparse(outFile, 0);
        outFile.close();

        return;
    }
}