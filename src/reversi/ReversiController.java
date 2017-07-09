/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reversi;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;

import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

/**
 *
 * @author Mahbub
 */
public class ReversiController implements Initializable {

    @FXML
    private RadioButton humanVsComputerRB;
    @FXML
    private RadioButton computerVsComputerRB;
    @FXML
    private RadioButton humanVsHumanRB;

    @FXML
    Pane grid;

    @FXML
    Label winLabel;

    @FXML
    Label infoLabel;

    @FXML
    Label agent1Label;

    @FXML
    Label agent2Label;

    @FXML
    Label agent1ScoreLabel;

    @FXML
    Label currentMoveLabel;

    @FXML
    Label agent2ScoreLabel;
    @FXML
    TextField player1TextField;
    @FXML
    TextField player2TextField;
    @FXML
    TextField timeLimitTextField;
    
    @FXML
    Button startGameButton;

    @FXML
    Button finishGameButton;
    @FXML
    CheckBox showHintCheckbox;
    private final double radius = 20;
    private final double spacing = 5;

    private static final PseudoClass SELECTED_P_C = PseudoClass.getPseudoClass("selected");
    private final ObjectProperty<Circle> selectedCircle = new SimpleObjectProperty<>();

    private final Circle[][] pieces = new Circle[8][8];
    private ToggleGroup group;
    private int gameMode;

    public static ReversiGame game;
    private Agent agent1;
    private Agent agent2;

    public static Cell sharedCell;
    private boolean showHint;
   // private Text[][] text;
    private int[][] hintBoard;
    @FXML
    private void finishGameButtonAction(ActionEvent event) {
        if (game != null && !game.isFinished()) {
            game.finishGame();
            game = null;

        }
        initGUI();
        event.consume();
    }

    @FXML
    private void startGameButtonAction(ActionEvent event) {
      //  System.out.println("start game");
        //updateInfoLabel("Starting game...");

        winLabel.setText("");
        String player1 = "Player 1";
        String player2 = "Player 2";
        int timeLimit1 = -1; //not set
        int timeLimit2 = -1;
        if (!timeLimitTextField.getText().isEmpty()) {
            String tim = timeLimitTextField.getText();
            try {
                int x = Integer.parseInt(tim);
                if (x > 0) {
                    timeLimit1 = x;
                    timeLimit2 = x;
                }
            } catch (NumberFormatException e) {

            }
        }

//        showHint = false;
//        if (showHintCheckbox.isSelected()) {
//            showHint = true;
//        }
        if (!player1TextField.getText().isEmpty()) {
            player1 = player1TextField.getText();
        }
        if (!player2TextField.getText().isEmpty()) {
            player2 = player2TextField.getText();
        }
        sharedCell = new Cell();

        switch (gameMode) {
            case 0:
                agent1 = new HumanAgent(player1, timeLimit1,this);
                agent2 = new MinimaxAgent(player2, timeLimit2, this);
                break;
            case 1:
                agent1 = new MinimaxAgent(player1, timeLimit2, this);

                agent2 = new MinimaxAgent(player2, timeLimit2, this);
                //System.out.println("sfldkaj");
                break;
            default:
                agent1 = new HumanAgent(player1,  timeLimit1,this);
                //Agent human = new MinimaxTTTAgent("007");
                agent2 = new HumanAgent(player2,  timeLimit1,this);
                break;
        }
        if (game != null && !game.isFinished()) {
            game.finishGame();
        }
        game = new ReversiGame(agent2, agent1, this);
//        updateBoard();
//        updateScoreBoard();
        finishGameButton.setDisable(false);

        startGameButton.setDisable(true);
        game.play();

        event.consume();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        group = new ToggleGroup();
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
                if (group.getSelectedToggle() != null) {
                    RadioButton rb = (RadioButton) group.getSelectedToggle();
                    String gamemode = rb.getText();
                    if (gamemode.equals("Human vs Computer")) {
                        gameMode = 0;

                    } else if (gamemode.equals("Computer vs Computer")) {
                        gameMode = 1;
                    } else {
                        gameMode = 2;
                    }
                    System.out.println(gamemode);

                }
            }
        });
        
        
        humanVsComputerRB.setToggleGroup(group);
        humanVsComputerRB.setSelected(true);
        computerVsComputerRB.setToggleGroup(group);
        humanVsHumanRB.setToggleGroup(group);

         showHintCheckbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
        public void changed(ObservableValue<? extends Boolean> ov,
            Boolean old_val, Boolean new_val) {
                if(showHintCheckbox.isSelected())
                {
                    showHint = true;
                }
                else
                {
                    showHint = false;
                }
        }
    });
        selectedCircle.addListener((obs, oldSelection, newSelection) -> {
            if (oldSelection != null) {
                oldSelection.pseudoClassStateChanged(SELECTED_P_C, false);
            }
            if (newSelection != null) {
                newSelection.pseudoClassStateChanged(SELECTED_P_C, true);
            }
        });
        initGUI();
       // text = new Text[8][8];
