##############
# Runs a test to compare
# src class gen jimple
# vs
# src cache gen jimple
##############

#make the test generators
./build.sh

#go from gen->class and class->jimple
./srctest.sh

#TODO add check that the prev steps generated the expected class and jimple files

#make the cache->jimple
#the cache placement run
java -Xshareclasses:name=cacheSrcTest  \
     -Djava.library.path=$LD_LIBRARY_PATH:/root/openj9-openjdk-jdk8/openj9/runtime/ddrext:/root/openj9-openjdk-jdk8/build/linux-x86_64-normal-server-release/jdk/classes/com/ibm/oti/shared/ \
     -cp /root/openj9-openjdk-jdk8/build/linux-x86_64-normal-server-release/jdk/classes/com/ibm/oti/shared/:/root/openj9-openjdk-jdk8/build/linux-x86_64-normal-server-release/images/j2sdk-image/jre/lib/rt.jar:/root/openj9-openjdk-jdk8/build/linux-x86_64-normal-server-release/images/j2sdk-image/jre/lib/ddr/j9ddr.jar:/root/soot/cacheTest/:target/sootclasses-trunk-jar-with-dependencies.jar:/root/openj9-openjdk-jdk8/build/linux-x86_64-normal-server-release/jdk/ddr/classes/com/ibm/j9ddr/vm29/structure/ cacheSrcTest

#the actual run
java  -Dcom.ibm.j9ddr.structurefile=/root/openj9-openjdk-jdk8/build/linux-x86_64-normal-server-release/vm/j9ddr.dat \
      -Xshareclasses:name=cacheSrcTest \
      -Djava.library.path=$LD_LIBRARY_PATH:/root/openj9-openjdk-jdk8/openj9/runtime/ddrext:/root/openj9-openjdk-jdk8/build/linux-x86_64-normal-server-release/jdk/classes/com/ibm/oti/shared/\
      -cp /root/openj9-openjdk-jdk8/build/linux-x86_64-normal-server-release/jdk/classes/com/ibm/oti/shared/:/root/openj9-openjdk-jdk8/build/linux-x86_64-normal-server-release/images/j2sdk-image/jre/lib/rt.jar:/root/openj9-openjdk-jdk8/build/linux-x86_64-normal-server-release/images/j2sdk-image/jre/lib/ddr/j9ddr.jar:/root/soot/tests/:target/sootclasses-trunk-jar-with-dependencies.jar:/root/openj9-openjdk-jdk8/build/linux-x86_64-normal-server-release/jdk/ddr/classes/com/ibm/j9ddr/vm29/structure/ \
      -XshowSettings -Xmx5g soot.Main \
      -f J -src-prec cache cacheSrcTest

#TODO replace this to read from some config file
if [ ! -f ./sootOutput/cacheSrcTest.jimple ];then
    echo "test failed"

else

    if ! diff -q sootOutput/cacheSrcTest.jimple AllOpsTest/cacheSrcTest.jimple &>/dev/null; then
	diff -y --left-column sootOutput/cacheSrcTest.jimple AllOpsTest/cacheSrcTest.jimple
    else
	echo "test passed"
    fi
    
fi
