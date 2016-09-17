//package edu.utsa.tl15;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;

/*
 * This is the Parser which implements all the Production Rules for the TL15 grammar
 */

public class ParserAst {
	Hashtable htable= null;

	public ParserAst(InputStream tl15In, OutputStream parseOut) throws Exception {

		/*
		 * The in-built scanner in java is used to convert the input .tl15 file
		 * into Tokens
		 */
		Scanner scanner = new Scanner(tl15In);

		// An ArrayList inputFile contains the ouput from the scanner that is
		// the "TOKENS"
		ArrayList inputFile = new ArrayList();

		while (scanner.hasNext()) {
			if ((!scanner.hasNext(" "))) {
				inputFile.add(scanner.next());
			}
		}

		/*
		 * Creating ArrayLists for all the Non-Terminals in the Production rules
		 */

		// ArrayList for the Non-Terminal <program>
		ArrayList program = new ArrayList();
		program.add("program");
		program.add("<declarations>");
		program.add("begin");
		program.add("<statementSequence>");
		program.add("end");

		// ArrayList for the Non-Terminal <declarations>
		ArrayList declarations = new ArrayList();
		declarations.add("var");
		declarations.add("ident");
		declarations.add("as");
		declarations.add("<type>");
		declarations.add("sc");
		declarations.add("<declarations>");

		// ArrayList for the Non-Terminal <type>
		ArrayList type = new ArrayList();
		type.add("int");
		type.add("bool");

		// ArrayList for the Non-Terminal <statementSequence>
		ArrayList statementSequence = new ArrayList();
		statementSequence.add("<statement>");
		statementSequence.add(";");
		statementSequence.add("<statementSequence>");

		// ArrayList for the Non-Terminal <statement>
		ArrayList statement = new ArrayList();
		statement.add("<assignment>");
		statement.add("<ifStatement>");
		statement.add("<whileStatement>");
		statement.add("<writeint>");

		// ArrayList for the Non-Terminal <assignment>
		ArrayList assignment = new ArrayList();
		assignment.add("ident");
		assignment.add(":=");
		assignment.add("<expression>");
		assignment.add("readint");

		// ArrayList for the Non-Terminal <ifStatement>
		ArrayList ifStatement = new ArrayList();
		ifStatement.add("if");
		ifStatement.add("<expression>");
		ifStatement.add("then");
		ifStatement.add("<statementSequence>");
		ifStatement.add("<elseClause>");
		ifStatement.add("end");

		// ArrayList for the Non-Terminal <elseClause>
		ArrayList elseClause = new ArrayList();
		elseClause.add("else");
		elseClause.add("<statementSequence>");

		// ArrayList for the Non-Terminal <whileStatement>
		ArrayList whileStatement = new ArrayList();
		whileStatement.add("while");
		whileStatement.add("<expression>");
		whileStatement.add("do");
		whileStatement.add("<statementSequence>");
		whileStatement.add("end");

		// ArrayList for the Non-Terminal <writeint>
		ArrayList writeint = new ArrayList();
		writeint.add("writeint");
		writeint.add("<expression>");

		// ArrayList for the Non-Terminal <expression>
		ArrayList expression = new ArrayList();
		expression.add("<simpleExpression>");
		expression.add("<simpleExpressionPrime");

		// ArrayList for the Non-Terminal <simpleExpressionPrime>
		ArrayList simpleExpressionPrime = new ArrayList();
		simpleExpressionPrime.add("op4");
		simpleExpressionPrime.add("<simpleExpression>");

		// ArrayList for the Non-Terminal <termPrime>
		ArrayList termPrime = new ArrayList();
		termPrime.add("op3");
		termPrime.add("<term>");

		// ArrayList for the Non-Terminal <term>
		ArrayList term = new ArrayList();
		term.add("<factor>");
		term.add("<factorprime>");

		// ArrayList for the Non-Terminal <factorPrime>
		ArrayList factorPrime = new ArrayList();
		factorPrime.add("op2");
		factorPrime.add("<factor>");

		// ArrayList for the Non-Terminal <factor>
		ArrayList factor = new ArrayList();
		factor.add("ident");
		factor.add("num");
		factor.add("boollit");
		factor.add("lp");
		factor.add("<expression>");
		factor.add("rp");

		// A Copy of the ArrayList inputFile is created as any modifications for
		// the inputFile won't reflect on the data from the original one
		ArrayList inputFileCopy = new ArrayList();
		for (int k = 0; k < inputFile.size(); k++) {
			inputFileCopy.add(inputFile.get(k));
		}
		
//		  Field stringListField = Parser.class.getDeclaredField("inputFileCopy");
//	        ParameterizedType stringListType = (ParameterizedType) stringListField.getGenericType();
//	        Class<?> stringListClass = (Class<?>) stringListType.getActualTypeArguments()[0];
//	        System.out.println(stringListClass); // class java.lang.String.
//
//	        Field integerListField = Parser.class.getDeclaredField("inputFileCopy");
//	        ParameterizedType integerListType = (ParameterizedType) integerListField.getGenericType();
//	        Class<?> integerListClass = (Class<?>) integerListType.getActualTypeArguments()[0];
//	        System.out.println(integerListClass); // class java.lang.Integer.
		
		
//		for (Object o : inputFileCopy) {
//		    if (o instanceof Integer) {
//		    	System.out.println(o.getClass().getComponentType());
//		        //handleInt((int)o);
//		    }
//		    else if (o.getClass().equals(String.class)) {
//		    	System.out.println(o.getClass().getComponentType());
//		        //handleString((String)o);
//		    }
//		    else if (o.getClass().equals(Boolean.TYPE)) {
//		    	System.out.println(o.getClass().getComponentType());
//		        //handleString((String)o);
//		    }
//		    
//		}
		
//		Iterator itr = inputFileCopy.iterator();
//		while(itr.hasNext()){
//		System.out.println(itr.next());
//		}
//
//		ListIterator listItr = inputFileCopy.listIterator();
//		while(listItr.hasNext()){
//		System.out.println(itr.next());
//		}

		

		// ArrayList allKeywords contains all the valid keywords which will be
		// used to compare the inputFile
		ArrayList allKeywords = new ArrayList();
		allKeywords.add("program");
		allKeywords.add("begin");
		allKeywords.add("end");

		allKeywords.add("var");
		allKeywords.add("as");
		allKeywords.add("int");
		allKeywords.add("bool");
		allKeywords.add(";");

		allKeywords.add("if");
		allKeywords.add("then");
		allKeywords.add("else");
		allKeywords.add("while");
		allKeywords.add("do");
		allKeywords.add("writeint");
		allKeywords.add("readint");

		allKeywords.add(":=");

		// op4 --- "=" | "<>" | "<" | ">" | "<=" | ">="
		allKeywords.add("=");
		allKeywords.add("<>");
		allKeywords.add("<");
		allKeywords.add(">");
		allKeywords.add("<=");
		allKeywords.add(">=");

		// op3 --- "+" | "-"
		allKeywords.add("+");
		allKeywords.add("-");

		// op2 --- "*" | "div" | "mod"
		allKeywords.add("*");
		allKeywords.add("div");
		allKeywords.add("mod");

		// allKeywords.add("num");
		allKeywords.add("true");
		allKeywords.add("false");
		allKeywords.add("(");
		allKeywords.add(")");

		int countWords = 0;
		 System.out.println(inputFileCopy.size());
		 System.out.println(allKeywords.size());
		 
		 
//		 for (int i = 0; i < inputFileCopy.size(); i++) {
//			 System.out.println(inputFileCopy.get(i));
//			// System.out.println(allKeywords.get(i));
//
//			if (inputFileCopy.contains("(") && inputFileCopy.contains(")")) {
//				 	continue;
//				}
//				else
//					throw new Exception("one of the parenthesis is missing");				
//			}

		 
		 
		  for (int i = 0; i < allKeywords.size(); i++) {
			for (int j = 0; j < inputFileCopy.size(); j++) {
//				 System.out.println(inputFileCopy.get(j));
//				 System.out.println(allKeywords.get(i));

				if (inputFileCopy.get(j).equals(allKeywords.get(i))) {
//					System.out.println("inputFileCopy.get(j): "+inputFileCopy.get(j));
//					System.out.println("allKeywords.get(i): "+allKeywords.get(i));
					countWords++;
					 //System.out.println(countWords);
				}

			}
		}

//		 System.out.println(inputFileCopy.size());
//		 System.out.println(allKeywords.size());
//		 System.out.println(countWords);
		

		// counts "ident" values
		int countId = 0;
		for (int k = 0; k < inputFileCopy.size(); k++) {
			if (((String) inputFileCopy.get(k)).matches("[A-Z][A-z0-9]*") && !(inputFileCopy.get(k).equals("PROGRAM")) && !(inputFileCopy.get(k).equals("BEGIN"))
					&& !(inputFileCopy.get(k).equals("END")) && !(inputFileCopy.get(k).equals("VAR")) && !(inputFileCopy.get(k).equals("AS"))
					&& !(inputFileCopy.get(k).equals("INT")) && !(inputFileCopy.get(k).equals("BOOL")) && !(inputFileCopy.get(k).equals("IF")) 
					&& !(inputFileCopy.get(k).equals("THEN")) && !(inputFileCopy.get(k).equals("ELSE"))&& !(inputFileCopy.get(k).equals("WHILE"))
					&& !(inputFileCopy.get(k).equals("DO")) && !(inputFileCopy.get(k).equals("WRITEINT")) && !(inputFileCopy.get(k).equals("READINT"))) {
				countId++;
				//System.out.println(inputFileCopy.get(k));
			}
		}

		// counts "num" values
		int countNum = 0;
		for (int k = 0; k < inputFileCopy.size(); k++) {
			if (((String) inputFileCopy.get(k)).matches("[0-9]*")) {
				countNum++;

			}
		}

		countWords = countWords + (countId + countNum);

		if (countWords == inputFileCopy.size()) {
			System.out.println("success : Valid input File");
		} else {
			System.out.println("Invalid input : Please check the input file");
			System.exit(0);

		}		
		
		int countHashKeys = 0;
		//for hash table
		for (int m = 0; m < inputFileCopy.size(); m++) {
			if(((String) inputFileCopy.get(m)).matches("[A-Z][A-z0-9]*")){
				if(inputFileCopy.get(m+2).equals("int")){
					if(htable == null){
					SymbolTable(inputFileCopy.get(m).toString(), inputFileCopy.get(m+2).toString());
					//countHashKeys++;
					}
					else if((htable != null) && (htable.containsKey(inputFileCopy.get(m)))){
						throw new Exception("Each variable may only be declared once...");
					}
					else{
						SymbolTable(inputFileCopy.get(m).toString(), inputFileCopy.get(m+2).toString());
					}
				}
				else if(inputFileCopy.get(m+2).equals("bool")){
					if(htable == null){
						SymbolTable(inputFileCopy.get(m).toString(), inputFileCopy.get(m+2).toString());
						//countHashKeys++;
						}
						else if((htable != null) && (htable.containsKey(inputFileCopy.get(m)))){
							throw new Exception("Each variable may only be declared once...");
						}
						else{
							SymbolTable(inputFileCopy.get(m).toString(), inputFileCopy.get(m+2).toString());
						}
				}				
			}			
		}
		
		for (int n = 0; n < inputFileCopy.size(); n++) {
			if(((String) inputFileCopy.get(n)).matches("[A-Z][A-z0-9]*")){
				if(htable.containsKey(inputFileCopy.get(n))){
					continue;				
				}
				else{
					throw new Exception(" All variables must be declared with a particular type...");
				}
			}
		}
		
//		for(int n = 0; n < countHashKeys; n++){
//			
//		}
		
		
		

		/*
		 * Stating point for the input .tl15 file to be parsed.
		 */

		// checks whether it starts correctly otherwise returns error
		if (inputFileCopy.get(0).equals("program")) {
			Program(program, declarations, type, statementSequence, statement,
					assignment, ifStatement, elseClause, whileStatement,
					writeint, expression, simpleExpressionPrime, termPrime,
					term, factorPrime, factor, inputFile, inputFileCopy,
					parseOut);
		}

	}

