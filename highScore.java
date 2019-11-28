
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class highScore extends JFrame{
    
    int x;
    Image highScore;
    
    highScore() throws FileNotFoundException{
        FileReader fin = new FileReader("highScore.txt");
        
        Scanner test = new Scanner(fin);
        
        highScore = new ImageIcon(getClass().getResource("highScore.jpg")).getImage();
       
        
        x = test.nextInt();
    }
    
    public void paint(Graphics g){
        super.paint(g);       
        
        g.setFont(new Font("comic Sans MS", Font.PLAIN, 30));
        //g.drawImage(back, 0, 0, 1300, 800, this);
       
        
        g.drawImage(highScore, 0, 0, 1600, 1200, this);
        
        //g.drawString("High Score "+x, 1000, 100);
        //g.drawString(playerPoint2, 1000, 500);

        //g.drawString(won, 800, 700);

    }
}
