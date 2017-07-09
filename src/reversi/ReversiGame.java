package reversi;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReversiGame extends Game {

    private ReversiController controller;
    public int[][] board;
    int turn;
    boolean running;

    public ReversiGame(Agent a, Agent b, ReversiController con) {
        super(a, b);

        controller = con;
        a.setRole(0);//computer red
        b.setRole(1); //human blue

        name = "Reversi";
        running = true;
        board = new int[8][8];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = -1;//empty
            }
        }
        board[3][3] = 1;//max blue
        board[4][4] = 1;
        board[3][4] = 0; //min red
        board[4][3] = 0;

    }

    int getCellColor(int i, int j) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return board[i][j];

    }

    /**
     * Called by the play method of Game class. It must update the winner
     * variable. In this implementation, it is done inside checkForWin()
     * function.
     */
    @Override
    void initialize(boolean fromFile) {
        // TODO Auto-generated method stub
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = -1;//empty
            }
        }
        board[3][3] = 1;//max blue
        board[4][4] = 1;
        board[3][4] = 0; //min red
        board[4][3] = 0;
        agent[0].setScore(2);
        agent[1].setScore(2);
    }

    /**
     * Prints the current board (may be replaced/appended with by GUI)
     */
    @Override
    public void play() {
        start();

    }

    @Override
    void showGameState() {
        // TODO Auto-generated method stub

        System.out.println("-----------------------------------------");

        for (int i = 0; i < 8; i++) {
            System.out.print("| ");
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == -1) {
                    System.out.print(" " + " | ");
                } else if (board[i][j] == 0) {
                    System.out.print("O | ");
                } else {
                    System.out.print("X | ");
                }
            }
            System.out.println();
            System.out.println("------------------------------------------");
        }
    }

    /**
     * Loop through all cells of the board and if one is found to be empty
     * (contains -1) then return false. Otherwise the board is full.
     */
    public boolean isBoardFull() {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == -1) //empty
                {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean validMoveExists(int role) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (isValidCell(i, j, role)) {
                    //System.out.println("sldfj "+i+" "+j+" "+role);
                    return true;

                }
            }
        }
        return false;
    }

    @Override
    boolean isFinished() {
        // TODO Auto-generated method stub
        if (checkForWin() != -1) {
            return true;
        }
        return false;
    }

    public int checkForWin() {
        winner = null;
        int winRole = -1;
        //row
        if (!isBoardFull() && (validMoveExists(0) || validMoveExists(1))) {
            return winRole;
        }
        int blue = agent[1].getScore();
        int red = agent[0].getScore();
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                if (board[i][j] == 0) {
//                    red++;
//                } else if (board[i][j] == 1) {
//                    blue++;
//                }
//            }
//        }

        if (red > blue) {
            winRole = 0;
            winner = agent[0];

        } else if (blue > red) {
            winRole = 1;
            winner = agent[1];

        } else {
            winRole = -2;
        }

        return winRole;
    }

    public boolean isValidCell(int row, int col, int role) {
        if (row < 0 || row > 7 || col < 0 || col > 7) {
            return false;
        }
        if (board[row][col] != -1) {
            return false;
        }

        return opponentExists(row, col, role);

    }

    @Override
    void updateMessage(String msg) {
        // TODO Auto-generated method stub
        System.out.println(msg);
    }

    public boolean opponentExists(int row, int col, int role) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        int count = 0;
        for (int j = col + 1; j < 8; j++)//right direction
        {
            if (board[row][j] == role || board[row][j] == -1) {
                if (board[row][j] == role) // flip opponent
                {
                    for (int k = col + 1; k < j; k++) {
                        //board[row][k] = role;
                        count++;
                    }
                }
                break;
            }

        }
        if (count > 0) {
            return true;
        }
        for (int j = col - 1; j >= 0; j--)//left direction
        {
            if (board[row][j] == role || board[row][j] == -1) {
                if (board[row][j] == role) // flip opponent
                {
                    for (int k = col - 1; k > j; k--) {
                        // board[row][k] = role;
                        count++;
                    }
                }
                break;
            }

        }

        if (count > 0) {
            return true;
        }
        for (int i = row - 1; i >= 0; i--)//up direction
        {
            if (board[i][col] == role || board[i][col] == -1) {
                if (board[i][col] == role) {
                    for (int k = row - 1; k > i; k--) {
                        //board[k][col] = role;
                        count++;
                    }
                }
                break;
            }

        }

        if (count > 0) {
            return true;
        }
        for (int i = row + 1; i < 8; i++)//down direction
        {
            if (board[i][col] == role || board[i][col] == -1) {
                if (board[i][col] == role) {
                    for (int k = row + 1; k < i; k++) {
                        //board[k][col] = role;
                        count++;
                    }
                }
                break;
            }

        }

        if (count > 0) {
            return true;
        }
        int i, j;
        for (i = row + 1, j = col + 1; i < 8 && j < 8; i++, j++)// right down direction
        {
            if (board[i][j] == role || board[i][j] == -1) {
                if (board[i][j] == role) {
                    int k, p;
                    for (k = row + 1, p = col + 1; k < i && p < j; k++, p++) {
                        // board[k][p] = role;
                        count++;
                    }
                }
                break;
            }

        }

        if (count > 0) {
            return true;
        }
        for (i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--)// left up direction
        {
            if (board[i][j] == role || board[i][j] == -1) {
                if (board[i][j] == role) {
                    int k, p;
                    for (k = row - 1, p = col - 1; k > i && p > j; k--, p--) {
                        // board[k][p] = role;
                        count++;
                    }
                }
                break;
            }

        }
        if (count > 0) {
            return true;
        }
        for (i = row - 1, j = col + 1; i >= 0 && j < 8; i--, j++)// right up direction
        {
            if (board[i][j] == role || board[i][j] == -1) {
                if (board[i][j] == role) {
                    int k, p;
                    for (k = row - 1, p = col + 1; k > i && p < j; k--, p++) {
                        //  board[k][p] = role;
                        count++;
                    }
                }
                break;
            }

        }
        if (count > 0) {
            return true;
        }
        for (i = row + 1, j = col - 1; i < 8 && j >= 0; i++, j--)// left down direction
        {
            if (board[i][j] == role || board[i][j] == -1) {
                if (board[i][j] == role) {
                    int k, p;
                    for (k = row + 1, p = col - 1; k < i && p > j; k++, p--) {
                        //board[k][p] = role;
                        count++;
                    }
                }
                break;
            }

        }
        if (count > 0) {
            return true;
        }
        return false;

    }

    public int opponentCount(int row, int col, int role) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        int count = 0;
        for (int j = col + 1; j < 8; j++)//right direction
        {
            if (board[row][j] == role || board[row][j] == -1) {
                if (board[row][j] == role) // flip opponent
                {
                    for (int k = col + 1; k < j; k++) {
                        //board[row][k] = role;
                        count++;
                    }
                }
                break;
            }

        }

        for (int j = col - 1; j >= 0; j--)//left direction
        {
            if (board[row][j] == role || board[row][j] == -1) {
                if (board[row][j] == role) // flip opponent
                {
                    for (int k = col - 1; k > j; k--) {
                        // board[row][k] = role;
                        count++;
                    }
                }
                break;
            }

        }

        for (int i = row - 1; i >= 0; i--)//up direction
        {
            if (board[i][col] == role || board[i][col] == -1) {
                if (board[i][col] == role) {
                    for (int k = row - 1; k > i; k--) {
                        //board[k][col] = role;
                        count++;
                    }
                }
                break;
            }

        }

        for (int i = row + 1; i < 8; i++)//down direction
        {
            if (board[i][col] == role || board[i][col] == -1) {
                if (board[i][col] == role) {
                    for (int k = row + 1; k < i; k++) {
                        //board[k][col] = role;
                        count++;
                    }
                }
                break;
            }

        }

        int i, j;
        for (i = row + 1, j = col + 1; i < 8 && j < 8; i++, j++)// right down direction
        {
            if (board[i][j] == role || board[i][j] == -1) {
                if (board[i][j] == role) {
                    int k, p;
                    for (k = row + 1, p = col + 1; k < i && p < j; k++, p++) {
                        // board[k][p] = role;
                        count++;
                    }
                }
                break;
            }

        }

        for (i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--)// left up direction
        {
            if (board[i][j] == role || board[i][j] == -1) {
                if (board[i][j] == role) {
                    int k, p;
                    for (k = row - 1, p = col - 1; k > i && p > j; k--, p--) {
                        // board[k][p] = role;
                        count++;
                    }
                }
                break;
            }

        }

        for (i = row - 1, j = col + 1; i >= 0 && j < 8; i--, j++)// right up direction
        {
            if (board[i][j] == role || board[i][j] == -1) {
                if (board[i][j] == role) {
                    int k, p;
                    for (k = row - 1, p = col + 1; k > i && p < j; k--, p++) {
                        //  board[k][p] = role;
                        count++;
                    }
                }
                break;
            }

        }

        for (i = row + 1, j = col - 1; i < 8 && j >= 0; i++, j--)// left down direction
        {
            if (board[i][j] == role || board[i][j] == -1) {
                if (board[i][j] == role) {
                    int k, p;
                    for (k = row + 1, p = col - 1; k < i && p > j; k++, p--) {
                        //board[k][p] = role;
                        count++;
                    }
                }
                break;
            }

        }
        return count;

    }

    public void finishGame() {
        running = false;
    }

    @Override
    public void run() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        // updateMessage("Starting " + name + " between " + agent[0].name + " and " + agent[1].name + ".");

        controller.updateInfoLabel("Starting " + name + " between " + agent[1].name + " and " + agent[0].name + ".");