	/**
	 * Starts with the prdoduction <program> ::= PROGRAM <declarations> BEGIN
	 * <statementSequence> END
	 * 
	 * @param program
	 * @param declarations
	 * @param type
	 * @param statementSequence
	 * @param statement
	 * @param assignment
	 * @param ifStatement
	 * @param elseClause
	 * @param whileStatement
	 * @param writeint
	 * @param expression
	 * @param simpleExpressionPrime
	 * @param termPrime
	 * @param term
	 * @param factorPrime
	 * @param factor
	 * @param inputFile
	 * @param inputFileCopy
	 * @param parseOut
	 * @throws Exception 
	 */

	private void Program(ArrayList program, ArrayList declarations,
			ArrayList type, ArrayList statementSequence, ArrayList statement,
			ArrayList assignment, ArrayList ifStatement, ArrayList elseClause,
			ArrayList whileStatement, ArrayList writeint, ArrayList expression,
			ArrayList simpleExpressionPrime, ArrayList termPrime,
			ArrayList term, ArrayList factorPrime, ArrayList factor,
			ArrayList inputFile, ArrayList inputFileCopy, OutputStream parseOut) throws Exception {
		int countNodeVal = 0;
		PrintStream ps = new PrintStream(parseOut);

		// Stating point for the parse tree
		ps.println("digraph TL15ast {");
		ps.println("ordering=out;");
		ps.println("node [shape = box];");
		ps.println("node [colorscheme=\"pastel13\" style=filled fillcolor=\"lightgrey\"];");

		for (int i = 0; i < (inputFileCopy.size());) {
			// executes if the inputFileCopy at this point has "program"
			//int decllistNode = 0;
			if (inputFileCopy.get(i).equals("program")) {

				ps.println("n" + countNodeVal + " [label=\"program\"];");    // (n0) program

				countNodeVal++;
				ps.println("n" + countNodeVal + " [label=\"decl list\" shape=none style=solid];");
				ps.println("n0 -> n" + countNodeVal + ";");                       // (n0) program -> decl list (n1)
				
				//decllistNode = countNodeVal;		

				inputFileCopy.remove(i);

				ps.println();

			}			
				

			// executes if the inputFileCopy at this point has "VAR"
			if (inputFileCopy.get(i).equals("var")) {
				
				countNodeVal = Declarations(countNodeVal, inputFileCopy.get(i),
						inputFileCopy.get(i + 1), inputFileCopy.get(i + 2),
						inputFileCopy.get(i + 3), inputFileCopy.get(i + 4),
						inputFileCopy.get(i + 5), inputFile, inputFileCopy,
						parseOut);
				inputFileCopy.remove(i);
				inputFileCopy.remove(i);
				inputFileCopy.remove(i);
				inputFileCopy.remove(i);
				inputFileCopy.remove(i);

				ps.println();
			}

			// executes if the inputFileCopy at this point has "BEGIN"
			if (inputFileCopy.get(i).equals("begin")) {

				inputFileCopy.remove(i);
				ps.println();
			}
			
			if (inputFileCopy.get(i).equals("readint")){
				throw new Exception("syntax error in using readint");
			}

			// executes if the inputFileCopy at this point has "ident" value or
			// "if" or "while" or "writeint"
			if ((((String) inputFileCopy.get(i)).matches("[A-Z][A-z0-9]*")&& inputFileCopy.get(i + 1).equals(":="))
					|| inputFileCopy.get(i).equals("if")
					|| inputFileCopy.get(i).equals("while")
					|| inputFileCopy.get(i).equals("writeint")) {

				countNodeVal++;
				ps.println("n" + countNodeVal
						+ " [label=\"stmt list\" shape=none style=solid];");
				ps.println("n0 -> n" + countNodeVal + ";");                        // (n0) program -> stmt list (n11)

				final int count = countNodeVal;
				

				// goes to Statement
				countNodeVal = Statement(count, countNodeVal, inputFile,
						inputFileCopy, parseOut);

				inputFileCopy.remove(0);

				ArrayList ifToThenVal = new ArrayList();

				// goes to StatementSequence
				countNodeVal = StatementSequence(count, countNodeVal, ifToThenVal,
						inputFile, inputFileCopy, parseOut);
				ps.println();
			}

			// executes if the inputFileCopy at this point has "END"
			if (inputFileCopy.get(i).equals("end")) {

				inputFileCopy.remove(i);

				ps.println();
				ps.print("}");

			}
		}
	}

