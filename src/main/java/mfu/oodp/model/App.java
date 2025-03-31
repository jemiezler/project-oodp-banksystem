package mfu.oodp.model;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankSystem bankSystem = new BankSystem();
        bankSystem.loadClientsFromFile();

        while (true) {
            System.out.println("====== Welcome to the Bank System ======");
            System.out.println("\nPlease choose an option:");
            System.out.println(
                    "\n1. Add Client  \n2. Deposit  \n3. Withdraw  \n4. Show Balance    \n5. View Requests   \n6. Approve Request  \n7. Reject Request \n8.Exit ");
            System.out.print("\nEnter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter client name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter initial balance: ");
                    double balance = scanner.nextDouble();
                    bankSystem.addClient(new Client(name, balance));
                    bankSystem.saveClientsToFile();
                    break;
                case 2:
                    System.out.print("Enter client name: ");
                    Client client = bankSystem.getClient(scanner.nextLine());
                    if (client != null) {
                        System.out.print("Enter deposit amount: ");
                        client.deposit(scanner.nextDouble());
                        bankSystem.saveClientsToFile();
                    } else {
                        System.out.println("Client not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter client name: ");
                    String clientName = scanner.nextLine();
                    System.out.print("Enter withdrawal amount: ");
                    double amount = scanner.nextDouble();
                    bankSystem.submitWithdrawalRequest(clientName, amount);
                    break;
                case 4:
                    bankSystem.displayAllClients();
                    break;

                case 5:
                    bankSystem.viewWithdrawalRequests();
                    break;
        
                case 6:
                    System.out.print("Enter client name to approve request: ");
                    clientName = scanner.nextLine();
                    bankSystem.approveWithdrawalRequest(clientName);
                    break;
                case 7:
                    System.out.print("Enter client name to reject request: ");
                    clientName = scanner.nextLine();
                    bankSystem.rejectWithdrawalRequest(clientName);
                    break;
                case 8:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice.");

            }
        }
    }
}
