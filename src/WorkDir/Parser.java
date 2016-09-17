//package edu.utsa.tl15;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class Parser {
	static String LP = "(";
	static String RP = ")";
	static Hashtable variable1 = new Hashtable();
	static String White = "\"/x11/white\"";
	static String Pink = "\"/pastel13/1\"";
	static String Green = "\"/pastel13/3\"";
	static String Gray = "\"/x11/lightgrey\"";
	static List<String> variableList = new ArrayList<String>();
	static List<String> variableListType = new ArrayList<String>();
	static int variableListIndex = -1;
	static int astNodeNumber = 0;
	static List<String> astNodeList = new ArrayList<String>();
	static List<String> astTokenType = new ArrayList<String>();
	static List<String> astToken = new ArrayList<String>();
	static String currentAstToken;
	static int astIndex = 0;
	static Queue<String> TokenFile = new LinkedList<String>();
	static Queue<String> TokenList = new LinkedList<String>();
	static String currentToken = null;
	static List<String> NodeList = new ArrayList<String>();
	static List<String> tokenArrayList = new ArrayList<String>();


	public static int NodeNumber = 0;
	public static String fileName;
	public static String pathFile;
	



	public static String consume() {
		return TokenFile.poll();

	}

	public static String view() {
		return TokenFile.peek();
	}

	public static void main(String[] args) {
		
		Scanner scnPath = new Scanner(System.in);
		pathFile = args[0];
		String sourceln;
		String noComment = "";
		String newString = "";
		try {
			FileInputStream file = new FileInputStream(pathFile);
			String parserPthFile = pathFile.replace("tl", "tl.pt.dot");
			String astPathfile = pathFile.replace("tl", "tl.ast.dot");
			String ilocPathfile = pathFile.replace("tl", "iloc.cfg.dot");
			String mipsPathfFile=pathFile.replace("tl", "s");
			// // using DataInputStream to get data

			DataInputStream input1 = new DataInputStream(file);
			BufferedReader ln = new BufferedReader(
					new InputStreamReader(input1));

			while ((sourceln = ln.readLine()) != null) {
				noComment = sourceln.replaceAll("%.*$", " ");
				noComment = noComment.replaceAll("$", " ");
				noComment = noComment.replaceAll("\n", " ");
				noComment = noComment.replaceAll("\t", " ");
				newString = newString + "" + noComment;
				;
			}

			System.out.println(newString);

			Scanner scn1 = new Scanner(newString);

			while (scn1.hasNext())

			{
				String token = scn1.next();
				TokenFile.add(token);
				if (token.matches("program")) {
					astToken.add(token);
					astTokenType.add("PROGRAM");
				} else if (token.matches("begin")) {
					astToken.add(token);
					astTokenType.add("BEGIN");
				} else if (token.matches("end")) {
					astToken.add(token);
					astTokenType.add("END");
				} else if (token.matches("var")) {
					astToken.add(token);
					astTokenType.add("VAR");
				} else if (token.matches("as")) {
					astToken.add(token);
					astTokenType.add("AS");
				} else if (token.matches(";")) {
					astToken.add(token);
					astTokenType.add("SC");
				} else if (token.matches("[A-Z][A-z0-9]*")) {
					astToken.add(token);
					astTokenType.add("ident");
				} else if (token.matches("int")) {
					astToken.add(token);
					astTokenType.add("INT");
				} else if (token.matches("bool")) {
					astToken.add(token);
					astTokenType.add("BOOL");
				} else if (token.matches(":=")) {
					astToken.add(token);
					astTokenType.add("ASGN");
				} else if (token.matches("readint")) {
					astToken.add(token);
					astTokenType.add("READINT");
				} else if (token.matches("-|[+]")) {
					astToken.add(token);
					astTokenType.add("OP3");
				} else if (token.matches("else")) {
					astToken.add(token);
					astTokenType.add("ELSE");
				} else if (token.matches("[1-9][0-9]*|0")) {
					astToken.add(token);
					astTokenType.add("num");
				} else if (token.matches("false")) {
					astToken.add(token);
					astTokenType.add("boollit");
				} else if (token.matches("true")) {
					astToken.add(token);
					astTokenType.add("boollit");
				} else if (token.matches("[(]")) {
					astToken.add(token);
					astTokenType.add("LP");
				} else if (token.matches("[)]")) {
					astToken.add(token);
					astTokenType.add("RP");
				} else if (token.matches("[*]|div|mod")) {
					astToken.add(token);
					astTokenType.add("OP2");
				} else if (token.matches("if")) {
					astToken.add(token);
					astTokenType.add("IF");
				} else if (token.matches("then")) {
					astToken.add(token);
					astTokenType.add("THEN");
				} else if (token.matches("=|!=|<|>|<=|>=")) {
					astToken.add(token);
					astTokenType.add("OP4");
				} else if (token.matches("while")) {
					astToken.add(token);
					astTokenType.add("WHILE");
				} else if (token.matches("do")) {
					astToken.add(token);
					astTokenType.add("DO");
				} else if (token.matches("writeint")) {
					astToken.add(token);
					astTokenType.add("WRITEINT");
				} else if (token.matches("then")) {
					astToken.add(token);
					astTokenType.add("THEN");
				} else
					System.out.println(token + " is not a TL15 keyword!");

			}

			//System.out.println("Program Start....");
			for (int i = 0; i < astToken.size(); i++) {

				System.out.print(astToken.get(i) + " ");

			}
			System.out.println();
			System.out.println("Number of Tokens is: " + TokenFile.size());
			System.out.println("#######################################################################################################"
                    	   + "\n####################################### Welcome to TL15 parser! #######################################"
                           + "\n#######################################################################################################");   
			System.out.println("\n=========================================== Parser Phase ==============================================");
     		System.out.println("\nParsing Start....");
			

			astNode Program = new astNode("program");
			Program = astProgram(0, Program);

			
			if (program(0) == true) {

				System.out.println("Sucess: parsing done successfylly!");
			} else
				System.out
						.println("########Compiler Type Error: The parser have not finished sucessfuly! ####### ");

			createFile(parserPthFile);
			



			createAstFile(astPathfile);



			System.out.println("\nParser Dot File: \n" + parserPthFile);

			System.out.println("\nAST Dot File: \n" + astPathfile);



		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (NoSuchElementException e) {
			System.out.println("Error :" + e.getMessage());
		} catch (NullPointerException e) {
			System.out.println(" Error NullPointerException : "
					+ e.getMessage());
		}
	}

	// <program> ::= PROGRAM <declarations> BEGIN <statementSequence> END

	public static boolean program(int RootNumber) {
		String line = "n" + RootNumber + " [label=\"" + "<program>"
				+ "\",fillcolor=\"/x11/white\",shape=box]";
		NodeList.add(line);
		NodeNumber++;
		currentToken = consume();
		if (currentToken.matches("program")) {
			createTerminal(NodeNumber, RootNumber);

			if (declarations(NodeNumber, RootNumber) == true) {

				currentToken = view();

				if (currentToken.matches("begin")) {
					createTerminal(NodeNumber, RootNumber);

					currentToken = consume();

					if (statementSequence(NodeNumber, RootNumber) == true)

						currentToken = consume();
					{
						if (currentToken.matches("end")) {
							createTerminal(NodeNumber, RootNumber);
							return true;
						} else {
							System.out
									.println("'end' expected to finish the program instead of "
											+ currentToken);
							return false;
						}
					}
				} else {
					System.out.println("'begin' expected");
					return false;
				}
			}
		} else {
			System.out.print("program must start with 'program'");
			return false;
		}
		return false;
	}

	// <declarations> ::= VAR ident AS <type> SC <declarations> | e

	public static boolean declarations(int myNumber, int myPrent) {
		createNonTerminal("<declaration>", myNumber, myPrent);
		currentToken = view();

		if (currentToken.matches("var")) {
			createTerminal(NodeNumber, myNumber);

			currentToken = consume();
			currentToken = view();

			if (currentToken.matches("[A-Z][A-z0-9]*"))

			{

				createTerminal(NodeNumber, myNumber);
				currentToken = consume();
				currentToken = view();

				if (currentToken.matches("as")) {
					createTerminal(NodeNumber, myNumber);
					currentToken = consume();

					if (type(NodeNumber, myNumber) == true) {

						currentToken = consume();

						if (currentToken.matches(";")) {
							createTerminal(NodeNumber, myNumber);
							if (declarations(NodeNumber, myNumber) == true) {
								return true;
							}
						} else {
							System.out
									.println("Error at declaration() : ';' expected to finish declaration instead of :  "
											+ currentToken);
							return false;
						}
					}

				} else {
					System.out
							.println("declaration() error 'as' expected instead of "
									+ currentToken);
					return false;
				}

			} else {
				System.out
						.println("'declaration() error identifier' expected instead of "
								+ currentToken);
				return false;
			}
		}
		if (currentToken.matches("begin")) {
			return true;
		}

		else {
			System.out
					.println("declaration() error 'var' or 'begin' expected  instead of "
							+ currentToken);
			return false;
		}

	}

	// <statementSequence> ::= <statement> SC <statementSequence>| epcilone

	public static boolean statementSequence(int myNumber, int myParent) {
		createNonTerminal("<statementSequence>", myNumber, myParent);
		currentToken = view();
		if (currentToken.matches("else|;|end")) {
			return true;
		} else if (currentToken.matches("if|while|writeint|[A-Z][A-z0-9]*")) {

			if (statement(NodeNumber, myNumber) == true) {

				currentToken = view();

				if (currentToken.matches(";")) {

					createTerminal(NodeNumber, myNumber);
					currentToken = consume();

					if (statementSequence(NodeNumber, myNumber) == true) {

						return true;

					}
				} else {
					return false;
				}
			}
		} else if (currentToken.matches("else|;|end")) {
			return true;
		}

		return false;
	}

	// <type> ::= INT | BOOL
	public static boolean type(int myNumber, int myParent) {
		createNonTerminal("<type>", myNumber, myParent);
		currentToken = view();

		if (currentToken.matches("int|bool")) {
			createTerminal(NodeNumber, myNumber);
			currentToken = consume();

			return true;
		} else {
			System.out.println("'int or boolean' expectedinstead of "
					+ currentToken);
			return false;
		}

	}


	public static boolean statement(int myNumber, int myParent) {
		createNonTerminal("<statement>", myNumber, myParent);
		currentToken = view();

		if (currentToken.matches("[A-Z][A-z0-9]*")) {
			if (assignment(NodeNumber, myNumber) == true) {

				return true;
			}
		} else if (currentToken.matches("if")) {
			if (ifStatement(NodeNumber, myNumber) == true) {

				return true;

			}
		} else if (currentToken.matches("while")) {
			if (whileStatement(NodeNumber, myNumber) == true) {

				return true;
			}
		} else if (currentToken.matches("writeint")) {
			if (writeint(NodeNumber, myNumber) == true) {

				return true;
			}
		} else {
			return false;
		}
		if ((currentToken.matches("[)]|then|else|do|;|end"))) {
			return true;
		} else
			return false;
	}

	// <assignment> ::= ident ASGN <assignment'>

	public static boolean assignment(int myNumber, int myParent) {
		createNonTerminal("<assignment>", myNumber, myParent);
		currentToken = view();

		if (currentToken.matches("[A-Z][A-z0-9]*")) {
			createTerminal(NodeNumber, myNumber);
			currentToken = consume();

			currentToken = view();

			if (currentToken.matches(":="))

			{
				createTerminal(NodeNumber, myNumber);
				currentToken = consume();
				currentToken = view();

				if (assignment1(NodeNumber, myNumber) == true) {

					return true;
				}
			}
		} else if (currentToken.matches(";|else|end"))
			return true;

		return false;
	}

	// <assignment'> ::= <expression> | READINT
	public static boolean assignment1(int myNumber, int myParent) {
		createNonTerminal("<assignmentPrime>", myNumber, myParent);

		currentToken = view();

		if (currentToken.matches("readint")) {

			createTerminal(NodeNumber, myNumber);
			currentToken = consume();

			currentToken = view();

			return true;
		} else if (currentToken
				.matches("[A-Z][A-z0-9]*|[(]|true|false|[1-9][0-9]*|0")) {

			if (expression(NodeNumber, myNumber) == true) {

				currentToken = view();

				return true;
			}
		} else if (currentToken.matches("end|else|;"))
			return true;
		else {
			System.out.println("'value' or 'readint' expected instead of "
					+ currentToken);
			return false;
		}
		return false;

	}

	// <expression> ::= <simpleExpression> <simpleExpression'>

	public static boolean expression(int myNumber, int myParent) {
		createNonTerminal("<expression>", myNumber, myParent);
		currentToken = view();

		if (currentToken.matches("[A-Z][A-z0-9]*|true|[(]|false|[1-9][0-9]*|0"))

		{
			{
				if (simpleExpression(NodeNumber, myNumber) == true)

				{
					if (simpleExpressionPrime(NodeNumber, myNumber) == true) {

						return true;
					}

				}
			}
		} else if (currentToken.matches("[)]|then|do|;|else|end"))
			return true;
		return false;
	}

	// <simpleExpression> ::= <term> OP3 <term> | <term>
	// <simpleExpression> ::= <term> <term'>
	public static boolean simpleExpression(int myNumber, int myParent) {
		createNonTerminal("<simpleExpression>", myNumber, myParent);
		currentToken = view();

		if (currentToken.matches("[A-Z][A-z0-9]*|[(]|true|false|[1-9][0-9]*|0")) {
			if (term(NodeNumber, myNumber) == true)

			{
				if (termPrime(NodeNumber, myNumber) == true) {
					return true;
				}
			}
		} else if (currentToken.matches("[)]|then|do|;|else|end")) {
			return true;
		}
		return false;

	}

	// <termPrime> ::= OP3 <term> | e
	public static boolean termPrime(int myNumber, int myParent) {
		createNonTerminal("<termPrime>", myNumber, myParent);
		currentToken = view();
		if (currentToken.matches("=|!=|<|>|<=|>=|;|else|then|do|[)]")) {
			return true;
		} else if ((currentToken.matches("-|[+]"))) {
			createTerminal(NodeNumber, myNumber);

			currentToken = consume();
			currentToken = view();

			if (term(NodeNumber, myNumber) == true) {

				return true;
			}
		} else {
			System.out
					.println("'error termPrime() arethmetic operato expected factor operations instead of :  "
							+ currentToken);
			return false;
		}
		return false;

	}

	// <term> ::= <factor> OP2 <factor> | <factor>
	// //<term> ::= <factor> <factorPrime>
	public static boolean term(int myNumber, int myParent) {
		createNonTerminal("<term>", myNumber, myParent);
		currentToken = view();

		if ((currentToken
				.matches("[A-Z][A-z0-9]*|[(]|[1-9][0-9]*|0|false|true"))) {

			if (factor(NodeNumber, myNumber) == true) {

				if (factorPrime(NodeNumber, myNumber) == true) {

					return true;
				}
			} else {
				return false;
			}
		} else if (currentToken.matches("else|end|;")) {
			return true;
		}
		return false;

	}

	// <factor> ::= ident | num | boollit || LP <expression> RP
	public static boolean factor(int myNumber, int myParent) {
		createNonTerminal("<factor>", myNumber, myParent);
		currentToken = view();

		if ((currentToken.matches("[A-Z][A-z-0-9]*|[1-9][0-9]*|0|false|true"))) {
			createTerminal(NodeNumber, myNumber);
			currentToken = consume();

			currentToken = view();

			return true;

		} else if (currentToken.matches("[(]")) {
			createTerminal(NodeNumber, myNumber);
			currentToken = consume();
			if (expression(NodeNumber, myNumber) == true) {

				currentToken = consume();

				if (currentToken.matches("[)]")) {
					createTerminal(NodeNumber, myNumber);

					// currentToken=consume();
					currentToken = view();
					System.out.println("1   : " + currentToken);
					return true;

				} else {
					System.out.println("error at factor(): ')' expected");
					return false;
				}
			}
		} else {
			System.out
					.println("error in factor()  '(' or viariable expected instead of : "
							+ currentToken);
			return false;
		}

		return false;

	}

	// <factorPrime> ::= OP2 <factor> | epcilone //to be checked later
	public static boolean factorPrime(int myNumber, int myParent) {
		createNonTerminal("<factoPrime>", myNumber, myParent);
		currentToken = view();
		if (currentToken.matches("[*]|div|mod")) {
			createTerminal(NodeNumber, myNumber);
			currentToken = consume();
			currentToken = view();

			if (factor(NodeNumber, myNumber) == true) {

				return true;

			}
		} else if (currentToken
				.matches(";|=|else|!=|<|>|<=|>=|[+]|[-]|then|do|[)]")) {
			return true;
		} else {
			return false;
		}
		return false;

	}

	// <simpleExpression'> ::= OP4 <simpleExpression> | e /stuck at
	// statementsequence
	public static boolean simpleExpressionPrime(int myNumber, int myParent) {
		createNonTerminal("<expressionPrime>", myNumber, myParent);

		currentToken = view();

		if (currentToken.matches("=|!=|<|>|<=|>=")) {
			createTerminal(NodeNumber, myNumber);

			currentToken = consume();

			if (simpleExpression(NodeNumber, myNumber) == true) {

				return true;
			}

		}
		if ((currentToken.matches("[)]|then|do|;|else|end"))) {
			return true;
		} else

		{
			System.out
					.println("Error simpleExpressionPrime: logic operator' expected");
			return false;
		}

	}

	// <writeint> ::= WRITEiNT <expression>
	public static boolean writeint(int myNumber, int myParent) {
		createNonTerminal("<writeint>", myNumber, myParent);
		currentToken = view();
		if (currentToken.matches("writeint")) {
			createTerminal(NodeNumber, myNumber);
			currentToken = consume();
			if (expression(NodeNumber, myNumber) == true) {

				return true;
			}
		} else {
			return false;
		}
		return false;

	} // after stuck statement sequence
		// <ifStatement> ::= IF <expression> THEN <statementSequence>
		// <elseClause>
		// END

	public static boolean ifStatement(int myNumber, int myParent) {
		createNonTerminal("<ifStatement>", myNumber, myParent);
		currentToken = view();

		if (currentToken.matches("if")) {
			createTerminal(NodeNumber, myNumber);
			currentToken = consume();

			if (expression(NodeNumber, myNumber) == true) {

				currentToken = view();

				if (currentToken.matches("then"))

				{
					createTerminal(NodeNumber, myNumber);
					currentToken = consume();

					if (statementSequence(NodeNumber, myNumber) == true) {
						if (currentToken.matches("else")) {
							if (elseClause(NodeNumber, myNumber) == true) {

								currentToken = view();
								if (currentToken.matches("end")) {
									createTerminal(NodeNumber, myNumber);
									currentToken = consume();

									return true;
								}
							}
						}

					}
				} else {
					System.out
							.println("ifStatement() error: 'then' expected instead of "
									+ currentToken + " at token number ");
					return false;
				}

			}
		} else {
			return false;
		}
		return false;

	}// after stuck statement sequence

	// //<elseClause> ::= ELSE <statementSequence> | e
	public static boolean elseClause(int myNumber, int myParent) {
		createNonTerminal("<elseClause>", myNumber, myParent);

		currentToken = view();
		// System.out.println("else clause token "+currentToken);

		if (currentToken.matches("else")) {
			createTerminal(NodeNumber, myNumber);
			currentToken = consume();
			if (statementSequence(NodeNumber, myNumber) == true) {

				return true;
			}
		} else if (currentToken.matches(("end"))) {
			return true;
		}
		// else {System.out.println("'else' expected");return false;}
		return false;

	}

	// <whileStatement> ::= WHILE <expression> DO <statementSequence> END
	public static boolean whileStatement(int myNumber, int myParent) {
		createNonTerminal("<whileStatement>", myNumber, myParent);
		currentToken = view();
		if (currentToken.matches("while")) {
			createTerminal(NodeNumber, myNumber);
			currentToken = consume();
			// if((currentToken.matches(
			// "[A-Z][A-z0-9]*|[1-9][0-9]*|0|false|true")))

			{
				if (expression(NodeNumber, myNumber) == true) {

					currentToken = view();
					if (currentToken.matches("do")) {
						createTerminal(NodeNumber, myNumber);
						currentToken = consume();
						if (statementSequence(NodeNumber, myNumber) == true) {

							currentToken = view();
							if (currentToken.matches("end")) {
								createTerminal(NodeNumber, myNumber);
								currentToken = consume();

								return true;
							} else {
								System.out
										.println("error at whileStatement() : 'end' expected instead of : "
												+ currentToken);
								return false;
							}
						}
					} else {
						System.out
								.println("error at whileStatement() :token :  'do' expected instead of:  "
										+ currentToken);
						return false;
					}
				}
			}
		} else {
			return false;
		}
		return false;

	}// after stuck statement sequence

	public static void createFile(String filepath) {

		try {
			String header = "digraph parsertree {\n  ordering=out;\n  node [shape = box, style = filled];\n";

			String EOF = "\n}";

			FileWriter WriteToFile = new FileWriter(filepath);

			PrintWriter WritToMemory = new PrintWriter(WriteToFile);
			WritToMemory.println(header);
			for (int i = 0; i < NodeList.size(); i++) {

				WritToMemory.println(NodeList.get(i));

			}

			WritToMemory.println(EOF);
			WritToMemory.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void createNonTerminal(String itsName, int itsNumber,
			int itsParent) {

		String line = "n" + itsNumber + " [label=\"" + itsName
				+ "\",fillcolor=\"/x11/white\",shape=box]";
		NodeList.add(line);
		String relation = "n" + itsParent + " -> n" + itsNumber + ";";
		NodeList.add(relation);
		NodeNumber++;
	}

	public static void createTerminal(int itsNumber, int itsParent) {

		String line = "n" + itsNumber + " [label=\"" + currentToken
				+ "\",fillcolor=\"/x11/white\",shape=box]";
		NodeList.add(line);
		String relation = "n" + itsParent + " -> n" + itsNumber + ";";
		NodeList.add(relation);
		NodeNumber++;
	}

	// //////Starting of AST functions///////////////////////////////

	/*
	 * 
	 * <statement> ::= <assignment> | <ifStatement> | <whileStatement> |
	 * <writeint> <assignment> ::= ident ASGN <assignment'>
	 *  <ifStatement> ::= IF <expression> THEN <statementSequence> <elseClause> END
	*	<elseClause> ::= ELSE <statementSequence> | e <whileStatement> ::=
	 * WHILE <expression> DO <statementSequence> END *
	 * <writeint> ::= WRITEiNT <expression> 
	 * <expression> ::= <simpleExpression> <simpleExpression'>
	 * <simpleExpression'> ::= OP4 <simpleExpression> | e <simpleExpression> ::=
	 * <term> <term'> <term'> ::= OP3 <term> | e <term> ::= <factor> <factor'>
	 * 
	 * <factor> ::= ident | num | boollit | LP <expression> RP
	 */

	// <program> ::= PROGRAM <declarations> BEGIN <statementSequence> END
	// <astProgram>::=<astDecList><astStmtList>
	public static astNode astProgram(int Root, astNode program) {
		currentAstToken = astToken.get(astIndex);
		if (currentAstToken.matches("program")) {

			String line = "n" + Root + " [label=\"" + currentAstToken
					+ "\",fillcolor=" + Gray + ",shape=box]";
			astNodeList.add(line);
			astNodeNumber++;

			program.setName(currentAstToken);

			astIndex++;
			currentAstToken = astToken.get(astIndex);

			while (currentAstToken.matches("var")) {

				astNode astVar = new astNode("Decl: var");

				astVar = astDecList(astNodeNumber, 0, astVar);

				astVar.setParent(program);
				program.setChild(astVar);

			}
			if (currentAstToken.matches("begin")) {
				

				astNode begin = new astNode("Statement list");

				begin = astStmtList(astNodeNumber, 0, begin);
				program.setChild(begin);
				begin.setParent(program);

			} else
				System.out
						.println("Error on AST astProgram var or begin expected instead of "
								+ currentAstToken);
		}

		return program;
	}

	// <declarations> ::= VAR ident AS <type> SC <declarations> | e
	// <astDecList>::=<astDecList>|e

	public static astNode astDecList(int Root, int itsParent, astNode root) {

		if (currentAstToken.matches("var")) {
			root.setName("Dec List");

			createAstNode(Root, itsParent, root);

			while (currentAstToken.matches("var")) {
				astIndex++;

				currentAstToken = astToken.get(astIndex);

				if (currentAstToken.matches("[A-Z][A-z0-9]*")) {
					// check variable
					String Name = currentAstToken;
					if (ifVariableDeclared(currentAstToken) == true) {
						System.out.println("astDeclist Error: "
								+ currentAstToken + "  is already declared");
					} else {
						astNode var = new astNode("decl ' " + currentAstToken
								+ "'");
						var = astVar(astNodeNumber, Root, var);
						root.setChild(var);
						// System.out.println("after astVar "+currentAstToken);
						if (currentAstToken.matches("begin"))
							return root;

						// ADD(Name, var);
					}

				}

			}

		}
		return root;
	}

	// <astVar>::=<astVar><astVar>|e
	public static astNode astVar(int Root, int itsParent, astNode root) {
		if (currentAstToken.matches("[A-Z][A-z0-9]*")) {
			if (ifVariableDeclared(currentAstToken) == true)
				System.out.println("error astVar: " + currentAstToken
						+ " : is already declared");

			String variableName = currentAstToken;

			root.setName(currentAstToken);

			astIndex++;
			currentAstToken = astToken.get(astIndex);

			if (currentAstToken.matches("as")) {
				astIndex++;
				currentAstToken = astToken.get(astIndex);
				if (currentAstToken.matches("int")) {

					root.setToIntType();
					createAstNode(Root, itsParent, root);

					astNode Int = new astNode("int");

					Int.setParent(root);
					root.setChild(Int);
					Int.setToIntType();

					ADD(variableName, "int");

					Int = astIdent(astNodeNumber, Root, Int);

					astIndex++;
					currentAstToken = astToken.get(astIndex);

				} else if (currentAstToken.matches("bool")) {
					root.setToBoolType();
					createAstNode(Root, itsParent, root);
					astNode Bool = new astNode("bool");

					Bool.setParent(root);
					root.setChild(Bool);
					Bool.setToBoolType();

					// ADD(variableName, root);
					ADD(variableName, "bool");

					Bool = astIdent(astNodeNumber, Root, Bool);

					astIndex++;
					currentAstToken = astToken.get(astIndex);

				} else
					System.out
							.println("error at astVar int or bool expected instead of :"
									+ currentAstToken);
			} else
				System.out.println("astVar() Error: 'as' expected instead of "
						+ currentAstToken);
			// variableList.add(root);

		} else if (currentAstToken.matches(";")) {
			astIndex++;
			currentAstToken = astToken.get(astIndex);
			if (currentAstToken.matches("var")) {
				astNode decl = new astNode("var");
				decl = astDecList(astNodeNumber, itsParent, decl);
			} else if (currentAstToken.matches("var")) {
				astNode stmtList = new astNode("stmtList");
				stmtList = astStmtList(astNodeNumber, itsParent, stmtList);
			}
		} else
			System.out
					.println("astVar() Error: identifier expected instead of "
							+ currentAstToken);
		if (currentAstToken.matches(";")) {
			astIndex++;
			currentAstToken = astToken.get(astIndex);
		}
		return root;
	}

	// <statementSequence> ::= <statement> SC <statementSequence> | e
	/**
	 * @param Root
	 * @param itsParent
	 * @param begin
	 * @return
	 */
	public static astNode astStmtList(int Root, int itsParent, astNode begin)

	{
		if (currentAstToken.matches("begin")) {
		
			astIndex++;
			currentAstToken = astToken.get(astIndex);
		}

		if (currentAstToken.matches("[A-Z][A-z0-9]*|;|if|while|writeint")) {

			while ((currentAstToken
					.matches("[A-Z][A-z0-9]*|;|if|while|writeint"))) {
		

				if ((currentAstToken.matches("[A-Z][A-z0-9]*")))

				{

					astNode assign = new astNode("Assign");

					astNodeNumber++;
					assign = astAssign(astNodeNumber, Root, assign);

					begin.setChild(assign);
					assign.setParent(begin);
				

				} else if ((currentAstToken.matches("if"))) {
					
				

					astNode ifstmt = new astNode(currentAstToken);

					astNodeNumber++;
					ifstmt = astIf(astNodeNumber, Root, ifstmt);
					begin.setChild(ifstmt);
					ifstmt.setParent(begin);

					
					currentAstToken = astToken.get(astIndex);

				}
				else if ((currentAstToken.matches("while"))) {

					
					astNode whilestmt = new astNode(currentAstToken);

					astNodeNumber++;
					whilestmt = astWhile(astNodeNumber, Root, whilestmt);

					begin.setChild(whilestmt);
					whilestmt.setParent(begin);
					

				} else if ((currentAstToken.matches("writeint"))) {

					astNode writeintstmt = new astNode(currentAstToken);

					astNodeNumber++;
					writeintstmt = astWriteint(astNodeNumber, Root,
							writeintstmt);
					begin.setChild(writeintstmt);
					writeintstmt.setParent(begin);

					

					currentAstToken = astToken.get(astIndex);

				}
				else if (currentAstToken.matches(";"))// if
														// (currentAstToken.matches(";|end"))
				{
					astIndex++;
					currentAstToken = astToken.get(astIndex);

					return begin;
				} else
					System.out.println("Error at astStmtList wrong tokent: "
							+ currentAstToken);

				
				if (currentAstToken.matches("end")
						&& astIndex < astToken.size() - 1)
															
				{
					astIndex++;
					currentAstToken = astToken.get(astIndex);
					if (currentAstToken.matches(";")) {
		
						astIndex--;
						currentAstToken = astToken.get(astIndex);

					}

					
				}

			}
		}

		for (int i = 0; i < begin.child.size(); i++) {
			begin.valid = begin.valid && begin.child.get(i).valid;
		}
		createAstNode(Root, itsParent, begin);

		// ---------------------iloc-------------------

		
		 /* block1.closeBlock();
		  
		  Cluster.addContent(block1.blockConttent); if
		  (Cluster.hasHeader==true)Cluster.addfooter();
		  cfgIlocNodeList.add(Cluster.content);
		  cfgIlocNodeList.add("entry"+"->"+"n0\n");
		  cfgIlocNodeList.add(block1.ilocNode+"->"+"exit\n");
		*/
		return begin;
	}

	// <assignment> ::= ident ASGN <expression>
	public static astNode astAssign(int Root, int itsParent, astNode root) {
		if ((currentAstToken.matches("[A-Z][A-z0-9]*"))) {
			String leftOp = currentAstToken;
			astNode leftOperand = new astNode(leftOp);

			if (ifVariableDeclared(currentAstToken) == false)

			{
				System.out.println("Error astAssign: Variable not declared: "
						+ currentAstToken + " Token number  : " + astIndex);

				leftOperand.valid = false;

			} else {
				
				leftOperand.type = declaredVariable(currentAstToken);
				leftOperand.valid = true;
				
				System.out.println(leftOperand.name + " is defined as "
						+ leftOperand.type);
				
			}

			leftOperand.setName(currentAstToken);

			

			leftOperand.setParent(root);
			root.setChild(leftOperand);

			astIndex++;
			currentAstToken = astToken.get(astIndex);

			if ((currentAstToken.matches(":="))) {

				root.name = ":=";

				leftOperand.setParent(root);
				root.setChild(leftOperand);

				astIndex++;
				currentAstToken = astToken.get(astIndex);

				if (currentAstToken.matches("readint")) {
					astNode rightOperand = new astNode(currentAstToken);
					rightOperand.setToIntType();
					
					rightOperand.setParent(root);
					root.setChild(rightOperand);

					root.ilocInstruction = leftOperand.ilocInstruction
							+ rightOperand.ilocInstruction;

					if (leftOperand.type == "int" && rightOperand.type == "int") {
						root.setToIntType();
						root.setValid(true, root);
					}
					
					astNodeNumber++;

					createAstNode(astNodeNumber, Root, leftOperand);
					root.setChild(leftOperand);
					leftOperand.setParent(root);
					createAstNode(astNodeNumber, Root, rightOperand);
					createAstNode(Root, itsParent, root);

					astIndex++;
					currentAstToken = astToken.get(astIndex);

					if (currentAstToken.matches(";")) {
						astIndex++;
						currentAstToken = astToken.get(astIndex);
						return root;
					}
				} else if ((currentAstToken
						.matches("[A-Z][A-z0-9]*|[1-9][0-9]*|0|true|false|[(]"))) {

					astNode rightOperand = new astNode(currentAstToken);

					astNodeNumber++;

					createAstNode(astNodeNumber, Root, leftOperand);
					leftOperand.setParent(root);
					root.setChild(leftOperand);

					rightOperand = ast_Expression(astNodeNumber, Root,
							rightOperand);

				
					root.setChild(rightOperand);
					rightOperand.setParent(root);

					if (leftOperand.type == "int" && rightOperand.type == "int") {
						root.setToIntType();
						root.setValid(true, root);
					} else
						root.setValid(false, root);
				

					
					createAstNode(Root, itsParent, root);

					
					if (currentAstToken
							.matches("end|if|writeint|while|do|then|else|[A-Z][A-z0-9]*"))
						return root;// we have to return the else
					astIndex++;
					currentAstToken = astToken.get(astIndex);

				}

			}

		}
		if (currentAstToken.matches(";")) {
			astIndex++;
			currentAstToken = astToken.get(astIndex);
			return root;
		}

		return root;
	}

	public static astNode astIf1(int Root, int itsParent, astNode root) {
		if (currentAstToken.matches("if")) {

			root.setName(currentAstToken);
			astIndex++;
			currentAstToken = astToken.get(astIndex);

			astNode expression = new astNode("expression");

			astNodeNumber++;
			expression = ast_Expression(astNodeNumber, Root, expression);

			if (expression.valid == false || expression.type != "bool")
				root.valid = false;
			createAstNode(Root, itsParent, root);
			root.setChild(expression);
			expression.setParent(root);
			if (currentAstToken.matches("then")) {
				astIndex++;
				currentAstToken = astToken.get(astIndex);

				if (currentAstToken
						.matches("[A-Z][A-z0-9]*|;|if|while|writeint")) {
					astNode statment = new astNode("Statmet");

					astNodeNumber++;
					statment = astStmtList(astNodeNumber, Root, statment);
					statment.setParent(root);
					root.setChild(statment);

     			}
				System.out.print("hhhhhhhhhhhhhhh " + currentAstToken);
				if (currentAstToken.matches("else")) {
					
					astNode elsestatment = new astNode("Statmet");
					elsestatment = astStmtList(astNodeNumber, Root,
							elsestatment);
					elsestatment.setParent(root);
					root.setChild(elsestatment);

				}
				if (currentAstToken.matches("end")) {
					astIndex++;
					currentAstToken = astToken.get(astIndex);
					if (currentAstToken.matches(";")) {
						astIndex++;
						currentAstToken = astToken.get(astIndex);

						return root;
					}
				}

			}

		}
		if (currentAstToken.matches(";")) {
			astIndex++;
			currentAstToken = astToken.get(astIndex);
			return root;
		}

		return root;
	}

	public static astNode astIf(int Root, int itsParent, astNode root) {
		if (currentAstToken.matches("if")) {

			root.setName(currentAstToken);
			astIndex++;
			currentAstToken = astToken.get(astIndex);
			
			astNode expression = new astNode("expression");
			astNodeNumber++;
			expression = ast_Expression(astNodeNumber, Root, expression);

			if (expression.valid == false || expression.type != "bool")
				root.valid = false;
			createAstNode(Root, itsParent, root);
			root.setChild(expression);
			expression.setParent(root);

			if (currentAstToken.matches("then")) {
				astIndex++;
				currentAstToken = astToken.get(astIndex);

				if (currentAstToken.matches("[A-Z][A-z0-9]*|;|if|while|writeint")) {
					astNode statment = new astNode("ifStatmet");
					astNodeNumber++;
					statment = astStmtList(astNodeNumber, Root, statment);
					statment.setParent(root);
					root.setChild(statment);
					if (currentAstToken.matches("end")&&astIndex+1<=astToken.size()-1) {
						astIndex++;
						currentAstToken = astToken.get(astIndex);
						if (currentAstToken.matches(";")) {
						
							astIndex++;

							currentAstToken = astToken.get(astIndex);
						}
						return root;
					} else if (currentAstToken.matches("else")) 
					{astNode elseStmt = new astNode("elseStatment");
					elseStmt=astEelse(astNodeNumber, Root, elseStmt);
						
					}
				}

				if (currentAstToken.matches("end")&&astIndex+1<=astToken.size()-1) {
					astIndex++;
					currentAstToken = astToken.get(astIndex);
					if (currentAstToken.matches(";")) {
						astIndex++;
						currentAstToken = astToken.get(astIndex);

						return root;
					}
				}

			}

		}
		if (currentAstToken.matches(";")) {
			astIndex++;
			currentAstToken = astToken.get(astIndex);
			return root;
		}

		return root;
	}

	
	
	public static astNode astEelse(int Root, int itsParent,astNode root){

		
		astIndex++;

		currentAstToken = astToken.get(astIndex);
		
		root.name="elseStatmet";
		astNode elsestatment = new astNode("elseStatmet");

		astNodeNumber++;
		root = astStmtList(Root, itsParent,
				root);
		elsestatment.setParent(root);
		root.setChild(elsestatment);
		
		currentAstToken = astToken.get(astIndex);
		
		if (currentAstToken.matches("end")) {
			astIndex++;
			currentAstToken = astToken.get(astIndex);
			if (currentAstToken.matches(";")) {
				astIndex++;

				currentAstToken = astToken.get(astIndex);
			}
			return root;
		}
		return root;
	}
	
	
	
	
	public static astNode astWhile(int Root, int itsParent, astNode root) {
		if (currentAstToken.matches("while")) {
			root.setName(currentAstToken);

			astIndex++;
			currentAstToken = astToken.get(astIndex);

			astNode expression = new astNode("expression");

			astNodeNumber++;
			expression = ast_Expression(astNodeNumber, Root, expression);
			root.setChild(expression);
			expression.setParent(root);

			if (expression.valid == false || expression.type != "bool")
				root.valid = false;
			createAstNode(Root, itsParent, root);


			if (currentAstToken.matches("do")) {
				astIndex++;
				currentAstToken = astToken.get(astIndex);
				if (currentAstToken
						.matches("[A-Z][A-z0-9]*|;|if|while|writeint")) {
					astNode statment = new astNode("statment");
					statment = astStmtList(astNodeNumber, Root, statment);
					statment.setParent(root);
					root.setChild(statment);

					if (currentAstToken.matches("end")
							&& astIndex + 1 < astToken.size() - 1) {
						System.out
								.println(currentAstToken
										+ " at the last line of astWhile token number:  "
										+ astIndex);
						astIndex++;

						currentAstToken = astToken.get(astIndex);
						if (currentAstToken.matches(";")) {
							astIndex++;
							currentAstToken = astToken.get(astIndex);

							return root;

						}
					}

				}
			}
		}

		return root;
	}

	public static astNode astWriteint(int Root, int itsParent, astNode root) {
		if (currentAstToken.matches("writeint")) {
			root.setName(currentAstToken);

			astIndex++;
			currentAstToken = astToken.get(astIndex);
			if (currentAstToken.matches("[A-Z][A-z0-9]*|[1-9][0-9]*|0")) {
				astNode expression = new astNode("expression");

				astNodeNumber++;

				expression = ast_Expression(astNodeNumber, Root, expression);

				expression.setParent(root);
				root.setChild(expression);

				if (expression.type == "int") {
					root.setToIntType();
					root.setValid(true, root);
				} else
					root.setValid(false, root);
				
				createAstNode(Root, itsParent, root);

			}
		}
		if (currentAstToken.matches(";")) {
			astIndex++;
			currentAstToken = astToken.get(astIndex);
		}
		return root;
	}



	public static astNode astBoolit(int Root, int itsParent, astNode root) {

		createAstNode(Root, itsParent, root);

		return root;
	}

	public static astNode astNum(int Root, int itsParent, astNode root) {

		createAstNode(Root, itsParent, root);

		return root;
	}

	public static astNode astIdent(int Root, int itsParent, astNode root) {

		createAstNode(Root, itsParent, root);

		return root;
	}

	public static void ADD(String S, String type) {
		variableList.add(S);
		variableListType.add(type);
		System.out.println(S + "    is declared as    " + type);
		variableListIndex++;

	}

	

	public static boolean ifVariableDeclared(String S) {

		if (variableList.contains(S)) {

			return true;

		} else
			return false;

	}

	public static String declaredVariable(String S) {

		return variableListType.get(variableList.indexOf(S));

	}
	
	public static astNode astExpression1(int Root, int itsParent, astNode root) {
		if (currentAstToken
				.matches("[A-Z][A-z0-9]*|[1-9][0-9]*|0|true|false|[(]")) {
			astNode leftOpeand = new astNode(currentAstToken);
			astIndex++;
			currentAstToken = astToken.get(astIndex);
			if (currentAstToken.matches("=|!=|<|>|<=|>=")) {
				root.name = currentAstToken;
				astIndex++;
				currentAstToken = astToken.get(astIndex);
				if (currentAstToken
						.matches("[A-Z][A-z0-9]*|[1-9][0-9]*|0|true|false|[(]")) {
					astNode rightOperand = new astNode(currentAstToken);

					astIndex--;
					astIndex--;
					currentAstToken = astToken.get(astIndex);

					astNodeNumber++;
					leftOpeand = astTerm(astNodeNumber, Root, leftOpeand);

					root.setChild(leftOpeand);
					leftOpeand.setParent(root);

					astIndex++;
					astIndex++;
					currentAstToken = astToken.get(astIndex);

					rightOperand = astTerm(astNodeNumber, Root, rightOperand);
					root.setChild(rightOperand);
					rightOperand.setParent(root);

					if ((leftOpeand.type == "int" && rightOperand.type == "int")
							|| ((leftOpeand.type == "bool" && rightOperand.type == "bool"))) {
						root.type = "bool";
						root.setValid(true, root);
					} else
						root.setValid(false, root);
			
					createAstNode(Root, itsParent, root);

					astIndex++;
					currentAstToken = astToken.get(astIndex);

					if (currentAstToken.matches(";")) {
						astIndex++;
						currentAstToken = astToken.get(astIndex);
					}
				}
			} else {
				astIndex--;
				currentAstToken = astToken.get(astIndex);

				root = astTerm(Root, itsParent, root);

				if (currentAstToken.matches(";")) {
					astIndex++;
					currentAstToken = astToken.get(astIndex);
				}
			}
		}

		return root;
	}

	public static astNode astTerm(int Root, int itsParent, astNode root) {
		if (currentAstToken
				.matches("[A-Z][A-z0-9]*|[1-9][0-9]*|0|true|false|[(]")) {
			astNode leftOpeand = new astNode(currentAstToken);
			astIndex++;
			currentAstToken = astToken.get(astIndex);
			if (currentAstToken.matches("[+]|[-]")) {
				root.name = currentAstToken;
				astIndex++;
				currentAstToken = astToken.get(astIndex);
				if (currentAstToken
						.matches("[A-Z][A-z0-9]*|[1-9][0-9]*|0|true|false|[(]")) {
					astNode rightOperand = new astNode(currentAstToken);

					astIndex--;
					astIndex--;
					currentAstToken = astToken.get(astIndex);

					astNodeNumber++;
					leftOpeand = astFactor(astNodeNumber, Root, leftOpeand);
					root.setChild(leftOpeand);
					leftOpeand.setParent(root);

					astIndex++;
					astIndex++;
					currentAstToken = astToken.get(astIndex);

					rightOperand = astFactor(astNodeNumber, Root, rightOperand);
					root.setChild(rightOperand);
					rightOperand.setParent(root);

					if ((leftOpeand.type == "int" && rightOperand.type == "int")) {
						root.type = "int";
						root.setValid(true, root);
					} else
						root.setValid(false, root);

					
					createAstNode(Root, itsParent, root);

					astIndex++;
					currentAstToken = astToken.get(astIndex);

					if (currentAstToken.matches(";")) {
						astIndex++;
						currentAstToken = astToken.get(astIndex);
					}
				}
			} else {
				astIndex--;
				currentAstToken = astToken.get(astIndex);
				root.name = currentAstToken;
				root = astFactor(Root, itsParent, root);

				if (currentAstToken.matches(";")) {
					astIndex++;
					currentAstToken = astToken.get(astIndex);
				}
			}
		}

		return root;
	}

	public static astNode astFactor(int Root, int itsParent, astNode root) {
		if (currentAstToken
				.matches("[A-Z][A-z0-9]*|[1-9][0-9]*|0|true|false|[(]")) {
			astNode leftOpeand = new astNode(currentAstToken);
			astIndex++;
			currentAstToken = astToken.get(astIndex);
			if (currentAstToken.matches("[*]|div|mod")) {

				root.name = currentAstToken;
				astIndex++;
				currentAstToken = astToken.get(astIndex);

				if (currentAstToken
						.matches("[A-Z][A-z0-9]*|[1-9][0-9]*|0|true|false|[(]")) {
					astNode rightOperand = new astNode(currentAstToken);

					astIndex--;
					astIndex--;
					currentAstToken = astToken.get(astIndex);

					astNodeNumber++;
					leftOpeand = astVariable(astNodeNumber, Root, leftOpeand);
					root.setChild(leftOpeand);
					leftOpeand.setParent(root);

					astIndex++;
					astIndex++;
					currentAstToken = astToken.get(astIndex);

					rightOperand = astVariable(astNodeNumber, Root,
							rightOperand);
					root.setChild(rightOperand);
					rightOperand.setParent(root);

					if ((leftOpeand.type == "int" && rightOperand.type == "int")) {
						root.type = "int";
						root.setValid(true, root);
					} else
						root.setValid(false, root);


					createAstNode(Root, itsParent, root);

					astIndex++;
					currentAstToken = astToken.get(astIndex);

					if (currentAstToken.matches(";")) {
						astIndex++;
						currentAstToken = astToken.get(astIndex);
					}
				}
			} else {
				astIndex--;
				currentAstToken = astToken.get(astIndex);
				root.name = currentAstToken;
				root = astVariable(Root, itsParent, root);

				if (currentAstToken.matches(";")) {
					astIndex++;
					currentAstToken = astToken.get(astIndex);
				}
			}
		}
		return root;
	}

	public static astNode astVariable(int Root, int itsParent, astNode root) {
		astNode Operand = new astNode(root.name);
		if (currentAstToken.matches("[A-Z][A-z0-9]*")) {

			root.setName(currentAstToken);

			if (ifVariableDeclared(currentAstToken) == true) {
				root.type = variableListType.get(variableList
						.indexOf(currentAstToken));
				root.setValid(true, root);
		
			}
			Operand = root;

		} else if (currentAstToken.matches("[1-9][0-9]*|0")) {
			
			root.setName(currentAstToken);
			// must check if its greater
			root.setToIntType();
			Operand = root;


		} else if (currentAstToken.matches("true|false")) {
			root.setName(currentAstToken);
			root.setToBoolType();
			Operand = root;


		} else if (currentAstToken.matches("[(]")) {
			astIndex++;
			currentAstToken = astToken.get(astIndex);
			root = astExpression1(Root, itsParent, root);
			if (currentAstToken.matches("[)]"))
				return root;

		}

		
		createAstNode(Root, itsParent, root);

		return root;
	}

	public static void createAstNode(int itsNumber, int itsParent, astNode Node) {
		String itsColor = White;
		String itsShape;
		if (Node.valid == true) {
			if (Node.type == "int")
				itsColor = Gray;
			else if (Node.type == "bool")
				itsColor = Green;
		} else
			itsColor = Pink;

		if (Node.type == "int" || Node.type == "bool")
			itsShape = "box";
		else
			itsShape = "none";

		String line = "n" + itsNumber + " [label=\"" + Node.name
				+ "\",fillcolor=" + itsColor + ",shape=" + itsShape + "]";
		astNodeList.add(line);
		String relation = "n" + itsParent + " -> n" + itsNumber + ";";
		astNodeList.add(relation);
		astNodeNumber++;
	}

	public static void chektype(astNode root, String S) {
		boolean check = true;

		for (int i = 0; i < root.child.size(); i++) {
			if (root.child.get(i).type == S) {
				root.valid = root.valid && true;
			} else
				root.valid = root.valid && false;

			if (root.valid == true) {

				if (root.name.matches("[+]|[-]|[*]|div|mod"))
					root.type = "int";
				else if (root.name.matches("=|!=|<|>|<=|>="))
					root.type = "bool";
			}

		}

	}

	public static void createAstFile(String filepath) {

		try {
			String header = "digraph AST {\n  ordering=out;\n  node [shape = box, style = filled];\n";

			String EOF = "\n}";

			FileWriter WriteToFile = new FileWriter(filepath);

			PrintWriter WritToMemory = new PrintWriter(WriteToFile);
			WritToMemory.println(header);
			for (int i = 0; i < astNodeList.size(); i++) {

				WritToMemory.println(astNodeList.get(i));

			}

			WritToMemory.println(EOF);
			WritToMemory.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}



	// <expression> ::= <simpleExpression> <simpleExpression'>
	public static astNode ast_Expression(int Root, int itsParent, astNode root) {
		if (currentAstToken
				.matches("[A-Z][A-z0-9]*|[1-9][0-9]*|0|true|false|[(]")) {
			int positionIndex = astIndex;
			astNode test = new astNode("test");
			astNode Operande1 = new astNode(currentAstToken);

			test = TestsimpleExpression(test);
			if (currentAstToken.matches("=|!=|<|>|<=|>=")) {
				astIndex = positionIndex;
				currentAstToken = astToken.get(astIndex);
				Operande1.name = currentAstToken;

				astNodeNumber++;
				Operande1 = ast_simpleExpression(astNodeNumber, Root, Operande1);

				root.name = currentAstToken;
				root.setChild(Operande1);
				root = ast_simpleExpressionPrime(Root, itsParent, root);
				root.name = currentAstToken;
			} else {
				astIndex = positionIndex;
				currentAstToken = astToken.get(astIndex);
				root.name = currentAstToken;
				root = ast_simpleExpression(Root, itsParent, root);

			}
			if (currentAstToken.matches(";")) {

				astIndex++;
				currentAstToken = astToken.get(astIndex);

			}
		}
		return root;
	}

	// <simpleExpression'> ::= OP4 <simpleExpression> | e
	public static astNode ast_simpleExpressionPrime(int Root, int itsParent,
			astNode root) {
		if (currentAstToken.matches("=|!=|<|>|<=|>=")) {
			root.name = currentAstToken;

			astIndex++;
			currentAstToken = astToken.get(astIndex);
			astNodeNumber++;
			astNode Simple = new astNode(currentAstToken);
			Simple = ast_simpleExpression(astNodeNumber, Root, Simple);
			root.setChild(Simple);
			// /=================================================================

			if ((root.child.get(0).type == "int" && root.child.get(1).type == "int")
					|| ((root.child.get(0).type == "bool" && root.child.get(1).type == "bool"))) {
				root.type = "bool";
				root.setValid(true, root);
			} else
				root.setValid(false, root);
			
			// ==================================================================
			// createAstNode(Root, itsParent, root);
			createAstNode(Root, itsParent, root);
			
		}
		return root;
	}

	// <simpleExpression> ::= <term> <term'>

	public static astNode ast_simpleExpression(int Root, int itsParent,
			astNode root) {
		if (currentAstToken
				.matches("[A-Z][A-z0-9]*|[1-9][0-9]*|0|true|false|[(]")) {
			astNode test = new astNode("test");
			int positionIndex = astIndex;
			astNode Operande1 = new astNode(currentAstToken);
			astNodeNumber++;
			test = Testterm(test);
			if (currentAstToken.matches("[+]|[-]")) {

				astIndex = positionIndex;
				currentAstToken = astToken.get(astIndex);

				Operande1.name = currentAstToken;
				astNodeNumber++;
				Operande1 = ast_term(astNodeNumber, Root, Operande1);
				root.setChild(Operande1);
				root.name = currentAstToken;
				root = ast_termPrime(Root, itsParent, root);

			} else {
				astIndex = positionIndex;
				currentAstToken = astToken.get(astIndex);
				root.name = currentAstToken;
				root = ast_term(Root, itsParent, root);
				return root;

			}
		}

		return root;
	}

	// <term'> ::= OP3 <term> | e
	public static astNode ast_termPrime(int Root, int itsParent, astNode root) {
		if (currentAstToken.matches("[+]|[-]")) {
			root.name = currentAstToken;

			astIndex++;
			currentAstToken = astToken.get(astIndex);
			astNode operand = new astNode(currentAstToken);
			astNodeNumber++;
			operand = ast_term(astNodeNumber, Root, operand);
			root.setChild(operand);
			// ****************************************************

			if ((root.child.get(0).type == "int" && root.child.get(1).type == "int")) {
				root.type = "int";
				root.setValid(true, root);
			} else
				root.setValid(false, root);

			createAstNode(Root, itsParent, root);
		} else
			return root;

		return root;
	}

	// <term> ::= <factor> <factor'>
	public static astNode ast_term(int Root, int itsParent, astNode root) {
		if (currentAstToken
				.matches("[A-Z][A-z0-9]*|[1-9][0-9]*|0|true|false|[(]")) {
			astNode test = new astNode("test");
			int operandIndex = astIndex;
			astNode Operand = new astNode(currentAstToken);
			astNodeNumber++;
			test = Testfactor(test);

			if (currentAstToken.matches("[*]|div|mod")) {

				astIndex = operandIndex;
				currentAstToken = astToken.get(astIndex);

				astNodeNumber++;
				Operand = ast_factor(astNodeNumber, Root, Operand);
				root.setChild(Operand);
				root = ast_factorPrime(Root, itsParent, root);

				return root;

			} else {

				astIndex = operandIndex;
				currentAstToken = astToken.get(astIndex);
				root.name = currentAstToken;
				root = ast_factor(Root, itsParent, root);
				return root;

			}

		}
		return root;
	}

	// <factor'> ::= OP2 <factor> | e
	public static astNode ast_factorPrime(int Root, int itsParent, astNode root) {
		if (currentAstToken.matches("[*]|div|mod")) {
			root.name = currentAstToken;
			astIndex++;
			currentAstToken = astToken.get(astIndex);
			astNode operand = new astNode(currentAstToken);
			astNodeNumber++;
			operand = ast_factor(astNodeNumber, Root, operand);
			root.setChild(operand);
			// *******************************************************

			if ((root.child.get(0).type == "int" && root.child.get(1).type == "int")) {
				root.type = "int";
				root.setValid(true, root);
			} else
				root.setValid(false, root);

		  	createAstNode(Root, itsParent, root);

		} else
			return root;
		return root;
	}

	// <factor> ::= ident | num | boollit | LP <expression> RP
	public static astNode ast_factor(int Root, int itsParent, astNode root) {
		astNode Operand = new astNode(root.name);
		if (currentAstToken.matches("[A-Z][A-z0-9]*")) {

			root.setName(currentAstToken);

			if (ifVariableDeclared(currentAstToken) == true) {
				root.type = variableListType.get(variableList
						.indexOf(currentAstToken));
				root.setValid(true, root);
				
			} else {
				root.setValid(false, root);

			}
			Operand = root;

		} else if (currentAstToken.matches("[1-9][0-9]*|0")) {
			// System.out.println("4.2   : "+currentAstToken);
			root.setName(currentAstToken);
			// must check if its greater
			root.setToIntType();
			Operand = root;

			
		} else if (currentAstToken.matches("true|false")) {
			root.setName(currentAstToken);
			root.setToBoolType();
			Operand = root;

			
		} else if (currentAstToken.matches("[(]")) {
			astIndex++;
			currentAstToken = astToken.get(astIndex);
			root = ast_Expression(Root, itsParent, root);
			if (currentAstToken.matches("[)]"))
				return root;

		}

		
		astIndex++;
		currentAstToken = astToken.get(astIndex);

		createAstNode(Root, itsParent, root);

		return root;

	}

	// /---------------visitors------------------------------
	// <expression> ::= <simpleExpression> <simpleExpression'>
	public static astNode testExpression(astNode root) {
		if (currentAstToken
				.matches("[A-Z][A-z0-9]*|[1-9][0-9]*|0|true|false|[(]")) {
			int positionIndex = astIndex;
			astNode Operande1 = new astNode(currentAstToken);

			Operande1 = TestsimpleExpression(Operande1);
			if (currentAstToken.matches("=|!=|<|>|<=|>=")) {
				astIndex = positionIndex;
				currentAstToken = astToken.get(astIndex);
				Operande1.name = currentAstToken;

				Operande1 = TestsimpleExpression(Operande1);

				root.name = currentAstToken;
				root = TestsimpleExpressionPrime(root);
				root.name = currentAstToken;
				root.setChild(Operande1);

			} else {
				astIndex = positionIndex;
				currentAstToken = astToken.get(astIndex);
				root.name = currentAstToken;
				root = TestsimpleExpression(root);

				return root;

			}
			if (currentAstToken.matches(";")) {

				astIndex++;
				currentAstToken = astToken.get(astIndex);

			}
		}
		return root;
	}

	// <simpleExpression'> ::= OP4 <simpleExpression> | e
	public static astNode TestsimpleExpressionPrime(astNode root) {
		if (currentAstToken.matches("=|!=|<|>|<=|>=")) {
			root.name = currentAstToken;

			astIndex++;
			currentAstToken = astToken.get(astIndex);

			astNode Simple = new astNode(currentAstToken);
			Simple = TestsimpleExpression(Simple);

		}
		return root;
	}

	// <simpleExpression> ::= <term> <term'>

	public static astNode TestsimpleExpression(astNode root) {
		if (currentAstToken
				.matches("[A-Z][A-z0-9]*|[1-9][0-9]*|0|true|false|[(]")) {
			int positionIndex = astIndex;
			astNode Operande1 = new astNode(currentAstToken);

			Operande1 = Testterm(Operande1);
			if (currentAstToken.matches("[+]|[-]")) {

				astIndex = positionIndex;
				currentAstToken = astToken.get(astIndex);

				Operande1.name = currentAstToken;

				Operande1 = Testterm(Operande1);
				root.name = currentAstToken;
				root = TesttermPrime(root);
			} else {
				astIndex = positionIndex;
				currentAstToken = astToken.get(astIndex);
				root.name = currentAstToken;
				root = Testterm(root);
				return root;

			}
		}
		return root;
	}

	// <term'> ::= OP3 <term> | e
	public static astNode TesttermPrime(astNode root) {
		if (currentAstToken.matches("[+]|[-]")) {
			root.name = currentAstToken;

			astIndex++;
			currentAstToken = astToken.get(astIndex);
			astNode operand = new astNode(currentAstToken);

			operand = Testterm(operand);
			root.setChild(operand);

		} else
			return root;

		return root;
	}

	// <term> ::= <factor> <factor'>
	public static astNode Testterm(astNode root) {
		//System.out.println("==================>" + currentAstToken
	//			+ "===========>" + astIndex);
		if (currentAstToken
				.matches("[A-Z][A-z0-9]*|[1-9][0-9]*|0|true|false|[(]")) {
			int operandIndex = astIndex;
			astNode Operand = new astNode(currentAstToken);

			Operand = Testfactor(Operand);
			root.setChild(Operand);
			if (currentAstToken.matches("[*]|div|mod")) {

				astIndex = operandIndex;
				currentAstToken = astToken.get(astIndex);

				Operand = Testfactor(Operand);
				root = TestfactorPrime(root);

				return root;

			} else {

				astIndex = operandIndex;
				currentAstToken = astToken.get(astIndex);
				root.name = currentAstToken;
				root = Testfactor(root);
				return root;

			}

		}
		return root;
	}

	// <factor'> ::= OP2 <factor> | e
	public static astNode TestfactorPrime(astNode root) {
		if (currentAstToken.matches("[*]|div|mod")) {
			root.name = currentAstToken;
			astIndex++;
			currentAstToken = astToken.get(astIndex);
			astNode operand = new astNode(currentAstToken);

			operand = Testfactor(operand);

		} else
			return root;
		return root;
	}

	// <factor> ::= ident | num | boollit | LP <expression> RP
	public static astNode Testfactor(astNode root) {

		astNode Operand = new astNode(root.name);
		if (currentAstToken.matches("[A-Z][A-z0-9]*")) {

			root.setName(currentAstToken);

			Operand = root;

		} else if (currentAstToken.matches("[1-9][0-9]*|0")) {
			Operand = root;

		} else if (currentAstToken.matches("true|false")) {
			root.setName(currentAstToken);

		} else if (currentAstToken.matches("[(]")) {
			astIndex++;
			currentAstToken = astToken.get(astIndex);
			root = testExpression(root);
			if (currentAstToken.matches("[)]"))
				return root;

		}

		astIndex++;
		currentAstToken = astToken.get(astIndex);

		return root;

	}
/////// try to refactor the ast with this grammar

	//<program>::=program<declaration list>begin<statment list>end
	public static astNode Program(int Root, astNode root){
		if (currentAstToken.matches("program")){
			astIndex++;
			currentAstToken = astToken.get(astIndex);
			if (currentAstToken.matches("var")){
				astNode varList=new astNode("dec_list");
				varList=DecList(astNodeNumber, Root, varList);
				 if (currentAstToken.matches("begin")){
						astIndex++;
						currentAstToken = astToken.get(astIndex);
						
						astNode Stamen_tList=new astNode("StatementList");
						Stamen_tList=StatementList(astNodeNumber, Root, Stamen_tList);
						if (currentAstToken.matches("end")){
							System.out.println("ASTNode Programe: 1-Ast Tree Done!!");
						}
						}
			}else if (currentAstToken.matches("begin")){
				astIndex++;
				currentAstToken = astToken.get(astIndex);
				
				astNode Stamen_tList=new astNode("StatementList");
				Stamen_tList=StatementList(astNodeNumber, Root, Stamen_tList);
				if (currentAstToken.matches("end")){
					System.out.println("ASTNode Programe: 2-Ast Tree Done!!");
				}
				}
		}
		return root;
	}
	//<declaration list> ::= <declaration>SC<declarationlist>| e
	public static astNode DecList(int Root, int itsParent, astNode root){
		if (currentAstToken.matches("begin")){
			return root; 
		}else if (currentAstToken.matches("var")){
			
			astNode delaration = new astNode("Declaration");
			delaration=Declaration(astNodeNumber, Root, delaration);
			if (currentAstToken.matches(";")){
				astIndex++;
				currentAstToken = astToken.get(astIndex);
				
			}
		}
		return root;
	}
	//<declaration> ::= var x as int ;
	public static astNode Declaration(int Root, int itsParent, astNode root){
		return root;
	}

	//<type> ::= INT | BOOL
	//<statementList> ::=begin <statmentsequence>end
	public static astNode StatementList(int Root, int itsParent, astNode root){
		return root;
	}

	//<statementsequence>::=<statment>sc <statment sequence>|e
	public static astNode StatementSequence(int Root, int itsParent, astNode root){
		return root;
	}

	//<statement> ::= <assignment> | <ifStatement>  | <whileStatement> | <writeint>
	
	public static astNode Statment(int Root, int itsParent, astNode root){
		return root;
	}

	//<assignment> ::= ident ASGN <assignment'>
	public static astNode Assignment(int Root, int itsParent, astNode root){
		return root;
	}

	//<assignment'> ::= <expression>sc | READINT 
	
	public static astNode AssigmentPrime(int Root, int itsParent, astNode root){
		return root;
	}

	//<ifStatement> ::= IF <expression> THEN <statementSequence> <elseClause> END
	public static astNode IfStatement(int Root, int itsParent, astNode root){
		return root;
	}

	//<elseClause> ::= ELSE <statementSequence> | e
	public static astNode ElseClause(int Root, int itsParent, astNode root){
		return root;
	}

	//<whileStatement> ::= WHILE <expression> DO <statementSequence> END
	public static astNode WhileStatement(int Root, int itsParent, astNode root){
		return root;
	}

	//<writeint> ::= WRITEiNT <expression>
	public static astNode Writeint(int Root, int itsParent, astNode root){
		return root;
	}

	//<expression> ::= <simpleExpression> <simpleExpression'>
	public static astNode Expression(int Root, int itsParent, astNode root){
		return root;
	}

	//<simpleExpression'> ::= OP4 <simpleExpression> | e
	public static astNode SimpleExpressionPrime(int Root, int itsParent, astNode root){
		return root;
	}

	//<simpleExpression> ::= <term> <term'>
	
	public static astNode SimpleExpression(int Root, int itsParent, astNode root){
		return root;
	}

	//<term'> ::= OP3 <term> | e
	public static astNode TermPrime(int Root, int itsParent, astNode root){
		return root;
	}

	//<term> ::= <factor> <factor'>
	public static astNode Term(int Root, int itsParent, astNode root){
		return root;
	}

	//<factor'> ::= OP2 <factor> | e
	
	public static astNode FactorPrime(int Root, int itsParent, astNode root){
		return root;
	}

	//<factor> ::= ident   | num  | boollit  | LP <expression> RP
	public static astNode Factor(int Root, int itsParent, astNode root){
		return root;
	}

	
}

