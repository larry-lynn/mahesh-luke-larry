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
					tok = MPIntegerLitFSM();
					dispatcherState = State.q7;
					break;
					
				// Dispatch to Comment Style 1 FSM
				case '{':
					consumeComment1();
					break;
				// using fall-through switching for matching mutiple types of
				// whitespace
					
				// Dispatch to semi-colon FSM
				case ';':
				    tok = MPSemiColonFSM();
					dispatcherState = State.q7;
				    break;
				    
				case ' ':
				case '\t':
					dispatcherState = State.q1;
					break;
				case '\n':
					dispatcherState = State.q2;
				default:
					// XXX Fixme - this should be changed to handle scanning errors
					tok = null;
					break;
				} // end inndf q0 state switch
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
			case q7:
				// the while terminator should intercept the flow of control
				// such that this is unreachable
				System.out.println("Reached dispactcher state q7 in switch! This should not happen!");
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
				System.out.println("Default case in dispatcher FSM should be unreachable!");
				break;

			} // end dispatcher state swtich
		} // end dispatcher while loop

		// Reset dispatcher FSM for more scanning
		dispatcherState = State.q0;
		return (tok);

	} // end get token

	public Token MPIntegerLitFSM() {
		int peek = 0;
		int i = 0;
		State fsm_state = State.q0;
		Token tok;
		StringBuilder lex;

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
					break;
				default:
					// we've scanned another character, not a digit
					// peek = peek + 1;
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
		for (i = 0; i < peek; ++i) {
			lex.append(source_to_scan[file_pointer + i]);
		}
		tok = new Token("MP_INTEGER_LIT", lex.toString());

		// update token with extra information
		tok.column_number = column;
		tok.line_number = row;

		// update column & file pointer
		column = column + peek;
		file_pointer = file_pointer + peek;

		return tok;

	} // end MPIntegerLitFSM()

	public Token MPSemiColonFSM() {
		int peek = 0;
		State fsm_state = State.q0;
		Token tok;

		System.out.println("semi colon FSM triggered");
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
	
	void consumeComment1() {
		State commentState = State.q0;

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
				break;  // end q0

			case q1:
				// skipping everything between '{' and '}'
				switch (source_to_scan[file_pointer]) {
				case '{':
					System.out.println("Scanner Warning: found { embedded in comment.  Possibly missing }?");
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
				break;  // end q1
			case q8:
				System.out.println("Reached supposadly unreachable q8 case.");
				break;
			case q9:
				// runaway comment -- comment ran to EOF
				System.out.println("Scanner Error: Runaway Comment");
				dispatcherState = State.q8;
				return;
			default:
				System.out.println("Comment unexpected error state 3");
				break;
			}  // end state switch
		} // end comment state while loop
        return;
		
	}

}
