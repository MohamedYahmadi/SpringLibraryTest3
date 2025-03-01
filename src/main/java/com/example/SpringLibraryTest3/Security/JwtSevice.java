package com.example.SpringLibraryTest3.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
public class JwtSevice {
    private final String key ="EeHa13yNI06enJKg/jSIyCHIQeb10ttSCVmTfi0A7UOce9ExMmTkLjG6ua4D3DZoqo2hc0e6k/Pv3JRnZsy36BCBrXw525FHNmUmBcga7jwPdGEmBZgiG5T+Rc1C15f08XrUwvLvU15Ds2PQTUvFn2LedGSaM7lcap4NFdrgnb8yjFe/M5+fPnLXx+gBjVFRmAL+q90UwgmKqS7JD9qW2bDSkvOktePUxsHf7t6+ysmbl2XxYKrL1pqbPqtzB5j53kyWNcQYbU4h4llC8UTnk77LTZlNy7mlx7brLxp4OcxbvaYh00AGR9AWJEYRUYT1nPr0gN3iDngtdtS/Bhjofq6bR9x1OTEsUAyrtNTTXQE=\n";


    public String createToken(String email){
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(email)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,key)
                .compact();
    }

    public String extractEmail(String token){
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

    }
    public Boolean validateToken(String token, String email){return email.equals(extractEmail(token));
    }
}
