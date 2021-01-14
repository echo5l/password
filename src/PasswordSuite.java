import java.util.*;
import java.io.*;

public class PasswordSuite 
{
    public static void main (String args[])
    {
        Scanner input = new Scanner(System.in);
        int choice;
        while (true) 
        {
            try 
            {
                System.out.println("__Menu Options__: ");
                System.out.println("1.) Check Duplicate Character");
                System.out.println("2.) Exit");

                choice = input.nextInt();
                String password;

                switch(choice) 
                {
                    case 1:
                        System.out.println("\nEnter String to check for duplicate char: ");
                        password = input.next();
                        findDuplicateChar(password);
                        break;
                    case 2:
                        System.out.println("Exiting Program...");
                        input.close();
                        System.exit(0);
                    default:
                        System.out.println("\nInvalid Menu Option");
                        break;
                }
            }
            catch (Exception e)
            {
                System.err.println(e);
            }
        }
    }

    public static void findDuplicateChar(String password) 
    {        
        String result = "";
        HashSet<Character> charSet = new HashSet<>();
        for (int i=0; i<password.length(); i++) 
        {
            if (charSet.contains(password.charAt(i))) 
            {
                result += password.charAt(i);
            }
            charSet.add(password.charAt(i));
        }

        if (result == "") 
        {
            System.out.printf("\n\"%s\" has no duplicate character\n\n", password);
        } 
        else 
        {
            System.out.printf("\nDuplicate character for \"%s\" ==> ___%s___\n\n", password, result);
        }
    }
}