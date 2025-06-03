package operations;

public class Potencia implements Operation {
    @Override
    public double calcular(double base, double exponente) {
        return Math.pow(base, exponente);
    }
}
