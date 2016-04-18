## Phase 2: functionality, integration, polish ##
  * update build.xml  -- Larry   DONE 

  * program integration  -- Larry   IN PROGRESS 

  * print out warnings to stderr (optional) -- Larry   DONE 

  * Conflict resolution -- Luke

  * Match  DONE 

  * must be able to handle assignment, read, write (no procedures) -- Larry   DONE 

  * upgrade parser to handle IF statement -- Larry  DONE 

  * upgrade parser to handle REPEAT statement  -- Larry

  * upgrade parser to handle WHILE statement  -- Larry

  * upgrade parser to handle FOR statement  -- Larry

  * upgrade parser to handle Procedure declaration  -- Larry

  * upgrade parser to handle Function declaration  -- Larry

  * Syntax Error: a message similar to "expecting the start of a statement, but found '(' -- Mahesh

  * completion: "The input program parses!" -- Mahesh

  * upgrade debug routines - use method instead of println (optional) -- Mahesh

  * grammar animator - check LL(1) table  -- Luke

  * upgrade syntax error to use exceptions - can be used for exception recovery -- Mahesh

  * implement good error messages in parser -- Mahesh

  * create test programs to exercise parser -- Luke

  * write match() output to file and also stdout -- Mahesh

  * update non-terminal methods to map to epsilon where appropriate given our most recent LL(1) table -- (everyone takes the 21 stubs that they originally wrote) -- Larry  DONE 

  * update non-terminal methods to map to epsilon where appropriate given our most recent LL(1) table -- Luke

  * update non-terminal methods to map to epsilon where appropriate given our most recent LL(1) table -- Mahesh

UNCLAIMED WORK

  * write sample test programs that are intended to fail parsing to confirm that the parser catches the problems and throws the appropriate parse errors.

  * write sample test programs that are intended to have errors in both scanning and parsing.

  * Figure out what is meant by 'pre and post conditions'  implement these if necessary


## Phase 1: parser stubs ##
```
1:SystemGoal : Luke  ** DONE **
2:Program : Luke ** DONE **
3:ProgramHeading : Luke ** DONE **
4:Block : Luke ** DONE **
5:VariableDeclarationPart : Luke ** DONE **
6:VariableDeclarationTail : Luke ** DONE **
7:VariableDeclaration : Luke ** DONE **
8:Type : Luke ** DONE **
9:ProcedureAndFunctionDeclarationPart : Luke ** DONE **
10:ProcedureDeclaration : Luke ** DONE **
11:FunctionDeclaration : Luke ** DONE **
12:ProcedureHeading : Luke ** DONE **
13:FunctionHeading : Luke ** DONE **
14:OptionalFormalParameterList : Luke ** DONE **
15:FormalParameterSectionTail : Luke ** DONE **
16:FormalParameterSection : Luke ** DONE **
17:ValueParameterSection : Luke ** DONE **
18:VariableParameterSection : Luke ** DONE **
19:StatementPart : Luke ** Done **
20:CompoundStatement : Luke ** Done **
21:StatementSequence : Luke ** Done **
22:StatementTail : Larry  ** DONE **
23:Statement : Larry  ** DONE **
24:EmptyStatement : Larry  ** SKIPPED**
25:ReadStatement : Larry ** Done **
26:ReadParameterTail : Larry ** Done **
27:ReadParameter : Larry ** Done **
28:WriteStatement : Larry ** Done **
29:WriteParameterTail : Larry ** Done **
30:WriteParameter : Larry ** Done **
31:AssignmentStatement : Larry ** Done **
32:IfStatement : Larry ** Done **
33:OptionalElsePart : Larry ** Done **
34:RepeatStatement : Larry ** Done **
35:WhileStatement : Larry ** Done **
36:ForStatement : Larry ** Done **
37:ControlVariable : Larry ** Done **
38:InitialValue : Larry ** Done **
39:StepValue : Larry ** Done **
40:FinalValue : Larry ** Done **
41:ProcedureStatement : Larry ** Done **
42:OptionalActualParameterList : Larry ** Done **
43:ActualParameterTail : Mahesh ** Done **
44:ActualParameter : Mahesh ** Done **
45:Expression : Mahesh ** Done **
46:OptionalRelationalPart : Mahesh ** Done **
47:RelationalOperator : Mahesh ** Done **
48:SimpleExpression : Mahesh ** Done **
49:TermTail : Mahesh ** Done **
50:OptionalSign : Mahesh ** Done **
51:AddingOperator : Mahesh ** Done **
52:Term : Mahesh ** Done **
53:FactorTail : Mahesh ** Done **
54:MultiplyingOperator : Mahesh ** Done **
55:Factor : Mahesh ** Done - Ambiguity yet to be solved - UnsignedInteger treated as MP_INTEGER_LIT **
56:ProgramIdentifier : Mahesh ** Done **
57:VariableIdentifier : Mahesh ** Done **
58:ProcedureIdentifier : Mahesh ** Done **
59:FunctionIdentifier : Mahesh ** Done **
60:BooleanExpression : Mahesh ** Done **
61:OrdinalExpression : Mahesh ** Done **
62:IdentifierList : Mahesh ** Done **
63:IdentifierTail : Mahesh ** Done **
```