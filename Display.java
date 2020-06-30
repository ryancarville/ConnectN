
//Display class
//Contains all I/O
import java.io.*;

class Display {
    // BufferedReader
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    // colum integer
    int col = 0;

    // constructor
    public Display() {
        welcome();
    }

    // welcome message
    public void welcome() {
        System.out.println("");
        System.out.println("Welcome to ConnectN!!!");
        System.out.println("INSTRUCTIONS:");
        System.out.println("1. Enter the number of players you would like(2 - 7):");
        System.out.println("2. Enter the number of \"N\" you would like to play with (2 - 6):");
        System.out.println("3. You go first as the marker \"R\".  Select a column to place your marker.");
        System.out.println(
                "4. The sequence of computers will play automatically, but you will be given their move coordinates and the board when played.");
        System.out.println("5. Repeat steps 2-3 until a winner is declared or it's called a draw.");
        System.out
                .println("Winner declared when a player has \"N\" markers in a horizontal, vertical or diagonal row.");
        System.out.println("");
        System.out.println("To pause at any time enter \"P\". (case-sensitive)");
        System.out.println("");

    }

    // display whos turn it is
    public void whosTurn(String player) {
        System.out.println(String.format("%s it is your turn.", player));
    }

    // get number of players
    public void getNumOfPlayers() {
        System.out.print("How many players? 2 thru 7: ");
    }

    // ask for move
    public int getCol() {
        try {
            String input;
            System.out.print("Please enter a column:");
            input = reader.readLine();
            if (input.equals("P")) {
                if (playAgain(true)) {
                    return getCol();
                }
            } else if (input.length() == 0 || !String.valueOf(input.charAt(0)).matches("[1-7]")) {
                System.out.println("Entry must contain a valid number.");
                System.out.println(String.format("Your entry was: %s", input));
                System.out.println("Please try again.");
                return getCol();
            } else {
                col = Integer.parseInt(String.valueOf(input.charAt(0)));
                return col;
            }
        } catch (IOException e) {
            System.err.print(e.getMessage());
        }
        return col;
    }

    // ask for "N"
    public int getN() {
        int N = 0;
        try {
            System.out.print("Please enter the \"N\" you would like (3-6): ");
            String input = reader.readLine();

            if (input.equals("P")) {
                if (playAgain(true)) {
                    return getN();
                }
            }
            if (input.length() == 0 || !String.valueOf(input.charAt(0)).matches("[3-6]")
                    || input.matches("!@#$%^&*()><?/=+")) {
                System.out.println("Entry must contain a valid number.");
                System.out.println(String.format("Your entry was: %s", input));
                System.out.println("Please try again.");
                return getN();
            } else {
                N = Integer.parseInt(input);
            }
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
        return N;
    }

    // get number of players
    // default is 2
    public int getP() {
        int p = 2;
        try {
            System.out.println("How many players? (2-7)");
            String input = reader.readLine();
            if (input.equals("P")) {
                if (playAgain(true)) {
                    return getP();
                }
            }
            if (input.length() == 0 || !String.valueOf(input.charAt(0)).matches("[2-7]")) {
                System.out.println("Entry must contain a valid number of players.");
                System.out.println(String.format("Your entry was: %s", input));
                System.out.println("Please try again.");
                return getP();
            } else {
                p = Integer.parseInt(input);
            }
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
        return p;
    }

    // ask if they would like to play again or quit
    public boolean playAgain(boolean pause) {
        try {
            if (pause) {
                System.out.print("Game paused. Do you want to continue? (y/n)");
            } else {
                System.out.print("Do you want to play again? (y/n)");
            }
            String input = reader.readLine();
            if (input.length() < 0) {
                System.out.println("Invalid input.  Entry must be a \"y\" or \"n\". Please try again.");
            } else {
                input = String.valueOf(input.charAt(0));
                input.toLowerCase();
                System.out.println("input: " + input);
                if (input.equals("y")) {
                    return true;
                } else if (input.equals("n")) {
                    System.out.println("Thanks for playing!!!");
                    System.exit(1);
                } else {
                    System.out.println("Invalid input.  Entry must be a \"y\" or \"n\". Please try again.");
                    return playAgain(pause);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}