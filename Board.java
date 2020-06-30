//Board class
//Database and game progress

class Board {
    // Board matrix
    String[][] board = new String[7][15];
    // String markers
    String currMarker, red, yellow, green, blue, indigo, orange, violet;
    // String array for players
    String[] players, listOfPlayers;
    // Integers
    int currPlayerIndex, markerCounter, spacesOpen, connectN;

    // constructor
    // makes the blank board
    // sets all the markers and players
    public Board() {
        makeBoard();
        this.red = " R ";
        this.orange = " O ";
        this.yellow = " Y ";
        this.green = " G ";
        this.blue = " B ";
        this.indigo = " I ";
        this.violet = " V ";
        this.listOfPlayers = new String[] { this.red, this.orange, this.yellow, this.green, this.blue, this.indigo,
                this.violet };
        currMarker = this.red;
        this.currPlayerIndex = 1;
        this.markerCounter = 0;
        this.spacesOpen = 42;
        this.connectN = 4;
    }

    // create the board matrix
    private void makeBoard() {
        int colNum = 1;
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                if (j == 0) {
                    this.board[i][j] = "|";
                }
                if (j == board.length - 1) {
                    this.board[i][j] = "|";
                }
                if ((j % 2) != 0) {
                    this.board[i][j] = "   ";
                } else {
                    this.board[i][j] = "|";
                }
                if (i == board.length - 1) {
                    if (j % 2 != 0) {
                        this.board[i][j] = String.format(" %s ", String.valueOf(colNum));
                        colNum++;
                    }
                }

            }
        }
    }

    // get the board
    public String[][] getBoard() {
        return this.board;
    }

    // print the board matrix
    public void printBoard() {
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                System.out.print(this.board[i][j]);
                if (j == (this.board[i].length - 1)) {
                    System.out.print("\n");
                }
            }
        }
        System.out.print("\n");
    }

    // make the players array for the current game
    public void setConnectN(int N) {
        this.connectN = N;
        System.out.println();
        System.out.println(String.format("First one to connect %d in a row, wins!", N));
        System.out.println("Good luck!");
        System.out.println();
    }

    // make players array
    public void makePlayers(int num) {
        this.players = new String[num];
        for (int i = 0; i < num; i++) {
            this.players[i] = this.listOfPlayers[i];
        }
        printPlayers();
    }

    // print the ist of payers
    private void printPlayers() {
        for (int j = 0; j < this.players.length; j++) {
            if (j == 0) {
                System.out.println(String.format("Player %d: %s", (j + 1), this.players[j]));
            } else {
                System.out.println(String.format("Computer %d: %s", (j), this.players[j]));
            }
        }
        System.out.println();
    }

    // return all the current players
    public String[] getPlayers() {
        return this.players;
    }

    // return the current single player
    public String getCurrPlayer() {
        return this.currMarker;
    }

    public int colOffSet(int column) {
        // offSet for column indices
        if (column == 2) {
            column = 3;
        } else if (column == 3) {
            column = 5;
        } else if (column == 4) {
            column = 7;
        } else if (column == 5) {
            column = 9;
        } else if (column == 6) {
            column = 11;
        } else if (column == 7) {
            column = 13;
        }
        return column;
    }

    // add the marker to the board if space is open
    public int[] addMarker(int col) {
        // coordinates for marker in matrix
        int[] coords = new int[2];
        // offSet for column indices
        col = colOffSet(col);
        // loop to check if column has space. If so add marker
        for (int i = this.board.length - 2; i >= 0; i--) {
            if (!this.board[0][col].equals("   ")) {
                coords = new int[1];
                break;
            } else {
                if (this.board[i][col].equals("   ")) {
                    this.board[i][col] = currMarker;
                    coords[0] = i;
                    coords[1] = col;
                    spacesOpen--;
                    break;
                } else {
                    continue;
                }
            }
        }
        return coords;
    }

    // check how many spaces are left
    public int spacesLeft() {
        return this.spacesOpen;
    }

    // update the marker to the next player and loop the index
    public void updateCurrMarker() {
        if (currPlayerIndex == this.players.length) {
            currPlayerIndex = 0;
        }
        this.currMarker = this.players[this.currPlayerIndex];
        printBoard();
        currPlayerIndex++;
    }

    // declare if the current player has reached the N number in a row
    private boolean isWinner() {
        if (this.markerCounter == this.connectN) {
            return true;
        }
        return false;
    }

    // check if there is a winner vertically
    // if it's anything other than the current player, skip
    // if it is the current players marker, increment counter and check if the
    // counter equals the N number
    public boolean checkVertical(int col) {
        for (int i = this.board.length - 2; i > 0; i--) {
            if (this.board[i][col].equals(this.currMarker)) {
                this.markerCounter++;
                if (isWinner()) {
                    return true;
                }
                continue;
            } else {
                markerCounter = 0;
                continue;
            }
        }
        return isWinner();
    }

    // check if there is a winner horizontally
    // if it's anything other than the current player, skip
    // if it is the current players marker, increment counter and check if the
    // counter equals the N number
    public boolean checkHorizontal(int row) {
        for (int i = 0; i < this.board[row].length; i++) {
            if (this.board[row][i].equals("|")) {
                continue;
            } else {
                if (this.board[row][i].equals(this.currMarker)) {
                    this.markerCounter++;
                    if (isWinner()) {
                        return true;
                    }
                    continue;
                } else {
                    markerCounter = 0;
                    continue;
                }
            }
        }
        return isWinner();
    }

    // check if there is a winner diagonally
    public boolean checkDiagonal() {
        // search first half of diagonal
        int col = 0, row = 0;
        String[][] b = this.getBoard();
        while (row < b.length) {
            int rowTemp = row;
            while (rowTemp < b.length) {
                String curr = b[rowTemp][col];
                if (curr.equals("|")) {
                    col++;
                    continue;
                } else {
                    if (curr.equals(this.currMarker)) {
                        markerCounter++;
                        if (isWinner()) {
                            return true;
                        }
                    } else {
                        markerCounter = 0;
                    }
                    rowTemp++;
                    col++;
                    continue;
                }
            }
            col = 0;
            row++;
        }
        // search second half of diagonal
        col = 0;
        while (col < b[1].length) {
            int colTemp = col;
            row = b.length - 1;
            while (colTemp < b[1].length - 1) {
                String curr = b[row][colTemp];
                if (curr.equals("|")) {
                    colTemp++;
                    continue;
                }
                if (curr.equals(this.currMarker)) {
                    markerCounter++;
                    if (isWinner()) {
                        return true;
                    }
                } else {
                    markerCounter = 0;
                }
                colTemp++;
                row--;
                continue;
            }
            row = b.length - 1;
            col++;
        }
        return isWinner();
    }

}