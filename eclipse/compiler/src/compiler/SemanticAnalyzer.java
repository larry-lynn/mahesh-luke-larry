package compiler;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.File;

public class SemanticAnalyzer {
    PrintWriter irOutputFileHandle;
    SymbolTableMaster symbolTableHandle;
    int labelCounter;
    String irOutputFileName;
    
    public SemanticAnalyzer(String fileWithPath, SymbolTableMaster stHandle) throws Exception{        
        irOutputFileHandle = new PrintWriter(fileWithPath + ".ir");
        symbolTableHandle = stHandle;
        labelCounter = 0;
	irOutputFileName = fileWithPath + ".ir";
    }
    
    public String genUniqueLabel(){
        StringBuilder label = new StringBuilder();
        label.append("L");
        label.append(labelCounter);
        labelCounter++;
        return(label.toString());
    }

    public void assignDxForRelativeAddressing(){
        String depth;
        depth = symbolTableHandle.getDepthAsString();
        irOutputFileHandle.format("MOV\tSP\t%s\t ;backup SP to Dx to enable realtive addressing\n", depth);
    }
    
    public void genAllocateMemForLocalVarsIR(){
    	
    	//int symCount;
    	//int i;
    	//symCount = symbolTableHandle.getSymbolCountForCurrentTable();
    	
    	ArrayList<Symbol> topTableAsList = symbolTableHandle.topToArrayList();
    		
        for(Symbol s : topTableAsList){
            if(s.getKind() == SymbolKind.MP_SYMBOL_VAR){
                irOutputFileHandle.format("PUSH\t#\"MEM FOR: %s\"\t ;allocate stack memory for var %s\n", s.getLexeme(), s.getLexeme());
            }
        }
    }
    
    public void tearDownMainVariablesIR(){
        ArrayList<Symbol> topTableAsList = symbolTableHandle.topToArrayList();   
        
        for(Symbol s : topTableAsList){
            if(s.getKind() == SymbolKind.MP_SYMBOL_VAR){
                irOutputFileHandle.format("POP\tD0\t ; free up memory for all variables in main program\n");
            }
        }
    }
    
    
    public void genAssignmentIR(String varLex, SymbolType type){
        // Inserted type checking here
        String offset;
        Symbol sym;
        String depth;
        sym = symbolTableHandle.fetchSymbolByLexeme(varLex);
        offset = sym.getOffset();
        depth = symbolTableHandle.getDepthAsString();
	//check to see if we have a VAR
	if(sym.kind == SymbolKind.MP_SYMBOL_VAR)
	{
	    Symbol temp = symbolTableHandle.fetchSymbolByLexeme(varLex);
	    SymbolWithType temp2 = (SymbolWithType)temp;
	    SymbolType lhs = temp2.getType();
	    //System.out.printf("Check on lhs with type %s\n",lhs);
	    //System.out.printf("Check on rhs with type %s\n",type);
	    //check for lhs is int and rhs is float
	    if(lhs == SymbolType.MP_SYMBOL_INTEGER && isFloatType(type))
	    {
		//signal call to make cast
		castFloatToIntIR();
	    }
	    // else check for lhs is float and rhs is int
	    else if( isFloatType(lhs) &&  type == SymbolType.MP_SYMBOL_INTEGER)
		castIntToFloatIR();
            // else check for invalid assignments
	    else if( (lhs == SymbolType.MP_SYMBOL_INTEGER || isFloatType(lhs)) && !( isFloatType(type) || type == SymbolType.MP_SYMBOL_INTEGER))
	    {
		System.out.println("Semantic Error: Can not do assignment to float/int with type not float/int");
		cleanupAndDeleteIRFile();
		System.exit(-12);
	    }
	    else if( lhs == SymbolType.MP_SYMBOL_BOOLEAN && type != SymbolType.MP_SYMBOL_BOOLEAN)
		{
		    System.out.println("Semantic Error: Can not do assignment to bool without non bool type");
		    cleanupAndDeleteIRFile();
		    System.exit(-13);
		}
	    else if( lhs == SymbolType.MP_SYMBOL_STRING && type != SymbolType.MP_SYMBOL_STRING)
		{
		    System.out.println("Semantic Error: Can not do assignment to string without string type");
		    cleanupAndDeleteIRFile();
		    System.exit(-14);
		}
	}
        //irOutputFileHandle.format(";retrieve a value from the stack & store it in a vairable\n");
        irOutputFileHandle.format("POP\t%s\t ;retrieve a value from the stack & store it in a vairable\n", offset);
    }

    public void genAssignmentWithIndirectionIR(String varLex, SymbolType type){
        // XXX this probably needs hardening for type safety
        String offset;
        Symbol sym;
        sym = symbolTableHandle.fetchSymbolByLexeme(varLex);
        offset = sym.getOffset();
        irOutputFileHandle.format("POP\t@%s\t ;retrieve a value from the stack; store it in an address with indirection\n", offset);
    }
    
    public void genNegativeIR(SymbolType inputType) {
	//Signal we have to make the number negative
	if(isFloatType(inputType))
	    irOutputFileHandle.format("NEGSF ;make the float number negative\n");
	else if(inputType == SymbolType.MP_SYMBOL_INTEGER)
	    irOutputFileHandle.format("NEGS ;make the integer number negative\n");
	else
	{
	    System.out.println("Semantic Error: Cannot apply negative operation on given type.");
	    cleanupAndDeleteIRFile();
	    System.exit(-11);
	}
    }
    
    public void genStoreNumberLitIR(String literalVal){
        //irOutputFileHandle.format(";store an integer literal value on the stack\n");
        irOutputFileHandle.format("PUSH\t#%s\t ;store an integer literal on the stack\n", literalVal); 
    }
    
