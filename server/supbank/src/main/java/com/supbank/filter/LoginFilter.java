package com.supbank.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(filterName = "LoginFilter",urlPatterns = {"/homePage/search", "/homePage/getLastTransaction", "/block/getBlockById", "/block/longestLegalChain", "/transaction/*"})
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String requestUrl = request.getRequestURI();


        System.out.println(requestUrl);


            if(null==request.getSession().getAttribute("username")||("").equals(request.getSession().getAttribute("username"))){
                //返回未登录
                request.getRequestDispatcher("/failed/expired").forward(request, response);
                return;

            }else {
                chain.doFilter(request, response);
                return;
            }










    }

    public void init(FilterConfig config) throws ServletException {

    }

}
