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
    
    public void match(String checkString){
    	if(lookahead.getLexeme().equals(checkString)){
    		// put the token on the parse tree and get a new one
    		System.out.println("putting " + checkString + " on parse tree");
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
    	switch(lookahead.token_name){
    	case MP_SCOLON:
    		// 30: StatementTail      ⟶ ";" Statement StatementTail
            // 31: StatementTail      ⟶ ε
    		match(";");
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
    	// 43:ReadStatement       ⟶ "read" "(" ReadParameter ReadParameterTail ")"
        switch(lookahead.token_name){
    	case MP_READ:
    		match("read");
    		match("(");
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
    	//44:ReadParameterTail   ⟶ "," ReadParameter ReadParameterTail
    	//45:                    ⟶ ε
        switch(lookahead.token_name){
    	case MP_COMMA:
    		match(",");
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
    	// 47:WriteStatement      ⟶ "write" "(" WriteParameter WriteParameterTail ")"
        switch(lookahead.token_name){
    	case MP_WRITE:
    		match("write");
    		match("(");
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
    	//48:WriteParameterTail  ⟶ "," WriteParameter
    	//49:                    ⟶ ε
        switch(lookahead.token_name){
    	case MP_COMMA:
    		match(",");
    		WriteParameter();
    		break;
        default:
    		// parsing error
    		System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    		System.exit(-5);
        }
    }

    public void WriteParameter(){
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
    	// 53:IfStatement         ⟶ "if" BooleanExpression "then" Statement OptionalElsePart
        switch(lookahead.token_name){
    	case MP_IF:
    		match("if");
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
    		match("else");
    		Statement();
    		break;
        default:
    		// parsing error
    		System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    		System.exit(-5);
        }
    }

    public void RepeatStatement(){
    	// 56:RepeatStatement     ⟶ "repeat" StatementSequence "until" BooleanExpression
        switch(lookahead.token_name){
    	case MP_REPEAT:
    		match("repeat");
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
    	// 57:WhileStatement      ⟶ "while" BooleanExpression "do" Statement 
        switch(lookahead.token_name){
    	case MP_WHILE:
    		match("while");
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
    	// 58:ForStatement        ⟶ "for" ControlVariable ":=" InitialValue StepValue FinalValue "do" Statement
        switch(lookahead.token_name){
    	case MP_FOR:
    		match("for");
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
    	// 61:StepValue           ⟶ "to"
    	// 62:                    ⟶ "downto"
        switch(lookahead.token_name){
    	case MP_TO:
    		match("to");
    		break;
    	case MP_DOWNTO:
    		match("downto");
    		break;
        default:
    		// parsing error
    		System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    		System.exit(-5);
        }
    }

    public void FinalValue(){
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
    	// 65:OptionalActualParameterList ⟶ "(" ActualParameter ActualParameterTail ")"
    	// 66:                            ⟶ ε
        switch(lookahead.token_name){
        case MP_LPAREN:
    	    match("(");
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
