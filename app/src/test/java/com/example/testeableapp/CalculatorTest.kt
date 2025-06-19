package com.example.testeableapp


import com.example.testeableapp.ui.Screens.calculateTip
import kotlin.test.Test
import kotlin.test.assertEquals


class CalculatorTest {

    //-Pruebas Unitarias (2 pts):

    //Cálculo con 37% de propina y redondeo
    @Test
    fun testCalculateTipWith37PercentAndRoundUp() {
        // Arrange
        val amount = 100.0
        val tipPercent = 37
        val roundUp = true

        // Act
        val result = calculateTip(amount, tipPercent, roundUp)

        // Assert
        assertEquals(37.0, result, 0.0)
    }

    //Cálculo con cantidad negativa (debe dar 0)
    @Test
    fun testCalculateTipWithNegativeAmount() {
        // Arrange
        val amount = -50.0
        val tipPercent = 20
        val roundUp = false

        // Act
        val result = calculateTip(amount, tipPercent, roundUp)

        // Assert
        assertEquals(0.0, result, 0.0)
    }

    //Cálculo del total a pagar por persona (incluye propina)
    @Test
    fun testCalculateTotalPerPerson() {
        // Arrange
        val amount = 100.0
        val tipPercent = 37
        val numberOfPeople = 4
        val roundUp = true

        // Act
        val tip = calculateTip(amount, tipPercent, roundUp)
        val totalPerPerson = if (numberOfPeople > 0) (amount + tip) / numberOfPeople else 0.0

        // Assert
        assertEquals(34.25, totalPerPerson, 0.0)
    }

//---------------------------------------------------------------------------------------------------------------------------------

//    Pruebas Unitarias adicionales (2 pts):

//    Prueba 1
//    Validar que el porcentaje de propina nunca sea mayor al 50%.
//
//    Descripción: Si el porcentaje de propina supera el límite de 50%, debería ser capped a 50%.
//
//    Por qué es relevante: Asegura que los valores de entrada no violen un límite razonable y que el cálculo sea consistente y válido.

    @Test
    fun testCalculateTipWithPercentageGreaterThan50() {
        // Arrange
        val amount = 100.0
        val tipPercent = 60
        val roundUp = false

        // Act
        val result = calculateTip(amount, tipPercent, roundUp)

        // Assert
        val expectedTip = amount * 50 / 100
        assertEquals(expectedTip, result, 0.0)
    }

    // El error "Expected <50.0> with absolute tolerance <0.0>, actual <60.0>" indica que la función
    // calculateTip está devolviendo un valor incorrecto. En este caso, esperabas que la propina
    // fuera 50.0, pero la función está devolviendo 60.0.

    // Esto sugiere que la lógica de cálculo de la propina no está limitando correctamente el porcentaje
    // de la propina cuando este supera el 50%. Esto podría ocurrir si el porcentaje de propina se introduce
    // con un valor mayor al permitido (en este caso, 60%), pero la función no lo está limitando a 50% como debería.


//    Prueba 2
//    Verificar que el monto total por persona sea 0 si el número de personas es 0.
//
//    Descripción: Cuando el número de personas es 0, el cálculo del total por persona debe ser 0.
//
//    Por qué es relevante: Asegura que la aplicación maneje correctamente los casos de entradas inválidas como un número de personas igual a 0.

    @Test
    fun testCalculateTotalPerPersonWithZeroPeople() {
        // Arrange
        val amount = 100.0
        val tipPercent = 20
        val numberOfPeople = 0
        val roundUp = false

        // Calcular la propina usando la lógica de `calculateTip`
        val tip = calculateTip(amount, tipPercent, roundUp)

        // Calcular el total por persona (lo que se haría en el Composable)
        val totalPerPerson = if (numberOfPeople > 0) (amount + tip) / numberOfPeople else 0.0

        // Assert
        assertEquals(0.0, totalPerPerson, 0.0)
    }

    //El cálculo del total por persona cuando el número de personas es 0 está funcionando como se esperaba.
}