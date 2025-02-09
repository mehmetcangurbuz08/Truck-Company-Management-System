//Create a class to hold parking lots data and use these node in avl trees
class AVLNode {
    ParkingLot parkingLot;
    AVLNode left, right;
    int height;

    public AVLNode(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
        this.left = null;
        this.right = null;
        this.height = 1;
    }
}
