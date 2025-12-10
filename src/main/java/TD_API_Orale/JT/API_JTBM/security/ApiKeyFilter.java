package TD_API_Orale.JT.API_JTBM.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// filtre pour vérifier la clé X-API-KEY sur les méthodes non-GET
@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    @Value("${app.api-key}")
    private String expectedApiKey;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String method = request.getMethod();

        // autorise toutes les requêtes GET sans clé
        if (HttpMethod.GET.matches(method)) {
            filterChain.doFilter(request, response);
            return;
        }

        // récupère la clé envoyée dans le header X-API-KEY
        String apiKey = request.getHeader("X-API-KEY");

        // vérifie la clé pour POST, PUT, DELETE, etc.
        if (apiKey == null || !apiKey.equals(expectedApiKey)) {
            // réponse 401 si absence ou valeur incorrecte
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("API key invalide ou absente");
            return;
        }

        // continue le filtre si la clé est correcte
        filterChain.doFilter(request, response);
    }
}
