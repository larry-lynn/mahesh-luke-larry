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
    
    public void genStoreLiteralIR(String literal){
        irOutputFileHandle.format(";store a literal value on the stack\n");
        irOutputFileHandle.format("PUSH\t#\"%s\"\n", literal);
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
    
    public void terminateIR(){
        irOutputFileHandle.format("HLT\n");
    }
    
    public void cleanup(){
        irOutputFileHandle.close();
    }
}
