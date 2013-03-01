package compiler;

public class Var extends Symbol {
       private enum var_data_type;

       // Constructor if the input is not know
       public Var(){
       }
       
       // Constructor if the input is known
       public Var(enum input){
       	      this.var_data_type = input;
       }

       // Set the var_data_type
       public void setType(enum input){
       	      this.var_data_type = input;
       }

       // Get the var_data_type
       public enum getType(){
       	      return this.var_data_type;
       }
}