//        try {
//            sleep(2000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(ReversiGame.class.getName()).log(Level.SEVERE, null, ex);
//        }
        controller.updateInfoLabel("");

        turn = random.nextInt(2);

        //System.out.println(agent[turn].name+ " makes the first move.");
        initialize(false);
        controller.updateScoreBoard();
        controller.updateBoard();
        while (running && !isFinished()) {
            //updateMessage(agent[turn].name + "'s turn. ");
            controller.updateCurrentTurn(turn);
            if (validMoveExists(turn)) {

                int flipCount = agent[turn].makeMove(this);
                //   System.out.println(flipCount);
                if (flipCount > 0) {
                    agent[turn].increaseScore(flipCount + 1);
                    //showGameState();
                    agent[(turn + 1) % 2].decreaseScore(flipCount);
                } else {
//                    try {
//                        throw new Exception();
//                    } catch (Exception ex) {
//                        Logger.getLogger(ReversiGame.class.getName()).log(Level.SEVERE, null, ex);
//                    }
                    System.out.println("problem detect");
                    controller.updateInfoLabel("problem detected");
                }

                controller.updateBoard();
                controller.updateScoreBoard();

//                try {
//                    sleep(500);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(ReversiGame.class.getName()).log(Level.SEVERE, null, ex);
//                }
            } else {
                // updateMessage("passed");
                controller.updateInfoLabel("No valid move exists. Passed.");
                try {
                    sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ReversiGame.class.getName()).log(Level.SEVERE, null, ex);
                }
                controller.updateInfoLabel("");
            }

            turn = (turn + 1) % 2;
        }

        controller.updateScoreBoard();
        //System.out.println(agent[0].getScore() + " " + agent[1].getScore());
        if (winner != null) {
            // updateMessage(winner.name + " wins!!!");
            controller.updateWinLabel(winner.name + " wins!!!");

        } else {
            // updateMessage("Game drawn!!");
            controller.updateWinLabel("Game drawn!!");
        }
        //controller.initGUI();
    }

    int getTurn() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return turn;
    }

    int makeMove(int row, int col, int role) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

