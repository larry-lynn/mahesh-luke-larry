def create_primes(to_go_to):
    if to_go_to == 2:
        return [2]
    elif to_go_to < 2:
        return []
    s = range(3,to_go_to,2)
    mroot = to_go_to ** 0.5
    mid_point = (to_go_to + 1)/2-1
    i = 0
    m = 3
    while m <= mroot:
        if s[i]:
            j = (m*m-3)/2
            s[j] = 0
            while j < mid_point:
                s[j] = 0
                j += m
        i += 1
        m = 2*i+3
    output_list = [2] + [x for x in s if x]
    return output_list
