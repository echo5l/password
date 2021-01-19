import java.util.*;
import java.io.*;
import java.security.SecureRandom;

public class PasswordSuite 
{
    private static final IllegalArgumentException ILLEGAL_PASSWORD_SIZE = new IllegalArgumentException("Invalid Password Size!");
    private static final IllegalArgumentException ILLEGAL_PASSWORD      = new IllegalArgumentException("Invalid Password!");
    private static final String UPPER_CASE   = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_CASE   = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS      = "0123456789";
    private static final String SPECIAL_CHAR = "~`!@#$%^&*()-+_={}[]|\\:\";'<>,.?/";
    public static void main (String args[])
    {
        Scanner input = new Scanner(System.in);
        SecureRandom random;
        int choice, len;
        boolean loop = true;
        String password;

        do
        {
            printMenu();

            try 
            {
                choice = input.nextInt();

                switch(choice) 
                {
                    case 1:
                        findDuplicateChar(input);
                        break;
                    case 2:
                        random = new SecureRandom();
                        password = generateRandomPassword(input, random);
                        break;
                    case 3:
                        random = new SecureRandom();
                        password = generateUniqueRandomPassword(input, random);
                        break;
                    case 4:
                        System.out.println("\nExiting Program...\n");
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

    public static void printMenu() 
    {
        System.out.println("\n__Menu Options__: ");
        System.out.println("1.) Check Duplicate Character");
        System.out.println("2.) Password Generator");
        System.out.println("3.) Unique Password Generator");
        System.out.println("4.) Exit");
    }

    public static String findDuplicateChar(Scanner input) 
    {     
        System.out.println("\nEnter String to check for duplicate char: ");
        String password = input.next();
        if ((password==null) || password.length()<=0) throw ILLEGAL_PASSWORD;
    
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
        else 
        {  
            System.out.printf("\nDuplicate character for \"%s\" ==> ___%s___\n", password, duplicate);
            System.out.printf("%s\n\n", Arrays.toString(arrC));
        }
        return Arrays.toString(arrC);
    }

    // Function to generate random alpha-numeric password of specific length
    public static String generateRandomPassword(Scanner input, SecureRandom random) {
        System.out.print("\nEnter password length: ");
        int len;
        if (!input.hasNextInt()) throw new InputMismatchException("Non-Integer Length!");
        len = input.nextInt();
        if (len<1) throw ILLEGAL_PASSWORD_SIZE;

        random = new SecureRandom();
        String combined = UPPER_CASE + LOWER_CASE + NUMBERS + SPECIAL_CHAR;
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

    public static String generateUniqueRandomPassword(Scanner input, SecureRandom random) {
        System.out.print("\nEnter password length: ");
        int len;
        if (!input.hasNextInt()) throw new InputMismatchException("Non-Integer Length!");
        len = input.nextInt();
        if (len<1) throw ILLEGAL_PASSWORD_SIZE;

        System.out.print("Enter number of Special Characters: ");
        int numberOfSpecialChar;
        if (!input.hasNextInt()) throw new InputMismatchException("Non-Integer Special Character Count!");
        numberOfSpecialChar = input.nextInt();
        if (numberOfSpecialChar<0) throw new IllegalArgumentException("Invalid Special Character Count!");

        String combined = UPPER_CASE + LOWER_CASE + NUMBERS + SPECIAL_CHAR;
        StringBuilder ps = new StringBuilder();
        HashSet<Integer> uniqueIndex = new HashSet<>();
        
        boolean checkSpecialChar;
        int randomIndex, count=0, i=0;
        while (i < len) 
        {
            randomIndex = random.nextInt(combined.length());
            if (uniqueIndex.add(randomIndex)) 
            {
                checkSpecialChar = SPECIAL_CHAR.contains(Character.toString(combined.charAt(randomIndex)));
                if (checkSpecialChar)   // generate special character up to special character count
                {
                    if (count<numberOfSpecialChar)
                    {
                        ps.append(combined.charAt(randomIndex));
                        i++;
                        count++;
                        continue;
                    }
                    else 
                    {
                        continue;
                    }
                    
                } 
                ps.append(combined.charAt(randomIndex));
                i++;
            }
        }
        System.out.printf("\n%s\n\n", ps.toString());

        return ps.toString();
    }
}
