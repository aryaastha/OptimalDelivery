import sun.java2d.xr.MutableInteger;

/**
 * Created by astha.a on 16/02/18.
 */
public class TestClass {
    public static void testFunction(MutableInteger i){
        i.setValue(10);
        System.out.println(i);
    }
    public static void main(String[] args) {
        MutableInteger i = new MutableInteger(9);
//        StringBuilder x = new StringBuilder();
        testFunction(i);
        System.out.println(i.getValue());
    }
}
