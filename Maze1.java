
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Maze1 extends JFrame {

    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;
    static boolean paint;

    
//    public Socket s;
//    public DataInputStream din;
//    public DataOutputStream dout;
//    public boolean paint;

    /**
     * Conventions:
     *
     * maze[row][col]
     *
     * Values: 0 = not-visited node 1 = wall (blocked) 2 = visited node 9 =
     * target node
     *
     * borders must be filled with "1" to void ArrayIndexOutOfBounds exception.
     */
    static public int maze[][]
            = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 1, 0, 2, 0, 1, 0, 1, 1, 1, 2, 0, 0, 0, 1, 0, 0, 0, 1, 2, 1, 1, 1, 0, 1},
            {1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 2, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 5},
            {1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 2, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 2, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 2, 0, 0, 1},
            {1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 1, 1, 1, 0, 0, 2, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1},
            {1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 1, 2, 1, 0, 0, 2, 0, 0, 1, 0, 0, 0, 0, 1, 2, 1, 0, 0, 2, 0, 0, 1},
            {1, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 1, 1, 2, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 2, 0, 0, 2, 0, 0, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
            {1, 0, 1, 0, 1, 0, 1, 2, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 2, 0, 0, 0, 0, 1},
            {1, 0, 2, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 2, 1, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
            };
    
    
    spider spi ;

    static public int pos1X, pos1Y, pos2X, pos2Y, sz,player1, player2, spiPosx,spiPosy,spiMove,spiFlag,mazePass,spiPosx2,spiPosy2,spiPosx3,spiPosy3;

    public int diamondCount;
    public ImageIcon image1;
    public ImageIcon image2;
    public JLabel label1;
    
    String playerPoint1, playerPoint2;
    public static String info;
    

    Image bricksImg, grassImg, sprite, diamond, sprite2, back, background,spider;

    public Maze1() throws IOException {
        s = new Socket("192.168.43.219", 1111);
        //s = new Socket("localhost", 1111);
        din = new DataInputStream(s.getInputStream());
       dout = new DataOutputStream(s.getOutputStream());
        new serverop(1).start();
        
        setTitle("Simple Maze Solver client");
        setSize(1600, 1200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           
        background = new ImageIcon(getClass().getResource("background.jpg")).getImage();
        
        bricksImg = new ImageIcon(getClass().getResource("bricks.jpg")).getImage();
        grassImg = new ImageIcon(getClass().getResource("grass.jpg")).getImage();
        sprite = new ImageIcon(getClass().getResource("sprite.png")).getImage();
        diamond = new ImageIcon(getClass().getResource("diamond.png")).getImage();
        background = new ImageIcon(getClass().getResource("back.JPG")).getImage();
        spider = new ImageIcon(getClass().getResource("spider.png")).getImage();
        sprite2 = new ImageIcon(getClass().getResource("f1.png")).getImage();
        //back = new ImageIcon(getClass().getResource("back.JPG")).getImage();
        
        
        
       
        sz = 50;
        pos1X = 1;
        pos1Y = 0;
        pos2X = 1;
        pos2Y = 24;

        player1 = 0;
        player2 = 0;
        playerPoint2 = "Your Point :" + player2;
        playerPoint1 = "Opponent Point :" + player1;
        
        
        spiFlag = 0;
        spiPosx=12;
        spiPosy=1;
        spi = new spider(spiPosx,spiPosy,1);
        
        
        mazePass=0;

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);       
        
        g.setFont(new Font("comic Sans MS", Font.PLAIN, 30));
        //g.drawImage(back, 0, 0, 1300, 800, this);
        g.drawImage(background,0, 0,  1600, 1200 , this);
        
        
        
        check();
        
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {

                Color color;

                switch (maze[row][col]) {
                    case 1:

                        g.drawImage(bricksImg, sz * col, sz * row, sz, sz, this);
                        break;

                    case 9:
                        color = Color.RED;
                        g.setColor(color);
                        g.fillRect(sz * col + 2, sz * row + 2, sz, sz);
                        g.setColor(Color.BLACK);

                        break;

                    case 2:
                        g.drawImage(grassImg, sz * col, sz * row, sz, sz, this);
                        g.drawImage(diamond, sz * col, sz * row, sz, sz, this);

                        break;

                    default:
                        g.drawImage(grassImg, sz * col, sz * row, sz, sz, this);

                }

            }
        }
        
        
        g.drawImage(spider, sz * spiPosy2, sz * spiPosx2, sz, sz, this);
        
        g.drawImage(spider, sz * spiPosy, sz * spiPosx, sz, sz, this);
        g.drawImage(spider, sz * spiPosy3, sz * spiPosx3, sz, sz, this);
        
        g.drawImage(sprite, sz * pos1Y, sz * pos1X, sz, sz, this);
        g.drawString(playerPoint1, 1300, 100);
        g.drawString(playerPoint2, 1300, 500);

        g.drawImage(sprite2, sz * pos2Y, sz * pos2X, sz, sz, this);
        
             

    }

    @Override
    protected void processKeyEvent(KeyEvent ke) {
        
        if (ke.getID() != KeyEvent.KEY_PRESSED) {
            return;
        }

        if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {

            try {
                check5(pos2X, pos2Y);
            } catch (IOException ex) {
                Logger.getLogger(Maze1.class.getName()).log(Level.SEVERE, null, ex);
            }
            

            if (maze[pos2X][pos2Y] == 2) {
                maze[pos2X][pos2Y] = 0;

                //playerPoint2 = "Your Point :" + player2;
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

            if (maze[pos2X][pos2Y + 1] != 1) {
                pos2Y++;
                
                info = pos2X + " " + pos2Y + " " + player2+" "+spiPosx+" "+spiPosy +" "+ mazePass;
                try {

                    dout.writeUTF(info);
                } catch (IOException ex) {
                    Logger.getLogger(Maze1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
            try {
                check5(pos2X, pos2Y);
            } catch (IOException ex) {
                Logger.getLogger(Maze1.class.getName()).log(Level.SEVERE, null, ex);
            }
            

            if (maze[pos2X][pos2Y] == 2) {
                maze[pos2X][pos2Y] = 0;

                //playerPoint2 = "Your Point :" + player2;
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

           

            if (maze[pos2X][pos2Y - 1] != 1) {
                pos2Y--;
                
                info = pos2X + " " + pos2Y + " " + player2+" "+spiPosx+" "+spiPosy + " "+mazePass;
                try {

                    dout.writeUTF(info);
                } catch (IOException ex) {
                    Logger.getLogger(Maze1.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            

        } else if (ke.getKeyCode() == KeyEvent.VK_DOWN) {

            try {
                check5(pos2X, pos2Y);
            } catch (IOException ex) {
                Logger.getLogger(Maze1.class.getName()).log(Level.SEVERE, null, ex);
            }
            

            if (maze[pos2X][pos2Y] == 2) {
                maze[pos2X][pos2Y] = 0;

                
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

            if (maze[pos2X + 1][pos2Y] != 1) {
                pos2X++;
                info = pos2X + " " + pos2Y + " " + player2+" "+spiPosx+" "+spiPosy+ " "+ mazePass;
                
                try {

                    dout.writeUTF(info);
                } catch (IOException ex) {
                    Logger.getLogger(Maze1.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        
        else if (ke.getKeyCode() == KeyEvent.VK_UP) {
            try {
                check5(pos2X, pos2Y);
            } catch (IOException ex) {
                Logger.getLogger(Maze1.class.getName()).log(Level.SEVERE, null, ex);
            }
            

            if (maze[pos2X][pos2Y] == 2) {
                maze[pos2X][pos2Y] = 0;

                //playerPoint2 = "Your Point :" + player2;
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

            if (maze[pos2X - 1][pos2Y] != 1) {
                pos2X--;
                
                info = pos2X + " " + pos2Y + " " + player2+" "+spiPosx+" "+spiPosy+ " "+mazePass;
                
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
            Logger.getLogger(Maze1.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        if (maze[pos1X][pos1Y] == 2) {
            maze[pos1X][pos1Y] = 0;
            //player1++;

        }
        
        info = pos2X + " " + pos2Y + " " + player2+" "+spiPosx+" "+spiPosy+ " "+mazePass;
        
        try {

            dout.writeUTF(info);
        } catch (IOException ex) {
            Logger.getLogger(Maze1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
        repaint();

    }
    
    
    
    public void check(){
        
        
        playerPoint2 = "Your Point :" + player2;
        playerPoint1 = "Opponent Point :" + player1;
        spiPosx = spi.getxPos();
        spiPosy = spi.getyPos();
        
        info = pos2X + " " + pos2Y + " " + player2+" "+spiPosx+" "+spiPosy+ " "+ mazePass;
                
        
        
        if(spiPosx==pos2X && spiPosy== pos2Y){
            player2--;
        }
        
        if(spiPosx2==pos2X && spiPosy2== pos2Y){
            player2--;
        }
        
        if(spiPosx3==pos2X && spiPosy3== pos2Y){
            player2--;
        }
        
        
        if (maze[pos1X][pos1Y] == 2) {
            maze[pos1X][pos1Y] = 0;
            player1++;

        }
    }

    public void check5(int xpos, int ypos) throws IOException {


        if (maze[xpos][ypos] == 5 || mazePass==1) {
//            mazePass=1;
//            info = pos2X + " " + pos2Y + " " + player2+" "+spiPosx+" "+spiPosy+ " "+mazePass;
//        
//            Maze2 m1 = new Maze2(player1,player2);
//
//            m1.setVisible(true);
//            this.setVisible(false);
        
        mazePass=1 ;
        info = pos2X + " " + pos2Y + " " + player2+" "+spiPosx+" "+spiPosy+ " "+mazePass;
        gameOver go = new gameOver(player1,player2);
        go.setVisible(true);
        this.setVisible(false);

        } else {
            return;
        }
    }

    

    
    
//    public static void main(String[] args) throws IOException {
//
//        Maze1 view = new Maze1();
//        view.setVisible(true);
//
//    }

}
