/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlebarfinal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vinicios
 */
public class Bar3 extends Thread {

    private int cadeiras;
    private int qtdCliente;
    private boolean cheio;

    Semaphore mutex;
    boolean[] cadeirasOcupadas;

    List<Semaphore> filaDeEspera;
    boolean wait;

    public Bar3() {
        this.qtdCliente = 0;

        mutex = new Semaphore(1);
        filaDeEspera = new ArrayList();
        this.cheio = false;
        this.wait = false;

        setPriority(5);
    }
    
    public void setCadeira(int c) {
        this.cadeiras = c;
        this.cadeirasOcupadas = new boolean[this.cadeiras];
    }

    public void sitDown() throws InterruptedException {
        mutex.acquire();
        Semaphore s = new Semaphore(0);
        filaDeEspera.add(s);
        mutex.release();
        s.acquire();
    }

    public void getUp(int chair) throws InterruptedException {
        mutex.acquire();
        this.qtdCliente -= 1;
        this.cadeirasOcupadas[chair-1] = false;
        if (this.qtdCliente == 0) {
            this.cheio = false;
        }
        mutex.release();
    }

    public int getChair() throws InterruptedException {
        mutex.acquire();
        int chair = -1;
        for (int i = 0; i < this.cadeiras; i++) {
            if (!this.cadeirasOcupadas[i]) {
                this.cadeirasOcupadas[i] = true;
                chair = i + 1;
                break;
            }
        }
        mutex.release();
        return chair;
    }

    @Override
    public void run() {
        while (true) {
            try {
                mutex.acquire();
                if (!filaDeEspera.isEmpty()) {
                    if (!this.cheio) {
                        this.qtdCliente += 1;
                        if (this.qtdCliente == this.cadeiras)
                            this.cheio = true;
                        Semaphore s = filaDeEspera.remove(0);
                        s.release();                        
                    }
                }
                mutex.release();
            } catch (InterruptedException ex) {
                Logger.getLogger(Bar3.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
