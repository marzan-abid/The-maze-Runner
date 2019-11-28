/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dell
 */
public class serverop extends Thread{
    
    int flag;
    serverop(int flag){
        this.flag = flag ;
    }
    
    @Override
    public void run()
    {
        
        if(flag == 1){
            while(true)
            {   


                String str=null;
                try {
                    str = Maze1.din.readUTF();
                } catch (IOException ex) {
                    Logger.getLogger(serverop.class.getName()).log(Level.SEVERE, null, ex);
                }
                StringTokenizer st = new StringTokenizer(str," ");  

                    Maze1.pos1X = Integer.parseInt(st.nextToken());
                    Maze1.pos1Y = Integer.parseInt(st.nextToken());
                    Maze1.player1 = Integer.parseInt(st.nextToken());
                    Maze1.spiPosx2 = Integer.parseInt(st.nextToken());
                    Maze1.spiPosy2 = Integer.parseInt(st.nextToken());
                    
                    Maze1.spiPosx3 = Integer.parseInt(st.nextToken());
                    Maze1.spiPosy3 = Integer.parseInt(st.nextToken());
                    Maze1.mazePass= Integer.parseInt(st.nextToken());



           }
       }
        
        
       if(flag==2){
           
           while(true)
            {   


                String str=null;
                try {
                    str = Maze2.din.readUTF();
                } catch (IOException ex) {
                    Logger.getLogger(serverop.class.getName()).log(Level.SEVERE, null, ex);
                }
                StringTokenizer st = new StringTokenizer(str," ");  

                    Maze2.pos1X = Integer.parseInt(st.nextToken());
                    Maze2.pos1Y = Integer.parseInt(st.nextToken());
                    Maze2.player1 = Integer.parseInt(st.nextToken());
                    


           }
           
           
       }
       
       if(flag==3){
           
           while(true)
            {   


                String str=null;
                try {
                    str = Maze3.din.readUTF();
                } catch (IOException ex) {
                    Logger.getLogger(serverop.class.getName()).log(Level.SEVERE, null, ex);
                }
                StringTokenizer st = new StringTokenizer(str," ");  

                    Maze3.pos1X = Integer.parseInt(st.nextToken());
                    Maze3.pos1Y = Integer.parseInt(st.nextToken());
                    Maze3.player1 = Integer.parseInt(st.nextToken());
                    


           }
           
           
       }
    }
}
