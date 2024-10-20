import com.mysql.cj.exceptions.ConnectionIsClosedException;

import java.sql.*;
import java.util.Scanner;

public class BankingSystem {

    private static final String url = "jdbc:mysql://localhost:3306/bank";
    private static final String username = "root";
    private static final String pass = "12345";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try {// loading driver
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("drivers loaded ");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());

        }
        try {
            Connection connection = DriverManager.getConnection(url, username, pass);
            Bank bank=new Bank();
            while (true)
            {
                System.out.println(" Banking system menu");
                System.out.println("1.create Account");
                System.out.println("2.Deposit");
                System.out.println("3.Credit");
                System.out.println("4.Get Balance");
                System.out.println("5. Account Details");
                System.out.println("6.Exit");

                System.out.println("choose an option");
                Scanner scanner=new Scanner(System.in);
                int choice=scanner.nextInt();

                switch (choice)
                {
                case 1:bank.createAccount(connection,scanner);
                break;
                case 2:bank.deposit(connection,scanner);
                break;
                case 3:
                    bank.credit(connection,scanner);
                    break;
                case 4:bank.getBalance(connection,scanner);
                break;
                case 5:
                    bank.Accountdetails(connection,scanner);
                    break;
                case 6:System.out.println("exit");
                return;
                default:System.out.println("invalid choice please try again.");
                break;

                }
            }

        }
        catch (SQLException e) {e.getMessage();
        }
    }

}