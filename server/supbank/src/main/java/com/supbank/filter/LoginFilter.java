package com.supbank.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter",urlPatterns = "/*")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String requestUrl = request.getRequestURI();
        if(requestUrl.indexOf("user/")==-1) {

            if(requestUrl.indexOf("wallet/") > -1){
                chain.doFilter(request, response);
                return;
            }

            if(null==request.getSession().getAttribute("username")||("").equals(request.getSession().getAttribute("username"))){
                //返回未登录
                request.getRequestDispatcher("/failed").forward(request,response);
                return;
            }
        }



        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
