STUFF WE CAN WORK ON NOW
  * update scanner for float div  **Luke DONE**
  * update parser after scanner so it can handle float div **Larry DONE**
  * test parser with TRUE and FALSE
  * test parser with string assignments **DONE**
  * test parser with WRITELN  **DONE**
  * parser needs update for all new grammar rules
  * update build file for umachine  **Larry DONE**
  * parser needs semantic records for expressions
  * symbol table dump needs reformatting  **Larry DONE**
  * symbol table depth & insert() method **Larry DONE**
  * function returns actually returning
  * write test for program with no variable declaration  **Larry, Mahesh DONE**
  * talk to Rocky, ask if the parser needs to handle an MP\_FIXED\_LIT token from the scanner or a negative float from the scanner.  This may imply more rules.
  * check that the symbol table can handle string type **Larry DONE**
  * We need a symbolTableStack data type or something like that.  **LUKE  DONE**
  * extend symbol table code to be able to populate sub-tables for procedures & functions  **MAHESH DONE**
  * update rule list wiki page **DONE**
  * update token list wiki page **DONE**
  * create new non-terminal methods to match new rules
    * writeln & vardecl -> sigma **Larry DONE**
  * reserved word checker rewrite (wait for a response from Dr Ross)  **LUKE DONE**
  * create a new typedef enum for parse / symbol errors **Larry**
  * create dedicated method for handling errors (optional)
  * extend symbol table so we can handle functions  **Larry DONE**
  * create a wiki page for parse / symbol error types  **DONE**
  * update parser / use symbol table to resolve ambiguities   **Larry DONE**
    * Statement: disambiguate Assignment vs Procedure  **Larry DONE**
    * Assignment-Statement: disambiguate identifier vs function identifier  **Larry DONE**
    * Factor: disambiguate identifier vs function-identifier  **Larry DONE**
  * create new updated LL1 table **DEFERRED THIS FOR NOW**
  * update build file, run only symbol table tests by default **Larry DONE**
  * upgrade symbol table dump format - extra info for func & proc params **Larry DONE**


---


OUTSTANDING QUESTIONS
  * How do we further segment 'semantic records' & the construction of the symbol table from parser: analyzer?   ANSWERED 
  * ask about data types again
    * factor -> String?
    * MP\_FIXED scanned for but unused.  correct?
    * Unsigned-Float not a Type according to CFG?