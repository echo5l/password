import java.util.*;
import java.io.*;
import java.security.SecureRandom;
import java.util.stream.Collectors;

public class PasswordSuite 
{
    private static final IllegalArgumentException ILLEGAL_PASSWORD_SIZE = new IllegalArgumentException("Invalid Password Size!");
    private static final IllegalArgumentException ILLEGAL_PASSWORD      = new IllegalArgumentException("Invalid Password!");
    private static final String UPPER_CASE   = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_CASE   = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS      = "0123456789";
    private static final String SPECIAL_CHARACTER = "~`!@#$%^&*()-+_={}[]|\\:\";'<>,.?/";
    
    public static void main (String args[])
    {
        runPasswordMenu();
    }

    public static void printMenu() 
    {
        System.out.println("\n__Menu Options__: ");
        System.out.println("1.) Check Duplicate Character");
        System.out.println("2.) Password Generator");
        System.out.println("3.) Unique Password Generator");
        System.out.println("4.) Exit");
    }

    public static void runPasswordMenu() 
    {
        Scanner input = new Scanner(System.in);
        int choice;
        boolean loop = true;

        do
        {
            printMenu();
            try 
            {
                choice = input.nextInt();
                switch(choice) 
                {
                    case 1:
                        findDuplicateChar();
                        break;
                    case 2:
                        generateRandomPassword();
                        break;
                    case 3:
                        generateUniqueRandomPassword();
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
            catch (Exception e) 
            {
                System.err.printf("\n%s\n\n", e);
                input = new Scanner(System.in);
            }
        } while(loop);
    }

    public static String findDuplicateChar() 
    {     
        Scanner in = new Scanner(System.in);

        System.out.println("\nEnter String to check for duplicate char: ");
        String password = in.next();
        if ((password==null) || password.length()<=0) throw ILLEGAL_PASSWORD;
    
        char[] charArray = new char[password.length()];
        String duplicate = "";
        HashSet<Character> charSet = new HashSet<>();
        char c;
        
        for (int i=0; i<password.length(); i++) 
        {
            c = password.charAt(i);
            if (!charSet.add(c))        // duplicate found in charSet
            {
                duplicate += c;
                charArray[i] = c;
            }
            else charArray[i] = '_';    // blank filler for unique char
        }

        if (duplicate.equals("")) 
            System.out.printf("\nNo duplicate character(s) for \"%s\"\n", password);
        else 
        {  
            System.out.printf("\nDuplicate character(s) for \"%s\" ==> ___%s___\n", password, duplicate);
            System.out.format("%-22s: %s\n\n", "Duplicate character(s)", Arrays.toString(charArray));
            // System.out.printf("%s\n\n", Arrays.toString(charArray));
        }
        return Arrays.toString(charArray);
    }

    // Generate SecureRandom password of specific length
    public static String generateRandomPassword() 
    {
        SecureRandom random = new SecureRandom();
        Scanner in = new Scanner(System.in);

        System.out.print("\nEnter password length: ");
        if (!in.hasNextInt()) throw new InputMismatchException("Non-Integer Length!");
        int len = in.nextInt();
        if (len<1) throw ILLEGAL_PASSWORD_SIZE;

        random = new SecureRandom();
        String combined = UPPER_CASE + LOWER_CASE + NUMBERS + SPECIAL_CHARACTER;
        StringBuilder ps = new StringBuilder();

        // each iteration of loop choose a character randomly from the given ASCII range
        // and append it to StringBuilder instance
        int randomIndex;
        for (int i = 0; i < len; i++) 
        {
            randomIndex = random.nextInt(combined.length());
            ps.append(combined.charAt(randomIndex));
        }

        System.out.format("\n%-9s: %s\n", "Password", ps.toString());
        String shuffledPassword = shuffled(ps.toString());
        System.out.format("%-9s: %s\n", "Shuffled", shuffledPassword);
        return shuffledPassword;
    }

    // Generate unique random password of specific length + special character upto special character count
    public static String generateUniqueRandomPassword()
    {
        SecureRandom random = new SecureRandom();
        Scanner in = new Scanner(System.in);

        System.out.print("\nEnter password length: ");
        if (!in.hasNextInt()) throw new InputMismatchException("Non-Integer Length!");
        int len = in.nextInt();
        if (len<1) throw ILLEGAL_PASSWORD_SIZE;

        System.out.print("Enter number of Special Characters: ");
        if (!in.hasNextInt()) throw new InputMismatchException("Non-Integer Special Character Count!");
        int numberOfSpecialChar = in.nextInt();
        if (numberOfSpecialChar<0 || numberOfSpecialChar>len) throw new IllegalArgumentException("Invalid Special Character Count!");

        String combined = UPPER_CASE + LOWER_CASE + NUMBERS + SPECIAL_CHARACTER;
        StringBuilder ps = new StringBuilder();
        HashSet<Integer> uniqueIndex = new HashSet<>();
        
        int randomIndex; 
        boolean checkSpecialChar;
        int count=0, i=0;
        while (i < len) 
        {
            randomIndex = random.nextInt(combined.length());
            if (uniqueIndex.add(randomIndex)) 
            {
                checkSpecialChar = SPECIAL_CHARACTER.contains(Character.toString(combined.charAt(randomIndex)));
                if (checkSpecialChar)   // generate special character up to special character count
                {
                    if (count<numberOfSpecialChar) {
                        ps.append(combined.charAt(randomIndex));
                        i++;
                        count++;
                        continue;
                    }
                    else continue;
                } 
                ps.append(combined.charAt(randomIndex));
                i++;
            }
        }

        System.out.format("\n%-9s: %s\n", "Password", ps.toString());
        String shuffledPassword = shuffled(ps.toString());
        System.out.format("%-9s: %s\n", "Shuffled", shuffledPassword);
        return shuffledPassword;
    }

    // Helper method to shuffle password for good measure
    public static String shuffled(String password) 
    {
        List<String> ps = Arrays.asList(password.split(""));
        Collections.shuffle(ps);
        return ps.stream().collect(Collectors.joining());
    }
}
