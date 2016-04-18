
```
uPascal Definition (EBNF)

Program                             = ProgramHeading ";" Block "." 
ProgramHeading                      = "program" Identifier 

Block                               = VariableDeclarationPart ProcedureAndFunctionDeclarationPart StatementPart 
VariableDeclarationPart             = [ "var" VariableDeclaration ";" { VariableDeclaration ";" } ] 
ProcedureAndFunctionDeclarationPart = { ( ProcedureDeclaration | FunctionDeclaration )  ";" }
StatementPart                       = CompoundStatement 

VariableDeclaration                 = Identifierlist ":" Type
Type                                = "Integer" | "Float" 
ProcedureDeclaration                = ProcedureHeading ";" Block 
FunctionDeclaration                 = FunctionHeading ";" Block 

ProcedureHeading                    = "procedure" Identifier [ FormaParameterList ] 
FunctionHeading                     = "function" Identifier [ FormalParameterList ] : Type
FormalParameterList                 = "(" FormalParameterSection { ";" FormalParameterSection } ")" 
FormalParameterSection              = ValueParameterSection | VariableParameterSection 

ValueParamterSection                = IdentifierList : Type
VariableParameterSection            = "var" IdentifierList : Type

CompoundStatement                   = "begin" StatementSequence "end" 
StatementSequence                   = Statement { ";" Statement } 
Statement                           = SimpleStatement | StructuredStatement 
SimpleStatement                     = EmptyStatement | ReadStatement | WriteStatement | AssignmentStatement | ProcedureStatement 
StructuredStatement                 = CompoundStatement | ConditionalStatement | RepetitiveStatement 
ConditionalStatement                = IfStatement 
RepetitiveStatement                 = WhileStatement | RepeatStatement | ForStatement 

EmptyStatement                      = 
ReadStatement                       = "read" ReadParameterList 
WriteStatement                      = "write" WriteParameterList 
AssignmentStatement                 = ( Variable | FunctionIdentifier ) ":=" Expression 
ProcedureStatement                  = ProcedureIdentifier [ ActualParameterList ] 
IfStatement                         = "if" BooleanExpression "then" Statement [ "else" Statement ] 
RepeatStatement                     = "repeat" StatementSequence "until" BooleanExpression 
WhileStatement                      = "while" BooleanExpression "do" Statement 
ForStatement                        = "for" ControlVariable ":=" InitialValue ( "to" | "downto" ) FinalValue "do" Statement 
ControlVariable                     = VariableIdentifier 
InitialValue                        = OrdinalExpression 
FinalValue                          = OrdinalExpression 

Expression                          = SimpleExpression [ RelationalOperator SimpleExpression ] 
SimpleExpression                    = [ Sign ] Term { AddingOperator Term } 
Term                                = Factor { MultiplyingOperator Factor } 
Factor                              = UnsignedInteger | Variable | FunctionDesignator | "not" Factor | "(" Expression ")" 
RelationalOperator                  = "=" | "<" | ">" | "<=" | ">=" | "<>" 
AddingOperator                      = "+" | "-" | "or" 
MultiplyingOperator                 = "*" | "div" | "mod" | "and" 
FunctionDesignator                  = FunctionIdentifier [ ActualParameterList ] 

Variable                            = VariableIdentifier 
ActualParameterList                 = "(" ActualParameter { "," ActualParameter } ")" 
ActualParameter                     = Expression
ReadParameterList                   = "(" ReadParameter { "," ReadParameter } ")" 
ReadParameter                       = Variable 
WriteParameterList                  = "(" WriteParameter { "," WriteParameter } ")" 
WriteParameter                      = Expression 
BooleanExpression                   = OrdinalExpression 
OrdinalExpression                   = Expression 

VariableIdentifier                  = Identifier 
ProcedureIdentifier                 = Identifier 
FunctionIdentifier                  = Identifier 

IdentifierList                      = Identifier { "," Identifier } 

Identifier                          = ??
UnsignedInteger                     = DigitSequence 
Sign                                = "+" | "-" 
Under                               = "_"
DigitSequence                       = Digit { Digit } 
Letter                              = "a" | "b" | "c" | "d" | "e" | "f" | "g" | "h" | "i" | "j" | "k" | "l" | "m" 
                                     |"n" | "o" | "p" | "q" | "r" | "s" | "t" | "u" | "v" | "w" | "x" | "y" | "z" 
                                     |"A" | "B" | "C" | "D" | "E" | "F" | "G" | "H" | "I" | "J" | "K" | "L" | "M" 
                                     |"N" | "O" | "P" | "Q" | "R" | "S" | "T" | "U" | "V" | "W" | "X" | "Y" | "Z" 
Digit                               = "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" 
  
```