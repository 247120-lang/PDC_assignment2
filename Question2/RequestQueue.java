public class RequestQueue {
    private char[] queueArray;
    private int front;
    private int rear;
    private int size;

    public RequestQueue(int capacity) {
        this.queueArray = new char[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    public void enqueue(char c) {
        if (size < queueArray.length) {
            rear = (rear + 1) % queueArray.length;
            queueArray[rear] = c;
            size++;
        }
    }

    public char dequeue() {
        if (!isEmpty()) {
            char item = queueArray[front];
            front = (front + 1) % queueArray.length;
            size--;
            return item;
        }
        return '\0';
    }

    public boolean isEmpty() {
        return size == 0;
    }
}