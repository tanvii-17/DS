import java.util.*;

public class Ring {
    int max_processes; // Total number of processes in the ring
    int coordinator; // ID of the current coordinator
    boolean processes[]; // Array to track the status (up or down) of each process
    ArrayList<Integer> pid; // List to hold process IDs during elections

    // Constructor to initialize the Ring with a specified maximum number of
    // processes
    public Ring(int max) {
        coordinator = max; // Set the initial coordinator to the maximum process ID
        max_processes = max; // Set the total number of processes
        pid = new ArrayList<Integer>(); // Initialize the ArrayList to hold process IDs
        processes = new boolean[max]; // Initialize the array to track process statuses

        // Create processes and set them all to up (true)
        for (int i = 0; i < max; i++) {
            processes[i] = true;
            System.out.println("P" + (i + 1) + " created.");
        }
        System.out.println("P" + (coordinator) + " is the coordinator");
    }

    // Display the status of all processes and who is the coordinator
    void displayProcesses() {
        for (int i = 0; i < max_processes; i++) {
            if (processes[i])
                System.out.println("P" + (i + 1) + " is up.");
            else
                System.out.println("P" + (i + 1) + " is down.");
        }
        System.out.println("P" + (coordinator) + " is the coordinator");
    }

    // Method to mark a specific process as up (active)
    void upProcess(int process_id) {
        if (!processes[process_id - 1]) {
            processes[process_id - 1] = true;
            System.out.println("Process P" + (process_id) + " is up.");
        } else {
            System.out.println("Process P" + (process_id) + " is already up.");
        }
    }

    // Method to mark a specific process as down (inactive)
    void downProcess(int process_id) {
        if (!processes[process_id - 1]) {
            System.out.println("Process P" + (process_id) + " is already down.");
        } else {
            processes[process_id - 1] = false;
            System.out.println("Process P" + (process_id) + " is down.");
        }
    }

    // Helper method to display contents of the ArrayList (used during elections)
    void displayArrayList(ArrayList<Integer> pid) {
        System.out.print("[ ");
        for (Integer x : pid) {
            System.out.print(x + " ");
        }
        System.out.print(" ]\n");
    }

    // Initiate the election process from a specified process ID
    void initElection(int process_id) {
        if (processes[process_id - 1]) { // Check if the process is up
            pid.add(process_id); // Start the election with the initiating process ID

            int temp = process_id;

            System.out.print("Process P" + process_id + " sending the following list:- ");
            displayArrayList(pid);

            // Pass the PID list around the ring until it reaches back to the initiator
            while (temp != process_id - 1) {
                if (processes[temp]) { // Ensure the process is up before adding its ID
                    pid.add(temp + 1);
                    System.out.print("Process P" + (temp + 1) + " sending the following list:- ");
                    displayArrayList(pid);
                }
                temp = (temp + 1) % max_processes; // Wrap around the ring
            }
            coordinator = Collections.max(pid); // Elect the process with the highest ID as coordinator
            System.out.println("Process P" + process_id + " has declared P" + coordinator + " as the coordinator");
            pid.clear(); // Clear the list for future elections
        } else {
            System.out.println("Process " + process_id + " is down and cannot initiate an election.");
        }
    }

    public static void main(String args[]) {
        Ring ring = null;
        int max_processes = 0, process_id = 0;
        int choice = 0;
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Ring Algorithm");
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
                    System.out.print("Enter the total number of processes:- ");
                    max_processes = sc.nextInt();
                    ring = new Ring(max_processes); // Create the Ring with the specified number of processes
                    break;
                case 2:
                    ring.displayProcesses(); // Display current status of all processes and the coordinator
                    break;
                case 3:
                    System.out.print("Enter the process to up:- ");
                    process_id = sc.nextInt();
                    ring.upProcess(process_id); // Mark a specified process as active
                    break;
                case 4:
                    System.out.print("Enter the process to down:- ");
                    process_id = sc.nextInt();
                    ring.downProcess(process_id); // Mark a specified process as inactive
                    break;
                case 5:
                    System.out.print("Enter the process which will initiate election:- ");
                    process_id = sc.nextInt();
                    ring.initElection(process_id); // Initiate an election from a specified process
                    break;
                case 6:
                    System.exit(0); // Exit the program
                    break;
                default:
                    System.out.println("Error in choice. Please try again."); // Handle invalid choices
                    break;
            }
        }
    }
}