    public void storeString(String stringLit){
        //irOutputFileHandle.format(";store value of a string literal on the stack\n");
        irOutputFileHandle.format("PUSH\t#\"%s\"\t;store a string literal on the stack\n", stringLit);
    }
    
    public void genWriteIR(ArrayList<StackTopRecord> writeOutVars){
		int i,count;
		count = writeOutVars.size();
		//We can write from a src using WRT and might not have to push and pop
		for(i = count; i > 0;i--)
		{
			irOutputFileHandle.format("WRT\t%s(SP)\t;print value on the stack\n", -(i));
		}
		//perform some arithmatic on the pointer
		irOutputFileHandle.format("PUSH SP\n");
		irOutputFileHandle.format("PUSH #%s\n", count);
		irOutputFileHandle.format("SUBS\n");
		irOutputFileHandle.format("POP SP\n");
    }
    
    public void genWriteLineIR(ArrayList<StackTopRecord> writeOutVars){
        int i, count;
        count = writeOutVars.size();
        // XXX Hack - does it work if we reverse the order of what's on the stack 
        // then tear it down?
        for(i = count; i > 0; --i){
			irOutputFileHandle.format("WRTLN\t%s(SP)\t;print value on the stack\n",-i);
        }
        irOutputFileHandle.format("PUSH SP\n");
        irOutputFileHandle.format("PUSH #%s\n", count);
        irOutputFileHandle.format("SUBS\n");
        irOutputFileHandle.format("POP SP\n");
        /*
        for(StackTopRecord singleWriteOutVar : writeOutVars){
            irOutputFileHandle.format("WRTLNS\t ;print a value left on the stack by an expression\n");
        }
        */
        //irOutputFileHandle.format("WRTLNS\t ;print a value left on the stack by an expression\n");
    }
    
    public void genReadIR(ArrayList<String> readIntoVars){
    	Symbol varSym;
    	SymbolWithType typedVarSym;
    	String offset;
    	SymbolMode symMode = null;
    	Args param = null;
    	Boolean isParam = false;
    	
        for(String varLex : readIntoVars){
        	varSym = symbolTableHandle.fetchSymbolByLexeme(varLex);
        	typedVarSym = (SymbolWithType) varSym;
        	offset = typedVarSym.getOffset();
        	if(varSym.getKind() == SymbolKind.MP_SYMBOL_PARAMETER){
        	    isParam = true;
        	    param = (Args) varSym;
        	    symMode = param.getMode();
        	}
        	
        	switch(typedVarSym.getType() ){
        	case MP_SYMBOL_INTEGER:
        	    if(isParam && (symMode == SymbolMode.MP_SYMBOL_REFERENCE)){
        	        irOutputFileHandle.format("RD\t@%s\t; read integer into param with indirection\n", offset);
        	    }
        	    else{
        	        irOutputFileHandle.format("RD\t%s\t; read integer into int var\n", offset);
        	    }
        		break;
        	case MP_SYMBOL_STRING:
                if(isParam && (symMode == SymbolMode.MP_SYMBOL_REFERENCE)){
                    irOutputFileHandle.format("RDS\t@%s\t; read string into param with indirection\n", offset);
                }
                else{
                    irOutputFileHandle.format("RDS\t%s\t; read stringr into var\n", offset);
                }	
        		break;
        	case MP_SYMBOL_FIXED:
        	case MP_SYMBOL_FLOAT:
        	case MP_SYMBOL_REAL:
                if(isParam && (symMode == SymbolMode.MP_SYMBOL_REFERENCE)){
                    irOutputFileHandle.format("RDF\t@%s\t; read float into param with indirection\n", offset);
                }
                else{
                    irOutputFileHandle.format("RDF\t%s\t; read integer into\n", offset);
                }
        		break;
        	default:
        		System.out.println("This block in read IR should be unreachable");
        		System.exit(-220);
        		
        	}

        }
    }
    
    public String genBranchAroundDefsIR(){
        String branchAroundDefsLabel;
        branchAroundDefsLabel = genUniqueLabel();
        irOutputFileHandle.format("BR\t%s\t ; branching around Procedure and Function Declarations\n",branchAroundDefsLabel);
        return( branchAroundDefsLabel );
    }
    
    public void genFuncCallIR(String funcLabel){
        irOutputFileHandle.format("PUSH\t#\"RESERVED SPACE FOR RETURN\" ; reserving space for function return on stack\n");
        irOutputFileHandle.format("CALL\t%s\t ; Call a function\n", funcLabel);
    }
    
    public void postFuncCallCleanupIR(String funcName){
        Function funcHandle;
        int argCount;
        funcHandle = (Function) symbolTableHandle.fetchSymbolByLexeme(funcName);
        argCount = funcHandle.getArgCount();
        
        if(argCount > 0){
            // move return value down, clobber the lowest arg
            irOutputFileHandle.format("POP\t-%s(SP)\t ; move ret val down the stack so it is still around after we wipe the input params\n", argCount);
            irOutputFileHandle.format("PUSH\tSP\t ; tear down input params to function so they dont clutter the stack \n");
            irOutputFileHandle.format("PUSH #%s\n", (argCount - 1) );
            irOutputFileHandle.format("SUBS\n");
            irOutputFileHandle.format("POP\tSP\n");
        }
        
    }
    
