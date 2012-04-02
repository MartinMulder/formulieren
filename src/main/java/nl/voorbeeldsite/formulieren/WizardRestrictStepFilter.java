package nl.voorbeeldsite.formulieren;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WizardRestrictStepFilter implements Filter {

    private static final Logger logger = Logger.getLogger(WizardRestrictStepFilter.class);
    private static final String RESOURCE_DIR = "javax.faces.resource";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        Wizard wizard = Wizard.makeWizard((HttpServletRequest) request);
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        String servletPath = httpServletRequest.getServletPath();

        if (shouldRedirect(servletPath, wizard)) {
            redirect(httpServletRequest.getContextPath() + wizard.getCurrentViewPrettyUrl(), response);
        } else {
            chain.doFilter(request, response);
        }
    }

    private void redirect(String url, ServletResponse response) throws IOException {
        ((HttpServletResponse) response).sendRedirect(url);
    }

    private boolean shouldRedirect(String servletPath, Wizard wizard) {
        String currentViewPath = "/" + wizard.getCurrentViewId();

        if (servletPath.contains(RESOURCE_DIR)) {
            return false;
        }
        return !currentViewPath.equals(servletPath);
    }

    @Override
    public void destroy() {

    }

}
