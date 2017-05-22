/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlebarfinal;

import java.net.URISyntaxException;
import java.util.Random;

/**
 *
 * @author Vinicios
 */
public class ClientMaker {
    
    public static Client createClient(int tc, int tb, String nome) throws URISyntaxException {
        Client c = null;
        
        Random r = new Random();
        
        int i = r.nextInt(3);

        switch(i) {
            case 0:
                c = new ClientBrendan(tc, tb, nome);
                break;
            case 1:
                c = new ClientMay(tc, tb, nome);
                break;
            case 2:
                c = new ClientWally(tc, tb, nome);
                break;
        }
        return c;
    }
}
