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
public class Bar {

    private int cadeiras;
    private int qtdCliente;
    private boolean cheio;

    Semaphore mutex;
    boolean[] cadeirasOcupadas;

    public Semaphore semaforo;

    public Bar() {
        this.qtdCliente = 0;

        mutex = new Semaphore(1);
        this.cheio = false;
    }
    
    public void setCadeira(int c) {
        this.cadeiras = c;
        this.cadeirasOcupadas = new boolean[this.cadeiras];
        semaforo = new Semaphore(this.cadeiras, true);
    }

    public void sitDown() throws InterruptedException {
        semaforo.acquire();
        mutex.acquire();
        this.qtdCliente += 1;
        if (this.qtdCliente == this.cadeiras)
            this.cheio = true;
        mutex.release();
    }

    public void getUp(int chair) throws InterruptedException {
        mutex.acquire();
        this.qtdCliente -= 1;
        this.cadeirasOcupadas[chair-1] = false;
        if (this.qtdCliente == 0) {
            this.cheio = false;
            this.semaforo.release(this.cadeiras);
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
}
