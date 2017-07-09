package reversi;

/**
 * An example class implementing Agent class for human players. The
 * implementation is coupled with the actual game (here, TickTackToe) the agent
 * is playing.
 *
 * @author Azad
 *
 */
public class HumanAgent extends Agent {

    //static Scanner in = new Scanner(System.in);
    public int timeLimit;

    private Cell sharedCell;
    private ReversiController controller;

    public HumanAgent(String name, int timeLimit,ReversiController con) {
        super(name);
        sharedCell = ReversiController.sharedCell;
        this.timeLimit = timeLimit;
        controller = con;
        // TODO Auto-generated constructor stub
    }

    @Override
    public int makeMove(Game game) {
        // TODO Auto-generated method stub
        
        int row, col;
        ReversiGame reversiGame = (ReversiGame) game;
        int [][] board = reversiGame.getHintBoard(role);
        
        controller.showHintRunLater(board);
        //boolean first = true;
        sharedCell.makeEmpty();

        while (sharedCell.isEmpty()) {
            synchronized (sharedCell) {
//                System.out.println("No mouse clickis " + Thread.currentThread().getName()
//                        + " is waiting");

                try {
                    sharedCell.wait();
                } catch (InterruptedException ex) {
                    // Logger.getLogger(class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        row = sharedCell.getRow();
        col = sharedCell.getColumn();
        sharedCell.makeEmpty();
        //int ret = reversiGame.countOpponent(row, col, role,true);
       // controller.clearHint();
       return reversiGame.makeMove(row, col, role);

        //return ret;
    }

}
