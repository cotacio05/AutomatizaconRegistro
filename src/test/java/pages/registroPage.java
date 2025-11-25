package pages;

public class registroPage extends basePage {

    // Localizadores
    private String txtNombre = "//input[@id='FirstName']";
    private String txtApellido = "//input[@id='LastName']";
    private String txtCorreo = "//input[@id='Email']";
    private String txtpassword = "//input[@id='Password']";
    private String txtconfirmar = "//input[@id='ConfirmPassword']";
    private String btnRegistrar ="//input[@id='register-button']";
    private String textmensajeSistema ="//div[@class='result']";
    private String txtFormatoCorreo = "//span[@data-valmsg-for='Email']/span[@for='Email' and normalize-space(text())='Wrong email']";
    private String txtContraseñas = "//span[@for='ConfirmPassword' and normalize-space(text())='The password and confirmation password do not match.']";


    public registroPage(){
        super();
    }

    //Metodo para navegar en la pagina
    public void ingresarPagina(){
        navegacionA("https://demowebshop.tricentis.com/register");
    }

    //Metodo para seleccionar el genero
    public void seleccionarGenero(String label){
        String genero = "//label[text()='" + label + "']";
        clickElemento(genero);
    }

    //Metodo para ingresar Nombre
    public void ingresarNombre(String nombre){
        escribir(txtNombre, nombre);
    }

    //Metodo para ingresar Apellido
    public void ingresarApellido(String apellido){
        escribir(txtApellido, apellido);
    }

    //Metodo para ingresar correo
    public void ingresarCorreo(String correo){
        escribir(txtCorreo, correo);
    }

    //Metodo para ingresar contraseña
    public void ingresarPassword(String password){
        escribir(txtpassword, password);
    }

    //Metodo para confirmar contraseña
    public void ingresraConfirmacionPassword(String confirmar){
        escribir(txtconfirmar, confirmar);

    }

    //Metodo para dar clic en registrar
    public void darClicRegistrar(){
        clickElemento(btnRegistrar);
    }

    //Metodo para verificar registro exitoso
    public void mensajeExitoso(String mensajeEsperado ){
        verificarMensaje(textmensajeSistema,mensajeEsperado);
    }

    //Metodo para validar mensaje formato correo
    public void mensajeFormatoCorreo(String mensajeFormatoCorreo){
        verificarMensaje(txtFormatoCorreo, mensajeFormatoCorreo);

    }

    //Metodo para validar mensaje error en contraseña no coinciden
    public void mesajeNoCoinciden(String mensajeContraseñas){
        verificarMensaje(txtContraseñas, mensajeContraseñas);

    }
}
