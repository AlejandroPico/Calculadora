package operations;

public class Seno implements Operation {
    @Override
    public double calcular(double a, double b) { // b will be ignored
        return Math.sin(a);
    }
}
