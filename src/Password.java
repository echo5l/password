import java.util.*;

public class Password
{

    public static void main (String args[])
    {

        int choice;
        Scanner input = new Scanner(System.in);
        PasswordSuite ps = new PasswordSuite();
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
                        ps.findDuplicateChar();
                        break;
                    case 2:
                        ps.generateRandomPassword();
                        break;
                    case 3:
                        ps.generateUniqueRandomPassword();
                        break;
                    case 4:
                        System.out.println("\nExiting Program...\n");
                        input.close();
                        loop = false;
                        break;
                    default:
                        System.out.printf("\nInvalid Menu Option <%d>\n\n", choice);
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


    public static void printMenu()
    {
        System.out.println("\n__Menu Options__: ");
        System.out.println("1.) Check Duplicate Character");
        System.out.println("2.) Password Generator");
        System.out.println("3.) Unique Password Generator");
        System.out.println("4.) Exit");
    }

}
