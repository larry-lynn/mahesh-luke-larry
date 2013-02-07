non_terminals = ["SystemGoal","Program","ProgramHeading","Block","VariableDeclarationPart","VariableDeclarationTail","VariableDeclaration","Type","ProcedureAndFunctionDeclarationPart","ProcedureDeclaration","FunctionDeclaration","ProcedureHeading","FunctionHeading","OptionalFormalParameterList","FormalParameterSectionTail","FormalParameterSection","ValueParameterSection","VariableParameterSection","StatementPart","CompoundStatement","StatementSequence","StatementTail","Statement","EmptyStatement","ReadStatement","ReadParameterTail","ReadParameter","WriteStatement","WriteParameterTail","WriteParameter","AssignmentStatement","IfStatement","OptionalElsePart","RepeatStatement","WhileStatement","ForStatement","ControlVariable","InitialValue","StepValue","FinalValue","ProcedureStatement","OptionalActualParameterList","ActualParameterTail","ActualParameter","Expression","OptionalRelationalPart","RelationalOperator","SimpleExpression","TermTail","OptionalSign","AddingOperator","Term","FactorTail","MultiplyingOperator","Factor","ProgramIdentifier","VariableIdentifier","ProcedureIdentifier","FunctionIdentifier","BooleanExpression","OrdinalExpression","IdentifierList","IdentifierTail"]

block = """
    public void %s(){
        switch(lookahead.token_name){
        default:
        System.out.println("nobody here but us chickens");
        // parsing error
        //System.out.println("Parsing error at: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    System.exit(-5);
        }
    }
"""

for non_term in non_terminals:
    print block % non_term
