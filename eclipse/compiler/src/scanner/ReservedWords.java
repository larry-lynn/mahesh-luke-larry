package scanner;

// research on simple hash functions foundhere
// http://www.cs.hmc.edu/~geoff/classes/hmc.cs070.200101/homework10/hashfuncs.html
public class ReservedWords {
	public static final int a_prime = 137;
	
	public static final String [] preComputedTable = {
		 "read", // index 0 
		 "", // index 1 
		 "", // index 2 
		 "", // index 3 
		 "", // index 4 
		 "", // index 5 
		 "", // index 6 
		 "write", // index 7 
		 "until", // index 8 
		 "", // index 9 
		 "procedure", // index 10 
		 "", // index 11 
		 "", // index 12 
		 "", // index 13 
		 "else", // index 14 
		 "", // index 15 
		 "", // index 16 
		 "", // index 17 
		 "", // index 18 
		 "", // index 19 
		 "then", // index 20 
		 "", // index 21 
		 "", // index 22 
		 "", // index 23 
		 "", // index 24 
		 "", // index 25 
		 "", // index 26 
		 "", // index 27 
		 "", // index 28 
		 "", // index 29 
		 "", // index 30 
		 "", // index 31 
		 "", // index 32 
		 "and", // index 33 
		 "", // index 34 
		 "", // index 35 
		 "", // index 36 
		 "end", // index 37 
		 "", // index 38 
		 "", // index 39 
		 "", // index 40 
		 "", // index 41 
		 "", // index 42 
		 "", // index 43 
		 "", // index 44 
		 "", // index 45 
		 "mod", // index 46 
		 "", // index 47 
		 "function", // index 48 
		 "div", // index 49 
		 "", // index 50 
		 "", // index 51 
		 "", // index 52 
		 "for", // index 53 
		 "", // index 54 
		 "var", // index 55 
		 "", // index 56 
		 "", // index 57 
		 "", // index 58 
		 "", // index 59 
		 "", // index 60 
		 "", // index 61 
		 "", // index 62 
		 "not", // index 63 
		 "", // index 64 
		 "integer", // index 65 
		 "", // index 66 
		 "", // index 67 
		 "", // index 68 
		 "", // index 69 
		 "if", // index 70 
		 "", // index 71 
		 "", // index 72 
		 "", // index 73 
		 "do", // index 74 
		 "program", // index 75 
		 "", // index 76 
		 "", // index 77 
		 "", // index 78 
		 "", // index 79 
		 "", // index 80 
		 "", // index 81 
		 "", // index 82 
		 "", // index 83 
		 "", // index 84 
		 "", // index 85 
		 "", // index 86 
		 "", // index 87 
		 "or", // index 88 
		 "", // index 89 
		 "to", // index 90 
		 "", // index 91 
		 "", // index 92 
		 "repeat", // index 93 
		 "", // index 94 
		 "", // index 95 
		 "", // index 96 
		 "", // index 97 
		 "", // index 98 
		 "", // index 99 
		 "", // index 100 
		 "", // index 101 
		 "", // index 102 
		 "", // index 103 
		 "", // index 104 
		 "", // index 105 
		 "begin", // index 106 
		 "", // index 107 
		 "", // index 108 
		 "", // index 109 
		 "", // index 110 
		 "", // index 111 
		 "", // index 112 
		 "", // index 113 
		 "", // index 114 
		 "", // index 115 
		 "", // index 116 
		 "fixed", // index 117 
		 "", // index 118 
		 "downto", // index 119 
		 "", // index 120 
		 "", // index 121 
		 "", // index 122 
		 "float", // index 123 
		 "", // index 124 
		 "", // index 125 
		 "while", // index 126 
		 "", // index 127 
		 "", // index 128 
		 "", // index 129 
		 "", // index 130 
		 "", // index 131 
		 "", // index 132 
		 "", // index 133 
		 "", // index 134 
		 "", // index 135 
		 "" // index 136 
	};
	
	public static TokenType hashToTokenType(int input){
		// this is only safe to use after we have performed the refine step
		TokenType output = null;
		switch(input){
		case 1: // read
			output = TokenType.MP_READ;
			break;
		case 7: // write
			output = TokenType.MP_WRITE;
			break;
		case 8: // until
			output = TokenType.MP_UNTIL;
			break;
		case 10: // procedure
			output = TokenType.MP_PROCEDURE;
			break;
		case 14: // else
			output = TokenType.MP_ELSE;
			break;
		case 20: // then
			output = TokenType.MP_THEN;
			break;
		case 33: // and
			output = TokenType.MP_AND;
			break;
		case 37: // end
			output = TokenType.MP_END;
			break;
		case 46: // mod
			output = TokenType.MP_MOD;
			break;
		case 49: // div
			output = TokenType.MP_DIV;
			break;
		case 53: // for
			output = TokenType.MP_FOR;
			break;
		case 55: // var
			output = TokenType.MP_VAR;
			break;
		case 63: // not
			output = TokenType.MP_NOT;
			break;
		case 65: // integer
			output = TokenType.MP_INTEGER;
			break;
		case 70: // if
			output = TokenType.MP_IF;
			break;
		case 74: // do
			output = TokenType.MP_DO;
			break;
		case 75: // program
			output = TokenType.MP_PROGRAM;
			break;
		case 88: // or
			output = TokenType.MP_OR;
			break;
		case 90: // to
			output = TokenType.MP_TO;
			break;
		case 93: // repeat
			output = TokenType.MP_REPEAT;
			break;
		case 106: // begin
			output = TokenType.MP_BEGIN;
			break;
		case 117: // fixed
			output = TokenType.MP_FIXED;
			break;
		case 119: // downto
			output = TokenType.MP_DOWNTO;
			break;
		case 123: // float
			output = TokenType.MP_FLOAT;
			break;
		case 126: // while
			output = TokenType.MP_WHILE;
			break;
		default:
			output = null;
			break;
		
		}
		return(output);
	}
	
	public static int checkSum(String input){
		int sum = 0;
		int i;
		for(i=0; i< input.length(); ++i){
			sum = sum + input.charAt(i);
		}
		return(sum);	
	}

	public static int simpleHash(String input){
        int cs = checkSum(input);
		int hash_val = (cs % a_prime);
		return(hash_val);
	}
	
	public static TokenType checkReserved(String input){
		int hashVal = simpleHash(input.toLowerCase());
		TokenType output = null;
		// filter step
		String possibleMatch = preComputedTable[hashVal];
		// refine step - resolve possible collisions
		if(possibleMatch.length() > 0 ){
			if(input.toLowerCase().equals(possibleMatch)){
				output = hashToTokenType(hashVal);
			}
		}
		return(output);
	}
	
}  // end class

