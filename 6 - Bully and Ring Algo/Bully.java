import java.util.*;

public class Bully {
    int coordinator; // Variable to store the ID of the coordinator
    int max_processes; // Total number of processes in the system
    boolean processes[]; // Array to track the status (up or down) of each process

    // Constructor to initialize the system with a given number of processes
    public Bully(int max) {
        max_processes = max; // Set the number of processes
        processes = new boolean[max_processes]; // Initialize array to track process status
        coordinator = max; // Initially, the highest process ID is the coordinator

        System.out.println("Creating processes..");
        for (int i = 0; i < max; i++) {
            processes[i] = true; // Mark each process as active
            System.out.println("P" + (i + 1) + " created"); // Notify creation
        }
        System.out.println("Process P" + coordinator + " is the coordinator"); // Display the initial coordinator
    }

    // Method to display the status of each process and the current coordinator
    void displayProcesses() {
        for (int i = 0; i < max_processes; i++) {
            if (processes[i]) {
                System.out.println("P" + (i + 1) + " is up");
            } else {
                System.out.println("P" + (i + 1) + " is down");
            }
        }
        System.out.println("Process P" + coordinator + " is the coordinator");
    }

    // Method to activate a process that is down
    void upProcess(int process_id) {
        if (!processes[process_id - 1]) {
            processes[process_id - 1] = true;
            System.out.println("Process " + process_id + " is now up.");
        } else {
            System.out.println("Process " + process_id + " is already up.");
        }
    }

    // Method to deactivate an active process
    void downProcess(int process_id) {
        if (!processes[process_id - 1]) {
            System.out.println("Process " + process_id + " is already down.");
        } else {
            processes[process_id - 1] = false;
            System.out.println("Process " + process_id + " is down.");
        }
    }

    // Method to run an election from a given process ID
    void runElection(int process_id) {
        if (!processes[process_id - 1]) {
            System.out.println("Process " + process_id + " is down and cannot initiate an election.");
            return; // Exit the method if the process is down.
        }

        coordinator = process_id; // Temporarily set the coordinator to the initiating process
        boolean keepGoing = true; // Control variable for the loop

        for (int i = process_id; i < max_processes && keepGoing; i++) {
            System.out.println("Election message sent from process " + process_id + " to process " + (i + 1));

            if (processes[i]) { // If the process is up, recursively start an election from this process
                keepGoing = false; // Stop the current election loop
                runElection(i + 1); // Start new election from next process
            }
        }

       displayProcesses(); // display the status of each process and the current coordinator
    }

    public static void main(String args[]) {
        Bully bully = null;
        int max_processes = 0, process_id = 0;
        int choice = 0;
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Bully Algorithm");
            System.out.println("1. Create processes");
            System.out.println("2. Display processes");
            System.out.println("3. Up a process");
            System.out.println("4. Down a process");
            System.out.println("5. Run election algorithm");
            System.out.println("6. Exit Program");
            System.out.print("Enter your choice:- ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter the number of processes:- ");
                    max_processes = sc.nextInt();
                    bully = new Bully(max_processes); // Initialize the Bully system with specified number of processes
                    break;
                case 2:
                    bully.displayProcesses(); // Display the status of all processes and the coordinator
                    break;
                case 3:
                    System.out.print("Enter the process number to up:- ");
                    process_id = sc.nextInt();
                    bully.upProcess(process_id); // Activate a specified process
                    break;
                case 4:
                    System.out.print("Enter the process number to down:- ");
                    process_id = sc.nextInt();
                    bully.downProcess(process_id); // Deactivate a specified process
                    break;
                case 5:
                    System.out.print("Enter the process number which will perform election:- ");
                    process_id = sc.nextInt();
                    bully.runElection(process_id); // Initiate an election from a specified process
                    break;
                case 6:
                    System.exit(0); // Exit the program
                    break;
                default:
                    System.out.println("Error in choice. Please try again."); // Handle invalid input choices
                    break;
            }
        }
    }
}