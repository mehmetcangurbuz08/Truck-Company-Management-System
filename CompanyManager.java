// Create a CompanyManager class to write all necessary main functions and create AVL trees for this functions
public class CompanyManager {
    private AVLTree parkingLots = new AVLTree(); // main AVL tree for general usage
    private AVLTree availableParkingLotsForAdd = new AVLTree(); // AVL Tree for available parking lots
    private AVLTree readyParkingLots = new AVLTree(); // AVL tree to hold all suitable nodes for ready command
    private AVLTree loadAvailableTree = new AVLTree(); // AVL tree to hold all suitable nodes for load function

    // Method to create a parking lot and, insert these lots into parkingLots and availableParkingLotsForAdd trees.
    public void createParkingLot(int capacityConstraint, int truckLimit) {
        if (parkingLots.findParkingLot(capacityConstraint) == null) {
            ParkingLot newParkingLot = new ParkingLot(capacityConstraint, truckLimit);
            parkingLots.insertParkingLot(newParkingLot);
            availableParkingLotsForAdd.insertParkingLot(newParkingLot); // Add to both trees

        }
    }

    // Method to delete a parking lot from every tree which we create earlier
    public void deleteParkingLot(int capacityConstraint) {
        parkingLots.deleteParkingLot(capacityConstraint);
        availableParkingLotsForAdd.deleteParkingLot(capacityConstraint);
        readyParkingLots.deleteParkingLot(capacityConstraint);
        loadAvailableTree.deleteParkingLot(capacityConstraint);
    }

    // Create method to add Truck into the lot with the desired capacity limit
    public int addTruck(int truckId, int truckCapacity) {
        Truck newTruck = new Truck(truckId, truckCapacity, 0);
        ParkingLot suitableLot = availableParkingLotsForAdd.findParkingLot(truckCapacity); // Find a suitable parking lot for truck

        // If the parking lot is null or the lot is full, look for the largest available smaller lot
        while (suitableLot == null || suitableLot.isFull()) {
            if (suitableLot != null && suitableLot.isFull()) {
                // Remove the full lot from available parking lots tree
                availableParkingLotsForAdd.deleteParkingLot(suitableLot.getCapacityConstraint());
            }
            suitableLot = availableParkingLotsForAdd.findMaximumAvailableParkingLotSmaller(truckCapacity);

            if (suitableLot == null) {
                return -1; // No suitable parking lot found
            }

        }
        // Create a boolean to check the parking lot's waiting section is empty or not before the adding
        boolean isWaitingEmpty = suitableLot.getWaitingSection().isEmpty();
        suitableLot.addTruck(newTruck);
        // If the lot is empty before and not empty now, insert this parking lot into the ready parking Lots tree
        if (isWaitingEmpty) {
            readyParkingLots.insertParkingLot(suitableLot);
        }

        // If the parking lot becomes full, delete it from the available lots
        if (suitableLot.isFull()) {
            availableParkingLotsForAdd.deleteParkingLot(suitableLot.getCapacityConstraint());
        }

        return suitableLot.getCapacityConstraint();
    }
    //Create a function for making add truck operations in load function without create a new truck with 0 load
    public int addTruckForLoad1(Truck truck) {

        ParkingLot suitablelot = availableParkingLotsForAdd.findParkingLot(truck.getRemainingCapacity());

        // If the suitable lot is null or the lot is full, look for the largest available smaller lot
        while (suitablelot == null || suitablelot.isFull()) {
            if (suitablelot != null && suitablelot.isFull()) {
                // Remove the full lot from available lots
                availableParkingLotsForAdd.deleteParkingLot(suitablelot.getCapacityConstraint());
            }
            suitablelot = availableParkingLotsForAdd.findMaximumAvailableParkingLotSmaller(truck.getRemainingCapacity());

            if (suitablelot == null) {
                return -1; // No suitable parking lot found
            }
        }
        // create a boolean to check the parking lot's waiting section is empty or not before the adding
        boolean isWaitingEmpty = suitablelot.getWaitingSection().isEmpty();
        suitablelot.addTruck(truck);
        // If the lot is empty before and not empty now, insert this parking lot into the ready parking Lots tree
        if (isWaitingEmpty) {
            readyParkingLots.insertParkingLot(suitablelot);
        }

        // If the parking lot becomes full, remove it from the available lots
        if (suitablelot.isFull()) {
            availableParkingLotsForAdd.deleteParkingLot(suitablelot.getCapacityConstraint());
        }

        // Add the parking lot to the readyParkingLots if not already present
        return suitablelot.getCapacityConstraint();
    }

