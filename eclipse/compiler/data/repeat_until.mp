Program IF_else;
VAR A : Integer;
BEGIN
A := 3;
REPEAT

  writeln('BONGO!');
  A := A - 1;

UNTIL
  ( A < 1 )
; {end repeat block}
    

END
.
