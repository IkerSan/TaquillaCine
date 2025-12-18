package cuatrovientos.dam.psp.sincronizacion.taquillas;

import java.util.List;

public class Taquilla extends Thread {
    
    private int id;
    private List<Cola> colas;
    private Cine cine;
    private long tiempoTrabajado = 0;
    private boolean activo = true;

    public Taquilla(int id, List<Cola> colas, Cine cine) {
        this.id = id;
        this.colas = colas;
        this.cine = cine;
    }

    @Override
    public void run() {
        while (activo) {
            Cliente cliente = null;
            
            // Buscar cliente en las colas 
            for (Cola cola : colas) {
                cliente = cola.sacarCliente();
                if (cliente != null) {
                    break;
                }
            }

            if (cliente != null) {
                long inicio = System.currentTimeMillis();
                
                // Simular tiempo de venta 
                try {
                    Thread.sleep((long) (Math.random() * 11 + 20)); 
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
                if (cine.comprarEntrada()) {
                    System.out.println("Taquilla " + id + " vendió entrada a Cliente " + cliente.getId());
                } else {
                    System.out.println("Taquilla " + id + " no pudo vender a Cliente " + cliente.getId() + " (Agotado)");
                }
                
                long fin = System.currentTimeMillis();
                tiempoTrabajado += (fin - inicio);
            } else {
                // Si no hay clientes, esperar un poco
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public void detener() {
        this.activo = false;
    }

    public long getTiempoTrabajado() {
        return tiempoTrabajado;
    }
}
