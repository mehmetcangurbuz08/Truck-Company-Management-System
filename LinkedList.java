//Create a linked list to use for ready section of the parking lots and implement it with necessary functions
class LinkedList {
    private Node head;
    private Node tail;
    private int size;

    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // Add a truck to the end of the list
    public void addLast(Truck data) {
        Node newNode = new Node(data);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    // Remove the first truck
    public Truck removeFirst() {
        if (head == null) {
            System.out.println("List is empty.");
            return null;
        }

        Truck data = head.data;
        head = head.next;

        if (head != null) {
            head.prev = null;
        } else {
            tail = null;
        }

        size--;
        return data;
    }
    public Node getHead() {
        return head;
    }
    // Control the list is empty or not
    public boolean isEmpty() {
        return size == 0;
    }
    public int getSize() {
        return size;
    }


}