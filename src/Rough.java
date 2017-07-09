//package reversi;
//
//import java.io.IOException;
//import static java.lang.Thread.sleep;
//import java.util.ArrayList;
//import java.util.Random;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// * Example MiniMax agent extending Agent class. Here, for simplicity of
// * understanding min and max functions are written separately. One single
// * function can do the task.
// *
// * @author Azad
// *
// */
//public class MinimaxAgent extends Agent {
//
//    public int timeLimit;
//    ReversiController controller;
//    public final int inf = 999999999;
//
//    public MinimaxAgent(String name, int time, ReversiController con) {
//        super(name);
//        timeLimit = time;
//        controller = con;
//        // TODO Auto-generated constructor stub
//    }
//
//    @Override
//    public int makeMove(Game game) {
//
////        try {
////            Thread.sleep(500);
////        } catch (InterruptedException e) {
////            // TODO Auto-generated catch block
////            e.printStackTrace();
////        }
//        ReversiGame reversiGame = (ReversiGame) game;
//       // reversiGame.showGameState();
//        CellValueTuple best = max(reversiGame, 0);
//       // reversiGame.showGameState();
//
//        if (best.row == -1) {
//            System.out.println("What happened!");
//            // return best.utility;
//             return 0;
//        } else {
//
//            //System.out.println(best.utility +" "+role);
//            //int ret = reversiGame.countOpponent(best.row, best.col, role,true);
////                    try {
////                        System.in.read();
////                    } catch (IOException ex) {
////                        Logger.getLogger(MinimaxAgent.class.getName()).log(Level.SEVERE, null, ex);
////                    }
////            controller.updateInfoLabel("making real move");
////            try {
////                Thread.sleep(2000);
////            } catch (InterruptedException e) {
////                // TODO Auto-generated catch block
////                e.printStackTrace();
////            }
//            int ret = reversiGame.makeMove(best.row, best.col, role);
//           // System.out.println(best.row+" "+best.col+" "+role+" "+ret);
////            try {
////                Thread.sleep(2000);
////            } catch (InterruptedException e) {
////                // TODO Auto-generated catch block
////                e.printStackTrace();
////            }
////System.out.println(ret);
//            return ret;
//        }
//
//    }
//
//    private CellValueTuple max(ReversiGame game, int d) {
//        CellValueTuple maxCVT = new CellValueTuple();
//        maxCVT.utility = -100;
//
//        int winner = game.checkForWin();
//
//        //terminal check
//        if (winner == role) {
//            maxCVT.utility = 1; //this agent wins
//            return maxCVT;
//        } else if (winner != -1) {
//            maxCVT.utility = -1; //opponent wins
//            return maxCVT;
//        } else if (game.isBoardFull()) {
//            maxCVT.utility = 0; //draw
//            return maxCVT;
//        }
//        if (d == 4) {
//            maxCVT.utility = game.approximateUtility(role);
//            //  System.out.println("depth limit mx "+maxCVT.utility);
//
//            return maxCVT;
//        }
//        //boolean flag  = true;
//        for (int i = 0; i < 8; i++) {
//            int j,p,k;
//            for ( j = 0,p=7; j < 8; j++,p--) {
//                // System.out.println(""+i +" max "+j);
//                
//                if(role==0)
//                {
//                    k=j;
//                }
//                else
//                {
//                    k=p;
//                }
//                if (!game.isValidCell(i, k, role)) {
//                    continue;
//                }
//                 
//                //game.board[i][j] = role; //temporarily making a move
//                ArrayList<CellValueTuple> tempMoves = game.makeTemporaryMove(i, k, role);
//
////                            controller.updateBoard();
////                            try {
////                                
////                                Thread.sleep(1000);
////                                
////                            } catch (InterruptedException e) {
////                                // TODO Auto-generated catch block
////                                e.printStackTrace();
////                            }
//                //  System.out.println(tempMoves.size()+" tmp move size mxx");
////                                for(int p=0;p<8;p++)
////                                 {
////                                     for(int q=0;q<8;q++)
////                                     {
////                                         System.out.print(game.board[p][q]+" ");
////                                     }
////                                     System.out.println("");
////                                     
////                                 }
//                int v = min(game, d + 1).utility;
//                // System.out.println(v +" max");
//                if ( v > maxCVT.utility) {
//                    maxCVT.utility = v;
//                    //  System.out.println("updated max "+v);
//                    maxCVT.row = i;
//                    maxCVT.col = k;
//                } 
//                //game.board[i][j] = -1; // reverting back to original state
//                game.removeTemporaryMoves(tempMoves);
////                                 controller.updateBoard();
////                                 
////                            try {
////                                
////                                Thread.sleep(1000);
////                                
////                            } catch (InterruptedException e) {
////                                // TODO Auto-generated catch block
////                                e.printStackTrace();
////                            }
//                           
//
//            }
//        }
//
//        return maxCVT;
//
//    }
//
//    private CellValueTuple min(ReversiGame game, int d) {
//        CellValueTuple minCVT = new CellValueTuple();
//        minCVT.utility = 100;
//
//        int winner = game.checkForWin();
//
//        //terminal check
//        if (winner == role) {
//            minCVT.utility = 1; //max wins
//            return minCVT;
//        } else if (winner != -1) {
//            minCVT.utility = -1; //min wins
//            return minCVT;
//        } else if (game.isBoardFull()) {
//            minCVT.utility = 0; //draw
//            return minCVT;
//        }
//
//        if (d == 4) {
//            minCVT.utility = game.approximateUtility(role);
//            //minCVT.utility =100;
//            // System.out.println("depth limit min " +minCVT.utility);
//            return minCVT;
//        }
//        for (int i = 0; i < 8; i++) {
//            int j,p;
//            for ( j = 0,p=7; j < 8;p--, j++) {
//                // System.out.println(""+i +" min "+j);
//                int k ;
//                if(role==0)
//                {
//                    k=j;
//                }
//                else
//                {
//                    k=p;
//                }
//                if (!game.isValidCell(i, k, minRole())) {
//                    continue;
//                }
//
//                //game.board[i][j] = minRole();
//                //game.board[i][j] = (role+1)%2;
//                
//                ArrayList<CellValueTuple> tempMoves = game.makeTemporaryMove(i, k, minRole());
////                                 System.out.println(tempMoves.size()+" tmp move size");
////                                 for(int p=0;p<8;p++)
////                                 {
////                                     for(int q=0;q<8;q++)
////                                     {
////                                         System.out.print(game.board[p][q]+" ");
////                                     }
////                                     System.out.println("");
////                                     
////                                 }
////                        controller.updateBoard();
////                            try {
////                                
////                                Thread.sleep(1000);
////                                
////                            } catch (InterruptedException e) {
////                                // TODO Auto-generated catch block
////                                e.printStackTrace();
////                            }
//                int v = max(game, d + 1).utility;
//                //  System.out.println(v+" min");
//
//                if (v < minCVT.utility) {
//                    minCVT.utility = v;
//                    // System.out.println("updated min "+v);
//                    minCVT.row = i;
//                    minCVT.col = k;
//                } 
//                //game.board[i][j] = -1;
//                game.removeTemporaryMoves(tempMoves);
////                                controller.updateBoard();
////                            try {
////                                
////                                Thread.sleep(1000);
////                                
////                            } catch (InterruptedException e) {
////                                // TODO Auto-generated catch block
////                                e.printStackTrace();
////                            }
////
//            }
//
//        }
//        return minCVT;
//
//    }
//
//    private int minRole() {
//        return (role + 1) % 2;
//    }
////        private int [][] makeTempMove(int[][] board,int row,int col,int role)
////        {
////            board[row][col] = role;
////        
////        for (int j = col + 1; j < 8; j++)//right direction
////        {
////            if (board[row][j] == role || board[row][j] == -1) {
////                if (board[row][j] == role) // flip opponent
////                {
////                    for (int k = col + 1; k < j; k++) {
////                        board[row][k] = role;
////                    }
////                }
////                break;
////            }
////
////        }
////
////        for (int j = col - 1; j >= 0; j--)//left direction
////        {
////            if (board[row][j] == role || board[row][j] == -1) {
////                if (board[row][j] == role) // flip opponent
////                {
////                    for (int k = col - 1; k > j; k--) {
////                        board[row][k] = role;
////                    }
////                }
////                break;
////            }
////
////        }
////
////        for (int i = row - 1; i >= 0; i--)//up direction
////        {
////            if (board[i][col] == role || board[i][col] == -1) {
////                if (board[i][col] == role) {
////                    for (int k = row - 1; k > i; k--) {
////                        board[k][col] = role;
////                    }
////                }
////                break;
////            }
////
////        }
////
////        for (int i = row + 1; i < 8; i++)//down direction
////        {
////            if (board[i][col] == role || board[i][col] == -1) {
////                if (board[i][col] == role) {
////                    for (int k = row + 1; k < i; k++) {
////                        board[k][col] = role;
////                    }
////                }
////                break;
////            }
////
////        }
////
////        int i, j;
////        for (i = row + 1, j = col + 1; i < 8 && j < 8; i++, j++)// right down direction
////        {
////            if (board[i][j] == role || board[i][j] == -1) {
////                if (board[i][j] == role) {
////                    int k, p;
////                    for (k = row + 1, p = col + 1; k < i && p < j; k++, p++) {
////                        board[k][p] = role;
////                    }
////                }
////                break;
////            }
////
////        }
////
////        for (i = row - 1, j = col + 1; i >= 0 && j < 8; i--, j++)// right up direction
////        {
////            if (board[i][j] == role || board[i][j] == -1) {
////                if (board[i][j] == role) {
////                    int k, p;
////                    for (k = row - 1, p = col + 1; k > i && p < j; k--, p++) {
////                        board[k][p] = role;
////                    }
////                }
////                break;
////            }
////
////        }
////
////        for (i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--)// left up direction
////        {
////            if (board[i][j] == role || board[i][j] == -1) {
////                if (board[i][j] == role) {
////                    int k, p;
////                    for (k = row - 1, p = col - 1; k > i && p > j; k--, p--) {
////                        board[k][p] = role;
////                    }
////                }
////                break;
////            }
////
////        }
////
////        for (i = row + 1, j = col - 1; i < 8 && j >= 0; i++, j--)// left down direction
////        {
////            if (board[i][j] == role || board[i][j] == -1) {
////                if (board[i][j] == role) {
////                    int k, p;
////                    for (k = row + 1, p = col - 1; k < i && p > j; k++, p--) {
////                        board[k][p] = role;
////                    }
////                }
////                break;
////            }
////
////        }
////            
////            return board;
////        }
//
//}
//
//class CellValueTuple {
//
//    public int row, col, utility;
//
//    public CellValueTuple() {
//        // TODO Auto-generated constructor stub
//        row = -1;
//        col = -1;
//    }
//
//    public CellValueTuple(int r, int c, int role) {
//        // TODO Auto-generated constructor stub
//        row = r;
//        col = c;
//        utility = role;
//    }
//}
