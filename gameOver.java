
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class gameOver extends JFrame {
    int player1;
    int player2;
    String playerPoint1;
    String playerPoint2,won;
    
    Image gameOver;
    
    gameOver(int player1,int player2){
        
        setTitle("Simple Maze Solver client");
        setSize(1600, 1200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        this.player1 = player1;
        this.player2 = player2;
        
         playerPoint1 = "Opponent Point "+player1;
         
         playerPoint2 = "Your Point "+player2;
         
         
         if(player1>player2){
             won = "OOppps You Lost!";
         }
         
         else if(player1<player2){
             won = "congratulation You won";
         }
         
         else{
             won = "The game is drawn";
         }
       gameOver = new ImageIcon(getClass().getResource("gameOver.jpg")).getImage();
         
    }
    
    @Override
    public void paint(Graphics g){
        
        super.paint(g);       
        
        g.setFont(new Font("comic Sans MS", Font.PLAIN, 30));
        //g.drawImage(back, 0, 0, 1300, 800, this);
        
        
        g.drawImage(gameOver, 0, 0, 1600, 1200, this);
        
        g.drawString(playerPoint1, 600, 500);
        g.drawString(playerPoint2, 600, 600);

        g.drawString(won, 800, 700);

    }
    
    
}
