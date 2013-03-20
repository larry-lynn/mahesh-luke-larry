package compiler;

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
        else if( (newNode.getLexeme().compareTo(payload.getLexeme() )) == 0){
            return false;
        }
        // case 3 - the new symbol is lexicographically lower
        //        - go deeper down the left hand side of the tree
        else if( (newNode.getLexeme().compareTo(payload.getLexeme() )) < 0){
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
    	ParserSymbol symbolType;
    	
	    if (left != null){
            left.traverse();
        }
        if (payload != null){
            symbolType = payload.getDataType();
            switch(symbolType){
            case MP_SYMBOL_PROCEDURE:

                StringBuilder argList = new StringBuilder();
                Procedure proc = (Procedure) payload;
                argList.append("(");
                Boolean first = true;
                for(Args a : proc.getArgs() ){
                    if(first){first = false;}
                    else{argList.append(", ");}
                    argList.append(a.getLexeme());
                }
                argList.append(")");
                outputLine = String.format("%-20s%-20s%-7s%s\n", payload.getLexeme(), payload.getDataType(), argList.toString(), "");
                
                break;
                
            default:
                outputLine = String.format("%-20s%-20s%-7s%s\n", payload.getLexeme(), payload.getDataType(), "", "");
                break;
            }
        	
            System.out.println(outputLine );
        }
        if (right != null){
            right.traverse();
	}
    } // end traverse

    public Symbol search(String lexeme){
        if(payload == null){
            return null;
        }
        else if(lexeme.equals( payload.getLexeme() ) ){
            return(payload);
        }
        else if( (lexeme.compareTo(payload.getLexeme() )) < 0){
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
	
}
