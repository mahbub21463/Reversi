package reversi;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Example MiniMax agent extending Agent class. Here, for simplicity of
 * understanding min and max functions are written separately. One single
 * function can do the task.
 *
 * @author Azad
 *
 */
public class MinimaxAgent extends Agent {

    public int timeLimit;
    ReversiController controller;
    public final int inf = 9999999;

    public MinimaxAgent(String name, int time, ReversiController con) {
        super(name);
        timeLimit = time;
        controller = con;
        // TODO Auto-generated constructor stub
    }

    @Override
    public int makeMove(Game game) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ReversiGame reversiGame = (ReversiGame) game;
       //reversiGame.showGameState();
        CellValueTuple best = max(reversiGame,-inf,inf, timeLimit);
       //reversiGame.showGameState();

        if (best.row == -1) {
            System.out.println("not expected!");
           
             return 0;
        } else {


            int ret = reversiGame.makeMove(best.row, best.col, role);
 
   
            return ret;
        }

    }

    private CellValueTuple max(ReversiGame game,int alpha,int beta, int d) {
        CellValueTuple maxCVT = new CellValueTuple();
        maxCVT.utility = -inf;

        int winner = game.checkForWin();

        //terminal check
        if (winner == role) {
            maxCVT.utility = inf-5; //this agent wins
            return maxCVT;
        } else if (winner != -1) {
            maxCVT.utility = -inf+5; //opponent wins
            return maxCVT;
        } else if (game.isBoardFull()) {
            maxCVT.utility = 0; //draw
            return maxCVT;
        }
        if (d == 0) {
            if(role ==0)
                maxCVT.utility = game.approximateUtility(role);
            else if(role ==1)
                maxCVT.utility = game.greedyUtility(role);
            //  System.out.println("depth limit mx "+maxCVT.utility);

            return maxCVT;
        }
        //boolean flag  = true;
        for (int i = 0; i < 8; i++) {
            int j,p,k;
            for ( j = 0,p=7; j < 8; j++,p--) {
               // System.out.println(""+i +" max "+j);
                
                if(role==0)
                {
                    k=j;
                }
                else
                {
                    k=p;
                }
                if (!game.isValidCell(i, k, role)) {
                    continue;
                }
                 
                //game.board[i][j] = role; //temporarily making a move
                ArrayList<CellValueTuple> tempMoves = game.makeTemporaryMove(i, k, role);


                int v = min(game,alpha,beta, d - 1).utility;
                // System.out.println(v +" max");
                game.removeTemporaryMoves(tempMoves);
                 if(v==inf)
                 {
                     v=500;
                 }
                if (  v > maxCVT.utility) {
                    maxCVT.utility = v;
                    //  System.out.println("updated max "+v);
                    maxCVT.row = i;
                    maxCVT.col = k;
                   
                }
                if (maxCVT.utility >= beta) {

                    return maxCVT;
                }
                if (maxCVT.utility > alpha) {
                    alpha = maxCVT.utility;
                }
                //game.board[i][j] = -1; // reverting back to original state

            }
        }
        
        return maxCVT;

    }

    private CellValueTuple min(ReversiGame game,int alpha,int beta, int d) {
        CellValueTuple minCVT = new CellValueTuple();
        minCVT.utility = inf;

        int winner = game.checkForWin();

        //terminal check
        if (winner == role) {
            minCVT.utility = inf-5; //max wins
             
            
            return minCVT;
        } else if (winner != -1) {
            minCVT.utility = -inf+5; //min wins
           
            return minCVT;
        } else if (game.isBoardFull()) {
            minCVT.utility = 0; //draw
            
            return minCVT;
        }

        if (d == 0) {
            if(role ==0)
                minCVT.utility = game.approximateUtility(role);
            else if(role ==1)
                minCVT.utility = game.greedyUtility(role);
            //minCVT.utility =100;
          // System.out.println("depth limit min " +minCVT.utility);
//            if(minCVT.utility == -9999900 )
//            {
//                try {
//                    throw new Exception();
//                } catch (Exception ex) {
//                    Logger.getLogger(MinimaxAgent.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
            return minCVT;
        }
        for (int i = 0; i < 8; i++) {
            int j,p,k;
            for ( j = 0,p=7; j < 8;p--, j++) {
                // System.out.println(""+i +" min "+j);
                
                if(role==0)
                {
                    k=j;
                }
                else
                {
                    k=p;
                }
                if (!game.isValidCell(i, k, minRole())) {
                    continue;
                }


                
                ArrayList<CellValueTuple> tempMoves = game.makeTemporaryMove(i, k, minRole());

                int v = max(game, alpha,beta,d - 1).utility;
                game.removeTemporaryMoves(tempMoves);
                //  System.out.println(v+" min");
                if( v == -inf )
                 {
                     v=-500;
                 }
                if ( v < minCVT.utility) {
                //    System.out.println(minCVT.utility+ " current min");
                    minCVT.utility = v;
                    
                    minCVT.row = i;
                    minCVT.col = k;
                    
//                    if (minCVT.utility == -9999900) {
//                  //      System.out.println(v+" from max");
//                        try {
//                            throw new Exception();
//                        } catch (Exception ex) {
//                            Logger.getLogger(MinimaxAgent.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }
                   
                }
                if (minCVT.utility <= alpha) {
                    // game.removeTemporaryMoves(tempMoves);
                    return minCVT;
                }
                if (minCVT.utility < beta) {
                    beta = minCVT.utility;
                }

            }

        }
//         if (minCVT.utility == -9999900) {
//                        try {
//                            throw new Exception();
//                        } catch (Exception ex) {
//                            Logger.getLogger(MinimaxAgent.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }
        return minCVT;

    }
    
    private int minRole() {
        return (role + 1) % 2;
    }


}

class CellValueTuple {

    public int row, col, utility;

    public CellValueTuple() {
        // TODO Auto-generated constructor stub
        row = -1;
        col = -1;
    }

    public CellValueTuple(int r, int c, int role) {
        // TODO Auto-generated constructor stub
        row = r;
        col = c;
        utility = role;
    }
}
