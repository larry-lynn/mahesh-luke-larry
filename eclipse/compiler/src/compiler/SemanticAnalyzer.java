package compiler;

import java.io.PrintWriter;
import java.util.ArrayList;

public class SemanticAnalyzer {
    PrintWriter irOutputFileHandle;
    SymbolTableMaster symbolTableHandle;
    
    public SemanticAnalyzer(String fileWithPath, SymbolTableMaster stHandle) throws Exception{        
        irOutputFileHandle = new PrintWriter(fileWithPath + ".ir");
        symbolTableHandle = stHandle;
    }
    
    public void genCreateActivationRecordIR(){
    	String depth;
    	int symCount;
    	int i;
    	symCount = symbolTableHandle.getSymbolCountForCurrentTable();
    	depth = symbolTableHandle.getDepthAsString();
    	
    	//irOutputFileHandle.format(";backup SP so we can restore it later\n");
    	irOutputFileHandle.format("MOV\tSP\t%s\t ;backup SP so we can restore it later\n", depth);
    	for(i = 0; i < symCount; ++i){
	    //irOutputFileHandle.format(";make room on stack for X(DX) variables\n");
    	    irOutputFileHandle.format("PUSH\t%s\t ;make room on stack for X(DX) variables\n", depth);
    	}
    	/*
    	ArrayList<Symbol> topTableAsList = symbolTableHandle.topToArrayList();
        for(Symbol s : topTableAsList){
        	System.out.println("AAA: " + s.getLexeme());
        }
        */
    }
    
    public void genAssignmentIR(String varLex, SymbolType type){
        // XXX need type checking in here
        String offset;
        Symbol sym;
        String depth;
        sym = symbolTableHandle.fetchSymbolByLexeme(varLex);
        offset = sym.getOffset();
        depth = symbolTableHandle.getDepthAsString();
        //irOutputFileHandle.format(";retrieve a value from the stack & store it in a vairable\n");
        // XXX not sure about the memory management here - should this be in the symbol table?
        irOutputFileHandle.format("POP\t%s\t ;retrieve a value from the stack & store it in a vairable\n", offset);
    }
    
    public void genStoreNumberLitIR(String literalVal){
        //irOutputFileHandle.format(";store an integer literal value on the stack\n");
        irOutputFileHandle.format("PUSH\t#%s\t ;store an integer literal on the stack\n", literalVal); 
    }
    
    public void storeString(String stringLit){
        //irOutputFileHandle.format(";store value of a string literal on the stack\n");
        irOutputFileHandle.format("PUSH\t#\"%s\"\t;store a string literal on the stack\n", stringLit);
    }
    
    public void genWriteIR(){
        //irOutputFileHandle.format(";print a value left on the stack by an expression\n");
        irOutputFileHandle.format("WRTLNS\t ;print a value left on the stack by an expression\n");
    }
    
    public void putVarOnStack(String offset){
        //irOutputFileHandle.format(";put the value of a variable in a factor onto the stack\n");
        irOutputFileHandle.format("PUSH\t%s\t ;put the value of a variable in a factor onto the stack\n", offset);
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
        // XXX Luke's code to go here
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
        // XXX Luke's code to go here
        System.out.println("Cast int on top of stack to float");
        irOutputFileHandle.format("CASTSF ;cast the int on top of the stack to a float\n");

        newTypeOnStack = SymbolType.MP_SYMBOL_FLOAT;
        return (newTypeOnStack);
    }

