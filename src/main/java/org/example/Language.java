package org.example;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Language {
    public static ResourceBundle LANGUAGE = ResourceBundle.getBundle("lang");
    public static byte selectLanguage(){

        System.out.println("1. english\n2. polish");
        Scanner scanner = new Scanner(System.in);
        byte languageNumber;
        do {
            try {
                System.out.print("Select language (number): ");
                languageNumber = scanner.nextByte();
                if(languageNumber!=1 && languageNumber!=2){
                    throw new InternalError();
                }
                return languageNumber;
            } catch (InputMismatchException e) {
                System.out.println("Enter a number");
                scanner.next();
            }catch (InternalError e){
                System.out.println("Enter a range number");
            }
        }while(true);


    }
    public static void setLANGUAGE(byte languageNumber) {
        if (languageNumber == 1){
            Locale english = new Locale("en", "US");
            LANGUAGE = ResourceBundle.getBundle("lang", english);
        }
        else if(languageNumber == 2){
            Locale polish = new Locale("pl","PL");
            LANGUAGE = ResourceBundle.getBundle("lang",polish);
        }
    }
}
