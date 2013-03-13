package compiler;

public class SymbolTable {
    String name;
    SymbolTreeNode root;
    // class variables go here

    // constructor
    public SymbolTable(String programIdentifierRec){
        
        root = new SymbolTreeNode();
        name = programIdentifierRec;

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
    	String outputLine;
    	System.out.println("SYMBOL TABLE NAME: " + name);
    	outputLine = String.format("%-20s%-20s%-7s%s\n", "Symbol Name", "Sym. Type  ", "Res. 1", "Res. 2");
    	System.out.println(outputLine);
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
