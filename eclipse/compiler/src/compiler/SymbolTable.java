package compiler;

public class SymbolTable {
    String name;
    SymbolTreeNode root;
    // class variables go here

    // constructor
    public SymbolTable(Token programIdentifierRec){
        
        root = new SymbolTreeNode();
        name = programIdentifierRec.lexeme;

    }

    // methods    
    /*
    public Symbol search(){

    }

    public void list(){

    }
    */

    public boolean insert(Symbol newNode){
	boolean ret_val = root.insert(newNode);
        return ret_val;
    }

    public void dump(){
        root.traverse();
    }	
   
    public String lookup(String needle){
        Symbol possible_hit = root.search(needle);
        if(possible_hit != null){
            return possible_hit.getLexeme();
        }
        else{
            return("NOT FOUND");
        }
    }
}