	/**
	 * Executes the production rule: <statementSequence> ::= <statement> SC
	 * <statementSequence>|epsilon
	 * 
	 * @param countNodeVal
	 * @param ifToThenVal
	 * @param inputFile
	 * @param inputFileCopy
	 * @param parseOut
	 * @return
	 * @throws Exception 
	 */
                                //countAssign
	public int StatementSequence(int count, int countNodeVal, ArrayList ifToThenVal,
			ArrayList inputFile, ArrayList inputFileCopy, OutputStream parseOut) throws Exception {
		final int count1 = countNodeVal;

		PrintStream ps = new PrintStream(parseOut);
		
		if (inputFileCopy.get(0).equals("readint")){
			throw new Exception("syntax error in using readint");
		}

		// executes only if the first value in the ArrayList inputFileCopy is
		// equal to "writeint" or "if" or "while" or any "ident" value otherwise
		// StatementSequence should be epsilon
		if (inputFileCopy.get(0).equals("writeint")
				|| inputFileCopy.get(0).equals("if")
				|| inputFileCopy.get(0).equals("while")
				|| (((String) inputFileCopy.get(0)).matches("[A-Z][A-z0-9]*")&& inputFileCopy.get(1).equals(":="))) {


			countNodeVal = Statement(count, countNodeVal, inputFile, inputFileCopy,
					parseOut);


			inputFileCopy.remove(0);    //removes ;


			countNodeVal = StatementSequence(count, countNodeVal, ifToThenVal,
					inputFile, inputFileCopy, parseOut);

		}
		return countNodeVal;
	}

