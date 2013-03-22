# checksum adapted from http://code.activestate.com/recipes/52251-simple-string-checksum/
#prime_list = input()

#  prime_list

def checksum(st):
    #return reduce(lambda x,y:x+y, map(ord, st)) % 37
    return reduce(lambda x,y:x+y, map(ord, st)) 

def simple_variant(k,prime_numb):
  m = prime_numb
  return ( k % m )

def knuth_variant(k):
  # m is a prime number
  m = 239
  return ( (k * (k + 3)) % m)

the_list =["and","begin","div","do","downto","else","end","fixed","float","for","function","if","integer","mod","not","or","procedure","program","read","repeat","then","to","until","var","while","write"]

the_other_list = ["and","begin","div","do","downto","else","end","fixed","float","for","function","if","integer","mod","not","or","procedure","program","read","repeat","then","to","until","var","while","write","writeln","boolean","string",]

#the_list = ["do"]

#for word in the_list:
  #print word, 
  #print checksum(word)
  #cs = checksum(word)
  #print knuth_variant(cs)
  #print simple_variant(cs),
  #print "\t",
  #print word

numb_primes = 1000
import prime_maker
primes = prime_maker.create_primes(numb_primes)
print primes
hash_list = []

for prime in primes:
    hash_list = []
    good_prime = True
    # print prime
    for words in the_other_list:
        cs = checksum(words)
        new_hash = simple_variant(cs,prime),
        # print "\t",
        # print words
        if new_hash in hash_list:
            good_prime = False
        hash_list.append(new_hash)
    if good_prime:
        print hash_list
        print "Found a possible prime number: " + str(prime)
        die
    else:
        print "This prime number has collisions: " + str(prime)
