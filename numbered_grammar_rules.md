
```
1:SystemGoal      ⟶ Program eof              
2:Program         ⟶ ProgramHeading ";" Block "."
3:ProgramHeading  ⟶ "program" ProgramIdentifier 
4:Block           ⟶ VariableDeclarationPart ProcedureAndFunctionDeclarationPart StatementPart
5:VariableDeclarationPart  ⟶ "var" VariableDeclaration ";" VariableDeclarationTail
6:VariableDeclarationTail  ⟶ VariableDeclaration ";" VariableDeclarationTail 
7:                         ⟶ ε
8:VariableDeclaration      ⟶ Identifierlist ":" Type 
9:Type                     ⟶ "Integer"
10:ProcedureAndFunctionDeclarationPart ⟶ ProcedureDeclaration ProcedureAndFunctionDeclarationPart
11:                                    ⟶ FunctionDeclaration ProcedureAndFunctionDeclarationPart
12:                                    ⟶ ε
13:ProcedureDeclaration                ⟶ ProcedureHeading ";" Block ";" 
14:FunctionDeclaration                 ⟶ FunctionHeading ";" Block ";"              
15:ProcedureHeading                    ⟶ "procedure" procedureIdentifier OptionalFormalParameterList 
16:FunctionHeading                     ⟶ "function" functionIdentifier OptionalFormalParameterList ":" Type 
17:OptionalFormalParameterList         ⟶ "(" FormalParameterSection FormalParameterSectionTail ")"
18:                                    ⟶ ε
19:FormalParameterSectionTail          ⟶ ";" FormalParameterSection FormalParameterSectionTail
20:                                    ⟶ ε
21:FormalParameterSection              ⟶ ValueParameterSection
22:                                    ⟶ VariableParameterSection              
23:ValueParameterSection               ⟶ IdentifierList ":" Type
24:VariableParameterSection            ⟶ "var" IdentifierList ":" Type
25:StatementPart      ⟶ CompoundStatement              
26:CompoundStatement  ⟶ "begin" StatementSequence "end"
27:StatementSequence  ⟶ Statement StatementTail
28:StatementTail      ⟶ ";" Statement StatementTail
29:                   ⟶ ε
30:Statement           ⟶ EmptyStatement
31:                    ⟶ CompoundStatement
32:                    ⟶ ReadStatement
33:                    ⟶ WriteStatement
34:                    ⟶ AssignmentStatement
35:                    ⟶ IfStatement
36:                    ⟶ WhileStatement
37:                    ⟶ RepeatStatement
38:                    ⟶ ForStatement
39:                    ⟶ ProcedureStatement              
40:EmptyStatement      ⟶ ε
41:ReadStatement       ⟶ "read" "(" ReadParameter ReadParameterTail ")"
42:ReadParameterTail   ⟶ "," ReadParameter ReadParameterTail
43:                    ⟶ ε
44:ReadParameter       ⟶ VariableIdentifier              
45:WriteStatement      ⟶ "write" "(" WriteParameter WriteParameterTail ")"
46:WriteParameterTail  ⟶ "," WriteParameter WriteParameterTail
47:                    ⟶ ε
48:WriteParameter      ⟶ OrdinalExpression              
49:AssignmentStatement ⟶ VariableIdentifier ":=" Expression
50:                    ⟶ FunctionIdentifier ":=" Expression              
51:IfStatement         ⟶ "if" BooleanExpression "then" Statement OptionalElsePart
52:OptionalElsePart    ⟶ "else" Statement
53:                    ⟶ ε              
54:RepeatStatement     ⟶ "repeat" StatementSequence "until" BooleanExpression              
55:WhileStatement      ⟶ "while" BooleanExpression "do" Statement              
56:ForStatement        ⟶ "for" ControlVariable ":=" InitialValue StepValue FinalValue "do" Statement
57:ControlVariable     ⟶ VariableIdentifier
58:InitialValue        ⟶ OrdinalExpression
59:StepValue           ⟶ "to"
60:                    ⟶ "downto"
61:FinalValue          ⟶ OrdinalExpression              
62:ProcedureStatement  ⟶ ProcedureIdentifier OptionalActualParameterList
63:OptionalActualParameterList ⟶ "(" ActualParameter ActualParameterTail ")"
64:                            ⟶ ε
65:ActualParameterTail ⟶ "," ActualParameter ActualParameterTail
66:                    ⟶ ε
67:ActualParameter     ⟶ OrdinalExpression
68:Expression              ⟶ SimpleExpression OptionalRelationalPart
69:OptionalRelationalPart  ⟶ RelationalOperator SimpleExpression
70:                        ⟶ ε
71:RelationalOperator      ⟶ "="
72:                        ⟶ "<"
73:                        ⟶ ">"
74:                        ⟶ "<="
75:                        ⟶ ">="
76:                        ⟶ "<>"              
77:SimpleExpression        ⟶ OptionalSign Term TermTail
78:TermTail                ⟶ AddingOperator Term TermTail
79:                        ⟶ ε
80:OptionalSign            ⟶ "+"
81:                        ⟶ "-"
82:                        ⟶ ε
83:AddingOperator          ⟶ "+"
84:                        ⟶ "-"
85:                        ⟶ "or"              
86:Term                    ⟶ Factor FactorTail              
87:FactorTail              ⟶ MultiplyingOperator Factor FactorTail
88:                        ⟶ ε              
89:MultiplyingOperator     ⟶ "*"
90:                        ⟶ "div"
91:                        ⟶ "mod"
92:                        ⟶ "and"              
93:Factor                  ⟶ UnsignedInteger
94:                        ⟶ VariableIdentifier
95:                        ⟶ "not" Factor
96:                        ⟶ "(" Expression ")"
97:                        ⟶ FunctionIdentifier OptionalActualParameterList
98:ProgramIdentifier    ⟶ Identifier  
99:VariableIdentifier   ⟶ Identifier
100:ProcedureIdentifier  ⟶ Identifier
101:FunctionIdentifier   ⟶ Identifier              
102:BooleanExpression    ⟶ Expression
103:OrdinalExpression    ⟶ Expression              
104:IdentifierList       ⟶ Identifier IdentifierTail
105:IdentifierTail       ⟶ "," Identifier IdentifierTail
106:                     ⟶ ε      
107:VariableDeclarationPart ⟶ ε 
108:Type                 ⟶ "Float"
109:                     ⟶ "String"
110:                     ⟶ "Boolean"
111:WriteStatement       ⟶ "writeln" "(" WriteParameter Write ParameterTail ")"
112:MultiplyingOperator  ⟶ "/"
113:Factor               ⟶ UnsignedFloat
114:                     ⟶ StringLiteral
115:                     ⟶ "True"
116:                     ⟶ "False"


```