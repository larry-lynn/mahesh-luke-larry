Program foo;

var 
x, y, z : integer;

BEGIN
y := 2;
z := 3;
for x := 0 to 5 do
    y := y * z;

for x := 3 downto 0 do
    z := z + x;

END
.
