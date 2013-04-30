if [ $# -ne 1 ]
then
  echo "Usage: $0 <micro-pascal-program-to-run>"
  exit -255
fi
cwd=${PWD##*/}
echo "Current Wroking Directory is: $cwd"
if [ $cwd != "classes" ]
then
  echo "copy $0 into XXX"
  exit -254
fi

echo "passed pre-flight checks.  about to compile"
# set memory for esus & compile
java -Xmx1024m compiler.Parser $1
echo " "
echo "DONE"