    public void genFuncDefPreambleIR(String functionName){
        // back up old Dx
        // allocate memory for all parameters
        String depth;
        ArrayList<Symbol> topTableAsList = symbolTableHandle.topToArrayList();
        Function funcSym;
        int argCount, i, j, stackOffset;
        Args singleArg;
        
        depth = symbolTableHandle.getDepthAsString();
        funcSym = (Function) symbolTableHandle.fetchSymbolByLexeme(functionName);
        argCount = funcSym.getArgCount();     
        
        irOutputFileHandle.format("PUSH\t%s\t ; Back up Old-Dx to assist recursion\n",depth);
        assignDxForRelativeAddressing();
 
        for(Symbol s : topTableAsList){
            if(s.getKind() == SymbolKind.MP_SYMBOL_PARAMETER){
                irOutputFileHandle.format("PUSH\t#\"MEM FOR: %s\"\t ;allocate stack memory for param %s\n", s.getLexeme(), s.getLexeme());
            }
        }
        
        // load argument vals from expression stack into local param memory
        j = 0;
        for(i = argCount; i > 0; --i){
            stackOffset = 0 - (3 + i);
            singleArg = Args.getArgAtPosition(topTableAsList, j);
            irOutputFileHandle.format("MOV\t%s(%s)\t%s\t ; load local param memory with data\n",stackOffset, depth, singleArg.getOffset() );
            ++j;
        }
        
        
    }
    
    public void genFuncDefPostambleIR(String functionName){
        String depth;
        ArrayList<Symbol> topTableAsList = symbolTableHandle.topToArrayList();
        
        depth = symbolTableHandle.getDepthAsString();
        
        for(Symbol s : topTableAsList){
            if(s.getKind() == SymbolKind.MP_SYMBOL_PARAMETER || s.getKind() == SymbolKind.MP_SYMBOL_VAR){
                irOutputFileHandle.format("POP\t%s\t; De-allocate local memory for all func args and vars\n", depth);        
            }
        }
        
        irOutputFileHandle.format("POP\t%s\t; Restore value of old Dx register\n", depth);
        irOutputFileHandle.format("RET\t ; Returning from procedure to caller\n");
    }
    
    public void stashFunctionRetValIR(){
        String depth;
        depth = symbolTableHandle.getDepthAsString();
        irOutputFileHandle.format("POP\t-3(%s)\t ; tuck return value down below activation rec\n", depth);
    }
    
    public void genProcCallIR(String procLabel){
        irOutputFileHandle.format("CALL\t%s\t ; Call a procedure\n", procLabel);
    }
    
    public void postProcCallCleanupIR(String procName){
        Procedure procHandle;
        int argCount;
        procHandle = (Procedure) symbolTableHandle.fetchSymbolByLexeme(procName);
        argCount = procHandle.getArgCount();
        irOutputFileHandle.format("PUSH SP\n");
        irOutputFileHandle.format("PUSH #%s\n", argCount);
        irOutputFileHandle.format("SUBS\n");
        irOutputFileHandle.format("POP SP\n");
        
    }
    
    public void genProcDefPreambleIR(String procedureName){
        // back up old Dx
        // allocate memory for all parameters
        String depth;
        ArrayList<Symbol> topTableAsList = symbolTableHandle.topToArrayList();
        Procedure procSym;
        int argCount, i, j, stackOffset;
        Args singleArg;
        
        depth = symbolTableHandle.getDepthAsString();
        procSym = (Procedure) symbolTableHandle.fetchSymbolByLexeme(procedureName);
        argCount = procSym.getArgCount();
        
        irOutputFileHandle.format("PUSH\t%s\t ; Back up Old-Dx to assist recursion\n",depth);
        assignDxForRelativeAddressing();
 
        for(Symbol s : topTableAsList){
            if(s.getKind() == SymbolKind.MP_SYMBOL_PARAMETER){
                irOutputFileHandle.format("PUSH\t#\"MEM FOR: %s\"\t ;allocate stack memory for param %s\n", s.getLexeme(), s.getLexeme());
            }
        }
        
        // load argument vals from expression stack into local param memory
        j = 0;
        for(i = argCount; i > 0; --i){
            
            stackOffset = 0 - (2 + i);
            singleArg = Args.getArgAtPosition(topTableAsList, j);
            irOutputFileHandle.format("MOV\t%s(%s)\t%s\t ; load local param memory with data\n",stackOffset, depth, singleArg.getOffset() );
            ++j;
        }
        
        
    }
    
    public void genProcDefPostambleIR(String procedureName){
        String depth;
        int symbolCount;
        ArrayList<Symbol> topTableAsList = symbolTableHandle.topToArrayList();
        
        symbolCount = symbolTableHandle.getSymbolCountForCurrentTable(); 
        depth = symbolTableHandle.getDepthAsString();
        
        for(Symbol s : topTableAsList){
            if(s.getKind() == SymbolKind.MP_SYMBOL_PARAMETER || s.getKind() == SymbolKind.MP_SYMBOL_VAR){
                irOutputFileHandle.format("POP\t%s\t; De-allocate local memory for all proc args and vars\n", depth);        
            }
        }
        
        
        irOutputFileHandle.format("POP\t%s\t; Restore value of old Dx register\n", depth);
        irOutputFileHandle.format("RET\t ; Returning from procedure to caller\n");
    }

