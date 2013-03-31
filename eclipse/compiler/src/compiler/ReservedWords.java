package compiler;

// research on simple hash functions foundhere
// http://www.cs.hmc.edu/~geoff/classes/hmc.cs070.200101/homework10/hashfuncs.html
public class ReservedWords {
	public static final int a_prime = 173;
	
	public static final String [] preComputedTable = {
		 "", // index 0
		 "", // index 1
		 "", // index 2
		 "", // index 3
		 "false", // index 4
		 "function", // index 5
		 "", // index 6
		 "", // index 7
		 "", // index 8
		 "fixed", // index 9
		 "", // index 10
		 "", // index 11
		 "", // index 12
		 "", // index 13
		 "", // index 14
		 "float", // index 15
		 "", // index 16
		 "", // index 17
		 "while", // index 18
		 "", // index 19
		 "", // index 20
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
		 "", // index 33
		 "if", // index 34
		 "", // index 35
		 "write", // index 36
		 "until", // index 37
		 "do", // index 38
		 "", // index 39
		 "", // index 40
		 "", // index 41
		 "", // index 42
		 "", // index 43
		 "boolean", // index 44
		 "", // index 45
		 "", // index 46
		 "", // index 47
		 "", // index 48
		 "", // index 49
		 "", // index 50
		 "", // index 51
		 "or", // index 52
		 "", // index 53
		 "to", // index 54
		 "", // index 55
		 "", // index 56
		 "", // index 57
		 "integer", // index 58
		 "", // index 59
		 "", // index 60
		 "", // index 61
		 "", // index 62
		 "", // index 63
		 "", // index 64
		 "", // index 65
		 "read", // index 66
		 "", // index 67
		 "program", // index 68
		 "", // index 69
		 "", // index 70
		 "", // index 71
		 "", // index 72
		 "", // index 73
		 "", // index 74
		 "", // index 75
		 "", // index 76
		 "", // index 77
		 "", // index 78
		 "else", // index 79
		 "", // index 80
		 "writeln", // index 81
		 "", // index 82
		 "", // index 83
		 "", // index 84
		 "then", // index 85
		 "", // index 86
		 "", // index 87
		 "", // index 88
		 "", // index 89
		 "", // index 90
		 "", // index 91
		 "", // index 92
		 "", // index 93
		 "", // index 94
		 "", // index 95
		 "", // index 96
		 "", // index 97
		 "", // index 98
		 "", // index 99
		 "", // index 100
		 "", // index 101
		 "true", // index 102
		 "", // index 103
		 "procedure", // index 104
		 "", // index 105
		 "", // index 106
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
		 "", // index 117
		 "", // index 118
		 "", // index 119
		 "", // index 120
		 "", // index 121
		 "repeat", // index 122
		 "", // index 123
		 "", // index 124
		 "", // index 125
		 "", // index 126
		 "", // index 127
		 "", // index 128
		 "", // index 129
		 "", // index 130
		 "", // index 131
		 "", // index 132
		 "", // index 133
		 "and", // index 134
		 "", // index 135
		 "", // index 136
		 "", // index 137
		 "end", // index 138
		 "", // index 139
		 "", // index 140
		 "", // index 141
		 "", // index 142
		 "", // index 143
		 "string", // index 144
		 "", // index 145
		 "", // index 146
		 "mod", // index 147
		 "downto", // index 148
		 "", // index 149
		 "div", // index 150
		 "", // index 151
		 "", // index 152
		 "", // index 153
		 "for", // index 154
		 "", // index 155
		 "var", // index 156
		 "", // index 157
		 "", // index 158
		 "", // index 159
		 "", // index 160
		 "", // index 161
		 "", // index 162
		 "", // index 163
		 "not", // index 164
		 "", // index 165
		 "", // index 166
		 "", // index 167
		 "", // index 168
		 "", // index 169
		 "", // index 170
		 "begin", // index 171
		 "", // index 172
	};
	
	public static TokenType hashToTokenType(int input){
		// this is only safe to use after we have performed the refine step
		TokenType output = null;
		switch(input){
		case 4: // false
			output = TokenType.MP_FALSE;
			break;
		case 5: // function
			output = TokenType.MP_FUNCTION;
			break;
		case 9: // fixed
			output = TokenType.MP_FIXED;
			break;
		case 15: // float
			output = TokenType.MP_FLOAT;
			break;
		case 18: // while
			output = TokenType.MP_WHILE;
			break;
		case 34: // if
			output = TokenType.MP_IF;
			break;
		case 36: // write
			output = TokenType.MP_WRITE;
			break;
		case 37: // until
			output = TokenType.MP_UNTIL;
			break;
		case 38: // do
			output = TokenType.MP_DO;
			break;
		case 44: // boolean
			output = TokenType.MP_BOOLEAN;
			break;
		case 52: // or
			output = TokenType.MP_OR;
			break;
		case 54: // to
			output = TokenType.MP_TO;
			break;
		case 58: // integer
			output = TokenType.MP_INTEGER;
			break;
		case 66: //read
			output = TokenType.MP_READ;
			break;
		case 68: // program
			output = TokenType.MP_PROGRAM;
			break;
		case 79: // else
			output = TokenType.MP_ELSE;
			break;
		case 81: // writeln
			output = TokenType.MP_WRITELN;
			break;
		case 85: // then
			output = TokenType.MP_THEN;
			break;
		case 102: // true
			output = TokenType.MP_TRUE;
			break;
		case 104: // procedure
			output = TokenType.MP_PROCEDURE;
			break;
		case 122: // repeat
			output = TokenType.MP_REPEAT;
			break;
		case 134: // and
			output = TokenType.MP_AND;
			break;
		case 138: // end
			output = TokenType.MP_END;
			break;
		case 144: // string
			output = TokenType.MP_STRING;
			break;
		case 147: // mod
			output = TokenType.MP_MOD;
			break;
		case 148: // downto
			output = TokenType.MP_DOWNTO;
			break;
		case 150: // div
			output = TokenType.MP_DIV;
			break;
		case 154: // for
			output = TokenType.MP_FOR;
			break;
		case 156: // var
			output = TokenType.MP_VAR;
			break;
		case 164: // not
			output = TokenType.MP_NOT;
			break;
		case 171: // begin
			output = TokenType.MP_BEGIN;
			break;
		// Commenting out for now to test new hash
/*		case 1: // read
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
		case 33: // andc
			output = TokenType.MP_AND;
			break;
		case 37: // end
			output = TokenType.MP_END;
			break;
		case 46: // mod
			output = TokenType.MP_MOD;
			break;
		case 48: // function
			output = TokenType.MP_FUNCTION;
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
			break;*/
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