//        for(int i=0;i<8;i++)
//        {
//            for(int j=0;j<8;j++)
//            {
//                
////                    text[i][j] = new Text("");
////                    text[i][j].setBoundsType(TextBoundsType.VISUAL);
////                    StackPane stack = new StackPane();
////                    stack.getChildren().addAll(pieces[i][j],text[i][j]);
//                if(pieces[i][j]==null)
//                {
//                    System.out.println("why");
//                }
//                    text[i][j] = new Text(pieces[i][j].getCenterX(),pieces[i][j].getCenterY(),"");
//                
//           }
//        }

    }

    private Circle createCircle(int x, int y) {
        Circle circle = new Circle();

        circle.setCenterY(x * (spacing + radius + radius) + spacing + radius);
        circle.setCenterX(y * (spacing + radius + radius) + spacing + radius);
        circle.setRadius(radius);

        circle.setStroke(Color.DARKCYAN);
        circle.setFill(Color.web("#9ba9b5"));
        circle.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e -> {
            selectedCircle.set(circle);

            if (game != null && isHumanTurn(game.getTurn()) && game.isValidCell(x, y, game.getTurn())) {
                synchronized (sharedCell) {
                    sharedCell.produce(x, y);
                    sharedCell.notifyAll();
                }
               // clearHint();
               //System.out.println("make move " + (x) + " " + (y) + " turn " + game.getTurn());
            } 
            e.consume();

        });
        circle.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_ENTERED, e -> {
            selectedCircle.set(circle);
            //selectedLocation.set(new Point2D(x, y));
            if (game != null && isHumanTurn(game.getTurn()) && game.isValidCell(x, y, game.getTurn())) {
                makeTemporaryMove(x, y, game.getTurn());
            }
            
            // System.out.println("show hint");
            e.consume();
        });
        circle.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_EXITED, e -> {
            selectedCircle.set(circle);
            //selectedLocation.set(new Point2D(x, y));
            //System.out.println("hide hint");
            if (game != null && isHumanTurn(game.getTurn())) {
                 for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8 && game!=null; j++) {
                        //setPiece(i,j);
                        int c = game.getCellColor(i, j);

                        // Color color;
                        if (c == 0)//
                        {
                            //color = Color.RED;
                            pieces[i][j].setFill(Color.RED);
                        } else if (c == 1) {
                            pieces[i][j].setFill(Color.BLUE);
                        } else {
                            //color = Color.web("");
                            pieces[i][j].setFill(Color.web("#9ba9b5"));
                        }

                    }
                }
                 if(showHint)
                    showHint(hintBoard);
            }
            e.consume();
        });

        return circle;
    }

    public boolean isHumanTurn(int turn) {
        if ((turn == 1 && gameMode != 1) || gameMode == 2) {
            return true;
        }
        
        return false;

    }

    public void initGUI() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (int x = 0; x < 8; x++) {
                    for (int y = 0; y < 8; y++) {

                        pieces[x][y] = createCircle(x, y);
                        grid.getChildren().add(pieces[x][y]);
                    }
                }
                pieces[3][3].setFill(Color.BLUE);
                pieces[3][4].setFill(Color.RED);
                pieces[4][3].setFill(Color.RED);
                pieces[4][4].setFill(Color.BLUE);
                // player1TextField.setText("Player 1");
                // player2TextField.setText("Player 2");
                //agent1Label.setText("Player 1");
                //agent2Label.setText("Player 2");
                agent1ScoreLabel.setText("2");
                agent2ScoreLabel.setText("2");
                //timeLimitTextField.setText("60");
                //timerLabel.setText("Timer");

                startGameButton.setDisable(false);

                currentMoveLabel.setText("");
                infoLabel.setText("");
                currentMoveLabel.setText("");
                finishGameButton.setDisable(true);
                winLabel.setText("");
                showHintCheckbox.setSelected(false);
            }
        });

    }
    public void showHintRunLater(int [][] board)
    {
        hintBoard = board;
        if (!showHint) {
            //clearHint();
            return;
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (board[i][j] > 0) {
                            pieces[i][j].setFill(Color.BLACK);
                            // text[i][j] = new Text(board[i][j]+"");

                            // text[i][j] = new Text(pieces[i][j].getCenterX(),pieces[i][j].getCenterY(),board[i][j]+"jj");
//                    text.setBoundsType(TextBoundsType.VISUAL);
//                    StackPane stack = new StackPane();
//                    stack.getChildren().addAll(pieces[i][j],text);
                        }
                    }
                }
            }
        });

        
    }
    public void showHint(int [][] board)
    {
        
        if(!showHint)
        {
            //clearHint();
            return;
        }
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                if (board[i][j] > 0) {
                     pieces[i][j].setFill(Color.BLACK);
                   // text[i][j] = new Text(board[i][j]+"");
                   
                  // text[i][j] = new Text(pieces[i][j].getCenterX(),pieces[i][j].getCenterY(),board[i][j]+"jj");
                   
//                    text.setBoundsType(TextBoundsType.VISUAL);
//                    StackPane stack = new StackPane();
//                    stack.getChildren().addAll(pieces[i][j],text);
                }
            }
        }
    }
  
    public void updateWinLabel(String str) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                
                winLabel.setText(str);
            }
            });
    }

    public void updateBoard() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //if you change the UI, do it here !
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8 && game!=null; j++) {
                        //setPiece(i,j);
                        int c = game.getCellColor(i, j);

                        // Color color;
                        if (c == 0)//
                        {
                            //color = Color.RED;
                            pieces[i][j].setFill(Color.RED);
                        } else if (c == 1) {
                            pieces[i][j].setFill(Color.BLUE);
                        } else {
                            //color = Color.web("");
                            pieces[i][j].setFill(Color.web("#9ba9b5"));
                        }

                    }
                }
            }
        });

    }

    public void updateCurrentTurn(int turn) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //if you change the UI, do it here !
                if (turn == 0) {
                    currentMoveLabel.setText(agent2.getName() + "'s turn.");
                    currentMoveLabel.setTextFill(Color.RED);
                } else {
                    currentMoveLabel.setText(agent1.getName() + "'s turn.");
                    currentMoveLabel.setTextFill(Color.BLUE);
                }
            }
        });

    }

    public void updateInfoLabel(String str) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //if you change the UI, do it here !
                infoLabel.setText(str);

            }
        });

    }

    public void updateScoreBoard() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //if you change the UI, do it here !
                agent1Label.setText(agent1.getName());
                agent2Label.setText(agent2.getName());
                agent1ScoreLabel.setText(Integer.toString(agent1.getScore()));
                agent2ScoreLabel.setText(Integer.toString(agent2.getScore()));
            }
        });

    }

    public void makeTemporaryMove(int row, int col, int role) {
        //board[row][col] = role;

        pieces[row][col].setFill(role == 1 ? Color.BLUE : Color.RED);

        for (int j = col + 1; j < 8; j++)//right direction
        {
            if (game.getCellColor(row, j) == role || game.getCellColor(row, j) == -1) {
                if (game.getCellColor(row, j) == role) // flip opponent
                {
                    for (int k = col + 1; k < j; k++) {
                        //board[row][k] = role;
                        pieces[row][k].setFill(role == 1 ? Color.BLUE : Color.RED);
                    }
                }
                break;
            }

        }

        for (int j = col - 1; j >= 0; j--)//left direction
        {
            if (game.getCellColor(row, j) == role || game.getCellColor(row, j) == -1) {
                if (game.getCellColor(row, j) == role) // flip opponent
                {
                    for (int k = col - 1; k > j; k--) {
                        pieces[row][k].setFill(role == 1 ? Color.BLUE : Color.RED);
                    }
                }
                break;
            }

        }

        for (int i = row - 1; i >= 0; i--)//up direction
        {
            if (game.getCellColor(i, col) == role || game.getCellColor(i, col) == -1) {
                if (game.getCellColor(i, col) == role) {
                    for (int k = row - 1; k > i; k--) {
                        //  board[k][col] = role;
                        pieces[k][col].setFill(role == 1 ? Color.BLUE : Color.RED);
                    }
                }
                break;
            }

        }

        for (int i = row + 1; i < 8; i++)//down direction
        {
            if (game.getCellColor(i, col) == role || game.getCellColor(i, col) == -1) {
                if (game.getCellColor(i, col) == role) {
                    for (int k = row + 1; k < i; k++) {
                        pieces[k][col].setFill(role == 1 ? Color.BLUE : Color.RED);
                    }
                }
                break;
            }

        }
        int i, j;
        for (i = row + 1, j = col + 1; i < 8 && j < 8; i++, j++)// right down direction
        {
            if (game.getCellColor(i, j) == role || game.getCellColor(i, j) == -1) {
                if (game.getCellColor(i, j) == role) {
                    int k, p;
                    for (k = row + 1, p = col + 1; k < i && p < j; k++, p++) {
                        pieces[k][p].setFill(role == 1 ? Color.BLUE : Color.RED);
                    }
                }
                break;
            }

        }

        for (i = row - 1, j = col + 1; i >= 0 && j < 8; i--, j++)// right up direction
        {
            if (game.getCellColor(i, j) == role || game.getCellColor(i, j) == -1) {
                if (game.getCellColor(i, j) == role) {
                    int k, p;
                    for (k = row - 1, p = col + 1; k > i && p < j; k--, p++) {
                        pieces[k][p].setFill(role == 1 ? Color.BLUE : Color.RED);
                    }
                }
                break;
            }

        }

        for (i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--)// left up direction
        {
            if (game.getCellColor(i, j) == role || game.getCellColor(i, j) == -1) {
                if (game.getCellColor(i, j) == role) {
                    int k, p;
                    for (k = row - 1, p = col - 1; k > i && p > j; k--, p--) {
                        pieces[k][p].setFill(role == 1 ? Color.BLUE : Color.RED);
                    }
                }
                break;
            }

        }

        for (i = row + 1, j = col - 1; i < 8 && j >= 0; i++, j--)// left down direction
        {
            if (game.getCellColor(i, j) == role || game.getCellColor(i, j) == -1) {
                if (game.getCellColor(i, j) == role) {
                    int k, p;
                    for (k = row + 1, p = col - 1; k < i && p > j; k++, p--) {
                        pieces[k][p].setFill(role == 1 ? Color.BLUE : Color.RED);
                    }
                }
                break;
            }

        }

    }

}