	/**
	 * Executes the production rule: <statement> ::= <assignment> |
	 * <ifStatement> | <whileStatement> | <writeint>
	 * 
	 * @param countNodeVal
	 * @param inputFile
	 * @param inputFileCopy
	 * @param parseOut
	 * @return
	 * @throws Exception 
	 */
	public int Statement(int count, int countNodeVal, ArrayList inputFile,
			ArrayList inputFileCopy, OutputStream parseOut) throws Exception {

		PrintStream ps = new PrintStream(parseOut);

		for (int i = 0; i < (inputFileCopy.size()); i++) {

			// executes <statement> ::= <assignment>
			if ((((String) inputFileCopy.get(i)).matches("[A-Z][A-z0-9]*"))
					&& inputFileCopy.get(i + 1).equals(":=")) {


				// This ArrayList contains the elements which are between := and
				// ;
				ArrayList ifToThenVal = new ArrayList();

				for (int j = i + 2;;) {
					if (!inputFileCopy.get(j).equals(";")) {
						ifToThenVal.add(inputFileCopy.get(j));
					}
					if (inputFileCopy.get(j).equals(";"))
						break;
					j++;
				}

				// This arrayList copies the elements "ident" value and "ASGN"
				// as :=
				ArrayList assignValues = new ArrayList();
				assignValues.add(inputFileCopy.get(i));
				assignValues.add(inputFileCopy.get(i + 1));

				inputFileCopy.remove(i);
				inputFileCopy.remove(i);

				// goes to Assignment
				countNodeVal = Assignment(count, countNodeVal, assignValues,
						inputFileCopy.get(i), ifToThenVal, inputFile,
						inputFileCopy, parseOut);

				return countNodeVal;

			}
			// executes <statement> ::= <ifStatement>
			else if (inputFileCopy.get(i).equals("if")) {

				// prints <ifStatement>
				countNodeVal++;
				ps.println("n" + countNodeVal + " [label=\"if\"];");
				ps.println("n" + count + " -> n" + countNodeVal	+ ";");       // (n8) stmt list -> if (n11)

				// This ArrayList contains the elements which are between if and
				// then
				ArrayList ifToThenVal = new ArrayList();

				for (int j = i + 1;;) {
					if (!inputFileCopy.get(j).equals("then")) {
						ifToThenVal.add(inputFileCopy.get(j));
					}
					if (inputFileCopy.get(j).equals("then"))
						break;
					j++;
				}

				inputFileCopy.remove(i);     //removes if

				// goes to ifStatement
				countNodeVal = IfStatement(count, countNodeVal, ifToThenVal,
						inputFile, inputFileCopy, parseOut);

				return countNodeVal;
			}
			// executes <statement> ::= <whileStatement>
			else if (inputFileCopy.get(i).equals("while")) {

				// prints <whileStatement>
				countNodeVal++;
				ps.println("n" + countNodeVal + " [label=\"while\"];");        // (n11) stmt list -> while
				ps.println("n" + count + " -> n" + countNodeVal
						+ ";");

				// This ArrayList contains the elements which are between while
				// and do
				ArrayList ifToThenVal = new ArrayList();
				for (int j = i + 1;;) {
					if (!inputFileCopy.get(j).equals("do")) {
						ifToThenVal.add(inputFileCopy.get(j));
					}
					if (inputFileCopy.get(j).equals("do"))
						break;
					j++;
				}

				// goes to WhileStatement
				countNodeVal = WhileStatement(count, countNodeVal, ifToThenVal,
						inputFile, inputFileCopy, parseOut);

				return countNodeVal;
			}
			// executes <statement> ::= <writeint>
			else if (inputFileCopy.get(i).equals("writeint")) {

				// prints writeint
				countNodeVal++;
				ps.println("n" + countNodeVal + " [label=\"writeint\"];");             // (n21) stmt list -> writeint (n22)
				ps.println("n" + count + " -> n" + countNodeVal
						+ ";");

				// This ArrayList contains the elements which are between
				// writeint and ;
				ArrayList ifToThenVal = new ArrayList();
				for (int j = i + 1;;) {
					if (!inputFileCopy.get(j).equals(";")) {
						ifToThenVal.add(inputFileCopy.get(j));
					}
					if (inputFileCopy.get(j).equals(";"))
						break;
					j++;
				}

				inputFileCopy.remove(i);

				// goes to Writeint
				countNodeVal = Writeint(countNodeVal, ifToThenVal, inputFile,
						inputFileCopy, parseOut);

				return countNodeVal;

			}
		}
		return countNodeVal;

	}

	/**
	 * Executes the Production Rule: <writeint> ::= WRITEiNT <expression>
	 * 
	 * @param countNodeVal
	 * @param ifToThenVal
	 * @param inputFile
	 * @param inputFileCopy
	 * @param parseOut
	 * @return
	 * @throws Exception 
	 */
	public int Writeint(int countNodeVal, ArrayList ifToThenVal,
			ArrayList inputFile, ArrayList inputFileCopy, OutputStream parseOut) throws Exception {
		PrintStream ps = new PrintStream(parseOut);
		final int count = countNodeVal;
		int countAssign = count;

		// goes to Expression
		ArrayList rightChildValues = Expression(countAssign, countNodeVal, ifToThenVal, inputFile,
				inputFileCopy, parseOut);
		
		Object leftChild = rightChildValues.get(0);
		countNodeVal = (Integer) rightChildValues.get(1);
		
	
		return countNodeVal;
	}

	/**
	 * Executes the Production Rule: <whileStatement> ::= WHILE <expression> DO
	 * <statementSequence> END
	 * 
	 * @param countNodeVal
	 * @param ifToThenVal
	 * @param inputFile
	 * @param inputFileCopy
	 * @param parseOut
	 * @return
	 * @throws Exception 
	 */
	public int WhileStatement(int count, int countNodeVal, ArrayList ifToThenVal,
			ArrayList inputFile, ArrayList inputFileCopy, OutputStream parseOut) throws Exception {
		PrintStream ps = new PrintStream(parseOut);

		int count1 = countNodeVal;
		int countAssign = count1;

		inputFileCopy.remove(0);

		// goes to Expression
		ArrayList rightChildValues = Expression(countAssign, countNodeVal, ifToThenVal, inputFile,
				inputFileCopy, parseOut);
		
		Object leftChild = rightChildValues.get(0);
		countNodeVal = (Integer) rightChildValues.get(1);
		
		if(ifToThenVal.contains("writeint")){
			countNodeVal++;
			ps.println("n" + countNodeVal + " [label=\"stmt list\" shape = none style = solid];");
			ps.println("n" + count1 + " -> n" + countNodeVal + ";");                    // (n27) while -> stmt list (n)
			  
			countAssign = countNodeVal;	
		}
		else{			
			countAssign = count1;				
		}
			

		inputFileCopy.remove(0);

		// goes to StatementSequence
		countNodeVal = StatementSequence(countAssign, countNodeVal, ifToThenVal, inputFile,
				inputFileCopy, parseOut);

		inputFileCopy.remove(0);

		return countNodeVal;
	}

