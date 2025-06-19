package com.example.testeableapp


import com.example.testeableapp.ui.Screens.calculateTip
import kotlin.test.Test
import kotlin.test.assertEquals


class CalculatorTest {

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

        // Act: Calculamos el total por persona (incluyendo la propina)
        val tip = calculateTip(amount, tipPercent, roundUp)
        val totalPerPerson = if (numberOfPeople > 0) (amount + tip) / numberOfPeople else 0.0

        // Assert: Verificamos que el total por persona sea el esperado
        assertEquals(34.25, totalPerPerson, 0.0)
    }


}