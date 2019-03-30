package com.mycompany.app;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import java.sql.*;

public class App extends JFrame {
    private JTextField vehicleNo;
    private JTextField customerName;
    private JTextField quantity;
    private JButton addBtn;
    private JButton showBtn;
    private static boolean insertInfo;
    private static boolean showInfo;
    private static String VehicleNum, CustomerName;
    private static int Quantity;

    public App(){
        super("Customer Information");
        setLayout(new FlowLayout());

        JLabel vehicleLabel=new JLabel("Vehicle No");
        add(vehicleLabel);

        vehicleNo=new JTextField(10);
        add(vehicleNo);

        JLabel customerLabel=new JLabel("Customer Name");
        add(customerLabel);

        customerName=new JTextField(10);
        add(customerName);

        JLabel quantityLabel=new JLabel("Quantity");
        add(quantityLabel);

        quantity=new JTextField(10);
        add(quantity);

        addBtn=new JButton("Add");
        add(addBtn);

        showBtn=new JButton("Show");
        add(showBtn);

        thehandler handler= new thehandler();
        vehicleNo.addActionListener(handler);
        customerName.addActionListener(handler);
        quantity.addActionListener(handler);
        addBtn.addActionListener(handler);
        showBtn.addActionListener(handler);

    }

    private class thehandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {

            if (event.getSource() == vehicleNo) {
                VehicleNum = event.getActionCommand();
                //System.out.println(VehicleNum);

            } else if (event.getSource() == customerName) {
                CustomerName = event.getActionCommand();
                //System.out.println(CustomerName);

            } else if (event.getSource() == quantity) {
                 Quantity = Integer.parseInt(event.getActionCommand());
                //System.out.println(Quantity);

            }else if (event.getSource()==addBtn){
                insertInfo=true;
                showInfo=false;
                App.db();
                //System.out.println("Adding Info");
            }else if (event.getSource()==showBtn){
                showInfo=true;
                insertInfo=false;
                //System.out.println("Database Info"+insertInfo);
                App.db();

            }
        }
    }
     static void db(){
        String URL="jdbc:mysql://localhost:3306/gunasekara_holdings";
        String UserNm="root";
        String Passwrd="";
         try {
             Class.forName("com.mysql.jdbc.Driver");
         } catch (ClassNotFoundException e) {
             System.out.println("Class not found");
             e.printStackTrace();

         }
         Connection con1= null;
        try{
            con1 = DriverManager.getConnection(URL,UserNm, Passwrd);
            Statement stat=con1.createStatement();
            if(insertInfo) {
                int count = stat.executeUpdate("insert into customer_info(vehicle_no,customer_name,quantity) values ('"+VehicleNum+"','"+CustomerName+"','"+Quantity+"') ");
                //ID=ID+count;
                System.out.println("Field successfully added");
            }else if(showInfo){
                ResultSet res=stat.executeQuery("select * from customer_info");
                while (res.next()){
                    System.out.println(res.getInt(1)+"  "+res.getString(2)+"    "+res.getString(3)+"   "+res.getInt(4));
                }
            }
            stat.close();
            con1.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String args[]) throws IOException, ClassNotFoundException {

        App DB1=new App();
        DB1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DB1.setSize(450,200);
        DB1.setVisible(true);

   }

}
