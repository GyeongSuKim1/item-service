package mvc.itemservice.web.filter;

import javax.servlet.*;
import java.io.IOException;

public interface Filter {
    public default void init(FilterConfig filterConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException;

    public default void destroy() {}
}
