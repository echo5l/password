import java.util.*;
import java.io.*;
import java.security.SecureRandom;
import java.util.stream.Collectors;

public class PasswordSuite
{
    private static final IllegalArgumentException ILLEGAL_PASSWORD_SIZE  = new IllegalArgumentException("Password length must be >= 1");
    private static final IllegalArgumentException ILLEGAL_PASSWORD       = new IllegalArgumentException("Invalid Password!");
    private static final InputMismatchException INPUT_MISMATCH_EXCEPTION = new InputMismatchException("Password length must an integer");
    private static final String UPPER_CASE              = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_CASE              = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS                 = "0123456789";
    private static final String ASCII_SPECIAL_CHARACTER = "!\"#$%&\'()*+,-./:;<=>?@[\\]^_`{|}~";   // ASCII special characters in ascending order
    private String password;
    private String combined;
    private int combinedLength;
    private int length;

    public PasswordSuite() {
        this.password = null;
        this.combined = UPPER_CASE + LOWER_CASE + NUMBERS + ASCII_SPECIAL_CHARACTER;
        this.combinedLength = combined.length();
        this.length   = 0;
    }

    public String findDuplicateChar()
    {
        Scanner in = new Scanner(System.in);

        System.out.println("\nEnter String to check for duplicate char: ");
        password = in.next();
        if ((password==null) || password.length()<=0) throw ILLEGAL_PASSWORD;

        char[] charArray = new char[password.length()];
        String duplicate = "";
        HashSet<Character> charSet = new HashSet<>();
        char c;

        for (int i=0; i<password.length(); i++)
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

    // Generate SecureRandom password of specific length
    public String generateRandomPassword()
    {
        SecureRandom random = new SecureRandom();
        Scanner in = new Scanner(System.in);
        StringBuilder ps = new StringBuilder();

        System.out.print("\nEnter password length: ");
        if (!in.hasNextInt()) throw INPUT_MISMATCH_EXCEPTION;
        length = in.nextInt();
        if (length<1) throw ILLEGAL_PASSWORD_SIZE;

        // each iteration of loop choose a character randomly from the given ASCII range
        // and append it to StringBuilder instance
        int randomIndex;
        for (int i=0; i<length; i++)
        {
            randomIndex = random.nextInt(combinedLength);
            ps.append(combined.charAt(randomIndex));
        }

        System.out.format("\n%-9s: %s\n", "Password", ps.toString());
        password = shuffled(ps.toString());
        System.out.format("%-9s: %s\n", "Shuffled", password);
        return password;
    }

    // Generate unique random password of specific length + special character upto special character count
    public String generateUniqueRandomPassword()
    {
        SecureRandom random = new SecureRandom();
        Scanner in = new Scanner(System.in);

        System.out.print("\nEnter password length: ");
        if (!in.hasNextInt()) throw INPUT_MISMATCH_EXCEPTION;
        length = in.nextInt();
        if (length<1) throw ILLEGAL_PASSWORD_SIZE;

        System.out.print("Enter number of Special Characters: ");
        if (!in.hasNextInt()) throw new InputMismatchException("Special Character count must be an integer");
        int numberOfSpecialChar = in.nextInt();
        if (numberOfSpecialChar<0 || numberOfSpecialChar>length)
            throw new IllegalArgumentException("Special Character Count must be a non-negative int AND <= password length");

        HashSet<Integer> uniqueIndex = new HashSet<>();
        StringBuilder ps = new StringBuilder();

        int randomIndex;
        boolean checkSpecialChar;
        int count=0, i=0;
        while (i<length)
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
        password = shuffled(ps.toString());
        System.out.format("%-9s: %s\n", "Shuffled", password);
        return password;
    }

    // Helper method to shuffle password for good measure
    public String shuffled(String word)
    {
        List<String> ps = Arrays.asList(word.split(""));
        Collections.shuffle(ps);
        return ps.stream().collect(Collectors.joining());
    }
}
