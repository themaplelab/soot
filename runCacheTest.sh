##############
# Runs a test to compare
# src class gen jimple
# vs
# src cache gen jimple
#
# the class files are placed in:
# the jimple from class src is also in:
# /root/soot/cacheTestClassfiles
#
# the jimple from the cache src are in:
# /root/soot/cacheTestCacheSrcJimple 
#
# ASSUMES test generators (/root/soot/src/main/java/soot/asm/tests/*.java)
# are already built, wasteful to run it in here
##############

#make the test generators
#TODO move these to a test compile, idk something wrong in maven phases right now probably not setup for custom jvm
#./build.sh

testconfig=./src/main/java/soot/asm/tests/tests.config

#try to go from gen->class and class->jimple
./srctest.sh &> srctest.out
cat srctest.out
if ! cat srctest.out | grep -q "something went wrong"; then
rm srctest.out

 echo "########################################################"
 echo "Generating classfiles and class->jimple step success!"
 echo "########################################################"
 
#use the config file for which tests were executed, to diff
while IFS= read -r simpleTestname || [ -n "$simpleTestname" ]; do
    
rm report.txt
    
testname=${simpleTestname}Generated
    
    echo "########################################################"
    echo "Running the cache src conversion: $simpleTestname"
    echo "########################################################"
    
#make the cache->jimple
#the cache placement run, use same cache all tests, why not
java -Xshareclasses:name=cacheSrcTest  \
     -Djava.library.path=$LD_LIBRARY_PATH:/root/openj9-openjdk-jdk8/openj9/runtime/ddrext:/root/openj9-openjdk-jdk8/build/linux-x86_64-normal-server-release/jdk/classes/com/ibm/oti/shared/ \
     -cp /root/openj9-openjdk-jdk8/build/linux-x86_64-normal-server-release/jdk/classes/com/ibm/oti/shared/:/root/openj9-openjdk-jdk8/build/linux-x86_64-normal-server-release/images/j2sdk-image/jre/lib/rt.jar:/root/openj9-openjdk-jdk8/build/linux-x86_64-normal-server-release/images/j2sdk-image/jre/lib/ddr/j9ddr.jar:/root/soot/tests/:/root/PanathonExampleMaterials/exBin/:/root/soot/cacheTestClassfiles/:/root/openj9-openjdk-jdk8/build/linux-x86_64-normal-server-release/jdk/ddr/classes/com/ibm/j9ddr/vm29/structure/ \
     -XshowSettings $testname
#&>/dev/null

#the actual translation run
java  -Dcom.ibm.j9ddr.structurefile=/root/openj9-openjdk-jdk8/build/linux-x86_64-normal-server-release/vm/j9ddr.dat \
      -Xshareclasses:name=cacheSrcTest \
      -Djava.library.path=$LD_LIBRARY_PATH:/root/openj9-openjdk-jdk8/openj9/runtime/ddrext:/root/openj9-openjdk-jdk8/build/linux-x86_64-normal-server-release/jdk/classes/com/ibm/oti/shared/\
      -cp /root/openj9-openjdk-jdk8/build/linux-x86_64-normal-server-release/jdk/classes/com/ibm/oti/shared/:/root/openj9-openjdk-jdk8/build/linux-x86_64-normal-server-release/images/j2sdk-image/jre/lib/rt.jar:/root/openj9-openjdk-jdk8/build/linux-x86_64-normal-server-release/images/j2sdk-image/jre/lib/ddr/j9ddr.jar:/root/soot/tests/:/root/PanathonExampleMaterials/exBin/:/root/soot/cacheTestClassfiles/:target/sootclasses-trunk-jar-with-dependencies.jar:/root/openj9-openjdk-jdk8/build/linux-x86_64-normal-server-release/jdk/ddr/classes/com/ibm/j9ddr/vm29/structure/ \
      -XshowSettings -Xmx5g soot.Main \
      -cp /root/soot/cacheTestClassfiles:/root/openj9-openjdk-jdk8/build/linux-x86_64-normal-server-release/images/j2sdk-image/jre/lib/rt.jar \
      -f J -src-prec only-cache -d cacheTestCacheSrcJimple \
      -allow-phantom-refs \
      $testname

#first lets check if prev steps made the jimples
jimple=$testname.jimple

if [ ! -f ./cacheTestCacheSrcJimple/$jimple ]; then
    echo "########################################################"
    echo "Test failed at cache->jimple:"
    echo "jimple file $cachejimple not generated"
    echo "########################################################"

    echo "########################################################" >> report.txt
    echo "Test failed at cache->jimple:" >> report.txt
    echo "jimple file $cachejimple not generated" >> report.txt
    echo "########################################################" >> report.txt

    
elif [ ! -f ./cacheTestClassfiles/$jimple ]; then
    echo "########################################################"
    echo "Test failed at class->jimple:"
    echo "jimple file $classjimple not generated"
    echo "########################################################"

    echo "########################################################" >> report.txt
    echo "Test failed at class->jimple:" >> report.txt
    echo "jimple file $classjimple not generated" >> report.txt
    echo "########################################################" >> report.txt
    
else

    #files exist
    if ! diff -q  ./cacheTestClassfiles/$jimple ./cacheTestCacheSrcJimple/$jimple &>/dev/null; then
	echo "########################################################"
	echo "Test failed: ./cacheTestClassfiles/$jimple and ./cacheTestCacheSrcJimple/$jimple contain differences."
	echo "########################################################"

	echo "########################################################" >> report.txt
        echo "Test failed: ./cacheTestClassfiles/$jimple and ./cacheTestCacheSrcJimple/$jimple contain differences." >> report.txt
        echo "########################################################" >> report.txt

	mkdir diffs
	diff -y --left-column ./cacheTestClassfiles/$jimple ./cacheTestCacheSrcJimple/$jimple >> diffs/$simpleTestname.txt
	diff -y --left-column ./cacheTestClassfiles/$jimple ./cacheTestCacheSrcJimple/$jimple >> diffs/$simpleTestname.txt >> report.txt
	
    else
	echo "########################################################"
	echo "Test passed: ./cacheTestClassfiles/$jimple and ./cacheTestCacheSrcJimple/$jimple are same."
	echo "########################################################"

	echo "########################################################" >> report.txt
        echo "Test passed: ./cacheTestClassfiles/$jimple and ./cacheTestCacheSrcJimple/$jimple are same." >> report.txt
        echo "########################################################" >> report.txt

    fi
    
     fi
done<$testconfig

else

    echo "########################################################"
    echo "Generating classfiles and class->jimple step failed"
    echo "########################################################"

    echo "########################################################" >> report.txt
    echo "Generating classfiles and class->jimple step failed" >> report.txt
    echo "########################################################" >> report.txt

fi
