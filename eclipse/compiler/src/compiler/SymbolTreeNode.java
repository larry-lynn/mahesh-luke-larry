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
        StringBuilder argList = null;
        Procedure proc = null;
        Function func = null;
        Boolean first = true;

        if (left != null){
            left.traverse();
        }
        if (payload != null){
            symbolKind = payload.getKind();
            if(symbolKind == SymbolKind.MP_SYMBOL_PROCEDURE){
            	symbolType = null;
            }
            else{
            	SymbolWithType sType = (SymbolWithType) payload;
            	symbolType = sType.getType();
            }
            
            switch(symbolKind){
            case MP_SYMBOL_PROCEDURE:
                argList = new StringBuilder();
                proc = (Procedure) payload;
                argList.append("(");
                first = true;
                for(Args a : proc.getArgs() ){
                    if(first){first = false;}
                    else{argList.append(", ");}
                    argList.append(a.getLexeme());
                }
                argList.append(")");
                outputLine = String.format("%-20s%-20s%-20s%-7s%s\n", payload.getLexeme(), symbolKind, symbolType, argList.toString(), "");
                
                break;
            case MP_SYMBOL_FUNCTION:
                argList = new StringBuilder();
                func = (Function) payload;
                argList.append("(");
                first = true;
                for(Args a : func.getArgs() ){
                    if(first){first = false;}
                    else{argList.append(", ");}
                    argList.append(a.getLexeme());
                }
                argList.append(")");
                outputLine = String.format("%-20s%-20s%-20s%-7s%s\n", payload.getLexeme(), symbolKind, symbolType, argList.toString(), "");
                
                break;
                
            default:
                outputLine = String.format("%-20s%-20s%-7s%s\n", payload.getLexeme(), payload.getKind(), "", "");
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

    /*
    public Symbol iSearch(String lexeme){
        System.out.println("Searching for " + lexeme);
        
        String lexLower = lexeme.toLowerCase();
        if(payload == null){
            return null;
        }
        String payloadLex = payload.getLexeme();
        System.out.println("Current nod payload is: " + payloadLex);
        String payloadLexLower = payloadLex.toLowerCase();      
      

        if(lexLower.equals( payloadLexLower ) ){
            return(payload);
        }
        else if( (lexLower.compareTo( payloadLexLower )) < 0){
            if(left == null){
                return null;
            }
            return(left.iSearch( lexLower ));
        }
        else {
            if(right == null){
                return null;
            }
            return(right.iSearch( lexLower ));
        }
    }  // end search
    */
	
}
