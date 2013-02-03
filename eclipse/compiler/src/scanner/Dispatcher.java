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
					        fsm_state = State.q6;
					        break;
					    default:
						if ((file_pointer + peek) >= source_to_scan.length) {
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
		tok = null; 
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
	
	public Token MPIdentifierFSM(){
		StringBuilder idLexeme = new StringBuilder();
		StringBuilder testLongerLexeme = new StringBuilder();
		Token tok = new Token("MP_IDENTIFIER", row, column, "");
		State fsm_state = State.q0;
		int peek = 0;
		int peekFurther = 0;
		int arrayPointer = 0;

		//Hash table for reserved words
		String[][] reservedWords = new String[23][2];
		    reservedWords[0][0] = "and";
		    reservedWords[0][1] = "MP_AND";
		    reservedWords[1][0] = "begin";
		    reservedWords[1][1] = "MP_BEGIN";
		    reservedWords[2][0] = "div";
		    reservedWords[2][1] = "MP_DIV";
      		    reservedWords[3][0] = "do";
		    reservedWords[3][1] = "MP_DO";
		    reservedWords[4][0] = "downto";
		    reservedWords[4][1] = "MP_DOWNTO";
		    reservedWords[5][0] = "else";
		    reservedWords[5][1] = "MP_ELSE";
		    reservedWords[6][0] = "end";
		    reservedWords[6][1] = "MP_END";
		    reservedWords[7][0] = "for";
		    reservedWords[7][1] = "MP_FOR";
		    reservedWords[8][0] = "function";
		    reservedWords[8][1] = "MP_FUNCTION";
		    reservedWords[9][0] = "if";
		    reservedWords[9][1] = "MP_IF";
		    reservedWords[10][0] = "mod";
		    reservedWords[10][1] = "MP_MOD";
		    reservedWords[11][0] = "not";
		    reservedWords[11][1] = "MP_NOT";
		    reservedWords[12][0] = "or";
		    reservedWords[12][1] = "MP_OR";
		    reservedWords[13][0] = "procedure";
		    reservedWords[13][1] = "MP_PROCEDURE";
		    reservedWords[14][0] = "program";
		    reservedWords[14][1] = "MP_PROGRAM";
		    reservedWords[15][0] = "read";
		    reservedWords[15][1] = "MP_READ";
		    reservedWords[16][0] = "repeat";
		    reservedWords[16][1] = "MP_REPEAT";
		    reservedWords[17][0] = "then";
		    reservedWords[17][1] = "MP_THEN";
		    reservedWords[18][0] = "to";
		    reservedWords[18][1] = "MP_TO";
		    reservedWords[19][0] = "until";
		    reservedWords[19][1] = "MP_UNTIL";
		    reservedWords[20][0] = "var";
		    reservedWords[20][1] = "MP_VAR";
		    reservedWords[21][0] = "while";
		    reservedWords[21][1] = "MP_WHILE";
		    reservedWords[22][0] = "write";
		    reservedWords[22][1] = "MP_WRITE";
		
		
		//Staying in the loop until we reach the done state
		while( fsm_state != State.q7){
            switch(fsm_state){
            // initial state - letter or underscore
            case q0:
                switch(source_to_scan[file_pointer + peek]){
                // use fallthrough logic to match all letters
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
    				idLexeme.append(source_to_scan[file_pointer + peek]);
					tok.lexeme = idLexeme.toString();
				peek = peek + 1;
					if ((file_pointer + peek) >= source_to_scan.length) {
						// early bailout for a<eof>
						fsm_state = State.q7;
					} else {
						fsm_state = State.q2;
					}
					break;
				// identifier starts with '_'
    			case '_':
    				idLexeme.append(source_to_scan[file_pointer + peek]);
    				tok.lexeme = idLexeme.toString();
       				peek = peek + 1;
					if ((file_pointer + peek) >= source_to_scan.length) {
						// early bailout for '_<eof>'
						tok.token_name = "MP_ERROR";
						fsm_state = State.q7;
					} else {
						fsm_state = State.q1;
					}
					break;
				default:
					// Shouldn't be reachable -- dispatcher problem?
					System.exit(-2);
                }  // end q0 case
                break;
                
            // id starts with '_'
            case q1:
            	switch(source_to_scan[file_pointer + peek]){
                // use fall-through logic to match all letters and numbers
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
    				idLexeme.append(source_to_scan[file_pointer + peek]);
					tok.lexeme = idLexeme.toString();
    				peek = peek + 1;
					if ((file_pointer + peek) >= source_to_scan.length) {
						// early bailout for '_a<eof>'
						fsm_state = State.q7;
					} else {
						fsm_state = State.q2;
					}	
					break;
    			default:
    				// error case for '_+' or '__'
					tok = new Token("MP_ERROR", row, column, idLexeme.toString() );
					fsm_state = State.q7;
					break;
            	} // end q1 case
            	break;
            	
            case q2:
            	// Once we get to q2, we know we are going to return and identifier
            	switch(source_to_scan[file_pointer + peek]){
                // use fallthrough logic to match all letters and numbers
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
    				idLexeme.append(source_to_scan[file_pointer + peek]);
    				tok.lexeme = idLexeme.toString();
    				peek = peek + 1;
					if ((file_pointer + peek) >= source_to_scan.length) {
						fsm_state = State.q7;
					} 
					// if we haven't reached EOF, continue scanning in state q2
					break;
    			case '_':
    				// found 'aaa_'
    				peekFurther = peek;
    				peekFurther = peekFurther + 1;
					if ((file_pointer + peekFurther) >= source_to_scan.length) {
						// accept 'aaa' leave '_<eof>' to produce error next iteration 
	    				tok.lexeme = idLexeme.toString();
						fsm_state = State.q7;
					} 
					else{
						testLongerLexeme = new StringBuilder(idLexeme);
						// XXX hacking
						testLongerLexeme.append(source_to_scan[file_pointer + peek]);
						fsm_state = State.q3;
					}
    			    break;
				default:
					// case 'aa_b+' -- accept 'aa_b', leave '+ for next iteration
					fsm_state = State.q7;
            	} // end q2 case 
            	break;
            	
            case q3:
            	// scanned 'ab_' -- unsure if identifier continues
            	switch(source_to_scan[file_pointer + peekFurther]){
                // use fallthrough logic to match all letters and numbers
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
					testLongerLexeme.append(source_to_scan[file_pointer + peekFurther]);
					peek = peekFurther;
					idLexeme = new StringBuilder(testLongerLexeme);
    				tok.lexeme = idLexeme.toString();
    				peek = peek + 1;
					if ((file_pointer + peek) >= source_to_scan.length) {
						fsm_state = State.q7;
					}
					else{
					    fsm_state = State.q2;
					}
					// if we haven't reached EOF, continue scanning in state q2
    				break;
    			default:
    				// case 'abc_*' -- need to back up, return 'abc', leve '_' for next iteration
    				// need to back up here
    				peekFurther = 0;
    				testLongerLexeme = null;
    				// XXX hacking 2
    				// idLexeme.append(source_to_scan[file_pointer + peek]);
    				tok.lexeme = idLexeme.toString();
    				fsm_state = State.q7;
    				
    				break;
            	} // end q3 switch
            break;
            	
            // default for main FSM switch
            default:
				// shouldn't ever get here
            	System.out.println("in id state default");
				System.exit(-1);

            } // end main FSM state switch
		} //end FSM state loop
		
		//update column & file pointer
		column = column + peek;
		file_pointer = file_pointer +  peek;

		for(arrayPointer = 0; arrayPointer < 23; arrayPointer++){
		    if(tok.lexeme.toLowerCase().equals(reservedWords[arrayPointer][0])){
			tok.token_name = reservedWords[arrayPointer][1];
			break;
		    }
		}
		
		return(tok);
	}

}
