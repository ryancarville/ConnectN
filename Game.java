//Game Class
//Superclass
//Contains all the flow control
class Game {
    // classes
    Display display;
    Board board;
    // Integers
    int N, P, col;

    // constructor
    public Game() {
        this.display = new Display();
        this.board = new Board();
        board.printBoard();
        start();
    }

    // starts the game by getting "N" number
    // printing the players
    // asking for the first move
    private void start() {
        P = display.getP();
        board.makePlayers(P);
        N = display.getN();
        board.setConnectN(N);
        getCol();
    }

    // obtains the column input and checks if it is a open space
    // checks if there is a winner yet, if, declare winner, else update the board
    // and continue
    public void getCol() {
        display.whosTurn(board.getCurrPlayer().trim());
        col = display.getCol();
        int[] coords = board.addMarker(col);
        if (coords.length > 1) {
            checkBoard(coords);
        } else {
            System.out.println("That column is already full.");
            getCol();
        }

    }

    // get random number for computer move and execute move
    private void compMove() {
        int min = 1;
        int max = 7;
        int range = max - min + 1;
        int col = (int) (Math.floor(Math.random() * range) + 1);
        System.out.println("Rand: " + col);
        display.whosTurn(board.getCurrPlayer().trim());
        if (0 >= col || col >= 8) {
            compMove();
        } else {
            int[] coords = board.addMarker(col);
            if (coords.length > 1) {
                System.out.println(
                        String.format("%s moved to: [%d, %d]", board.getCurrPlayer().trim(), (coords[0] + 1), col));
                checkBoard(coords);
            } else {
                System.out.println("That column is already full.");
                compMove();
            }
        }
    }

    // check for winner
    // else check if there are no more spaces
    // flow control for computer players and human
    public void checkBoard(int[] coords) {
        if (board.checkHorizontal(coords[0]) || board.checkVertical(coords[1]) || board.checkDiagonal()) {
            declareWinner();
        } else if (board.spacesOpen == 0) {
            System.out.println("");
            System.out.println("--------------------------------");
            System.out.println("It's a draw!");
            board.printBoard();
            if (display.playAgain(false)) {
                new Game();
            }
        } else {
            board.updateCurrMarker();
            if (!board.getCurrPlayer().equals(board.getPlayers()[0])) {
                compMove();
            } else {
                getCol();
            }
        }
    }

    // declare the winner
    // new game or exit
    private void declareWinner() {
        board.printBoard();
        System.out.println("The winner is " + board.getCurrPlayer());
        if (display.playAgain(false)) {
            new Game();
        }
    }

}