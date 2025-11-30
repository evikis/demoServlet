package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

@WebFilter("/*")
public class LoggingFilter implements Filter {
    private static final Logger logger = Logger.getLogger(LoggingFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("LoggingFilter инициализирован");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String clientIP = getClientIP(httpRequest);
        String requestURL = httpRequest.getRequestURL().toString();
        String method = httpRequest.getMethod();
        Date timestamp = new Date();

        // Логирование информации о запросе
        String logMessage = String.format(
                "Время: %s | IP: %s | Метод: %s | URL: %s",
                timestamp, clientIP, method, requestURL
        );

        logger.info(logMessage);

        chain.doFilter(request, response);
    }

    private String getClientIP(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0];
        }
        return request.getRemoteAddr();
    }

    @Override
    public void destroy() {
        logger.info("LoggingFilter уничтожен");
    }
}