package com.web.faces.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.pet.bean.UsuarioBean;

public class AdminPagesAccessFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		UsuarioBean usuarioBean = (UsuarioBean)((HttpServletRequest)servletRequest).getSession().getAttribute("usuarioBean");
        
        if (usuarioBean == null || !usuarioBean.isAutenticado()) {
        	//Se guarda la pagina que se quiere visitar
        	HttpServletRequest request = (HttpServletRequest)servletRequest;
        	String pagina = request.getServletPath();
        	if(pagina != null) {
        		String parametros = request.getQueryString();
	        	String urlrequested = pagina + "?faces-redirect=true" + (parametros != null ? "&" + parametros : "");
	        	request.getSession().setAttribute("urlrequested", urlrequested);
        	}
        	
        	//Si no esta logoneado redirecciona a login
            String contextPath = ((HttpServletRequest)servletRequest).getContextPath();
            ((HttpServletResponse)servletResponse).sendRedirect(contextPath + "/pages/login.jsf");
        }else{
        	filterChain.doFilter(servletRequest, servletResponse);
        }
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
