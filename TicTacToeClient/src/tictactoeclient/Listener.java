/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import onlineplaying.PlayerDto;

/**
 *
 * @author abdullahraed
 */
public interface Listener {
    
    void onServerResponse(boolean success);
    default void onOnlinePlayersUpdate(ArrayList<PlayerDto> onlinePlayers){
    
    
    }
    

            
}