	/**
	 * Executes the Production Rule: <ifStatement> ::= IF <expression> THEN
	 * <statementSequence> <elseClause> END
	 * 
	 * @param countNodeVal
	 * @param ifToThenVal
	 * @param inputFile
	 * @param inputFileCopy
	 * @param parseOut
	 * @return
	 * @throws Exception 
	 */
	public int IfStatement(int count, int countNodeVal, ArrayList ifToThenVal,
			ArrayList inputFile, ArrayList inputFileCopy, OutputStream parseOut) throws Exception {
		PrintStream ps = new PrintStream(parseOut);

		int count1 = countNodeVal;         // countNodeVal = count1 = if   (n11)
		int countAssign = count1;	       // countAssign = (n11)
		

		// goes to Expression
		ArrayList rightChildValues = Expression(countAssign, countNodeVal, ifToThenVal, inputFile,
				inputFileCopy, parseOut);
		
		Object leftChild = rightChildValues.get(0);
		countNodeVal = (Integer) rightChildValues.get(1);
		
		countNodeVal++;
		ps.println("n" + countNodeVal + " [label=\"stmt list\" shape = none style = solid];");
		ps.println("n" + count1 + " -> n" + countNodeVal + ";");                    // (n17) if -> stmt list (n21)
		  
		countAssign = countNodeVal;		

		inputFileCopy.remove(0);       //removes then

		// goes to StatementSequence
		countNodeVal = StatementSequence(countAssign, countNodeVal, ifToThenVal, inputFile,
				inputFileCopy, parseOut);

		if(inputFileCopy.get(0).equals("else")){
			countNodeVal++;
			ps.println("n" + countNodeVal + " [label=\"stmt list\" shape = none style = solid];");
			ps.println("n" + count1 + " -> n" + countNodeVal + ";");                    // (n17) if -> stmt list (n24)
			  
			countAssign = countNodeVal;		
			
			// goes to ElseClause
			countNodeVal = ElseClause(countAssign, count, countNodeVal, inputFile, inputFileCopy,
					parseOut);		
		}
		else{
			countAssign = countNodeVal;
		}

		inputFileCopy.remove(0); //removes end

		return countNodeVal;

	}

	/**
	 * Executes the Production Rule: <elseClause> ::= ELSE <statementSequence> |
	 * epsilon
	 * 
	 * @param countNodeVal
	 * @param inputFile
	 * @param inputFileCopy
	 * @param parseOut
	 * @return
	 * @throws Exception 
	 */
	public int ElseClause(int countAssign, int count, int countNodeVal, ArrayList inputFile,
			ArrayList inputFileCopy, OutputStream parseOut) throws Exception {
		PrintStream ps = new PrintStream(parseOut);

		int count1 = countNodeVal;

		inputFileCopy.remove(0);

		ArrayList ifToThenVal = new ArrayList();

		// goes to StatementSequence
		countNodeVal = StatementSequence(countAssign, countNodeVal, ifToThenVal, inputFile,
				inputFileCopy, parseOut);
		return countNodeVal;
	}

	/**
	 * Executes the Production Rule: <expression> ::= <simpleExpression>
	 * <simpleExpressionPrime>
	 * 
	 * @param countNodeVal
	 * @param ifToThenVal
	 * @param inputFile
	 * @param inputFileCopy
	 * @param parseOut
	 * @return
	 * @throws Exception 
	 */
	public ArrayList Expression(int countAssign, int countNodeVal, ArrayList ifToThenVal,
			ArrayList inputFile, ArrayList inputFileCopy, OutputStream parseOut) throws Exception {
		PrintStream ps = new PrintStream(parseOut);

		int count = countNodeVal;
		int count1 = count;
		ArrayList rightChildValues = new ArrayList();

		    ArrayList leftChildValues = SimpleExpression(countAssign, countNodeVal, ifToThenVal, inputFile,
				inputFileCopy, parseOut);

		    Object leftChild = leftChildValues.get(0);
		    countNodeVal = (Integer) leftChildValues.get(1);
		    
		// simpleExpressionPrime contains the op4 elements like "="
		// , "<>", "<", ">", "<=", ">=" 

		// goes to SimpleExpressionPrime
		rightChildValues = SimpleExpressionPrime(countAssign, leftChild, countNodeVal, ifToThenVal,
				inputFile, inputFileCopy, parseOut);
		
		if(rightChildValues.get(0) != null){
			countNodeVal++;
			ps.println("n" + countNodeVal + " [label=\""+ leftChild +"\" fillcolor=3];");
			ps.println("n" + countAssign + " -> n" + countNodeVal + ";");
			
			rightChildValues.set(0, null);
			rightChildValues.set(1, countNodeVal);
		}
		
		return rightChildValues;

	}

	/**
	 * Executes the Production Rule: <simpleExpressionPrime> ::= epsilon | op4
	 * <simpleExpression>
	 * @param leftChild 
	 * 
	 * @param countNodeVal
	 * @param ifToThenVal
	 * @param inputFile
	 * @param inputFileCopy
	 * @param parseOut
	 * @return
	 * @throws Exception 
	 */
	public ArrayList SimpleExpressionPrime(int countAssign, Object leftChild, int countNodeVal, ArrayList ifToThenVal,
			ArrayList inputFile, ArrayList inputFileCopy, OutputStream parseOut) throws Exception {

		int count = countNodeVal;
		ArrayList leftChildValues = new ArrayList();
		PrintStream ps = new PrintStream(parseOut);
		for (int i = 0; i < (ifToThenVal.size()); i++) {
			// executes only if the ifToThenVal has an element which equals to
			// op4 like "=" , "<>", "<", ">", "<=", ">="
			//System.out.println("leftChild : "+ leftChild);
			if (ifToThenVal.get(i).equals("=")
					|| ifToThenVal.get(i).equals("<>")
					|| ifToThenVal.get(i).equals("<")
					|| ifToThenVal.get(i).equals(">")
					|| ifToThenVal.get(i).equals("<=")
					|| ifToThenVal.get(i).equals(">=")) {
				countNodeVal++;
				ps.println("n" + countNodeVal + " [label=\""
						+ ifToThenVal.get(i) + "\" fillcolor=3];");
				ps.println("n" + countAssign + " -> n" + countNodeVal + ";");           // := -> op4
				count++;

				ifToThenVal.remove(i);
				inputFileCopy.remove(i);
				
				int x = -1;				
				if(((String) leftChild).matches("[0-9]*")){
					x = new Integer(leftChild.toString()).intValue();
				}
				
				if((htable.get(leftChild) != null) && htable.get(leftChild).equals("int")) {
					countNodeVal++;
					ps.println("n" + countNodeVal + " [label=\""
							+ leftChild + "\" fillcolor=3];");
					ps.println("n" + count + " -> n" + countNodeVal + ";");                 // op4 -> 
				}
				else if(x >=0){
					throw new Exception("operands of OP4 operator should be integers and the first operand should be a variable ");
				}
				else
					throw new Exception("operands of operators should be integers");
					//System.err.println("operands of operators should be integers \""+ leftChild + "\"........should be int...");

				// goes to SimpleExression
				ArrayList rightChildValues = SimpleExpression(countAssign, countNodeVal, ifToThenVal,
						inputFile, inputFileCopy, parseOut);
				
				Object rightChild = rightChildValues.get(0);
				countNodeVal = (Integer) rightChildValues.get(1);
				
				System.out.println(rightChild.toString());
				
				int y = -1;
				if(((String) rightChild).matches("[0-9]*")){
					y = new Integer(rightChild.toString()).intValue();
				}
				
				if((y >= 0) || ((htable.get(rightChild) != null) && htable.get(rightChild).equals("int")) ){
					countNodeVal++;
					ps.println("n" + countNodeVal + " [label=\""
							+ rightChild + "\" fillcolor=3];");
					ps.println("n" + count + " -> n" + countNodeVal + ";");                //op4 ->
				}
				else{
					throw new Exception("operands of operators should be integers");
					//System.err.println("operands of operators should be integers \""+ rightChild + "\"........should be int...");
					
				}
				

				leftChild = null;
				rightChild = null;
			}			
			
		}		
		leftChildValues.add(leftChild);
		leftChildValues.add(countNodeVal);
		
		return leftChildValues;
			
	}