//        if (!isValidCell(row, col, role)) {
//            System.out.println("problem");
//        }
        board[row][col] = role;
        int count = 0;
        for (int j = col + 1; j < 8; j++)//right direction
        {
            if (board[row][j] == role || board[row][j] == -1) {
                if (board[row][j] == role) // flip opponent
                {
                    for (int k = col + 1; k < j; k++) {
                        board[row][k] = role;
                        count++;
                    }
                }
                break;
            }

        }

        for (int j = col - 1; j >= 0; j--)//left direction
        {
            if (board[row][j] == role || board[row][j] == -1) {
                if (board[row][j] == role) // flip opponent
                {
                    for (int k = col - 1; k > j; k--) {
                        board[row][k] = role;
                        count++;
                    }
                }
                break;
            }

        }

        for (int i = row - 1; i >= 0; i--)//up direction
        {
            if (board[i][col] == role || board[i][col] == -1) {
                if (board[i][col] == role) {
                    for (int k = row - 1; k > i; k--) {
                        board[k][col] = role;
                        count++;
                    }
                }
                break;
            }

        }

        for (int i = row + 1; i < 8; i++)//down direction
        {
            if (board[i][col] == role || board[i][col] == -1) {
                if (board[i][col] == role) {
                    for (int k = row + 1; k < i; k++) {
                        board[k][col] = role;
                        count++;
                    }
                }
                break;
            }

        }

        int i, j;
        for (i = row + 1, j = col + 1; i < 8 && j < 8; i++, j++)// right down direction
        {
            if (board[i][j] == role || board[i][j] == -1) {
                if (board[i][j] == role) {
                    int k, p;
                    for (k = row + 1, p = col + 1; k < i && p < j; k++, p++) {
                        board[k][p] = role;
                        count++;
                    }
                }
                break;
            }

        }

        for (i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--)// left up direction
        {
            if (board[i][j] == role || board[i][j] == -1) {
                if (board[i][j] == role) {
                    int k, p;
                    for (k = row - 1, p = col - 1; k > i && p > j; k--, p--) {
                        board[k][p] = role;
                        count++;
                    }
                }
                break;
            }

        }
        for (i = row - 1, j = col + 1; i >= 0 && j < 8; i--, j++)// right up direction
        {
            if (board[i][j] == role || board[i][j] == -1) {
                if (board[i][j] == role) {
                    int k, p;
                    for (k = row - 1, p = col + 1; k > i && p < j; k--, p++) {
                        board[k][p] = role;
                        count++;
                    }
                }
                break;
            }

        }
        for (i = row + 1, j = col - 1; i < 8 && j >= 0; i++, j--)// left down direction
        {
            if (board[i][j] == role || board[i][j] == -1) {
                if (board[i][j] == role) {
                    int k, p;
                    for (k = row + 1, p = col - 1; k < i && p > j; k++, p--) {
                        board[k][p] = role;
                        count++;
                    }
                }
                break;
            }

        }
        return count;

    }

    ArrayList<CellValueTuple> makeTemporaryMove(int row, int col, int role) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        ArrayList<CellValueTuple> tempMoves;
        tempMoves = new ArrayList<>();
        board[row][col] = role;
        tempMoves.add(new CellValueTuple(row, col, -1));
        for (int j = col + 1; j < 8; j++)//right direction
        {
            if (board[row][j] == role || board[row][j] == -1) {
                if (board[row][j] == role) // flip opponent
                {
                    for (int k = col + 1; k < j; k++) {
                        tempMoves.add(new CellValueTuple(row, k, (role + 1) % 2));
                        board[row][k] = role;
                    }
                }
                break;
            }

        }

        for (int j = col - 1; j >= 0; j--)//left direction
        {
            if (board[row][j] == role || board[row][j] == -1) {
                if (board[row][j] == role) // flip opponent
                {
                    for (int k = col - 1; k > j; k--) {
                        board[row][k] = role;
                        tempMoves.add(new CellValueTuple(row, k, (role + 1) % 2));
                    }
                }
                break;
            }

        }

        for (int i = row - 1; i >= 0; i--)//up direction
        {
            if (board[i][col] == role || board[i][col] == -1) {
                if (board[i][col] == role) {
                    for (int k = row - 1; k > i; k--) {
                        board[k][col] = role;
                        tempMoves.add(new CellValueTuple(k, col, (role + 1) % 2));
                    }
                }
                break;
            }

        }

        for (int i = row + 1; i < 8; i++)//down direction
        {
            if (board[i][col] == role || board[i][col] == -1) {
                if (board[i][col] == role) {
                    for (int k = row + 1; k < i; k++) {
                        board[k][col] = role;
                        tempMoves.add(new CellValueTuple(k, col, (role + 1) % 2));
                    }
                }
                break;
            }

        }

        int i, j;
        for (i = row + 1, j = col + 1; i < 8 && j < 8; i++, j++)// right down direction
        {
            if (board[i][j] == role || board[i][j] == -1) {
                if (board[i][j] == role) {
                    int k, p;
                    for (k = row + 1, p = col + 1; k < i && p < j; k++, p++) {
                        board[k][p] = role;
                        tempMoves.add(new CellValueTuple(k, p, (role + 1) % 2));
                    }
                }
                break;
            }

        }

        for (i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--)// left up direction
        {
            if (board[i][j] == role || board[i][j] == -1) {
                if (board[i][j] == role) {
                    int k, p;
                    for (k = row - 1, p = col - 1; k > i && p > j; k--, p--) {
                        board[k][p] = role;
                        tempMoves.add(new CellValueTuple(k, p, (role + 1) % 2));
                    }
                }
                break;
            }

        }
        for (i = row - 1, j = col + 1; i >= 0 && j < 8; i--, j++)// right up direction
        {
            if (board[i][j] == role || board[i][j] == -1) {
                if (board[i][j] == role) {
                    int k, p;
                    for (k = row - 1, p = col + 1; k > i && p < j; k--, p++) {
                        board[k][p] = role;
                        tempMoves.add(new CellValueTuple(k, p, (role + 1) % 2));
                    }
                }
                break;
            }

        }

        for (i = row + 1, j = col - 1; i < 8 && j >= 0; i++, j--)// left down direction
        {
            if (board[i][j] == role || board[i][j] == -1) {
                if (board[i][j] == role) {
                    int k, p;
                    for (k = row + 1, p = col - 1; k < i && p > j; k++, p--) {
                        board[k][p] = role;
                        tempMoves.add(new CellValueTuple(k, p, (role + 1) % 2));
                    }
                }
                break;
            }

        }
        return tempMoves;
    }

    void removeTemporaryMoves(ArrayList<CellValueTuple> tempMoves) {
        for (int i = tempMoves.size() - 1; i >= 0; i--) {
            board[tempMoves.get(i).row][tempMoves.get(i).col] = tempMoves.get(i).utility;

        }
    }
    int greedyUtility(int r)
    {
        int count =0;
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                if(board[i][j]==r)
                    count++;
            }
        }
        return count;
    }
    int approximateUtility(int r) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        //return countMoves(r);
        // int ownMove = 0;
        // int oppMove = 0;
        int ownScore = 0;
       
        //CellValueTuple tup = new CellValueTuple();
        //tup.utility = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //ownScore = opponentCount(i, j, r);
                if(board[i][j]==-1)
                {
                    ownScore+=opponentCount(i,j,r);
                }
                //oppScore = opponentCount(i, j, (r + 1) % 2);

                if (board[i][j] == r) {
                    ownScore++;
                    if (isCorner(i, j)) {
                        ownScore += 20;
                    } else if (isSide(i, j)) {
                        ownScore += 10;
                    } else if (isXsquare(i, j)) {
                        ownScore -= 35;
                    } else if (isCsquare(i, j)) {
                        ownScore -= 5;
                    }
                }
