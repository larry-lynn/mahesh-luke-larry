# checksum adapted from http://code.activestate.com/recipes/52251-simple-string-checksum/
def checksum(st):
    #return reduce(lambda x,y:x+y, map(ord, st)) % 37
    return reduce(lambda x,y:x+y, map(ord, st)) 

def simple_variant(k):
  m = 173
  return ( k % m )

def knuth_variant(k):
  # m is a prime number
  m = 239
  return ( (k * (k + 3)) % m)

the_list =["and","begin","div","do","downto","else","end","fixed","float","for","function","if","integer","mod","not","or","procedure","program","read","repeat","then","to","until","var","while","write","string","boolean","writeln","true","false"]

#the_list = ["do"]

for word in the_list:
  #print word, 
  #print checksum(word)
  cs = checksum(word)
  #print knuth_variant(cs)
  print simple_variant(cs),
  print "\t",
  print word
