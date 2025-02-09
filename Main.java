/**
 * This program is an application that provides general management of the capacity, load and parking areas of a truck company.
 * @author Mehmet Can Gürbüz, Student ID: 2022400177
 * @since Date: 07.11.2024
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
//Create a main class for reading inputs , processing given functions and giving the output file.
public class Main {
    public static void main(String[] args) {
        // Create a CompanyManager object
        CompanyManager companySystem = new CompanyManager();
        try {
            // Reading the input file
            File inputFile = new File(args[0]);
            Scanner scanner = new Scanner(inputFile);

            // Writing the output file
            FileWriter writer = new FileWriter(args[1], false); // false ensures the file is overwritten

            //Iteratively read every line and complete the function
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                String function = parts[0];

                // Prepare to store output for each function
                String output = "";

                // Process the functions
                if (function.equals("create_parking_lot")) {
                    int capacityConstraint = Integer.parseInt(parts[1]);
                    int truckLimit = Integer.parseInt(parts[2]);
                    companySystem.createParkingLot(capacityConstraint, truckLimit);
                } else if (function.equals("delete_parking_lot")) {
                    int capacityConstraint = Integer.parseInt(parts[1]);
                    companySystem.deleteParkingLot(capacityConstraint);
                } else if (function.equals("add_truck")) {
                    int truckId = Integer.parseInt(parts[1]);
                    int truckCapacity = Integer.parseInt(parts[2]);
                    int result = companySystem.addTruck(truckId, truckCapacity);
                    output = String.valueOf(result);
                } else if (function.equals("ready")) {
                    int readyCapacity = Integer.parseInt(parts[1]);
                    output = companySystem.ready(readyCapacity);
                } else if (function.equals("count")) {
                    int countCapacity = Integer.parseInt(parts[1]);
                    int result = companySystem.count(countCapacity);
                    output = String.valueOf(result);
                } else if (function.equals("load")) {
                    int loadCapacity = Integer.parseInt(parts[1]);
                    int loadAmount = Integer.parseInt(parts[2]);
                    output = companySystem.load(loadCapacity, loadAmount);
                }
                // Write the output to the file
                if (!output.isEmpty()) {
                    writer.write(output + "\n");
                }
            }
            writer.flush();
            // Close the scanner and writer
            scanner.close();
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}