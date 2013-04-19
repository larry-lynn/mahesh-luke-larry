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
    	
    	irOutputFileHandle.format(";backup SP so we can restore it later\n");
    	irOutputFileHandle.format("MOV\tSP\t%s\n", depth);
    	for(i = 0; i < symCount; ++i){
    		irOutputFileHandle.format(";make room on stack for X(DX) variables\n");
    		irOutputFileHandle.format("PUSH\t%s\n", depth);
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
        irOutputFileHandle.format(";retrieve a value from the stack & store it in a vairable\n");
        // XXX not sure about the memory management here - should this be in the symbol table?
        irOutputFileHandle.format("POP\t%s\n", offset);
    }
    
    public void genStoreNumberLitIR(String literalVal){
        irOutputFileHandle.format(";store an integer literal value on the stack\n");
        irOutputFileHandle.format("PUSH\t#%s\n", literalVal); 
    }
    
    public void storeString(String stringLit){
        irOutputFileHandle.format(";store value of a string literal on the stack\n");
        irOutputFileHandle.format("PUSH\t#\"%s\"\n", stringLit);
    }
    
    public void genWriteIR(){
        irOutputFileHandle.format(";print a value left on the stack by an expression\n");
        irOutputFileHandle.format("WRTLNS\n");
    }
    
    public void putVarOnStack(String offset){
        irOutputFileHandle.format(";put the value of a variable in a factor onto the stack\n");
        irOutputFileHandle.format("PUSH\t%s\n", offset);
    }
    
    public void genMulIR(){
        irOutputFileHandle.format(";about to multiply 2 values on stack\n");
        irOutputFileHandle.format("MULS\n");
    }
    public void deepCastFloatToIntIR(){
        // XXX Luke's code to go here
        System.out.println("Cast float 2 deep in stack to int");
    }
    public void castFloatToIntIR(){
        // XXX Luke's code to go here
        System.out.println("Cast float on top of stack to int");  
    }
    public void deepCastIntToFloatIR(){
        // XXX Luke's code to go here
        System.out.println("Cast int 2 deep in stack to float");
    }
    public void castIntToFloatIR(){
        // XXX Luke's code to go here
        System.out.println("Cast int on top of stack to float");  
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
        // XXX FIXME Mahesh -- this is just a stub so our perevious expression program compiles right
        
        SymbolType newTypeOnStack = null;
        irOutputFileHandle.format(";about to do int add for 2 values on stack\n");
        irOutputFileHandle.format("ADDS\n");
        newTypeOnStack = SymbolType.MP_SYMBOL_INTEGER;
        return(newTypeOnStack);
    }
    
    public void genMulIntIR(){
        irOutputFileHandle.format(";about to do int mult for 2 values on stack\n");
        irOutputFileHandle.format("MULS\n");
    }
    
    public void genMulFloatIR(){
        irOutputFileHandle.format(";about to do int mult for 2 values on stack\n");
        irOutputFileHandle.format("MULSF\n");
    }
    
    public void genDivIntIR(){
        irOutputFileHandle.format(";about to do int div for 2 values on stack\n");
        irOutputFileHandle.format("DIVS\n");
    }
    
    public void genDivFloatIR(){
        irOutputFileHandle.format(";about to do float div for 2 values on stack\n");
        irOutputFileHandle.format("DIVSF\n");
    }
    
    public void genBoolAndBoolIR(){
        irOutputFileHandle.format(";about to emit AND for 2 Booleans\n");
        irOutputFileHandle.format("ANDS\n");
    }
    public void genIntModIntIR(){
        irOutputFileHandle.format(";about to emit MOD for 2 Ints\n");
        irOutputFileHandle.format("MODS\n");
    }
    
    
    public void genAddOpIR(SymbolType lhsType, AddOpType addType, SymbolType rhsType){
        // XXX needs type casting
        // XXX THIS WHOLE METHOD NEEDS TO BE DISASSEMBLED
        if(lhsType == SymbolType.MP_SYMBOL_STRING || rhsType == SymbolType.MP_SYMBOL_STRING){
            System.out.println("Semantic Error: No legal operations for string types");
            System.exit(-11);
        }
        else if(( lhsType == rhsType) && (rhsType == SymbolType.MP_SYMBOL_BOOLEAN)){
        // Both operands booleans - only boolean mulops permitted
            
        }
        else{
            switch(addType){
            case MP_PLUS:
                irOutputFileHandle.format(";about to do int add for 2 values on stack\n");
                irOutputFileHandle.format("ADDS\n");
                break;
            case MP_MINUS:
                irOutputFileHandle.format(";about to do int div for 2 values on stack\n");
                irOutputFileHandle.format("SUBS\n");
                break;
            case MP_OR:
                irOutputFileHandle.format(";about to do OR for 2 values on stack\n");
                irOutputFileHandle.format("ORS\n");
                break;
            default:
                break;
            }
        }
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
