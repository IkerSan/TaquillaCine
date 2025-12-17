package cuatrovientos.dam.psp.sincronizacion.taquillas;

import java.util.concurrent.Semaphore;

public class Cine {
    
    // Semáforo para controlar los asientos disponibles (200)
    private Semaphore asientos;
    private int entradasVendidas = 0;
    private int clientesRechazados = 0; 

    public Cine(int numeroAsientos) {
        this.asientos = new Semaphore(numeroAsientos);
    }

    // Intenta comprar entrada
    public boolean comprarEntrada() {
        if (asientos.tryAcquire()) {
            synchronized (this) {
                entradasVendidas++;
            }
            return true;
        } else {
            // Rechaza si no hay asientos disponibles
            incrementarRechazados();
            return false;
        }
    }

    public synchronized void incrementarRechazados() {
        clientesRechazados++;
    }

    public int getEntradasVendidas() {
        return entradasVendidas;
    }

    public int getClientesRechazados() {
        return clientesRechazados;
    }
}
