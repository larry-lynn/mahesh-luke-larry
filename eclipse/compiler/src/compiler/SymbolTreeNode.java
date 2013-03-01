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
            if(left == null){
                left = new SymbolTreeNode();
            }
            return(left.insert(newNode));
        }

    }
	
}