	/**
	 * Executes the Production Rule: <simpleExpression> ::= <term> <termPrime>
	 * 
	 * @param countNodeVal
	 * @param ifToThenVal
	 * @param inputFile
	 * @param inputFileCopy
	 * @param parseOut
	 * @return
	 * @throws Exception 
	 */
	public ArrayList SimpleExpression(int countAssign, int countNodeVal, ArrayList ifToThenVal,
			ArrayList inputFile, ArrayList inputFileCopy, OutputStream parseOut) throws Exception {
		PrintStream ps = new PrintStream(parseOut);

		int count = countNodeVal;
		int count1 = count;
		ArrayList leftChildValues = new ArrayList();

		    ArrayList rightChildValues = Term(countAssign, countNodeVal, ifToThenVal, inputFile,
				inputFileCopy, parseOut);

		    Object leftChild = rightChildValues.get(0);
		    countNodeVal = (Integer) rightChildValues.get(1);
		    
		// executes only if ifToThenVal contains op3 as "+" or "-" 

		// goes to TermPrime
		leftChildValues = TermPrime(countAssign, leftChild, countNodeVal, ifToThenVal, inputFile,
				inputFileCopy, parseOut);
		
		return leftChildValues;
	}

	/**
	 * Executes the Production Rule: <termPrime> ::= op3 <term> | epsilon
	 * @param leftChild 
	 * 
	 * @param countNodeVal
	 * @param ifToThenVal
	 * @param inputFile
	 * @param inputFileCopy
	 * @param parseOut
	 * @return
	 * @throws Exception 
	 */
	public ArrayList TermPrime(int countAssign, Object leftChild, int countNodeVal, ArrayList ifToThenVal,
			ArrayList inputFile, ArrayList inputFileCopy, OutputStream parseOut) throws Exception {
		PrintStream ps = new PrintStream(parseOut);

		int count = countNodeVal;
		ArrayList rightChildValues = new ArrayList();
		ArrayList leftChildValues = new ArrayList();
		
		for (int i = 0; i < (ifToThenVal.size()); i++) {
			// executes only if ifToTehnVal contains op3 as "+" or "-"
			if (ifToThenVal.get(i).equals("+")
					|| ifToThenVal.get(i).equals("-")) {
				countNodeVal++;
				ps.println("n" + countNodeVal + " [label=\""
						+ ifToThenVal.get(i) + "\" fillcolor=3];");
				ps.println("n" + countAssign + " -> n" + countNodeVal + ";");        //  (n12) := -> op3 (-) (n14)
				count++;
				countAssign = countNodeVal;

				ifToThenVal.remove(i);
				inputFileCopy.remove(i);
				
				int x = -1;				
				if(((String) leftChild).matches("[0-9]*")){
					x = new Integer(leftChild.toString()).intValue();
				}

				if((x >= 0) || ((htable.get(leftChild) != null) && htable.get(leftChild).equals("int")) ){
					countNodeVal++;
					ps.println("n" + countNodeVal + " [label=\""
							+ leftChild + "\" fillcolor=3];");
					ps.println("n" + count + " -> n" + countNodeVal + ";");           // (n14) (-) op3 -> A   (left Child) (n15)
				}
				else{
					//throw new Exception("operands of operators should be integers");
					System.err.println("operands of operators should be integers \""+ leftChild + "\"........should be int...");
				}
				
				// goes to Term
				rightChildValues = Term(countAssign, countNodeVal, ifToThenVal, inputFile,             
						inputFileCopy, parseOut);
				
				Object rightChild =  rightChildValues.get(0);
				countNodeVal = (Integer) rightChildValues.get(1);
				
				
				int y = -1;
				if(((String) rightChild).matches("[0-9]*")){
					y = new Integer(rightChild.toString()).intValue();
				}


				if((y >= 0) || ((htable.get(rightChild) != null) && htable.get(rightChild).equals("int")) ){
					countNodeVal++;
					ps.println("n" + countNodeVal + " [label=\""
							+ rightChild + "\" fillcolor=3];");
					ps.println("n" + count + " -> n" + countNodeVal + ";");           // (n16) (-) op3 -> B  (right child) (n18)
				}
				else{
					//throw new Exception("operands of operators should be integers");
					System.err.println("operands of operators should be integers \""+ rightChild + "\"........should be int...");
				}
				
				leftChild = null;
				rightChild = null;

			}
		}
		
		leftChildValues.add(leftChild);
		leftChildValues.add(countNodeVal);
		
		return leftChildValues;
	}

