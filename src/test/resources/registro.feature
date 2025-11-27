Feature: Verificación de Formulario de Registro de Usuarios

    Scenario Outline: Registro exitoso de usuario con datos válidos
        Given que el usuario está en la página de registro
        When el usuario hace clic en el radio "<Genero>"
        And el usuario ingresa su nombre "<Nombre>"
        And el usuario ingresa su apellido "<Apellido>"
        And el usuario ingresa el correo "<Correo>"
        And ingresa la contraseña "<Contrasena>"
        And confirma la contraseña "<Confirmacion>"
        And hace clic en el botón Registrar
        Then debería ver un mensaje de éxito "Your registration completed"

        Examples: 
            | Genero | Nombre | Apellido | Correo | Contrasena | Confirmacion |
            | Male | Juan | Perez | juan.perez35@test.com | Segura123! | Segura123! |
            | Female | Ana | Gómez | ana.gomez35@corp.net | Fuerte456$ | Fuerte456$ |


    Scenario: Fallo al registrar con correos 
        Given que el usuario está en la página de registro
        When el usuario hace clic en el radio "Female"
        And el usuario ingresa su nombre "Maria"
        And el usuario ingresa su apellido "Lopez"
        And el usuario ingresa el correo "correo-sin-arroba.com"
        And ingresa la contraseña "Pass1234"
        And confirma la contraseña "Pass1234"
        And hace clic en el botón Registrar
        Then debería ver un error de formato en el correo "Wrong email"


    Scenario: Fallo cuando las contraseñas no coinciden
        Given que el usuario está en la página de registro
        When el usuario hace clic en el radio "Male"
        And el usuario ingresa su nombre "Carlos"
        And el usuario ingresa su apellido "Ruiz"
        And el usuario ingresa el correo "carlos1@test.com"
        And ingresa la contraseña "Clave123"
        And confirma la contraseña "ClaveDiferente"
        And hace clic en el botón Registrar
        Then debería ver un error de coincidencia "The password and confirmation password do not match."





    