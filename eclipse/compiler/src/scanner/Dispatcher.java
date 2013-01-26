package scanner;

public class Dispatcher {
  
    public enum State { q0, q1, q2, q3, q4, q5, q6 }

    char[] source_to_scan;
    int row;
    int column;
    int file_pointer;

    //Constructor
    public Dispatcher(char[] file_data_from_driver){
        source_to_scan = file_data_from_driver;
        row = 0;
        column = 0;
        file_pointer = 0;
    }

    public Token getToken(){
        Token tok = null;

        // XXX EOF detection needs an FSM
        if( file_pointer >= source_to_scan.length ){
            return(null);
        }
        // XXX whitespce skipping should probably be done with an FSM in the dispatcher
        while( (source_to_scan[file_pointer] == ' ') || (source_to_scan[file_pointer] == '\n') ){
            if(source_to_scan[file_pointer] == ' '){
                // skip white space     
                column = column + 1;
                file_pointer = file_pointer + 1;
		if( file_pointer >= source_to_scan.length ){
		    return(null);
		}
            }
            else{
		// special white space skipping for a newline char
		column = 0;
		row = row + 1;
		file_pointer = file_pointer + 1;                                             
		if( file_pointer >= source_to_scan.length ){
		    return(null);
		}
            }
        }

        switch(source_to_scan[file_pointer]){
          case '0':
          case '1':
          case '2':
          case '3':
          case '4':
          case '5':
          case '6':
          case '7':
          case '8':
          case '9':
                    tok = MPIntegerLitFSM();
                    break;
	  /*
          case ' ':
              System.out.println("in the whitespace case");
              // skip white space
              column = column + 1;
              file_pointer = file_pointer + 1;
              break;
          case '\n':
              // special white space skipping for a newline char
              column = 0;
              row = row + 1;
              file_pointer = file_pointer + 1;
              System.out.println("in the newline case");
              break;
	  */
          default:
                    tok = null;
                    break;
        }


        return(tok);
        
    } // end get token

    public Token MPIntegerLitFSM(){
        int peek = 0;
        int i = 0;
        State fsm_state = State.q0;
        Token tok;
        StringBuilder lex;

        while (fsm_state != State.q2){
            switch(fsm_state){
                case q0:
                    //initial state
                    switch(source_to_scan[file_pointer + peek]){
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                            fsm_state = State.q1;
                            break;
                        default: 
                            // shouldn't ever get here
                            System.exit(-1);
                    } // end q0 inner switch 
                    break; // end q0 state case
                case q1:
                    switch(source_to_scan[file_pointer + peek]){
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                            peek = peek + 1;
                            break;
                        default: 
                            // we've scanned another character, not a digit
                            //peek = peek + 1;
                            fsm_state = State.q2;
                            break;
                    } // end q1 inner switch
                    break; // end q1 state case
                default:
                    // shouldn't ever get here
                    System.exit(-2);
            

            } // end outer fsm switch

        } // end big while loop for fsm - q2 exit state reached
        lex = new StringBuilder(peek);
        for( i = 0; i < peek ; ++i){
            lex.append(source_to_scan[file_pointer + i]);       
        }
        tok = new Token("MP_INTEGER_LIT", lex.toString() );

        // update token with extra information
        tok.column_number = column;
        tok.line_number = row;

        //update column & file pointer
        column = column + peek;
        file_pointer = file_pointer + peek;

        return tok;

    } // end MPIntegerLitFSM()	
	
}
