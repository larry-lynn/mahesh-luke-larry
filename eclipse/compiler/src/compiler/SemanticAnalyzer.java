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
    
    public void genMulOpIR(SymbolType lhsType, MulOpType mulType, SymbolType rhsType){
        // XXX needs type casting
        if(lhsType == SymbolType.MP_SYMBOL_STRING || rhsType == SymbolType.MP_SYMBOL_STRING){
            System.out.println("Semantic Error: No legal operations for string types");
            System.exit(-11);
        }
        else if(( lhsType == rhsType) && (rhsType == SymbolType.MP_SYMBOL_BOOLEAN)){
        // Both operands booleans - only boolean mulops permitted
            
        }
        else{
            switch(mulType){
            case MP_TIMES:
                irOutputFileHandle.format(";about to do int mult for 2 values on stack\n");
                irOutputFileHandle.format("MULS\n");
                break;
            case MP_DIV:
                irOutputFileHandle.format(";about to do int div for 2 values on stack\n");
                irOutputFileHandle.format("DIVS\n");
                break;
            case MP_DIVISION:
                irOutputFileHandle.format(";about to do int div for 2 values on stack\n");
                irOutputFileHandle.format("DIVSF\n");
                break;
            case MP_MOD:
                // XXX throw semantic error
                break;
            case MP_AND:
                // XXX: throw semantic error
                break;
            default:
                break;
            }
        }
    }
    
    public void genAddOpIR(SymbolType lhsType, AddOpType addType, SymbolType rhsType){
        // XXX needs type casting
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
}
