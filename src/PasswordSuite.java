import java.util.*;
import java.io.*;
import java.security.SecureRandom;
import java.util.stream.Collectors;

class PasswordSuite
{
    private static final String UPPER_CASE              = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_CASE              = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS                 = "0123456789";
    private static final String ASCII_SPECIAL_CHARACTER = "!\"#$%&\'()*+,-./:;<=>?@[\\]^_`{|}~";   // ASCII special characters in ascending order
    private SecureRandom random;
    private Scanner input;
    private String combined;
    private int combinedLength;

    public PasswordSuite() {
        this.random   = new SecureRandom();
        this.input    = null;
        this.combined = UPPER_CASE + LOWER_CASE + NUMBERS + ASCII_SPECIAL_CHARACTER;
        this.combinedLength = combined.length();
    }


    public String findDuplicateChar()
    {
        input = new Scanner(System.in);
        System.out.println("\nEnter String to check for duplicate char: ");
        String password = input.next();
        int passwordLength = password.length();

        char[] charArray = new char[passwordLength];
        String duplicate = "";
        HashSet<Character> charSet = new HashSet<>();
        char c;

        for (int i=0; i<passwordLength; i++)
        {
            c = password.charAt(i);
            if (!charSet.add(c))        // char c already cached in hashset
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
        }
        return Arrays.toString(charArray);
    }


    // Generate SecureRandom password of specified length
    public String generateRandomPassword()
    {
        int passwordLength = getLength();
        StringBuilder ps = new StringBuilder();

        int randomIndex;
        for (int i=0; i<passwordLength; i++)
        {
            randomIndex = random.nextInt(combinedLength);
            ps.append(combined.charAt(randomIndex));
        }

        System.out.format("\n%-9s: %s\n", "Password", ps.toString());
        String password = shuffled(ps.toString());
        System.out.format("%-9s: %s\n", "Shuffled", password);
        return password;
    }


    // Generate unique random password of specified length + special character upto special character count
    // --Unique password length can only be upto 94 or combined.length()
    public String generateUniqueRandomPassword()
    {
        int passwordLength = getLength();
        if (passwordLength>combinedLength)
            throw new IllegalArgumentException("Password length must be < 95");
        int numberOfSpecialChar = getSpecialCharacterCount(passwordLength);

        HashSet<Integer> uniqueIndex = new HashSet<>();
        StringBuilder ps = new StringBuilder();

        int randomIndex;
        boolean checkSpecialChar;
        int count=0, i=0;

        while (i<passwordLength)
        {
            randomIndex = random.nextInt(combinedLength);
            if (uniqueIndex.add(randomIndex))
            {
                checkSpecialChar = ASCII_SPECIAL_CHARACTER.contains(Character.toString(combined.charAt(randomIndex)));
                if (checkSpecialChar)
                {   // generate special character up to special character count
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
        String password = shuffled(ps.toString());
        System.out.format("%-9s: %s\n", "Shuffled", password);
        return password;
    }



    /****************************************************/
    // Helper method to shuffle password for good measure
    public String shuffled(String word)
    {
        List<String> ps = Arrays.asList(word.split(""));
        Collections.shuffle(ps);
        return ps.stream().collect(Collectors.joining());
    }

    public int getLength()
    {
        input = new Scanner(System.in);
        System.out.print("\nEnter password length: ");

        if (!input.hasNextInt())
            throw new InputMismatchException("Password length must an integer");

        int passwordLength = input.nextInt();

        if (passwordLength<1)
            throw new IllegalArgumentException("Password length must be > 0");

        return passwordLength;
    }

    public int getSpecialCharacterCount(int passwordLength)
    {
        input = new Scanner(System.in);
        System.out.print("Enter number of Special Characters: ");

        if (!input.hasNextInt())
            throw new InputMismatchException("Special Character count must be an integer");

        int numberOfSpecialChar = input.nextInt();

        if (numberOfSpecialChar<0)
            throw new IllegalArgumentException("Special Character Count must be >= 0");
        else if (numberOfSpecialChar>passwordLength)
            throw new IllegalArgumentException("Special Character Count must be < password length");

        return numberOfSpecialChar;
    }
}
