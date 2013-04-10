package compiler;

import java.util.Stack;

public class SymbolTableMaster {
    Stack<SymbolTable> symbolTableStack;
    SymbolTable currentTable;
    
    public SymbolTableMaster(){
        symbolTableStack = new  Stack<SymbolTable>();         
        currentTable = null;
    }
    
    public boolean insert(Symbol newNode){
        return(currentTable.insert(newNode));
    }
    
    public void dump(){
        // XXX needs to be fixed to dump all tables
        currentTable.dump();
    }
    
    public SymbolKind getKindByLexeme(String needle){
        // XXX needs to change to search multiple tables
        return(currentTable.getKindByLexeme(needle));
    }
    
    public Boolean varHasBeenDeclared(String needle){
         // XXX needs to change to search multiple tables
        return(currentTable.varHasBeenDeclared(needle));
    }
    
    public void newSymbolTableForNewContext(String tableName){
        // XXX needs massive rework
        currentTable = new SymbolTable(tableName);
    }
}