    public void checkModesPrepRefs(String procLex, ArrayList<StackTopRecord> actualParamRecs){
        ArrayList<Args> formalParams = null;
        Symbol tmpSym, varByRef;
        Procedure proc;
        Function func;
        int i, numFormalParams, numActualParams, relativeOffset;
        Args singleFormalParam, priorInputArg;
        StackTopRecord singleActualParam;
        String register;
        SymbolMode priorParamInputMode;


        tmpSym = symbolTableHandle.fetchSymbolByLexeme(procLex);
        if(tmpSym.getKind() == SymbolKind.MP_SYMBOL_PROCEDURE){
            proc = (Procedure) tmpSym;
            formalParams = proc.getArgs();
        }
        else if(tmpSym.getKind() == SymbolKind.MP_SYMBOL_FUNCTION){
            func = (Function) tmpSym;
            formalParams = func.getArgs();
        }
        numFormalParams = formalParams.size();
        numActualParams = actualParamRecs.size();
        if(numFormalParams != numActualParams){
            System.out.println("Semantic error calling subroutine");
            System.out.println("Expected: " + numFormalParams + " arguments, but got: " + numActualParams);
	    cleanupAndDeleteIRFile();
            System.exit(-20);
        }
    
        for(i = 0; i < numFormalParams; ++i){
	        singleFormalParam = formalParams.get(i);
            singleActualParam = actualParamRecs.get(i);
            tmpSym = symbolTableHandle.fetchSymbolByLexeme(singleActualParam.variableLexeme);

            if(tmpSym == null){
                priorParamInputMode = SymbolMode.MP_SYMBOL_VALUE;
            }
            else if(tmpSym.getKind() == SymbolKind.MP_SYMBOL_PARAMETER){
                priorInputArg = (Args) tmpSym;
                priorParamInputMode = priorInputArg.getMode();
            }
            else{
                priorParamInputMode = SymbolMode.MP_SYMBOL_VALUE;
            }


            if( (singleFormalParam.getMode() == SymbolMode.MP_SYMBOL_REFERENCE) &&
                (singleActualParam.callTypeCompatibility != SymbolMode.MP_SYMBOL_REFERENCE) ){
                System.out.println("Semantic error calling subroutine");
                System.out.println("Parameter: " + singleFormalParam.getLexeme() + " is defined call-by-reference");
                System.out.println("But actual parameter is not compatible with this");
		cleanupAndDeleteIRFile();
                System.exit(-21);
            }
            else if( (singleFormalParam.getMode() == SymbolMode.MP_SYMBOL_REFERENCE) &&
                (singleActualParam.callTypeCompatibility == SymbolMode.MP_SYMBOL_REFERENCE) &&
                 (priorParamInputMode == SymbolMode.MP_SYMBOL_REFERENCE) ){
                // Pass by reference is in play
                // SPECIAL CASE - pass by ref param passed again to a subroutine that is also
                // pass by ref. 
                genPushRawAddressForParamContainingAnAddressIR(singleActualParam);
                genReplaceValWithAddressIR(i, numFormalParams);
            }
            else if( (singleFormalParam.getMode() == SymbolMode.MP_SYMBOL_REFERENCE) &&
                (singleActualParam.callTypeCompatibility == SymbolMode.MP_SYMBOL_REFERENCE) ){
                // Pass by reference is in play
                varByRef = symbolTableHandle.fetchSymbolByLexeme(singleActualParam.variableLexeme);
                relativeOffset = varByRef.getPartialNumericAddress();
                register = varByRef.getRegister();
                genCalculateAddressIR(register, relativeOffset);
                genReplaceValWithAddressIR(i, numFormalParams);
            }
        } 

    }
    
    public void genCalculateAddressIR(String register, int relativeOffset){
        irOutputFileHandle.format("PUSH\t%s\t ; calculating the runtime address of a variable\n", register);
        irOutputFileHandle.format("PUSH\t#%s\n", relativeOffset);
        irOutputFileHandle.format("ADDS\t ; Should leave a calculated address on top of the stack\n");
    }
    
    public void genPushRawAddressForParamContainingAnAddressIR(StackTopRecord priorByRefParam){
        String paramLex = priorByRefParam.variableLexeme;
        Symbol argSym = symbolTableHandle.fetchSymbolByLexeme(paramLex);
        String offset = argSym.getOffset(); 
        irOutputFileHandle.format("PUSH\t%s\t ;put a raw address on the stack for the dreaded double-call-by-ref\n", offset);
    }
    
    public void genReplaceValWithAddressIR(int position, int numParams){
        int depthInStack;
        depthInStack = (-1 * numParams) + position;
        irOutputFileHandle.format("POP\t%s(SP)\t ; replace a value in the stack with an address\n",depthInStack);
    }
    
	public String genIfIR(){
        //irOutputFileHandle.format(";write a branch statement for IF\n");
		String elseLabel = genUniqueLabel();
        irOutputFileHandle.format("BRFS\t%s;branch to the label if the condition satisfies\n", elseLabel);
		return elseLabel;
    }

    public void putElseLabel(String elseLabel){
        //irOutputFileHandle.format(";write the new Label name\n");
        irOutputFileHandle.format("%s:;put the new Label\n", elseLabel);
    }
	
	public String genLabelAroundElse(){
        //irOutputFileHandle.format(";write a branch statement for IF\n");
		String afterElseLabel = genUniqueLabel();
		return afterElseLabel;
    }	
	
	public void branchAroundElse(String afterElseLabel){
		irOutputFileHandle.format("BR\t%s;branch to the Label if the condition satisfies\n", afterElseLabel);
	}
	
	public void putAfterElseLabel(String afterElseLabel){
        //irOutputFileHandle.format(";write the new Label name\n");
        irOutputFileHandle.format("%s:;put the new Label\n", afterElseLabel);
    }
    public void dropLabelIR(String label){
        irOutputFileHandle.format("%s:\t;drop a label to be refered to later\n", label);
    }
    public void genUntilTerminationIR(String returnLabel){
        irOutputFileHandle.format("BRFS\t%s\t; repeat if the condition is not satisfied\n", returnLabel);
    }  
    
    public void genWhileLoopPreambleIR(String exitWhileLoopLabel){
        irOutputFileHandle.format("BRFS\t%s\t; repeat WHILE until terminating condition satisfied\n", exitWhileLoopLabel);
    }
    
