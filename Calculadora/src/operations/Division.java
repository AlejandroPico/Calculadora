package operations;

public class Division implements Operation {

	@Override
	public double calcular(double a, double b) {

		if (a == 0 && b == 0) {
			throw new ArithmeticException("Resultado indefinido");
		}

		if (b == 0) {
			throw new ArithmeticException("No se puede dividir entre cero");
		}

		return a / b;
	}
}
