package com.clubnautico.serviceImpl;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.clubnautico.services.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {

	@Value("${jwt.secret.key}") // tomara el valor de este campo en el application.properties
	private String secretKey; // nos permite firmar el token.

	@Value("${jwt.time.expiration}") // tomara el valor de este campo en el application.properties
	private String timeExpiration; // tiempo de duracion del token (en segs)

	@Value("${jwt.time.expiration.refresh}") // tomara el valor de este campo en el application.properties
	private String timeExpirationRefresh; // tiempo de duracion del token (en segs)
	
	

	public String generateToken(UserDetails userDetails) {

		long nowMillis = System.currentTimeMillis();
		long expMillis = nowMillis + Long.parseLong(timeExpiration) * 1000;
		Date dateExp = new Date(expMillis);

		return Jwts.builder()
				// aqui se envia el sujeto q genera el token. “sub” es una clave reservada en la carga útil de JWT q
				// se utiliza para el sujeto del token
				.claim("sub", userDetails.getUsername())

				// fecha de creacion del token
				.claim("iat", new Date(nowMillis)) //'iat' es la clave para la fecha de expiración

				// cuando va a expirar el token. Es la fecha de creacion + el timeExpiration (al ser string, hay
				// parsearlo a long)
				.claim("exp", dateExp) // 'exp' es la clave para la fecha de expiración
				.signWith(getSignatureKey()) // firmar el token
				.compact();

		// Nota: El algoritmo HS256 es el único compatible con hmacShaKeyFor del método getSignatureKey
	}

	
	public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {

		long nowMillis = System.currentTimeMillis();
		long expMillis = nowMillis + Long.parseLong(timeExpirationRefresh) * 1000;
		Date dateExp = new Date(expMillis);

		return Jwts.builder().claims(extraClaims).claim("sub", userDetails.getUsername())
				.claim("iat", new Date(nowMillis)).claim("exp", dateExp).signWith(getSignatureKey()).compact();

	}

	// Obtener el username del token
	public String getUsernameFromToken(String token) {
		return getClaim(token, Claims::getSubject);
	}
	

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		boolean res = username.equals(userDetails.getUsername()) && !isTokenExpired(token);
		return res;

	}

	// Obtener un solo claim
	// <T> T: Aquí, <T> es un parámetro de tipo. Estos parámetros permiten q los  métodos y las clases sean genéricos
	// Es decir, q puedan trabajar con diferentes tipos d datos. Esto significa q este método devolverá un objeto 
	// d tipo T, dond T puede ser cualquier tipo d objeto(como String, etc.), dependiendo d cómo se llame al método

	// Function<Claims, T> es una interfaz funcional de Java que representa una función que toma un argumento de tipo
	// Claims y devuelve un valor de tipo T. El método apply de esta interfaz se utiliza para aplicar la función a
	// las claims extraídas del token
	private <T> T getClaim(String token, Function<Claims, T> claimsFunction) {
		Claims claims = extractAllClaims(token);
		return claimsFunction.apply(claims);
	}
	
	

	// Método para obtener la clave de firma del token.
	private SecretKey getSignatureKey() {

		// Primero decodificamos la clave secreta de Base64. Luego, generamos una clave HMAC usando SHA-256 (HS256)
		// que se pasará a signWith para firmar el token
		byte[] keyBytes = Decoders.BASE64.decode(secretKey); // 1ro
		return Keys.hmacShaKeyFor(keyBytes); // 2do
	}
	
	

	// Obtener todos los claims del token. De las 3 partes q forman el token ->
	// (header:algoritmo de encriptacion y tipo de token)
	// (Payload: datos; cuerpo del token. Usuario q lo genera(sub), fecha(iat) ... (Cada uno d estos valores es un CLAIM)
	// (Verify Signature: la firma del token)
	private Claims extractAllClaims(String token) {
		SecretKey key = getSignatureKey();

		return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
	}
	
	

	private boolean isTokenExpired(String token) {
		boolean res = getClaim(token, Claims::getExpiration).before(new Date());
		return res;
	}

}
