{ THIS TEST FAILS AND WILL FAIL UNTIL WE HAVE
  A SYMBOL TABLE AND CAN RESOLVE THE CONTEXT
  OF a IN fibonacci( a ) }
Program foo;

VAR
Three, Blind, Mice : integer;
FUNCTION fibonacci( a : integer) : integer;
    var Y, Z : integer;
    Begin
        Y := a - 1;
        Z := a - 2;
        if ( a < 2 )
        THEN
            fibonacci := a {dont terminate with ';' - statement, not stat seq}
        ELSE
            fibonacci := fibonacci( Y ) + fibonacci( Z ) {again no ';'}
    End
    ;

BEGIN
mice := 10;
write( fibonacci(mice) );
END
.