	/**
	 * Executes the Production Rule: <term> ::= <factor> <factorPrime>
	 * 
	 * @param countNodeVal
	 * @param ifToThenVal
	 * @param inputFile
	 * @param inputFileCopy
	 * @param parseOut
	 * @return
	 * @throws Exception 
	 */
	public ArrayList Term(int countAssign, int countNodeVal, ArrayList ifToThenVal,
			ArrayList inputFile, ArrayList inputFileCopy, OutputStream parseOut) throws Exception {
		PrintStream ps = new PrintStream(parseOut);

		int count = countNodeVal;
		int count1 = count;
		ArrayList rightChildValues = new ArrayList();

		// goes to Factor
		ArrayList leftChildValues = Factor(countAssign, countNodeVal, ifToThenVal, inputFile,
				inputFileCopy, parseOut);
		
		Object leftChild = leftChildValues.get(0);
		countNodeVal = (Integer) leftChildValues.get(1);
		

		// executes only if the ifToThenVal contains op2 as "*" , "div" , "mod"

		// goes to FactorPrime
		rightChildValues = FactorPrime(countAssign, leftChild, countNodeVal, ifToThenVal, inputFile,
				inputFileCopy, parseOut);
		
		
		return rightChildValues;
	}

	/**
	 * Executes the Production Rule: <factorPrime> ::= op2 <factor> | epsilon
	 * @param leftChild2 
	 * 
	 * @param countNodeVal
	 * @param ifToThenVal
	 * @param inputFile
	 * @param inputFileCopy
	 * @param parseOut
	 * @return
	 * @throws Exception 
	 */
	public ArrayList FactorPrime(int countAssign, Object leftChild, int countNodeVal, ArrayList ifToThenVal,
			ArrayList inputFile, ArrayList inputFileCopy, OutputStream parseOut) throws Exception {

		PrintStream ps = new PrintStream(parseOut);

		for (int i = 0; i < (ifToThenVal.size()); i++) {
			// executes only of the ifToThenVal contains op2 as "*" or "div" or
			// "mod"
			if (ifToThenVal.get(i).equals("*")
					|| ifToThenVal.get(i).equals("div")
					|| ifToThenVal.get(i).equals("mod")) {
				int count = countNodeVal;  //count = 13
					
				          
				countNodeVal++;
				ps.println("n" + countNodeVal + " [label=\""
						+ ifToThenVal.get(i) + "\" fillcolor=3];");
				ps.println("n" + countAssign + " -> n" + countNodeVal + ";");         //(n12) := -> op2 ie * (n14)
				count++;    //count = 14
				
				countAssign = countNodeVal;

				ifToThenVal.remove(i);
				inputFileCopy.remove(i);
				
				int x = -1;				
				if(((String) leftChild).matches("[0-9]*")){
					x = new Integer(leftChild.toString()).intValue();
				}

				if((x >= 0) || ((htable.get(leftChild) != null) && htable.get(leftChild).equals("int")) ){
					countNodeVal++;
					ps.println("n" + countNodeVal + " [label=\""
							+ leftChild + "\" fillcolor=3];");
					ps.println("n" + count + " -> n" + countNodeVal + ";");        //(n14) left child op2 -> factor(ident|num|boollit|lp <e> rp (n15)
				}
				else{
					//throw new Exception("operands of operators should be integers");
					System.err.println("operands of operators should be integers "+ leftChild + "........should be int...");
				}



				// goes to Factor
				ArrayList leftChildValues = Factor(countAssign, countNodeVal, ifToThenVal, inputFile,
						inputFileCopy, parseOut);
				
				Object rightChild = leftChildValues.get(0);
				countNodeVal = (Integer) leftChildValues.get(1);
				
				int y = -1;
				if(((String) rightChild).matches("[0-9]*")){
					y = new Integer(rightChild.toString()).intValue();
				}

				
				if(rightChild != null){
					if((y >= 0) || ((htable.get(rightChild) != null) && htable.get(rightChild).equals("int")) ){
						countNodeVal++;
						ps.println("n" + countNodeVal + " [label=\""
								+ rightChild + "\" fillcolor=3];");
						ps.println("n" + count + " -> n" + countNodeVal + ";");        //(n14) right child op2 -> factor(ident|num|boollit|lp <e> rp (n16)
					}
					else{
						//throw new Exception("operands of operators should be integers");
						System.err.println("operands of operators should be integers "+ rightChild + "........should be int...");
					}
				}

				leftChild = null;
				rightChild = null;
			}
		}
		
		ArrayList rightChildValues = new ArrayList();
		rightChildValues.add(leftChild);
		rightChildValues.add(countNodeVal);
		return rightChildValues;	
		
	}

	/**
	 * Executes the Production Rule: <factor> ::= ident | num | boollit | LP
	 * <expression> RP
	 * 
	 * @param countNodeVal
	 * @param ifToThenVal
	 * @param inputFile
	 * @param inputFileCopy
	 * @param parseOut
	 * @return
	 * @throws Exception 
	 */
	public ArrayList Factor(int countAssign, int countNodeVal, ArrayList ifToThenVal,
			ArrayList inputFile, ArrayList inputFileCopy, OutputStream parseOut) throws Exception {
		PrintStream ps = new PrintStream(parseOut);

		final int count = countNodeVal;
		Object leftChild = null;
		ArrayList leftChildValues = new ArrayList();

		for (int i = 0; i < (ifToThenVal.size()); i++) {

			// executes if the value is an "ident"
			if (((String) ifToThenVal.get(i)).matches("[A-Z][A-z0-9]*")) {

				leftChild = ifToThenVal.get(i);
								
				ifToThenVal.remove(i);
				inputFileCopy.remove(i);       //removes Y
				
				leftChildValues.add(leftChild);
				leftChildValues.add(countNodeVal);
				
				return leftChildValues;

			}

			// executes if the value is "num"
			else if (((String) ifToThenVal.get(i)).matches("[0-9]*")) {
				//try{
				if((Integer.parseInt((String) ifToThenVal.get(i))) >=0 && (Integer.parseInt((String) ifToThenVal.get(i))) <= 2147483647){
			
				leftChild = ifToThenVal.get(i);

				ifToThenVal.remove(i);
				inputFileCopy.remove(i);

				leftChildValues.add(leftChild);
				leftChildValues.add(countNodeVal);
				
				return leftChildValues;
				}
				//}
				//catch(Exception e){
				else{
					System.out.println("Num value out of range...The literal value should be numbers 0 through 2147483647");
					//System.out.println(e.getMessage());
					//throw new Exception("Num value out of range...The literal value should be numbers 0 through 2147483647");
					System.exit(0);
				}

			}

			// executes if the value is a "boollit" as "true" or "false"
			else if (ifToThenVal.get(i).equals("true")
					|| ifToThenVal.get(i).equals("false")) {


				leftChild = ifToThenVal.get(i);

				ifToThenVal.remove(i);
				inputFileCopy.remove(i);

				leftChildValues.add(leftChild);
				leftChildValues.add(countNodeVal);
				
				return leftChildValues;

			}

			// executes if the value is left parenthesis as "("
			else if (ifToThenVal.get(i).equals("(")) {


				ifToThenVal.remove(i);
				inputFileCopy.remove(i);


				// goes to function Expression
				ArrayList rightChildValues = Expression(countAssign, countNodeVal, ifToThenVal, inputFile,
						inputFileCopy, parseOut);
				
				Object rightChild = rightChildValues.get(0);
				countNodeVal = (Integer) rightChildValues.get(1);
				

				
				if(inputFileCopy.get(i).equals(")")){
					ifToThenVal.remove(i);
					inputFileCopy.remove(i);
				}
				else 
					throw new Exception("right parenthesis missing");

				leftChildValues.add(rightChild);
				leftChildValues.add(countNodeVal);
				System.out.println("countNodeVal = "+countNodeVal);
				
				return leftChildValues;
			}
			else
				throw new Exception("given input does not match the production rules");

		}
		
		return leftChildValues;

	}

