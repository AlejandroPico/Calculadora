package operations;

public class Porcentaje implements Operation {
    @Override
    public double calcular(double a, double b) { // b will be ignored
        return a / 100.0;
    }
}
