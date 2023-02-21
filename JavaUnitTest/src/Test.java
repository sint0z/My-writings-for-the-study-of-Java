import Anotation.DoAfterAll;
import Anotation.DoBeforeAll;
import Anotation.DoTest;

public class Test {
    @DoBeforeAll
    public void init(){
        System.out.println("test i am before");
    }

    @DoTest()
    public void testDoTest(){
        System.out.println("test i am second");
    }
    @DoTest()
    public void test1(){
        System.out.println("test i am first");
    }

    @DoAfterAll
    public void afterInit(){
        System.out.println("test i am after");

    }

}
