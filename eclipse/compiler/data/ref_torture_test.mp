Program ref_torture_test;
var X, Y, Z : integer;
Procedure outer_proc(var a,b : Integer; c : Integer);
    var d : integer;
    PROCEDURE inner_proc(var foo, bar : integer; baz : integer);
        BEGIN
            foo := foo + 100;
            bar := bar + 100;
            baz := baz + 100;
            writeln('in nested function after math');
            writeln(foo);
            writeln(bar);
            writeln(baz);
        END;
    BEGIN
        a := a + 10;
        b := b + 10;
        c := c + 10;
        d := a + 40;
        writeln('in top level function after math');
        writeln(a);
        writeln(b);
        writeln(c);
        writeln(d);
        inner_proc(a, d, c);
        writeln('in top level func after subproc');
        writeln(c);
        writeln(d);
    END;

BEGIN
writeln('Program: ref_torture_test');
writeln('Expected results: last 3 numbers 115, 16, 7');
X := 5;
Y := 6;
Z := 7;
writeln('before any procedure calls');
writeln(X);
writeln(Y);
writeln(Z);
outer_proc(x,y,z);
writeln('after all procedure calls');
writeln(X);
writeln(Y);
writeln(Z);

writeln(' ');
writeln('MU');
writeln(' ');
END
.
