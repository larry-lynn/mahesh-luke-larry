package compiler;

import java.util.ArrayList;

public class SymbolTreeNode {
    SymbolTreeNode left;
    SymbolTreeNode right;
    Symbol payload;

    // class variables go here

    // constructor
    public SymbolTreeNode(){
        left = null;
        right = null;
        payload = null;
    }


    // methods    
    public boolean insert(Symbol newNode){
        // case 1 - we're at a null node - insert
        if (payload == null){
            payload = newNode;
            return true;
        }
        // case 2 - matching lexems - collision
        else if( (newNode.getLexeme().compareToIgnoreCase(payload.getLexeme() )) == 0){
            return false;
        }
        // case 3 - the new symbol is lexicographically lower
        //        - go deeper down the left hand side of the tree
        else if( (newNode.getLexeme().compareToIgnoreCase(payload.getLexeme() )) < 0){
            if(left == null){
                left = new SymbolTreeNode();
            }
            return(left.insert(newNode));
        }
        // case 4 - the new symbol is lexicographically HIGHER
        //        - go deeper down the right hand side of the tree
        else{
            if(right == null){
                right = new SymbolTreeNode();
            }
            return(right.insert(newNode));
        }
    }  // end insert

    public void traverse(){
    	String outputLine;
    	SymbolKind symbolKind;
    	SymbolType symbolType;

        if (left != null){
            left.traverse();
        }
        if (payload != null){
            symbolKind = payload.getKind();
            if(symbolKind == SymbolKind.MP_SYMBOL_PROCEDURE){
            	symbolType = null;
            }
            else{
            	SymbolWithType swType = (SymbolWithType) payload;
            	symbolType = swType.getType();
            }
            
            switch(symbolKind){
            case MP_SYMBOL_PROCEDURE:
            case MP_SYMBOL_FUNCTION:
                outputLine = String.format("%-20s%-20s%-20s%-7s%s\n", payload.getLexeme(), symbolKind, symbolType, payload.getOffset(), Args.formatArglist(payload) );    
                break;
                
            default:
                outputLine = String.format("%-20s%-20s%-20s%-7s%s\n", payload.getLexeme(), symbolKind, symbolType, payload.getOffset(), "");
                break;
            }
        	
            System.out.print(outputLine );
        }
        if (right != null){
            right.traverse();
	    }
    } // end traverse

    public Symbol search(String lexeme){
        if(payload == null){
            return null;
        }
        else if(lexeme.equalsIgnoreCase( payload.getLexeme() ) ){
            return(payload);
        }
        else if( (lexeme.compareToIgnoreCase(payload.getLexeme() )) < 0){
            if(left == null){
                return null;
            }
            return(left.search(lexeme));
        }
        else {
            if(right == null){
                return null;
            }
            return(right.search(lexeme));
        }
    }  // end search
    
    public void traverseGenList(ArrayList<Symbol> TreeAsList){
        if (left != null){
            left.traverseGenList(TreeAsList);
        }
        if (payload != null){
            TreeAsList.add(payload);
        }
        if (right != null){
        	right.traverseGenList(TreeAsList);
        }
    }
	
}
