package pl.coderslab;


import pl.coderslab.entity.UserDao;

import java.util.Scanner;

public class Main {
    private static UserDao userDao = new UserDao();
    private static User user;

    public static void main(String[] args) {
        int loop=1;
        while(loop==1) {
            System.out.println();
            menu();
            loop = options();
        }

    }

    private static void showUsers(User[] arr) {
        System.out.println("USERS:");
        for (User item : arr) {
            printUser(item);
        }
    }

    private static void printUser(User item){
        try {
            System.out.println(String.format("%s\t %-30s %-20s %s", item.getId(), item.getEmail(), item.getUsername(), item.getPassword()));
        }catch (NullPointerException e){
            System.out.println("Invalid ID");
        }
    }

    private static void menu() {
        System.out.println("MENU\n========");
        System.out.println("ADD USER (add)");
        System.out.println("DELETE USER (delete)");
        System.out.println("UPDATE USER (update)");
        System.out.println("SHOW USER INFO (show)");
        System.out.println("SHOW ALL USERS INFO (showAll)");
        System.out.println("EXIT (exit)");
        System.out.println("=========");
    }

    private static int options() {
        String option = Input.getInputString("Type an option");
        if (option.equals("add")) {
            addUsr();
        } else if (option.toLowerCase().equals("delete")) {
            int id = Input.getInputInt("Select id to delete");
            userDao.delete(id);
        } else if (option.toLowerCase().equals("update")) {
            updateUsr();
        } else if (option.toLowerCase().equals("show")) {
            int id = Input.getInputInt("Select id to show");
            printUser(userDao.read(id));
        } else if (option.toLowerCase().equals("showall")) {
            showUsers(userDao.findAll());
        } else if (option.toLowerCase().equals("exit")) {
            System.out.println("Bye");
            return 0;
        } else {
            System.out.println("Invalid command");
        }
        return 1;
    }

    private static void updateUsr() {
        int id = Input.getInputInt("Select id to update");
        try{
            userDao.update(userDao.read(id));
        }catch (NullPointerException e){
            System.out.println("This id doesnt exist type (showall) to see id's");
        }
    }

    private static void addUsr() {
        String email;
        do {
            email = Input.getInputString("Set Email:");
            if(!isEmailValid(email)){
                System.out.println("Email not valid!");
            }
        }while(!isEmailValid(email));
        String username = Input.getInputString("Set Username:");
        String password = Input.getInputString("Set Password:");
        user = new User(email, username, password);
        userDao.create(user);
    }

    public static boolean isEmailValid(String email){
        return email.matches("^[A-Za-z0-9.]*@[a-zA-Z0-9.]*\\.[a-z]{2,3}$");
    }
}
