package cuatrovientos.dam.psp.sincronizacion.taquillas;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // CONFIGURACIÓN
        int NUM_TAQUILLAS = 2;
        int NUM_ASIENTOS = 200;
        int NUM_COLAS = 4;
        int CAPACIDAD_COLA = 10;
        int MINUTOS_VENTA = 30; 

        System.out.println("Inicio Cine");

        Cine cine = new Cine(NUM_ASIENTOS);
        
        // Crear colas
        List<Cola> colas = new ArrayList<>();
        for (int i = 0; i < NUM_COLAS; i++) {
            colas.add(new Cola(CAPACIDAD_COLA));
        }

        // Crear e iniciar taquillas
        ArrayList<Taquilla> taquillas = new ArrayList<>();
        for (int i = 0; i < NUM_TAQUILLAS; i++) {
            Taquilla t = new Taquilla(i + 1, colas, cine);
            taquillas.add(t);
            t.start();
        }

        // Bucle para generar clientes
        int idCliente = 1;
        for (int min = 0; min < MINUTOS_VENTA; min++) {
            // Entre 10 y 15 clientes por "minuto"
            int numClientes = (int) (Math.random() * 6 + 10); 
            System.out.println("Generando " + numClientes + " clientes en minuto " + (min + 1));

            // Generamos clientes
            for (int k = 0; k < numClientes; k++) {
                Cliente c = new Cliente(idCliente++);
                boolean enCola = false;
                
                // Intenta entrar en alguna cola
                for (Cola cola : colas) {
                    if (cola.agregarCliente(c)) {
                        enCola = true;
                        System.out.println("Cliente " + c.getId() + " entró en cola.");
                        break;
                    }
                }

                if (!enCola) {
                    System.out.println("Cliente " + c.getId() + " se marcha (Colas llenas).");
                    cine.incrementarRechazados();
                }

                // Pequeña pausa entre clientes
                Thread.sleep(100); 
            }
            // Pausa entre minutos
            Thread.sleep(1000);
        }

        System.out.println("Generación terminada. Esperando vaciar colas...");
        // Esperar a que se vacíen las colas
        Thread.sleep(3000);

        // Paramos taquillas
        for (Taquilla t : taquillas) {
            t.detener();
            t.join();
        }

        // RESULTADOS 
        System.out.println("\n--- RESULTADOS ---");
        System.out.println("Entradas vendidas: " + cine.getEntradasVendidas());
        System.out.println("Clientes rechazados: " + cine.getClientesRechazados());
        
        long tiempoTotal = 0;
        for(Taquilla t : taquillas) {
             tiempoTotal += t.getTiempoTrabajado();
             System.out.println("Tiempo trabajado Taquilla " + t.getName() + ": " + t.getTiempoTrabajado() + "ms");
        }
        System.out.println("Tiempo total venta acumulado (ms): " + tiempoTotal);
    }
}
