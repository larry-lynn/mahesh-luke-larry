package compiler;

import java.util.ArrayList;

public class SymbolTable {
    String name;
    int symbolCount;
    int depth;
    int offsetRelativeToDepth;
    SymbolTreeNode root;
    // class variables go here

    // constructor
    public SymbolTable(String programIdentifierRec, int depthVal){
        
        root = new SymbolTreeNode();
        name = programIdentifierRec;
        symbolCount = 0;
        offsetRelativeToDepth = 0;
        depth = depthVal;

    }

    // methods    

    public boolean insert(Symbol newNode){
        newNode.offset = genOffsetString();
	    boolean ret_val = root.insert(newNode);
        if(ret_val){
	        symbolCount++;
	        offsetRelativeToDepth++;
        }
        return ret_val;
    }

    public void dump(){
    	String outputLine;
    	System.out.println("SYMBOL TABLE NAME: " + name);
        System.out.println("Symbol Count: " + symbolCount);
    	outputLine = String.format("%-20s%-20s%-20s%-7s%s\n", "Symbol lexeme", "Sym. Kind", "Data Type", "Offset", "Args [lex:type:mode]");
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
    
    public Symbol fetchSymbolByLexeme(String needle){
        Symbol possible_hit;
        possible_hit = root.search(needle);
        if(possible_hit != null){
            return(possible_hit);
        }
        else{
            return(null);
        }
    }
    
    public String genOffsetString(){
        StringBuilder offsetString = new StringBuilder();
        offsetString.append(offsetRelativeToDepth);
        offsetString.append("(");
        offsetString.append("D");
        offsetString.append(depth);
        offsetString.append(")");
        return(offsetString.toString());
    }

    public String genDepthString(){
        StringBuilder depthString = new StringBuilder();
        depthString.append("D");
        depthString.append(depth);
        return(depthString.toString());
    }
    
    public ArrayList<Symbol> toArrayList(){
        ArrayList<Symbol> TreeAsList = new ArrayList<Symbol>();	
        root.traverseGenList(TreeAsList);
        return(TreeAsList);
    }
    
    public int getSymbolCount(){
    	return(symbolCount);
    }
    
}
