package operations;

public class Logaritmo implements Operation {
    @Override
    public double calcular(double a, double b) {
        if (a <= 0) {
            throw new ArithmeticException("Log negativo");
        }
        return Math.log(a);
    }
}