    public void genWhileLoopPostambleIR(String beginWhileLoopLabel){
        irOutputFileHandle.format("BR\t%s\t ; return to top of FOR loop\n", beginWhileLoopLabel);
    }
    
    public void genForLoopPreambleIR(String controlVarLex, Boolean positive, String exitForLoopLabel){
        putVarOnStackByName(controlVarLex);
	//Added today on 4-24 for an error about CMPNES, need to drop Terminator here as well
        if(positive){
			irOutputFileHandle.format("CMPGES\t; check FOR loop condition\n");
        }
        else{
			irOutputFileHandle.format("CMPLES\t; check FOR loop condition\n");
        }
        irOutputFileHandle.format("BRFS\t%s\t; repeat FOR until terminating condition satisfied\n", exitForLoopLabel);
   
    }
    public void genForLoopPostambleIR(String controlVarLex, Boolean positive, String beginForLoopLabel){
        putVarOnStackByName(controlVarLex);
        irOutputFileHandle.format("PUSH\t#1\t ;put FOR, loop step:int 1 on the stack\n");
        if(positive){
            genAddIntIR();

        }
        else{
            genSubIntIR();
        }
        genAssignmentIR(controlVarLex, SymbolType.MP_SYMBOL_INTEGER);
        irOutputFileHandle.format("BR\t%s\t ; return to top of FOR loop\n", beginForLoopLabel);
    }
	
    public void putVarOnStack(String offset){
        //irOutputFileHandle.format(";put the value of a variable in a factor onto the stack\n");
        irOutputFileHandle.format("PUSH\t%s\t ;put the value of a variable in a factor onto the stack\n", offset);
    }
    
    public void putValOnStackWithIndirection(String offset){
        irOutputFileHandle.format("PUSH\t@%s\t ; use index to get an address; dereference address; put that val on the stack\n", offset);
    }
    
    public void putVarOnStackByName(String varLexeme){
        Symbol sym = symbolTableHandle.fetchSymbolByLexeme( varLexeme );
        String offset = sym.getOffset();
        putVarOnStack(offset);
    }

    public SymbolType deepCastFloatToIntIR() {
	SymbolType newTypeOnStack = null;
	// XXX Luke's code to go here
	System.out.println("Cast float to int 2 deep on stack");
	irOutputFileHandle.format(";cast the float to an int 2 deep on the stack\n");
	irOutputFileHandle.format("PUSH -2(SP) ;push the bottom float on top of the stack\n ");
	irOutputFileHandle.format("CASTSI ;cast the top to an int\n");
	irOutputFileHandle.format("POP -2(SP) ;move the value back 2 deep\n");

	newTypeOnStack = SymbolType.MP_SYMBOL_INTEGER;
	return newTypeOnStack;

    }
    public SymbolType castFloatToIntIR(){
        SymbolType newTypeOnStack = null;
        // XXX Luke's code to go here
        System.out.println("Cast float on top of stack to int");
        irOutputFileHandle.format(";cast the float on top of stack to an int\n");
        irOutputFileHandle.format("CASTSI\n");

        newTypeOnStack = SymbolType.MP_SYMBOL_INTEGER;
        return (newTypeOnStack);

    }
    public SymbolType deepCastIntToFloatIR(){
        SymbolType newTypeOnStack = null;
        System.out.println("Cast int 2 deep in stack to float");
        irOutputFileHandle.format(";cast the int 2 deep on the stack to a float\n");
        irOutputFileHandle.format("PUSH -2(SP) ;push the bottom int on top of the stack\n");
        irOutputFileHandle.format("CASTSF ;cast the top to a float\n");
        irOutputFileHandle.format("POP -2(SP) ;move the value back 2 deep\n");

        newTypeOnStack = SymbolType.MP_SYMBOL_FLOAT;
        return (newTypeOnStack);
    }

    public SymbolType castIntToFloatIR(){
        SymbolType newTypeOnStack = null;
        System.out.println("Cast int on top of stack to float");
        irOutputFileHandle.format("CASTSF ;cast the int on top of the stack to a float\n");

        newTypeOnStack = SymbolType.MP_SYMBOL_FLOAT;
        return (newTypeOnStack);
    }

