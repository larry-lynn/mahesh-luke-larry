package compiler;

public class Parser {
    Token lookahead;
    Scanner scan;

    public static void main(String[] args) throws Exception{
        if( (args.length == 0) || (args.length > 1) ){
            System.out.println("Usage: java compiler.Parser <source-code-file>");
            System.exit(-3);
        }
        System.out.println("Working Directory = " +  System.getProperty("user.dir"));
        
        String infile = args[0];
        
        Parser parse;
        parse = new Parser(infile);
        parse.SystemGoal();
        
    }
    
    // Constructor 1
    public Parser(String fileWithPath) throws Exception {
        scan = new Scanner();
        scan.openFile(fileWithPath);
        lookahead = scan.getToken();
    }

    public void match(TokenType compareTok) {
        if (lookahead.token_name == compareTok) {
            // put the token on the parse tree and get a new one
            System.out.println("putting token: " + lookahead.token_name + ", lexeme: " + lookahead.getLexeme() + " on parse tree");
            // early return if we've parsed everything successfully
            if(lookahead.token_name == TokenType.MP_EOF){
                // XXX change this to meet specs
                return;
            }
            else{
                lookahead = scan.getToken();
            }
        } else {
            // parsing error
            // XXX fixme - this block needs to be moved out of match and into a dedicated error routine
            System.out.println("Scan Error at line: " + lookahead.getLineNumber() + ", column: " + lookahead.getColumnNumber());
            System.out.println("Expected: " + compareTok + ", but got: " + lookahead.token_name);
            // Thread.currentThread().getStackTrace()[1].getMethodName()
            System.exit(-6);
        }
    }

    // ### LUKES BLOCK STARTS HERE ### //
    public void SystemGoal() {
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 1:SystemGoal      ⟶ Program eof    
        switch(lookahead.token_name){
        	case MP_PROGRAM:
        		Program();
        		match(TokenType.MP_EOF);
        		break;
	        default:
		        // parsing error
		        System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
		        System.exit(-5);
        }
    }

    public void Program() {
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 2: Program         ⟶ ProgramHeading ";" Block "."
        switch(lookahead.token_name){
        	case MP_PROGRAM:
                ProgramHeading();
        		match(TokenType.MP_SCOLON);
                Block();
        		match(TokenType.MP_PERIOD);
        		break;
	        default:
	        // parsing error
	        	System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
	        	System.exit(-5);
        }
    }

    public void ProgramHeading() {
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 3:ProgramHeading  ⟶ "program" ProgramIdentifier
        switch(lookahead.token_name){
	        case MP_PROGRAM:
	        	//We are looking for the program ID so we can expand
	        	match(TokenType.MP_PROGRAM);
	        	ProgramIdentifier();
	        	break;
	        default:
		        // parsing error
		        System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
		        System.exit(-5);
        }
    }

    public void Block() {
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 4:Block           ⟶ VariableDeclarationPart ProcedureAndFunctionDeclarationPart StatementPart
        switch(lookahead.token_name){
	        case MP_VAR:
	        	VariableDeclarationPart();
	        	ProcedureAndFunctionDeclarationPart();
	        	StatementPart();
	        	break;
	        default:
		        // parsing error
		        System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
		        System.exit(-5);
        }
    }

    public void VariableDeclarationPart() {
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 5:VariableDeclarationPart  ⟶ "var" VariableDeclaration ";" VariableDeclarationTail
        switch(lookahead.token_name){
	        case MP_VAR:
	        	match(TokenType.MP_VAR);
	            VariableDeclaration();
	        	match(TokenType.MP_SCOLON);
	        	VariableDeclarationTail();
	        	break;
	        default:
	        	// Might need some follow here since Tail could go to nothing
		        // parsing error
		        System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
		        System.exit(-5);
        }
    }

