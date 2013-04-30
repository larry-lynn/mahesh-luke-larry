package compiler;

import java.io.*;
import java.util.ArrayList;

public class Parser {
    Token lookahead;
    Scanner scan;
    SymbolTableMaster symbolTableHandle;
    SemanticAnalyzer analyze;
    
    PrintWriter logFileHandle;
    PrintWriter ruleFileHandle;
    Boolean debug;


    public static void main(String[] args) throws Exception{
        if( (args.length == 0) || (args.length > 1) ){
            System.out.println("Usage: java compiler.Parser <source-code-file>");
            System.exit(-3);
        }
        
        String infile = args[0];
        String message = "Working Directory = " +  System.getProperty("user.dir");
        //Boolean debugOn = true;
        Boolean debugOn = false;
        
        Parser parse;
        
        parse = new Parser(infile, debugOn);
        
        //Write input file name to LogFile.txt and RulesAppled.txt
        parse.infoLog(message);
        parse.ruleLog(message);
        
        // Recursively create the entire parser tree
        parse.SystemGoal();
        
        parse.cleanup();
        
    }
    
    // Constructor 1
    public Parser(String fileWithPath) throws Exception {
        scan = new Scanner();
        scan.openFile(fileWithPath);
        symbolTableHandle = new SymbolTableMaster();
        // share the symbol table with the static analyzer
        analyze = new SemanticAnalyzer(fileWithPath, symbolTableHandle);
        
        lookahead = scan.getToken();
        logFileHandle = new PrintWriter(fileWithPath + ".infolog.txt");
        ruleFileHandle = new PrintWriter(fileWithPath + ".rulelog.txt");

        
    }
    
    // Constructor 2
    public Parser(String fileWithPath, Boolean enableDebug) throws Exception {
        // turn on verbose messages if passed as an argument to constructor
        debug = enableDebug;
        
        scan = new Scanner();
        scan.openFile(fileWithPath);
        symbolTableHandle = new SymbolTableMaster();
        // share the symbol table with the static analyzer
        analyze = new SemanticAnalyzer(fileWithPath, symbolTableHandle);
        
        lookahead = scan.getToken();
        logFileHandle = new PrintWriter(fileWithPath + ".infolog.txt");
        ruleFileHandle = new PrintWriter(fileWithPath + ".rulelog.txt");

        
    }

    //method to write the token names to file data/LogFile.txt
    public void infoLog(String msg){
        logFileHandle.format("%s\n", msg);
        if(debug){
            System.out.println(msg);
        }
    }

    //method to write the rule applied to file data/RulesApplied.txt
    public void listRule(int rulenumber){
        ruleFileHandle.format("%d\n", rulenumber);
        if(debug){
            System.out.println(rulenumber);
        }
    }
    
    public void ruleLog(String rulemsg){
        ruleFileHandle.format("%s\n", rulemsg);
        // They always seem to be dups of things that are already echoed by infoLog
        //System.out.println(rulemsg);    
    }

    public String genStdInfoMsg(){
        String logMessage = String.format("Non-Terminal: %s --- Lookahead: %s ",Thread.currentThread().getStackTrace()[2].getMethodName(), lookahead.getLexeme() );
        return(logMessage);
    }
    
    public void cleanup(){
        analyze.cleanup();
        logFileHandle.close();
        ruleFileHandle.close();
    }

