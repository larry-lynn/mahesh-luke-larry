{ THIS TEST FAILS AND WILL FAIL UNTIL WE HAVE
  A SYMBOL TABLE AND CAN RESOLVE THE CONTEXT
  OF a IN fibonacci( a ) }
Program foo;

VAR
Three, Blind, Mice : integer;
FUNCTION fibonacci( a ) : integer
    Begin
        if ( a < 2 )
        THEN
            fibonacci := a;
        ELSE
            fibonacci := fibonacci( a - 1) + fibonacci( a - 2 );
    End
    ;

BEGIN
mice := 10;
write( fibonacci(mice) );
END
.
