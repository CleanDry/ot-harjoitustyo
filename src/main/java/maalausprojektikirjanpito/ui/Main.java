package maalausprojektikirjanpito.ui;

import java.util.Scanner;
import maalausprojektikirjanpito.domain.ManagerService;

public class Main {
    public static void main(String[] args) {
        System.out.println("Ohjelma kääntyy ja ajaa!");
        
        Scanner lukija = new Scanner(System.in);
        ManagerService service = new ManagerService();
        
        System.out.println("Enter username:");
        String UID = lukija.nextLine();
        System.out.println("Enter password:");
        String PW = lukija.nextLine();
        
        service.createUser(UID, PW);
        service.login(UID, PW);
        System.out.println(service.getLoggedIn());
        service.logout();
        System.out.println(service.getLoggedIn());
    }
}
