/**
 *
 * @author Lawrence Gomez, Late submisson april 22, 2018 :/
 */

import hardwarestore.HardwareStore;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import hardwarestore.items.Item;
import hardwarestore.users.User;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GUI extends JFrame{

    /**
     * @param args the command line arguments
     */
    
    private static final Logger logger = Logger.getLogger(GUI.class.getName());
    
    public static void main(String[] args) throws IOException{        
        
        HardwareStore hardwareStore = new HardwareStore();
        
        logger.setLevel(Level.INFO);
        logger.log(Level.INFO, "Set up complete");
                
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Hardware Store Database");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(5, 5));
        
        JButton showAll = new JButton("Show all existing records");
        JButton Add = new JButton("Add new item/quantity");
        JButton Delete = new JButton("Delete an item ");
        JButton Search = new JButton("Search for an item");
        JButton List = new JButton("Show a list of users");
        JButton NewUser = new JButton("Add new user");
        JButton Update = new JButton("Update user info");
        JButton Trans = new JButton("Complete a sale transaction");
        JButton Complete = new JButton("Show completed sale transactions");
        JButton Exit = new JButton("Exit program");   
        
        JLabel L = new JLabel("      Welcome to the database!");

        frame.getContentPane().add( L );
        frame.add(showAll);
        frame.add(Add);
        frame.add(Delete);
        frame.add(Search);
        frame.add(List);
        frame.add(NewUser);
        frame.add(Update);
        frame.add(Trans);
        frame.add(Complete);
        frame.add(Exit);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(600,600);
        
        showAll.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                logger.entering(getClass().getName(), "Show all existing items");
                logger.log(Level.INFO,"Show all items");
                
                HardwareStore.sortItemList();
                JOptionPane.showMessageDialog(null, hardwareStore.getAllItemsFormatted());
                
                logger.exiting(null, "Exiting");
            }   
        });
        
        Add.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                logger.entering(getClass().getName(), "Show all existing items");
                logger.log(Level.INFO,"Show all items");
                
                String text = "Input the ID of item \n If the item does not exist, it will be added as a new entry";
                String idNumber = JOptionPane.showInputDialog(null,text);
                
                //logger.addHandler(fileHandler);
                
                
                int itemIndex = hardwareStore.findItemIndex(idNumber);
                if (itemIndex != -1){
                    JOptionPane.showMessageDialog(null,"Item found in database.");
                    int quantity = 0;
                    while (true) {
                    JOptionPane.showMessageDialog(null,"Current quantity: " + hardwareStore.findItem(idNumber).getQuantity());
                    String temp;
                    temp = JOptionPane.showInputDialog(null,"Enter quantity you want to add.");
                    quantity = Integer.parseInt(temp);
                        if (quantity <= 0) {
                            JOptionPane.showMessageDialog(null,"Invalid quantity. "
                                    + "The addition amount must be larger than 0.");
                            continue;
                        }
                    break;
                    }
                hardwareStore.addQuantity(itemIndex, quantity);
                } 
                
                else {
                    JOptionPane.showMessageDialog(null,"Item does not exist......creating");
                    String name = JOptionPane.showInputDialog(null,"Type the name of item");
                    int quantity = 0;
                        while(true) {
                            String temp;
                            temp = JOptionPane.showInputDialog(null,"Type the quantity of the item");
                            quantity = Integer.parseInt(temp);
                            if (quantity < 0) {
                                JOptionPane.showMessageDialog(null,"Invalid quantity. "
                                    + "The quantity cannot be smaller than 0.");
                            }
                        break;
                        }

                    float price = 0;
                    while (true) {
                        String temp;
                        temp = JOptionPane.showInputDialog(null,"Please type the price of the item (float)");
                        price = Float.parseFloat(temp);
                        if (price < 0) {
                            System.out.println("Invalid price. "
                                    + "The price cannot be smaller than 0.");
                        }
                        break;
                    }

                    while (true) {
                        String [] op = {"Small Hardware Items", "Appliances"};
                        int selection = JOptionPane.showOptionDialog(null, "Please select the type of item", "Item Type", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, op, op[0]);
                        switch (selection) {
                            case 0:
                                String [] cat = {"Door&Window","Cabinet&Furniture","Fasteners","Structural","Other"};
                                String category = (String) JOptionPane.showInputDialog(null, "Please select the category of item.", "Item category", JOptionPane.QUESTION_MESSAGE , null, cat, cat[0]);

                                hardwareStore.addNewSmallHardwareItem(idNumber, name, quantity, price, category);
                                return;
                            case 1:
                                String brand = JOptionPane.showInputDialog(null, "Please input the brand of appliance. (String)");
                                String [] ap = {"Refrigerators","Washers&Dryers","Ranges&Ovens","Small Appliance"};
                                String type = (String) JOptionPane.showInputDialog(null, "Please select the type of appliance", "Appliance Type", JOptionPane.QUESTION_MESSAGE, null, ap, ap[0]);

                                hardwareStore.addNewAppliance(idNumber, name, quantity, price, brand, type);
                                return;
                        }
                    }
                }
                logger.exiting(null, "Exiting");
            }
        });
        
        Delete.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                logger.entering(getClass().getName(), "Show all existing items");
                logger.log(Level.INFO,"Show all items");
                        
                String text = "Input the ID of item you wish to delete";
                String idNumber = JOptionPane.showInputDialog(null,text);

                int itemIndex = hardwareStore.findItemIndex(idNumber);
                if (itemIndex == -1) {
                    JOptionPane.showInputDialog(null,"Item does not exsist");
                    return;
                } 
                else {
                    JOptionPane.showMessageDialog(null,"Item found and deleted");
                    hardwareStore.removeItem(itemIndex);
                }   
                logger.exiting(null, "Exiting");
            }
        });
                
        Search.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                logger.entering(getClass().getName(), "Show all existing items");
                logger.log(Level.INFO,"Show all items");
                        
                String name = JOptionPane.showInputDialog(null,"Please input the name of item.");
                String output = hardwareStore.getMatchingItemsByName(name);
                if (output == null) {
                    JOptionPane.showMessageDialog(null,"Item not found with.");
                } else {
                    JOptionPane.showMessageDialog(null,output);
                }
                logger.exiting(null, "Exiting");
            }
        });
        
        List.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                logger.entering(getClass().getName(), "Show all existing items");
                logger.log(Level.INFO,"Show all items");
                        
            JOptionPane.showMessageDialog(null,hardwareStore.getAllUsersFormatted());
                
            logger.exiting(null, "Exiting");
            }
        });
        
        NewUser.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                logger.entering(getClass().getName(), "Show all existing items");
                logger.log(Level.INFO,"Show all items");
                        
            String [] type = {"Employee","Customer"};
            
            while (true) {
                int selection = JOptionPane.showOptionDialog(null, "Please select the type of user", "User Type", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, type, type[0]);
                    switch (selection) {
                        case 0:
                            String temp;
                            //temp = JOptionPane.showInputDialog(null,"Please Employee ID (String):");
                            //int id = Integer.parseInt(temp);
                            String firstName = JOptionPane.showInputDialog(null,"Input the first name:");
                            String lastName =JOptionPane.showInputDialog(null,"Input the last name:");
                            System.out.println("Please input the SSN 9 digit integer:");
                            temp = JOptionPane.showInputDialog(null,"Input the SSN 9 digit integer:");
                            int socialSecurityNumber = Integer.parseInt(temp);
                            temp = JOptionPane.showInputDialog(null,"Input the monthly salary:");
                            float monthlySalary = Float.parseFloat(temp);

                            hardwareStore.addEmployee( firstName,lastName, socialSecurityNumber, monthlySalary);
                            logger.exiting(null, "Exiting");
                            return;

                        case 1:
                            // Add Customer
                            String firstNameC = JOptionPane.showInputDialog(null,"Input the first name:");
                            String lastNameC =JOptionPane.showInputDialog(null,"Input the last name:");
                            String phoneC = JOptionPane.showInputDialog(null,"Input the phone number:");
                            String addyC =JOptionPane.showInputDialog(null,"Input the address:");
                            hardwareStore.addCustomer(firstNameC, lastNameC, phoneC, addyC);
                            logger.exiting(null, "Exiting");
                            return;
                    }
            }
            
            }
        });
        
        Update.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                logger.entering(getClass().getName(), "Show all existing items");
                logger.log(Level.INFO,"Show all items");
                        
            String id = JOptionPane.showInputDialog(null,"Input the ID of user.");
            int idInput = Integer.parseInt(id);
            

            User editUser = hardwareStore.findUser(idInput);
            if (editUser == null) {
                JOptionPane.showMessageDialog(null,"User not found.");
                logger.exiting(null, "Exiting");
                return;
            }
            String text = " -------------------------------------------------------------------------------------------------\n" +
                    String.format("| %-10s| %-9s| %-12s| %-12s| %-45s|%n", "User Type", "User ID", "First Name", "Last Name", "Special") +
                    " -------------------------------------------------------------------------------------------------\n";
            text += editUser.getFormattedText();
            text += " -------------------------------------------------------------------------------------------------\n";
            
            JOptionPane.showMessageDialog(null,"Uptodate user information:");
            JOptionPane.showMessageDialog(null,text);
            if (editUser.isEmployee) {
                String temp;
                String firstName = JOptionPane.showInputDialog(null,"Input the first name:");
                String lastName =JOptionPane.showInputDialog(null,"Input the last name:");
                System.out.println("Please input the SSN 9-digit integer:");
                temp = JOptionPane.showInputDialog(null,"Input the SSN (9 integers only):");
                int socialSecurityNumber = Integer.parseInt(temp);
                temp = JOptionPane.showInputDialog(null,"Input the monthly salary:");
                float monthlySalary = Float.parseFloat(temp);
            
                hardwareStore.editEmployeeInformation(idInput, firstName,lastName, socialSecurityNumber, monthlySalary);
                logger.exiting(null, "Exiting");
                return;
            } 
            else {
                String firstNameC = JOptionPane.showInputDialog(null,"Input the first name:");
                String lastNameC =JOptionPane.showInputDialog(null,"Input the last name:");
                String phoneC = JOptionPane.showInputDialog(null,"Input the phone number:");
                String addyC =JOptionPane.showInputDialog(null,"Input the address:");
                hardwareStore.addCustomer(firstNameC, lastNameC, phoneC, addyC);
                logger.exiting(null, "Exiting");
                return;
            }
                
            }
        });
        
        Trans.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                logger.entering(getClass().getName(), "Show all existing items");
                logger.log(Level.INFO,"Show all items");
                        
                int itemIndex = 0;
                int saleQuantity = 0;
                String itemID = "";
                while (true) {
                    itemID = JOptionPane.showInputDialog(null,"Please input the item ID:");
                    itemIndex = hardwareStore.findItemIndex(itemID);
                    if (itemIndex == -1) {
                        JOptionPane.showInputDialog(null,"Item not found. Will return to main menu.");
                        return;
                    } else {
                        Item tempItem = hardwareStore.findItem(itemID);
                        String temp = JOptionPane.showInputDialog(null,"Please input the amount of items sold in this transaction (int)");
                        //System.out.println("Maximum number: " + tempItem.getQuantity());
                        saleQuantity = Integer.parseInt(temp);
                    }
                    break;
                }

                //Get employee ID. Will check the input: must be a user, and employee.
                int employeeID = 0;
                while (true) {
                    String temp = JOptionPane.showInputDialog(null,"Please input the id of the employee.");
                    employeeID = Integer.parseInt(temp);
                    break;
                }

                //Get customer ID. Will check the input: must be a user, and customer.
                int customerID = 0;
                while (true) {
                    String temp = JOptionPane.showInputDialog(null,"Please input the id of the customer.");
                    customerID = Integer.parseInt(temp);
                    break;
                }

                hardwareStore.progressTransaction(itemID, saleQuantity, customerID, employeeID, itemIndex);
                JOptionPane.showMessageDialog(null,"Transaction complete.");

                logger.exiting(null, "Exiting");
            }
        });
         
        Complete.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                logger.entering(getClass().getName(), "Show all existing items");
                logger.log(Level.INFO,"Show all items");
                        
            JOptionPane.showMessageDialog(null,hardwareStore.getAllTransactionsFormatted());
                
            logger.exiting(null, "Exiting");
            }
        });
        
        Exit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                logger.entering(getClass().getName(), "Show all existing items");
                logger.log(Level.INFO,"Show all items");
                
                try{
                hardwareStore.writeDatabase();
                }
                catch(IOException a)
                {
                    a.printStackTrace();
                }
                logger.exiting(null, "Exiting database");
            }
        });
        
    }
}