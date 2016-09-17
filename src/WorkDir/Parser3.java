//package edu.utsa.tl15;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;

/*
 * This is the Parser which implements all the Production Rules for the TL15 grammar
 */

public class Parser3 {
	// creates a hash table
	Hashtable htable = null;
	Hashtable htablereg = null;	
	Hashtable htablevarnames = null;

	public Parser3(InputStream tl15In, OutputStream parseOut) throws Exception {

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
//		System.out.println(inputFileCopy.size());
//		System.out.println(allKeywords.size());

		// for (int i = 0; i < inputFileCopy.size(); i++) {
		// System.out.println(inputFileCopy.get(i));
		// // System.out.println(allKeywords.get(i));
		//
		// if (inputFileCopy.contains("(") && inputFileCopy.contains(")")) {
		// continue;
		// }
		// else
		// throw new Exception("one of the parenthesis is missing");
		// }

		for (int i = 0; i < allKeywords.size(); i++) {
			for (int j = 0; j < inputFileCopy.size(); j++) {
//				System.out.println(inputFileCopy.get(j));
//				System.out.println(allKeywords.get(i));

				if (inputFileCopy.get(j).equals(allKeywords.get(i))) {
//					System.out.println("inputFileCopy.get(j): "
//							+ inputFileCopy.get(j));
//					System.out.println("allKeywords.get(i): "
//							+ allKeywords.get(i));
					countWords++;
					//System.out.println(countWords);
				}

			}
		}

//		System.out.println(inputFileCopy.size());
//		System.out.println(allKeywords.size());
//		System.out.println(countWords);

		// counts "ident" values
		int countId = 0;
		for (int k = 0; k < inputFileCopy.size(); k++) {
			if (((String) inputFileCopy.get(k)).matches("[A-Z][A-z0-9]*")
					&& !(inputFileCopy.get(k).equals("PROGRAM"))
					&& !(inputFileCopy.get(k).equals("BEGIN"))
					&& !(inputFileCopy.get(k).equals("END"))
					&& !(inputFileCopy.get(k).equals("VAR"))
					&& !(inputFileCopy.get(k).equals("AS"))
					&& !(inputFileCopy.get(k).equals("INT"))
					&& !(inputFileCopy.get(k).equals("BOOL"))
					&& !(inputFileCopy.get(k).equals("IF"))
					&& !(inputFileCopy.get(k).equals("THEN"))
					&& !(inputFileCopy.get(k).equals("ELSE"))
					&& !(inputFileCopy.get(k).equals("WHILE"))
					&& !(inputFileCopy.get(k).equals("DO"))
					&& !(inputFileCopy.get(k).equals("WRITEINT"))
					&& !(inputFileCopy.get(k).equals("READINT"))) {
				countId++;
				// System.out.println(inputFileCopy.get(k));
			}
		}

		// counts "num" values
		int countNum = 0;
		for (int k = 0; k < inputFileCopy.size(); k++) {
			if (((String) inputFileCopy.get(k)).matches("[0-9]*")) {
				countNum++;

			}
		}

//		System.out.println(countWords);
		countWords = countWords + (countId + countNum);
//		System.out.println(countWords);
//		System.out.println(countId);
//		System.out.println(countNum);

		if (countWords == inputFileCopy.size()) {
			System.out.println("success : Valid input File");
		} else {
			System.out.println("Invalid input : Please check the input file");
			System.exit(0);

		}

		int countHashKeys = 0;
		// for hash table
		// puts all the declared variables into hash table
		for (int m = 0; m < inputFileCopy.size(); m++) {
			if (((String) inputFileCopy.get(m)).matches("[A-Z][A-z0-9]*")) {
				if (inputFileCopy.get(m + 2).equals("int")) {
					if (htable == null) {
						SymbolTable(inputFileCopy.get(m).toString(),
								inputFileCopy.get(m + 2).toString());
						// countHashKeys++;
					} else if ((htable != null)
							&& (htable.containsKey(inputFileCopy.get(m)))) {
						// throw new
						// Exception("Each variable may only be declared once...");
						System.err.println("Each variable may only be declared once...");
					} else {
						SymbolTable(inputFileCopy.get(m).toString(),
								inputFileCopy.get(m + 2).toString());
					}
				} else if (inputFileCopy.get(m + 2).equals("bool")) {
					if (htable == null) {
						SymbolTable(inputFileCopy.get(m).toString(),
								inputFileCopy.get(m + 2).toString());
						// countHashKeys++;
					} else if ((htable != null)
							&& (htable.containsKey(inputFileCopy.get(m)))) {
						// throw new
						// Exception("Each variable may only be declared once...");
						System.err.println("Each variable may only be declared once...");
					} else {
						SymbolTable(inputFileCopy.get(m).toString(),
								inputFileCopy.get(m + 2).toString());
					}
				}
			}
		}
		
		// This puts values for the symbol table "SymbolTableRegisters" which contains all the offset
		//      values for registers
		int num = 0;
		for(int i=0;i<32;i++)
			{
				num = num - 4;
				SymbolTableRegisters("R"+i, num);
			}
				

		// checks whether All the variables are declared or not
		for (int n = 0; n < inputFileCopy.size(); n++) {
			if (((String) inputFileCopy.get(n)).matches("[A-Z][A-z0-9]*")) {
				if (htable.containsKey(inputFileCopy.get(n))) {
					continue;
				} else {
					// throw new
					// Exception(" All variables must be declared with a particular type...");
					System.err.println("All variables must be declared with a particular type...");
				}
			}
		}

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
			ArrayList inputFile, ArrayList inputFileCopy, OutputStream parseOut)
			throws Exception {
		int countNodeVal = 0;
		int regCount = -1;
		ArrayList countValues = new ArrayList();

		PrintStream ps = new PrintStream(parseOut);

		// Stating point for the parse tree
		ps.print("             ");
		ps.println(".data");
		ps.println("newline:     .asciiz \"\\n\"");
		ps.print("             ");
		ps.println(".text");
		ps.print("             ");
		ps.println(".globl main");

		for (int i = 0; i < (inputFileCopy.size());) {
			// executes if the inputFileCopy at this point has "program"
			// int decllistNode = 0;
			if (inputFileCopy.get(i).equals("program")) {

				ps.println("main:");
				ps.print("             ");
				ps.println("li $fp, 0x7ffffffc");
//				ps.print("n" + countNodeVal + " [label= \" B" + countNodeVal
//						+ ": ");

				inputFileCopy.remove(i);
			}

			// executes if the inputFileCopy at this point has "VAR"
			if (inputFileCopy.get(i).equals("var")) {

				regCount++;
				countValues = Declarations(countNodeVal, regCount,
						inputFileCopy.get(i), inputFileCopy.get(i + 1),
						inputFileCopy.get(i + 2), inputFileCopy.get(i + 3),
						inputFileCopy.get(i + 4), inputFileCopy.get(i + 5),
						inputFile, inputFileCopy, parseOut);

				countNodeVal = (Integer) countValues.get(0);
				regCount = (Integer) countValues.get(1);

				inputFileCopy.remove(i);
				inputFileCopy.remove(i);
				inputFileCopy.remove(i);
				inputFileCopy.remove(i);
				inputFileCopy.remove(i);
			}

			// executes if the inputFileCopy at this point has "BEGIN"
			if (inputFileCopy.get(i).equals("begin")) {
				
				System.out.println("htablevarnames is "+ htablevarnames);
				inputFileCopy.remove(i);				
			}

			if (inputFileCopy.get(i).equals("readint")) {
				throw new Exception("syntax error in using readint");
			}

			// executes if the inputFileCopy at this point has "ident" value or
			// "if" or "while" or "writeint"
			if ((((String) inputFileCopy.get(i)).matches("[A-Z][A-z0-9]*") && inputFileCopy
					.get(i + 1).equals(":="))
					|| inputFileCopy.get(i).equals("if")
					|| inputFileCopy.get(i).equals("while")
					|| inputFileCopy.get(i).equals("writeint")) {

				final int count = countNodeVal;
				ArrayList regCountValues = new ArrayList();
				regCountValues.add(countNodeVal);
				regCountValues.add(regCount);

				// goes to Statement
				regCountValues = Statement(regCountValues, regCount, count,
						countNodeVal, inputFile, inputFileCopy, parseOut);

				countNodeVal = (Integer) regCountValues.get(0);
				regCount = (Integer) regCountValues.get(1);

				inputFileCopy.remove(0);

				ArrayList ifToThenVal = new ArrayList();

				// goes to StatementSequence
				regCountValues = StatementSequence(regCountValues, regCount,
						count, countNodeVal, ifToThenVal, inputFile,
						inputFileCopy, parseOut);

				countNodeVal = (Integer) regCountValues.get(0);
				regCount = (Integer) regCountValues.get(1);

			}

			// executes if the inputFileCopy at this point has "END"
			if (inputFileCopy.get(i).equals("end")) {

				inputFileCopy.remove(i);

				ps.print("             ");
				ps.println("j Bexit ");
				
				countNodeVal++;
				
				ps.println();
				ps.println("Bexit:       li $a0, 10");
				ps.print("             ");
				ps.println("li $v0, 10");
				ps.print("             ");
				ps.println("syscall");

			}
		}
	}

