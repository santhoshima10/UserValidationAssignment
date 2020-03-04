

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class UserValidation {
    static User[] userArray = new User[4];
    static String name;

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        int noOfLoginAttempts = 5;
        boolean loginSuccessful = false;
        String inputEmailId = "";
        String inputPassword = "";

        processUserDataFromFile();

        //This check is added to proceed only if the array of user objects are loaded from file.
        if(userArray[0]!=null) {

            UserValidation userValidation = new UserValidation();
            Scanner scanner = new Scanner(System.in);

            while (noOfLoginAttempts > 0) {
                System.out.print("Enter your email:");
                inputEmailId = scanner.nextLine();
                System.out.print("Enter your password:");
                inputPassword = scanner.nextLine();
                if (userValidation.validateUser(inputEmailId, inputPassword)) {
                    loginSuccessful = true;
                    break;
                } else {
                    System.out.println("Invalid login, please try again.");
                    noOfLoginAttempts--;
                }

            }
            if (loginSuccessful) {
                System.out.println("Welcome " + name);
            } else {
                System.out.println("Too many failed login attempts, you are now locked out.");
            }
            scanner.close();
        }

    }

    // function to process the user data from text file and create an array of user
    // data objects
    public static void processUserDataFromFile() {

        int index = 0;
        BufferedReader bufferedReader = null;

        try {

            bufferedReader = new BufferedReader(new FileReader("data.txt"));
            String line = bufferedReader.readLine();
            while (line != null) {
                User user = createUserObject(line);
                userArray[index] = user;
                index++;
                line = bufferedReader.readLine();
            }

        } catch (IOException ex) {
            System.out.println("Error in Reading the file using the BufferedReader");
            ex.printStackTrace();
        }
        finally {
            if(bufferedReader!=null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    System.out.println("Error closing the BufferedReader");
                    e.printStackTrace();
                }
            }
        }

    }

    public static User createUserObject(String input) {

        String[] userDetails = input.split(",");
        User user = new User();
        user.setLoginEmail(userDetails[0]);
        user.setLoginPassword(userDetails[1]);
        user.setLoginUsername(userDetails[2]);
        return user;

    }

    // function to check if the user name and password entered from console matches
    // the user data objects
    public boolean validateUser(String emailId, String password) {

        boolean isValid = false;

        for (int i = 0; i < userArray.length; i++) {
            if (userArray[i] != null) {
                User user = userArray[i];
                if (user.getLoginEmail().equalsIgnoreCase(emailId) && user.getLoginPassword().equals(password)) {
                    name = user.getLoginUsername();
                    isValid = true;
                    break;
                }
            } else {
                break;
            }
        }
        return isValid;
    }

}
