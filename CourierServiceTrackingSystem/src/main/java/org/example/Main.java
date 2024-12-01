package org.example;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static void mainDisplay(){
        System.out.println("---------- CHOOSE -----------");
        System.out.println("1. ADMIN LOGIN");
        System.out.println("2. CUSTOMER LOGIN");
        System.out.println("3. TRACK STATUS");
        System.out.println("4. POST COURIER");
        System.out.println("5. DATABASE MANAGER LOGIN");
        System.out.println("6. EXIT");
        System.out.println("----------------------------");
    }

    private static void adminLogin(Scanner sc , Admin ad, Customer cus) throws Exception{
        sc.nextLine();
        System.out.println("Enter the Admin ID : ");
        String id = sc.nextLine();
        System.out.println("Enter the Admin name :");
        String name = sc.nextLine();
        System.out.println("Enter the Admin password");
        String pass =sc.nextLine();
        ad.setAdminName(name);
        ad.setPassword(pass);
        int res = ad.fetch();
        if(res==1) {
            notifydisp(ad);
            adminWork(sc, ad, cus);
        }
        if(res ==-1){
            adminLogin(sc,ad,cus);
        }
    }

    private static void notifydisp(Admin ad) throws SQLException {
        String[] notification = ad.fetchNotification();
        String notify = notification[0];
        String packId = notification[1];
        System.out.println("----------------------------");
        System.out.println(" NEW NOTIFICATION : ");
        System.out.println(notify+packId);
        System.out.println("----------------------------");
    }
    private static void adminWork(Scanner sc,Admin ad,Customer cus) throws Exception{
        admindisplay();
        DeliverStatus stat= new DeliverStatus();
        int opt = sc.nextInt();
        sc.nextLine();
        if (opt ==1 ){
            adminUpdateTracking(sc,stat,ad,cus);
        }
        else if(opt ==2){
            track(sc,stat);
        }
        else if(opt ==3){
            postCourier(sc,cus);
        }
        else if(opt ==4){
            trackup(sc,stat);
        }
        else if(opt == 5){
            cancelPack(sc,ad);
        }
        else if(opt == 6){
            System.out.println("EXITING ....");
            Thread.sleep(1000);
            System.exit(0);
        }
        else{
            System.out.println("Enter the correct option");
        }
    }

    private static void cancelPack(Scanner sc, Admin ad) throws Exception {
        System.out.println("Enter the pack id : ");
        int packId= sc.nextInt();
        ad.deletePackage(packId);
        ad.deletePayment(packId);
        ad.deleteDeliveryStatus(packId);
        ad.deleteNotification(packId);
        System.out.println("The package "+ packId +" is cancelled successfully .");
        System.out.println("EXITING ....");
        Thread.sleep(1000);
        System.exit(0);
    }

    private static void trackup(Scanner sc, DeliverStatus stat) throws SQLException, InterruptedException {
        System.out.println("Enter the package id : ");
        int packid=sc.nextInt();
        StatusDisp();
        int opt = sc.nextInt();
        StatusOfDelivery status = updateStatus(opt);
        stat.updateStatusByPackageId(packid, String.valueOf(status));
        System.out.println("UPDATED SUCCESSFULLY");
        System.out.println("EXITING ....");
        Thread.sleep(1000);
        System.exit(0);
    }

    private static void adminUpdateTracking(Scanner sc, DeliverStatus stat ,Admin ad, Customer cus) throws Exception {
        System.out.println("Enter the package id :");
        int packId = sc.nextInt();
        sc.nextLine();
        Package pack = new Package();
        int cusid = pack.getCustomerIdByPackageId(packId);
        StatusDisp();
        int opt = sc.nextInt();
        sc.nextLine();
        setStatus(stat,opt);
        System.out.println("Enter the current location : ");
        String loc = sc.nextLine();
        stat.setLocation(loc);
        stat.setPackageId(packId);
        stat.insertIntoDatabase();
        adminWork(sc,ad,cus);
    }

    private static StatusOfDelivery updateStatus(int opt){
        StatusOfDelivery status = null;
        if(opt ==1){
            status = StatusOfDelivery.Package_successfully_delivered;
        }
        else if(opt ==2){
            status = StatusOfDelivery.package_lost;
        }
        else if(opt ==3){
            status = StatusOfDelivery.package_out_for_delivery;
        }
        else if(opt==4){
            status = StatusOfDelivery.unsuccessful_delivery;
        }
        else{
            System.out.println("Enter the correct choose");
        }
        return status;
    }
    private static void setStatus(DeliverStatus stat, int opt) {
        StatusOfDelivery status = null;
        if(opt ==1){
            status = StatusOfDelivery.Package_successfully_delivered;
        }
        else if(opt ==2){
            status = StatusOfDelivery.package_lost;
        }
        else if(opt ==3){
            status = StatusOfDelivery.package_out_for_delivery;
        }
        else if(opt==4){
            status = StatusOfDelivery.unsuccessful_delivery;
        }
        else{
            System.out.println("Enter the correct choose");
        }
        stat.setStatus(status);
    }

    private static void StatusDisp(){
        System.out.println("---------- CHOOSE -----------");
        System.out.println("1. Package_successfully_delivered");
        System.out.println("2. package_lost ");
        System.out.println("3. package_out_for_delivery");
        System.out.println("4. unsuccessful_delivery");
        System.out.println("----------------------------");
    }
    private static void customerLogin(Scanner sc,Customer cus) throws Exception {
        System.out.println("------------- CHOOSE --------------");
        System.out.println("1.ALREADY HAVE AN ACCOUNT LOGIN");
        System.out.println("2.DON'T HAVE AN ACCOUNT SIGNUP");
        System.out.println("----------------------------------");
        int choose = sc.nextInt();
        sc.nextLine();
        if(choose ==1 ){
            customerSignin(sc,cus);
        }
        else if(choose ==2){
            createAccount(sc , cus);
        }
        else{
            System.out.println("No such option");
        }
    }
    private static void customerSignin(Scanner sc,Customer cus) throws Exception {
        System.out.println("---------- SIGN IN -----------");
        System.out.println("Enter the name : ");
        String Lname = sc.nextLine();
        System.out.println("Enter the password : ");
        String Lpass = sc.nextLine();
        int res =cus.fetch(Lname,Lpass);
        if(res==1) {
            displayCustomerUsage();
            CustomerUsage(sc, cus);
        }
        if(res == -1){
            customerSignin(sc,cus);
        }
    }
    private static void dbmanage(Scanner sc) throws SQLException, InterruptedException {
        DBmanager db = new DBmanager();
        System.out.println("ENTER DB MANAGER ID : ");
        int dbId = sc.nextInt();
        sc.nextLine();
        System.out.println("ENTER THE DATABASE MANAGER PASSWORD : ");
        String pass = sc.nextLine();
        if(dbId == db.dbId && pass.equals(db.dbPass)){
            System.out.println("Enter the admin ID : ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter the name of the admin :");
            String aName = sc.nextLine();
            System.out.println("Enter the password of the admin :");
            String aPass = sc.nextLine();
            db.insertAdmin(id,aName,aPass);
            System.exit(0);
        }
        else{
            System.out.println("ACCESS NOT PERMITTED");
            Thread.sleep(2000);
            System.exit(0);
        }
    }
    private static void CustomerUsage(Scanner sc, Customer cus) throws Exception {
        int choice = sc.nextInt();
        sc.nextLine();
        Payment pay = new Payment();
        DeliverStatus stat = new DeliverStatus();
        if(choice ==1){
            track(sc,stat);
        }
        else if(choice ==2){
            System.out.println("Enter the package id : ");
            int packid = sc.nextInt();
            int price =pay.getPriceByPackId(packid);
            System.out.println("The price of "+ packid +" is " +price );
            System.out.println("EXITING ....");
            Thread.sleep(1000);
            System.exit(0);
        }
        else if (choice ==3){
            postCourier(sc,cus);
        }
    }
    private static void postCourier(Scanner sc,Customer cus) throws Exception {
        int custID;
        System.out.println("enter your first name : ");
        String fname = sc.nextLine();
        cus.setFirstName(fname);
        custID = cus.getCustomerIdByFirstName(cus.getFirstName());

        Package pack = new Package();
        packDisp();
        int type = sc.nextInt();
        double weight = 0;
        if(type ==2) {
            System.out.println("Enter the weight (in kgs) : ");
            weight = sc.nextDouble();
            pack.setWeight(weight);
        }
        sc.nextLine();
        System.out.println("Enter the dimensions : (length , breadth , height)");
        String dim = sc.nextLine();
        System.out.println("Enter your name : ");
        String from = sc.nextLine();
        System.out.println("Enter the name of the person who gets the delivery : ");
        String to = sc.nextLine();
        System.out.println("Enter the pick up address : ");
        String pick = sc.nextLine();
        System.out.println("Enter the delivery address : ");
        String deliver = sc.nextLine();
        System.out.println("Enter the pick up date  - ' YYYY-MM-DD '");
        String pickDate = sc.nextLine();
        System.out.println("Enter the delivery date  - ' YYYY-MM-DD '");
        String delDate = sc.nextLine();
        pack.setCustomerId(custID);
        pack.setDimension(dim);
        pack.setPickupAddress(pick);
        pack.setFromName(from);
        pack.setToName(to);
        pack.setDeliveryAddress(deliver);
        pack.setPickupDate(pickDate);
        pack.setDeliveryDate(delDate);
        pack.insert();
        int packid = pack.getLatestPackageId();
        payBill(sc, type, cus, weight, custID , packid);
    }

    private static void payBill(Scanner sc, int type, Customer cus, double weight, int custID , int packid) throws Exception {
        Payment pay = new Payment();
        if(type==1){
            System.out.println("Enter the distance between the start and end point in kms");
            int dist = sc.nextInt();
            sc.nextLine();
            int price1 =(100+(dist/10));
            System.out.println("The price is : "+(100+(dist/10)));
            pay.setPrice(price1);
            pay.setCustomerID(custID);
            paymentDisp(sc,custID,pay,packid);
        }
        if(type ==2){
            System.out.println("Enter the distance between the start and end point in kms");
            int dist = sc.nextInt();
            sc.nextLine();
            int price2 = (int) (100+(dist/10)+(weight/10));
            System.out.println("The price is : "+(100+(dist/10)+(weight/10)));
            pay.setPrice(price2);
            pay.setCustomerID(custID);
            paymentDisp(sc,custID,pay,packid);
        }
    }

    private static void paymentDisp(Scanner sc,int custId,Payment pay,int packid) throws Exception {
        System.out.println("1. TRACK TO PAYMENT GATEWAY");
        System.out.println("2. CANCEL PAYMENT");
        int choose = sc.nextInt();
        sc.nextLine();
        if(choose ==1){
            System.out.println("DIRECTING TO PAYMENT GATEWAY.....");
            Thread.sleep(2000);
            System.out.println("PAYMENT SUCCESSFULL");
            Thread.sleep(2000);
            System.out.println("YOUR ID TO TRACK IS "+ custId);
            System.out.println("YOUR PACKAGE ID IS "+packid);
            pay.insert(packid);
            pay.insertNotification(packid);
            System.out.println("EXITING ....");
            Thread.sleep(1000);
            System.exit(0);
        }
        else{
            System.out.println("PAYMENT CANCELLED");
            System.out.println("EXITING ....");
            Thread.sleep(1000);
            System.exit(0);
        }
    }

    private static void displayCustomerUsage(){
        System.out.println("1. TRACK ");
        System.out.println("2. CHECK PRICE");
        System.out.println("3. POST COURIER");
    }
    private static void packDisp(){
        System.out.println("WHAT TYPE OF PARCEL IT IS ? ");
        System.out.println("1. COURIER");
        System.out.println("2. PACKAGE");
    }
    private static void createAccount(Scanner sc , Customer cus) throws Exception {
        System.out.println("Enter the firt name : ");
        String firstName=sc.nextLine();
        System.out.println("Enter the last name : ");
        String lastName=sc.nextLine();
        System.out.println("Enter the password : ");
        String password=sc.nextLine();
        System.out.println("Enter the email : ");
        String email=sc.nextLine();
        System.out.println("Enter the phone : ");
        String phone=sc.nextLine();
        System.out.println("Enter the address: ");
        String address=sc.nextLine();
        cus.setFirstName(firstName);
        cus.setLastName(lastName);
        cus.setPassword(password);
        cus.setEmail(email);
        cus.setPhone(phone);
        cus.setAddress(address);
        cus.insert(firstName,lastName,password,email,phone,address);
        System.out.println("ACCOUNT CREATED SUCCESSFULLY");
        System.out.println("----------------------------");
        customerSignin(sc,cus);
    }
    private static void admindisplay(){
        System.out.println("---------- SIGN IN -----------");
        System.out.println("1. ALLOT TRACK ");
        System.out.println("2. DELIVERY STATUS");
        System.out.println("3. BOOK COURIER");
        System.out.println("4. UPDATE TRACK ");
        System.out.println("5. CANCEL A COURIER ");
        System.out.println("6. EXIT");
    }
    private static void track(Scanner sc,DeliverStatus stat) throws Exception {
        System.out.println("ENTER THE PACKAGE ID :");
        int id = sc.nextInt();
        sc.nextLine();
        StatusOfDelivery status = stat.getStatusByPackageId(id);
        System.out.println(status);
        System.out.println("EXITING ....");
        Thread.sleep(1000);
        System.exit(0);
    }
    public static void main(String[] args) throws Exception {
        mainDisplay();
        Scanner sc = new Scanner(System.in);
        Admin ad = new Admin();
        Customer cus = new Customer();
        DeliverStatus stat = new DeliverStatus();
        while(true){
            int choice = sc.nextInt();

            if(choice ==1){
                adminLogin(sc,ad,cus);
            }
            else if (choice ==2){
                customerLogin(sc,cus);
            }
            else if (choice ==3){
                track(sc,stat);
            }
            else if (choice ==4){
                customerLogin(sc,cus);
            }
            else if(choice == 5){
                dbmanage(sc);
            }
            else if (choice ==6){
                System.out.println("EXITING ....");
                Thread.sleep(1000);
                System.exit(0);
            }
            else{
                System.out.println("Enter the correct option");
            }
        }

    }
}