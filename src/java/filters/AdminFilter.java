/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import businesslogic.UserService;
import domainmodel.User;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 715583
 */
public class AdminFilter implements Filter {
    
    private FilterConfig filterConfig = null;
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        // this code executes before the servlet
        // ...
        
        // ensure user is authenticated
        HttpSession session = ((HttpServletRequest)request).getSession();
        String username = (String) session.getAttribute("username");
        UserService us =new UserService();
        User user=null;
        try {
            user = us.get(username);
        } catch (Exception ex) {
            Logger.getLogger(AdminFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (user != null && user.getRole().getRoleID()==1) {
            // yes, go onwards to the servlet or next filter
            chain.doFilter(request, response);
        } else {
            // get out of here!
            ((HttpServletResponse)response).sendRedirect("login");
        }
        
       // this code executes after the servlet
       // ...
            
    }

    @Override
    public void destroy() {        
    }

    @Override
    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
    }

    
}
