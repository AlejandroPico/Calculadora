package operations;

public class Tangente implements Operation {
    @Override
    public double calcular(double a, double b) { // b will be ignored
        // Consider Math.tan behavior for asymptotes (e.g., tan(PI/2)) - can return large values or Infinity.
        // For very large results or Infinity, the formatResult might need adjustment or it will display "Infinity".
        return Math.tan(a);
    }
}
