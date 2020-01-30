import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

class PuzzleGUI extends JPanel {
    private GraphicsPanel    _puzzleGraphics;
    public static char[][] tempBoard = new char[3][3];

    public PuzzleGUI(char[][] board) {
        this.tempBoard = board;
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        _puzzleGraphics = new GraphicsPanel(board);

        this.setLayout(new BorderLayout());
        this.add(controlPanel, BorderLayout.NORTH);
        this.add(_puzzleGraphics, BorderLayout.CENTER);
    }

    //this class is in this class so as to use the instance variables in the outer class
    class GraphicsPanel extends JPanel implements MouseListener {
        private PuzzleModel _puzzleModel;

        private static final int ROWS = 3;
        private static final int COLS = 3;
        
        private static final int CELL_SIZE = 120;
        private Font _biggerFont;

        public GraphicsPanel(char[][] board) {
            _biggerFont = new Font("SansSerif", Font.BOLD, CELL_SIZE/2);
            this.setPreferredSize(
                   new Dimension(CELL_SIZE * COLS, CELL_SIZE*ROWS));
            this.setBackground(Color.white);
            this.addMouseListener(this);

            checkSolvability(board);

            _puzzleModel = new PuzzleModel(board);
        }
        
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int r=0; r<ROWS; r++) {
                for (int c=0; c<COLS; c++) {
                    int x = c * CELL_SIZE;
                    int y = r * CELL_SIZE;

                    String text = _puzzleModel.getFace(r, c);
                    if (text != null) {
                        g.setColor(Color.pink);
                        g.fillRect(x+2, y+2, CELL_SIZE-4, CELL_SIZE-4);
                        g.setColor(Color.white);
                        g.setFont(_biggerFont);
                        g.drawString(text, x+20, y+(3*CELL_SIZE)/4);
                    }
                }
            }
        }
        
        public void mousePressed(MouseEvent e) {
            int col = e.getX()/CELL_SIZE;
            int row = e.getY()/CELL_SIZE;
            
            if (!_puzzleModel.moveTile(row, col)) {
                Toolkit.getDefaultToolkit().beep();
            }

            this.repaint();
        }

        public void checkSolvability(char[][] board){
            if(_puzzleModel.isSolvable(board)){
                System.out.println("Solvable");
            }else{
                System.out.println("Not Solvable");
                System.exit(0);
            }
        }

        public void checkOver(){
            if(_puzzleModel.isGameOver()) {
                System.exit(0);
            }
        }

        //this is placed so that there is no override what is that error thingy haha
        public void mouseClicked (MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        public void mouseEntered (MouseEvent e) {}
        public void mouseExited  (MouseEvent e) {}
    }
    

}