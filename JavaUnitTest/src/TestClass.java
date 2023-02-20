import Anotation.DoAfterAll;
import Anotation.DoBeforeAll;
import Anotation.DoTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;


public class TestClass {
    public static void start(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        int beforeCount = 0,
                afterCount = 0;
        Map<Method, Integer> map = new HashMap<>();
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(DoTest.class)) {
                map.put(m, m.getAnnotation(DoTest.class).order());
            }
            if (m.isAnnotationPresent(DoBeforeAll.class)) {
                beforeCount++;
                map.put(m, 0);
            } else if (beforeCount > 1)
                throw new RuntimeException("Using more than one @DoBeforeAll annotation is prohibited");

            if (m.isAnnotationPresent(DoAfterAll.class)){
                afterCount++;
                map.put(m, 4);
            }
            else if (afterCount > 1)
                throw new RuntimeException("Using more than one @DoAfterAll annotation is prohibited");
            m.invoke(clazz.getDeclaredConstructor().newInstance());
        }
    }

    public static void start(String nameClass) {
        try {
            Class<?> clazz = Class.forName(nameClass);
            TestClass.start(clazz);
        } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | InstantiationException e) {
            System.out.println("Class = " + nameClass + ".class not found");
            e.printStackTrace();
        }
    }
}


