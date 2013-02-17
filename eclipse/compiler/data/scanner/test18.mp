{pass cases - all of these should be accepted}
a  111
_a 222
_1 333
a1 444
aa 555
a_1 666
a_a 777
a_b_c_d_1_2_3_4 888
{failure cases - these should fail, although failure modes will be different}
__a  123
1abc 456
a__b 789
a_ 101112
b__ 131415
{trick cases}
a_1
a_
abc
abc+
abc_+
a_1