import java.util.*;
import java.io.*;
import java.security.SecureRandom;

public class PasswordSuite 
{
    final static String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    final static String lowerCase = "abcdefghijklmnopqrstuvwxyz";
    final static String numbers   = "0123456789";
    final static String specialChar = "~`!@#$%^&*()-+_={}[]|\\:\";'<>,.?/";
    public static void main (String args[])
    {
        Scanner input = new Scanner(System.in);
        SecureRandom random;
        int choice, len;
        boolean loop = true;
        String password;

        do
        {
            System.out.println("\n__Menu Options__: ");
            System.out.println("1.) Check Duplicate Character");
            System.out.println("2.) Password Generator");
            System.out.println("3.) Unique Password Generator");
            System.out.println("4.) Exit\n");

            try 
            {
                choice = input.nextInt();

                switch(choice) 
                {
                    case 1:
                        System.out.println("\nEnter String to check for duplicate char: ");
                        password = input.next();
                        findDuplicateChar(password);
                        break;
                    case 2:
                        System.out.print("\nEnter password length: ");
                        len = input.nextInt();
                        random = new SecureRandom();
                        password = generateRandomPassword(random, len);
                        break;
                    case 3:
                        System.out.print("\nEnter password length: ");
                        len = input.nextInt();
                        System.out.print("Enter number of Special Characters: ");
                        int numberOfSpecialChar = input.nextInt();
                        random = new SecureRandom();
                        password = generateUniqueRandomPassword(random, len, numberOfSpecialChar);
                        break;
                    case 4:
                        System.out.println("Exiting Program...\n");
                        input.close();
                        loop = false;
                        break;
                    default:
                        System.out.println("\nInvalid Menu Option!\n");
                        break;
                }
            }
            catch (Exception e) // InputMismatchException
            {
                System.err.printf("\n%s\n\n", e);
                input = new Scanner(System.in);
            }
        } while(loop);
    }

    public static String findDuplicateChar(String password) 
    {        
        char[] arrC = new char[password.length()];
        String duplicate = "";
        HashSet<Character> charSet = new HashSet<>();
        char c;
        
        for (int i=0; i<password.length(); i++) 
        {
            c = password.charAt(i);
            if (!charSet.add(c))    // duplicate found in hashset
            {
                duplicate += c;
                arrC[i] = c;
            }
            else                    // blank filler for unique char
            {
                arrC[i] = '_';
            }
        }

        if (duplicate.equals("")) 
        {
            System.out.printf("\n\"%s\" has no duplicate character\n", password);
        } 
        else  System.out.printf("\nDuplicate character for \"%s\" ==> ___%s___\n", password, duplicate);

        System.out.printf("%s\n\n", Arrays.toString(arrC));
        return Arrays.toString(arrC);
    }

    // Function to generate random alpha-numeric password of specific length
    public static String generateRandomPassword(SecureRandom random, int len) {
        String combined = upperCase + lowerCase + numbers + specialChar;

        StringBuilder sb = new StringBuilder();

        // each iteration of loop choose a character randomly from the given ASCII range
        // and append it to StringBuilder instance
        int randomIndex;
        for (int i = 0; i < len; i++) 
        {
            randomIndex = random.nextInt(combined.length());
            sb.append(combined.charAt(randomIndex));
        }
        System.out.printf("\n%s\n\n", sb.toString());

        return sb.toString();
    }

    public static String generateUniqueRandomPassword(SecureRandom random, int len, int specialCount) {
        String combined = upperCase + lowerCase + numbers + specialChar;
        StringBuilder sb = new StringBuilder();

        HashSet<Integer> uniqueIndex = new HashSet<>();
        int randomIndex, count=0, i=0;
        boolean checkSpecialChar;
        while (i < len) 
        {
            randomIndex = random.nextInt(combined.length());
            if (uniqueIndex.add(randomIndex)) 
            {
                checkSpecialChar = specialChar.contains(Character.toString(combined.charAt(randomIndex)));
                if (checkSpecialChar)   // generate special character up to specialCount
                {
                    if (count<specialCount)
                    {
                        sb.append(combined.charAt(randomIndex));
                        i++;
                        count++;
                        checkSpecialChar = false;
                        continue;
                    }
                    else 
                    {
                        checkSpecialChar = false;
                        continue;
                    }
                    
                } 
                sb.append(combined.charAt(randomIndex));
                i++;
            }
        }
        System.out.printf("\n%s\n\n", sb.toString());

        return sb.toString();
    }
}