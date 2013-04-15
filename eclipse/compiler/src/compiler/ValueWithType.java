package compiler;

public class ValueWithType {
    Object value;
    SymbolType type;
    
    public ValueWithType(){
        value = null;
        type = null;
    }
    
    public ValueWithType(SymbolType newType, Object newValue){
        value = null;
        type = null;
    }
    
    public SymbolType getType(){
        return(type);
    }
    
    public void setType(SymbolType newType){
        type = newType;
    }
    
    public Object getValue(){
        return(value);
    }
    
    public void setValue(Object newVal){
        value = newVal;
    }
}
