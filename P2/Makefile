###
# This Makefile can be used to make a scanner for the cimple language
# (Yylex.class) and to make a program that tests the scanner (P2.class).
#
# The default makes both the scanner and the test program.
#
# make clean removes all generated files.
#
# Note: P2.java will not compile unless Yylex.class exists.
#
###

# define the java compiler to be used and the flags
JC = javac
FLAGS = -g -cp $(CP)
CP = ../deps:.

P2.class: P2.java Yylex.class sym.class
	$(JC) $(FLAGS) P2.java

Yylex.class: cimple.jlex.java ErrMsg.class sym.class
	$(JC) $(FLAGS) cimple.jlex.java

cimple.jlex.java: cimple.jlex sym.class
	java -cp $(CP) JLex.Main cimple.jlex

sym.class: sym.java
	$(JC) $(FLAGS) sym.java

ErrMsg.class: ErrMsg.java
	$(JC) $(FLAGS) ErrMsg.java

	
###
# testing - add more here to run your tester and compare its results
# to expected results
###
test:
	java -cp $(CP) P2 

difftest:
	java -cp $(CP) P2 
	diff resAndId.in resAndId.out
	diff idAndIntLits.in idAndIntLits.out
	diff stringLits.in stringLits.out
	diff badStringLits.exp badStringLits.out
	diff charSyms.in charSyms.out
	diff moreErrors.exp moreErrors.out
	diff eof.exp eof.out
	diff comments.exp comments.out	
	diff goodTokens.exp goodTokens.out
###
# clean up
###

clean:
	rm -f *~ *.class cimple.jlex.java

cleantest:
	rm -f resAndId.out idAndIntLits.out stringLits.out badStringLits.out charSyms.out moreErrors.out eof.out comments.out goodTokens.out

cleanall:
	rm -f *~ *.class cimple.jlex.java
	rm -f resAndId.out idAndIntLits.out stringLits.out charSyms.out badStringLits.out moreErrors.out eof.out comments.out goodTokens.out
