package compiler;

import java.util.Stack;

public class SymbolTableMaster {
    Stack<SymbolTable> symbolTableStack;
    Stack<SymbolTable> restoreStack;
    //SymbolTable currentTable;
    
    public SymbolTableMaster(){
        symbolTableStack = new  Stack<SymbolTable>();
        restoreStack = new  Stack<SymbolTable>();
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
    
    public void dump(){
        // XXX needs to be fixed to dump all tables
        SymbolTable currentTable;
        while(!symbolTableStack.empty() ){
            currentTable = popAndSave();
            currentTable.dump();
        }
        // XXX needs to be fixed to re-wind
        rewind();
    }
    
    public SymbolKind getKindByLexeme(String needle){
        // XXX needs to change to search multiple tables
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
         // XXX needs to change to search multiple tables
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
        // XXX needs massive rework
        SymbolTable tmpTable = new SymbolTable(tableName);
        symbolTableStack.push(tmpTable);
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
}
