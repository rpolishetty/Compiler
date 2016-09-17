//package edu.utsa.tl15;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class Compiler{
    public static void main (String[] args) throws Exception {
        System.out.println("========================================== Code Generation Phase =========================================");
        System.out.println("\nCodeGen Start....");
        System.out.println("\nControl Flow Graph Dot File: \n" + args[0]+".cfg.dot");
        System.out.println("\nCode Generation File: \n" + args[0]+".s");

        String inputFileName = args[0];
        int baseNameOffset = inputFileName.length()-3;

        String baseName;
        if (inputFileName.substring(baseNameOffset).equals(".tl"))
            baseName = inputFileName.substring(0,baseNameOffset);
        else
            throw new RuntimeException("inputFileName does not end in .tl");

        //String parseOutName1 = baseName + ".tl" + ".ast.dot";
        String parseOutName2 = baseName + ".tl" +  ".cfg.dot";
        String parseOutName3 = baseName + ".tl" +  ".s";

    
        //System.out.println("Input file: " + inputFileName);
    	//System.out.println("Output file: " + parseOutName1);
    	
    	InputStream tl15In1 = new FileInputStream(inputFileName);
    	//OutputStream parseOut1 = new FileOutputStream(parseOutName1);	
    	
    	//ParserAst parser1 = new ParserAst(tl15In1, parseOut1);
    	
    	
    	
    	//System.out.println("Input file: " + inputFileName);
    	//System.out.println("Output file: " + parseOutName2);
    	
    	InputStream tl15In2 = new FileInputStream(inputFileName);
    	OutputStream parseOut2 = new FileOutputStream(parseOutName2);	
    	
    	ParserCfg parser2 = new ParserCfg(tl15In2, parseOut2);
    	
    	
        
        
		//System.out.println("Input file: " + inputFileName);
		//System.out.println("Output file: " + parseOutName3);
		
		InputStream tl15In3 = new FileInputStream(inputFileName);
		OutputStream parseOut3 = new FileOutputStream(parseOutName3);	
		
		Parser3 parser3 = new Parser3(tl15In3, parseOut3);
	
	
	
    }
}

	
