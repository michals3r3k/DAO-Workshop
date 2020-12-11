package pl.coderslab;

import java.util.Scanner;

public class Input {
    private static Scanner in =new Scanner(System.in);

    public static String getInputString(String text){
        System.out.println(text);
        return in.next();
    }

    public static int getInputInt(String text){
        System.out.println(text);
        while (!in.hasNextInt()){
            System.out.println("Invalid Id, type a number greater than 0");
            in.next();
        }
        return in.nextInt();
    }
}
