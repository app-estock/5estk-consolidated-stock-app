package com.market.stock.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.filter.GenericFilterBean;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Base64;

public class JwtFilterConfig extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        final HttpServletRequest request =  (HttpServletRequest)req;
        final HttpServletResponse response = (HttpServletResponse) res;
        final String authHeader = request.getHeader("auth_token");
        System.out.println("OPTIONS".equals(request.getMethod()));

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(req, res);
        } else {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new ServletException("Missing or invalid Authorization header");
            }

            final String token = authHeader.substring(7);

            String secret = "1231231231412aisdjasndskfniwn21312312312143423sdasdrcwqeqwdsadd23123";

            Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());
            final Claims claims = Jwts.parserBuilder().setSigningKey(hmacKey).build().parseClaimsJws(token).getBody();

            request.setAttribute("claims", claims);
            chain.doFilter(req, res);

        }

    }
}
