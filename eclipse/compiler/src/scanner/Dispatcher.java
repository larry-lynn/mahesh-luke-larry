package scanner;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;

public class Dispatcher {

	public enum State {
		q0, q1, q2, q3, q4, q5, q6, q7, q8, q9
	}

	char[] source_to_scan;
	int row;
	int column;
	int file_pointer;
	State dispatcherState;
	String fileAsString;

	// For the dispatcher, states are
	// q0 = normal scanning
	// q1 = consuming whitespace
	// q2 = consuming newline
	// q3 = consuming old style comment
	// q4 = consuming new style comment
	// q6 = error state - hit EOF while scanning a comment
	// q7 = found token
	// q8 = file pointer advanced till end of file
	// q9 = EOF processed and scanning complete

	// Constructor
	public Dispatcher() {
		source_to_scan = new char[0];
		row = 0;
		column = 0;
		file_pointer = 0;
		dispatcherState = State.q0;
	}

	public void openFile(String fileWithPath) throws IOException {
		/*
		 * adapted from
		 * http://stackoverflow.com/questions/326390/how-to-create-a
		 * -java-string-from-the-contents-of-a-file
		 */
		FileInputStream stream = new FileInputStream(new File(fileWithPath));
		try {
			FileChannel fc = stream.getChannel();
			MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc
					.size());
			/* Instead of using default, pass in a decoder. */
			fileAsString = Charset.defaultCharset().decode(bb).toString();
			// convert the contents of the file to an array of characters
			source_to_scan = fileAsString.toCharArray();

		} finally {
			stream.close();
		}
	}

	// Main method for scanning and retrieving tokens
	public Token getToken() {
		Token tok = null;

		while (dispatcherState != State.q7) {

			switch (dispatcherState) {

			case q0:
				// State q0: normal scanning
				switch (source_to_scan[file_pointer]) {
				// using fall through switching for multi-char matching

				// Dispatch to Number FSM
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
					tok = MPNumberLitFSM();
					dispatcherState = State.q7;
					break;

				// Dispatch to comma FSM
				case ',':
					tok = MPCommaFSM();
					dispatcherState = State.q7;
					break;

				// Dispatch to equal FSM
				case '=':
					tok = MPEqualFSM();
					dispatcherState = State.q7;
					break;

				// Dispatch to Comment Style 1 FSM
				case '{':
					tok = consumeCommentFSM();
					// tok should be null in a normal state,
					// run-on comment token on error
					break;

				// Dispatch to semi-colon FSM
				case ';':
					tok = MPSemiColonFSM();
					dispatcherState = State.q7;
					break;

				// Dispatch to Plus FSM
				case '+':
					tok = MPPlusFSM();
					dispatcherState = State.q7;
					break;

				// Dispatch to Minus FSM
				case '-':
					tok = MPMinusFSM();
					dispatcherState = State.q7;
					break;
				//
				case '<':
					tok = MPLtLeqNeq();
					dispatcherState = State.q7;
					break;

				// Dispatch to GreaterThan FSM
				case '>':
					tok = MPGtGeq();
					dispatcherState = State.q7;
					break;

				// Dispatch to Colon FSM
				case ':':
					tok = MPColonAssign();
					dispatcherState = State.q7;
					break;

				// Dispatch to String Literal FSM
				case '\'':
					tok = MPStringLitFSM();
					dispatcherState = State.q7;
					break;
					
				//New methods added by Luke today
				// Dispatch to Period FSM
				case '.':
					tok = MPPeriodFSM();
					dispatcherState = State.q7;
					break;
					
				// Dispatch to Left Paren FSM
				case '(':
					tok = MPLeftParenFSM();
					dispatcherState = State.q7;
					break;
					
				// Dispatch to Right Paren FSM
				case ')':
					tok = MPRightParenFSM();
					dispatcherState = State.q7;
					break;
					
				// Dispatch to Time FSM
				case '*':
					tok = MPTimesFSM();
					dispatcherState = State.q7;
					break;
					
				//Falling through switch statements to check for a-z,A-Z which will test for valid tokens
				case 'a':
				case 'b':
				case 'c':
				case 'd':
				case 'e':
				case 'f':
				case 'g':
				case 'h':
				case 'i':
				case 'j':
				case 'k':
				case 'l':
				case 'm':
				case 'n':
				case 'o':
				case 'p':
				case 'q':
				case 'r':
				case 's':
				case 't':
				case 'u':
				case 'v':
				case 'w':
				case 'x':
				case 'y':
				case 'z':
				case 'A':
				case 'B':
				case 'C':
				case 'D':
				case 'E':
				case 'F':
				case 'G':
				case 'H':
				case 'I':
				case 'J':
				case 'K':
				case 'L':
				case 'M':
				case 'N':
				case 'O':
				case 'P':
				case 'Q':
				case 'R':
				case 'S':
				case 'T':
				case 'U':
				case 'V':
				case 'W':
				case 'X':
				case 'Y':
				case 'Z':
				case '_':
					tok = MPIdentifierFSM();
					dispatcherState = State.q7;
					break;

				// using fall-through switching for matching multiple types of
				// whitespace
				case ' ':
				case '\t':
                                case '\r':
					dispatcherState = State.q1;
					break;

				case '\n':
					dispatcherState = State.q2;
					break;

				default:
					// XXX Fixme - this should be changed to handle scanning
					// errors
					// System.out.println("XXX reached a case reserved for future scanning errors");
					// System.out.println("Scanned character was: " +
					// source_to_scan[file_pointer]);

					// Unrecongnized Character
					tok = new Token("MP_ERROR", row, column, Character
							.toString(source_to_scan[file_pointer]));
					
					dispatcherState = State.q7;

					// update column & file pointer
					column = column + 1;
					file_pointer = file_pointer + 1;

					break;
				} // end token-scanning q0 state switch
				break; // end main scanning case

			case q1:
				// State q1: consuming whitespace
				column = column + 1;
				file_pointer = file_pointer + 1;
				dispatcherState = State.q0;
				if (file_pointer >= source_to_scan.length) {
					dispatcherState = State.q8;
				}
				break;
			case q2:
				// State q2: consuming newline
				column = 0;
				row = row + 1;
				file_pointer = file_pointer + 1;
				dispatcherState = State.q0;
				if (file_pointer >= source_to_scan.length) {
					dispatcherState = State.q8;
				}
				break;
			case q6:
				dispatcherState = State.q8;
				// alternate return path for MP_RUN_COMMENT
				return (tok);
			case q7:
				// the while terminator should intercept the flow of control
				// such that this is unreachable
				System.out
						.println("Reached dispactcher state q7 in switch! This should not happen!");
				break;
			case q8:
				// state q8: file pointer advanced till end of file
				tok = new Token("MP_EOF", row, column, null);
				dispatcherState = State.q9;
				// alternate return path for EOF to avoid resetting dispatcher
				// FSM
				// to scan state
				return (tok);
			case q9:
				// state q9: EOF processed and scanning complete
				// this is an ERROR STATE because getToken() should not be
				// called
				// after the dispatcher has reached the end of file
				tok = new Token("MP_EOF", row, column, null);
				System.out
						.println("Scanner Error: Attempted to get new token after EOF reached");
				// Should this terminate things?
				return (tok);
			default:
				System.out
						.println("Default case in dispatcher FSM should be unreachable!");
				break;

			} // end dispatcher state swtich
		} // end dispatcher while loop

		if (file_pointer < source_to_scan.length) {
			// we can keep scanning
			dispatcherState = State.q0;
		} else {
			// set dispatcher FSM to indicate EOF
			dispatcherState = State.q8;
		}

		return (tok);
	} // end get token

	public Token MPNumberLitFSM() {
		int peek = 0;
		int i = 0;
		State fsm_state = State.q0;
		Token tok;
		StringBuilder lex;
		String tokenType = "";

		while (fsm_state != State.q2) {
			switch (fsm_state) {
			case q0:
				// initial state
				switch (source_to_scan[file_pointer + peek]) {
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
				switch (source_to_scan[file_pointer + peek]) {
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
					if ((file_pointer + peek) >= source_to_scan.length) {
						// Terminate token FSM early if EOF reached
					        tokenType = "MP_INTEGER_LIT";
						fsm_state = State.q2;
					}
					break;
				case '.':
				        // Case to handle fixed or floating point numbers
					peek = peek + 1;
					if ((file_pointer + peek) >= source_to_scan.length) {
						// Terminate token FSM early if EOF reached, and return the number before current character as integer
					        peek = peek - 1;
						tokenType = "MP_INTEGER_LIT";
						fsm_state = State.q2;
					}
					else {
					    fsm_state = State.q3;
					}
					break;
 				default:
					// we've scanned another character, not a digit
				        tokenType = "MP_INTEGER_LIT";
					fsm_state = State.q2;
					break;
				} // end q1 inner switch
				break; // end q1 state case

			case q3:
      				switch (source_to_scan[file_pointer + peek]) {
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
					if ((file_pointer + peek) >= source_to_scan.length) {
						// Terminate token FSM early if EOF reached
					        tokenType = "MP_FIXED_LIT";
						fsm_state = State.q2;
					}
					else {
					    fsm_state = State.q4;
					}
					break;
 				default:
					// we've scanned another character, not a digit
				        // return the number 1 character before this character as integer
				        peek = peek - 1;
				        tokenType = "MP_INTEGER_LIT";
					fsm_state = State.q2;
					break;
				}
				break; // end q3 state case

			case q4:
				switch (source_to_scan[file_pointer + peek]) {
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
					if ((file_pointer + peek) >= source_to_scan.length) {
						// Terminate token FSM early if EOF reached
					        tokenType = "MP_FIXED_LIT";
					        fsm_state = State.q2;
					}
					break;
				case 'e':
				case 'E':
				        // Case to handle fixed or floating point numbers
					peek = peek + 1;
					if ((file_pointer + peek) >= source_to_scan.length) {
						// Terminate token FSM early if EOF reached, and return the number 1 place before current charatcer as fixed point number
					        peek = peek - 1;
						tokenType = "MP_FIXED_LIT";
						fsm_state = State.q2;
					}
					else {
					    fsm_state = State.q5;
					}
				    break;
 				default:
					// we've scanned another character, not a digit
				        // return the number 1 character before this character as fixed point number
				        tokenType = "MP_FIXED_LIT";
					fsm_state = State.q2;
					break;
				}
				break; // end q4 state here

			case q5:
				switch (source_to_scan[file_pointer + peek]) {
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
					if ((file_pointer + peek) >= source_to_scan.length) {
						// Terminate token FSM early if EOF reached
					        tokenType = "MP_FLOAT_LIT";
						fsm_state = State.q2;
					}
					else {
					       fsm_state = State.q6;
					}
				        break;
				case '+': 
				case '-':
					peek = peek + 1;
				        if (source_to_scan[file_pointer + peek] == '0' || 
					    source_to_scan[file_pointer + peek] == '1' || 
					    source_to_scan[file_pointer + peek] == '2' || 
					    source_to_scan[file_pointer + peek] == '3' || 
					    source_to_scan[file_pointer + peek] == '4' || 
					    source_to_scan[file_pointer + peek] == '5' || 
					    source_to_scan[file_pointer + peek] == '6' || 
					    source_to_scan[file_pointer + peek] == '7' || 
					    source_to_scan[file_pointer + peek] == '8' || 
					    source_to_scan[file_pointer + peek] == '9'
					    ){
					        fsm_state = State.q6;
					}
					else if ((file_pointer + peek) >= source_to_scan.length) {
						// Terminate token FSM early if EOF reached, and return the number 2 places before current character as fixed point number
					        peek = peek - 2;
						tokenType = "MP_FIXED_LIT";
						fsm_state = State.q2;
					}
					else {
						// Terminate token FSM early if EOF reached, and return the number 2 places before current character as fixed point number
					        peek = peek - 2;
						tokenType = "MP_FIXED_LIT";
						fsm_state = State.q2;
					}
					break;
				default:
					// we've scanned another character, not a digit
				        // return the number 1 character before this character as fixed point number
				        peek = peek - 1;
				        tokenType = "MP_FIXED_LIT";
					fsm_state = State.q2;
				        break;
				}
				break; //end q5 state here

			case q6:
				switch (source_to_scan[file_pointer + peek]) {
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
					if ((file_pointer + peek) >= source_to_scan.length) {
						// Terminate token FSM early if EOF reached
					        tokenType = "MP_FLOAT_LIT";
						fsm_state = State.q2;
					}
					break;
 				default:
					// we've scanned another character, not a digit
				        tokenType = "MP_FLOAT_LIT";
					fsm_state = State.q2;
					break;
				}			    
			        break; // end q6 state here

	      		default:
				// shouldn't ever get here
				System.exit(-2);

			} // end outer fsm switch

		} // end big while loop for fsm - q2 exit state reached
		lex = new StringBuilder(peek);
		for (i = 0; i < peek; ++i) {
			lex.append(source_to_scan[file_pointer + i]);
		}
		tok = new Token(tokenType, lex.toString());

		// update token with extra information
		tok.column_number = column;
		tok.line_number = row;

		// update column & file pointer
		column = column + peek;
		file_pointer = file_pointer + peek;

		return tok;

	} // end MPNumberLitFSM()

	public Token MPSemiColonFSM() {
		int peek = 0;
		State fsm_state = State.q0;
		Token tok;

		while (fsm_state != State.q2) {
			switch (fsm_state) {
			case q0:
				// initial state
				switch (source_to_scan[file_pointer + peek]) {
				case ';':
					fsm_state = State.q1;
					break;
				default:
					// shouldn't ever get here
					System.exit(-1);
				} // end q0 inner switch
				break; // end q0 state case
			case q1:
				switch (source_to_scan[file_pointer + peek]) {
				case ';':
					peek = peek + 1;
					fsm_state = State.q2;
					break;
				default:
					// shouldn't ever get here
					System.exit(-3);
				} // end q1 inner switch
				break; // end q1 state case
			default:
				// shouldn't ever get here
				System.exit(-2);
			} // end outer fsm switch
		} // end big while loop for fsm - q2 exit state reached

		tok = new Token("MP_SCOLON", ";");

		// update token with extra information
		tok.column_number = column;
		tok.line_number = row;

		// update column & file pointer
		column = column + peek;
		file_pointer = file_pointer + peek;

		return tok;

	} // end MPSemiColonFSM()

	public Token MPPlusFSM() {

		int peek = 0;
		State fsm_state = State.q0;
		Token tok;

		while (fsm_state != State.q2) {
			switch (fsm_state) {
			case q0:
				// initial state
				switch (source_to_scan[file_pointer + peek]) {
				case '+':
					fsm_state = State.q1;
					break;
				default:
					// shouldn't ever get here
					System.exit(-1);
				} // end q0 inner switch
				break; // end q0 state case
			case q1:
				switch (source_to_scan[file_pointer + peek]) {
				case '+':
					peek = peek + 1;
					fsm_state = State.q2;
					break;
				default:
					// shouldn't ever get here
					System.exit(-3);
				} // end q1 inner switch
				break; // end q1 state case
			default:
				// shouldn't ever get here
				System.exit(-2);
			} // end outer fsm switch
		} // end big while loop for fsm - q2 exit state reached

		tok = new Token("MP_PLUS", "+");

		// update token with extra information
		tok.column_number = column;
		tok.line_number = row;

		// update column & file pointer
		column = column + peek;
		file_pointer = file_pointer + peek;

		return tok;
	}

	public Token MPCommaFSM() {

		int peek = 0;
		State fsm_state = State.q0;
		Token tok;

		while (fsm_state != State.q2) {
			switch (fsm_state) {
			case q0:
				// initial state
				switch (source_to_scan[file_pointer + peek]) {
				case ',':
					fsm_state = State.q1;
					break;
				default:
					// shouldn't ever get here
					System.exit(-1);
				} // end q0 inner switch
				break; // end q0 state case
			case q1:
				switch (source_to_scan[file_pointer + peek]) {
				case ',':
					peek = peek + 1;
					fsm_state = State.q2;
					break;
				default:
					// shouldn't ever get here
					System.exit(-3);
				} // end q1 inner switch
				break; // end q1 state case
			default:
				// shouldn't ever get here
				System.exit(-2);
			} // end outer fsm switch
		} // end big while loop for fsm - q2 exit state reached

		tok = new Token("MP_COMMA", ",");

		// update token with extra information
		tok.column_number = column;
		tok.line_number = row;

		// update column & file pointer
		column = column + peek;
		file_pointer = file_pointer + peek;

		return tok;
	}

	public Token MPEqualFSM() {

		int peek = 0;
		State fsm_state = State.q0;
		Token tok;

		while (fsm_state != State.q2) {
			switch (fsm_state) {
			case q0:
				// initial state
				switch (source_to_scan[file_pointer + peek]) {
				case '=':
					fsm_state = State.q1;
					break;
				default:
					// shouldn't ever get here
					System.exit(-1);
				} // end q0 inner switch
				break; // end q0 state case
			case q1:
				switch (source_to_scan[file_pointer + peek]) {
				case '=':
					peek = peek + 1;
					fsm_state = State.q2;
					break;
				default:
					// shouldn't ever get here
					System.exit(-3);
				} // end q1 inner switch
				break; // end q1 state case
			default:
				// shouldn't ever get here
				System.exit(-2);
			} // end outer fsm switch
		} // end big while loop for fsm - q2 exit state reached

		tok = new Token("MP_EQUAL", "=");

		// update token with extra information
		tok.column_number = column;
		tok.line_number = row;

		// update column & file pointer
		column = column + peek;
		file_pointer = file_pointer + peek;

		return tok;
	}

	public Token MPMinusFSM() {

		int peek = 0;
		State fsm_state = State.q0;
		Token tok;

		while (fsm_state != State.q2) {
			switch (fsm_state) {
			case q0:
				// initial state
				switch (source_to_scan[file_pointer + peek]) {
				case '-':
					fsm_state = State.q1;
					break;
				default:
					// shouldn't ever get here
					System.exit(-1);
				} // end q0 inner switch
				break; // end q0 state case
			case q1:
				switch (source_to_scan[file_pointer + peek]) {
				case '-':
					peek = peek + 1;
					fsm_state = State.q2;
					break;
				default:
					// shouldn't ever get here
					System.exit(-3);
				} // end q1 inner switch
				break; // end q1 state case
			default:
				// shouldn't ever get here
				System.exit(-2);
			} // end outer fsm switch
		} // end big while loop for fsm - q2 exit state reached

		tok = new Token("MP_MINUS", "-");

		// update token with extra information
		tok.column_number = column;
		tok.line_number = row;

		// update column & file pointer
		column = column + peek;
		file_pointer = file_pointer + peek;

		return tok;
	}

	public Token MPLtLeqNeq() {

		int peek = 0;
		State fsm_state = State.q0;
		Token tok;
		tok = null;

		while (fsm_state != State.q7) {
			switch (fsm_state) {
			case q0:
				// initial state
				switch (source_to_scan[file_pointer + peek]) {
				case '<':
					fsm_state = State.q1;
					break;
				default:
					// shouldn't ever get here
					System.exit(-1);
				} // end q0 inner switch
				break; // end q0 state case
			// consume first character. check file pointer. possibly continue
			case q1:
				switch (source_to_scan[file_pointer + peek]) {
				case '<':
					peek = peek + 1;
					if ((file_pointer + peek) >= source_to_scan.length) {
						// Terminate token FSM early if EOF reached
						tok = new Token("MP_LTHAN", row, column, "<");
						fsm_state = State.q7;
					} else {
						fsm_state = State.q2;
					}
					break;
				default:
					// shouldn't ever get here
					System.exit(-3);
				} // end q1 inner switch
				break; // end q1 state case
			// found 1 character - try to find a larger token
			case q2:
				switch (source_to_scan[file_pointer + peek]) {
				case '=':
					peek = peek + 1;
					tok = new Token("MP_LEQUAL", row, column, "<=");
					fsm_state = State.q7;
					break;
				case '>':
					peek = peek + 1;
					tok = new Token("MP_NEQUAL", row, column, "<>");
					fsm_state = State.q7;
					break;
				default:
					// found another character, but not one that can complete a
					// 2 character token
					fsm_state = State.q7;
					tok = new Token("MP_LTHAN", row, column, "<");
					break;
				} // end q2 switch
				break;

			default:
				// shouldn't ever get here
				System.exit(-2);
			} // end outer fsm switch
		} // end big while loop for fsm - q7 exit state reached

		// update column & file pointer
		column = column + peek;
		file_pointer = file_pointer + peek;

		return (tok);
	} // end MPLtLeqNeq()

	public Token MPGtGeq() {

		int peek = 0;
		State fsm_state = State.q0;
		Token tok;
		tok = null;

		while (fsm_state != State.q7) {
			switch (fsm_state) {
			case q0:
				// initial state
				switch (source_to_scan[file_pointer + peek]) {
				case '>':
					fsm_state = State.q1;
					break;
				default:
					// shouldn't ever get here
					System.exit(-1);
				} // end q0 inner switch
				break; // end q0 state case
			// consume first character. check file pointer. possibly continue
			case q1:
				switch (source_to_scan[file_pointer + peek]) {
				case '>':
					peek = peek + 1;
					if ((file_pointer + peek) >= source_to_scan.length) {
						// Terminate token FSM early if EOF reached
						tok = new Token("MP_GTHAN", row, column, ">");
						fsm_state = State.q7;
					} else {
						fsm_state = State.q2;
					}
					break;
				default:
					// shouldn't ever get here
					System.exit(-3);
				} // end q1 inner switch
				break; // end q1 state case
			// found 1 character - try to find a larger token
			case q2:
				switch (source_to_scan[file_pointer + peek]) {
				case '=':
					peek = peek + 1;
					tok = new Token("MP_GEQUAL", row, column, ">=");
					fsm_state = State.q7;
					break;
				default:
					fsm_state = State.q7;
					tok = new Token("MP_GTHAN", row, column, ">");
					break;
				} // end q2 switch
				break;

			default:
				// shouldn't ever get here
				System.exit(-2);
			} // end outer fsm switch
		} // end big while loop for fsm - q7 exit state reached

		// update column & file pointer
		column = column + peek;
		file_pointer = file_pointer + peek;

		return (tok);
	} // end MPGtGeq()

	public Token MPColonAssign() {

		int peek = 0;
		State fsm_state = State.q0;
		Token tok;
		tok = null;

		while (fsm_state != State.q7) {
			switch (fsm_state) {
			case q0:
				// initial state
				switch (source_to_scan[file_pointer + peek]) {
				case ':':
					fsm_state = State.q1;
					break;
				default:
					// shouldn't ever get here
					System.exit(-1);
				} // end q0 inner switch
				break; // end q0 state case
			// consume first character. check file pointer. possibly continue
			case q1:
				switch (source_to_scan[file_pointer + peek]) {
				case ':':
					peek = peek + 1;
					if ((file_pointer + peek) >= source_to_scan.length) {
						// Terminate token FSM early if EOF reached
						tok = new Token("MP_COLON", row, column, ":");
						fsm_state = State.q7;
					} else {
						fsm_state = State.q2;
					}
					break;
				default:
					// shouldn't ever get here
					System.exit(-3);
				} // end q1 inner switch
				break; // end q1 state case
			// found 1 character - try to find a larger token
			case q2:
				switch (source_to_scan[file_pointer + peek]) {
				case '=':
					peek = peek + 1;
					tok = new Token("MP_ASSIGN", row, column, ":=");
					fsm_state = State.q7;
					break;
				default:
					fsm_state = State.q7;
					tok = new Token("MP_COLON", row, column, ":");
					break;
				} // end q2 switch
				break;

			default:
				// shouldn't ever get here
				System.exit(-2);
			} // end outer fsm switch
		} // end big while loop for fsm - q7 exit state reached

		// update column & file pointer
		column = column + peek;
		file_pointer = file_pointer + peek;

		return (tok);
	} // end MPColonAssign()

	public Token MPStringLitFSM() {
		int peek = 0;
		int longPeek = 0;
		State fsm_state = State.q0;
		Token tok;
		StringBuilder lex = new StringBuilder("");
		StringBuilder bestLexSoFar = new StringBuilder("");
		tok = null; // XXX fixme
		while (fsm_state != State.q7) {
			switch (fsm_state) {
			case q0:
				// initial state
				switch (source_to_scan[file_pointer + peek]) {
				case '\'':
					fsm_state = State.q1;
					break;
				default:
					// shouldn't ever get here
					System.exit(-1);
				} // end q0 inner switch
				break; // end q0 state case
			case q1:
				// consume opening apostrophe
				switch (source_to_scan[file_pointer + peek]) {
				case '\'':
					peek = peek + 1;
					if ((file_pointer + peek) >= source_to_scan.length) {
						// Terminate token FSM early if EOF reached
						fsm_state = State.q9;
					} else {
						fsm_state = State.q2;
					}
					break;
				default:
					// shouldn't ever get here
					System.exit(-3);
				} // end q1 inner switch
				break; // end q1 state case
			case q2:
				// normal reading of string lit constant
				switch (source_to_scan[file_pointer + peek]) {
				case '\n':
					// newline illegal in micro-pascal string
					// Terminate token FSM early if EOF reached
					fsm_state = State.q9;
					break;
				case '\'':
					// found string terminator - haven't determined if it is an
					// escape character
					peek = peek + 1;
					if ((file_pointer + peek) >= source_to_scan.length) {
						bestLexSoFar = lex;
						fsm_state = State.q7;
					} else {
						fsm_state = State.q3;
					}
					break;
				default:
					// found a non EOF, non EOL, non apostrophe - normal string
					// reading
					lex.append(source_to_scan[file_pointer + peek]);
					peek = peek + 1;
					if ((file_pointer + peek) >= source_to_scan.length) {
						// Terminate token FSM early if EOF reached
						fsm_state = State.q9;
					}
					break;

				} // end q2 switch
				break; // end outer q2 case
			case q3:
				// Found this pattern '*' -- don't know if the 2nd apostrophe is
				// an escape char
				// fsm_state = State.q7;
				longPeek = peek;
				// bestLexSoFar = lex;
				bestLexSoFar = new StringBuilder(lex.toString());

				switch (source_to_scan[file_pointer + longPeek]) {
				case '\'':
					// found an escaped apostrophe - map to apostrophe literal
					longPeek = longPeek + 1;
					lex.append('\'');
					if ((file_pointer + longPeek) >= source_to_scan.length) {
						// case 'a''<eof> -- accept 'a'
						// throw away longPeek, back up 1
						fsm_state = State.q7;
					}
					fsm_state = State.q4;
					break;
				default:
					// if we scanned anything other than 'a', not 'a'' then end
					// the string
					fsm_state = State.q7;
					break;
				} // end inner q3 switch
				break; // end q3 case
			case q4:
				// found 'a''b... continue scanning
				// reading of string lit constant after escape seq
				switch (source_to_scan[file_pointer + longPeek]) {
				case '\n':
					// found 'a''bc<eol> - abandon longPeek and back up to 'a'
					fsm_state = State.q7;
					break;
				case '\'':
					// case 'a''b' -- accept state, but don't necessarily stop
					// scanning
					// save longPeek
					longPeek = longPeek + 1;
					peek = longPeek;
					// bestLexSoFar = lex;
					bestLexSoFar = new StringBuilder(lex.toString());
					if ((file_pointer + peek) >= source_to_scan.length) {
						// case 'a''b'<eof>
						fsm_state = State.q7;
					} else {
						// case 'a''b' . . . something (could continue)
						fsm_state = State.q3;
					}
					break;
				default:
					// found a non EOF, non EOL, non apostrophe - normal string
					// reading
					lex.append(source_to_scan[file_pointer + longPeek]);
					longPeek = longPeek + 1;
					if ((file_pointer + longPeek) >= source_to_scan.length) {
						// case 'a''bcd<eof> - abandon longPeek and back up to
						// 'a'
						fsm_state = State.q7;
					}
					break;

				} // end q4 switch
				break; // end outer q4 case

			case q9:
				// error case - reached EOF or EOL while scanning string
				tok = new Token("MP_RUN_STRING", row, column, null);
				column = column + peek;
				file_pointer = file_pointer + peek;
				return (tok);
			default:
				// shouldn't ever get here
				System.exit(-2);
			} // end outer fsm switch

		} // end FSM while loop

		tok = new Token("MP_STRING_LIT", row, column, bestLexSoFar.toString());

		// update column & file pointer
		column = column + peek;
		file_pointer = file_pointer + peek;

		return (tok);
	}

	public Token consumeCommentFSM() {
		State commentState = State.q0;
		// Create a placeholder token that stores original row and column
		// in case we encounter a run-on comment
		Token tok = new Token("MP_RUN_COMMENT", row, column, null);

		while (commentState != State.q8) {
			switch (commentState) {
			case q0:
				switch (source_to_scan[file_pointer]) {
				case '{':
					file_pointer = file_pointer + 1;
					column = column + 1;
					commentState = State.q1;
					break;
				default:
					System.out.println("Comment unexpected error state1");
					break;
				}
				break; // end q0

			case q1:
				// skipping everything between '{' and '}'
				switch (source_to_scan[file_pointer]) {
				case '{':
					System.out
							.println("Scanner Warning: found { embedded in comment.  Possibly missing }?");
					file_pointer = file_pointer + 1;
					column = column + 1;
					if (file_pointer >= source_to_scan.length) {
						commentState = State.q9;
					}
					break;
				case '\n':
					file_pointer = file_pointer + 1;
					column = 0;
					row = row + 1;
					if (file_pointer >= source_to_scan.length) {
						commentState = State.q9;
					}
					break;
				case '}':
					file_pointer = file_pointer + 1;
					column = column + 1;
					commentState = State.q8;
					break;
				default:
					// scanned anything else
					file_pointer = file_pointer + 1;
					column = column + 1;
					if (file_pointer >= source_to_scan.length) {
						commentState = State.q9;
					}
					break;
				}
				break; // end q1
			case q8:
				System.out.println("Reached supposadly unreachable q8 case.");
				break;
			case q9:
				// runaway comment -- comment ran to EOF
				dispatcherState = State.q6;
				return (tok);
			default:
				System.out.println("Comment unexpected error state 3");
				break;
			} // end state switch
		} // end comment state while loop
		// normal termination - no run-on comment. null out token
		tok = null;
		return (tok);

	}
	
	//Method: 		Determine Period
	//Input:  		Takes the current location of file pointer 
	//Output: 		Returns a Token containing the information
	//Procedure:	Determines whether or not the token is a period?
	public Token MPPeriodFSM()
	{
		//Token coin that will be returned
		Token coin = new Token("MP_PERIOD",".");
		//State object to determine the "."
		State p_fsm = State.q0;
		//int peek to look ahead of the file pointer
		int peek = 0;
		
		//While check
		while(p_fsm != State.q1)
		{
			if(source_to_scan.length > peek)
			{
				char ch = source_to_scan[file_pointer + peek];
				switch(ch)
				{
					case '.':
						p_fsm = State.q1;
						peek += 1;
						break;
					case ' ':
						peek+=1;
						break;
					default:
						System.exit(-1);
						
				}
			}
			//we somehow got the end and should exit the system
			else
			{
				System.exit(-1);
			}
		}
		
		//update the coin with information
		coin.column_number = column + peek;
		coin.line_number = row;
		
		//update column & file pointer
		column += peek;
		file_pointer += peek;
		
		return coin;
	}
	
	//Method:		Determine Left Paren
	//Input:		Takes the current location of the file pointer
	//Output: 		Returns a Token containing the information
	//Procedure:	Determines whether the current pointer of the file is looking at a "("
	public Token MPLeftParenFSM()
	{
		//Token coin that will be returned with the information
		Token coin = new Token("MP_LPAREN","(");
		//State object for the left paren
		State lp_fsm = State.q0;
		//Int peek to look ahead of the file pointer
		int peek = 0;
		
		//While checker
		while(lp_fsm != State.q1)
		{
			char ch = source_to_scan[file_pointer + peek];
			if (source_to_scan.length > peek)
			{
				switch(ch)
				{
					case '(':
						lp_fsm = State.q1;
						peek += 1;
						break;
					case ' ':
						peek += 1;
						break;
					default:
						System.exit(-1);
				}
			}
			//this would be an exit state
			else
			{
				System.exit(-1);
			}
		}
		
		//update coin information
		coin.column_number = column + peek;
		coin.line_number = row;
		
		//update the column and file_pointer information
		column += peek;
		file_pointer += peek;
		
		return coin;
	}
	
	//Method:		Determine Right Paren
	//Input: 		Takes the current location of the file pointer
	//Output:		Returns a Token containing the information
	//Procedure:	Determines whether the current pointer of the file is looking at a ")"
	public Token MPRightParenFSM()
	{
		//Token coin that will be returned with the information
		Token coin = new Token("MP_RPAREN",")");
		//State object to determine the state
		State rp_fsm = State.q0;
		//Int object peek to look ahead of the file pointer
		int peek = 0;
		
		while(rp_fsm != State.q1)
		{
			char ch = source_to_scan[file_pointer + peek];
			if(source_to_scan.length > peek)
			{
				switch(ch)
				{
					case ')':
						rp_fsm = State.q1;
						peek += 1;
						break;
					case ' ':
						peek += 1;
						break;
					default:
						System.exit(-1);
				}
			}
			else
			{
				System.exit(-1);
			}
		}
		
		//update coin information
		coin.column_number = column + peek;
		coin.line_number = row;
		
		//update the file_pointer & column
		file_pointer += peek;
		column += peek;
		return coin;
	}
	
	//Method:		Determine Times
	//Input:		Takes the current location of the file pointer
	//Output:		Returns a Token containing the information
	//Procedure:	Determines whether the current pointer of the file is looking at a "*"
	public Token MPTimesFSM()
	{
		//Token coin containing the information
		Token coin = new Token("MP_TIMES","*");
		//State object for the times fsm
		State t_fsm = State.q0;
		//Int object peek to look ahead of the file pointer
		int peek = 0;
		
		//while checker
		while(t_fsm != State.q1)
		{
			char ch = source_to_scan[file_pointer + peek];
			if(source_to_scan.length > peek)
			{
				switch(ch)
				{
					case '*':
						t_fsm = State.q1;
						peek += 1;
						break;
					case ' ':
						peek += 1;
						break;
					default:
						System.exit(-1);
				}
			}
		}
		
		//update the coin information
		coin.column_number = column + peek;
		coin.line_number = row;
		//update the file_pointer & column
		column += peek;
		file_pointer += peek;
		return coin;
	}
	//Method:		Determine Identifier
	//Input: 		Takes the current pointer of the file 
	//Output:		Returns a Token containing the information
	//Procedure:	Determines whether or not the string obtained is a valid identifier following
	//				The syntax of (letter | "_"(letter | digit)) {["_"](letter | digit)}
	
	public Token MPIdentifierFSM()
	{
		//String built for the identifier
		StringBuilder id = new StringBuilder();
		//Token coin that will be returned
		Token coin = new Token("MP_Identifier",id.toString());
		//State object for the ID fsm
		State id_fsm = State.q0;
		//int peek to look ahead of the file pointer
		int peek = 0;
		
		//Staying in the loop until we reach the done state
		while( id_fsm != State.q3)
		{
			char ch = source_to_scan[file_pointer + peek];
			switch(id_fsm)
			{
				//inital state we enter in on
				case q0:
					//add the first char we get onto the string
					id.append(ch);
					//increment peek to look at the next char
					peek += 1;
					//check to make sure we won't run off the file
					if(source_to_scan.length > file_pointer + peek)
					{
						//get the next char in the source to check
						ch = source_to_scan[file_pointer + peek];
						//if statement to check for 0-9,A-Z,a-z respectively.
						if( (ch >= 48 && ch <= 57) || (ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122) )
						{
							//change to accept state as we are valid right now
							id_fsm = State.q1;
							break;
						}
						else if(ch == '_')
						{
							//change to state where we are expecting another input
							id_fsm = State.q2;
							break;
						}
						else
						{
							//we have reached a state where we scanned something not valid in the ID syntax.
							id_fsm = State.q3;
							break;
						}
					}
					else
					{
						id_fsm = State.q3;
						break;
					}
				//state q1 is an accept state
				case q1:
					//add the char we are pointing at
					id.append(ch);
					//increment peek to look at the next char
					peek += 1;
					//check to make sure we don't run off the file
					if(source_to_scan.length > file_pointer + peek)
					{
						//get the next char in the source to check
						ch = source_to_scan[file_pointer + peek];
						//if statement to check for 0-9,A-Z,a-z respectively.
						if( (ch >= 48 && ch <= 57) || (ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122) )
						{
							//change to accept state as we are valid right now
							id_fsm = State.q1;
							break;
						}
						else if(ch == '_')
						{
							//change to state where we are expecting another input
							id_fsm = State.q2;
							break;
						}
						else
						{
							//we have reached a state where we scanned something not valid in the ID syntax.
							id_fsm = State.q3;
							break;
						}
						
					}
					else
					{
						id_fsm = State.q3;
						break;
					}
				//state q2 is a reject state where we just read an underscore
				case q2:
					//check to see if we have the double underscore case
					if(id.charAt(peek - 1) == '_' && ch == '_')
					{
						id_fsm = State.q3;
						break;
					}
					//add the char we are pointer at
					id.append(ch);
					//increment peek to look at the next char
					peek += 1;
					//check to make sure we don't run off the file
					if(source_to_scan.length > file_pointer + peek)
					{
						//get the next char in the source to check
						ch = source_to_scan[file_pointer + peek];
						//if statement to check for 0-9,A-Z,a-z respectively.
						if( (ch >= 48 && ch <= 57) || (ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122) )
						{
							//change to accept state as we are valid right now
							id_fsm = State.q1;
							break;
						}
						else if(ch == '_')
						{
							//change to state where we got invalid input and also strip last off
							id.deleteCharAt(peek - 1);
							peek -= 1;
							id_fsm = State.q3;
							break;
						}
						else
						{
							//we have reached a state where we scanned something not valid in the ID syntax.
							id_fsm = State.q3;
							break;
						}
						
					}
					else
					{
						id.deleteCharAt(peek - 1);
						peek -= 1;
						id_fsm = State.q3;
						break;
					}
			} //end switch statement
		} //end while loop
		
		//update the coin with information
		coin.column_number = column + peek;
		coin.line_number = row;
		coin.lexeme = id.toString();
		
		//update column & file pointer
		column += peek;
		file_pointer += peek;
		
		return coin;
	}

}
