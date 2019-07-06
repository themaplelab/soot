import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.ConstantCallSite;

public class Test0{

    //should try IJDL, just to test const pool reading of args for bsm 
    public static int minus(int a, int b) {  // method I want to dynamically invoke
    return a + b;
}

public static CallSite bootstrap0(MethodHandles.Lookup caller, String name,
        MethodType type) throws Exception {

    //IJDL
    MethodHandle mh = MethodHandles.lookup().findStatic(Test0.class,
							"minus", MethodType.methodType(int.class, int.class, int.class));

    return new ConstantCallSite(mh);
}
}
