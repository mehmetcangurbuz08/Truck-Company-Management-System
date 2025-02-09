//Implement an avl tree class for usage of our avl trees and create necessary functions in this class
class AVLTree {
    private AVLNode root;

    public AVLNode getRoot() {
        return root;
    }

    public void insertParkingLot(ParkingLot parkingLot) {
        root = insert(root, parkingLot);
    }

    private AVLNode insert(AVLNode node, ParkingLot parkingLot) {
        if (node == null) {
            return new AVLNode(parkingLot);
        }

        // Insert node according to capacity constraint
        if (parkingLot.getCapacityConstraint() < node.parkingLot.getCapacityConstraint()) {
            node.left = insert(node.left, parkingLot);
        } else if (parkingLot.getCapacityConstraint() > node.parkingLot.getCapacityConstraint()) {
            node.right = insert(node.right, parkingLot);
        } else {
            return node;  // Doubles are not allowed
        }

        // Update the height
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Balance the node
        return balanceNode(node);
    }

    // Helper method to get the height
    private int height(AVLNode node) {
        return (node == null) ? 0 : node.height;
    }

    // Balancing logic to ensure the AVL Tree properties
    private AVLNode balanceNode(AVLNode node) {
        int balance = getBalance(node);

        // Left Heavy (Left-Left case)
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rightRotate(node);
        }

        // Left Heavy (Left-Right case)
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Heavy (Right-Right case)
        if (balance < -1 && getBalance(node.right) <= 0) {
            return leftRotate(node);
        }

        // Right Heavy (Right-Left case)
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;  // Return the balanced node
    }

    // Helper method to get the balance factor of a node
    private int getBalance(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return height(node.left) - height(node.right);
    }

    // Right rotation
    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        // Making rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        // Return new root
        return x;
    }

    // Left rotation
    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        // Return new root
        return y;
    }


    // Find a parking lot by capacity constraint
    public ParkingLot findParkingLot(int capacityConstraint) {
        return search(root, capacityConstraint);
    }
    //Create a function for searching parking lots according to their capacity constraint with traversing the tree
    private ParkingLot search(AVLNode node, int capacityConstraint) {
        while (node != null) {
            if (capacityConstraint == node.parkingLot.getCapacityConstraint()) {
                return node.parkingLot;
            } else if (capacityConstraint < node.parkingLot.getCapacityConstraint()) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return null;
    }

    // Delete a parking lot according to capacity constraint
    public void deleteParkingLot(int capacityConstraint) {
        root = deleteNode(root, capacityConstraint);
    }

    private AVLNode deleteNode(AVLNode node, int capacityConstraint) {
        if (node == null) {
            return null; //If the node is not found in the tree
        }

        // Traverse the tree to find the node to delete
        if (capacityConstraint < node.parkingLot.getCapacityConstraint()) {
            node.left = deleteNode(node.left, capacityConstraint);
        } else if (capacityConstraint > node.parkingLot.getCapacityConstraint()) {
            node.right = deleteNode(node.right, capacityConstraint);
        } else {
            // Node to be deleted is found
            if (node.left == null || node.right == null) {
                // One child or no child case
                AVLNode temp = (node.left != null) ? node.left : node.right;
                node = temp; // If temp is null, node becomes null (no children case)
            } else {
                // Node with two children: Get the inorder successor (smallest in the right subtree)
                AVLNode temp = getMinValueNode(node.right);
                node.parkingLot = temp.parkingLot; // Copy the inorder successor's data to this node
                node.right = deleteNode(node.right, temp.parkingLot.getCapacityConstraint()); // Delete the successor
            }
        }

        // If the node becomes null after the deletion, return it
        if (node == null) {
            return null;
        }

        // Update the height of the current node
        node.height = Math.max(height(node.left), height(node.right)) + 1;

        // Balance the node
        return balanceAfterDeletion(node);
    }

    private AVLNode balanceAfterDeletion(AVLNode node) {
        int balance = getBalance(node);

        // Left heavy (balance factor > 1)
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rightRotate(node);
        }

        // Left-Right case
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right heavy (balance factor < -1)
        if (balance < -1 && getBalance(node.right) <= 0) {
            return leftRotate(node);
        }

        // Right-Left case
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node; // Return the balanced node
    }
    // Helper method to get the node with the minimum value
    private AVLNode getMinValueNode(AVLNode node) {
        AVLNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    // Find the largest available parking lot smaller than a given capacity
    public ParkingLot findMaximumAvailableParkingLotSmaller(int capacity) {
        AVLNode current = root;
        ParkingLot maximum = null;

        // Traverse the AVL tree
        while (current != null) {
            if (current.parkingLot.getCapacityConstraint() < capacity) {
                // Update largest if the current parking lot's capacity is bigger than the current one
                if (maximum == null || current.parkingLot.getCapacityConstraint() > maximum.getCapacityConstraint()) {
                    maximum = current.parkingLot;
                }
                current = current.right; // Move right to find bigger values
            } else {
                current = current.left; // Move left to find smaller values
            }
        }
        return maximum;
    }

    // Find the smallest available parking lot bigger than a given capacity
    public ParkingLot findMinimumAvailableParkingLotBigger(int capacity) {
        AVLNode current = root;
        ParkingLot minimum = null;

        // Traverse the AVL tree
        while (current != null) {
            if (current.parkingLot.getCapacityConstraint() > capacity) {
                // Update smallest if the current parking lot's capacity is smaller than the current one
                if (minimum == null || current.parkingLot.getCapacityConstraint() < minimum.getCapacityConstraint()) {
                    minimum = current.parkingLot;
                }
                current = current.left; // Move left to find smaller values
            } else {
                current = current.right; // Move right to find larger values
            }
        }
        return minimum;
    }





}