	/**
	 * Executes the production rule: <statementSequence> ::= <statement> SC
	 * <statementSequence>|epsilon
	 * 
	 * @param regCountValues
	 * @param regCount
	 * @param count
	 * @param countNodeVal
	 * @param ifToThenVal
	 * @param inputFile
	 * @param inputFileCopy
	 * @param parseOut
	 * @return
	 * @throws Exception
	 */
	// countAssign
	public ArrayList StatementSequence(ArrayList regCountValues, int regCount,
			int count, int countNodeVal, ArrayList ifToThenVal,
			ArrayList inputFile, ArrayList inputFileCopy, OutputStream parseOut)
			throws Exception {
		final int count1 = countNodeVal;
		// ArrayList regCountValues = new ArrayList();

		PrintStream ps = new PrintStream(parseOut);

		if (inputFileCopy.get(0).equals("readint")) {
			throw new Exception("syntax error in using readint");
		}

		// executes only if the first value in the ArrayList inputFileCopy is
		// equal to "writeint" or "if" or "while" or any "ident" value otherwise
		// StatementSequence should be epsilon
		if (inputFileCopy.get(0).equals("writeint")
				|| inputFileCopy.get(0).equals("if")
				|| inputFileCopy.get(0).equals("while")
				|| (((String) inputFileCopy.get(0)).matches("[A-Z][A-z0-9]*") && inputFileCopy
						.get(1).equals(":="))) {

			regCountValues = Statement(regCountValues, regCount, count,
					countNodeVal, inputFile, inputFileCopy, parseOut);

			countNodeVal = (Integer) regCountValues.get(0);
			regCount = (Integer) regCountValues.get(1);

			inputFileCopy.remove(0); // removes ;

			regCountValues = StatementSequence(regCountValues, regCount, count,
					countNodeVal, ifToThenVal, inputFile, inputFileCopy,
					parseOut);

		}
		return regCountValues;
	}

