
package sockets.server.network;

import java.net.Socket;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;
import sockets.server.controller.ServerController;


public class GameHandler implements Runnable{
    private final Socket playerSocket;
    private PrintWriter outToPlayer;
    private BufferedReader inFromPlayer;
    private final boolean autoFlush = true;
    private boolean  connected = false;
    private ServerController serverController;
    private static int numberOfPlayers = 0;
    
    
    public GameHandler(Socket playerSocket){
        this.playerSocket = playerSocket;
        this.connected = true;
    }
    
    @Override
    public void run(){
        
        try{
            numberOfPlayers++;
            System.out.println("New THREAD is created.");
            System.out.println("Number of players in the game: " + numberOfPlayers);
            outToPlayer = new PrintWriter(new OutputStreamWriter(playerSocket.getOutputStream()),autoFlush);
            inFromPlayer = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
                       
            String userInput;
            
            serverController = new ServerController();
            
            serverController.intro(outToPlayer);
           
            while(connected && ((userInput = inFromPlayer.readLine())!=null)){
                
                if(userInput.startsWith("exit") && userInput.length()==4){
                    System.out.println("\nClient requested for disconnection...\n");
                    numberOfPlayers--;
                    serverController.disconnect(playerSocket);
                    connected = false;
                }
                
                else{
                    serverController.checkUserInput(userInput, playerSocket, outToPlayer);
                }
            
            }
        } catch(IOException e){
            System.out.println("GameHandler: IOException occured");            
        }
    }
     
}
