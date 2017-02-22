package com.ironyard.filters;

import com.ironyard.security.TokenMaster;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jasonskipper on 2/21/17.
 */
@WebFilter(filterName = "RestFilter")
public class RestFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // we need to look for authorization token and validate it
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        // check for key based authentication
        boolean authorized = false;
        Long userId;
        String authToken = req.getHeader("x-authorization-key");
        TokenMaster tm = new TokenMaster();
        userId = tm.validateAndGetUserId(authToken);
        if(authToken != null && userId != null){
            // send request on its way like normal
            req.setAttribute("authToken", authToken);
            req.setAttribute("userId", userId);
            filterChain.doFilter(req, res);
        }else{
            res.getWriter().write("Invalide Authorization. No No. Go Away.");
        }

    }

    @Override
    public void destroy() {

    }
}