import java.util.Scanner;
import java.util.Random;

class Jugador {
    String nombre;
    int puntaje;
    Jugador anterior;
    Jugador siguiente;

    public Jugador(String nombre, int puntaje) {
        this.nombre = nombre;
        this.puntaje = puntaje;
        this.anterior = null;
        this.siguiente = null;
    }
}

class JuegoSumas {
    private Jugador inicio;
    private Jugador fin;

    public JuegoSumas() {
        this.inicio = null;
        this.fin = null;
    }

    public int jugar(String jugador, Scanner sc) {
        Random rand = new Random();
        int nivel = 1;
        int puntaje = 0;
        int tiempo = 10;

        while (true) {
            System.out.println("Nivel " + nivel + " ---");

            for (int i = 1; i <= 5; i++) {
                int a = rand.nextInt(50) + 1;
                int b = rand.nextInt(50) + 1;
                int resultado = a + b;

                System.out.println("Suma #" + i + ": ¿Cuánto es " + a + " + " + b + "?");
                long inicioTiempo = System.currentTimeMillis();

                String respuesta = sc.nextLine();

                long finTiempo = System.currentTimeMillis();
                long tiempoUsado = (finTiempo - inicioTiempo) / 1000;

                if (tiempoUsado > tiempo) {
                    System.out.println("Se nos va el tiempo (" + tiempoUsado + "s)");
                    System.out.println("Puntaje final: " + puntaje);
                    return puntaje;
                }

                int respuestaNum = Integer.parseInt(respuesta);

                if (respuestaNum == resultado) {
                    puntaje += 100;
                    System.out.println("Bien hecho +100 puntos");
                } else {
                    System.out.println("Incorrecto. La respuesta era " + resultado);
                    System.out.println("Puntaje final: " + puntaje);
                    return puntaje;
                }
            }

            nivel++;
            tiempo -= 2;
            if (tiempo < 2) tiempo = 2;
        }
    }

    public void insertarJugador(String nombre, int puntaje) {
        Jugador nuevo = new Jugador(nombre, puntaje);

        if (inicio == null) {
            inicio = fin = nuevo;
            return;
        }

        Jugador actual = inicio;
        while (actual != null && actual.puntaje >= puntaje) {
            actual = actual.siguiente;
        }

        if (actual == inicio) {
            nuevo.siguiente = inicio;
            inicio.anterior = nuevo;
            inicio = nuevo;
        } else if (actual == null) {
            fin.siguiente = nuevo;
            nuevo.anterior = fin;
            fin = nuevo;
        } else {
            Jugador previo = actual.anterior;
            previo.siguiente = nuevo;
            nuevo.anterior = previo;
            nuevo.siguiente = actual;
            actual.anterior = nuevo;
        }
    }

    public void mostrar() {
        System.out.println("LOS MEJORES JUGADORES");
        Jugador actual = inicio;
        int cont = 1;

        if (actual == null) {
            System.out.println("No hay jugadores aún.");
            return;
        }

        while (actual != null && cont <= 5) {
            System.out.println(cont + ". " + actual.nombre + " - " + actual.puntaje + " puntos");
            cont++;
            actual = actual.siguiente;
        }
        System.out.println("-----------------------------------");
    }
}

public class juego {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        JuegoSumas juego = new JuegoSumas();
        boolean seguir = true;

        while (seguir) {
            System.out.println("+++++ JUEGO: SUMAS RÁPIDAS +++++");
            System.out.print("Ingresa nombre de usuario: ");
            String nombre = sc.nextLine();

            int puntaje = juego.jugar(nombre, sc);
            juego.insertarJugador(nombre, puntaje);
            juego.mostrar();

            System.out.print("¿Deseas jugar otra vez? (si/no): ");
            String op = sc.nextLine().trim().toLowerCase();
            seguir = op.equals("si");
        }

        System.out.println("Vuelve pronto");
        sc.close();
    }
}
