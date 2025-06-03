package operations;

public class Coseno implements Operation {
    @Override
    public double calcular(double a, double b) { // b will be ignored
        return Math.cos(a);
    }
}
