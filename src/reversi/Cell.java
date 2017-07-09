/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reversi;

/**
 *
 * @author Mahbub
 */
public class Cell {

    private int row;
    private int col;
    private boolean empty;

    public Cell() {
        empty = true;
    }

    public Cell(int r, int c) {
        row = r;
        col = c;
        empty = false;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void makeEmpty() {
        empty = true;
    }

    public void produce(int r, int c) {
        row = r;
        col = c;
        empty = false;
    }

    int getRow() {
        return row;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    int getColumn() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return col;
    }

}
