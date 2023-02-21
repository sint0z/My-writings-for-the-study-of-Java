import Anotation.DoAfterAll;
import Anotation.DoBeforeAll;
import Anotation.DoTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class TestClass {
    public static void main(String[] args) throws Exception {
        TestClass.start(Test.class);
    }

    public static void start(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        int beforeCount = 0;
        int afterCount = 0;
        Map<Method, Integer> tests = new HashMap<>();
        Method before_all = null;
        Method after_all = null;
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(DoTest.class)) {
                tests.put(m, m.getAnnotation(DoTest.class).order());
            }

            if (m.isAnnotationPresent(DoBeforeAll.class)) {
                beforeCount++;
                before_all = m;
            }

            if (m.isAnnotationPresent(DoAfterAll.class)) {
                afterCount++;
                after_all = m;
            }
        }

        if (beforeCount > 1)
            throw new RuntimeException("Using more than one @DoBeforeAll annotation is prohibited");
        if (afterCount > 1)
            throw new RuntimeException("Using more than one @DoAfterAll annotation is prohibited");

        var testsList = tests.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .flatMap(e -> Stream.of(e.getKey()))
                .collect(Collectors.toList());

        if (before_all != null)
            before_all.invoke(clazz.getConstructor().newInstance());

        for (Method m : testsList) {
            try {
                m.invoke(clazz.getConstructor().newInstance());
            } catch (IllegalAccessException e) {
                System.out.println(e);
            }
        }

        if (after_all != null)
            after_all.invoke(clazz.getConstructor().newInstance());
    }

    public static void start(String nameClass) {
        try {
            Class<?> clazz = Class.forName(nameClass);
            TestClass.start(clazz);
        } catch (ClassNotFoundException | InvocationTargetException |
                NoSuchMethodException | IllegalAccessException | InstantiationException e) {
            System.out.println("Class = " + nameClass + ".class not found");
            e.printStackTrace();
        }
    }
}