    public void VariableDeclarationTail() {
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	//6:VariableDeclarationTail  ⟶ VariableDeclaration ";" VariableDeclarationTail 
    	//7:                         ⟶ ε
        switch(lookahead.token_name){
	        //Lookahead should be the ID 
	        case MP_IDENTIFIER:
	            VariableDeclaration();
	            match(TokenType.MP_SCOLON);
	            VariableDeclarationTail();
	        	break;
	        // mapping to sigma
	        case MP_BEGIN:
	        case MP_FUNCTION:
	        case MP_PROCEDURE:
	            break;
	        default:
	        	//XXX - Will need follow to deal with the lamnda case
		        // parsing error
		        System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
		        System.exit(-5);
        }
    }

    public void VariableDeclaration() {
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 8:VariableDeclaration      ⟶ Identifierlist ":" Type 
        switch(lookahead.token_name){
	        //We should be looking at IDs coming up
	        case MP_IDENTIFIER:
	        	IdentifierList();
	            match(TokenType.MP_COLON);
	        	Type();
	        	break;
	        default:
		        // parsing error
		        System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
		        System.exit(-5);
        }
    }

    public void Type() {
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 9. 	-> "Integer"
    	// 10. 	-> "Float"
    	// 11.	-> "Boolean"
        switch(lookahead.token_name){
	        case MP_INTEGER:
	        	match(TokenType.MP_INTEGER);
	        	break;
	        case MP_FLOAT:
	        	match(TokenType.MP_FLOAT);
	        	break;
	        // "Boolen" -> Identifier?
	        case MP_IDENTIFIER:
	        	if(lookahead.lexeme.toLowerCase().equals("boolean"))
	        	{
	        		match(TokenType.MP_IDENTIFIER);
	        	}
	        	// The ID was something other than boolean, which should throw an error
	        	else
	        	{
	        		 // parsing error
			        System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			        System.exit(-5);
	        	}
	        	break;
	        default:
		        // parsing error
		        System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
		        System.exit(-5);
		        
        }
    }

    public void ProcedureAndFunctionDeclarationPart() {
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	//12:ProcedureAndFunctionDeclarationPart ⟶ ProcedureDeclaration ProcedureAndFunctionDeclarationPart
    	//13:                                    ⟶ FunctionDeclaration ProcedureAndFunctionDeclarationPart
    	//14:                                    ⟶ ε
        switch(lookahead.token_name){
	        case MP_PROCEDURE:
	        	ProcedureDeclaration();
	        	ProcedureAndFunctionDeclarationPart();
	        	break;
	        case MP_FUNCTION:
	        	FunctionDeclaration();
	        	ProcedureAndFunctionDeclarationPart();
	        	break;
	        case MP_BEGIN:
	            // go to ε
                break;
	        
	        default:
	        	// parsing error
	        	System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
	        	System.exit(-5);
        }
    }

    public void ProcedureDeclaration() {
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 15. ProcedureDeclaration  ->  ProcedureHeading ";" Block ";"
        switch(lookahead.token_name){
	        case MP_PROCEDURE:
	        	ProcedureHeading();
	        	match(TokenType.MP_SCOLON);
	        	Block();
	        	match(TokenType.MP_SCOLON);
	        	break;
	        default:
	        	// parsing error
	        	System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
	        	System.exit(-5);
        }
    }

    public void FunctionDeclaration() {
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 16. <FunctionDeclaration> -> <FunctionHeading> ";" <Block> ";"
        switch(lookahead.token_name){
	        case MP_FUNCTION:
	        	FunctionHeading();
	        	match(TokenType.MP_SCOLON);
	        	Block();
	        	match(TokenType.MP_SCOLON);
	        	break;
	        default:
		        // parsing error
		        System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
		        System.exit(-5);
        }
    }

    public void ProcedureHeading() {
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 17. <ProcedureHeading> -> "procedure" <ProcedureIdentifer> <OptionalFormalParameterList>
        switch(lookahead.token_name){
	        case MP_PROCEDURE:
	        	match(TokenType.MP_PROCEDURE);
	        	ProcedureIdentifier();
	        	OptionalFormalParameterList();
	        	break;
	        default:
		        // parsing error
		        System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
		        System.exit(-5);
        }
    }