    // Method for checking comparison and their numbers
    public StackTopRecord errorCheckandCastCompareOp(StackTopRecord lhsRec, RelationalOpType opType, StackTopRecord rhsRec) {
	SymbolType newTypeOnStack = null;
	SymbolType lhsType = lhsRec.dataType;
	SymbolType rhsType = rhsRec.dataType;
	StackTopRecord newRecOnStack = new StackTopRecord(SymbolType.MP_SYMBOL_BOOLEAN);

 	//Check to see if we have strings first
	if( lhsType == SymbolType.MP_SYMBOL_STRING || rhsType == SymbolType.MP_SYMBOL_STRING )
	{
	    //Send message that we have an error
	    System.out.println(" Semantic Error: No legal operations for string types with relational operator" );
	    cleanupAndDeleteIRFile();
	    System.exit(-11);
	}
	//Check to see if we have booleans as well
	else if( (lhsType == SymbolType.MP_SYMBOL_BOOLEAN && rhsType != SymbolType.MP_SYMBOL_BOOLEAN) || (lhsType != SymbolType.MP_SYMBOL_BOOLEAN && rhsType == SymbolType.MP_SYMBOL_BOOLEAN ))
	{
	    //Send message that we have an error
	    System.out.println(" Semantic Error: No legal operations for non bool-bool types with relational operator");
	    cleanupAndDeleteIRFile();
	    System.exit(-12);
	}
	// Else check to see if we need to do casting before compare hapens
		 else if( lhsType == SymbolType.MP_SYMBOL_INTEGER && isFloatType(rhsType) )
	{
	    //Signal to do cast at first level
	    deepCastIntToFloatIR();
	    lhsType = SymbolType.MP_SYMBOL_FLOAT;
	}
		 else if( isFloatType(lhsType) && rhsType == SymbolType.MP_SYMBOL_INTEGER )
	{
	    //Signal to do cast at second level
	    castIntToFloatIR();
	    rhsType = SymbolType.MP_SYMBOL_FLOAT;
        }
	//Signal IR code generation
		 if( isFloatType(lhsType) && isFloatType(rhsType) )
        {
	    //check which symbol
	    if( opType == RelationalOpType.MP_EQUAL )
		genFloatEqualIR();
	    else if( opType == RelationalOpType.MP_GTHAN )
		genFloatGreaterIR();
	    else if( opType == RelationalOpType.MP_GEQUAL )
		genFloatGreaterOrEqualIR();
	    else if( opType == RelationalOpType.MP_LTHAN )
		genFloatLessIR();
	    else if( opType == RelationalOpType.MP_LEQUAL )
		genFloatLessOrEqualIR();
	    else if( opType == RelationalOpType.MP_NEQUAL )
		genFloatNotEqualIR();
	}
	else if( lhsType == SymbolType.MP_SYMBOL_INTEGER && rhsType == SymbolType.MP_SYMBOL_INTEGER )
	{
	    //check which symbol
	    
            if( opType == RelationalOpType.MP_EQUAL )
                genEqualIR();
            else if( opType == RelationalOpType.MP_GTHAN )
                genGreaterIR();
            else if( opType == RelationalOpType.MP_GEQUAL )
                genGreaterOrEqualIR();
            else if( opType == RelationalOpType.MP_LTHAN )
                genLessIR();
            else if( opType == RelationalOpType.MP_LEQUAL )
                genLessOrEqualIR();
            else if( opType == RelationalOpType.MP_NEQUAL )
                genNotEqualIR();

	}
	else if( lhsType == SymbolType.MP_SYMBOL_BOOLEAN && rhsType == SymbolType.MP_SYMBOL_BOOLEAN )
	    {
		if( opType == RelationalOpType.MP_EQUAL )
		    genEqualIR();
		else if( opType == RelationalOpType.MP_NEQUAL )
		    genNotEqualIR();
		else
		    {
			System.out.println("Semantic Error: Op Type does not work with Boolean types");
			cleanupAndDeleteIRFile();
			System.exit(-14);
		    }
	    }
	else {
	    // we some how got past all the checks and something went wrong
	    System.out.println("Semantic Error: Relational Operator checker went AWOL");
	}
	newTypeOnStack = SymbolType.MP_SYMBOL_BOOLEAN;
        newRecOnStack.dataType = newTypeOnStack;
	return (newRecOnStack);

    }
    
    
    public StackTopRecord errorCheckAndCastMulOp(StackTopRecord lhsRec, MulOpType mulType, StackTopRecord rhsRec){
        //System.out.println("ZZZ: " + lhsRec.dataType + ", YYY: " + rhsRec.dataType);
        StackTopRecord newRecOnStack = new StackTopRecord(SymbolType.MP_SYMBOL_INTEGER);
    	SymbolType lhsType = lhsRec.dataType;
    	SymbolType rhsType = rhsRec.dataType;
    	SymbolType newTypeOnStack = null;
    	
    	if( (lhsType==SymbolType.MP_SYMBOL_STRING) || (rhsType==SymbolType.MP_SYMBOL_STRING) ){
    	    // string types in a mulop
            System.out.println("Semantic Error: No legal operations for string types");
	    cleanupAndDeleteIRFile();
            System.exit(-11);
    	}
    	else if( (lhsType == SymbolType.MP_SYMBOL_BOOLEAN) && (mulType == MulOpType.MP_AND) &&
                (lhsType == rhsType)){
            // BOOL AND BOOL is OK -- no other mulops involving BOOLs or ANDs 
            genBoolAndBoolIR();
            newTypeOnStack = SymbolType.MP_SYMBOL_BOOLEAN;
    	}
    	else if(lhsType == SymbolType.MP_SYMBOL_BOOLEAN){
    	    System.out.println("Semantic Error: BOOL AND BOOL only legal boolean MulOp");
	    cleanupAndDeleteIRFile();
            System.exit(-12);
    	}
        else if(rhsType == SymbolType.MP_SYMBOL_BOOLEAN){
            System.out.println("Semantic Error: BOOL AND BOOL only legal boolean MulOp");
	    cleanupAndDeleteIRFile();
            System.exit(-13);
        }
        else if(mulType == MulOpType.MP_AND){
            System.out.println("Semantic Error: BOOL AND BOOL only legal boolean MulOp");
	    cleanupAndDeleteIRFile();
            System.exit(-15);
        }
    	else if( mulType == MulOpType.MP_MOD){
    	    // mod type mulop -- all numbers OK with proper casting
            if(lhsType != SymbolType.MP_SYMBOL_INTEGER){
                deepCastFloatToIntIR();
            }
            if(rhsType != SymbolType.MP_SYMBOL_INTEGER){
                castFloatToIntIR();
            }
            genIntModIntIR();
            newTypeOnStack = SymbolType.MP_SYMBOL_INTEGER;
    	}
    	else if( mulType == MulOpType.MP_DIV){
    	    // integer division type mulop -- all numbers OK with proper casting
            if(lhsType != SymbolType.MP_SYMBOL_INTEGER){
                deepCastFloatToIntIR();
            }
            if(rhsType != SymbolType.MP_SYMBOL_INTEGER){
                castFloatToIntIR();
            }
            genDivIntIR();
            newTypeOnStack = SymbolType.MP_SYMBOL_INTEGER;
    	}
        else if( mulType == MulOpType.MP_DIVISION){
            // float division type mulop -- all numbers OK with proper casting
            // WRONG - need float type check method
            if( !isFloatType(lhsType) ){
                deepCastIntToFloatIR();
            }
            if( !isFloatType(rhsType) ){
                castIntToFloatIR();
            }
            genDivFloatIR();
            newTypeOnStack = SymbolType.MP_SYMBOL_FLOAT;
        }
        else if( mulType == MulOpType.MP_TIMES){
            // int or float type multiplication depends on types of arguments
            if(lhsType == SymbolType.MP_SYMBOL_INTEGER && rhsType == SymbolType.MP_SYMBOL_INTEGER){
                genMulIntIR();
                newTypeOnStack = SymbolType.MP_SYMBOL_INTEGER;
            }
            else if( !isFloatType(lhsType) && isFloatType(rhsType)){
                deepCastIntToFloatIR();
                genMulFloatIR();
                newTypeOnStack = SymbolType.MP_SYMBOL_FLOAT;
            }
            else if( isFloatType(lhsType) && !isFloatType(rhsType)){
                castIntToFloatIR();
                genMulFloatIR();
                newTypeOnStack = SymbolType.MP_SYMBOL_FLOAT;
            }
            else{
                genMulFloatIR();
                newTypeOnStack = SymbolType.MP_SYMBOL_FLOAT; 
            }
            
        }
        newRecOnStack.dataType = newTypeOnStack;
    	//System.out.println("YYY: " + lhsType);
    	//System.out.println("ZZZ: " + rhsType);
  	
    	return(newRecOnStack);
    }
    
