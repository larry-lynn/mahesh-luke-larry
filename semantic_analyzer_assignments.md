# C level #
  * pass type data from term down to factor tail **Larry DONE**
  * use semantic data from term in factor tail to do type checking and casting **Larry DONE**
  * pass type data from simpleExp down to term tail **Larry DONE**
  * use semantic data from simpleExp in term tail for type checking **Larry DONE**
  * in Semantic Analyzer separate type-checking & casting from IR generation **Larry DONE**
  * write exhaustive routine for multiplication type-checking & casting **Larry DONE**
  * write exhaustive routine for addition type-checking & casting **Mahesh DONE**
  * need to write IR routines for generating float & int multiplication **Larry DONE**
  * write IR routines for generating int & float addition **Mahesh DONE**
  * write IR routines for OR & AND **Mahesh DONE**
  * write type check before NOT **Mahesh DONE**
  * write IR routine for NOT **Mahesh DONE**
  * write IR routine for level 1 int casting - return type to parser  **Luke DONE**
  * write IR routine for level 1 float casting - return type to parser **Luke DONE**
  * write IR routine for level 2 int casting - return type to parser **Luke DONE**
  * write IR routine for level 2 float casting - return type to parser **Luke DONE**
  * write exhaustive routine for comparison type-checking & casting **Luke DONE**
  * write IR routines for integer comparison **Luke DONE**
  * write IR routines for float comparison **Luke DONE**

# B level #
  * Create method to generate unique labels (semantic analyzer) **Larry DONE**
  * possibly necessary: update non-terminals to return a label, pass labels around tree
  * IR routines for IF - branch statements & drop label  **Mahesh DONE**
  * write test for if with statement **Larry DONE**
  * write test for if with compound statement **Luke DONE**
  * write test for if/statement else/compound statement **Mahesh DONE**
  * write test for if compound statement else statement **DONE**
  * write test for if, then nested if, then else which matches the inner if **DONE**
  * IR routines for optional ELSE **Mahesh DONE**
  * IR routines for FOR **Larry DONE**
  * IR routines for WHILE  **Larry DONE**
  * IR routines for REPEAT/UNITL **Larry DONE**
  * fix bug with backing up SP at BEGIN & not restoring it at END **Misunderstanding - dont do this**
  * add in type checking and casting for variable assignment
  * add in type checking for ordinal expressions on FOR loop
  * add in semantic actions for negative numbers, fix casting\_test\_less test.**Luke DONE**
  * fix complex if/else/loop test by always casting for loop expressions to int
  * restore 3 missing tests **Mahesh DONE**
  * move the type check logic for negation out of the parser and into the semantic analyzer **Luke DONE**

# A level #
  * pass extra semantic info around tree to support pass-by-ref **Larry DONE**
  * in parser, assemble actual parameters in an Array List **Larry DONE**
  * Write semantic code to emit IR that branches around functions & procedures on definition **Larry DONE**
  * memory management for function returns **Larry DONE**
  * memory management for pass by reference / pass by value **Larry Proc done**
  * update parser to pass semantic records that will be needed for pass-by-value **Larry DONE**
  * write IR code that loads parameter values deep in stack into func/proc activation records **Larry DONE**
  * IR routines to back up old Dx at the beginning of functions and procedures **Larry DONE**
  * IR routines to restore old Dx at end of functions and procedures **Larry DONE**
  * write cleanup IR that leaves function return on the stack **Larry DONE**
  * write teardown IR for proc local variables **Larry DONE**
  * write teardown IR for func local variables **Larry DONE**
  * write teardown IR for parameters for Procedures **Larry DONE**
  * write teardown IR for parameters for Function (different because of return) **Larry DONE**
  * write helper method to get the number of parameters for a function or procedure **Larry DONE**
  * fix bug for allocating memory for symbol table at BEGIN - needs to be earlier **Larry DONE**
  * upgrades necessary to compile with basic procedure that has no parameters **Larry DONE**
  * upgrades necessary to compile with procedure that has 1 parameter passed by value **Larry DONE**
  * upgrades necessary to compile with procedure that has 1 parameter passed by reference **Larry DONE**
  * test with proc that has its own local vars
  * upgrade necessary to compile with procedure that has multiple call by value parameters **Larry DONE**
  * upgrades necessary to compile complex procedure with multiple args, mix of call by ref, call by val
  * upgrades necessary to compile with basic function that has no parameters **Larry DONE**
  * upgrades necessary to compile with function that has 1 parameter passed by value **Larry DONE**
  * upgrades necessary to compile with function that has 1 parameter passed by reference
  * upgrade necessary to compile with function that has multiple call by value parameters **Larry DONE**
  * upgrades necessary to compile complex function with multiple args, mix of call by ref, call by val
  * fix problem with order of allocation for param/local var memory & passing arg data **Larry DONE**
  * update semantic analyzer - add check to make sure the number of paramters passed to a function or procedure matches the call definition **Larry DONE**
  * fix bug mentioned in comment in RSA test.

## Misc ##

  * update build file - separate out symbol table tests into separate directory; by default only run tests that generate IR that actually works **Larry DONE**
  * Write a subroutine that deletes IR if we get a parse, symbol or semantic error

## Optional ##

  * make error handlers throw exceptions; make an exception handler in Statement that catches them and continues the compile