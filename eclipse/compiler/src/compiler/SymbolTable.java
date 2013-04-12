package compiler;

public class SymbolTable {
    String name;
    int symbolCount;
    SymbolTreeNode root;
    // class variables go here

    // constructor
    public SymbolTable(String programIdentifierRec){
        
        root = new SymbolTreeNode();
        name = programIdentifierRec;
        symbolCount = 0;

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
        if(ret_val){
	        symbolCount++;
        }
        return ret_val;
    }

    public void dump(){
    	String outputLine;
    	System.out.println("SYMBOL TABLE NAME: " + name);
        System.out.println("Symbol Count: " + symbolCount);
    	outputLine = String.format("%-20s%-20s%-20s%-7s%s\n", "Symbol lexeme", "Sym. Kind", "Data Type", "Extra", "Arguments");
    	System.out.print(outputLine);
        root.traverse();
    }	
   
    // not sure if this is the right way to do a lookup.  consider retiring
    public String lookup(String needle){
        Symbol possible_hit = root.search(needle);
        if(possible_hit != null){
            return possible_hit.getLexeme();
        }
        else{
            return("NOT FOUND");
        }
    }
    
    public Boolean varHasBeenDeclared(String needle){
        Symbol possible_hit = root.search(needle);
        if(possible_hit != null){
            return(true);
        }
        else{
            return(false);
        }
    }

    public SymbolKind getKindByLexeme(String needle){
        Symbol possible_hit;
        SymbolKind kind = null;

        possible_hit = root.search(needle);
        if(possible_hit != null){
            kind = possible_hit.getKind();
            return(kind);
        }
        else{
            return(null);
        }
    }

    
}
