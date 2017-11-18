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

/**
 * ErrMsg
 *
 * This class is used to generate warning and fatal error messages.
 */
class ErrMsg {
    /**
     * Generates a fatal error message.
     * @param lineNum line number for error location
     * @param charNum character number (i.e., column) for error location
     * @param msg associated message for error
     */
    static void fatal(int lineNum, int charNum, String msg) {
        System.err.println(lineNum + ":" + charNum + " ***ERROR*** " + msg);
    }

    /**
     * Generates a warning message.
     * @param lineNum line number for warning location
     * @param charNum character number (i.e., column) for warning location
     * @param msg associated message for warning
     */
    static void warn(int lineNum, int charNum, String msg) {
        System.err.println(lineNum + ":" + charNum + " ***WARNING*** " + msg);
    }
    
    static boolean error = false;
}