	/**
	 * Executes the Production Rule: <assignment> ::= ident ASGN
	 * <assignmentPrime> <assignmentPrime ::= readint | <expression>
	 * 
	 * @param countNodeVal
	 * @param assignValues
	 * @param inputFileCopyVal1
	 * @param ifToThenVal
	 * @param inputFile
	 * @param inputFileCopy
	 * @param parseOut
	 * @return
	 * @throws Exception 
	 */
	public int Assignment(int countAssign1, int countNodeVal, ArrayList assignValues,
			Object inputFileCopyVal1, ArrayList ifToThenVal,
			ArrayList inputFile, ArrayList inputFileCopy, OutputStream parseOut) throws Exception

	{
		PrintStream ps = new PrintStream(parseOut);

		int count = countNodeVal;     //count=11


		// executes if <expressionPrime> is readint
		if (inputFileCopyVal1.equals("readint")) {

			countNodeVal++;
			ps.println("n" + countNodeVal + " [label=\":= " + inputFileCopyVal1
					+ "\"];");
			ps.println("n" + countAssign1 + " -> n" + countNodeVal + ";");

//			int leftChild = 0;
//			if(((String) assignValues.get(0)).matches("[A-Z][A-z0-9]*")){			
//				leftChild = new Integer(assignValues.get(0).toString()).intValue();     //leftChild = (Integer) assignValues.get(0);  
//			}
			
			if(((htable.get(assignValues.get(0)) != null) && htable.get(assignValues.get(0)).equals("int"))){				
				countNodeVal++;
				ps.println("n" + countNodeVal + " [label=\""
						+ assignValues.get(0) + "\"fillcolor=3];");
				ps.println("n" + (countNodeVal-1) + " -> n" + countNodeVal + ";");
			}
			else{
				//throw new Exception("operands of operators should be integers");
				System.err.println("Only integer variables may be assigned the result of readint... ");
			}

			
			ifToThenVal.remove(0);
			inputFileCopy.remove(0);

		}
		// executes if <expressionPrime> is <expression>
		else {

			countNodeVal++;
			ps.println("n" + countNodeVal + " [label=\":= \" fillcolor=3];");                   
			ps.println("n" + countAssign1 + " -> n" + countNodeVal + ";");              //(n11) stmt list -> := (n12)
			
			//count++;                 //count = 14
			int countAssign = count+1;
			
			if(((htable.get(assignValues.get(0)) != null) && htable.get(assignValues.get(0)).equals("int"))){
			countNodeVal++;
			ps.println("n" + countNodeVal + " [label=\""
					+ assignValues.get(0) + "\" fillcolor=3];");
			ps.println("n" + countAssign + " -> n" + countNodeVal + ";");              //(n12) := -> "A" (n13)
			}
			else{
				throw new Exception("The left-hand of assignment must be a variable... ");
				//throw new Exception("operands of operators should be integers");
				//System.err.println("The left-hand of assignment must be a variable... ");
			}
			
			ArrayList rightChildValues = Expression(countAssign, countNodeVal, ifToThenVal, inputFile,
					inputFileCopy, parseOut);
			
			Object rightChild = rightChildValues.get(0);
			countNodeVal = (Integer) rightChildValues.get(1);
			
		}
		return countNodeVal;

	}

	/**
	 * Executes the Production Rule: <declarations> ::= VAR ident AS <type> SC
	 * <declarations>
	 * 
	 * @param countNodeVal
	 * @param inputFileCopyVal1
	 * @param inputFileCopyVal2
	 * @param inputFileCopyVal3
	 * @param inputFileCopyVal4
	 * @param inputFileCopyVal5
	 * @param inputFileCopyVal6
	 * @param inputFileCopy
	 * @param inputFile
	 * @param parseOut
	 * @return
	 */
	public int Declarations(int countNodeVal, Object inputFileCopyVal1,
			Object inputFileCopyVal2, Object inputFileCopyVal3,
			Object inputFileCopyVal4, Object inputFileCopyVal5,
			Object inputFileCopyVal6, ArrayList inputFileCopy,
			ArrayList inputFile, OutputStream parseOut)

	{
		PrintStream ps = new PrintStream(parseOut);
		
		// prints var decl
		countNodeVal++;
		ps.println("n" + countNodeVal + " [label=\"" + inputFileCopyVal1
				+ " decl\"];");
		ps.println("n1 -> n" + (countNodeVal) + ";");        // (n1) decl list -> var decl (n2)
																	
		
		int count = countNodeVal; // count = 2 

		// prints variable
		countNodeVal++;
		ps.println("n" + countNodeVal + " [label=\"\\\""
				+ inputFileCopyVal2 + "\\\"\"];");
		ps.println("n" + count + "-> n" + (countNodeVal) + ";");                 // (n2) var decl -> "A" (n3)
																	

		
		// prints type as int or bool
		countNodeVal++;
		ps.println("n" + countNodeVal + " [label=\"" + inputFileCopyVal4
				+ "\"];");
		ps.println("n" + count + "-> n" + (countNodeVal) + ";");				          // (n2) var decl -> int (n4)
																				



		return countNodeVal;

	}
	
	
	public void SymbolTable(String var  , String type){
		
		if(htable == null)
			htable = new Hashtable(); 
	    
	    // Add key/value pairs to the map
		htable.put(var, type);
		
//		System.out.println(hashTable);

//		hashTable.put("x", new Integer(1));
//		hashTable.put("y", new Integer(2));
//		hashTable.put("z", new Integer(3));
//		
				
	}
	

} // end class parser
