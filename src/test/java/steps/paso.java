package steps;

import io.cucumber.java.en.*;
import pages.registroPage;

public class paso {

    registroPage main = new registroPage();

    @Given("que el usuario está en la página de registro")
    public void yoIngresoFormulario(){
        main.ingresarPagina();
    }

    @When("el usuario hace clic en el radio {string}")
    public void YoSeleccionoGenero(String label){
        main.seleccionarGenero(label);
    }

    @And("el usuario ingresa su nombre {string}")
    public void yoEscriboNombre(String nombre){
        main.ingresarNombre(nombre);
    }

    @And("el usuario ingresa su apellido {string}")
    public void yoEscriboApellido(String apellido){
        main.ingresarApellido(apellido);
    }

    @And("el usuario ingresa el correo {string}")
    public void yoIngresoCorreo(String correo){
        main.ingresarCorreo(correo);
    }

    @And("ingresa la contraseña {string}")
    public void yoIngresoPassword(String password){
        main.ingresarPassword(password);
    }

    @And("confirma la contraseña {string}")
    public void yoIngresoConfirmacionPassword(String confirmacion){
        main.ingresraConfirmacionPassword(confirmacion);
    }

    @And("hace clic en el botón Registrar")
    public void yoDoyClicRegistrar(){
        main.darClicRegistrar();
    }

    @Then ("debería ver un mensaje de éxito {string}")
    public void mensajeExitoso(String mensajeEsperado){
        main.mensajeExitoso(mensajeEsperado);
    }

    @Then("debería ver un error de formato en el correo {string}")
    public void  mensajeFormatoCorreo(String mesajeCorreo){
       main.mensajeFormatoCorreo(mesajeCorreo);
    }

    @Then("debería ver un error de coincidencia {string}")
    public void mensajeNoCoincidePassword(String contraseñas){
        main.mesajeNoCoinciden(contraseñas);
    }
   
}
