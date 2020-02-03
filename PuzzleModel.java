import java.awt.*;
import java.util.*;

//the variable _contents contains the board face
//and updates after every move 

class PuzzleModel {
    private static final int ROWS = 3;
    private static final int COLS = 3;
    
    private static char[][] tempBoard = new char[3][3];

    private Tile[][] _contents;
    private Tile     _emptyTile;
    private Tile[][] _goalState = new Tile[3][3];
    
    public PuzzleModel(char[][] board) {
        this.tempBoard = board;
        _contents = new Tile[ROWS][COLS];
        reset();
    }
    
    String getFace(int row, int col) {
        return _contents[row][col].getFace();
    }

    //initializes the tiles of the games
    //bases the faces from the input file
    //puts it into _contents
    public void reset() {
        for (int r=0; r<ROWS; r++) {
            for (int c=0; c<COLS; c++) {
                _contents[r][c] = new Tile(r, c, Character.toString(tempBoard[r][c]));

                if(tempBoard[r][c] == '0'){
                    _emptyTile = _contents[r][c];
                    _emptyTile.setFace(null);
                }
            }
        }
    }
    
    public boolean moveTile(int r, int c) {
        return checkEmpty(r, c, -1, 0) || checkEmpty(r, c, 1, 0)
            || checkEmpty(r, c, 0, -1) || checkEmpty(r, c, 0, 1);
    }
    
    private boolean checkEmpty(int r, int c, int rdelta, int cdelta) {
        int rNeighbor = r + rdelta;
        int cNeighbor = c + cdelta;

        if (isLegalRowCol(rNeighbor, cNeighbor) 
                  && _contents[rNeighbor][cNeighbor] == _emptyTile) {
            exchangeTiles(r, c, rNeighbor, cNeighbor);
            return true;
        }
        return false;
    }

    public boolean isLegalRowCol(int r, int c) {
        return r>=0 && r<ROWS && c>=0 && c<COLS;
    }

    private void exchangeTiles(int r1, int c1, int r2, int c2) {
        Tile temp = _contents[r1][c1];
        _contents[r1][c1] = _contents[r2][c2];
        _contents[r2][c2] = temp;
    }

    //counts the inversions of tiles
    //if even then solvable otherwise no
    static int getInvCount(char[][] board){
        int inv_count = 0;
        char[] temp;
        ArrayList<Character> list = new ArrayList<Character>();
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board.length; j++){
                list.add(board[i][j]);
            }
        }

        temp = new char[list.size()];
        for(int i=0;i<temp.length;i++){
            temp[i] = list.get(i);
        }

        //this counts the inversions
        for(int i=0;i<temp.length-1;i++){
            for(int j=i+1;j<temp.length;j++){
                if(Integer.parseInt(String.valueOf(temp[i])) > Integer.parseInt(String.valueOf(temp[j])) && Integer.parseInt(String.valueOf(temp[i])) != 0 && Integer.parseInt(String.valueOf(temp[j])) != 0){
                    inv_count++;
                }
            }
        }

        return inv_count;
    }

    static boolean isSolvable(char[][] board) {
        int invCount = getInvCount(board);

        //returns true if conversion count is even
        return(invCount % 2 == 0);
    }

    //this functions checks the board's inversion after every click
    public boolean isGameOver() {
        char[][] temp = new char[3][3];

        //this puts updated puzzle face into an array to check inversions again
        for(int i=0;i<temp.length;i++){
            for(int j=0;j<temp.length;j++){
                String s = _contents[i][j].getFace();              
                if(s == null){
                    temp[i][j] = '0';
                }else{
                    temp[i][j] = s.charAt(0);
                }

            }
        }

        if(getInvCount(temp) == 0){
            return true;
        }else{
            return false;
        }
    }

}
    
class Tile {
    private int _row;
    private int _col;
    private String _face;

    public Tile(int row, int col, String face) {
        _row = row;
        _col = col;
        _face = face;
    }

    public void setFace(String newFace) {
        _face = newFace;
    }

    public String getFace() {
        return _face;
    }

    public boolean isInFinalPosition(int r, int c) {
        return r==_row && c==_col;
    }    
}