    // Create a function to move the truck to ready section from the waiting section of the parking lots
    public String ready(int capacity) {
        // Find the suitable parking lots from the Ready Parking Lots tree
        ParkingLot suitableLot = readyParkingLots.findParkingLot(capacity);
        Truck replacedTruck;

        // Create a boolean to check if the ready section is empty before moving trucks for usage load function
        boolean isReadySectionEmpty;
        //If the suitable lot is found move the truck to the ready section
        if (suitableLot != null) {
            isReadySectionEmpty = suitableLot.getReadySection().isEmpty(); // check ready section for load function
            replacedTruck = suitableLot.moveToReady();
        } else {
            // If no suitable truck is found in the desired lot, continue searching for the next larger lot
            suitableLot = readyParkingLots.findMinimumAvailableParkingLotBigger(capacity);

            // Break the loop if no more lots are found
            if (suitableLot == null) {
                return "-1";
            }
            isReadySectionEmpty = suitableLot.getReadySection().isEmpty(); // check ready section for load function
            // If a suitable parking lot is found, move the truck
            replacedTruck = suitableLot.moveToReady();


        }
        //If suitable lot has no more truck in the waiting section delete the parking lot from the Ready Parking Lot Tree
        if (suitableLot.getWaitingSection().isEmpty()) {
            readyParkingLots.deleteParkingLot(suitableLot.getCapacityConstraint());
        }
        //If the Ready section empty before the moving, İnsert suitable lot into do Load Available Tree
        if(isReadySectionEmpty){
            loadAvailableTree.insertParkingLot(suitableLot);
        }

        // Return the output according to replaced truck
        if (replacedTruck != null) {
            return replacedTruck.getTruckId() + " " + suitableLot.getCapacityConstraint();
        } else {
            return "-1"; // No truck was moved
        }
    }


    // Method to distribute a load into trucks in the desired lots
    public String load(int capacity, int loadAmount) {
        // Find the suitable parking lots from the Ready Parking Lots tree
        ParkingLot suitableLot = loadAvailableTree.findParkingLot(capacity);
        if (suitableLot == null) {
            suitableLot = loadAvailableTree.findMinimumAvailableParkingLotBigger(capacity);

            if (suitableLot == null) {
                return "-1";// Return if no more lots are found
            }
        }
        String output = "";
        //Create a while loop according to load amount to looping among parking lots
        while (loadAmount > 0) {
            //Find a suitable node for distributing loads among trucks of this node
            Node suitableNode = suitableLot.getReadySection().getHead();
            //Create a while loop according to load amount and suitable node for looping among trucks in ready section
            while (loadAmount > 0 && suitableNode != null) {
                Truck truck = suitableNode.data;
                //Choose a load to be loaded on the selected truck is found by taking the smallest among the capacity and load amount.
                int toLoad = Math.min(loadAmount, suitableLot.getCapacityConstraint());
                truck.load(toLoad);
                loadAmount -= toLoad;
                //Check the parking lot is full or not for adding the lot Available Parking Lot tree again
                boolean isAvailable = suitableLot.isFull();
                //Remove the loaded truck
                Truck loadedTruck = suitableLot.getReadySection().removeFirst();
                //If the suitable lot is full earlier, and it is not full right now we have to insert that parking lot into Available Parking Lot tree again
                if (isAvailable) {
                    availableParkingLotsForAdd.insertParkingLot(suitableLot);
                }
                //If the loaded truck is full, add this truck into suitable parking lot according to its Max Capacity
                if (loadedTruck.isFull()) {
                    loadedTruck.unload();
                    int newLotCapacity = addTruckForLoad1(loadedTruck);
                    if (newLotCapacity != -1) {
                        output += loadedTruck.getTruckId() + " " + newLotCapacity + " - ";
                    } else {
                        output += loadedTruck.getTruckId() + " -1 - ";
                    }
                } else { // If the loaded truck has remaining capacity, add this truck into suitable parking lot according to its Remaining Capacity.
                    int betterLotCapacity = addTruckForLoad1(loadedTruck);
                    if (betterLotCapacity != -1) {
                        output += loadedTruck.getTruckId() + " " + betterLotCapacity + " - ";
                    } else {
                        output += loadedTruck.getTruckId() + " -1 - ";
                    }
                }
                //If the ready section of suitable parking lot is empty after loading,remove it from da Load Available Tree
                if (suitableLot.getReadySection().isEmpty()) {
                    loadAvailableTree.deleteParkingLot(suitableLot.getCapacityConstraint());
                }
                //MOve go the next truck in the waiting section for loading
                suitableNode = suitableNode.next;
            }
            // If the ready section of the lot is empty exit the inner loop and search for the other suitable parking lots.
            suitableLot = loadAvailableTree.findMinimumAvailableParkingLotBigger(suitableLot.getCapacityConstraint());

            if (suitableLot == null) {
                break;
            }
        }

        if (!output.isEmpty()) {
            output = output.substring(0, output.length() - 3); // Son " - " kısmını kaldır
        } else {
            output = "-1";
        }

        return output;
    }
    //Create a method that gives the number of trucks with capacities larger than the given capacity
    public int count(int capacity) {
        return countTrucksInLarger(parkingLots.getRoot(), capacity);
    }

    // Count trucks in larger lots with recursion
    private int countTrucksInLarger(AVLNode node, int capacity) {
        if (node == null) {
            return 0;
        }

        int count = 0;
        if (node.parkingLot.getCapacityConstraint() > capacity) {
            count += node.parkingLot.getWaitingSection().size();
            count += node.parkingLot.getReadySection().getSize();
        }
        count += countTrucksInLarger(node.left, capacity);
        count += countTrucksInLarger(node.right, capacity);

        return count;
    }
}



