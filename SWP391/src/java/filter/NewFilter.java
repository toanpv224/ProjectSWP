/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import dal.AccountDAO;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;

/**
 *
 * @author tretr
 */
public class NewFilter implements Filter {

    private HttpServletRequest httpRequest;
    private HttpServletResponse httpResponse;
    AccountDAO dao = new AccountDAO();

//    private static final String[] loginRequiredURLs = {
//            "/view_profile", "/edit_profile", "/update_profile"
//    };
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        httpRequest = (HttpServletRequest) request;

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        HttpSession session = httpRequest.getSession(false);

        boolean isLoggedIn = (session != null && session.getAttribute("account") != null);

        String loginURI = httpRequest.getContextPath() + "/home";
        boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);
        boolean isLoginPage = httpRequest.getRequestURI().endsWith("home");

        if (!isLoggedIn && (isLoginRequest || isLoginPage)) {
            // the user is already logged in and he's trying to login again
            // then forward to the homepage
            httpRequest.getRequestDispatcher("/").forward(request, response);

        } else if (isLoginRequired() == true) {
            // or the user is already logged in and gat authentication continue
            chain.doFilter(request, response);
        } else {
            request.getRequestDispatcher("accessdenied").forward(request, response);
        }
    }

    // Check if the user is authorized
    private boolean isLoginRequired() {
        String requestURL = httpRequest.getRequestURL() + "";
        Account a = (Account) httpRequest.getSession().getAttribute("account");
        List<String> loginRequiredURLs = new ArrayList<>();
        if (a == null) {
            loginRequiredURLs = dao.getListScreen(6);
        } else {
            loginRequiredURLs = dao.getListScreen(a.getRole_id());
        }
        for (String loginRequiredURL : loginRequiredURLs) {
            if (requestURL.contains(loginRequiredURL)) {
                return true;
            }
        }

        return false;
    }

    public void destroy() {
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }
}
