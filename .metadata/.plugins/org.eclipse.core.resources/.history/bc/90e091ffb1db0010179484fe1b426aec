package cuatrovientos.dam.psp.sincronizacion.taquillas;

import java.util.concurrent.Semaphore;

public class Cola {

    private ArrayList<Cliente> cola = new ArrayList<>();
    private Semaphore semaforo = new Semaphore(1);
    
    private int capacidadMaxima;

    public Cola(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    // Añadimos un cliente a la cola
    public boolean agregarCliente(Cliente cliente) {
        try {
            semaforo.acquire(); // Bloqueamos el acceso
            if (cola.size() < capacidadMaxima) {
                cola.add(cliente);
                semaforo.release(); // Liberamos el acceso
                return true;
            } else {
                semaforo.release();
                return false;
            }
        } catch (InterruptedException e) {
            return false;
        }
    }

    // Saca un cliente, null si la cola esta vacia
    public Cliente sacarCliente() {
        try {
            semaforo.acquire(); // Bloqueamos el acceso
            if (!cola.isEmpty()) {
                Cliente c = cola.poll(); // poll() es similar a remove(), pero no lanza una excepción si la lista está vacía
                semaforo.release(); // Liberamos el acceso
                return c;
            } else {
                semaforo.release(); 
                return null;
            }
        } catch (InterruptedException e) {
            return null;
        }
    }
    
    // Obtenemos el tamaño de la cola
    public int getTamano() {
        int tam = 0;
        try {
            semaforo.acquire();
            tam = cola.size();
            semaforo.release();
        } catch (InterruptedException e) { }
        return tam;
    }
}
