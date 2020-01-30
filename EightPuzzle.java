import javax.swing.JFrame;
import java.io.*;
import java.util.*;

class EightPuzzle {
    public static void main(String[] args) {
    	Scanner sc = new Scanner(System.in);
    	char[][] tempBoard = new char[3][3];
    	char space = ' ';
    	String line;
    	char[] chars = new char[3];

        //read the file and put the input in an array
    	try{
    		File file = new File("puzzle.in");
    		Scanner scanner = new Scanner(file);

    		for(int j=0; scanner.hasNextLine() && j<6; j++){
    			line = scanner.nextLine();
                String lineTrimmed = line.replaceAll("\\s", "");
                chars = lineTrimmed.toCharArray();

                for(int i=0;i<3 && i < chars.length; i++){
                    tempBoard[j][i] = chars[i];
                }
    		}
    	}catch(Exception e){
            System.out.println("File not found.");
        }

        JFrame window = new JFrame("8 Puzzle Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(new PuzzleGUI(tempBoard));
        window.pack();
        window.setVisible(true);
        window.setResizable(false);
    }
}