//                if (board[i][j] == (r + 1) % 2) {
//                    oppScore++;
//                    if (isCorner(i, j)) {
//                        oppScore += 20;
//                    } else if (isSide(i, j)) {
//                        oppScore += 10;
//                    } else if (isXsquare(i, j)) {
//                        oppScore -= 15;
//                    } else if (isCsquare(i, j)) {
//                        oppScore -= 5;
//                    }
//                }

            }
        }

       // System.out.println(ownScore);
        return ownScore ;

    }

    boolean isCorner(int i, int j) {
        if ((i == 0 || i == 7) && (j == 0 || j == 7)) {
            return true;
        }
        return false;
    }

    boolean isSide(int i, int j) {
        if ((i == 0 || i == 7) || (j == 0 || j == 7)) {
            return true;
        }
        return false;
    }

    boolean isCsquare(int i, int j) {
        if ((i == 0 || i == 7) || (j == 1 || j == 6)) {
            return true;
        }
        if ((i == 1 || i == 6) || (j == 0 || j == 7)) {
            return true;
        }
        return false;
    }

    boolean isXsquare(int i, int j) {
        if ((i == 1 || i == 6) && (j == 1 || j == 6)) {
            return true;
        }
        return false;
    }
    public int[][] getHintBoard(int role)
    {
        int [][] b = new int[8][8];
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                if(board[i][j]==-1)
                    b[i][j]=opponentCount(i,j,role);
                else
                    b[i][j]=0;
                
            }
        }
        return b;
    }

}
