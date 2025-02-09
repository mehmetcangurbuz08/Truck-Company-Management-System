//Create a parking lot class to make necessary operations in parking lots.
class ParkingLot {
    private int capacityConstraint;
    private int truckLimit;
    private Queue waitingSection;  // Use Queue for the waiting section
    private LinkedList readySection;  // Use LinkedList for the ready section

    public ParkingLot(int capacityConstraint, int truckLimit) {
        this.capacityConstraint = capacityConstraint;
        this.truckLimit = truckLimit;
        this.waitingSection = new Queue();
        this.readySection = new LinkedList();
    }

    public int getCapacityConstraint() {
        return capacityConstraint;
    }

    public Queue getWaitingSection() {
        return waitingSection;
    }

    public LinkedList getReadySection() {
        return readySection;
    }
    //Control parking lot is full or not
    public boolean isFull() {
        return (waitingSection.size() + readySection.getSize()) >= truckLimit;
    }
    //Add truck to parking lot's waiting section
    public void addTruck(Truck truck) {
        if (!isFull() && truck.getRemainingCapacity() >= capacityConstraint) {
            waitingSection.enqueue(truck);
        }
    }
    // Move truck to ready section from waiting section
    public Truck moveToReady() {
        if (!waitingSection.isEmpty()) {
            Truck truck = waitingSection.dequeue();
            readySection.addLast(truck);
            return truck;
        }
        return null;
    }
}