	/**
	 * Executes the production rule: <statement> ::= <assignment> |
	 * <ifStatement> | <whileStatement> | <writeint>
	 * 
	 * @param regCountValues
	 * @param regCount
	 * @param count
	 * @param countNodeVal
	 * @param inputFile
	 * @param inputFileCopy
	 * @param parseOut
	 * @return
	 * @throws Exception
	 */
	public ArrayList Statement(ArrayList regCountValues, int regCount,
			int count, int countNodeVal, ArrayList inputFile,
			ArrayList inputFileCopy, OutputStream parseOut) throws Exception {

		PrintStream ps = new PrintStream(parseOut);
		// ArrayList regCountValues = new ArrayList();

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
				regCountValues = Assignment(regCountValues, regCount, count,
						countNodeVal, assignValues, inputFileCopy.get(i),
						ifToThenVal, inputFile, inputFileCopy, parseOut);

				countNodeVal = (Integer) regCountValues.get(0);
				regCount = (Integer) regCountValues.get(1);

				return regCountValues;

			}
			// executes <statement> ::= <ifStatement>
			else if (inputFileCopy.get(i).equals("if")) {

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

				inputFileCopy.remove(i); // removes if

				// goes to ifStatement
				regCountValues = IfStatement(regCountValues, regCount, count,
						countNodeVal, ifToThenVal, inputFile, inputFileCopy,
						parseOut);

				countNodeVal = (Integer) regCountValues.get(0);
				regCount = (Integer) regCountValues.get(1);

				return regCountValues;
			}
			// executes <statement> ::= <whileStatement>
			else if (inputFileCopy.get(i).equals("while")) {

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
				regCountValues = WhileStatement(regCountValues, regCount,
						count, countNodeVal, ifToThenVal, inputFile,
						inputFileCopy, parseOut);

				countNodeVal = (Integer) regCountValues.get(0);
				regCount = (Integer) regCountValues.get(1);

				return regCountValues;
			}
			// executes <statement> ::= <writeint>
			else if (inputFileCopy.get(i).equals("writeint")) {

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

				// goes to writeint
				regCountValues = writeint(regCountValues, regCount,
						countNodeVal, ifToThenVal, inputFile, inputFileCopy,
						parseOut);

				countNodeVal = (Integer) regCountValues.get(0);
				regCount = (Integer) regCountValues.get(1);

				return regCountValues;

			}
		}
		return regCountValues;

	}

	/**
	 * Executes the Production Rule: <writeint> ::= writeint <expression>
	 * 
	 * @param regCountValues
	 * @param regCount
	 * @param count
	 * @param countNodeVal
	 * @param ifToThenVal
	 * @param inputFile
	 * @param inputFileCopy
	 * @param parseOut
	 * @return
	 * @throws Exception
	 */
	public ArrayList writeint(ArrayList regCountValues, int regCount,
			int countNodeVal, ArrayList ifToThenVal, ArrayList inputFile,
			ArrayList inputFileCopy, OutputStream parseOut) throws Exception {
		PrintStream ps = new PrintStream(parseOut);
		final int count = countNodeVal;
		int countAssign = count;

		// goes to Expression
		ArrayList rightChildValues = Expression(regCount, countAssign,
				countNodeVal, ifToThenVal, inputFile, inputFileCopy, parseOut);

		Object leftChild = rightChildValues.get(0);
		countNodeVal = (Integer) rightChildValues.get(1);
		regCount = (Integer) rightChildValues.get(2);

		regCountValues.set(0, countNodeVal);
		regCountValues.set(1, regCount);

//		ps.print("\\lwriteint R" + regCount);
		
		ps.print("             ");
		ps.println("lw $t2, "+ htablereg.get("R"+regCount) + "($fp)");
		ps.print("             ");
		ps.println("move $a0, $t2");
		ps.print("             ");
		ps.println("li $v0, 1");
		ps.print("             ");
		ps.println("syscall");

		return regCountValues;
	}

	/**
	 * Executes the Production Rule: <whileStatement> ::= WHILE <expression> DO
	 * <statementSequence> END
	 * 
	 * @param regCountValues
	 * @param regCount
	 * @param count
	 * @param countNodeVal
	 * @param ifToThenVal
	 * @param inputFile
	 * @param inputFileCopy
	 * @param parseOut
	 * @return
	 * @throws Exception
	 */
	public ArrayList WhileStatement(ArrayList regCountValues, int regCount,
			int count, int countNodeVal, ArrayList ifToThenVal,
			ArrayList inputFile, ArrayList inputFileCopy, OutputStream parseOut)
			throws Exception {
		PrintStream ps = new PrintStream(parseOut);

		int count1 = countNodeVal;
		int countAssign = count1;
		// ArrayList regCountValues = new ArrayList();

		inputFileCopy.remove(0);

		countNodeVal++;
		ps.println("B" + countNodeVal + ": "); // n3-----B3
		ps.println("             ");
		int countNode1 = countNodeVal;

		// goes to Expression
		ArrayList rightChildValues = Expression(regCount, countAssign,
				countNodeVal, ifToThenVal, inputFile, inputFileCopy, parseOut);

		Object leftChild = rightChildValues.get(0);
		countNodeVal = (Integer) rightChildValues.get(1);
		regCount = (Integer) rightChildValues.get(2);

		regCountValues.set(0, countNodeVal);
		regCountValues.set(1, regCount);

		ps.println("B" + countNodeVal +  ": "); // B4
				
		if (ifToThenVal.contains("writeint")) {
			countAssign = countNodeVal;
		} else {
			countAssign = count1;
		}

		inputFileCopy.remove(0);

		// goes to StatementSequence
		regCountValues = StatementSequence(regCountValues, regCount,
				countAssign, countNodeVal, ifToThenVal, inputFile,
				inputFileCopy, parseOut);

		inputFileCopy.remove(0);

		countNodeVal = (Integer) regCountValues.get(0);
		regCount = (Integer) regCountValues.get(1);

		ps.print("             ");
		ps.println("j B" + (countNodeVal + 1) ); // B5

		countNodeVal++;		
		ps.println("B" + countNodeVal + ": ");

		regCountValues.set(0, countNodeVal);
		regCountValues.set(1, regCount);

		return regCountValues;
	}

	/**
	 * Executes the Production Rule: <ifStatement> ::= IF <expression> THEN
	 * <statementSequence> <elseClause> END
	 * 
	 * @param regCountValues
	 * @param regCount
	 * @param count
	 * @param countNodeVal
	 * @param ifToThenVal
	 * @param inputFile
	 * @param inputFileCopy
	 * @param parseOut
	 * @return
	 * @throws Exception
	 */
	public ArrayList IfStatement(ArrayList regCountValues, int regCount,
			int count, int countNodeVal, ArrayList ifToThenVal,
			ArrayList inputFile, ArrayList inputFileCopy, OutputStream parseOut)
			throws Exception {
		PrintStream ps = new PrintStream(parseOut);

		int count1 = countNodeVal; // countNodeVal = count1 = if (n11)
		int countAssign = count1; // countAssign = (n11)
		// ArrayList regCountValues = new ArrayList();

		// goes to Expression
		ArrayList rightChildValues = Expression(regCount, countAssign,
				countNodeVal, ifToThenVal, inputFile, inputFileCopy, parseOut);

		Object leftChild = rightChildValues.get(0);
		countNodeVal = (Integer) rightChildValues.get(1);
		regCount = (Integer) rightChildValues.get(2);

		regCountValues.set(0, countNodeVal);
		regCountValues.set(1, regCount);

		countAssign = countNodeVal;

		inputFileCopy.remove(0); // removes then
		
		ps.println("B" + countNodeVal + ": ");
		int countNode1 = countNodeVal;

		// goes to StatementSequence
		regCountValues = StatementSequence(regCountValues, regCount,
				countAssign, countNodeVal, ifToThenVal, inputFile,
				inputFileCopy, parseOut);
		countNodeVal = (Integer) regCountValues.get(0);
		regCount = (Integer) regCountValues.get(1);

		ps.print("             ");
		ps.println("j B" + (countNodeVal + 2));

		if (inputFileCopy.get(0).equals("else")) {
			countNodeVal++;
			ps.println("B" + countNodeVal + ": ");

			countAssign = countNodeVal;

			// goes to ElseClause
			regCountValues = ElseClause(regCountValues, regCount, countAssign,
					count, countNodeVal, inputFile, inputFileCopy, parseOut);

			countNodeVal = (Integer) regCountValues.get(0);
			regCount = (Integer) regCountValues.get(1);

			ps.print("             ");
			ps.println("j B" + (countNodeVal + 1));
			int countNode2 = countNodeVal;

		} else {
			countAssign = countNodeVal;			
		}

		inputFileCopy.remove(0); // removes end

		regCountValues.set(0, countNodeVal);
		regCountValues.set(1, regCount);

		return regCountValues;

	}

	/**
	 * Executes the Production Rule: <elseClause> ::= ELSE <statementSequence> |
	 * epsilon
	 * 
	 * @param regCountValues
	 * @param regCount
	 * @param countAssign
	 * @param count
	 * @param countNodeVal
	 * @param inputFile
	 * @param inputFileCopy
	 * @param parseOut
	 * @return
	 * @throws Exception
	 */
	public ArrayList ElseClause(ArrayList regCountValues, int regCount,
			int countAssign, int count, int countNodeVal, ArrayList inputFile,
			ArrayList inputFileCopy, OutputStream parseOut) throws Exception {
		PrintStream ps = new PrintStream(parseOut);

		int count1 = countNodeVal;
		// ArrayList regCountValues = new ArrayList();

		inputFileCopy.remove(0);

		ArrayList ifToThenVal = new ArrayList();

		// goes to StatementSequence
		regCountValues = StatementSequence(regCountValues, regCount,
				countAssign, countNodeVal, ifToThenVal, inputFile,
				inputFileCopy, parseOut);

		return regCountValues;
	}

	/**
	 * Executes the Production Rule: <expression> ::= <simpleExpression>
	 * <simpleExpressionPrime>
	 * 
	 * @param regCount
	 * @param countAssign
	 * @param countNodeVal
	 * @param ifToThenVal
	 * @param inputFile
	 * @param inputFileCopy
	 * @param parseOut
	 * @return
	 * @throws Exception
	 */
	public ArrayList Expression(int regCount, int countAssign,
			int countNodeVal, ArrayList ifToThenVal, ArrayList inputFile,
			ArrayList inputFileCopy, OutputStream parseOut) throws Exception {
		PrintStream ps = new PrintStream(parseOut);

		int count = countNodeVal;
		int count1 = count;
		ArrayList rightChildValues = new ArrayList();

		ArrayList leftChildValues = SimpleExpression(regCount, countAssign,
				countNodeVal, ifToThenVal, inputFile, inputFileCopy, parseOut);

		Object leftChild = leftChildValues.get(0);
		countNodeVal = (Integer) leftChildValues.get(1);
		regCount = (Integer) leftChildValues.get(2);

		// simpleExpressionPrime contains the op4 elements like "="
		// , "<>", "<", ">", "<=", ">="

		// goes to SimpleExpressionPrime
		rightChildValues = SimpleExpressionPrime(regCount, countAssign,
				leftChild, countNodeVal, ifToThenVal, inputFile, inputFileCopy,
				parseOut);

		if (rightChildValues.get(0) != null) {
			if (leftChild.equals("true") || leftChild.equals("false")) {
//				regCount++;
//				ps.print("\\lloadI " + leftChild + " => R" + regCount);
				
				regCount++;
				ps.print("             ");
				if (leftChild.equals("true")){
					ps.println("li $t0, 1");
				}
				else if (leftChild.equals("false")){
					ps.println("li $t0, 0");
				}
				//System.out.println("htable value:"+ htablereg.get("R"+regCount));
				ps.print("             ");
				ps.println("sw $t0, "+ htablereg.get("R"+regCount) +"($fp)");		
				
			} else {
//				regCount++;
//				ps.print("\\lloadI @" + leftChild + " => R" + regCount);
//				regCount++;
//				ps.print("\\lloadAO Rarp, R" + (regCount - 1) + " => R"
//						+ regCount);
				
				
				regCount++;
				ps.print("             ");
				ps.println("li $t0, 0");			
				//System.out.println("htable value:"+ htablereg.get("R"+regCount));
				ps.print("             ");
				ps.println("sw $t0, "+ htablereg.get("R"+regCount) +"($fp)");
				
				//regCount++;
				
				//ps.print("\\lloadAO Rarp, R" + (regCount - 1) + " => R" + regCount);
				
				ps.print("             ");
				ps.println("lw $t0, "+ htablereg.get("R"+regCount)+"($fp)");	
				ps.print("             ");
				ps.println("addu $t1, $t0, $fp");
				
				regCount++;
				
				ps.print("             ");
				ps.println("sw $t1, "+ htablereg.get("R"+regCount) +"($fp)");
				
				
			}

			rightChildValues.set(0, null);
			rightChildValues.set(1, countNodeVal);
			rightChildValues.set(2, regCount);

		}

		return rightChildValues;

	}

	/**
	 * Executes the Production Rule: <simpleExpressionPrime> ::= epsilon | op4
	 * <simpleExpression>
	 * 
	 * @param regCount
	 * @param countAssign
	 * @param leftChild
	 * @param countNodeVal
	 * @param ifToThenVal
	 * @param inputFile
	 * @param inputFileCopy
	 * @param parseOut
	 * @return
	 * @throws Exception
	 */
	public ArrayList SimpleExpressionPrime(int regCount, int countAssign,
			Object leftChild, int countNodeVal, ArrayList ifToThenVal,
			ArrayList inputFile, ArrayList inputFileCopy, OutputStream parseOut)
			throws Exception {

		int count = countNodeVal;
		int regCountLeft = 0;
		int regCountRight = 0;
		ArrayList leftChildValues = new ArrayList();
		PrintStream ps = new PrintStream(parseOut);

		for (int i = 0; i < (ifToThenVal.size()); i++) {
			// executes only if the ifToThenVal has an element which equals to
			// op4 like "=" , "<>", "<", ">", "<=", ">="
			// System.out.println("leftChild : "+ leftChild);
			if (ifToThenVal.get(i).equals("=")
					|| ifToThenVal.get(i).equals("<>")
					|| ifToThenVal.get(i).equals("<")
					|| ifToThenVal.get(i).equals(">")
					|| ifToThenVal.get(i).equals("<=")
					|| ifToThenVal.get(i).equals(">=")) {

				Object val = ifToThenVal.get(i);

				ifToThenVal.remove(i);
				inputFileCopy.remove(i);

				int x = -1;
				if (((String) leftChild).matches("[0-9]*")) {
					x = new Integer(leftChild.toString()).intValue();
				}

				if ((htable.get(leftChild) != null)
						&& htable.get(leftChild).equals("int")) {
//					regCount++;
//					ps.print("\\lloadI @" + leftChild + " => R" + regCount);
//					regCount++;
//					ps.print("\\lloadAO Rarp, R" + (regCount - 1) + " => R"
//							+ regCount);

					
					regCount++;
					ps.print("             ");
					ps.println("li $t0, 0");			
					//System.out.println("htable value:"+ htablereg.get("R"+regCount));
					ps.print("             ");
					ps.println("sw $t0, "+ htablereg.get("R"+regCount) +"($fp)");
					
					//regCount++;
					
					//ps.print("\\lloadAO Rarp, R" + (regCount - 1) + " => R" + regCount);
					
					ps.print("             ");
					ps.println("lw $t0, "+ htablereg.get("R"+regCount)+"($fp)");	
					ps.print("             ");
					ps.println("addu $t1, $t0, $fp");
					
					regCount++;
					
					ps.print("             ");
					ps.println("sw $t1, "+ htablereg.get("R"+regCount) +"($fp)");
					
					
					regCountLeft = regCount;
				} else if (x >= 0) {
					// throw new
					// Exception("operands of OP4 operator should be integers and the first operand should be a variable ");
					System.err.println("operands of OP4 operator should be integers and the first operand should be a variable... ");
				} else
					// throw new
					// Exception("operands of operators should be integers");
					System.err.println("operands of operators should be integers... \""
									+ leftChild + "\"........should be int...");

				// goes to SimpleExression
				ArrayList rightChildValues = SimpleExpression(regCount,
						countAssign, countNodeVal, ifToThenVal, inputFile,
						inputFileCopy, parseOut);

				Object rightChild = rightChildValues.get(0);
				countNodeVal = (Integer) rightChildValues.get(1);
				regCount = (Integer) rightChildValues.get(2);

				//System.out.println(rightChild.toString());

				int y = -1;
				if (((String) rightChild).matches("[0-9]*")) {
					y = new Integer(rightChild.toString()).intValue();
				}

				if ((y >= 0)
						|| ((htable.get(rightChild) != null) && htable.get(
								rightChild).equals("int"))) {

					if ((htable.get(rightChild) != null)
							&& htable.get(rightChild).equals("int")) {
//						regCount++;
//						ps.print("\\lloadI @" + rightChild + " => R" + regCount);
//						regCount++;
//						ps.print("\\lloadAO Rarp, R" + (regCount - 1) + " => R"
//								+ regCount);
					

						regCount++;
						ps.print("             ");
						ps.println("li $t0, 0");			
						//System.out.println("htable value:"+ htablereg.get("R"+regCount));
						ps.print("             ");
						ps.println("sw $t0, "+ htablereg.get("R"+regCount) +"($fp)");
						
						//regCount++;
						
						//ps.print("\\lloadAO Rarp, R" + (regCount - 1) + " => R" + regCount);
						
						ps.print("             ");
						ps.println("lw $t0, "+ htablereg.get("R"+regCount)+"($fp)");	
						ps.print("             ");
						ps.println("addu $t1, $t0, $fp");
						
						regCount++;
						
						ps.print("             ");
						ps.println("sw $t1, "+ htablereg.get("R"+regCount) +"($fp)");
						
						
						regCountRight = regCount;
					} else if (y >= 0) {
//						regCount++;
//						ps.print("\\lloadI " + rightChild + " => R" + regCount);
						
						regCount++;
						ps.print("             ");
						ps.println("li $t0, " + rightChild);			
						//System.out.println("htable value:"+ htablereg.get("R"+regCount));
						ps.print("             ");
						ps.println("sw $t0, "+ htablereg.get("R"+regCount) +"($fp)");				
						

						regCountRight = regCount;
					}

				} else {
					// throw new
					// Exception("operands of operators should be integers");
					System.err.println("operands of operators should be integers... \""
									+ rightChild + "\"........should be int...");

				}

				// executes the compare and conditional branch statements
				if (val.equals("=")) {
					regCount++;
//					ps.print("\\lcmp_EQ R" + regCountLeft + ", R"
//							+ regCountRight + " => R" + regCount);
					
					ps.print("             ");
					ps.println("lw $t0, " + htablereg.get("R"+regCountLeft) + "($fp)");
					ps.print("             ");
					ps.println("lw $t1, " + htablereg.get("R"+regCountRight) + "($fp)");
					ps.print("             ");
					ps.println("seq $t2, $t0, $t1");         //set equal
					ps.print("             ");
					ps.println("sw $t2, " + htablereg.get("R"+regCount) + "($fp)");
					
					// regCount++;
					countNodeVal++;
//					ps.print("\\lcbr R" + (regCount) + " -> B" + countNodeVal
//							+ ", B" + (countNodeVal + 1) + " \"]");
					
					ps.print("             ");
					ps.println("lw $t0, " + htablereg.get("R"+regCount) + "($fp)");
					ps.print("             ");
					ps.println("beqz $t0, B" + (countNodeVal + 1));
					ps.print("             ");
					ps.println("j B" + countNodeVal);
					
				} else if (val.equals("<>")) {
					regCount++;
//					ps.print("\\lcmp_NE R" + regCountLeft + ", R"
//							+ regCountRight + " => R" + regCount);
					
					ps.print("             ");
					ps.println("lw $t0, " + htablereg.get("R"+regCountLeft) + "($fp)");
					ps.print("             ");
					ps.println("lw $t1, " + htablereg.get("R"+regCountRight) + "($fp)");
					ps.print("             ");
					ps.println("sne $t2, $t0, $t1");             //set not equal
					ps.print("             ");
					ps.println("sw $t2, " + htablereg.get("R"+regCount) + "($fp)");
					
					// regCount++;
					countNodeVal++;
//					ps.print("\\lcbr R" + (regCount) + " -> B" + countNodeVal
//							+ ", B" + (countNodeVal + 1) + " \"]");
					
					ps.print("             ");
					ps.println("lw $t0, " + htablereg.get("R"+regCount) + "($fp)");
					ps.print("             ");
					ps.println("beqz $t0, B" + (countNodeVal + 1));
					ps.print("             ");
					ps.println("j B" + countNodeVal);
					
				} else if (val.equals("<")) {
					regCount++;
//					ps.print("\\lcmp_LT R" + regCountLeft + ", R"
//							+ regCountRight + " => R" + regCount);
					
					ps.print("             ");
					ps.println("lw $t0, " + htablereg.get("R"+regCountLeft) + "($fp)");
					ps.print("             ");
					ps.println("lw $t1, " + htablereg.get("R"+regCountRight) + "($fp)");
					ps.print("             ");
					ps.println("slt $t2, $t0, $t1");                    //set less than
					ps.print("             ");
					ps.println("sw $t2, " + htablereg.get("R"+regCount) + "($fp)");
					
					// regCount++;
					countNodeVal++;
//					ps.print("\\lcbr R" + (regCount) + " -> B" + countNodeVal
//							+ ", B" + (countNodeVal + 1) + " \"]");
					
					ps.print("             ");
					ps.println("lw $t0, " + htablereg.get("R"+regCount) + "($fp)");
					ps.print("             ");
					ps.println("beqz $t0, B" + (countNodeVal + 1));
					ps.print("             ");
					ps.println("j B" + countNodeVal);
					
				} else if (val.equals(">")) {
					regCount++;
//					ps.print("\\lcmp_GT R" + regCountLeft + ", R"
//							+ regCountRight + " => R" + regCount);
					
					ps.print("             ");
					ps.println("lw $t0, " + htablereg.get("R"+regCountLeft) + "($fp)");
					ps.print("             ");
					ps.println("lw $t1, " + htablereg.get("R"+regCountRight) + "($fp)");
					ps.print("             ");
					ps.println("sgt $t2, $t0, $t1");              //set greater than
					ps.print("             ");
					ps.println("sw $t2, " + htablereg.get("R"+regCount) + "($fp)");
					
					// regCount++;
					countNodeVal++;
//					ps.print("\\lcbr R" + (regCount) + " -> B" + countNodeVal
//							+ ", B" + (countNodeVal + 1) + " \"]");
					
					ps.print("             ");
					ps.println("lw $t0, " + htablereg.get("R"+regCount) + "($fp)");
					ps.print("             ");
					ps.println("beqz $t0, B" + (countNodeVal + 1));
					ps.print("             ");
					ps.println("j B" + countNodeVal);
					
				} else if (val.equals("<=")) {
					regCount++;
//					ps.print("\\lcmp_LE R" + regCountLeft + ", R"
//							+ regCountRight + " => R" + regCount);
					
					ps.print("             ");
					ps.println("lw $t0, " + htablereg.get("R"+regCountLeft) + "($fp)");
					ps.print("             ");
					ps.println("lw $t1, " + htablereg.get("R"+regCountRight) + "($fp)");
					ps.print("             ");
					ps.println("sle $t2, $t0, $t1");            //set less than equal
					ps.print("             ");
					ps.println("sw $t2, " + htablereg.get("R"+regCount) + "($fp)");
					
					// regCount++;
					countNodeVal++;
					// ps.print("\\lcbr R"+
					// (regCount-1)+" -> B"+countNodeVal+", B"+(countNodeVal+1)+" \"]");
//					ps.print("\\lcbr R" + (regCount) + " -> B" + countNodeVal
//							+ ", B" + (countNodeVal + 1) + " \"]");
					
					ps.print("             ");
					ps.println("lw $t0, " + htablereg.get("R"+regCount) + "($fp)");
					ps.print("             ");
					ps.println("beqz $t0, B" + (countNodeVal + 1));
					ps.print("             ");
					ps.println("j B" + countNodeVal);
					
				} else if (val.equals(">=")) {
					regCount++;
//					ps.print("\\lcmp_GE R" + regCountLeft + ", R"
//							+ regCountRight + " => R" + regCount);
					
					ps.print("             ");
					ps.println("lw $t0, " + htablereg.get("R"+regCountLeft) + "($fp)");
					ps.print("             ");
					ps.println("lw $t1, " + htablereg.get("R"+regCountRight) + "($fp)");
					ps.print("             ");
					ps.println("sge $t2, $t0, $t1");            // set greater than equal
					ps.print("             ");
					ps.println("sw $t2, " + htablereg.get("R"+regCount) + "($fp)");
					
					// regCount++;
					countNodeVal++;
//					ps.print("\\lcbr R" + (regCount) + " -> B" + countNodeVal
//							+ ", B" + (countNodeVal + 1) + " \"]");
					
					ps.print("             ");
					ps.println("lw $t0, " + htablereg.get("R"+regCount) + "($fp)");
					ps.print("             ");
					ps.println("beqz $t0, B" + (countNodeVal + 1));
					ps.print("             ");
					ps.println("j B" + countNodeVal);
					
				}

				leftChild = null;
				rightChild = null;
			}

		}
		leftChildValues.add(leftChild);
		leftChildValues.add(countNodeVal);
		leftChildValues.add(regCount);

		return leftChildValues;

	}

	/**
	 * Executes the Production Rule: <simpleExpression> ::= <term> <termPrime>
	 * 
	 * @param regCount
	 * @param countAssign
	 * @param countNodeVal
	 * @param ifToThenVal
	 * @param inputFile
	 * @param inputFileCopy
	 * @param parseOut
	 * @return
	 * @throws Exception
	 */
	public ArrayList SimpleExpression(int regCount, int countAssign,
			int countNodeVal, ArrayList ifToThenVal, ArrayList inputFile,
			ArrayList inputFileCopy, OutputStream parseOut) throws Exception {
		PrintStream ps = new PrintStream(parseOut);

		int count = countNodeVal;
		int count1 = count;
		ArrayList leftChildValues = new ArrayList();

		ArrayList rightChildValues = Term(regCount, countAssign, countNodeVal,
				ifToThenVal, inputFile, inputFileCopy, parseOut);

		Object leftChild = rightChildValues.get(0);
		countNodeVal = (Integer) rightChildValues.get(1);
		regCount = (Integer) rightChildValues.get(2);

		// executes only if ifToThenVal contains op3 as "+" or "-"

		// goes to TermPrime
		leftChildValues = TermPrime(regCount, countAssign, leftChild,
				countNodeVal, ifToThenVal, inputFile, inputFileCopy, parseOut);

		return leftChildValues;
	}

	/**
	 * Executes the Production Rule: <termPrime> ::= op3 <term> | epsilon
	 * 
	 * @param regCount
	 * @param countAssign
	 * @param leftChild
	 * @param countNodeVal
	 * @param ifToThenVal
	 * @param inputFile
	 * @param inputFileCopy
	 * @param parseOut
	 * @return
	 * @throws Exception
	 */
	public ArrayList TermPrime(int regCount, int countAssign, Object leftChild,
			int countNodeVal, ArrayList ifToThenVal, ArrayList inputFile,
			ArrayList inputFileCopy, OutputStream parseOut) throws Exception {
		PrintStream ps = new PrintStream(parseOut);

		int count = countNodeVal;
		int regCountLeft = 0;
		int regCountRight = 0;

		ArrayList rightChildValues = new ArrayList();
		ArrayList leftChildValues = new ArrayList();

		for (int i = 0; i < (ifToThenVal.size()); i++) {
			// executes only if ifToTehnVal contains op3 as "+" or "-"
			if (ifToThenVal.get(i).equals("+")
					|| ifToThenVal.get(i).equals("-")) {
				count++;
				countAssign = countNodeVal;

				Object val = ifToThenVal.get(i);

				ifToThenVal.remove(i);
				inputFileCopy.remove(i);

				int x = -1;
				if (((String) leftChild).matches("[0-9]*")) {
					x = new Integer(leftChild.toString()).intValue();
				}

				if ((x >= 0)
						|| ((htable.get(leftChild) != null) && htable.get(
								leftChild).equals("int"))) {

					if ((htable.get(leftChild) != null)
							&& htable.get(leftChild).equals("int")) {
//						regCount++;
//						ps.print("\\lloadI @" + leftChild + " => R" + regCount);
//						regCount++;
//						ps.print("\\lloadAO Rarp, R" + (regCount - 1) + " => R"
//								+ regCount);
						
						if(!(htablevarnames.containsKey(leftChild)))
						{
						regCount++;
						ps.print("             ");
						ps.println("li $t0, 0");			
						//System.out.println("htable value:"+ htablereg.get("R"+regCount));
						ps.print("             ");
						ps.println("sw $t0, "+ htablereg.get("R"+regCount) +"($fp)");
						
						//regCount++;
						
						//ps.print("\\lloadAO Rarp, R" + (regCount - 1) + " => R" + regCount);
						
						ps.print("             ");
						ps.println("lw $t0, "+ htablereg.get("R"+regCount)+"($fp)");	
						ps.print("             ");
						ps.println("addu $t1, $t0, $fp");
						
						regCount++;
						
						ps.print("             ");
						ps.println("sw $t1, "+ htablereg.get("R"+regCount) +"($fp)");
						
						SymbolTableVarNames(leftChild, "R"+regCount);

						}

						regCountLeft = regCount;
					} else if (x >= 0) {
//						regCount++;
//						ps.print("\\lloadI " + leftChild + " => R" + regCount);
						
						if(!(htablevarnames.containsKey(leftChild)))
						{
						regCount++;
						ps.print("             ");
						ps.println("li $t0, " + leftChild);			
						//System.out.println("htable value:"+ htablereg.get("R"+regCount));
						ps.print("             ");
						ps.println("sw $t0, "+ htablereg.get("R"+regCount) +"($fp)");				
						
						SymbolTableVarNames(leftChild, "R"+regCount);
						}

						regCountLeft = regCount;
					}
				} else {
					// throw new
					// Exception("operands of operators should be integers");
					System.err.println("operands of operators should be integers \""
									+ leftChild + "\"........should be int...");
				}

				// goes to Term
				rightChildValues = Term(regCount, countAssign, countNodeVal,
						ifToThenVal, inputFile, inputFileCopy, parseOut);

				Object rightChild = rightChildValues.get(0);
				countNodeVal = (Integer) rightChildValues.get(1);
				regCount = (Integer) rightChildValues.get(2);

				int y = -1;
				if (((String) rightChild).matches("[0-9]*")) {
					y = new Integer(rightChild.toString()).intValue();
				}

				if ((y >= 0)
						|| ((htable.get(rightChild) != null) && htable.get(
								rightChild).equals("int"))) {
					//if ((y >= 0) ||
							if (((htable.get(rightChild) != null) && htable.get(
									rightChild).equals("int"))) {
//						regCount++;
//						ps.print("\\lloadI @" + rightChild + " => R" + regCount);
//						regCount++;
//						ps.print("\\lloadAO Rarp, R" + (regCount - 1) + " => R"
//								+ regCount);
						
						if(!(htablevarnames.containsKey(rightChild)))
						{
						
						regCount++;
						ps.print("             ");
						ps.println("li $t0, 0");			
						//System.out.println("htable value:"+ htablereg.get("R"+regCount));
						ps.print("             ");
						ps.println("sw $t0, "+ htablereg.get("R"+regCount) +"($fp)");
						
						//regCount++;
						
						//ps.print("\\lloadAO Rarp, R" + (regCount - 1) + " => R" + regCount);
						
						ps.print("             ");
						ps.println("lw $t0, "+ htablereg.get("R"+regCount)+"($fp)");	
						ps.print("             ");
						ps.println("addu $t1, $t0, $fp");
						
						regCount++;
						
						ps.print("             ");
						ps.println("sw $t1, "+ htablereg.get("R"+regCount) +"($fp)");					

						SymbolTableVarNames(rightChild, "R"+regCount);
						}
						
						regCountRight = regCount;
					} 
					else if (y >= 0) {
//						regCount++;
//						ps.print("\\lloadI " + rightChild + " => R" + regCount);
						
						if(!(htablevarnames.containsKey(rightChild)))
						{
						regCount++;
						ps.print("             ");
						ps.println("li $t0, " + rightChild);			
						//System.out.println("htable value:"+ htablereg.get("R"+regCount));
						ps.print("             ");
						ps.println("sw $t0, "+ htablereg.get("R"+regCount) +"($fp)");
						
						SymbolTableVarNames(rightChild, "R"+regCount);
						}

						regCountRight = regCount;
					}
				} else {
					// throw new
					// Exception("operands of operators should be integers");
					System.err.println("operands of operators should be integers \""
									+ rightChild + "\"........should be int...");
				}

				if (val.equals("+")) {
					regCount++;
////					ps.print("\\ladd R" + regCountLeft + ", R" + regCountRight
////							+ " => R" + regCount);
//					ps.print("             ");
//					ps.println("lw $t1, " + htablereg.get("R"+ regCountLeft )+ "($fp)");
//					ps.print("             ");
//					ps.println("lw $t2, " + htablereg.get("R"+ regCountRight )+ "($fp)");
//					ps.print("             ");
//					ps.println("addu $t0, $t1, $t2");
//					ps.print("             ");
//					ps.println("sw $t0, " + htablereg.get("R"+ regCount )+ "($fp)");
					
					ps.print("             ");
					ps.println("lw $t0, " + htablereg.get(htablevarnames.get(leftChild))+ "($fp)");
					ps.print("             ");
					ps.println("lw $t1, " + htablereg.get(htablevarnames.get(rightChild))+ "($fp)");
					ps.print("             ");					
					ps.println("addu $t2, $t0, $t1");
					ps.print("             ");
					ps.println("sw $t2, " + htablereg.get("R"+ regCount )+ "($fp)");
					
				} else if (val.equals("-")) {
					regCount++;
//					ps.print("\\lsub R" + regCountLeft + ", R" + regCountRight
//							+ " => R" + regCount);
					ps.print("             ");
					ps.println("lw $t0, " + htablereg.get(htablevarnames.get(leftChild))+ "($fp)");
					ps.print("             ");
					ps.println("lw $t1, " + htablereg.get(htablevarnames.get(rightChild))+ "($fp)");
//					ps.print("             ");
//					ps.println("lw $t2, 0");
					ps.print("             ");					
					ps.println("sub $t2, $t0, $t1");
					ps.print("             ");
					ps.println("sw $t2, " + htablereg.get("R"+ regCount )+ "($fp)");
															
				}

				leftChild = null;
				rightChild = null;

			}
		}

		leftChildValues.add(leftChild);
		leftChildValues.add(countNodeVal);
		leftChildValues.add(regCount);

		return leftChildValues;
	}

	/**
	 * Executes the Production Rule: <term> ::= <factor> <factorPrime>
	 * 
	 * @param regCount
	 * @param countAssign
	 * @param countNodeVal
	 * @param ifToThenVal
	 * @param inputFile
	 * @param inputFileCopy
	 * @param parseOut
	 * @return
	 * @throws Exception
	 */
	public ArrayList Term(int regCount, int countAssign, int countNodeVal,
			ArrayList ifToThenVal, ArrayList inputFile,
			ArrayList inputFileCopy, OutputStream parseOut) throws Exception {
		PrintStream ps = new PrintStream(parseOut);

		int count = countNodeVal;
		int count1 = count;
		ArrayList rightChildValues = new ArrayList();

		// goes to Factor
		ArrayList leftChildValues = Factor(regCount, countAssign, countNodeVal,
				ifToThenVal, inputFile, inputFileCopy, parseOut);

		Object leftChild = leftChildValues.get(0);
		countNodeVal = (Integer) leftChildValues.get(1);
		regCount = (Integer) leftChildValues.get(2);

		// executes only if the ifToThenVal contains op2 as "*" , "div" , "mod"

		// goes to FactorPrime
		rightChildValues = FactorPrime(regCount, countAssign, leftChild,
				countNodeVal, ifToThenVal, inputFile, inputFileCopy, parseOut);

		return rightChildValues;
	}

	/**
	 * Executes the Production Rule: <factorPrime> ::= op2 <factor> | epsilon
	 * 
	 * @param regCount
	 * @param countAssign
	 * @param leftChild
	 * @param countNodeVal
	 * @param ifToThenVal
	 * @param inputFile
	 * @param inputFileCopy
	 * @param parseOut
	 * @return
	 * @throws Exception
	 */
	public ArrayList FactorPrime(int regCount, int countAssign,
			Object leftChild, int countNodeVal, ArrayList ifToThenVal,
			ArrayList inputFile, ArrayList inputFileCopy, OutputStream parseOut)
			throws Exception {

		PrintStream ps = new PrintStream(parseOut);

		for (int i = 0; i < (ifToThenVal.size()); i++) {
			// executes only of the ifToThenVal contains op2 as "*" or "div" or
			// "mod"
			if (ifToThenVal.get(i).equals("*")
					|| ifToThenVal.get(i).equals("div")
					|| ifToThenVal.get(i).equals("mod")) {
				int count = countNodeVal; // count = 13
				int regCountLeft = 0;
				int regCountRight = 0;

				count++; // count = 14

				countAssign = countNodeVal;

				Object val = ifToThenVal.get(i);

				ifToThenVal.remove(i);
				inputFileCopy.remove(i);

				int x = -1;
				if (((String) leftChild).matches("[0-9]*")) {
					x = new Integer(leftChild.toString()).intValue();
				}

				if ((x >= 0)
						|| ((htable.get(leftChild) != null) && htable.get(
								leftChild).equals("int"))) {

					if ((htable.get(leftChild) != null)
							&& htable.get(leftChild).equals("int")) {
//						regCount++;
//						ps.print("\\lloadI @" + leftChild + " => R" + regCount);
//						regCount++;
//						ps.print("\\lloadAO Rarp, R" + (regCount - 1) + " => R"
//								+ regCount);

						if(!(htablevarnames.containsKey(leftChild)))
						{
						regCount++;
						ps.print("             ");
						ps.println("li $t0, 0");			
						//System.out.println("htable value:"+ htablereg.get("R"+regCount));
						ps.print("             ");
						ps.println("sw $t0, "+ htablereg.get("R"+regCount) +"($fp)");
						
						//regCount++;
						
						//ps.print("\\lloadAO Rarp, R" + (regCount - 1) + " => R" + regCount);
						
						ps.print("             ");
						ps.println("lw $t0, "+ htablereg.get("R"+regCount)+"($fp)");	
						ps.print("             ");
						ps.println("addu $t1, $t0, $fp");
						
						regCount++;
						
						ps.print("             ");
						ps.println("sw $t1, "+ htablereg.get("R"+regCount) +"($fp)");
						
						SymbolTableVarNames(leftChild, "R"+regCount);
						}
						
						regCountLeft = regCount;
					} else if (x >= 0) {
//						regCount++;
//						ps.print("\\lloadI " + leftChild + " => R" + regCount);
						
						if(!(htablevarnames.containsKey(leftChild)))
						{
						regCount++;
						ps.print("             ");
						ps.println("li $t0, " + leftChild);			
						//System.out.println("htable value:"+ htablereg.get("R"+regCount));
						ps.print("             ");
						ps.println("sw $t0, "+ htablereg.get("R"+regCount) +"($fp)");
						
						SymbolTableVarNames(leftChild, "R"+regCount);
						}

						regCountLeft = regCount;
					}
				} else {
					// throw new
					// Exception("operands of operators should be integers");
					System.err.println("operands of operators should be integers "
									+ leftChild + "........should be int...");
				}

				// goes to Factor
				ArrayList leftChildValues = Factor(regCount, countAssign,
						countNodeVal, ifToThenVal, inputFile, inputFileCopy,
						parseOut);

				Object rightChild = leftChildValues.get(0);
				countNodeVal = (Integer) leftChildValues.get(1);
				regCount = (Integer) leftChildValues.get(2);

				int y = -1;
				if ((rightChild != null)
						&& ((String) rightChild).matches("[0-9]*")) {
					y = new Integer(rightChild.toString()).intValue();
				}

				if (rightChild != null) {
					if ((y >= 0)
							|| ((htable.get(rightChild) != null) && htable.get(
									rightChild).equals("int"))) {
						if ((htable.get(rightChild) != null)
								&& htable.get(rightChild).equals("int")) {
//							regCount++;
//							ps.print("\\lloadI @" + rightChild + " => R"
//									+ regCount);
//							regCount++;
//							ps.print("\\lloadAO Rarp, R" + (regCount - 1)
//									+ " => R" + regCount);
							if(!(htablevarnames.containsKey(rightChild)))
							{
							regCount++;
							ps.print("             ");
							ps.println("li $t0, 0");			
							//System.out.println("htable value:"+ htablereg.get("R"+regCount));
							ps.print("             ");
							ps.println("sw $t0, "+ htablereg.get("R"+regCount) +"($fp)");
							
							//regCount++;
							
							//ps.print("\\lloadAO Rarp, R" + (regCount - 1) + " => R" + regCount);
							
							ps.print("             ");
							ps.println("lw $t0, "+ htablereg.get("R"+regCount)+"($fp)");	
							ps.print("             ");
							ps.println("addu $t1, $t0, $fp");
							
							regCount++;
							
							ps.print("             ");
							ps.println("sw $t1, "+ htablereg.get("R"+regCount) +"($fp)");
							
							SymbolTableVarNames(rightChild, "R"+regCount);
							}

							regCountRight = regCount;
						} else if (y >= 0) {
//							regCount++;
//							ps.print("\\lloadI " + rightChild + " => R"
//									+ regCount);
							if(!(htablevarnames.containsKey(rightChild)))
							{
							regCount++;
							ps.print("             ");
							ps.println("li $t0, " + rightChild);			
							//System.out.println("htable value:"+ htablereg.get("R"+regCount));
							ps.print("             ");
							ps.println("sw $t0, "+ htablereg.get("R"+regCount) +"($fp)");
							
							SymbolTableVarNames(rightChild, "R"+regCount);
							}
							
							regCountRight = regCount;
						}
					} else {
						// throw new
						// Exception("operands of operators should be integers");
						System.err.println("operands of operators should be integers "
										+ rightChild
										+ "........should be int...");
					}
				}

				if (val.equals("*")) {
//************************commented from here
//					regCount++;
////					ps.print("\\lmul R" + regCountLeft + ", R" + regCountRight
////							+ " => R" + regCount);	
//					ps.print("             ");
//					ps.println("lw $t0, " + htablereg.get("R"+ regCountLeft )+ "($fp)");
//					ps.print("             ");
//					ps.println("lw $t1, " + htablereg.get("R"+ regCountRight )+ "($fp)");
//					ps.print("             ");
//					ps.println("lw $t2, 0($t1)");
//					ps.print("             ");
//					ps.println("mul $t1, $t0, $t2");
//					ps.print("             ");
//					ps.println("sw $t1, " + htablereg.get("R"+ regCount )+ "($fp)");
//*********************commented till here					
					
					regCount++;
//					ps.print("\\lmul R" + regCountLeft + ", R" + regCountRight
//							+ " => R" + regCount);
					ps.print("             ");
					ps.println("lw $t0, " + htablereg.get(htablevarnames.get(leftChild))+ "($fp)");
					ps.print("             ");
					ps.println("lw $t1, " + htablereg.get(htablevarnames.get(rightChild))+ "($fp)");
					ps.print("             ");					
					ps.println("mul $t2, $t0, $t1");
					ps.print("             ");
					ps.println("sw $t2, " + htablereg.get("R"+ regCount )+ "($fp)");
						
					
					
				} else if (val.equals("div")) {
					regCount++;
//					ps.print("\\ldiv R" + regCountLeft + ", R" + regCountRight
//							+ " => R" + regCount);
//					ps.print("             ");
//					ps.println("lw $t1, " + htablereg.get("R"+ regCountLeft )+ "($fp)");
//					ps.print("             ");
//					ps.println("lw $t2, " + htablereg.get("R"+ regCountRight )+ "($fp)");
//					ps.print("             ");
//					ps.println("div $t0, $t1, $t2");
//					ps.print("             ");
//					ps.println("sw $t0, " + htablereg.get("R"+ regCount )+ "($fp)");
					
					ps.print("             ");
					ps.println("lw $t0, " + htablereg.get(htablevarnames.get(leftChild))+ "($fp)");
					ps.print("             ");
					ps.println("lw $t1, " + htablereg.get(htablevarnames.get(rightChild))+ "($fp)");
					ps.print("             ");					
					ps.println("div $t2, $t0, $t1");
					ps.print("             ");
					ps.println("sw $t2, " + htablereg.get("R"+ regCount )+ "($fp)");
					
				} else if (val.equals("mod")) {
					regCount++;
//					ps.print("\\lmod R" + regCountLeft + ", R" + regCountRight
//							+ " => R" + regCount);
//					ps.print("             ");
//					ps.println("lw $t1, " + htablereg.get("R"+ regCountLeft )+ "($fp)");
//					ps.print("             ");
//					ps.println("lw $t2, " + htablereg.get("R"+ regCountRight )+ "($fp)");
//					ps.print("             ");
//					ps.println("mod $t0, $t1, $t2");
//					ps.print("             ");
//					ps.println("sw $t0, " + htablereg.get("R"+ regCount )+ "($fp)");
					
					ps.print("             ");
					ps.println("lw $t0, " + htablereg.get(htablevarnames.get(leftChild))+ "($fp)");
					ps.print("             ");
					ps.println("lw $t1, " + htablereg.get(htablevarnames.get(rightChild))+ "($fp)");
					ps.print("             ");					
					ps.println("mod $t2, $t0, $t1");
					ps.print("             ");
					ps.println("sw $t2, " + htablereg.get("R"+ regCount )+ "($fp)");
					
				}

				leftChild = null;
				rightChild = null;
			}
		}

		ArrayList rightChildValues = new ArrayList();
		rightChildValues.add(leftChild);
		rightChildValues.add(countNodeVal);
		rightChildValues.add(regCount);
		return rightChildValues;

	}

	/**
	 * Executes the Production Rule: <factor> ::= ident | num | boollit | LP
	 * <expression> RP
	 * 
	 * @param regCount
	 * @param countAssign
	 * @param countNodeVal
	 * @param ifToThenVal
	 * @param inputFile
	 * @param inputFileCopy
	 * @param parseOut
	 * @return
	 * @throws Exception
	 */
	public ArrayList Factor(int regCount, int countAssign, int countNodeVal,
			ArrayList ifToThenVal, ArrayList inputFile,
			ArrayList inputFileCopy, OutputStream parseOut) throws Exception {
		PrintStream ps = new PrintStream(parseOut);

		final int count = countNodeVal;
		Object leftChild = null;
		ArrayList leftChildValues = new ArrayList();

		for (int i = 0; i < (ifToThenVal.size()); i++) {

			// executes if the value is an "ident"
			if (((String) ifToThenVal.get(i)).matches("[A-Z][A-z0-9]*")) {

				leftChild = ifToThenVal.get(i);

				ifToThenVal.remove(i);
				inputFileCopy.remove(i); // removes Y

				leftChildValues.add(leftChild);
				leftChildValues.add(countNodeVal);
				leftChildValues.add(regCount);

				return leftChildValues;

			}

			// executes if the value is "num"
			else if (((String) ifToThenVal.get(i)).matches("[0-9]*")) {
				// try{
				if ((Integer.parseInt((String) ifToThenVal.get(i))) >= 0
						&& (Integer.parseInt((String) ifToThenVal.get(i))) <= 2147483647) {

					leftChild = ifToThenVal.get(i);

					ifToThenVal.remove(i);
					inputFileCopy.remove(i);

					leftChildValues.add(leftChild);
					leftChildValues.add(countNodeVal);
					leftChildValues.add(regCount);

					return leftChildValues;
				}
				// }
				// catch(Exception e){
				else {
					System.out.println("Num value out of range...The literal value should be numbers 0 through 2147483647");
					// System.out.println(e.getMessage());
					// throw new
					// Exception("Num value out of range...The literal value should be numbers 0 through 2147483647");
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
				leftChildValues.add(regCount);

				return leftChildValues;

			}

			// executes if the value is left parenthesis as "("
			else if (ifToThenVal.get(i).equals("(")) {

				ifToThenVal.remove(i);
				inputFileCopy.remove(i);

				// goes to function Expression
				ArrayList rightChildValues = Expression(regCount, countAssign,
						countNodeVal, ifToThenVal, inputFile, inputFileCopy,
						parseOut);

				Object rightChild = rightChildValues.get(0);
				countNodeVal = (Integer) rightChildValues.get(1);
				regCount = (Integer) rightChildValues.get(2);

				if (inputFileCopy.get(i).equals(")")) {
					ifToThenVal.remove(i);
					inputFileCopy.remove(i);
				} else
					throw new Exception("right parenthesis missing");

				leftChildValues.add(rightChild);
				leftChildValues.add(countNodeVal);
				leftChildValues.add(regCount);

				return leftChildValues;
			} else
				throw new Exception("given input does not match the production rules");

		}

		return leftChildValues;

	}

	/**
	 * Executes the Production Rule: <assignment> ::= ident ASGN
	 * <assignmentPrime> <assignmentPrime ::= readint | <expression>
	 * 
	 * @param regCountValues
	 * @param regCount
	 * @param countAssign1
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
	public ArrayList Assignment(ArrayList regCountValues, int regCount,
			int countAssign1, int countNodeVal, ArrayList assignValues,
			Object inputFileCopyVal1, ArrayList ifToThenVal,
			ArrayList inputFile, ArrayList inputFileCopy, OutputStream parseOut)
			throws Exception

	{
		PrintStream ps = new PrintStream(parseOut);

		int count = countNodeVal; // count=11
		int regCountLeft = 0;
		// ArrayList regCountValues = new ArrayList();

		// executes if <expressionPrime> is readint
		if (inputFileCopyVal1.equals("readint")) {

			// int leftChild = 0;
			// if(((String) assignValues.get(0)).matches("[A-Z][A-z0-9]*")){
			// leftChild = new
			// Integer(assignValues.get(0).toString()).intValue(); //leftChild =
			// (Integer) assignValues.get(0);
			// }

			if (((htable.get(assignValues.get(0)) != null) && htable.get(
					assignValues.get(0)).equals("int"))) {

//				regCount++;
//				ps.print("\\lloadI @" + assignValues.get(0) + " => R"
//						+ regCount);
//				regCount++;
//				ps.print("\\lloadAO Rarp, R" + (regCount - 1) + " => R"
//						+ regCount);
				
				if(!(htablevarnames.containsKey(assignValues.get(0))))
						{
							regCount++;
							ps.print("             ");
							ps.println("li $t0, 0");			
							//System.out.println("htable value:"+ htablereg.get("R"+regCount));
							ps.print("             ");
							ps.println("sw $t0, "+ htablereg.get("R"+regCount) +"($fp)");
							
							//regCount++;
							
							//ps.print("\\lloadAO Rarp, R" + (regCount - 1) + " => R" + regCount);
							
							ps.print("             ");
							ps.println("lw $t0, "+ htablereg.get("R"+regCount)+"($fp)");	
							ps.print("             ");
							ps.println("addu $t1, $t0, $fp");
							
							regCount++;
							
							ps.print("             ");
							ps.println("sw $t1, "+ htablereg.get("R"+regCount) +"($fp)");
							
							SymbolTableVarNames(assignValues.get(0), "R"+regCount);
				
						}

				// countNodeVal++;
				// ps.println("n" + countNodeVal + " [label=\""
				// + assignValues.get(0) + "\"fillcolor=3];");
				// ps.println("n" + (countNodeVal-1) + " -> n" + countNodeVal +
				// ";");
			} else {
				// throw new
				// Exception("operands of operators should be integers");
				System.err
						.println("Only integer variables may be assigned the result of readint... ");
			}

			//ps.print("\\lreadint R" + regCount);
			
			ps.print("             ");
			ps.println("li $v0,5");
			ps.print("             ");
			ps.println("syscall");
			ps.print("             ");
			ps.println("move $t0, $v0");
			ps.print("             ");
			ps.println("sw $t0, " + htablereg.get(htablevarnames.get(assignValues.get(0))) + "($fp)");
//			ps.print("             ");
//			ps.println("sw $t0, 0($t1)");

			ifToThenVal.remove(0);
			inputFileCopy.remove(0);

		}

		// executes if <expressionPrime> is <expression>
		else {
			// count++; //count = 14
			int countAssign = count + 1;

			if (((htable.get(assignValues.get(0)) != null) && htable.get(
					assignValues.get(0)).equals("int"))) {
//				regCount++;
//				ps.print("\\lloadI @" + assignValues.get(0) + " => R"
//						+ regCount);
//				regCount++;
//				ps.print("\\lloadAO Rarp, R" + (regCount - 1) + " => R"
//						+ regCount);
				
				
				if(!(htablevarnames.containsKey(assignValues.get(0))))
				{
				regCount++;
				ps.print("             ");
				ps.println("li $t0, 0");			
				//System.out.println("htable value:"+ htablereg.get("R"+regCount));
				ps.print("             ");
				ps.println("sw $t0, "+ htablereg.get("R"+regCount) +"($fp)");
				
				//regCount++;
				
				//ps.print("\\lloadAO Rarp, R" + (regCount - 1) + " => R" + regCount);
				
				ps.print("             ");
				ps.println("lw $t0, "+ htablereg.get("R"+regCount)+"($fp)");	
				ps.print("             ");
				ps.println("addu $t1, $t0, $fp");
				
				regCount++;
				
				ps.print("             ");
				ps.println("sw $t1, "+ htablereg.get("R"+regCount) +"($fp)");					
				
				SymbolTableVarNames(assignValues.get(0), "R"+regCount);
				}
				
				regCountLeft = regCount;

			}

			else {
				throw new Exception("The left-hand of assignment must be a variable... ");
				// throw new
				// Exception("operands of operators should be integers");
				// System.err.println("The left-hand of assignment must be a variable... ");
			}

			ArrayList rightChildValues = Expression(regCount, countAssign,
					countNodeVal, ifToThenVal, inputFile, inputFileCopy,
					parseOut);

			Object rightChild = rightChildValues.get(0);
			countNodeVal = (Integer) rightChildValues.get(1);
			regCount = (Integer) rightChildValues.get(2);
			
//			ps.println("\\li2i R" + regCount + " => R" + regCountLeft);

			ps.print("             ");
			ps.println("lw $t0, " + htablereg.get("R"+ regCount) + "($fp)");
			ps.print("             ");
			ps.println("move $t1, $t0");
			ps.print("             ");
			ps.println("sw $t1, " + htablereg.get(htablevarnames.get(assignValues.get(0))) + "($fp)" );

		}

		regCountValues.set(0, countNodeVal);
		regCountValues.set(1, regCount);

		return regCountValues;

	}

	/**
	 * Executes the Production Rule: <declarations> ::= VAR ident AS <type> SC
	 * <declarations>
	 * 
	 * @param countNodeVal
	 * @param regCount
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
	public ArrayList Declarations(int countNodeVal, int regCount,
			Object inputFileCopyVal1, Object inputFileCopyVal2,
			Object inputFileCopyVal3, Object inputFileCopyVal4,
			Object inputFileCopyVal5, Object inputFileCopyVal6,
			ArrayList inputFileCopy, ArrayList inputFile, OutputStream parseOut)

	{
		PrintStream ps = new PrintStream(parseOut);
		
		//System.out.println("this is htablereg" + htablereg);

				
		//ps.print("\\lloadI @" + inputFileCopyVal2 + " => R" + regCount);
		
		ps.print("             ");
		ps.println("li $t0, 0");			
		//System.out.println("htable value:"+ htablereg.get("R"+regCount));
		ps.print("             ");
		ps.println("sw $t0, "+ htablereg.get("R"+regCount) +"($fp)");
		
		//regCount++;
		
		//ps.print("\\lloadAO Rarp, R" + (regCount - 1) + " => R" + regCount);
		
		ps.print("             ");
		ps.println("lw $t0, "+ htablereg.get("R"+regCount)+"($fp)");	
		ps.print("             ");
		ps.println("addu $t1, $t0, $fp");
		
		regCount++;
		
		ps.print("             ");
		ps.println("sw $t1, "+ htablereg.get("R"+regCount) +"($fp)");
		
		SymbolTableVarNames(inputFileCopyVal2, "R"+regCount);

		ArrayList countValues = new ArrayList();
		countValues.add(countNodeVal);
		countValues.add(regCount);

		return countValues;

	}

	private void SymbolTableVarNames(Object inputFileCopyVal2, String string) {
		
		if(htablevarnames == null)
			htablevarnames = new Hashtable();
		
		htablevarnames.put(inputFileCopyVal2, string);
	}

	// This method puts the values into Hash Table
	public void SymbolTable(String var, String type) {

		if (htable == null)
			htable = new Hashtable();

		// Add key/value pairs to the map
		htable.put(var, type);

		// System.out.println(hashTable);

		// hashTable.put("x", new Integer(1));
		// hashTable.put("y", new Integer(2));
		// hashTable.put("z", new Integer(3));
	}
	
	public void SymbolTableRegisters(String var, int num) {

		if (htablereg == null)
			htablereg = new Hashtable();

		// Add key/value pairs to the map
		
		htablereg.put(var, num);
	}

} // end class parser
