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
        if (a < 1)
        then
            fibonacci := 0
        else if (a = 1)
        then
            fibonacci := 1
        ELSE
            fibonacci := fibonacci( Y ) + fibonacci( Z ) 
    End
    ;

BEGIN
mice := 10;
write( fibonacci(mice) );
END
.
