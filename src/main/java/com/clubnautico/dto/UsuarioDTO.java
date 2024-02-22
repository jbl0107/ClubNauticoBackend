package com.clubnautico.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class UsuarioDTO {
	
	
	@NotNull(message = "El username no puede ser nulo")
	@NotEmpty(message = "Username requerido")
	@NotBlank(message = "El username no puede estar en blanco")
	@Size(min = 2, max = 20, message = "El nombre de usuario no puede tener menos de 2 caracteres ni mas de 20")
	private String username;
	
	@NotNull(message = "La contraseña no puede ser nulo")
	@NotEmpty(message = "Contraseña requerida")
	@NotBlank(message = "La contraseña no puede estar en blanco")
	@Size(min = 8, message = " no puede tener menos de 8 caracteres")
	private String password;
	
	@NotNull(message = "El firstName no puede ser nulo")
	@NotEmpty(message = "First Name requerido")
	@NotBlank(message = "El firstName no puede estar en blanco")
	@Size(min = 2, max = 25, message = "El nombre no puede tener más de 25 caracteres ni menos de 2")
	private String firstName;
	
	@NotNull(message = "El lastName no puede ser nulo")
	@NotEmpty(message = "Last Name requerido")
	@NotBlank(message = "El lastName no puede estar en blanco")
	@Size(min = 3, max = 35, message = "Los apellidos no puede tener más de 35 caracteres ni menos de 3")
	private String lastName;
	
	@Email(message="Por favor, proporciona un email válido")
	@NotNull(message = "El email no puede ser nulo")
	@NotEmpty(message = "Email requerido")
	@NotBlank(message = "El email no puede estar en blanco")
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Formato de email inválido")
	private String email;


}
