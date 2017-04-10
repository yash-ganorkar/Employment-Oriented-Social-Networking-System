/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.yganorkar.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Yash
 */
@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    private static Logger LOGGER = Logger.getLogger(LogoutServlet.class.getName());

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet LogoutServlet</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Servlet LogoutServlet at " + request.getContextPath() + "</h1>");

                if (request.isUserInRole("COM_EMP")) {
                    out.println("<a href=\"" + request.getContextPath() + "/company\">Login Again</a>");
                } else if (request.isUserInRole("APP_USER")) {
                    out.println("<a href=\"" + request.getContextPath() + "/userprofile\">Login Again</a>");
                }

                request.logout();
                out.println("</body>");
                out.println("</html>");
            }
        } catch (ServletException servletException) {
            LOGGER.log(Level.SEVERE, servletException.getMessage());
        } catch (IOException ioException) {
            LOGGER.log(Level.SEVERE, ioException.getMessage());
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try{
        processRequest(request, response);
        }catch(ServletException | IOException servletException){
            LOGGER.log(Level.SEVERE, servletException.getMessage());
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try{
        processRequest(request, response);
        }catch(ServletException | IOException servletException){
            LOGGER.log(Level.SEVERE, servletException.getMessage());
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
