//İmplement a queue to create a ready section in parking lots with using linked list.
class Queue {
    private LinkedList list;

    public Queue() {
        this.list = new LinkedList();
    }

    // Enqueue a truck
    public void enqueue(Truck data) {
        list.addLast(data);
    }

    // Dequeue a truck
    public Truck dequeue() {
        return list.removeFirst();
    }

    //Checks if the list is empty
    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.getSize();
    }
}