/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import java.util.ArrayList;
import onlineplaying.PlayerDto;

/**
 *
 * @author abdullahraed
 */
public interface Listener {
    
    void onServerResponse(boolean success, ArrayList responseData);
    default void onOnlinePlayersUpdate(ArrayList<PlayerDto> onlinePlayers){
        
    
    
    }
    void onServerCloseResponse(boolean serverClosed);
            
}
