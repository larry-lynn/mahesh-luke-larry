package compiler;

public class StackTopRecord {
    SymbolType dataType;
    String variableLexeme;
    SymbolMode callTypeCompatibility;
    
    // for literals & expression results
    public StackTopRecord(SymbolType dType){
    	dataType = dType;
    	variableLexeme = "";
    	callTypeCompatibility = SymbolMode.MP_SYMBOL_VALUE;
    }
    
    // for variables
    public StackTopRecord(SymbolType dType, String varLex, SymbolMode compatMode){
    	dataType = dType;
    	variableLexeme = varLex;
    	callTypeCompatibility = compatMode;
    }
    
    public void invalidateForCallByRef(){
    	variableLexeme = "";
    	callTypeCompatibility = SymbolMode.MP_SYMBOL_VALUE;
    }
}
