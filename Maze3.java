/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package client;

//import static Maze2.s;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Maze3 extends JFrame {
    
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;
    
private int  maze[][] = 
       { {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,6,1,1,1,1,1},
          {1,0,1,2,0,0,1,0,1,1,1,0,0,0,0,1,0,0,0,1,0,1,1,1,0,1},
          {1,0,1,0,1,1,1,0,1,0,1,0,0,1,0,1,0,1,1,1,0,1,0,1,0,1},
          {1,0,1,0,0,0,0,0,1,1,1,2,1,1,0,1,0,0,0,0,0,1,0,1,0,1},
          {1,0,1,2,1,1,1,0,1,0,0,0,1,1,0,1,0,1,1,1,0,1,0,0,0,1},
          {1,0,1,0,1,0,0,0,1,1,1,2,1,0,0,1,0,1,0,0,0,1,0,1,0,1},
          {1,0,1,0,1,0,1,0,0,0,0,2,0,0,0,1,0,1,0,0,0,0,0,0,0,1},
          {1,0,1,2,0,0,1,0,1,1,1,0,1,1,0,1,0,0,0,1,0,1,0,0,0,1},
          {1,0,0,0,1,1,1,0,0,0,0,0,1,1,0,0,0,1,1,1,0,0,0,0,0,1},
          {1,0,1,0,0,0,0,0,1,1,1,2,1,1,0,1,0,0,0,0,0,1,1,1,0,1},
          {1,0,1,0,1,1,1,0,1,0,0,0,1,1,0,1,0,1,1,1,0,1,0,0,0,1},
          {1,0,0,0,1,1,1,0,0,0,0,0,1,0,0,0,0,1,1,1,0,0,0,0,0,1},
          {1,0,1,0,1,0,0,0,1,1,1,0,0,0,0,1,0,1,0,0,0,1,1,1,0,1},
          {5,0,1,0,1,0,1,0,0,0,0,0,1,1,0,1,0,1,0,1,0,0,0,0,0,1},
          {1,0,1,0,1,1,1,0,1,0,1,0,1,1,0,1,0,1,1,1,0,1,0,1,0,1},
          {1,0,0,0,0,0,0,0,0,0,1,1,0,1,0,0,0,2,0,0,0,0,0,1,1,1},
          {1,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,1,1,1},
          {1,0,1,0,1,0,1,0,0,0,0,0,1,1,0,1,0,1,0,1,0,0,0,0,0,1},
          {1,0,1,0,1,0,1,0,0,0,0,0,1,1,0,1,0,1,2,1,0,2,0,0,0,1},
          {1,0,1,0,0,0,1,0,1,1,1,0,0,0,0,1,0,0,0,1,0,1,1,1,0,9},
          {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        };
    
    public static int pos1X,pos1Y,pos2X,pos2Y,sz, bombFlag,bombMove,bombx,bomby, player1,player2;
    public int diamondCount;
    private ImageIcon image1;
    private ImageIcon image2;
    private JLabel label1;
    
    String playerPoint1, playerPoint2;
    public static String info;
    
    
    Image bricksImg,grassImg,sprite,diamond,sprite2,bomb,fire;
    
    public Maze3(int player1,int player2) throws IOException {
        
        s = new Socket("localhost", 1111);
        s = new Socket("localhost", 1111);
        din = new DataInputStream(s.getInputStream());
        dout = new DataOutputStream(s.getOutputStream());
        new serverop(3).start();
        
        
        setTitle("Simple Maze Solver");
        setSize(1600, 1200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        bricksImg= new ImageIcon(getClass().getResource("bricks.jpg")).getImage();
        grassImg = new ImageIcon(getClass().getResource("grass.jpg")).getImage();
        sprite  = new ImageIcon(getClass().getResource("sprite.png")).getImage();
        diamond = new ImageIcon(getClass().getResource("diamond.png")).getImage();
        sprite2  = new ImageIcon(getClass().getResource("f1.png")).getImage();
        
        bomb  = new ImageIcon(getClass().getResource("bomb.png")).getImage();
        
        fire  = new ImageIcon(getClass().getResource("fire.jpg")).getImage();
        
        bombFlag=0;
        bombMove=0;
        
        this.player1 = player1;
        this.player2 = player2;
        
        playerPoint2 = "Your Point :" + player2;
        playerPoint1 = "Opponent Point :" + player1;
        
        
        sz=37;
        pos1X=0;
        pos1Y=1;
        pos2X=1;
        pos2Y=24;
    }
    
    
    
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
         g.setFont(new Font("comic Sans MS", Font.PLAIN, 30));
        //g.drawImage(back, 0, 0, 1300, 800, this);
       
        
        maze[bombx][bomby]=3;
        check();
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {
                
                Color color;
                
                switch (maze[row][col]) {
                        case 1 :
                    
                        g.drawImage(bricksImg, sz * col, sz * row, sz, sz , this);
                        break;
                    
                    case 9 : color = Color.RED;
                        g.setColor(color);
                        g.fillRect(sz * col+2, sz * row+2, sz, sz);
                        g.setColor(Color.BLACK);
                        
                        break;
                        
                        
                    case 2 :
                        g.drawImage(grassImg,sz * col, sz * row,  sz, sz , this);
                        g.drawImage(diamond,sz * col, sz * row,  sz, sz , this);
                        
                        break;
                    
                    
                    case 3 :
                        g.drawImage(grassImg,sz * col, sz * row,  sz, sz , this);
                        g.drawImage(bomb,sz * col, sz * row,  sz, sz , this);
                        //bombFlag=0;
                        
                        break;
                        
                    case 4 :
                        
                        g.drawImage(fire,sz * col, sz * row,  sz, sz , this);
                        maze[row][col]=0;
                        bombFlag=0;
                        bombMove=0;
                        
                        break;
                    
                    
                     
                    default : 
                        
                         g.drawImage(grassImg,sz * col, sz * row,  sz, sz , this);
                       
                      
                }
                
            }
        }
        
        
        //g.drawImage(bomb,100, 200,  sz, sz , this);
        
        //bombFlag=0;
        //bombMove=0; 
        g.drawString(playerPoint1, 1000, 100);
        g.drawString(playerPoint2, 1000, 500);

        
        g.drawImage(sprite,sz * pos1Y, sz * pos1X,  sz, sz , this);
        g.drawImage(sprite2,sz * pos2Y, sz * pos2X,  sz, sz , this);
                       
    }
    
    @Override
    protected void processKeyEvent(KeyEvent ke) {
        
        
        
        
        if (ke.getID() != KeyEvent.KEY_PRESSED) {
            return;
        }
        
        if(bombFlag==1){
            bombMove++;
        }
     
        
        if(ke.getKeyCode() == KeyEvent.VK_SPACE){
            maze[pos2X][pos2Y]=3;
            bombx = pos2X;
            bomby = pos2Y;
            bombFlag=1;
            //repaint();
        }

        
        else if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
            
            try {
                check5(pos2X,pos2Y);
                //check6(pos2X,pos2Y);
            } catch (IOException ex) {
                Logger.getLogger(Maze3.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            if(maze[pos2X][pos2Y] == 2){
                    maze[pos2X][pos2Y] = 0 ;
                     
                    try {
                    new music();
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(Maze1.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Maze1.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(Maze1.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                player2++;
                
                
                }
                
            if(maze[pos2X+1][pos2Y]!=1){
                bombx=-100;
                bomby=-100;
                info = pos2X + " " + pos2Y + " " + player2+" "+bombx+" "+bomby;
                
                try {

                   dout.writeUTF(info);
                } catch (IOException ex) {
                    Logger.getLogger(Maze1.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                   pos2X++;
                   
            }
        }
        else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
            
            try {
                check5(pos2X,pos2Y);
                //check6(pos2X,pos2Y);
            } catch (IOException ex) {
                Logger.getLogger(Maze3.class.getName()).log(Level.SEVERE, null, ex);
            }
            
                
                if(maze[pos2X][pos2Y] == 2){
                    maze[pos2X][pos2Y] = 0 ;
                     player2++;
           
                }
                
                //check5(pos2X,pos2Y);
                
            
            
                if(maze[pos2X][pos2Y-1]!=1){
                   pos2Y--;
                   
                bombx=-100;
                bomby=-100;
                info = pos2X + " " + pos2Y + " " + player2+" "+bombx+" "+bomby;
                
                try {

                   dout.writeUTF(info);
                } catch (IOException ex) {
                    Logger.getLogger(Maze1.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                   
                }
                
        }
        else if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
            try {
                check5(pos2X,pos2Y);
                //check6(pos2X,pos2Y);
            } catch (IOException ex) {
                Logger.getLogger(Maze3.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
                if(maze[pos2X][pos2Y] == 2){
                    maze[pos2X][pos2Y] = 0 ;
                     
                    try {
                    new music();
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(Maze1.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Maze1.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(Maze1.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                player2++;
                }
                
               // check5(pos2X,pos2Y);
                
            
            
                if(maze[pos2X][pos2Y+1]!=1){
                   pos2Y++;
                   
                   bombx=-100;
                bomby=-100;
                info = pos2X + " " + pos2Y + " " + player2+" "+bombx+" "+bomby;
                
                try {

                   dout.writeUTF(info);
                } catch (IOException ex) {
                    Logger.getLogger(Maze1.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                   
                }
                
        }
        else if (ke.getKeyCode() == KeyEvent.VK_UP) {
              
            try {
                check5(pos2X,pos2Y);
                // check6(pos2X,pos2Y);
            } catch (IOException ex) {
                Logger.getLogger(Maze3.class.getName()).log(Level.SEVERE, null, ex);
            }
            
                
              
              if(maze[pos2X][pos2Y] == 2){
                    maze[pos2X][pos2Y] = 0 ;
                     
                    try {
                    new music();
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(Maze1.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Maze1.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(Maze1.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                player2++;
                }
            

                if(maze[pos2X-1][pos2Y]!=1){
                   pos2X--;
                   
                   bombx=-100;
                bomby=-100;
                info = pos2X + " " + pos2Y + " " + player2+" "+bombx+" "+bomby;
                
                try {

                   dout.writeUTF(info);
                } catch (IOException ex) {
                    Logger.getLogger(Maze1.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                   
                }
                
        }
       
        try {
            check5(pos1X, pos1Y);
        } catch (IOException ex) {
            Logger.getLogger(Maze2.class.getName()).log(Level.SEVERE, null, ex);
        }
            

      
        if (maze[pos1X][pos1Y] == 2) {
            maze[pos1X][pos1Y] = 0;
            //player2++;

        }
        
                info = pos2X + " " + pos2Y + " " + player2+" "+bombx+" "+bomby;
                        
                try {

                   dout.writeUTF(info);
                } catch (IOException ex) {
                    Logger.getLogger(Maze1.class.getName()).log(Level.SEVERE, null, ex);
                }
                
        
        repaint(); 
    }
    
    public void check(){
        
        playerPoint2 = "Opponent Point :" + player2;
        playerPoint1 = "Your Point :" + player1;
        
        
        if(bombFlag==1 && bombMove>1){
            if(maze[pos1X][pos1Y]==3){
                maze[pos1X][pos1Y] = 4;
                player1=player1-3;
            }
        
            if(maze[pos2X][pos2Y]==3){
                maze[pos2X][pos2Y] = 4;
                player2 = player2-3;
            }
        }
    }
    
    
    
    public void bombBlust(int x,int y){
        
        maze[x][y]=4;
        
        if(maze[x][y+1]==0){
           maze[x][y+1]=4 ;
        }
        if(maze[x][y-1]==0){
           maze[x][y-1]=4 ;
        }
        if(maze[x+1][y]==0){
           maze[x+1][y]=4 ;
        }
        if(maze[x-1][y]==0){
          maze[x-1][y]=4 ;
        }
        
    }
    public void check5(int xpos,int ypos) throws IOException{
           if(maze[xpos][ypos]==5){
           Maze2 m=new Maze2(player1,player2);
           
                 m .setVisible(true);
                 this.setVisible(false);
           }
           else return ;
    }
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Maze3 view = null;
                try {
                    view = new  Maze3(0,0);
                } catch (IOException ex) {
                    Logger.getLogger(Maze3.class.getName()).log(Level.SEVERE, null, ex);
                }
                view.setVisible(true);
                
            }
        });
    }

}
    
    /**
     * Conventions:
     * 
     * maze[row][col]
     * 
     * Values: 0 = not-visited node
     *         1 = wall (blocked)
     *         2 = visited node
     *         9 = target node
     *
     * borders must be filled with "1" to void ArrayIndexOutOfBounds exception.
     */
    