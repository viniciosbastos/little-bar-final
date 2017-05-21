/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlebarfinal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Vinicios
 */
public class Bar {
    int cadeiras;
    int empty;
    boolean full;
    List<Semaphore> filaDeEspera;
    boolean[] cadeirasOcupadas;

    Semaphore mutex;

    public Bar(int c) {
        this.cadeiras = c;
        this.empty = c;
        this.full = false;
        this.cadeirasOcupadas = new boolean[c];

        mutex = new Semaphore(1);
        filaDeEspera = new ArrayList();
    }

    public void setCadeiras(int cadeiras) {
        this.cadeiras = cadeiras;
    }

    public int sitDown() throws InterruptedException {       
        mutex.acquire();        
        int i = 0;
        if (full) {
            mutex.release();
            entrarFilaDeEspera();
            mutex.acquire();
        }
        
        i = getChair();
        this.empty -= 1;
        if (this.empty == 0) {
            full = true;
        }
        mutex.release();
        return i;
    }

    public void getUp(int chair) throws InterruptedException {
        mutex.acquire();
        this.cadeirasOcupadas[chair-1] = false;
        if (full) {
            this.empty += 1;
            if (this.empty == this.cadeiras) {
                full = false;
                if (!filaDeEspera.isEmpty()) {
                    mutex.release();
                    for (int i = 0; i < this.cadeiras; i++) {
                        chamarFilaDeEspera();
                    }
                    return;
                }
            }
            mutex.release();
        } else {
            this.empty += 1;
            if (this.empty > this.cadeiras)
                this.empty = this.cadeiras;
            mutex.release();
        }
    }

    private void entrarFilaDeEspera() throws InterruptedException {
        Semaphore s = new Semaphore(0);
        filaDeEspera.add(s);
        s.acquire();
    }
    
    private void chamarFilaDeEspera() {
        if (!filaDeEspera.isEmpty()) {
            Semaphore s = filaDeEspera.remove(0);
            s.release();
        }
    }
    
    private int getChair() {
        int chair = 0;
        for (int i = 0; i < this.cadeiras; i++) {
            if (!this.cadeirasOcupadas[i]) {
                this.cadeirasOcupadas[i] = true;
                chair = i+1;
                break;
            }
        }
        return chair;
    }
}
