# Truck Company Management System

## Overview
This project implements a truck company management system, optimizing truck allocation and parking using custom data structures and AVL trees. The system efficiently manages truck assignments to parking lots, ensuring optimal performance in large-scale scenarios.

---

## Features
- **Truck and Parking Lot Management:** Create, assign, and manage trucks within parking lots dynamically.
- **Custom Data Structures:** Utilize custom implementations of linked lists, queues, and AVL trees for optimal data storage and retrieval.
- **Optimized Resource Allocation:** Ensures efficient allocation of parking spaces to trucks using AVL trees.

---

## Project Structure

```
TruckCompanyManager/
|-- AVLNode.java                 # Represents nodes in the AVL tree
|-- AVLTree.java                 # Implements AVL tree logic for optimized search
|-- CompanyManager.java          # Core logic for truck and parking lot management
|-- LinkedList.java              # Custom linked list implementation
|-- Main.java                    # Entry point of the application
|-- Node.java                    # General-purpose node structure
|-- ParkingLot.java              # Represents a parking lot
|-- Queue.java                   # Custom queue implementation
|-- Truck.java                   # Represents a truck
```

---

## How It Works

1. **Truck and Parking Lot Initialization:**
    - Trucks and parking lots are created using the **Truck** and **ParkingLot** classes.
    - The **CompanyManager** class handles interactions between trucks and parking lots.

2. **Efficient Resource Allocation:**
    - AVL trees are used to store and quickly retrieve information about available parking lots.
    - Custom linked lists and queues ensure efficient truck processing and task management.

3. **Dynamic Updates:**
    - When trucks arrive or depart, the AVL tree and associated data structures are updated to reflect the current state.

---

## Example Workflow
1. A truck arrives and requests a parking spot.
2. The system uses the AVL tree to find an optimal parking lot.
3. The truck is assigned a spot, and the data structures are updated accordingly.
4. When the truck leaves, the spot becomes available for future assignments.

---

## Installation and Usage

1. Clone the repository:
    ```bash
    git clone <repository-url>
    cd TruckCompanyManager
    ```

2. Compile the Java files:
    ```bash
    javac *.java
    ```

3. Run the application:
    ```bash
    java Main
    ```

---

## Future Improvements
- Implement priority-based truck scheduling for time-sensitive deliveries.
- Add real-time monitoring of parking lot occupancy.
- Integrate traffic data for dynamic truck routing and optimization.
