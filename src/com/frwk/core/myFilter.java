package com.frwk.core;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.frwk.kit.UrlKit;


/**
 * Servlet Filter implementation class myFilter
 */
//@WebFilter("/myFilter")
public class myFilter implements Filter {
	private static Logger logger = Logger.getLogger(myFilter.class);

	
	private myConfig config;
	
    /**
     * Default constructor. 
     */
    public myFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		logger.info("***************filter....");
		
		try {

			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;		
			
			req.setCharacterEncoding(config.getEncoding());
			resp.setCharacterEncoding(config.getEncoding());
			resp.setContentType("text/html;"+config.getEncoding());
			
			String uri = req.getRequestURI();
			
			if(uri.indexOf(".") == -1) {
			
				String context = req.getContextPath();
				String result[] = UrlKit.getUriTail(uri, context);
				Class controller = config.routes.get(result[0]);
				if(controller != null) {
					config.handel(result[1], controller, req, resp);
				}
			} else {
			// pass the request along the filter chain
			  chain.doFilter(request, response);
			}
		} catch(Exception ex) {
			logger.error(ex.getMessage());
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		
		String configClassName = fConfig.getInitParameter("config");
		try {
			config = (myConfig)Class.forName(configClassName).newInstance();
			config.init();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
