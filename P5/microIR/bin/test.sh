#!/bin/bash

# Put all your test cases (MiniIR Files) into a directory named "tests".
# "tests" directory should be in src or the folder you want to submit.
# The script only checks for correct MicroIR Programs.
javac Main.java
echo "Generating out files from miniIR."                                        
            
cd tests                                                        
for file in *.miniIR; do                                            
     T=`basename $file | sed s/.miniIR//`;                         
     java -jar pgi.jar < $file > $T.out;    
     echo so                                      
done;                                                         
cd ..                                                       

echo "Generating out files from your microIR now and comparing with expected output"           


i=0
j=0                                                
for file in tests/*.pg                                
do                                                      
     i=$((i+1))
     T=`basename $file | sed s/.miniIR//`                
     java Main < $file > /tmp/$T.microIR
     java -jar spp.jar < /tmp/$T.microIR     
     java -jar -Xss2048k pgi.jar < /tmp/$T.microIR  > /tmp/$T.out
     diff  /tmp/$T.out tests/$T.out  > /dev/null
     if [ $? -eq 0 ]; then                         
         echo $T passed                           
         j=$((j+1))
     else                                        
         echo $T failed                         
     fi 
                                            
done  

rm *.class
cd syntaxtree
rm *.class
cd ..
cd visitor
rm *.class
cd ..
cd tests
rm *.out
echo "Done. ($j/$i) passed."