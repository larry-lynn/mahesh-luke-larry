package compiler;

public class Parser {
    Token lookahead;
    Scanner scan;
    
    // Constructor 1
    public Parser(String fileWithPath) throws Exception {
    	scan = new Scanner();
    	scan.openFile(fileWithPath);
    	lookahead = scan.getToken();
    }
    
    public void match(TokenType compareTok){
    	if(lookahead.token_name == compareTok ){
    		// put the token on the parse tree and get a new one
    		System.out.println("putting " + lookahead.getLexeme() + " on parse tree");
    		lookahead = scan.getToken();
    	}
    	else{
    		// parsing error
    		System.out.println("Parsing error while matching at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    		System.exit(-6);
    	}
    }
    
    // ### LUKES BLOCK STARTS HERE ### //
    public void SystemGoal(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void Program(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void ProgramHeading(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void Block(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void VariableDeclarationPart(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void VariableDeclarationTail(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void VariableDeclaration(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void Type(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void ProcedureAndFunctionDeclarationPart(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void ProcedureDeclaration(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void FunctionDeclaration(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void ProcedureHeading(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void FunctionHeading(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void OptionalFormalParameterList(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void FormalParameterSectionTail(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void FormalParameterSection(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void ValueParameterSection(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void VariableParameterSection(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void StatementPart(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void CompoundStatement(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void StatementSequence(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }
    // ### LUKES BLOCK ENDS HERE ### //

    // ### LARRYS BLOCK STARTS HERE ### //
    // XXX Larry's bookmark
    public void StatementTail(){
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	switch(lookahead.token_name){
    	case MP_SCOLON:
    		// 30: StatementTail      ⟶ ";" Statement StatementTail
            // 31: StatementTail      ⟶ ε
    		match(TokenType.MP_SCOLON);
    		Statement();
    		StatementTail();
    	    break;
    	default:
    		// parsing error
    		System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    		System.exit(-5);
    	
    	}
    }

    public void Statement(){
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	switch(lookahead.token_name){
    	case MP_BEGIN:
    		// 33:Statement           ⟶ CompoundStatement
            CompoundStatement();
    	    break;
    	case MP_FOR:
    		// 40:Statement           ⟶ ForStatement
            ForStatement();
    	    break;
    	case MP_IF:
    		// 37:Statement           ⟶ IfStatement
            IfStatement();
    	    break;
    	case MP_READ:
    		// 34:Statement           ⟶ ReadStatement
            ReadStatement();
    	    break;
    	case MP_REPEAT:
    		// 39:Statement           ⟶ RepeatStatement
    		RepeatStatement();
    		break;
    	case MP_WHILE:
    	    // 38:Statement           ⟶ WhileStatement
    		WhileStatement();
    		break;
    	case MP_WRITE:
    		// 35:Statement           ⟶ WriteStatement
    		WriteStatement();
    		break;
    	case MP_IDENTIFIER:
    		// 36:Statement           ⟶ AssignmentStatement
    		// 41:Statement           ⟶ ProcedureStatement
    		// XXX Fixme -- AMBIGUOUS!! -- don't know how to resolve this yet.  Commenting out for now
    		AssignmentStatement();
    		//ProcedureStatement();
    		break;
    	default:
    		// parsing error
    		System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    		System.exit(-5);
    	}
    }  // end statement

    public void EmptyStatement(){
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 42:EmptyStatement      ⟶ ε
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }

    public void ReadStatement(){
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 43:ReadStatement       ⟶ "read" "(" ReadParameter ReadParameterTail ")"
        switch(lookahead.token_name){
    	case MP_READ:
    		match(TokenType.MP_READ);
    		match(TokenType.MP_LPAREN);
    		ReadParameter();
    		ReadParameterTail();
    		// XXX -- not sure if we can do the closing stuff without FOLLOW()
    		//match(")");
    		break;
        default:
    		// parsing error
    		System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    		System.exit(-5);
        }
    }

    public void ReadParameterTail(){
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	//44:ReadParameterTail   ⟶ "," ReadParameter ReadParameterTail
    	//45:                    ⟶ ε
        switch(lookahead.token_name){
    	case MP_COMMA:
    		match(TokenType.MP_COMMA);
    		ReadParameter();
    		ReadParameterTail();
    		break;
        default:
    		// parsing error
    		System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    		System.exit(-5);
        }
    }

    public void ReadParameter(){
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 46:ReadParameter       ⟶ VariableIdentifier 
        switch(lookahead.token_name){
    	case MP_IDENTIFIER:
    		VariableIdentifier();
    		break;
        default:
    		// parsing error
    		System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    		System.exit(-5);
        }
    }

    public void WriteStatement(){
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 47:WriteStatement      ⟶ "write" "(" WriteParameter WriteParameterTail ")"
        switch(lookahead.token_name){
    	case MP_WRITE:
    		match(TokenType.MP_WRITE);
    		match(TokenType.MP_LPAREN);
    		WriteParameter();
    		WriteParameterTail();
    		break;
        default:
    		// parsing error
    		System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    		System.exit(-5);
        }
    }

    public void WriteParameterTail(){
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	//48:WriteParameterTail  ⟶ "," WriteParameter
    	//49:                    ⟶ ε
        switch(lookahead.token_name){
    	case MP_COMMA:
    		match(TokenType.MP_COMMA);
    		WriteParameter();
    		break;
        default:
    		// parsing error
    		System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    		System.exit(-5);
        }
    }

    public void WriteParameter(){
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	//50:WriteParameter      ⟶ OrdinalExpression 
        switch(lookahead.token_name){
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

    public void AssignmentStatement(){
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 51:AssignmentStatement ⟶ VariableIdentifier ":=" Expression
    	// 52:                    ⟶ FunctionIdentifier ":=" Expression 
        switch(lookahead.token_name){
    	case MP_IDENTIFIER:
    		VariableIdentifier();
    		// XXX - not sure if we match non firsts yet
    		// match(":=");
    		// Expression();
    	// XXX - Ambiguity - not sure how to resolve this yet
    		break;
    	/*
    	case MP_IDENTIFIER:
    		VariableIdentifier();
    		// XXX - not sure if we match non firsts yet
    		// match(":=");
    		// Expression();
    		   break;
    		 */

        default:
    		// parsing error
    		System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    		System.exit(-5);
        }
    }

    public void IfStatement(){
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 53:IfStatement         ⟶ "if" BooleanExpression "then" Statement OptionalElsePart
        switch(lookahead.token_name){
    	case MP_IF:
    		match(TokenType.MP_IF);
    		BooleanExpression();
    		// XXX not sure if we can manage these without FOLLOW()
    		//match("then");
    		//Statement();
    		//OptionalElsePart();
    		break;
        default:
    		// parsing error
    		System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    		System.exit(-5);
        }
    }

    public void OptionalElsePart(){
    	//54:OptionalElsePart    ⟶ "else" Statement
    	//55:                    ⟶ ε  
        switch(lookahead.token_name){
    	case MP_ELSE:
    		match(TokenType.MP_ELSE);
    		Statement();
    		break;
        default:
    		// parsing error
    		System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    		System.exit(-5);
        }
    }

    public void RepeatStatement(){
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 56:RepeatStatement     ⟶ "repeat" StatementSequence "until" BooleanExpression
        switch(lookahead.token_name){
    	case MP_REPEAT:
    		match(TokenType.MP_REPEAT);
    		StatementSequence();
    		// XXX - we may need FOLLOW() for this
    		//match("until");
    		//BooleanExpression();
    		break;
        default:
    		// parsing error
    		System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    		System.exit(-5);
        }
    }

    public void WhileStatement(){
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 57:WhileStatement      ⟶ "while" BooleanExpression "do" Statement 
        switch(lookahead.token_name){
    	case MP_WHILE:
    		match(TokenType.MP_WHILE);
    		BooleanExpression();
    		// XXX - we may need FOLLOW() for this
    		//match("do");
    		//Statement();
    		break;
        default:
    		// parsing error
    		System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    		System.exit(-5);
        }
    }

    public void ForStatement(){
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 58:ForStatement        ⟶ "for" ControlVariable ":=" InitialValue StepValue FinalValue "do" Statement
        switch(lookahead.token_name){
    	case MP_FOR:
    		match(TokenType.MP_FOR);
    		ControlVariable();
    		// XXX - we may need FOLLOW() for this
    		//match(":=");
    		//InitialValue();
    		//StepValue();
    		//FinalValue();
    		//Match("do");
    		//Statement();
    		break;
        default:
    		// parsing error
    		System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    		System.exit(-5);
        }
    }

    public void ControlVariable(){
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 59:ControlVariable     ⟶ VariableIdentifier
        switch(lookahead.token_name){
    	case MP_IDENTIFIER:
            VariableIdentifier();
            break;
        default:
    		// parsing error
    		System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    		System.exit(-5);
        }
    }

    public void InitialValue(){
    	// 60:InitialValue        ⟶ OrdinalExpression
        switch(lookahead.token_name){
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

    public void StepValue(){
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 61:StepValue           ⟶ "to"
    	// 62:                    ⟶ "downto"
        switch(lookahead.token_name){
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

    public void FinalValue(){
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 63:FinalValue          ⟶ OrdinalExpression  
        switch(lookahead.token_name){
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

    public void ProcedureStatement(){
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 64:ProcedureStatement  ⟶ ProcedureIdentifier OptionalActualParameterList
        switch(lookahead.token_name){
    	case MP_IDENTIFIER:
            ProcedureIdentifier();
            // XXX may need FOLLOW() for this
            // OptionalActualParameterList();
            break;
        default:
    		// parsing error
    		System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    		System.exit(-5);
        }
    }

    public void OptionalActualParameterList(){
    	System.out.println("ZZZ : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    	// 65:OptionalActualParameterList ⟶ "(" ActualParameter ActualParameterTail ")"
    	// 66:                            ⟶ ε
        switch(lookahead.token_name){
        case MP_LPAREN:
    	    match(TokenType.MP_LPAREN);
    	    ActualParameter();
    	    // XXX we might need FOLLOW() for this
    	    // ActualParameterTail();
    	    // match(")");
    	    break;
        default:
    		// parsing error
    		System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    		System.exit(-5);
        }
    }
    // ### LARRYS BLOCK ENDS HERE

    // ### MAHSESHS BLOCK STARTS HERE
    public void ActualParameterTail(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void ActualParameter(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void Expression(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void OptionalRelationalPart(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void RelationalOperator(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void SimpleExpression(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void TermTail(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void OptionalSign(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void AddingOperator(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void Term(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void FactorTail(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void MultiplyingOperator(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void Factor(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void ProgramIdentifier(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void VariableIdentifier(){
        switch(lookahead.token_name){
        default:
        System.out.println("(variable parameter) nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void ProcedureIdentifier(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void FunctionIdentifier(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void BooleanExpression(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void OrdinalExpression(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void IdentifierList(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void IdentifierTail(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }
    // MAHESHS BLOCK ENDS HERE
    
}
