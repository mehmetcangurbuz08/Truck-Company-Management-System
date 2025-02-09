//Create a class to hold truck data's.
class Node {
    Truck data;
    Node next;
    Node prev;

    public Node(Truck data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}
