class Truck {

    private int truckId;
    private int maxCapacity;
    private int currentLoad;

    public Truck(int truckId, int maxCapacity, int currentLoad) {
        this.truckId = truckId;
        this.maxCapacity = maxCapacity;
        this.currentLoad = currentLoad;
    }
    public int getTruckId() {
        return truckId;
    }
    public int getRemainingCapacity() {
        return maxCapacity - currentLoad;
    }

    public void load(int load) {
        currentLoad += load;
    }

    public boolean isFull() {
        return currentLoad == maxCapacity;
    }

    public void unload() {
        currentLoad = 0;
    }
}
