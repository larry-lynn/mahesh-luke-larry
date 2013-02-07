package compiler;

public class Parser {
    Token lookahead;
    Scanner scan;
    
    public void match(String checkString){
    	if(lookahead.getLexeme().equals(checkString)){
    		// put the token on the parse tree and get a new one
    		System.out.println("putting " + checkString + " on parse tree");
    		lookahead = scan.getToken();
    	}
    	else{
    		// parsing error
    		System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
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
    	case MP_IDENTIFIER:
    		// 36:Statement           ⟶ AssignmentStatement
    		// 41:Statement           ⟶ ProcedureStatement
    		// XXX Fixme -- AMBIGUOUS!! -- don't know how to resolve this yet.  Commenting out for now
    		AssignmentStatement();
    		//ProcedureStatement();
    	default:
    		// parsing error
    		System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    		System.exit(-5);
    	}
    }  // end statement

    public void EmptyStatement(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void ReadStatement(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void ReadParameterTail(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void ReadParameter(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void WriteStatement(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void WriteParameterTail(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void WriteParameter(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void AssignmentStatement(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void IfStatement(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void OptionalElsePart(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void RepeatStatement(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void WhileStatement(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void ForStatement(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void ControlVariable(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void InitialValue(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void StepValue(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void FinalValue(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void ProcedureStatement(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }


    public void OptionalActualParameterList(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
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
        System.out.println("nobody here but us chickens");
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
