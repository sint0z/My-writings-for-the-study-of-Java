import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("unchecked")
public class NewArrayList<E> {
    private int size;
    private Object[] elementData;
    private static final int DEFAULT_CAPACITY = 10;

    public NewArrayList() {
        this.elementData = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public NewArrayList(int initialCapacity) throws IllegalArgumentException {
        if (initialCapacity > 0) {
            this.elementData = (E[]) new Object[initialCapacity];
        } else throw new IllegalArgumentException("Capacity < 0");
    }

    private int lenght() {
        return this.elementData.length;
    }

    public int size() {
        return this.size;
    }

    public boolean add(E element) {
        if (size == elementData.length)
            elementData = grow();
        elementData[size] = element;
        size += 1;
        return true;
    }

    private Object[] grow() {
        int oldCapacity = this.elementData.length;
        int newCapacity = oldCapacity + (oldCapacity / 2) + 1;
        return Arrays.copyOf(elementData, newCapacity);
    }

    public E get(int index) {
        isCheckIndex(index);
        return (E)elementData[index];
    }

    public boolean remove(int index) {
        isCheckIndex(index);
        System.arraycopy(elementData, index + 1, elementData, index, size - 1 - index);
        size--;
        elementData[size] = null;
        return true;
    }

    public void isCheckIndex(int index){
        if(index>=elementData.length || index <0) {
            throw new ArrayIndexOutOfBoundsException("The index "+ index + " unacceptable");
        }
    }

}
