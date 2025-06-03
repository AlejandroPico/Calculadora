package operations;

public class RaizCuadrada implements Operation {
    @Override
    public double calcular(double a, double b) { // b will be ignored for square root
        if (a < 0) {
            throw new ArithmeticException("Cannot calculate square root of a negative number");
        }
        return Math.sqrt(a);
    }
}