    public StackTopRecord errorCheckAndCastAddOp(StackTopRecord lhsRec, AddOpType addType, StackTopRecord rhsRec){
        //System.out.println("ZZZ: " + lhsRec.dataType + ", YYY: " + rhsRec.dataType);
    	SymbolType newTypeOnStack = null;
        SymbolType lhsType = lhsRec.dataType;
        SymbolType rhsType = rhsRec.dataType;
        StackTopRecord newRecOnStack = new StackTopRecord(SymbolType.MP_SYMBOL_INTEGER);
        /*
        System.out.println("XXX: " + lhsType);
        System.out.println("Operator :" + addType );
        System.out.println("YYY: " + rhsType);
        */    	

        if(lhsType == SymbolType.MP_SYMBOL_STRING || rhsType == SymbolType.MP_SYMBOL_STRING){
            System.out.println("Semantic Error: No legal operations for string types");
	    cleanupAndDeleteIRFile();
            System.exit(-11);
        }
    	else if( (lhsType == SymbolType.MP_SYMBOL_BOOLEAN) && (addType == AddOpType.MP_OR) &&
                (lhsType == rhsType)){
            genBoolOrBoolIR();
            newTypeOnStack = SymbolType.MP_SYMBOL_BOOLEAN;
    	}
    	else if(lhsType == SymbolType.MP_SYMBOL_BOOLEAN){
    	    System.out.println("Semantic Error: BOOL OR BOOL only legal boolean AddOp");
	    cleanupAndDeleteIRFile();
            System.exit(-12);
    	}
        else if(rhsType == SymbolType.MP_SYMBOL_BOOLEAN){
            System.out.println("Semantic Error: BOOL OR BOOL only legal boolean AddOp");
	    cleanupAndDeleteIRFile();
            System.exit(-13);
        }
        else if(addType == AddOpType.MP_OR){
            System.out.println("Semantic Error: BOOL OR BOOL only legal boolean AddOp");
	    cleanupAndDeleteIRFile();
            System.exit(-15);
        }
        else if( addType == AddOpType.MP_PLUS){
            if(lhsType == SymbolType.MP_SYMBOL_INTEGER && rhsType == SymbolType.MP_SYMBOL_INTEGER){
                genAddIntIR();
                newTypeOnStack = SymbolType.MP_SYMBOL_INTEGER;
            }
            else if( !isFloatType(lhsType) && isFloatType(rhsType)){
                deepCastIntToFloatIR();
                genAddFloatIR();
                newTypeOnStack = SymbolType.MP_SYMBOL_FLOAT;
            }
            else if( isFloatType(lhsType) && !isFloatType(rhsType)){
                castIntToFloatIR();
                genAddFloatIR();
                newTypeOnStack = SymbolType.MP_SYMBOL_FLOAT;
            }
            else{
                genAddFloatIR();
                newTypeOnStack = SymbolType.MP_SYMBOL_FLOAT; 
            }
            
        }

        else if( addType == AddOpType.MP_MINUS){
            if(lhsType == SymbolType.MP_SYMBOL_INTEGER && rhsType == SymbolType.MP_SYMBOL_INTEGER){
                genSubIntIR();
                newTypeOnStack = SymbolType.MP_SYMBOL_INTEGER;
            }
            else if( !isFloatType(lhsType) && isFloatType(rhsType)){
                deepCastIntToFloatIR();
                genSubFloatIR();
                newTypeOnStack = SymbolType.MP_SYMBOL_FLOAT;
            }
            else if( isFloatType(lhsType) && !isFloatType(rhsType)){
                castIntToFloatIR();
                genSubFloatIR();
                newTypeOnStack = SymbolType.MP_SYMBOL_FLOAT;
            }
            else{
                genSubFloatIR();
                newTypeOnStack = SymbolType.MP_SYMBOL_FLOAT; 
            }
            
        }
        newRecOnStack.dataType = newTypeOnStack;

    	return(newRecOnStack);
    }

