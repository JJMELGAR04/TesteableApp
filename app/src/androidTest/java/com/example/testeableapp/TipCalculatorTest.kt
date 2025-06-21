package com.example.testeableapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipe
import com.example.testeableapp.ui.Screens.TipCalculatorScreen
import com.example.testeableapp.ui.theme.TesteableAppTheme
import org.junit.Rule
import org.junit.Test

class TipCalculatorTest {

    @get:Rule
    val composeRule = createComposeRule()
    //Test 1
    //Redondear propina y validar
    //cambio de cálculo

    @Test
    fun testRoundUpFunctionality() {
        composeRule.setContent {
            TesteableAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TipCalculatorScreen()
                }
            }
        }

        // Introducir un monto de cuenta que genere una propina con decimales
        composeRule.onNodeWithText("Monto de la cuenta").performTextClearance()
        composeRule.onNodeWithText("Monto de la cuenta").performTextInput("99")

        // Verificar el cálculo de propina sin redondeo (15% de 99 = 14.85)
        composeRule.onNodeWithText("Propina: $14.85").assertExists()

        // Activar la opción de redondeo
        composeRule.onNodeWithTag("roundUpCheckbox").performClick()

        // Verificar que la propina ahora está redondeada hacia arriba (14.85 -> 15.00)
        composeRule.onNodeWithText("Propina: $15.00").assertExists()

        // Verificar que el total por persona también se ha actualizado correctamente
        composeRule.onNodeWithText("Total por persona: $114.00").assertExists()
    }

//    Test 2
//    Cambiar slider del porcentaje de
//    propina y verificar cálculo

    @Test
    fun calculateTipWithSlider() {
        composeRule.setContent {
            TesteableAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TipCalculatorScreen()
                }
            }
        }

        //Limpiamos campo
        composeRule.onNodeWithText("Monto de la cuenta").performTextClearance()
        composeRule.onNodeWithText("Monto de la cuenta").performTextInput("100")

        composeRule.onNodeWithText("Porcentaje de propina: 15%").assertExists()

//        composeRule.onNodeWithTag("slider").performTouchInput {
//            swipe(start = centerLeft, end = centerRight, durationMillis = 300) No funcionó esta implementación
//        }


        // Modificamos el Slider para establecer el 25%
        composeRule.onNodeWithTag("slider").performTouchInput {
            // Desliza desde el centro actual hasta una posición que represente el 30%
            swipe(
                start = center,
                end = Offset(center.x * 1.25f, center.y),
                durationMillis = 1000
            )
        }

        // Verificamos que el texto del porcentaje se actualice
        composeRule.onNodeWithText("Porcentaje de propina: 30%").assertExists()

        // Para $100 de propina con 25%, esperamos 25.00
        composeRule.onNodeWithText("Propina: $30.00")
            .assertExists("La propina calculada no coincide con el valor esperado")
    }

//    Test 3
//    Validar presencia de elementos UI:
//    monto, porcentaje, número de
//    personas

    @Test
    fun validateUIElementsExist() {
        // Configurar el entorno de prueba
        composeRule.setContent {
            TesteableAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TipCalculatorScreen()
                }
            }
        }

        // Verificar que el campo de monto de cuenta existe y es visible
        composeRule.onNodeWithText("Monto de la cuenta")
            .assertExists()
            .assertIsDisplayed()

        // Verificar que el porcentaje de propina existe y es visible
        composeRule.onNodeWithText("Porcentaje de propina: 15%")
            .assertExists()
            .assertIsDisplayed()

        // Verificar que el slider existe y es visible
        composeRule.onNodeWithTag("slider")
            .assertExists()
            .assertIsDisplayed()

        // Verificar que el número de personas existe y es visible
        composeRule.onNodeWithText("Número de personas: 1")
            .assertExists()
            .assertIsDisplayed()
    }

//    Test adicional 1
//    Validar que el total por persona sea correcto al cambiar valores entre 1 y 3
    @Test
    fun testPersonCountAdjustment() {
        composeRule.setContent {
            TesteableAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TipCalculatorScreen()
                }
            }
        }

        // Introducir un monto de cuenta
        composeRule.onNodeWithText("Monto de la cuenta").performTextClearance()
        composeRule.onNodeWithText("Monto de la cuenta").performTextInput("100")

        // Verificar el número inicial de personas y el total por persona
        composeRule.onNodeWithText("Número de personas: 1").assertExists()
        composeRule.onNodeWithText("Total por persona: $115.00").assertExists()

        // Incrementar el número de personas pulsando el botón +
        composeRule.onNodeWithTag("addPeopleButton").performClick()

        // Verificar que el número de personas aumentó a 2
        composeRule.onNodeWithText("Número de personas: 2").assertExists()

        // Verificar que el total por persona se ha actualizado correctamente (115 / 2 = 57.50)
        composeRule.onNodeWithText("Total por persona: $57.50").assertExists("Propina repartida erroneamente entre 2 personas")

        // Incrementar el número de personas otra vez
        composeRule.onNodeWithTag("addPeopleButton").performClick()

        // Verificar que el número de personas aumentó a 3
        composeRule.onNodeWithText("Número de personas: 3").assertExists()

        // Verificar que el total por persona se ha actualizado correctamente (115 / 3 = 38.33)
        composeRule.onNodeWithText("Total por persona: $38.33").assertExists("Propina repartida erroneamente entre 3 personas")

        // Decrementar el número de personas pulsando el botón -
        composeRule.onNodeWithTag("subtractPeopleButton").performClick()

        // Verificar que el número de personas disminuyó a 2
        composeRule.onNodeWithText("Número de personas: 2").assertExists()

        // Verificar que el total por persona se ha actualizado correctamente
        composeRule.onNodeWithText("Total por persona: $57.50").assertExists("Propina repartida no corresponde a 2 personas")
    }

//    Test 2 adicional
//
//    Verifique si el campo de monto de la cuenta maneja correctamente entradas no válidas
    @Test
    fun testInvalidBillAmountHandling() {
        composeRule.setContent {
            TesteableAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TipCalculatorScreen()
                }
            }
        }

        // Prueba con monto negativo
        composeRule.onNodeWithText("Monto de la cuenta").performTextClearance()
        composeRule.onNodeWithText("Monto de la cuenta").performTextInput("-50")

        // Verificar que la propina y el total por persona sean $0.00
        composeRule.onNodeWithText("Propina: $0.00").assertExists("La propina no se muestra correctamente para un monto negativo")
        composeRule.onNodeWithText("Total por persona: $0.00").assertExists("El total por persona no se muestra correctamente para un monto negativo")

        // Limpiar el campo y probar con una cadena vacía
        composeRule.onNodeWithText("Monto de la cuenta").performTextClearance()
        composeRule.onNodeWithText("Monto de la cuenta").performTextInput("")

        // Verificar que la propina y el total por persona sean $0.00 para una cadena vacía
        composeRule.onNodeWithText("Propina: $0.00").assertExists("La propina no se muestra correctamente para una cadena vacía")
        composeRule.onNodeWithText("Total por persona: $0.00").assertExists("El total por persona no se muestra correctamente para una cadena vacía")
    }

}
