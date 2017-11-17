/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sockets.client.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import sockets.client.controller.ClientController;

/**
 *
 * @author darkferi
 */
public class ClientInterpreter implements Runnable{
    
    private final int serverPort;
    private ClientController clientCntrl;
    private ConsoleOutput screenHandler;                                        //////////ezafe shod
    private BufferedReader console;
    private static boolean ThreadStarted = false;
    

    
    public ClientInterpreter(int serverPort){
        this.serverPort = serverPort;
    }
    
    
    @Override
    public void run(){
        
        ThreadStarted = true;
        clientCntrl = new ClientController();
        console = new BufferedReader(new InputStreamReader(System.in));
        String command;
        screenHandler = new ConsoleOutput();
        clientCntrl.connect(serverPort, screenHandler);             
        
        while(ThreadStarted){
            try {
                
                command = console.readLine().toLowerCase();
                command = command.trim();
                
                if(command.startsWith("exit") && command.length()==4){
                    clientCntrl.exit();
                    ThreadStarted = false;
                }
                
                else{
                    clientCntrl.sendMessage(command);
                }
                           
            } catch (IOException e) {
                System.out.println("ClientVeiw > run() > IOException");
            }
        }      
    }
}
