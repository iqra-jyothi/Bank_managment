import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Bank {
    private Connection connection;
    private Scanner scanner;
    static void createAccount(Connection connection, Scanner scanner){
        scanner.nextLine();
        System.out.println("enter account number");
        int number= scanner.nextInt();
        scanner.nextLine();
        System.out.println("enter account name");
        String name=scanner.nextLine();
//        scanner.nextLine();
        System.out.println("enter minimum to  deposit");
        double bal=scanner.nextDouble();
        String sql="insert into  BankAccounts (accountNumber,name,balance)values(?,?,?)";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,number);
            preparedStatement.setString(2,name);
            preparedStatement.setDouble(3,bal);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Account sucessfully created");
            } else {
                throw  new RuntimeException("Account Creation failed!!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        throw new RuntimeException("Account Already Exist");

    }
    void deposit(Connection connection,Scanner scanner)
    {    scanner.nextLine();
        System.out.println("enter account number");
        int number=scanner.nextInt();
        scanner.nextLine();
        System.out.println("deposit ammount");
        double amount=scanner.nextDouble();
        String sql="UPDATE BankAccounts set balance=balance+? where accountNumber=?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setDouble(1,amount);
            preparedStatement.setInt(2,number);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Account sucessfully deposited");
            } else {
                throw new RuntimeException(" failed to deposit!!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    void credit(Connection connection,Scanner scanner)
    {    scanner.nextLine();
        System.out.println("enter the accountnumber");
        int number=scanner.nextInt();
        scanner.nextLine();
        System.out.println("amount to credit");
        double amount=scanner.nextDouble();
        String sql="select balance from BankAccounts where accountNumber=?";
        try
        {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,number);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
            {
                double current_balance = resultSet.getDouble("balance");
                if(amount<=current_balance)
                {
                    String  credit= "UPDATE BankAccounts SET balance = balance - ? WHERE accountNumber = ?";
                    PreparedStatement preparedStatement1 = connection.prepareStatement(credit);
                    preparedStatement1.setDouble(1, amount);
                    preparedStatement1.setInt(2, number);
                    int rowsAffected = preparedStatement1.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Rs."+amount+" credited Successfully");
                    }
                    else{
                        System.out.println("not credited");
                    }

                }
                else {
                    System.out.println("not sufficent balance");
                }

            }else{
                System.out.println("not sefficent balance");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    void getBalance(Connection connection,Scanner scanner)
   {
//    scanner.nextLine();
        System.out.println("enter the AccountNumber");
        int number=scanner.nextInt();
        String sql="select balance from BankAccounts where accountNumber=?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,number);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
            {
                double balance=resultSet.getDouble("balance");
                System.out.println("current balance is:"+balance);
            }
            else {
                System.out.println("Account not found");
            }

        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
    void Accountdetails(Connection connection,Scanner scanner)
    {
        System.out.println("enter the account number");
        int number=scanner.nextInt();
        String sql="select * from BankAccounts where accountNumber=?";
        try{
            PreparedStatement  preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,number);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                System.out.println("Account number"+resultSet.getInt("accountNumber"));
                System.out.println("Account name"+resultSet.getString("name"));
                System.out.println("Account balance"+resultSet.getDouble("balance"));
            }
            else{
                System.out.println("not found");
            }
        }
        catch (SQLException e){
            throw  new RuntimeException(e);}
    }

}
