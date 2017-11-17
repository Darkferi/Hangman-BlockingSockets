/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sockets.client.view;

import sockets.client.network.OutputHandler;


public class ConsoleOutput implements OutputHandler {                      /////////ezafe shod
    
    private ClientConsoleThreadSafety consoleManager = new ClientConsoleThreadSafety();
    
    
    @Override
    public void messageOnScreen(String message) {
        consoleManager.println(message);
    }
    
}
