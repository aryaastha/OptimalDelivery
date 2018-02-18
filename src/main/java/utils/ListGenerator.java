package utils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by astha.a on 18/02/18.
 */
public class ListGenerator {
    public static <T> List<T> getList(Class<T> className, int n) throws Exception {
        Constructor<T> constructor = className.getDeclaredConstructor();
        List<T> dummyList = new ArrayList<>(n);
        for (int i = 0; i < n; i++){
            dummyList.add(constructor.newInstance());
        }
        return dummyList;
    }
}
