package compiler;

import java.util.Stack;

public class SymbolTableMaster {
    Stack<SymbolTable> symbolTableStack;
    Stack<SymbolTable> restoreStack;
    int currentDepth;
    //SymbolTable currentTable;
    
    public SymbolTableMaster(){
        symbolTableStack = new  Stack<SymbolTable>();
        restoreStack = new  Stack<SymbolTable>();
        currentDepth = 0;
        //currentTable = null;
    }
    
    public boolean insert(Symbol newNode){
        if(!symbolTableStack.empty()){
            SymbolTable currentTable = symbolTableStack.peek();
            Boolean retVal = currentTable.insert(newNode);
            return(retVal);
        }
        else{
            return(false);
        }
    }
    
    public void dumpAll(){
        SymbolTable currentTable;
        while(!symbolTableStack.empty() ){
            currentTable = popAndSave();
            currentTable.dump();
        }
        rewind();
    }
    
    public void dumpTop(){
        SymbolTable currentTable;
        if(!symbolTableStack.empty()){
            currentTable = symbolTableStack.peek();
            currentTable.dump();
        }
    }
    
    // XXX consider consolidating the search functions into 1 worker function
    // with multiple think wrappers as front-ends
    public Symbol fetchSymbolByLexeme(String needle){
        SymbolTable currentTable;
        Symbol retVal = null;
        while(!symbolTableStack.empty() ){
            currentTable = popAndSave();
            retVal = currentTable.fetchSymbolByLexeme(needle);
            if(retVal != null){
                // we found the token in the current stack
                rewind();
                return(retVal);
            }
        }
        // we searched all the symbol tables and we didn't find the needle
        rewind();
        return(null);
    }
    
    public SymbolKind getKindByLexeme(String needle){
        SymbolTable currentTable;
        SymbolKind retVal = null;
        while(!symbolTableStack.empty() ){
            currentTable = popAndSave();
            retVal = currentTable.getKindByLexeme(needle);
            if(retVal != null){
                // we found the token in the current stack
                rewind();
                return(retVal);
            }
        }
        // we searched all the symbol tables and we didn't find the needle
        rewind();
        return(null);
    }
    
    public Boolean varHasBeenDeclared(String needle){
        SymbolTable currentTable;
        Boolean retVal = false;
        while(!symbolTableStack.empty() ){
            currentTable = popAndSave();
            retVal = currentTable.varHasBeenDeclared(needle);
            if(retVal){
                rewind();
                return(true);
            }
        }    
        rewind();
        return(false);
    }
    
    public void newSymbolTableForNewContext(String tableName){
        SymbolTable tmpTable = new SymbolTable(tableName, currentDepth);
        symbolTableStack.push(tmpTable);
        currentDepth++;
    }
    
    public void ascendContextDestroyTable(){
        // pop a symbol table and just throw it away
        symbolTableStack.pop();
        currentDepth--;
        return;
    }
    
    private void rewind(){
        SymbolTable tmpTable;
        while(!restoreStack.empty() ){
            tmpTable = restoreStack.pop();
            symbolTableStack.push(tmpTable);
        }
    }
    
    private SymbolTable popAndSave(){
        SymbolTable tmpTable = symbolTableStack.pop();
        restoreStack.push(tmpTable);
        return(tmpTable);
    }
    
    public String getDepthAsString(){
        SymbolTable currentTable;
        String depthString = "";
        if(!symbolTableStack.empty()){
            currentTable = symbolTableStack.peek();
            depthString = currentTable.genDepthString();
        }
        return(depthString);
    }
}