    // Method for checking comparison and their numbers
    public SymbolType errorCheckandCastCompareOp(SymbolType lhsType, RelationalOpType opType, SymbolType rhsType) {
	SymbolType newTypeOnStack = null;

	System.out.printf("Left hand side type found was: %s\n", lhsType);
	System.out.printf("Op Type found was: %s\n",opType);
	System.out.printf("Right hand side type found was: %s\n", rhsType);

	//Check to see if we have strings first
	if( lhsType == SymbolType.MP_SYMBOL_STRING || rhsType == SymbolType.MP_SYMBOL_STRING )
	{
	    //Send message that we have an error
	    System.out.println(" Semantic Error: No legal operations for string types with relational operator" );
	    System.exit(-11);
	}
	//Check to see if we have booleans as well
	else if( lhsType == SymbolType.MP_SYMBOL_BOOLEAN || rhsType == SymbolType.MP_SYMBOL_BOOLEAN )
	{
	    //Send message that we have an error
	    System.out.println(" Semantic Error: No legal operations for bool types with relational operator");
	    System.exit(-12);
	}
	// Else check to see if we need to do casting before compare hapens
	else if( lhsType == SymbolType.MP_SYMBOL_INTEGER && rhsType == SymbolType.MP_SYMBOL_FLOAT )
	{
	    //Signal to do cast at first level
	    deepCastIntToFloatIR();
	    lhsType = SymbolType.MP_SYMBOL_FLOAT;
	}
	else if( lhsType == SymbolType.MP_SYMBOL_FLOAT && rhsType == SymbolType.MP_SYMBOL_INTEGER )
	{
	    //Signal to do cast at second level
	    castIntToFloatIR();
	    rhsType = SymbolType.MP_SYMBOL_FLOAT;
        }
	//Signal IR code generation
	if( lhsType == SymbolType.MP_SYMBOL_FLOAT && rhsType == SymbolType.MP_SYMBOL_FLOAT )
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
	else {
	    // we some how got past all the checks and something went wrong
	    System.out.println("Semantic Error: Relational Operator checker went AWOL");
	}
	newTypeOnStack = SymbolType.MP_SYMBOL_BOOLEAN;
	return (newTypeOnStack);

    }
    
    
    public SymbolType errorCheckAndCastMulOp(SymbolType lhsType, MulOpType mulType, SymbolType rhsType){
    	SymbolType newTypeOnStack = null;
    	
    	if( (lhsType==SymbolType.MP_SYMBOL_STRING) || (rhsType==SymbolType.MP_SYMBOL_STRING) ){
    	    // string types in a mulop
            System.out.println("Semantic Error: No legal operations for string types");
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
            System.exit(-12);
    	}
        else if(rhsType == SymbolType.MP_SYMBOL_BOOLEAN){
            System.out.println("Semantic Error: BOOL AND BOOL only legal boolean MulOp");
            System.exit(-13);
        }
        else if(mulType == MulOpType.MP_AND){
            System.out.println("Semantic Error: BOOL AND BOOL only legal boolean MulOp");
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

    	//System.out.println("YYY: " + lhsType);
    	//System.out.println("ZZZ: " + rhsType);
  	
    	return(newTypeOnStack);
    }
    
    public SymbolType errorCheckAndCastAddOp(SymbolType lhsType, AddOpType addType, SymbolType rhsType){
    	SymbolType newTypeOnStack = null;
        //System.out.println("XXX: " + lhsType);
        //System.out.println("YYY: " + rhsType);
    	
        if(lhsType == SymbolType.MP_SYMBOL_STRING || rhsType == SymbolType.MP_SYMBOL_STRING){
            System.out.println("Semantic Error: No legal operations for string types");
            System.exit(-11);
        }
    	else if( (lhsType == SymbolType.MP_SYMBOL_BOOLEAN) && (addType == AddOpType.MP_OR) &&
                (lhsType == rhsType)){
            genBoolOrBoolIR();
            newTypeOnStack = SymbolType.MP_SYMBOL_BOOLEAN;
    	}
    	else if(lhsType == SymbolType.MP_SYMBOL_BOOLEAN){
    	    System.out.println("Semantic Error: BOOL OR BOOL only legal boolean AddOp");
            System.exit(-12);
    	}
        else if(rhsType == SymbolType.MP_SYMBOL_BOOLEAN){
            System.out.println("Semantic Error: BOOL OR BOOL only legal boolean AddOp");
            System.exit(-13);
        }
        else if(addType == AddOpType.MP_OR){
            System.out.println("Semantic Error: BOOL OR BOOL only legal boolean AddOp");
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

    	return(newTypeOnStack);
    }

    public SymbolType errorCheckNotOp(SymbolType type){
    	SymbolType newTypeOnStack = SymbolType.MP_SYMBOL_BOOLEAN;

        if(type != SymbolType.MP_SYMBOL_BOOLEAN){
            System.out.println("Semantic Error: NOT only legal for BOOLEAN types");
            System.exit(-11);
        }

	genNotIR();

    	return(newTypeOnStack);
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
