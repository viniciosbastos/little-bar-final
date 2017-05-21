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
public class Bar2 {

    private int cadeiras;
    private int qtdCliente;
    private boolean cheio;
    
    Semaphore mutex;
    boolean[] cadeirasOcupadas;

    List<Semaphore> filaDeEspera;

    public Bar2(int cadeiras) {
        this.cadeiras = cadeiras;
        this.qtdCliente = 0;
        this.cadeirasOcupadas = new boolean[cadeiras];

        mutex = new Semaphore(1);
        filaDeEspera = new ArrayList();
        this.cheio = false;
    }

    public int sitDown() throws InterruptedException {
        mutex.acquire();
        int i = -1;
        if (!cheio && filaDeEspera.isEmpty()) {
            i = getChair();
            this.qtdCliente += 1;
            if (this.qtdCliente == this.cadeiras) {
                this.cheio = true;
            }
            mutex.release();
        } else {
            mutex.release();            
            entrarFilaDeEspera();
            return sitDown();
        }
        return i;
    }
    
    public void getUp(int chair) throws InterruptedException {
        mutex.acquire();
        this.qtdCliente -= 1;
        this.cadeirasOcupadas[chair-1] = false;
        if (this.cheio && this.qtdCliente == 0) {
            this.cheio = false;
            mutex.release();
            
            for (int i = 0; i < this.cadeiras; i++) {
                chamarFilaDeEspera();
            }
        } else
            mutex.release();
    }

    private int getChair() {
        int chair = -1;
        for (int i = 0; i < this.cadeiras; i++) {
            if (!this.cadeirasOcupadas[i]) {
                this.cadeirasOcupadas[i] = true;
                chair = i + 1;
                break;
            }
        }
        return chair;
    }

    private void entrarFilaDeEspera() throws InterruptedException {
        mutex.acquire();
        Semaphore s = new Semaphore(0);
        filaDeEspera.add(s);
        mutex.release();
        s.acquire();
    }

    private void chamarFilaDeEspera() throws InterruptedException {
        mutex.acquire();
        if (!filaDeEspera.isEmpty()) {
            Semaphore s = filaDeEspera.remove(0);
            s.release();
        }
        mutex.release();
    }

}
