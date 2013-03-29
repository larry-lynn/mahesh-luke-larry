package compiler;

// I think this is wrong - mixes kinds and types
// this should probably be removed after refactoring -- Larry
public enum ParserSymbol {
    // Symbol types so far
    MP_SYMBOL_INTEGER,
    MP_SYMBOL_REAL,
    MP_SYMBOL_FLOAT,
    MP_SYMBOL_BOOLEAN,
    MP_SYMBOL_STRING,
    MP_SYMBOL_FIXED,

    // Symbols of headings
    MP_SYMBOL_PROCEDURE,
    MP_SYMBOL_FUNCTION,
    MP_SYMBOL_PARAMETER,
    MP_SYMBOL_VAR
	
}
