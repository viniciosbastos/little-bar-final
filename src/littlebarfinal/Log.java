/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlebarfinal;

import java.util.concurrent.Semaphore;

/**
 *
 * @author Vinicios
 */
public class Log {
    private static final Semaphore mutex = new Semaphore(1);
    
    public static void log(String text) throws InterruptedException {
        mutex.acquire();
        System.out.println(text);
        mutex.release();
    }
}