    public void FunctionHeading() {
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 18. <FunctionHeading> -> <FunctionHeading> ";" <Block> ";"
        switch(lookahead.token_name){
	        case MP_FUNCTION:
	        	FunctionHeading();
	        	match(TokenType.MP_SCOLON);
	        	Block();
	        	match(TokenType.MP_SCOLON);
	        	break;
	        default:
		        // parsing error
		        System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
		        System.exit(-5);
        }
    }
    
    public void OptionalFormalParameterList() {
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 19. <OptionalFormalParameterList> -> "(" <FormalParameterSection> <FormalParameterSectionTail> ")"
    	// 20. <OptionalFormalParameterList> -> Sigma
        switch (lookahead.token_name) {
	        case MP_LPAREN:
	        	match(TokenType.MP_LPAREN);
	        	FormalParameterSection();
	        	FormalParameterSectionTail();
	        	match(TokenType.MP_RPAREN);
	        	break;
	        case MP_SCOLON:
	        case MP_COLON:
	        	// Mapping to sigma
	        	break;
	        default:
	        	// Need Follow() To deal with having sigma
	            // parsing error
	            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
	            System.exit(-5);
	        }
    }
    
    public void FormalParameterSectionTail() {
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 21. <FormalParameterSectionTail> -> ";" <FormalParameterSection> <FormalParameterSectionTail>
    	// 22. <FormalParameterSectionTail> -> Sigma
        switch (lookahead.token_name) {
	        case MP_SCOLON:
	        	match(TokenType.MP_SCOLON);
	        	FormalParameterSection();
	        	FormalParameterSectionTail();
	        	break;
	        case MP_RPAREN:
	        	// Mapping to sigma
	        	break;
	        default:
	        	// Need deal with sigma with Follow();
	            // parsing error
	            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
	            System.exit(-5);
	        }
    }

    public void FormalParameterSection() {
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 23. <FormalParameterSection> -> <ValueParameterSection>
    	// 24. <FormalParameterSection> -> <VariableParameterSection>
        switch (lookahead.token_name) {
	        case MP_IDENTIFIER:
	        	ValueParameterSection();
	        	break;
	        case MP_VAR:
	        	VariableParameterSection();
	        	break;
	        default:
	            // parsing error
	            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
	            System.exit(-5);
	        }
    }
    
    public void ValueParameterSection() {
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 25. <ValueParameterSection> -> <IdentifierList> ":" <Type>
        switch (lookahead.token_name) {
	        case MP_IDENTIFIER:
	        	IdentifierList();
	        	match(TokenType.MP_COLON);
	        	Type();
	        	break;
	        default:
	            // parsing error
	            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
	            System.exit(-5);
	        }
    }

    public void VariableParameterSection() {
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 26. <VariableParameterSection> -> "var" <IdentifierList> ":" <Type>
        switch (lookahead.token_name) {
	        case MP_VAR:
	        	match(TokenType.MP_VAR);
	        	IdentifierList();
	        	match(TokenType.MP_COLON);
	        	Type();
	        	break;
	        default:
	            // parsing error
	            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
	            System.exit(-5);
	        }
    }

    public void StatementPart() {
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 27:StatementPart      ⟶ CompoundStatement
        switch (lookahead.token_name) {
	        case MP_BEGIN:
	        	CompoundStatement();
	        	break;
	        default:
	            // parsing error
	            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
	            System.exit(-5);
	        }
    }

    public void CompoundStatement() {
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 28:CompoundStatement  ⟶ "begin" StatementSequence "end"
        switch (lookahead.token_name) {
	        case MP_BEGIN:
	        	match(TokenType.MP_BEGIN);
	            StatementSequence();
	        	match(TokenType.MP_END);
	        	break;
	        default:
	            // parsing error
	            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
	            System.exit(-5);
	        }
    }

