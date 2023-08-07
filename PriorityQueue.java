import java.util.*;

public class PriorityQueue<E>
{
    private int size;
    private Object H[];

    public PriorityQueue(int max_size)
    {
        H = new Object[max_size];
    }

    private Comparator<E> comparator;
    public PriorityQueue(int max_size, Comparator<E> comparator)
    {
        this.comparator = comparator;
        H = new Object[max_size];
    }

    private E parent(int i)
    {
        return (E) H[(i - 1) / 2];
    }

    private E left(int i)
    {
        return (E) H[2 * i + 1];
    }

    private E right(int i)
    {
        return (E) H[2 * i + 2];
    }

    private boolean greaterThanOrEqualTo(E e1, E e2)
    {
        if (comparator != null)
        {
            return comparator.compare(e1, e2) >= 0;
        }
        else
        {
            return ((Comparable<E>) e1).compareTo(e2) >= 0;
        }
    }

    private boolean lessThan(E e1, E e2)
    {
        if (comparator != null)
        {
            return comparator.compare(e1, e2) < 0;
        }
        else
        {
            return ((Comparable<E>) e1).compareTo(e2) < 0;
        }
    }

    private boolean lessThanOrEqualTo(E e1, E e2)
    {
        if (comparator != null)
        {
            return comparator.compare(e1, e2) <= 0;
        }
        else
        {
            return ((Comparable<E>) e1).compareTo(e2) <= 0;
        }
    }

    private boolean greaterThan(E e1, E e2)
    {
        if (comparator != null)
        {
            return comparator.compare(e1, e2) > 0;
        }
        else
        {
            return ((Comparable<E>) e1).compareTo(e2) > 0;
        }
    }

    private void swap(int i1, int i2)
    {
        E temp = get(i1);
        H[i1] = H[i2];
        H[i2] = temp;
    }

    private E get(int i)
    {
        return (E) H[i];
    }

    private void shiftUp(int i)
    {
        while (i > 0 & greaterThanOrEqualTo(get(i), parent(i)))
        {
            swap(i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    private void shiftDown(int i)
    {
        int max_index = i;

        if ((2 * i + 1) <= size-1 && lessThan(get(max_index), left(i)))
            max_index = 2 * i + 1;
        if (((2 * i + 2) <= size-1) && (lessThan(get(max_index), right(i))))
            max_index = 2 * i + 2;
        if (i != max_index)
        {
            swap(i, max_index);
            shiftDown(max_index);
        }
    }

    public void offer(E data)
    {
        if(size == H.length)
            System.out.println("Queue is full");
        else
        {
            H[size] = (E) data;
            shiftUp(size);
            size++;
        }
    }

    public E peek()
    {
        return get(0);
    }

    public E poll()
    {
        if(size==0)
        {
            System.out.println("Queue is empty");
            return null;
        }
        else
        {
            E result = get(0);
            H[0] = H[size-1];
            size--;
            shiftDown(0);
            return result;
        }
    }

    public E remove(E e)
    {
        return e;
    }

    public void replace(E e1, E e2)
    {
        offer(e2);
        remove(e1);
    }
}