    public String match(TokenType compareTok) {
    	String processedLexeme = "";
        if (lookahead.token_name == compareTok) {
            // put the token on the parse tree and get a new one
            infoLog("putting token: " + lookahead.token_name + ", lexeme: " + lookahead.getLexeme() + " on parse tree");
            processedLexeme = lookahead.getLexeme();
            // early return if we've parsed everything successfully
            if(lookahead.token_name == TokenType.MP_EOF){
                
                //Write the message to LogFile.txt and RulesAppled.txt
                String successMessage = "The input program parses!";
                infoLog(successMessage);
                ruleLog(successMessage);

                return(processedLexeme);
            }
            else{
                lookahead = scan.getToken();
            }
        } else {
            // parsing error
            // XXX fixme - this block needs to be moved out of match and into a dedicated error routine
            System.out.println("Parse Error at line: " + lookahead.getLineNumber() + ", column: " + lookahead.getColumnNumber());
            System.out.println("Expected: " + compareTok + ", but got: " + lookahead.token_name);
            // Thread.currentThread().getStackTrace()[1].getMethodName()
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-6);
        }
        return(processedLexeme);
    }

    // ### LUKES BLOCK STARTS HERE ### //
    public void SystemGoal() {
        // 1:SystemGoal      ⟶ Program eof    
        infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());
        switch(lookahead.token_name){
        	case MP_PROGRAM:
		        listRule(1); // List the rule number applied
        		Program();
        		match(TokenType.MP_EOF);
        		// XXX this might always be the same as dumpTop() in this context
                symbolTableHandle.dumpAll();
                analyze.terminateIR();
        		break;
	        default:
		        // parsing error
                    System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
                    System.out.println("Expected keyword 'PROGRAM' but found "+ lookahead.token_name);
		    analyze.cleanupAndDeleteIRFile();
                    System.exit(-5);
        }

    }

    public void Program() {
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 2: Program         ⟶ ProgramHeading ";" Block "."
        switch(lookahead.token_name){
        	case MP_PROGRAM:
		        listRule(2); // List the rule number applied
                ProgramHeading();
        		match(TokenType.MP_SCOLON);
                Block();
        		match(TokenType.MP_PERIOD);
        		break;
	        default:
	        // parsing error
	        	System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.out.println("Expected keyword 'PROGRAM' but found "+ lookahead.token_name);
			analyze.cleanupAndDeleteIRFile();
	        	System.exit(-5);
        }
        
        analyze.tearDownMainVariablesIR();
        
    }

    public void ProgramHeading() {
        // 3:ProgramHeading  ⟶ "program" ProgramIdentifier
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());

    	String programName;
    	
        switch(lookahead.token_name){
	        case MP_PROGRAM:
		        listRule(3); // List the rule number applied
	        	//We are looking for the program ID so we can expand
	        	match(TokenType.MP_PROGRAM);
	        	programName = ProgramIdentifier();
	        	
	            symbolTableHandle.newSymbolTableForNewContext(programName);
	            analyze.assignDxForRelativeAddressing();
	            
	        	break;
	        default:
		        // parsing error
		        System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.out.println("Expected keyword 'PROGRAM' but found "+ lookahead.token_name);
			analyze.cleanupAndDeleteIRFile();
		        System.exit(-5);
        }
    }

    public void Block() {
        // 4:Block           ⟶ VariableDeclarationPart ProcedureAndFunctionDeclarationPart StatementPart
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());
    	
    	String branchAroundDefsLabel;
    	
        switch(lookahead.token_name){
	        case MP_VAR:
			case MP_BEGIN:
			case MP_PROCEDURE:
			case MP_FUNCTION:
		        listRule(4); // List the rule number applied
	        	VariableDeclarationPart();

	        	branchAroundDefsLabel = analyze.genBranchAroundDefsIR();
	        	
	        	ProcedureAndFunctionDeclarationPart();
	        	
	        	analyze.dropLabelIR(branchAroundDefsLabel);
	        	
	        	StatementPart();
	        	break;
	        default:
		        // parsing error
		        System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
				System.out.println("Expected VAR, PROCEDURE, FUNCTION or BEGIN but found "+ lookahead.token_name);
			analyze.cleanupAndDeleteIRFile();
		        System.exit(-5);
        }
    }

    public void VariableDeclarationPart() {
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 5:VariableDeclarationPart  ⟶ "var" VariableDeclaration ";" VariableDeclarationTail
        switch(lookahead.token_name){
	        case MP_VAR:
		        listRule(5); // List the rule number applied
	        	match(TokenType.MP_VAR);
	            VariableDeclaration();
	        	match(TokenType.MP_SCOLON);
	        	VariableDeclarationTail();
	        	
	            // XXX set up activation record here
                analyze.genAllocateMemForLocalVarsIR();
	        	
	        	break;
			case MP_BEGIN:
			case MP_PROCEDURE:
			case MP_FUNCTION:
				listRule(107);
				break;
	        default:
	        	// Might need some follow here since Tail could go to nothing
		        // parsing error
		        System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
				System.out.println("Expected keyword 'VAR' but found "+ lookahead.token_name);
			analyze.cleanupAndDeleteIRFile();
		        System.exit(-5);
        }
    }

    public void VariableDeclarationTail() {
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());
    	//6:VariableDeclarationTail  ⟶ VariableDeclaration ";" VariableDeclarationTail 
    	//7:                         ⟶ ε
        switch(lookahead.token_name){
	        //Lookahead should be the ID 
	        case MP_IDENTIFIER:
		        listRule(6); // List the rule number applied
	            VariableDeclaration();
	            match(TokenType.MP_SCOLON);
	            VariableDeclarationTail();
	        	break;
	        // mapping to sigma
	        case MP_BEGIN:
	        case MP_FUNCTION:
	        case MP_PROCEDURE:
		        listRule(7); // List the rule number applied
	            break;
	        default:
		        // parsing error
		        System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
				System.out.println("Expected variable name OR procedure declaration or function declaration or keyword 'BEGIN' but found "+ lookahead.token_name);
			analyze.cleanupAndDeleteIRFile();
		        System.exit(-5);
        }
    }

    public void VariableDeclaration() {
    	// 8:VariableDeclaration      ⟶ Identifierlist ":" Type 
    	ArrayList<String> lexemes = new ArrayList<String>();
    	SymbolType varType = null;
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());
        switch(lookahead.token_name){
	        //We should be looking at IDs coming up
	        case MP_IDENTIFIER:
		        listRule(8); // List the rule number applied
	        	lexemes = IdentifierList();
	            match(TokenType.MP_COLON);
	        	varType = Type();
	        	break;
	        default: 
		        // parsing error
		        System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			    System.out.println("Expected variable name but found "+ lookahead.token_name);
			analyze.cleanupAndDeleteIRFile();
		        System.exit(-5);
        }
        for(String lexeme: lexemes){
        	Var newVar = new Var(lexeme, varType);
        	symbolTableHandle.insert(newVar);
        }
    }

    public SymbolType Type() {
    	SymbolType symbolType = null;
    	// 9. 	-> "Integer"
    	// 10. 	-> "Float"
    	// 11.	-> "Boolean"
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());
        switch(lookahead.token_name){
	        case MP_INTEGER:
		        listRule(9); // List the rule number applied
	        	match(TokenType.MP_INTEGER);
	        	symbolType = SymbolType.MP_SYMBOL_INTEGER;
	        	break;
	        case MP_FLOAT:
		        listRule(10); // List the rule number applied
	        	match(TokenType.MP_FLOAT);
	        	symbolType = SymbolType.MP_SYMBOL_FLOAT;
	        	break;
	        case MP_BOOLEAN:
	        	listRule(11); // List the rule number applied
	        	match(TokenType.MP_BOOLEAN);
	        	symbolType = SymbolType.MP_SYMBOL_BOOLEAN;
	        	break;
	        case MP_STRING:
	        	listRule(109); // List the rule number applied
	        	match(TokenType.MP_STRING);
	        	symbolType = SymbolType.MP_SYMBOL_STRING;
	        	break;
	        default:
		        // parsing error
		        System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
				System.out.println("Expected data type of variable, but found " + lookahead.token_name);
			analyze.cleanupAndDeleteIRFile();
		        System.exit(-5);
		        
        }
        return(symbolType);
    }

    public void ProcedureAndFunctionDeclarationPart() {
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());
    	//12:ProcedureAndFunctionDeclarationPart ⟶ ProcedureDeclaration ProcedureAndFunctionDeclarationPart
    	//13:                                    ⟶ FunctionDeclaration ProcedureAndFunctionDeclarationPart
    	//14:                                    ⟶ ε
        switch(lookahead.token_name){
	        case MP_PROCEDURE:
		        listRule(12); // List the rule number applied
	        	ProcedureDeclaration();
	        	ProcedureAndFunctionDeclarationPart();
	        	break;
	        case MP_FUNCTION:
		        listRule(13); // List the rule number applied
	        	FunctionDeclaration();
	        	ProcedureAndFunctionDeclarationPart();
	        	break;
	        case MP_BEGIN:
	            // go to ε
		        listRule(14); // List the rule number applied
			break;
	        
	        default:
	        	// parsing error
	        	System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
				System.out.println("Expected procedure declaration or function declaration or keyword 'BEGIN' but found "+ lookahead.token_name);
			analyze.cleanupAndDeleteIRFile();
	        	System.exit(-5);
        }
    }

    public void ProcedureDeclaration() {
        // 15. ProcedureDeclaration  ->  ProcedureHeading ";" Block ";"
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());

    	String procedureName;
    	
        switch(lookahead.token_name){
	        case MP_PROCEDURE:
		        listRule(15); // List the rule number applied
	        	procedureName = ProcedureHeading();
	        	
	        	match(TokenType.MP_SCOLON);
	        	Block();
	        	match(TokenType.MP_SCOLON);
	              // XXX should we do this just in debug mode?
                symbolTableHandle.dumpTop();
                
                analyze.genProcDefPostambleIR(procedureName);
                symbolTableHandle.ascendContextDestroyTable();
                

                
	        	break;
	        default:
	        	// parsing error
	        	System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			    System.out.println("Expected procedure declaration but found "+ lookahead.token_name);
			analyze.cleanupAndDeleteIRFile();
	        	System.exit(-5);
        }
    }

    public void FunctionDeclaration() {
        // 16. <FunctionDeclaration> -> <FunctionHeading> ";" <Block> ";"
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());

    	String functionName;
    	
        switch(lookahead.token_name){
	        case MP_FUNCTION:
		        listRule(16); // List the rule number applied
	        	functionName = FunctionHeading();
	        	match(TokenType.MP_SCOLON);
	        	Block();
	        	match(TokenType.MP_SCOLON);
	        	// XXX should we do this just in debug mode?
	        	symbolTableHandle.dumpTop();
	        	analyze.genProcDefPostambleIR(functionName);
	        	symbolTableHandle.ascendContextDestroyTable();
	        	
	        	break;
	        default:
		        // parsing error
		        System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
                System.out.println("Expected function declaration but found "+ lookahead.token_name);
			analyze.cleanupAndDeleteIRFile();
		        System.exit(-5);
        }
    }

    public String ProcedureHeading() {
        // 17. <ProcedureHeading> -> "procedure" <ProcedureIdentifer> <OptionalFormalParameterList>
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());

    	String procedureName = "";
        ArrayList<Args> argList = new ArrayList<Args>();
        Procedure procSym;
        String newLabel = "";
        int position;
    	
        switch(lookahead.token_name){
            case MP_PROCEDURE:
                listRule(17); // List the rule number applied
                    match(TokenType.MP_PROCEDURE);
                    procedureName = ProcedureIdentifier();
                    argList = OptionalFormalParameterList();        	
                    break;
	        default:
		        // parsing error
		        System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
                        System.out.println("Expected procedure declaration but found "+ lookahead.token_name);				
			analyze.cleanupAndDeleteIRFile();
		        System.exit(-5);
        }
        newLabel = analyze.genUniqueLabel();
        procSym = new Procedure(procedureName, argList, newLabel);
        symbolTableHandle.insert(procSym);

        symbolTableHandle.newSymbolTableForNewContext(procedureName);
        
        position = 0;
        for(Args argument : argList){
            argument.setPosition(position);
            symbolTableHandle.insert(argument);
            position++;
        }
        
        analyze.dropLabelIR(newLabel);
        analyze.genProcDefPreambleIR(procedureName);
        
        return(procedureName);

    }

    public String FunctionHeading() {
    	// 18. FunctionHeading                     ⟶ "function" functionIdentifier OptionalFormalParameterList ":" Type
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());

    	String functionName = "";
        ArrayList<Args> argList = new ArrayList<Args>();
        SymbolType funcRetType = null;
        Function funcSym;
        String newLabel;
        int position;

        switch(lookahead.token_name){
	        case MP_FUNCTION:
		        listRule(18); // List the rule number applied
	        	match(TokenType.MP_FUNCTION);
	        	functionName = FunctionIdentifier();
	        	argList = OptionalFormalParameterList();
	        	match(TokenType.MP_COLON);
	                funcRetType = Type();
	        	break;
	        default:
		        // parsing error
		        System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			    System.out.println("Expected function declaration but found "+ lookahead.token_name);
			analyze.cleanupAndDeleteIRFile();
		        System.exit(-5);
        }
        newLabel = analyze.genUniqueLabel();
        funcSym = new Function(functionName, funcRetType, argList, newLabel);
        symbolTableHandle.insert(funcSym);

        symbolTableHandle.newSymbolTableForNewContext(functionName);

        position = 0;
        for(Args argument : argList){
            argument.setPosition(position);
            symbolTableHandle.insert(argument);
            position++;
        }
        
        analyze.dropLabelIR(newLabel);
        analyze.genFuncDefPreambleIR(functionName);
        
        return(functionName);
        
    }
    
    public ArrayList<Args> OptionalFormalParameterList() {
        // 19. <OptionalFormalParameterList> -> "(" <FormalParameterSection> <FormalParameterSectionTail> ")"
        // 20. <OptionalFormalParameterList> -> Sigma
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());

        ArrayList<Args> argList = new ArrayList<Args>();
        ArrayList<Args> moreArgs = new ArrayList<Args>();
    	
        switch (lookahead.token_name) {
	        case MP_LPAREN:
		        listRule(19); // List the rule number applied
	        	match(TokenType.MP_LPAREN);
	        	argList = FormalParameterSection();
	        	moreArgs = FormalParameterSectionTail();
	        	match(TokenType.MP_RPAREN);
	        	break;
	        case MP_SCOLON:
	        case MP_COLON:
	        	// Mapping to sigma
		        listRule(20); // List the rule number applied
	        	break;
	        default:
	            // parsing error
	            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
				System.out.println("Expected parameter list or ':' [for functions] or ';' [for procedures] but found "+ lookahead.token_name);				
		    analyze.cleanupAndDeleteIRFile();
	            System.exit(-5);
	        }
        argList.addAll(moreArgs);
        return(argList);
        
    }
    
    public ArrayList<Args> FormalParameterSectionTail() {
        // 21. <FormalParameterSectionTail> -> ";" <FormalParameterSection> <FormalParameterSectionTail>
        // 22. <FormalParameterSectionTail> -> Sigma
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());
    	
        ArrayList<Args> argList = new ArrayList<Args>();
        ArrayList<Args> moreArgs = new ArrayList<Args>();

        switch (lookahead.token_name) {
	        case MP_SCOLON:
		        listRule(21); // List the rule number applied
	        	match(TokenType.MP_SCOLON);
	        	argList = FormalParameterSection();
	        	moreArgs = FormalParameterSectionTail();
	        	break;
	        case MP_RPAREN:
	        	// Mapping to sigma
		        listRule(22); // List the rule number applied
	        	break;
	        default:
	            // parsing error
	            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
				System.out.println("Expected ';' or ')' but found "+ lookahead.token_name);
		    analyze.cleanupAndDeleteIRFile();
	            System.exit(-5);
	        }
        argList.addAll(moreArgs);
        return(argList);
    }

    public ArrayList<Args> FormalParameterSection() {
        // 23. <FormalParameterSection> -> <ValueParameterSection>
        // 24. <FormalParameterSection> -> <VariableParameterSection>
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());
    	
        ArrayList<Args> argList = null;

        switch (lookahead.token_name) {
	        case MP_IDENTIFIER:
		        listRule(23); // List the rule number applied
	        	argList = ValueParameterSection();
	        	break;
	        case MP_VAR:
		        listRule(24); // List the rule number applied
		        argList = VariableParameterSection();
	        	break;
	        default:
	            // parsing error
	            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
				System.out.println("Expected variable name or kyword 'VAR' but found "+ lookahead.token_name);
		    analyze.cleanupAndDeleteIRFile();
	            System.exit(-5);
	        }
        
        return(argList);
    }
    
    public ArrayList<Args> ValueParameterSection() {
        // 25. <ValueParameterSection> -> <IdentifierList> ":" <Type>
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());

        ArrayList<String> lexemes = new ArrayList<String>();
        ArrayList<Args> argList = new ArrayList<Args>();
        SymbolType symbolType = null;
        Args singleArg = null;
        
        switch (lookahead.token_name) {
	        case MP_IDENTIFIER:
		        listRule(25); // List the rule number applied
	        	lexemes = IdentifierList();
	        	match(TokenType.MP_COLON);
	        	symbolType = Type();
	        	break;
	        default:
	            // parsing error
	            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
				System.out.println("Expected variable name but found "+ lookahead.token_name);
		    analyze.cleanupAndDeleteIRFile();
	            System.exit(-5);
	        }
        
        // prepare semantic records for return
        for(String lexeme : lexemes ){
            singleArg = new Args(lexeme, symbolType, SymbolMode.MP_SYMBOL_VALUE);
            argList.add(singleArg);
        }
        return(argList);
        
    }

    public ArrayList<Args> VariableParameterSection() {
        // 26. <VariableParameterSection> -> "var" <IdentifierList> ":" <Type>
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());

        ArrayList<String> lexemes = new ArrayList<String>();
        ArrayList<Args> argList = new ArrayList<Args>();
        SymbolType symbolType = null;
        Args singleArg = null;
    	
        switch (lookahead.token_name) {
	        case MP_VAR:
		        listRule(26); // List the rule number applied
	        	match(TokenType.MP_VAR);
	        	lexemes = IdentifierList();
	        	match(TokenType.MP_COLON);
	        	symbolType = Type();
	        	break;
	        default:
	            // parsing error
	            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
				System.out.println("Expected keyword 'VAR' but found "+ lookahead.token_name);				
		    analyze.cleanupAndDeleteIRFile();
	            System.exit(-5);
	        }
        // prepare semantic records for return
        for(String lexeme : lexemes ){
            singleArg = new Args(lexeme, symbolType, SymbolMode.MP_SYMBOL_REFERENCE);
            argList.add(singleArg);
        }
        return(argList);
    }

    public void StatementPart() {
    	//System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 27:StatementPart      ⟶ CompoundStatement
        switch (lookahead.token_name) {
	        case MP_BEGIN:
		        listRule(27); // List the rule number applied
	        	CompoundStatement();
	        	break;
	        default:
	            // parsing error
	            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
				System.out.println("Expected keyword 'BEGIN' but found "+ lookahead.token_name);
		    analyze.cleanupAndDeleteIRFile();
	            System.exit(-5);
	        }
    }

    public void CompoundStatement() {
    	// 28:CompoundStatement  ⟶ "begin" StatementSequence "end"
    	infoLog( genStdInfoMsg() );

        switch (lookahead.token_name) {
	        case MP_BEGIN:
		        listRule(28); // List the rule number applied
	        	match(TokenType.MP_BEGIN);

       	
	            StatementSequence();
	        	match(TokenType.MP_END);
	        	break;
	        default:
	            // parsing error
	            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
				System.out.println("Expected keyword 'BEGIN' but found "+ lookahead.token_name);	            	
				analyze.cleanupAndDeleteIRFile();
				System.exit(-5);
	        }
    }

    public void StatementSequence() {
    	// 29:StatementSequence  ⟶ Statement StatementTail
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());

        switch (lookahead.token_name) {
        case MP_BEGIN:
        case MP_END:
        case MP_FOR:
        case MP_IF:
        case MP_ELSE:
        case MP_READ:
        case MP_WRITE:
        case MP_WRITELN:
        case MP_REPEAT:
        case MP_WHILE:
        case MP_IDENTIFIER:
        case MP_SCOLON:
        case MP_UNTIL:
	        listRule(29); // List the rule number applied
        	Statement();
        	StatementTail();
        	break;
        default:
            // parsing error    
            System.out.println("Parsing error in: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            System.out.println("Expected start of statement but found "+ lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
    }

    // ### LUKES BLOCK ENDS HERE ### //

    // ### LARRYS BLOCK STARTS HERE ### //
    public void StatementTail() {
    	infoLog( genStdInfoMsg() );

        switch (lookahead.token_name) {
        case MP_SCOLON:
            // 30: StatementTail ⟶ ";" Statement StatementTail
            // 31: StatementTail ⟶ ε
                listRule(30); // List the rule number applied
            match(TokenType.MP_SCOLON);
            Statement();
            StatementTail();
            break;
        case MP_END:
        case MP_UNTIL:
            // go to ε
                listRule(31); // List the rule number applied
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println("Expected ';' or keyword 'END' or keyword 'UNTIL' [for REPEAT loop] but found "+ lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);

        }
    }

    public void Statement() {
    	infoLog( Thread.currentThread().getStackTrace()[1].getMethodName() );

        SymbolKind idKind = null;
        String lex = "";

        switch (lookahead.token_name) {
        case MP_END:
        case MP_UNTIL:
        case MP_SCOLON:
        case MP_ELSE:
        //XXX Note: ELSE is predicted by LL1 table, may be in error
        //case MP_ELSE:
        //32:Statement           ⟶ EmptyStatement
            listRule(32); // List the rule number applied
            EmptyStatement();
            break;
        case MP_BEGIN:
            // 33:Statement ⟶ CompoundStatement
            listRule(33); // List the rule number applied
            CompoundStatement();
            break;
        case MP_FOR:
            // 40:Statement ⟶ ForStatement
                listRule(40); // List the rule number applied
            ForStatement();
            break;
        case MP_IF:
            // 37:Statement ⟶ IfStatement
                listRule(37); // List the rule number applied
            IfStatement();
            break;
        case MP_READ:
            // 34:Statement ⟶ ReadStatement
                listRule(34); // List the rule number applied
            ReadStatement();
            break;
        case MP_REPEAT:
            // 39:Statement ⟶ RepeatStatement
                listRule(39); // List the rule number applied
            RepeatStatement();
            break;
        case MP_WHILE:
            // 38:Statement ⟶ WhileStatement
                listRule(39); // List the rule number applied
            WhileStatement();
            break;
        case MP_WRITE:
	    case MP_WRITELN:
            // 35:Statement ⟶ WriteStatement
            listRule(35); // List the rule number applied
            WriteStatement();
            break;
        case MP_IDENTIFIER:
            // use symbol table to dis-ambiguate different kinds of identifiers
            lex = lookahead.getLexeme();
            idKind = symbolTableHandle.getKindByLexeme( lex );

            if(idKind == null){
                System.out.println("Attempted to look up an undeclared variable: "+ lex);
                symbolTableHandle.dumpAll();
		analyze.cleanupAndDeleteIRFile();
                System.exit(-8);
            }

            switch(idKind){
            case MP_SYMBOL_VAR:
            case MP_SYMBOL_FUNCTION:
            case MP_SYMBOL_PARAMETER:
                // 36:Statement ⟶ AssignmentStatement
                listRule(36); // List the rule number applied
                AssignmentStatement();      
                break;            
            case MP_SYMBOL_PROCEDURE:
                // 41:Statement ⟶ ProcedureStatement
                listRule(41);
                ProcedureStatement();   
                break;
            default:
            // parsing error
                System.out.println("Parsing error in: " + Thread.currentThread().getStackTrace()[1].getMethodName());
                System.out.println("Found Identifier token: " + lookahead.getLexeme()
                    + ", of kind: " + idKind + ", looking for variable, function, parameter or procedure");
		analyze.cleanupAndDeleteIRFile();
                System.exit(-7);         
            }

            break;
        default:
            // parsing error
            System.out.println("Parsing error in: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            System.out.println("Expected start of statement but found "+ lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
    } // end statement

    public void EmptyStatement() {
        // 42:EmptyStatement ⟶ ε
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());

        switch (lookahead.token_name) {
        case MP_END:
        case MP_UNTIL:
        case MP_SCOLON:
        // XXX MP_ELSE predicted by LL1 table, may be in error
        //case MP_ELSE:
                listRule(42); // List the rule number applied
            break;
        default:
            // parsing error
            System.out.println("Parsing error in: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            System.out.println("Expected ';' or keyword 'END' or keyword 'UNTIL' [for REPEAT loop] but found "+ lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
    }

    public void ReadStatement() {
        //System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());
        // 43:ReadStatement ⟶ "read" "(" ReadParameter ReadParameterTail ")"
        switch (lookahead.token_name) {
        case MP_READ:
                listRule(43); // List the rule number applied
            match(TokenType.MP_READ);
            match(TokenType.MP_LPAREN);
            ReadParameter();
            ReadParameterTail();
            match(TokenType.MP_RPAREN);
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.out.println("Expected keyword 'READ' but found "+ lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
    }

    public void ReadParameterTail() {
        //System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());
        // 44:ReadParameterTail ⟶ "," ReadParameter ReadParameterTail
        // 45: ⟶ ε
        switch (lookahead.token_name) {
        case MP_COMMA:
                listRule(44); // List the rule number applied
            match(TokenType.MP_COMMA);
            ReadParameter();
            ReadParameterTail();
            break;
        case MP_RPAREN:
        	// map to ε
                listRule(45); // List the rule number applied
	    break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.out.println("Expected ',' or ')' but found "+ lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
    }

    public void ReadParameter() {
        //System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());
        // 46:ReadParameter ⟶ VariableIdentifier
        switch (lookahead.token_name) {
        case MP_IDENTIFIER:
                listRule(46); // List the rule number applied
            VariableIdentifier();
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.out.println("Expected an identifier but found " + lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
    }

    public void WriteStatement() {    
        // 47:WriteStatement ⟶ "write" "(" WriteParameter WriteParameterTail ")"
    	infoLog(genStdInfoMsg());

        switch (lookahead.token_name) {
        case MP_WRITE:
            listRule(47); // List the rule number applied
            match(TokenType.MP_WRITE);
            match(TokenType.MP_LPAREN);
            WriteParameter();
            WriteParameterTail();
            match(TokenType.MP_RPAREN);
            analyze.genWriteIR();
            break;
		case MP_WRITELN:
			listRule(111); // List the rule number applied
            match(TokenType.MP_WRITELN);
            match(TokenType.MP_LPAREN);
            WriteParameter();
            WriteParameterTail();
            match(TokenType.MP_RPAREN);
            analyze.genWriteLineIR();
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.out.println("Expected keyword 'WRITE' but found "+ lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
    }

    public void WriteParameterTail() {
        //System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());
        // 48:WriteParameterTail ⟶ "," WriteParameter
        // 49: ⟶ ε
        switch (lookahead.token_name) {
        case MP_COMMA:
                listRule(48); // List the rule number applied
            match(TokenType.MP_COMMA);
            WriteParameter();
            break;
        case MP_RPAREN:
            // map to ε
                listRule(49); // List the rule number applied
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.out.println("Expected ',' or ')' but found "+ lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
    }

    public void WriteParameter() {
    	infoLog( genStdInfoMsg() );
        // 50:WriteParameter ⟶ OrdinalExpression
        switch (lookahead.token_name) {
        case MP_PLUS:
        case MP_MINUS:
        case MP_LPAREN:
        case MP_NOT:
        case MP_IDENTIFIER:
        case MP_INTEGER_LIT:
	case MP_FIXED_LIT:
	case MP_FLOAT_LIT:
	case MP_STRING_LIT:
	case MP_TRUE:
	case MP_FALSE:
            listRule(50); // List the rule number applied
            OrdinalExpression();
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.out.println("Expected start of expression but found "+ lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
    }

    public void AssignmentStatement() {
        // 51:AssignmentStatement ⟶ VariableIdentifier ":=" Expression
        // 52:                    ⟶ FunctionIdentifier ":=" Expression
    	infoLog( genStdInfoMsg() );

        SymbolKind idKind = null;
        String lookaheadLex = "";
        String varLex = "";
        StackTopRecord rec = null;
        StackTopRecord noValOnStack = null;

        switch (lookahead.token_name) {
        case MP_IDENTIFIER:
            Boolean declared = symbolTableHandle.varHasBeenDeclared(lookahead.getLexeme());
            if(!declared){
                // XXX : move this into a better error handler
                System.out.println("Symbol error at line: " + lookahead.getLineNumber() + ", column: " + lookahead.getColumnNumber());
                System.out.println("Attempted to assign a value to an undeclared variable: "+ lookahead.getLexeme() );
		analyze.cleanupAndDeleteIRFile();
                System.exit(-6);
            }
            // XXX: need more logic here for checking that the types match

            // use symbol table to resolve ambiguity in language
            lookaheadLex = lookahead.getLexeme();
            idKind = symbolTableHandle.getKindByLexeme( lookaheadLex );

            switch(idKind){
            case MP_SYMBOL_VAR:
                listRule(51); // List the rule number applied
                varLex = VariableIdentifier();
                match(TokenType.MP_ASSIGN);
                rec = Expression(noValOnStack);

                analyze.genAssignmentIR(varLex, rec.dataType);
                break;
            case MP_SYMBOL_PARAMETER:
                listRule(51); // List the rule number applied
                varLex = VariableIdentifier();
                match(TokenType.MP_ASSIGN);
                rec = Expression(noValOnStack);
                Args param = (Args) symbolTableHandle.fetchSymbolByLexeme(varLex);
                if(param.getMode() == SymbolMode.MP_SYMBOL_VALUE){
                    analyze.genAssignmentIR(varLex, rec.dataType);
                }
                else if(param.getMode() == SymbolMode.MP_SYMBOL_REFERENCE){
                    analyze.genAssignmentWithIndirectionIR(varLex, rec.dataType);
                }

                break;
            case MP_SYMBOL_FUNCTION:
                listRule(52); // List the rule number applied
                FunctionIdentifier(); 
                match(TokenType.MP_ASSIGN); 
                rec = Expression(noValOnStack); 
                analyze.stashFunctionRetValIR();
                
                break;

            default:
            // parsing error
                System.out.println("Parsing error in: " + Thread.currentThread().getStackTrace()[1].getMethodName());
                System.out.println("Found Identifier token: " + lookahead.getLexeme()
                    + ", of kind: " + idKind + ", looking for variable, parameter or function");
		analyze.cleanupAndDeleteIRFile();
                System.exit(-7);         
            }
            
            break;

        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.out.println("Expected an identifier but found "+ lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
    }

    public void IfStatement() {
        // 53:IfStatement ⟶ "if" BooleanExpression "then" Statement OptionalElsePart
    	infoLog( genStdInfoMsg() );

		String elseLabel = null;
		String afterElseLabel = null;
		
        switch (lookahead.token_name) {
        case MP_IF:
            listRule(53); // List the rule number applied
            match(TokenType.MP_IF);
            BooleanExpression();
            match(TokenType.MP_THEN);
			elseLabel = analyze.genIfIR();	
            Statement();
			afterElseLabel = analyze.genLabelAroundElse();
			analyze.branchAroundElse(afterElseLabel);
			analyze.putElseLabel(elseLabel);
            OptionalElsePart(afterElseLabel);
			analyze.putAfterElseLabel(afterElseLabel);

            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.out.println("Expected keyword 'IF' but found "+ lookahead.token_name);			
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
    }

    public void OptionalElsePart(String afterElseLabel) {
        // 54:OptionalElsePart ⟶ "else" Statement
        // 55: ⟶ ε
    	infoLog( genStdInfoMsg() );

        switch (lookahead.token_name) {
        case MP_ELSE:
            listRule(54); // List the rule number applied
            match(TokenType.MP_ELSE);
            Statement();
            break;
        case MP_END:
        case MP_UNTIL:
        case MP_SCOLON:
        // XXX note: MP_ELSE predicted by LL(1) table - AMBIGOUS - resolving by always matching nearest MP_IF
        //case MP_ELSE:
            // map to ε
            listRule(55); // List the rule number applied
			analyze.branchAroundElse(afterElseLabel);
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.out.println("Expected keyword 'ELSE' or end of IF part but found "+ lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-15);
        }
    }

    public void RepeatStatement() {
        // 56:RepeatStatement ⟶ "repeat" StatementSequence "until" BooleanExpression
    	infoLog( genStdInfoMsg() );

        String returnLabel;

        switch (lookahead.token_name) {
        case MP_REPEAT:
            listRule(56); // List the rule number applied
            match(TokenType.MP_REPEAT);
            returnLabel = analyze.genUniqueLabel();
            analyze.dropLabelIR(returnLabel);

            StatementSequence();
            match(TokenType.MP_UNTIL);
            BooleanExpression();
            analyze.genUntilTerminationIR(returnLabel);

            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.out.println("Expected keyword 'REPEAT' but found "+ lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
    }

    public void WhileStatement() {
        // 57:WhileStatement ⟶ "while" BooleanExpression "do" Statement
    	infoLog( Thread.currentThread().getStackTrace()[1].getMethodName() );

        String beginWhileLoopLabel;
        String exitWhileLoopLabel;
        String  controlVarLex;
        StackTopRecord recOnStack;
        StackTopRecord terminatorType;
    	
        switch (lookahead.token_name) {
        case MP_WHILE:
            listRule(57); // List the rule number applied
            match(TokenType.MP_WHILE);
            
            beginWhileLoopLabel = analyze.genUniqueLabel();
            exitWhileLoopLabel = analyze.genUniqueLabel();
            analyze.dropLabelIR(beginWhileLoopLabel);
            
            BooleanExpression();
            
            match(TokenType.MP_DO);
            analyze.genWhileLoopPreambleIR(exitWhileLoopLabel);
            
            Statement();
            
            analyze.genWhileLoopPostambleIR(beginWhileLoopLabel);
            analyze.dropLabelIR(exitWhileLoopLabel);
            
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.out.println("Expected keyword 'WHILE' but found "+ lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
    }

    public void ForStatement() {
         // 58:ForStatement ⟶ "for" ControlVariable ":=" InitialValue StepValue FinalValue "do" Statement
    	infoLog( genStdInfoMsg() );
    	
    	String beginForLoopLabel;
    	String exitForLoopLabel;
    	Boolean positive = null;
    	String  controlVarLex;
    	StackTopRecord recOnStack;
    	StackTopRecord terminatorType;
        
        switch (lookahead.token_name) {
        case MP_FOR:
            listRule(58); // List the rule number applied
            match(TokenType.MP_FOR);
            
            beginForLoopLabel = analyze.genUniqueLabel();
            exitForLoopLabel = analyze.genUniqueLabel();
            
            controlVarLex = ControlVariable();
            match(TokenType.MP_ASSIGN);
            
            recOnStack = InitialValue();
            // doesn't make sense for this type to be anything but numeric. Add Semantic check
            analyze.genAssignmentIR(controlVarLex, recOnStack.dataType);
            
            positive = StepValue();
            analyze.dropLabelIR(beginForLoopLabel);
            terminatorType = FinalValue();
            // again we should do a semantic check & make sure this is numeric
	    // Also need to add the terminator value to the Preamble?
	    // See test...
            analyze.genForLoopPreambleIR(controlVarLex, exitForLoopLabel);
            match(TokenType.MP_DO);
            Statement();

            analyze.genForLoopPostambleIR(controlVarLex, positive, beginForLoopLabel);
            analyze.dropLabelIR(exitForLoopLabel);
            
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.out.println("Expected keyword 'FOR' but found "+ lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
    }

    public String ControlVariable() {
        // 59:ControlVariable ⟶ VariableIdentifier
    	infoLog( genStdInfoMsg() );
    	String controlVarLex = "";

        switch (lookahead.token_name) {
        case MP_IDENTIFIER:
            listRule(59); // List the rule number applied
            controlVarLex = VariableIdentifier();
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.out.println("Expected an identifier but found "+ lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
        return(controlVarLex);
    }

    public StackTopRecord InitialValue() {
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());
        // 60:InitialValue ⟶ OrdinalExpression
    	
    	StackTopRecord recOnStack = null;
    	
        switch (lookahead.token_name) {
        case MP_PLUS:
        case MP_MINUS:
        case MP_LPAREN:
        case MP_NOT:
        case MP_IDENTIFIER:
        case MP_INTEGER_LIT:
	    case MP_FIXED_LIT:
	    case MP_FLOAT_LIT:
        case MP_STRING_LIT:
            listRule(60); // List the rule number applied
            recOnStack = OrdinalExpression();
            
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.out.println("Expected start of expression but found "+ lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
        return(recOnStack);
    }

    public Boolean StepValue() {
        // 61:StepValue ⟶ "to"
        // 62: ⟶ "downto"
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());

    	Boolean positive = null;
    	
        switch (lookahead.token_name) {
        case MP_TO:
            listRule(61); // List the rule number applied
            match(TokenType.MP_TO);
            positive = true;
            break;
        case MP_DOWNTO:
            listRule(62); // List the rule number applied
            match(TokenType.MP_DOWNTO);
            positive = false;
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.out.println("Expected keyword 'TO' or 'DOWNTO' but found "+ lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
    	return(positive);
    }

    public StackTopRecord FinalValue() {
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());
    	StackTopRecord recOnStack = null;
        // 63:FinalValue ⟶ OrdinalExpression
        switch (lookahead.token_name) {
        case MP_PLUS:
	case MP_MINUS:
        case MP_LPAREN:
        case MP_NOT:
        case MP_IDENTIFIER:
        case MP_INTEGER_LIT:
	case MP_FIXED_LIT:
	case MP_FLOAT_LIT:
        case MP_STRING_LIT:
            listRule(63); // List the rule number applied
            recOnStack = OrdinalExpression();
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.out.println("Expected start of expression but found "+ lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
        return(recOnStack);
    }

    public void ProcedureStatement() {
        // 64:ProcedureStatement ⟶ ProcedureIdentifier OptionalActualParameterList
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());

    	Procedure procHandle = null;
    	String procLex = "";
    	String procLabel = "";
    	ArrayList<StackTopRecord> paramRecs = new ArrayList<StackTopRecord>();
    	
        switch (lookahead.token_name) {
        case MP_IDENTIFIER:
            listRule(64); // List the rule number applied
            procLex = ProcedureIdentifier();
            paramRecs = OptionalActualParameterList();

            procHandle = (Procedure) symbolTableHandle.fetchSymbolByLexeme(procLex);
            procLabel = procHandle.getJumpLabel();

            analyze.checkModesPrepRefs(procLex, paramRecs);

            analyze.genProcCallIR(procLabel);
            analyze.postProcCallCleanupIR(procLex);
            
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.out.println("Expected an identifier but found "+ lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
    }

    public ArrayList<StackTopRecord> OptionalActualParameterList() {
        // 65:OptionalActualParameterList ⟶ "(" ActualParameter ActualParameterTail ")"
        // 66: ⟶ ε
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());

    	StackTopRecord recOnStack = null;
    	ArrayList<StackTopRecord> paramRecs = new ArrayList<StackTopRecord>();
    	ArrayList<StackTopRecord> moreParamRecs = new ArrayList<StackTopRecord>();

        switch (lookahead.token_name) {
        case MP_LPAREN:
            listRule(65); // List the rule number applied
            match(TokenType.MP_LPAREN);
            recOnStack = ActualParameter();
            paramRecs.add(recOnStack);

            moreParamRecs = ActualParameterTail();
            paramRecs.addAll(moreParamRecs);

            match(TokenType.MP_RPAREN);
            break;
        case MP_AND:
        case MP_DIV:
        case MP_END:
        case MP_MOD:
        case MP_UNTIL:
        case MP_SCOLON:
        case MP_TIMES:
        case MP_RPAREN:
        case MP_COMMA:
        case MP_THEN:
        case MP_ELSE:
        case MP_DO:
        case MP_EQUAL:
        case MP_LTHAN:
        case MP_GTHAN:
        case MP_LEQUAL:
        case MP_GEQUAL:
        case MP_NEQUAL:
        case MP_PLUS:
        case MP_MINUS:
        case MP_OR:      
            // map to ε
                listRule(66); // List the rule number applied
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.out.println("Expected '(' or end of statement or multiplying operator but found "+ lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
        return(paramRecs);
    }

    // ### LARRYS BLOCK ENDS HERE

    // ### MAHESHS BLOCK STARTS HERE
    public ArrayList<StackTopRecord> ActualParameterTail() {
	//67:ActualParameterTail ⟶ "," ActualParameter ActualParameterTail
	//68:                    ⟶ ε
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());

    	StackTopRecord recOnStack = null;
    	ArrayList<StackTopRecord> paramRecs = new ArrayList<StackTopRecord>();
    	ArrayList<StackTopRecord> moreParamRecs = new ArrayList<StackTopRecord>();

        switch (lookahead.token_name) {
        case MP_COMMA:
                listRule(67); // List the rule number applied
            match(TokenType.MP_COMMA);
            recOnStack = ActualParameter();
            paramRecs.add(recOnStack);

            moreParamRecs = ActualParameterTail();
            paramRecs.addAll(moreParamRecs);
            break;
		case MP_RPAREN:
                listRule(68); // List the rule number applied
	    break;
        default:
            // Need FOLLOW here
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.out.println("Expected ',' or ')' but found "+ lookahead.token_name);            
			analyze.cleanupAndDeleteIRFile();
			System.exit(-5);
        }
        return(paramRecs);
    }

    public StackTopRecord ActualParameter() {
	//69:ActualParameter     ⟶ OrdinalExpression
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());

    	StackTopRecord recOnStack = null;

        switch (lookahead.token_name) {
        case MP_PLUS:
        case MP_MINUS:
        case MP_LPAREN:
        case MP_NOT:
        case MP_IDENTIFIER:
        case MP_INTEGER_LIT:
            listRule(69); // List the rule number applied
            recOnStack = OrdinalExpression();
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.out.println("Expected '+' or '-' but found "+ lookahead.token_name);            
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
        return(recOnStack);
    }

    public StackTopRecord Expression(StackTopRecord recOnStack) {
        //70:Expression              ⟶ SimpleExpression OptionalRelationalPart
    	infoLog( Thread.currentThread().getStackTrace()[1].getMethodName() );
    	
    	StackTopRecord newRec = null;

        switch (lookahead.token_name) {
        case MP_PLUS:
        case MP_MINUS:
        case MP_LPAREN:
        case MP_NOT:
        case MP_STRING_LIT:
        case MP_IDENTIFIER:
        case MP_INTEGER_LIT:
        case MP_FLOAT_LIT:
        case MP_FIXED_LIT:
        case MP_TRUE:
        case MP_FALSE:
            listRule(70); // List the rule number applied
            newRec = SimpleExpression(recOnStack);
            if(newRec != null){recOnStack = newRec;}
            newRec = OptionalRelationalPart(recOnStack);
            if(newRec != null){recOnStack = newRec;}
            break;
        default:
            // parsing error
            System.out.println("Parse Error at line: " + lookahead.getLineNumber() + ", column: " + lookahead.getColumnNumber());
            System.out.println("Expected start of expression but found "+ lookahead.token_name);            			
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }

        return(recOnStack);
    }

    public StackTopRecord OptionalRelationalPart(StackTopRecord recOnStack) {
        //71:OptionalRelationalPart  ⟶ RelationalOperator SimpleExpression
        //72:                        ⟶ ε
    	infoLog( Thread.currentThread().getStackTrace()[1].getMethodName() );
    	
    	StackTopRecord newType = null;
	StackTopRecord lhsRec = recOnStack;
	StackTopRecord rhsRec = null;
	RelationalOpType opType = null;

        switch (lookahead.token_name) {
        case MP_EQUAL:
        case MP_GTHAN:
        case MP_GEQUAL:
        case MP_LTHAN:
        case MP_LEQUAL:
        case MP_NEQUAL:
            listRule(71); // List the rule number applied
            opType = RelationalOperator();
            rhsRec = SimpleExpression(recOnStack);
	        newType = analyze.errorCheckandCastCompareOp(lhsRec, opType, rhsRec);
	    if ( newType != null)
		{
		    recOnStack = newType;
		}
            break;
        case MP_END:
        case MP_UNTIL:
        case MP_SCOLON:
        case MP_COMMA:
        case MP_RPAREN:
        case MP_THEN:
        case MP_ELSE:
	case MP_DO:
        case MP_TO:
        case MP_DOWNTO:
            // map to ε
            listRule(72); // List the rule number applied
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println("Expected relational operator or end of statement but found "+ lookahead.token_name);            			
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
	return (newType);
    }

    public RelationalOpType RelationalOperator() {
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());
	//73:RelationalOperator      ⟶ "="
	//74:                        ⟶ "<"
	//75:                        ⟶ ">"
	//76:                        ⟶ "<="
	//77:                        ⟶ ">="
	//78:                        ⟶ "<>" 
	RelationalOpType opType = null;
        switch (lookahead.token_name) {
        case MP_EQUAL:
            listRule(73); // List the rule number applied
            match(TokenType.MP_EQUAL);
	    opType = RelationalOpType.MP_EQUAL;
            break;
        case MP_GTHAN:
            listRule(75); // List the rule number applied
            match(TokenType.MP_GTHAN);
	    opType = RelationalOpType.MP_GTHAN;
            break;
        case MP_GEQUAL:
            listRule(77); // List the rule number applied
            match(TokenType.MP_GEQUAL);
	    opType = RelationalOpType.MP_GEQUAL;
            break;
        case MP_LTHAN:
            listRule(74); // List the rule number applied
            match(TokenType.MP_LTHAN);
	    opType = RelationalOpType.MP_LTHAN;
            break;
        case MP_LEQUAL:
            listRule(76); // List the rule number applied
            match(TokenType.MP_LEQUAL);
	    opType = RelationalOpType.MP_LEQUAL;
            break;
        case MP_NEQUAL:
            listRule(78); // List the rule number applied
            match(TokenType.MP_NEQUAL);
	    opType = RelationalOpType.MP_NEQUAL;
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
	    System.out.println("Expected relational operator but found "+ lookahead.token_name);                        
	    analyze.cleanupAndDeleteIRFile();
	    System.exit(-5);
        }
	return (opType);
    }

    public StackTopRecord SimpleExpression(StackTopRecord recOnStack) {
        //79:SimpleExpression        ⟶ OptionalSign Term TermTail
    	infoLog( genStdInfoMsg() );
    	
    	StackTopRecord lhsRec = recOnStack;
        StackTopRecord rhsRec = null;
    	StackTopRecord newRec = null;

    	//SymbolType lhsType = recOnStack.dataType;
        //SymbolType rhsType = null;
        SymbolType typeOnStack = null;
    	SymbolType newType = null;
	TokenType signType = null;
	Boolean isNumber = false;

        switch (lookahead.token_name) {
        case MP_PLUS:
        case MP_MINUS:
        case MP_LPAREN:
        case MP_NOT:
        case MP_IDENTIFIER:
	case MP_STRING_LIT:
        case MP_INTEGER_LIT:
        case MP_FLOAT_LIT:
	case MP_FIXED_LIT:
	case MP_TRUE:
	case MP_FALSE:
            listRule(79); // List the rule number applied

            signType = OptionalSign();

            //lhsType = Term(typeOnStack);
            lhsRec = Term(recOnStack);
            if(lhsRec != null){recOnStack = lhsRec;}
            //if(lhsType != null){typeOnStack = lhsType;}
            if(recOnStack.dataType != null){
                typeOnStack = recOnStack.dataType;
            }
	    if(signType == TokenType.MP_MINUS)
		analyze.genNegativeIR(lhsRec.dataType);
            newRec = TermTail(recOnStack);
            if(newRec != null){recOnStack = newRec;}

            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
            System.out.println("Expected start of expression but found "+ lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }

        return(recOnStack);
    }

    public StackTopRecord TermTail(StackTopRecord recOnStack) {
	//80:TermTail                ⟶ AddingOperator Term TermTail
	//81:                        ⟶ ε
    	infoLog(genStdInfoMsg());

        StackTopRecord newType = null;
        StackTopRecord lhsRec = recOnStack;
        StackTopRecord rhsRec = null;
    	AddOpType addType = null;
    	
        switch (lookahead.token_name) {
        case MP_OR:
        case MP_PLUS:
        case MP_MINUS:
            listRule(80); // List the rule number applied
            addType = AddingOperator();
            rhsRec = Term(recOnStack);
            newType = analyze.errorCheckAndCastAddOp(lhsRec, addType, rhsRec);
            if(newType != null){recOnStack = newType;}
            newType = TermTail(recOnStack);
            if(newType != null){recOnStack = newType;}
            break;
        case MP_END:
        case MP_UNTIL:
        case MP_SCOLON:
        case MP_EQUAL:
        case MP_GTHAN:
        case MP_GEQUAL:
        case MP_LTHAN:
        case MP_LEQUAL:
        case MP_NEQUAL:
        case MP_COMMA:
        case MP_RPAREN:
        case MP_THEN:
        case MP_ELSE:
	case MP_DO:
	case MP_TO:
	case MP_DOWNTO:
            // map to ε
                listRule(81); // List the rule number applied
            break;

        default:
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.out.println("Expected adding operator or *** but found "+ lookahead.token_name);            
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
        return(recOnStack);
    }

    public TokenType OptionalSign() {
        //82:OptionalSign            ⟶ "+"
        //83:                        ⟶ "-"
        //84:                        ⟶ ε
	TokenType signType = null;
    	infoLog( genStdInfoMsg() );

        switch (lookahead.token_name) {
        case MP_PLUS:
            listRule(82); // List the rule number applied
	    signType = TokenType.MP_PLUS;
            match(TokenType.MP_PLUS);
            break;
        case MP_MINUS:
            listRule(83); // List the rule number applied
	    signType = TokenType.MP_MINUS;
            match(TokenType.MP_MINUS);
            break;
        case MP_LPAREN:
        case MP_NOT:
        case MP_IDENTIFIER:
	case MP_STRING_LIT:
        case MP_INTEGER_LIT:
        case MP_FLOAT_LIT:
	case MP_FIXED_LIT:
	case MP_TRUE:
	case MP_FALSE:
	    // map to ε
            listRule(84); // List the rule number applied
            break;        
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.out.println("Expected '+' or '-' or '(' or identifier or integer or keyword 'NOT' but found "+ lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
	return (signType);
	
    }

    public AddOpType AddingOperator() {
        //85:AddingOperator          ⟶ "+"
        //86:                        ⟶ "-"
        //87:                        ⟶ "or"   
    	infoLog( genStdInfoMsg() );

    	AddOpType addType = null;
    	
        switch (lookahead.token_name) {
        case MP_OR:
            listRule(87); // List the rule number applied
            match(TokenType.MP_OR);
            addType = AddOpType.MP_OR;
            break;
        case MP_PLUS:
	    listRule(85); // List the rule number applied
            match(TokenType.MP_PLUS);
            addType = AddOpType.MP_PLUS;
            break;
        case MP_MINUS:
	    listRule(86); // List the rule number applied
            match(TokenType.MP_MINUS);
            addType = AddOpType.MP_MINUS;
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println("Expected keyword 'OR' or '+' or '-' but found "+ lookahead.token_name);            
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
        return(addType);
    }

    public StackTopRecord Term(StackTopRecord recOnStack) {
	//88:Term                    ⟶ Factor FactorTail 
    	infoLog(genStdInfoMsg());
    	
    	StackTopRecord newType = null;
        StackTopRecord newRec = null;        

        switch (lookahead.token_name) {
        case MP_NOT:
        case MP_IDENTIFIER:
        case MP_INTEGER_LIT:
	case MP_STRING_LIT:
	case MP_FLOAT_LIT:
	case MP_FIXED_LIT:
	case MP_TRUE:
	case MP_FALSE:
        case MP_LPAREN:
	    listRule(88); // List the rule number applied
	    newRec = Factor(recOnStack);
	        
	        // XXX check this
	    if(newRec != null){recOnStack = newRec;}
	     
            newRec = FactorTail(recOnStack);
            
            if(newRec != null){recOnStack = newRec;}
            break;
        default:
            // parsing error
            System.out.println("Parsing error at : " + Thread.currentThread().getStackTrace()[1].getMethodName());
			System.out.println("Expected  '(' or identifier or integer or keyword 'NOT' but found "+ lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
        
        return(recOnStack);
    }

    public StackTopRecord FactorTail(StackTopRecord recOnStack) {
        //89:FactorTail              ⟶ MultiplyingOperator Factor FactorTail
        //90:                        ⟶ ε 
    	infoLog(genStdInfoMsg() );

        
    	StackTopRecord newType = null;
    	StackTopRecord lhsRec = recOnStack;
    	StackTopRecord rhsRec = null;
    	StackTopRecord newRec = null;
    	MulOpType mulType;
    	
        switch (lookahead.token_name) {
        case MP_AND:
        case MP_DIV:
        case MP_MOD:
        case MP_TIMES:
        case MP_DIVISION:
	    listRule(89); // List the rule number applied
	    mulType = MultiplyingOperator();
            rhsRec = Factor(recOnStack);
            newRec = analyze.errorCheckAndCastMulOp(lhsRec, mulType, rhsRec);
            if(newRec != null){recOnStack = newRec;}
            
            newRec = FactorTail(recOnStack);
            if(newRec != null){recOnStack = newRec;}


            break;
        case MP_END:
        case MP_SCOLON:
        case MP_NOT:
        case MP_IDENTIFIER:
        case MP_INTEGER_LIT:
        case MP_LPAREN:
        case MP_COMMA:
        case MP_RPAREN:
        case MP_PLUS:
        case MP_MINUS:
        case MP_OR:
        case MP_LTHAN:
        case MP_GTHAN:
        case MP_LEQUAL:
        case MP_GEQUAL:
        case MP_NEQUAL:
        case MP_EQUAL:
        case MP_THEN:
        case MP_ELSE:
        case MP_UNTIL:
        case MP_DO:
        case MP_TO:
        case MP_DOWNTO:
            // map to ε
            listRule(90); // List the rule number applied
            break;
        default:
            System.out.println("Parsing error at : " + Thread.currentThread().getStackTrace()[1].getMethodName());
            System.out.println("Expected  '(' or identifier or integer or keyword 'NOT' or *** but found "+ lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
        return(recOnStack);
    }

    public MulOpType MultiplyingOperator() {
        //91:MultiplyingOperator     ⟶ "*"
        //92:                        ⟶ "div"
        //93:                        ⟶ "mod"
        //94:                        ⟶ "and"
        infoLog(genStdInfoMsg() );

        MulOpType mulType = null;
        
        switch (lookahead.token_name) {
        case MP_AND:
	        listRule(94); // List the rule number applied
            match(TokenType.MP_AND);
            mulType = MulOpType.MP_AND;
            break;
        case MP_DIV:
	        listRule(92); // List the rule number applied
            match(TokenType.MP_DIV);// MP_DIV???
            mulType = MulOpType.MP_DIV;
            break;
	    case MP_DIVISION:
	        listRule(112); //List the rule number applied
	        match(TokenType.MP_DIVISION);
            mulType = MulOpType.MP_DIVISION;
	        break;
        case MP_MOD:
	        listRule(93); // List the rule number applied
            match(TokenType.MP_MOD);// MP_MOD???
            mulType = MulOpType.MP_MOD;
            break;
        case MP_TIMES:
	        listRule(91); // List the rule number applied
            match(TokenType.MP_TIMES);// MP_TIMES???
            mulType = MulOpType.MP_TIMES;
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.out.println("Expected  multiplying operator but found "+ lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
        return(mulType);
    }

    public StackTopRecord Factor(StackTopRecord recOnStack) {
	//95:Factor                  ⟶ UnsignedInteger
	//96:                        ⟶ VariableIdentifier
	//97:                        ⟶ "not" Factor
	//98:                        ⟶ "(" Expression ")"
	//99:                        ⟶ FunctionIdentifier OptionalActualParameterList
	//113:                       ⟶ UnsignedFloat
	//114:                       ⟶ StringLiteral
	//115:                       ⟶ "True"
	//115:                       ⟶ "False"  
    	infoLog( genStdInfoMsg() );

        SymbolKind idKind = null;
        String lex = "";
        SymbolType newType = null;
        String literalVal = "";
        Symbol sym;
        String offset;
        SymbolWithType typedSym;

        StackTopRecord rec = recOnStack;
        StackTopRecord newRec = recOnStack;

        switch (lookahead.token_name) {
        case MP_NOT:
	    listRule(97); // List the rule number applied
            match(TokenType.MP_NOT);
            rec = Factor(recOnStack);
            newRec = analyze.errorCheckNotOp(rec);
            if(newRec != null){recOnStack = newRec;}
            break;
        case MP_INTEGER_LIT:
	    listRule(95); // List the rule number applied
            literalVal = match(TokenType.MP_INTEGER_LIT);
            newType = SymbolType.MP_SYMBOL_INTEGER;
            newRec = new StackTopRecord(newType);
            analyze.genStoreNumberLitIR(literalVal);
            break;
        case MP_FLOAT_LIT:
	    listRule(113); // List the rule number applied
	    literalVal = match(TokenType.MP_FLOAT_LIT);
            newType = SymbolType.MP_SYMBOL_FLOAT;
            newRec = new StackTopRecord(newType);
            analyze.genStoreNumberLitIR(literalVal);
            break;
        case MP_FIXED_LIT:
            listRule(113); // List the rule number applied                                                                                                           
            literalVal = match(TokenType.MP_FIXED_LIT);
            newType = SymbolType.MP_SYMBOL_FIXED;
            newRec = new StackTopRecord(newType);
            analyze.genStoreNumberLitIR(literalVal);
            break;
        case MP_STRING_LIT:
            String stringVal;
	    listRule(114); // List the rule number applied
            stringVal = match(TokenType.MP_STRING_LIT);
            newType = SymbolType.MP_SYMBOL_STRING;
            newRec = new StackTopRecord(newType);
            analyze.storeString(stringVal);
            break;
        case MP_TRUE:
            listRule(115); // List the rule number applied
            literalVal = match(TokenType.MP_TRUE);
            newType = SymbolType.MP_SYMBOL_BOOLEAN;
            newRec = new StackTopRecord(newType);
            analyze.genStoreNumberLitIR("1");			
            break;
        case MP_FALSE:
            listRule(116); // List the rule number applied
            literalVal = match(TokenType.MP_FALSE);
            newType = SymbolType.MP_SYMBOL_BOOLEAN;
            newRec = new StackTopRecord(newType);
            analyze.genStoreNumberLitIR("0");			
            break;
        case MP_LPAREN:
	    listRule(98); // List the rule number applied
            match(TokenType.MP_LPAREN);
            newRec = Expression(recOnStack);
            match(TokenType.MP_RPAREN);
            break;
        case MP_IDENTIFIER:

            // use symbol table to resolve ambiguity in language
            lex = lookahead.getLexeme();
            idKind = symbolTableHandle.getKindByLexeme( lex );
            if(idKind == null){
                System.out.println("Attempted to look up an undeclared variable: "+ lex);
                symbolTableHandle.dumpAll();
		analyze.cleanupAndDeleteIRFile();
                System.exit(-9);
            }

            switch(idKind){
            case MP_SYMBOL_VAR:
                listRule(96); // List the rule number applied
                VariableIdentifier();
                sym = symbolTableHandle.fetchSymbolByLexeme( lex );
                offset = sym.getOffset();
                analyze.putVarOnStack(offset);
                typedSym = (SymbolWithType) sym;
                newType = typedSym.getType();
                // The record on the stack is currently valid for pass-by-reference
                newRec = new StackTopRecord(newType, lex, SymbolMode.MP_SYMBOL_REFERENCE);  
                
                break;
            case MP_SYMBOL_PARAMETER:
                listRule(96); // List the rule number applied
                VariableIdentifier();
                sym = symbolTableHandle.fetchSymbolByLexeme( lex );
                Args param = (Args) sym;
                offset = param.getOffset();
                if(param.getMode() == SymbolMode.MP_SYMBOL_VALUE){
                    analyze.putVarOnStack(offset);
                }
                else if(param.getMode() == SymbolMode.MP_SYMBOL_REFERENCE){
                    // for using parameters that were passed by reference
                    analyze.putValOnStackWithIndirection(offset);
                }
                typedSym = (SymbolWithType) sym;
                newType = typedSym.getType();
                // The record on the stack is currently valid for pass-by-reference
                newRec = new StackTopRecord(newType, lex, SymbolMode.MP_SYMBOL_REFERENCE);            

                break;
            case MP_SYMBOL_FUNCTION:
                listRule(99); // List the rule number applied
                Function funcHandle;
                String funcLabel;
                ArrayList<StackTopRecord> paramRecs = new ArrayList<StackTopRecord>();
                
                FunctionIdentifier(); 
                sym = symbolTableHandle.fetchSymbolByLexeme( lex );
                offset = sym.getOffset();
                typedSym = (SymbolWithType) sym;
                newType = typedSym.getType();
                paramRecs = OptionalActualParameterList(); 
                
                funcHandle = (Function) typedSym;
                funcLabel = funcHandle.getJumpLabel();
                
                analyze.checkModesPrepRefs(lex, paramRecs);
                
                analyze.genFuncCallIR(funcLabel);
                analyze.postFuncCallCleanupIR( lex );
                
                
                newRec = new StackTopRecord(newType, lex, SymbolMode.MP_SYMBOL_VALUE);
                break;

            default:
            // parsing error
                System.out.println("Parsing error in: " + Thread.currentThread().getStackTrace()[1].getMethodName());
                System.out.println("Found Identifier token: " + lookahead.getLexeme()
                    + ", of kind: " + idKind + ", looking for variable or function");
		analyze.cleanupAndDeleteIRFile();
                System.exit(-7);         
            }
	    break;

        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[1].getMethodName() );
	        System.out.println("Expected  '(' or identifier or integer or keyword 'NOT' but found "+ lookahead.token_name);
		analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
        if( (newRec == null) && (newType != null) ){
            newRec = new StackTopRecord(newType);
        }
        /* XXX probably garbage
        System.out.println("AAA: " + newRec.dataType);
        System.out.println("BBB: " + newType);
        */

        recOnStack = newRec;

        //recOnStack.dataType = newType;

        return(recOnStack);
    }

    public String ProgramIdentifier() {
        //100:ProgramIdentifier    ⟶ Identifier
    	String programLexeme = "";
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());

        switch (lookahead.token_name) {
        case MP_IDENTIFIER:
	        listRule(100); // List the rule number applied
	        programLexeme = match(TokenType.MP_IDENTIFIER);

            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.out.println("Expected identifier but found "+ lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
        return(programLexeme);
    }

    public String VariableIdentifier() {
        //101:VariableIdentifier   ⟶ Identifier
    	infoLog( genStdInfoMsg() );

    	String lex = "";
        switch (lookahead.token_name) {
        case MP_IDENTIFIER:
	        listRule(101); // List the rule number applied
            lex = match(TokenType.MP_IDENTIFIER);
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.out.println("Expected identifier but found "+ lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
        return(lex);
    }

    public String ProcedureIdentifier() {
        //102:ProcedureIdentifier  ⟶ Identifier
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());
    	String procedureName = "";
    	
        switch (lookahead.token_name) {
        case MP_IDENTIFIER:
	        listRule(102); // List the rule number applied
            procedureName = match(TokenType.MP_IDENTIFIER);
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.out.println("Expected identifier but found "+ lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
        return(procedureName);
    }

    public String FunctionIdentifier() {
	//103:FunctionIdentifier   ⟶ Identifier
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());
        String functionName = "";

        switch (lookahead.token_name) {
        case MP_IDENTIFIER:
	    listRule(103); // List the rule number applied
            functionName = match(TokenType.MP_IDENTIFIER);
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.out.println("Expected identifier but found "+ lookahead.token_name);            
	    analyze.cleanupAndDeleteIRFile();
			System.exit(-5);
        }
        return(functionName);
    }

    public void BooleanExpression() {
        //104:BooleanExpression    ⟶ Expression
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());
    	
    	StackTopRecord noValOnStack = null;
    	StackTopRecord recOnStack;
    	
        switch (lookahead.token_name) {
        case MP_PLUS:
        case MP_MINUS:
        case MP_LPAREN:
        case MP_NOT:
        case MP_IDENTIFIER:
        case MP_INTEGER_LIT:
            listRule(104); // List the rule number applied
            recOnStack = Expression(noValOnStack);
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
            System.out.println("Expected '+' or '-' or '(' or identifier or integer or keyword 'NOT' but found "+ lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
    }

    public StackTopRecord OrdinalExpression() {
        //105:OrdinalExpression    ⟶ Expression 
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());
        // XXX'ordinal expression' suggests numbers
    	// should we throw a semantic error if the type on the stack is not numeric?
    	
    	StackTopRecord noValOnStack = null;
    	StackTopRecord recOnStack = null;
    	
        switch (lookahead.token_name) {
        case MP_PLUS:
        case MP_MINUS:
        case MP_LPAREN:
        case MP_NOT:
        case MP_IDENTIFIER:
        case MP_INTEGER_LIT:
        case MP_STRING_LIT:
        case MP_FIXED_LIT:
        case MP_FLOAT_LIT:
		case MP_TRUE:
		case MP_FALSE:
            listRule(105); // List the rule number applied
            recOnStack = Expression(noValOnStack);
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
            System.out.println("Expected '+' or '-' or '(' or identifier or integer or keyword 'NOT' but found "+ lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
        return(recOnStack);
    }

    public ArrayList<String> IdentifierList() {
        // 106:IdentifierList       ⟶ Identifier IdentifierTail
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());
    	ArrayList<String> lexemes = new ArrayList<String>();
    	ArrayList<String> moreLexemes = new ArrayList<String>();
    	String singleLexeme = "";
        switch (lookahead.token_name) {
        case MP_IDENTIFIER:
	        listRule(106); // List the rule number applied
	        singleLexeme = match(TokenType.MP_IDENTIFIER);
            lexemes.add(singleLexeme);
            moreLexemes = IdentifierTail();
            lexemes.addAll(moreLexemes);
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.out.println("Expected identifier but found "+ lookahead.token_name);			
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
        return(lexemes);
    }

    public ArrayList<String> IdentifierTail() {
        //107:IdentifierTail       ⟶ "," Identifier IdentifierTail
        //108:                     ⟶ ε        
    	infoLog(Thread.currentThread().getStackTrace()[1].getMethodName());
    	ArrayList<String> lexemes = new ArrayList<String>();
    	ArrayList<String> moreLexemes = new ArrayList<String>();
    	String singleLexeme = "";
        switch (lookahead.token_name) {
        case MP_COMMA:
	        listRule(107); // List the rule number applied
            match(TokenType.MP_COMMA);
            singleLexeme = match(TokenType.MP_IDENTIFIER);
            lexemes.add(singleLexeme);
            moreLexemes = IdentifierTail();
            lexemes.addAll(moreLexemes);
            break;
        case MP_COLON:
            // apply epsilon
	        listRule(108); // List the rule number applied
            break;
        default:
            // parsing error
            System.out.println("Parsing error in: " + Thread.currentThread().getStackTrace()[1].getMethodName());
			System.out.println("Expected ',' or ':' but found "+ lookahead.token_name);
	    analyze.cleanupAndDeleteIRFile();
            System.exit(-5);
        }
        return(lexemes);
    }
    // MAHESHS BLOCK ENDS HERE
}
