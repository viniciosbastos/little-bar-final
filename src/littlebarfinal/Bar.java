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

    Semaphore mutex;                //Semáforo de proteção das variáveis
    boolean[] cadeirasOcupadas;

    public Semaphore semaforo;      //Semáforo com N permissões

    public Bar() {
        this.qtdCliente = 0;

        mutex = new Semaphore(1);
        this.cheio = false;
    }
    
    public void setCadeira(int c) {
        this.cadeiras = c;
        this.cadeirasOcupadas = new boolean[this.cadeiras];
        semaforo = new Semaphore(this.cadeiras, true);      //Cria o semáforo com N permissões
    }

    public void sitDown() throws InterruptedException {
        semaforo.acquire();                        //Cliente tenta dar o down() no semáforo para entrar no bar            
        mutex.acquire();                           //Caso consiga, dá o down() no mutex, para proteger as variaveis
        this.qtdCliente += 1;                      //Incrementa número de clientes no bar atualmente
        if (this.qtdCliente == this.cadeiras)
            this.cheio = true;
        mutex.release();                           //up() no mutex;
    }

    public void getUp(int chair) throws InterruptedException {
        mutex.acquire();
        this.qtdCliente -= 1;                      //Diminui quantidade de clientes presentes no bar naquele momento
        this.cadeirasOcupadas[chair-1] = false;    //Libera a cadeira que o cliente estava para que outro possa usar
        if (this.qtdCliente == 0) {                //Se qtdCliente == 0 o último cliente acabou de sair, então acorda os clentes esperando
            this.cheio = false;
            this.semaforo.release(this.cadeiras);  //semaforo.release(N), faz acordar N clientes. Para não precisar de 'FOR'
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
