##### ERLANG ####
Erlang is a general-purpose, concurrent, functional programming language, and a garbage-collected runtime system

Build the application in Eclipse
  -> Run the money class : money:start().

#### JAVA ####
The same application written in java too. To run the java application.

1. Either create a java project or run in command line
	 Command line :-
		1. In current directory -> Enter commands 
				a) javac money.java 
				b) java money       


		Hint: 
		javac fileName = It generate classes for the java files
		java className = It executes the code

Project Description:

The objective is to model a simple banking environment. Specifically, you will be given a small number of customers, each of whom will contact a set of banks to request a number of loans. Eventually, they will either receive all of the money they require or they will end up without completely meeting their original objective. The application will display information about the various banking transactions before it finishes. That’s it. So now for the details. To begin, you will need a handful of customers and banks. These will be supplied in a pair of very simple text files – customers.txt and banks.txt. While Erlang provides many file primitives for processing disk files, the process is not quite as simple as Clojure’s slurp() function. So the two files will contain records that are already pre-formatted. In other words, the are ready to be read directly into standard Erlang data structures.

An example 
customers.txt file would be: {jill,450}.
{joe,157}.
{bob,100}.
{sue,125}.
An example banks.txt file would be:
{rbc,800}.
{bmo,700}.
{ing,200}.

The complete descripion of the project availabe in Erlang.pdf

Reference: http://erlang.org/doc/
