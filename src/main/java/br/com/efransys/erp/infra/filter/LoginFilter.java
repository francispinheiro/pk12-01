package br.com.efransys.erp.infra.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.efransys.erp.bean.geral.LogonMB;

import java.io.IOException;



public class LoginFilter implements Filter {

    public void destroy() {
        // TODO Auto-generated method stub

    }

    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        //Captura o ManagedBean chamado â€œusuarioMBâ€�
        LogonMB logonMB = (LogonMB)
                ((HttpServletRequest) request)
                        .getSession().getAttribute("logonMB");

        //Verifica se nosso ManagedBean ainda nÃ£o
        //foi instanciado ou caso a
        //variÃ¡vel loggedIn seja false, assim saberemos que
        // o usuÃ¡rio nÃ£o estÃ¡ logado
       
    }

    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }

}