    public void StatementSequence() {
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 29:StatementSequence  ⟶ Statement StatementTail
        switch (lookahead.token_name) {
        case MP_BEGIN:
        case MP_END:
        case MP_FOR:
        case MP_IF:
        case MP_READ:
        case MP_WRITE:
        case MP_REPEAT:
        case MP_WHILE:
        case MP_IDENTIFIER:
        	Statement();
        	StatementTail();
        	break;
        default:
            // parsing error    
            System.out.println("Parsing error in: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            System.out.println("Lookahead token is: " + lookahead.token_name);
            System.exit(-5);
        }
    }

    // ### LUKES BLOCK ENDS HERE ### //

    // ### LARRYS BLOCK STARTS HERE ### //
    // XXX Larry's bookmark
    public void StatementTail() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        switch (lookahead.token_name) {
        case MP_SCOLON:
            // 30: StatementTail ⟶ ";" Statement StatementTail
            // 31: StatementTail ⟶ ε
            match(TokenType.MP_SCOLON);
            Statement();
            StatementTail();
            break;
        case MP_END:
        case MP_UNTIL:
            // go to ε
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);

        }
    }

    public void Statement() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        switch (lookahead.token_name) {
        case MP_END:
        case MP_UNTIL:
        case MP_SCOLON:
            //32:Statement           ⟶ EmptyStatement
            // XXX fixme - may be other ways to map to empty statement & epsilon
            EmptyStatement();
            break;
        case MP_BEGIN:
            // 33:Statement ⟶ CompoundStatement
            CompoundStatement();
            break;
        case MP_FOR:
            // 40:Statement ⟶ ForStatement
            ForStatement();
            break;
        case MP_IF:
            // 37:Statement ⟶ IfStatement
            IfStatement();
            break;
        case MP_READ:
            // 34:Statement ⟶ ReadStatement
            ReadStatement();
            break;
        case MP_REPEAT:
            // 39:Statement ⟶ RepeatStatement
            RepeatStatement();
            break;
        case MP_WHILE:
            // 38:Statement ⟶ WhileStatement
            WhileStatement();
            break;
        case MP_WRITE:
            // 35:Statement ⟶ WriteStatement
            WriteStatement();
            break;
        case MP_IDENTIFIER:
            // 36:Statement ⟶ AssignmentStatement
            // 41:Statement ⟶ ProcedureStatement
            // XXX Fixme -- AMBIGUOUS!! -- don't know how to resolve this yet.
            // Commenting out for now
            AssignmentStatement();
            // ProcedureStatement();
            break;
        default:
            // parsing error
            System.out.println("Parsing error in: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            System.out.println("Lookahead token is: " + lookahead.token_name);
            System.exit(-5);
        }
    } // end statement

    public void EmptyStatement() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        // 42:EmptyStatement ⟶ ε
        switch (lookahead.token_name) {
        // XXX probably other ways to map to ε
        case MP_END:
        case MP_UNTIL:
        case MP_SCOLON:
            break;
        default:
            // parsing error
            System.out.println("Parsing error in: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            System.out.println("Lookahead token is: " + lookahead.token_name);
            System.exit(-5);
        }
    }

    public void ReadStatement() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        // 43:ReadStatement ⟶ "read" "(" ReadParameter ReadParameterTail ")"
        switch (lookahead.token_name) {
        case MP_READ:
            match(TokenType.MP_READ);
            match(TokenType.MP_LPAREN);
            ReadParameter();
            ReadParameterTail();
            match(TokenType.MP_RPAREN);
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void ReadParameterTail() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        // 44:ReadParameterTail ⟶ "," ReadParameter ReadParameterTail
        // 45: ⟶ ε
        switch (lookahead.token_name) {
        case MP_COMMA:
            match(TokenType.MP_COMMA);
            ReadParameter();
            ReadParameterTail();
            break;
        case MP_RPAREN:
        	// map to ε
        	break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void ReadParameter() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        // 46:ReadParameter ⟶ VariableIdentifier
        switch (lookahead.token_name) {
        case MP_IDENTIFIER:
            VariableIdentifier();
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void WriteStatement() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        // 47:WriteStatement ⟶ "write" "(" WriteParameter WriteParameterTail ")"
        switch (lookahead.token_name) {
        case MP_WRITE:
            match(TokenType.MP_WRITE);
            match(TokenType.MP_LPAREN);
            WriteParameter();
            WriteParameterTail();
            match(TokenType.MP_RPAREN);
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void WriteParameterTail() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        // 48:WriteParameterTail ⟶ "," WriteParameter
        // 49: ⟶ ε
        switch (lookahead.token_name) {
        case MP_COMMA:
            match(TokenType.MP_COMMA);
            WriteParameter();
            break;
        case MP_RPAREN:
            // map to ε
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void WriteParameter() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        // 50:WriteParameter ⟶ OrdinalExpression
        switch (lookahead.token_name) {
        case MP_PLUS:
        case MP_MINUS:
        case MP_LPAREN:
        case MP_NOT:
        case MP_IDENTIFIER:
        case MP_INTEGER_LIT:
            OrdinalExpression();
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void AssignmentStatement() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        // 51:AssignmentStatement ⟶ VariableIdentifier ":=" Expression
        // 52: ⟶ FunctionIdentifier ":=" Expression
        switch (lookahead.token_name) {
        case MP_IDENTIFIER:
            VariableIdentifier();
            match(TokenType.MP_ASSIGN);
            Expression();
            
            break;
         // XXX - Ambiguity - not sure how to resolve this yet
         /*   
         case MP_IDENTIFIER: 
             FunctionIdentifier(); 
             match(TokenType.MP_ASSIGN); 
             Expression(); 
             break;
         */
     
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void IfStatement() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        // 53:IfStatement ⟶ "if" BooleanExpression "then" Statement OptionalElsePart
        switch (lookahead.token_name) {
        case MP_IF:
            match(TokenType.MP_IF);
            BooleanExpression();
            match(TokenType.MP_THEN);
            Statement();
            OptionalElsePart();
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void OptionalElsePart() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        // 54:OptionalElsePart ⟶ "else" Statement
        // 55: ⟶ ε
        switch (lookahead.token_name) {
        case MP_ELSE:
            match(TokenType.MP_ELSE);
            Statement();
            break;
        case MP_END:
        case MP_UNTIL:
        case MP_SCOLON:
            // map to ε
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void RepeatStatement() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        // 56:RepeatStatement ⟶ "repeat" StatementSequence "until" BooleanExpression
        switch (lookahead.token_name) {
        case MP_REPEAT:
            match(TokenType.MP_REPEAT);
            StatementSequence();
            match(TokenType.MP_UNTIL);
            BooleanExpression();
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void WhileStatement() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        // 57:WhileStatement ⟶ "while" BooleanExpression "do" Statement
        switch (lookahead.token_name) {
        case MP_WHILE:
            match(TokenType.MP_WHILE);
            BooleanExpression();
            match(TokenType.MP_DO);
            Statement();
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void ForStatement() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        // 58:ForStatement ⟶ "for" ControlVariable ":=" InitialValue StepValue FinalValue "do" Statement
        switch (lookahead.token_name) {
        case MP_FOR:
            match(TokenType.MP_FOR);
            ControlVariable();
            match(TokenType.MP_ASSIGN);
            InitialValue();
            StepValue();
            FinalValue();
            match(TokenType.MP_DO);
            Statement();
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void ControlVariable() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        // 59:ControlVariable ⟶ VariableIdentifier
        switch (lookahead.token_name) {
        case MP_IDENTIFIER:
            VariableIdentifier();
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void InitialValue() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        // 60:InitialValue ⟶ OrdinalExpression
        switch (lookahead.token_name) {
        case MP_PLUS:
            OrdinalExpression();
            break;
        case MP_MINUS:
            OrdinalExpression();
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void StepValue() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        // 61:StepValue ⟶ "to"
        // 62: ⟶ "downto"
        switch (lookahead.token_name) {
        case MP_TO:
            match(TokenType.MP_TO);
            break;
        case MP_DOWNTO:
            match(TokenType.MP_DOWNTO);
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void FinalValue() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        // 63:FinalValue ⟶ OrdinalExpression
        switch (lookahead.token_name) {
        case MP_PLUS:
            OrdinalExpression();
            break;
        case MP_MINUS:
            OrdinalExpression();
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void ProcedureStatement() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        // 64:ProcedureStatement ⟶ ProcedureIdentifier OptionalActualParameterList
        switch (lookahead.token_name) {
        case MP_IDENTIFIER:
            ProcedureIdentifier();
            OptionalActualParameterList();
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void OptionalActualParameterList() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        // 65:OptionalActualParameterList ⟶ "(" ActualParameter ActualParameterTail ")"
        // 66: ⟶ ε
        switch (lookahead.token_name) {
        case MP_LPAREN:
            match(TokenType.MP_LPAREN);
            ActualParameter();
            ActualParameterTail();
            match(TokenType.MP_RPAREN);
            break;
        case MP_AND:
        case MP_DIV:
        case MP_END:
        case MP_MOD:
        case MP_UNTIL:
        case MP_SCOLON:
        case MP_TIMES:
            // map to ε
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    // ### LARRYS BLOCK ENDS HERE

    // ### MAHSESHS BLOCK STARTS HERE
    public void ActualParameterTail() {
	//67:ActualParameterTail ⟶ "," ActualParameter ActualParameterTail
	//68:                    ⟶ ε
        switch (lookahead.token_name) {
        case MP_COMMA:
            match(TokenType.MP_COMMA);
            ActualParameter();
            ActualParameterTail();
            break;
        default:
            // Need FOLLOW here
            // System.out.println("nobody here but us chickens");
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void ActualParameter() {
	//69:ActualParameter     ⟶ OrdinalExpression
        switch (lookahead.token_name) {
        case MP_PLUS:
        case MP_MINUS:
            OrdinalExpression();
            break;
        default:
            // System.out.println("nobody here but us chickens");
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void Expression() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
	//70:Expression              ⟶ SimpleExpression OptionalRelationalPart
        switch (lookahead.token_name) {
        case MP_PLUS:
        case MP_MINUS:
        case MP_LPAREN:
        case MP_NOT:
        case MP_IDENTIFIER:
        case MP_INTEGER_LIT:
            SimpleExpression();
            OptionalRelationalPart();
            break;
        default:
            // System.out.println("nobody here but us chickens");
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void OptionalRelationalPart() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
	//71:OptionalRelationalPart  ⟶ RelationalOperator SimpleExpression
	//72:                        ⟶ ε
        switch (lookahead.token_name) {
        case MP_EQUAL:
        case MP_GTHAN:
        case MP_GEQUAL:
        case MP_LTHAN:
        case MP_LEQUAL:
        case MP_NEQUAL:
            RelationalOperator();
            SimpleExpression();
            break;
        case MP_END:
        case MP_UNTIL:
        case MP_SCOLON:
        case MP_COMMA:
        case MP_RPAREN:
        case MP_THEN:
        case MP_ELSE:
            // map to ε
            break;
        default:
            // System.out.println("nobody here but us chickens");
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void RelationalOperator() {
	//73:RelationalOperator      ⟶ "="
	//74:                        ⟶ "<"
	//75:                        ⟶ ">"
	//76:                        ⟶ "<="
	//77:                        ⟶ ">="
	//78:                        ⟶ "<>" 
        switch (lookahead.token_name) {
        case MP_EQUAL:
            match(TokenType.MP_EQUAL);
            break;
        case MP_GTHAN:
            match(TokenType.MP_GTHAN);
            break;
        case MP_GEQUAL:
            match(TokenType.MP_GEQUAL);
            break;
        case MP_LTHAN:
            match(TokenType.MP_LTHAN);
            break;
        case MP_LEQUAL:
            match(TokenType.MP_LEQUAL);
            break;
        case MP_NEQUAL:
            match(TokenType.MP_NEQUAL);
            break;
        default:
            // System.out.println("nobody here but us chickens");
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void SimpleExpression() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
	//79:SimpleExpression        ⟶ OptionalSign Term TermTail
        switch (lookahead.token_name) {
        case MP_PLUS:
        case MP_MINUS:
        case MP_LPAREN:
        case MP_NOT:
        case MP_IDENTIFIER:
        case MP_INTEGER_LIT:
            OptionalSign();
            Term();
            TermTail();
            break;
        default:
            // System.out.println("nobody here but us chickens");
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void TermTail() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
	//80:TermTail                ⟶ AddingOperator Term TermTail
	//81:                        ⟶ ε
        switch (lookahead.token_name) {
        case MP_OR:
        case MP_PLUS:
        case MP_MINUS:
            AddingOperator();
            Term();
            TermTail();
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
            // map to ε
            break;

        default:
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void OptionalSign() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
	//82:OptionalSign            ⟶ "+"
	//83:                        ⟶ "-"
	//84:                        ⟶ ε
        switch (lookahead.token_name) {
        case MP_PLUS:
            match(TokenType.MP_PLUS);
            break;
        case MP_MINUS:
            match(TokenType.MP_MINUS);
            break;
        case MP_LPAREN:
        case MP_NOT:
        case MP_IDENTIFIER:
        case MP_INTEGER_LIT:
	    // map to ε
            break;        
        default:
            // System.out.println("nobody here but us chickens");
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void AddingOperator() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
	//85:AddingOperator          ⟶ "+"
	//86:                        ⟶ "-"
	//87:                        ⟶ "or" 
        switch (lookahead.token_name) {
        case MP_OR:
            match(TokenType.MP_OR);
            break;
        case MP_PLUS:
            match(TokenType.MP_PLUS);
            break;
        case MP_MINUS:
            match(TokenType.MP_MINUS);
            break;
        default:
            // System.out.println("nobody here but us chickens");
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void Term() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
	//88:Term                    ⟶ Factor FactorTail 
        switch (lookahead.token_name) {
        case MP_NOT:
        case MP_IDENTIFIER:
        case MP_INTEGER_LIT:
        case MP_LPAREN:
            Factor();
            FactorTail();
            break;
        default:
            // parsing error
            System.out.println("Parsing error at : " + Thread.currentThread().getStackTrace()[1].getMethodName());
            System.out.println("token is: " + lookahead.token_name);     
            System.exit(-5);
        }
    }

    public void FactorTail() {
	//89:FactorTail              ⟶ MultiplyingOperator Factor FactorTail
	//90:                        ⟶ ε 
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        switch (lookahead.token_name) {
        case MP_AND:
        case MP_DIV:
        case MP_MOD:
        case MP_TIMES:
            MultiplyingOperator();
            Factor();
            FactorTail();
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
        case MP_EQUAL:
        case MP_THEN:
        case MP_ELSE:
            // map to ε
            break;
        default:
            System.out.println("Parsing error at : " + Thread.currentThread().getStackTrace()[1].getMethodName());
            System.out.println("token is: " + lookahead.token_name);
            System.exit(-5);
        }
    }

    public void MultiplyingOperator() {
	//91:MultiplyingOperator     ⟶ "*"
	//92:                        ⟶ "div"
	//93:                        ⟶ "mod"
	//94:                        ⟶ "and"
        switch (lookahead.token_name) {
        case MP_AND:
            match(TokenType.MP_AND);
            break;
        case MP_DIV:
            match(TokenType.MP_DIV);// MP_DIV???
            break;
        case MP_MOD:
            match(TokenType.MP_MOD);// MP_MOD???
            break;
        case MP_TIMES:
            match(TokenType.MP_TIMES);// MP_TIMES???
            break;
        default:
            // System.out.println("nobody here but us chickens");
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void Factor() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
	//95:Factor                  ⟶ UnsignedInteger
	//96:                        ⟶ VariableIdentifier
	//97:                        ⟶ "not" Factor
	//98:                        ⟶ "(" Expression ")"
	//99:                        ⟶ FunctionIdentifier OptionalActualParameterList
        switch (lookahead.token_name) {
        case MP_NOT:
            match(TokenType.MP_NOT);
            Factor();
            break;
        case MP_INTEGER_LIT:
            // UnsignedInteger();// No rule for unsignedinteger
            match(TokenType.MP_INTEGER_LIT);
            break;
        case MP_LPAREN:
            match(TokenType.MP_LPAREN);
            Expression();
            match(TokenType.MP_RPAREN);
            break;
        case MP_IDENTIFIER:
            VariableIdentifier();
            break;
            // ambiguity
            // XXX Fixme: add in lookahead for FunctionIdentifier();
            // Probably need symbol table for this.
        default:
            // System.out.println("nobody here but us chickens");
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void ProgramIdentifier() {
	//100:ProgramIdentifier    ⟶ Identifier
        switch (lookahead.token_name) {
        case MP_IDENTIFIER:
            match(TokenType.MP_IDENTIFIER);
            break;
        default:
            // System.out.println("nobody here but us chickens");
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void VariableIdentifier() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
	//101:VariableIdentifier   ⟶ Identifier
        switch (lookahead.token_name) {
        case MP_IDENTIFIER:
            match(TokenType.MP_IDENTIFIER);
            break;
        default:
            // System.out.println("(variable parameter) nobody here but us chickens");
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void ProcedureIdentifier() {
	//102:ProcedureIdentifier  ⟶ Identifier
        switch (lookahead.token_name) {
        case MP_IDENTIFIER:
            match(TokenType.MP_IDENTIFIER);
            break;
        default:
            // System.out.println("nobody here but us chickens");
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void FunctionIdentifier() {
	//103:FunctionIdentifier   ⟶ Identifier
        switch (lookahead.token_name) {
        case MP_IDENTIFIER:
            match(TokenType.MP_IDENTIFIER);
            break;
        default:
            // System.out.println("nobody here but us chickens");
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void BooleanExpression() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
	//104:BooleanExpression    ⟶ Expression
        switch (lookahead.token_name) {
        case MP_PLUS:
        case MP_MINUS:
        case MP_LPAREN:
        case MP_NOT:
        case MP_IDENTIFIER:
        case MP_INTEGER_LIT:
            Expression();
            break;
        default:
            // System.out.println("nobody here but us chickens");
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void OrdinalExpression() {
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
	//105:OrdinalExpression    ⟶ Expression 
        switch (lookahead.token_name) {
        case MP_PLUS:
        case MP_MINUS:
        case MP_LPAREN:
        case MP_NOT:
        case MP_IDENTIFIER:
        case MP_INTEGER_LIT:
            Expression();
            break;
        default:
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void IdentifierList() {
        // 106:IdentifierList       ⟶ Identifier IdentifierTail
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        switch (lookahead.token_name) {
        case MP_IDENTIFIER:
            match(TokenType.MP_IDENTIFIER);
            IdentifierTail();
            break;
        default:
            // System.out.println("nobody here but us chickens");
            // parsing error
            System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.exit(-5);
        }
    }

    public void IdentifierTail() {
        //107:IdentifierTail       ⟶ "," Identifier IdentifierTail
        //108:                     ⟶ ε        
        System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        switch (lookahead.token_name) {
        case MP_COMMA:
            match(TokenType.MP_COMMA);
            match(TokenType.MP_IDENTIFIER);
            IdentifierTail();
            break;
        case MP_COLON:
            // apply epsilon
            break;
        default:
            // System.out.println("nobody here but us chickens");
            // parsing error
            System.out.println("Parsing error in: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            System.out.println("Lookahead token is: " + lookahead.token_name);
            System.exit(-5);
        }
    }
    // MAHESHS BLOCK ENDS HERE

}
