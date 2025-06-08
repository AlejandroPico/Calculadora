# Calculadora

Aplicación de calculadora con interfaz gráfica basada en Swing. Incorpora funciones básicas y científicas.

## Funcionalidades
- Suma, resta, multiplicación y división
- Potencias (`xʸ`) y raíces cuadradas y de índice n (`√`, `ⁿ√`)
- Operaciones con porcentajes
- Cambio de signo (`±`)
- Introducción de decimales
- Funciones trigonométricas: seno, coseno y tangente
- Logaritmo natural (`ln`)
- Constantes matemáticas π y e
- Memoria de cálculo (`M+`, `MR`, `MC`)
- Manejo de números grandes mediante notación científica

## Errores corregidos
- Display ampliado para una mejor visualización
- Eliminados decimales innecesarios en los resultados
- Tras mostrar un resultado el siguiente número borra automáticamente el display
- Mensaje de error al intentar dividir entre cero

## Pendiente
- Prioridad de operaciones con paréntesis
- Cálculo y visualización de fracciones o números periódicos

## Uso
Compilar:
```bash
javac -d bin src/main/Calculadora.java src/gui/Gui.java src/operations/*.java
```

Ejecutar:
```bash
java -cp bin main.Calculadora
```