    public StackTopRecord errorCheckNotOp(StackTopRecord rec){
        //System.out.println("XXX: " + type);

    	StackTopRecord newRecOnStack = new StackTopRecord(SymbolType.MP_SYMBOL_BOOLEAN);

        if(rec.dataType != SymbolType.MP_SYMBOL_BOOLEAN){
            System.out.println("Semantic Error: NOT only legal for BOOLEAN types");
	    cleanupAndDeleteIRFile();
            System.exit(-11);
        }

	genNotIR();

    	return(newRecOnStack);
    }

    
    public void genMulIntIR(){
        //irOutputFileHandle.format(";int mult for 2 values on stack\n");
        irOutputFileHandle.format("MULS \t ;int mult for 2 values on stack\n");
    }
    
    public void genMulFloatIR(){
        //irOutputFileHandle.format(";about to do float mult for 2 values on stack\n");
        irOutputFileHandle.format("MULSF \t ;float mult for 2 values on stack\n");
    }
    
    public void genDivIntIR(){
        //irOutputFileHandle.format(";about to do int div for 2 values on stack\n");
        irOutputFileHandle.format("DIVS \t ;int div for 2 values on stack\n");
    }
    
    public void genDivFloatIR(){
        //irOutputFileHandle.format(";about to do float div for 2 values on stack\n");
        irOutputFileHandle.format("DIVSF \t ;float div for 2 values on stack\n");
    }
    
    public void genBoolAndBoolIR(){
        irOutputFileHandle.format(";about to emit AND for 2 Booleans\n");
        irOutputFileHandle.format("ANDS\n");
    }
    public void genIntModIntIR(){
        irOutputFileHandle.format(";about to emit MOD for 2 Ints\n");
        irOutputFileHandle.format("MODS\n");
    }

    public void genAddIntIR(){
        irOutputFileHandle.format(";about to do int add for 2 values on stack\n");
        irOutputFileHandle.format("ADDS\n");
    }
    
    public void genAddFloatIR(){
        irOutputFileHandle.format(";about to do int add for 2 values on stack\n");
        irOutputFileHandle.format("ADDSF\n");
    }
    
    public void genSubIntIR(){
        irOutputFileHandle.format(";about to do int sub for 2 values on stack\n");
        irOutputFileHandle.format("SUBS\n");
    }
    
    public void genSubFloatIR(){
        irOutputFileHandle.format(";about to do float sub for 2 values on stack\n");
        irOutputFileHandle.format("SUBSF\n");
    }
    public void genBoolOrBoolIR(){
        irOutputFileHandle.format(";about to emit OR for 2 Booleans\n");
        irOutputFileHandle.format("ORS\n");
    }

    public void genOrIR(){
        irOutputFileHandle.format(";about to emit OR for 2 values on stack\n");
        irOutputFileHandle.format("ORS\n");
    }
    public void genAndIR(){
        irOutputFileHandle.format(";about to emit AND for 2 values on stack\n");
        irOutputFileHandle.format("ANDS\n");
    }
    public void genNotIR(){
        irOutputFileHandle.format(";about to emit NOT for value on stack\n");
        irOutputFileHandle.format("NOTS\n");
    }
    public void genEqualIR() {
	irOutputFileHandle.format("CMPEQS ;about to do an equal compare for 2 values on stack\n");
    }
    public void genNotEqualIR() {
	irOutputFileHandle.format("CMPNES ;about to do a not-equal compare for 2 values on stack\n");
    }
    public void genGreaterIR() {
	irOutputFileHandle.format("CMPGTS ;about to do a greater than compare for 2 values on stack\n");
    }
    public void genGreaterOrEqualIR() {
	irOutputFileHandle.format("CMPGES ;about to do a greater than or equal compare for 2 values on stack\n");
    }
    public void genLessIR() {
        irOutputFileHandle.format("CMPLTS ;about to do a less than compare for 2 values on stack\n");
    }
    public void genLessOrEqualIR() {
	irOutputFileHandle.format("CMPLES ;about to do a less than or equal compare for 2 values on stack\n");
    }
    public void genFloatEqualIR() {
        irOutputFileHandle.format("CMPEQSF ;about to do a float-equal compare for 2 values on stack\n");
    }
    public void genFloatNotEqualIR() {
	irOutputFileHandle.format("CMPNESF ;about to do a float-not-equal compare for 2 values on stack\n");
    }
    public void genFloatGreaterIR() {
	irOutputFileHandle.format("CMPGTSF ;about to do a float-greater than compare for 2 values on stack\n");
    }
    public void genFloatGreaterOrEqualIR() {
	irOutputFileHandle.format("CMPGESF ;about to do a float-greater than or equal compare for 2 values on stack\n");
    }
    public void genFloatLessIR() {
	irOutputFileHandle.format("CMPLTSF ;about to do a float-less than compare for 2 values on the stack\n");
    }
    public void genFloatLessOrEqualIR() {
	irOutputFileHandle.format("CMPLESF ;about to do a float-less than or equal compare for 2 values on the stack\n");
    }
    
    public void terminateIR(){
        irOutputFileHandle.format("HLT\n");
    }
    
    public void cleanup(){
        irOutputFileHandle.close();
    }

    public void cleanupAndDeleteIRFile(){
        irOutputFileHandle.close();
	File file = new File(irOutputFileName);
       	file.delete();
    }
    
    private boolean isFloatType(SymbolType sType){
        if( (sType == SymbolType.MP_SYMBOL_FIXED) || (sType == SymbolType.MP_SYMBOL_FLOAT) || 
            (sType == SymbolType.MP_SYMBOL_REAL) ){
                 return(true);
        }
        else{
            return(false);       
        }
    }
}
