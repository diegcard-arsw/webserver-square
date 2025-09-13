package org.arsw;


/**
 * Punto de entrada del proyecto que delega la ejecución en {@link SquareServer}.
 *
 * @author Diego Cardenas
 * @since 1.0
 */
public class Main {
	/**
	 * Arranca el servidor de cuadrados usando los argumentos suministrados.
	 *
	 * @param args argumentos de línea de comandos, donde {@code args[0]} puede ser el puerto
	 * @throws Exception si ocurre un error en el arranque del servidor
	 */
	public static void main(String[] args) throws Exception {
		// Delegamos en el servidor de cuadrados
		SquareServer.main(args);
	}
}