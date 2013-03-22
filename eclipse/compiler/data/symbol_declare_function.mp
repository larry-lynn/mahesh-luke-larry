Program function_TEST;

var A : integer;
FUNCTION func1( B, D: integer ) : integer;
    var C : integer;
    Begin
        C := B;
        func1 := C; { is this how we do function returns? }
    End
    ;

BEGIN
A := 2
END
.
