package cn.ypf88.frame.servlets;

import java.util.ListIterator;

/**
 * @author ypf
 * @version V1.0.0
 * @project ypfFrame
 * @package cn.ypf88.frame.servlets
 * @date 2016/5/12 22:53
 * @describe 链式列表
 */
public class LinkedList<T> {
    private Linked<T> head;
    private Linked<T> last;
    private int count;
    private int hashCode;

    public void add(T data) {
        if (this.count == 0) {
            this.head = this.last = new Linked<T>(data);
        } else {
            Linked<T> newData = new Linked<T>(data);
            this.last.setNext(newData);
            this.last = newData;
        }
        this.count++;
    }

    public T remove(T data) {
        if (this.head == null) {
            return null;
        }
        if (this.head != null && this.head.getData().equals(data)) {
            T oldData = this.head.getData();
            this.head = this.head.getNext();
            return oldData;
        }
        Linked<T> prev = head;
        Linked<T> curr = prev.getNext();
        while (curr != null) {
            if (curr.getData().equals(data)) {
                T oldData = curr.getData();
                prev.setNext(curr.getNext());
                return oldData;
            }
            prev = curr;
            curr = prev.getNext();
        }
        return null;
    }

    public ListIterator<T> listIterator() {
        return new LinkedListListIterator<T>(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Linked<T> temp = null;
        if (head != null) {
            temp = head;
            while (temp.getNext() != null) {
                sb.append(temp.toString() + ", ");
                temp = temp.getNext();
            }
            sb.append(temp.toString());
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public int hashCode() {
        if (this.hashCode == 0) {
            int res = 17;
            res = 37 * res + head.hashCode();
            res = 37 * res + last.hashCode();
            res = 37 * res + count;
            this.hashCode = res;
        }
        return this.hashCode;
    }

    /**
     * 内部链式元素
     *
     * @author ypf
     * @version V1.0.0
     */
    private static class Linked<T> {
        private T data;
        private Linked<T> next;
        private int hashCode;

        Linked(T data) {
            this.data = data;
            this.next = null;
        }

        Linked(T data, Linked<T> next) {
            this.data = data;
            this.next = next;
        }

        public T getData() {
            return this.data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Linked<T> getNext() {
            return this.next;
        }

        public void setNext(Linked<T> next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return data.toString();
        }

        @Override
        public boolean equals(Object l) {
            if (l == null) return false;
            if (l instanceof Linked) {
                Linked link = (Linked) l;
                if (data.equals(link.data)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public int hashCode() {
            if (this.hashCode == 0) {
                int result = 17;
                result = 37 * result + data.hashCode();
                result = 37 * result + (next != null ? next.hashCode() : 0);
                this.hashCode = result;
            }
            return this.hashCode;
        }
    }

    private static class LinkedListListIterator<T> implements ListIterator<T> {

        private final LinkedList<T> links;
        private Linked<T> prev, curr;
        private int currIndex;

        LinkedListListIterator(LinkedList<T> lists) {
            this.links = lists;
            this.currIndex = -1;
        }

        @Override
        public boolean hasNext() {
            return (this.curr == null && this.links.head != null && this.links.head.getNext() != null) || this.curr.getNext() != null;
        }

        @Override
        public T next() {
            if (curr == null) {
                prev = null;
                curr = this.links.head;
                this.currIndex = 0;
                return curr.getData();
            } else if (curr.getNext() != null) {
                prev = curr;
                curr = curr.getNext();
                this.currIndex++;
                return curr.getData();
            } else {
                return null;
            }
        }

        @Override
        public boolean hasPrevious() {
            return this.curr != null && !this.curr.equals(this.links.head);
        }

        @Override
        public T previous() {
            T data = curr.getData();
            if (curr.equals(this.links.head)) {
                curr = null;
                return data;
            }
            curr = prev;
            prev = null;
            Linked<T> tmp = this.links.head;
            while (!curr.equals(tmp)) {
                prev = tmp;
                tmp = tmp.getNext();
            }
            return data;
        }

        @Override
        public int nextIndex() {
            return this.currIndex + 1;
        }

        @Override
        public int previousIndex() {
            return this.currIndex - 2;
        }

        @Override
        public void remove() {
            if (prev != null) {
                prev.setNext(curr.getNext());
            } else {
                this.links.head = curr.getNext();
            }
            curr = curr.getNext();
            this.currIndex--;
        }

        @Override
        public void set(T t) {
            curr.setData(t);
        }

        @Override
        public void add(T t) {
            if (curr != null) {
                Linked<T> next = curr.getNext();
                Linked<T> data = new Linked<T>(t, next);
                curr.setNext(data);
                prev = curr;
                curr = data;
            } else {
                Linked<T> data = new Linked<T>(t);
                data.setNext(this.links.head);
                this.links.head = data;
            }
            this.currIndex++;
        }

        @Override
        public String toString() {
            return links.toString();
        }
    }

    public static void main(String[] args) {
        LinkedList<String> link = new LinkedList<String>();
        System.out.println("create link :" + link);
        link.add("1");
        System.out.println("add 1 :" + link);
        link.add("2");
        System.out.println("add 2 :" + link);
        link.add("3");
        System.out.println("add 3 :" + link);
        link.add("4");
        System.out.println("add 4 :" + link);
        link.add("5");
        System.out.println("add 5 :" + link);
        link.remove("1");
        System.out.println("remove 1 :" + link);
        link.remove("5");
        System.out.println("remove 5 :" + link);
        link.remove("3");
        System.out.println("remove 3 :" + link);
        System.out.println("list iterator:");
        ListIterator<String> list = link.listIterator();
        while (list.hasNext()) {
            System.out.print(((LinkedListListIterator) list).curr + "=" + list.nextIndex() + "=" + list.next() + ", ");
        }
        System.out.println();
        System.out.println(((LinkedListListIterator) list).curr + "=" + list.nextIndex() + "=" + list);
        list.set("10");
        System.out.println(((LinkedListListIterator) list).curr + "=" + list.nextIndex() + "=" + list);
        System.out.println("==============");
        for (int i = 0; i < 1; i++) {
            if (list.hasPrevious()) {
                list.previous();
            }
        }
        list.add("124");
        System.out.println(((LinkedListListIterator) list).curr + "=" + list.nextIndex() + "=" + list);
        for (int i = 0; i < 0; i++) {
            if (list.hasPrevious()) {
                list.previous();
                System.out.println(((LinkedListListIterator) list).curr);
            }
        }
        list.remove();
        System.out.println(((LinkedListListIterator) list).curr + "=" + list.nextIndex() + "=" + list);
    }
}
