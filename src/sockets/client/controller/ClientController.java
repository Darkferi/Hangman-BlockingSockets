/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sockets.client.controller;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import sockets.client.network.ClientSocketHandling;
import sockets.client.network.OutputHandler;


///////////////////////////////////***********CompletableFuture.runAsync(()*************//////////////////////////
public class ClientController {
    
    private final ClientSocketHandling clientSocket = new ClientSocketHandling();
        
    public void connect(int serverPort, OutputHandler screenHandler) {
        
        CompletableFuture.runAsync(() -> {
            clientSocket.connect(serverPort, screenHandler);
        });
       
    }

    public void exit() {
        clientSocket.disconnect();
    }

    
    public void sendMessage(String command) throws IOException {
        CompletableFuture.runAsync(() -> {
            clientSocket.sendMessage(command);
        });
    }    
}
