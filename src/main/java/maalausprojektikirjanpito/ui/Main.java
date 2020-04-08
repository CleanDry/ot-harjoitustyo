package maalausprojektikirjanpito.ui;

import java.util.Scanner;
import maalausprojektikirjanpito.domain.ManagerService;

public class Main {
    
    /**
     * Main method of the program, for now.
     * @param args 
     */
    public static void main(String[] args) {
        System.out.println("Ohjelma kääntyy ja ajaa!");
        
        Scanner lukija = new Scanner(System.in);
        ManagerService service = new ManagerService("db/example.db");
        
        while (true) {
            System.out.println("(C)reate new, (L)ogin or (Q)uit:  ");
            String input = lukija.nextLine();

            if (input.equals("C")) {
                System.out.println("Enter username:");
                String username = lukija.nextLine();
                System.out.println("Enter password:");
                String password = lukija.nextLine();
                System.out.println(service.getLoggedIn());
                service.createUser(username, password);
                service.login(username, password);
                System.out.println(service.getLoggedIn());
            } else if (input.equals("L")) {
                System.out.println("Enter username:");
                String username = lukija.nextLine();
                System.out.println("Enter password:");
                String password = lukija.nextLine();
                System.out.println(service.getLoggedIn());
                service.login(username, password);
                System.out.println(service.getLoggedIn());
            } else if (input.equals("Q")) {
                break;
            } else {
                System.out.println("Unknown command, " + service.getLoggedIn());
            }
            
            while (true) {
                System.out.println("(L)ogout or (P)rint logged user: ");
                String i = lukija.nextLine();
                if (i.equals("L")) {
                    break;
                } else {
                    System.out.println(service.getLoggedIn());
                }
            }
        }
        
        
    }
}
