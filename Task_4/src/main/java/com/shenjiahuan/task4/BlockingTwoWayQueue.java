package com.shenjiahuan.task4;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;


public final class BlockingTwoWayQueue<T> implements BlockingQueue<T>
{
    // we add and remove only from the end of the queue
    private final BlockingDeque<T> deque;
    private int threshold;

    public BlockingTwoWayQueue(int threshold) {
        deque = new LinkedBlockingDeque<T>();
        this.threshold = threshold;
    }

    public boolean add(T e) {
        deque.addLast(e);
        return true;
    }

    public boolean contains(Object o) {
        return deque.contains(o);
    }

    public int drainTo(Collection<? super T> c) {
        return deque.drainTo(c);
    }

    public int drainTo(Collection<? super T> c, int maxElements) {
        return deque.drainTo(c,maxElements);
    }

    public boolean offer(T e) {
        return deque.offerLast(e);
    }

    public boolean offer(T e, long timeout, TimeUnit unit) throws InterruptedException {
        return deque.offerLast(e,timeout,unit);
    }

    public T poll(long timeout, TimeUnit unit) throws InterruptedException {
        if (deque.size() > threshold) {
            return deque.pollLast(timeout, unit);
        } else {
            return deque.pollFirst(timeout, unit);
        }
    }

    public void put(T e) throws InterruptedException {
        deque.putLast(e);
    }

    public int remainingCapacity() {
        return deque.size();
    }

    public boolean remove(Object o) {
        return deque.remove(o);
    }

    public T take() throws InterruptedException {
        if (deque.size() > threshold) {
            return deque.takeLast();
        } else {
            return deque.takeFirst();
        }
    }

    public T element() {
        if (deque.isEmpty()) {
            throw new NoSuchElementException("empty stack");
        }

        return deque.element();
    }

    public T peek() {
        if (deque.size() > threshold) {
            return deque.peekLast();
        } else {
            return deque.peekFirst();
        }
    }

    public T poll() {
        if (deque.size() > threshold) {
            return deque.pollLast();
        } else {
            return deque.pollFirst();
        }
    }

    public T remove() {
        if (deque.isEmpty()) {
            throw new NoSuchElementException("empty stack");
        }

        if (deque.size() > threshold) {
            return deque.pollLast();
        } else {
            return deque.pollFirst();
        }
    }

    public boolean addAll(Collection<? extends T> c) {
        for (T e : c) {
            deque.add(e);
        }
        return true;
    }

    public void clear() {
        deque.clear();
    }

    public boolean containsAll(Collection<?> c) {
        return deque.containsAll(c);
    }

    public boolean isEmpty() {
        return deque.isEmpty();
    }

    public Iterator<T> iterator() {
        return deque.descendingIterator();
    }

    public boolean removeAll(Collection<?> c) {
        return deque.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return deque.retainAll(c);
    }

    public int size() {
        return deque.size();
    }

    public Object[] toArray() {return deque.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return deque.toArray(a);
    }
}
