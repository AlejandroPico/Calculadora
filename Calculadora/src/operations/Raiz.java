package operations;

public class Raiz implements Operation {
    @Override
    public double calcular(double valor, double indice) {
        if (indice == 0) {
            throw new ArithmeticException("Indice cero");
        }
        if (valor < 0 && indice % 2 == 0) {
            throw new ArithmeticException("Raiz negativa");
        }
        return Math.pow(valor, 1.0 / indice);
    }
}
