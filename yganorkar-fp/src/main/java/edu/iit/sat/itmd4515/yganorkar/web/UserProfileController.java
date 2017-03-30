/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.yganorkar.web;

import edu.iit.sat.itmd4515.yganorkar.domain.UserProfile;
import edu.iit.sat.itmd4515.yganorkar.ejb.UserProfileService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

/**
 *
 * @author Yash
 */
@WebServlet(name = "UserProfileController", urlPatterns = {"/userprofile"})
public class UserProfileController extends HttpServlet {
    
    @EJB
    private UserProfileService userService;
    
    private static Logger LOGGER = Logger.getLogger(UserProfileController.class.getName());
    
    @Resource
    private Validator validator; 

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if(request.isUserInRole("APP_USER")){
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UserProfileController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserProfileController at " + request.getContextPath() + "</h1>");
            
            
                UserProfile userProfile = userService.findByUsername(request.getRemoteUser());
                
                out.println("<ul>");
                out.println("<li> First Name: "+ userProfile.getFirstName() +" </li>");
                out.println("<li> Last Name: "+ userProfile.getLastName() +" </li>");
                out.println("<li> Email id: "+ userProfile.getEmail() +" </li>");
                out.println("<li> Street Address: "+ userProfile.getStreetAddress() +" </li>");
                out.println("<li> City: "+ userProfile.getCity() +" </li>");
                out.println("<li> State: "+ userProfile.getState() +" </li>");
                out.println("<li> Country: "+ userProfile.getCountry() +" </li>");                
                out.println("<li> Created At: "+ userProfile.getCreatedAt() +" </li>");                
                out.println("</ul>");
                
                out.println("<a href=\"" +request.getContextPath()+"/logout\">Logout</a>");            
                out.println("</body>");
                out.println("</html>");

            }   
            
            else{
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.html");
                requestDispatcher.forward(request, response);

            }
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
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String streetAddress = request.getParameter("streetAddress");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String country = request.getParameter("country");
        Integer zip = Integer.parseInt(request.getParameter("zip"));
        
        UserProfile newUser = new UserProfile(email, firstName, lastName, new GregorianCalendar(2017,1,7).getTime(), streetAddress, city, state, country,zip);

        Set<ConstraintViolation<UserProfile>> violations = validator.validate(newUser);
        
        if(violations.isEmpty()){
            LOGGER.log(Level.SEVERE, firstName + "" + lastName + "" + email);
            
            request.setAttribute("user", newUser);
            
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/confirmation.jsp");
            requestDispatcher.forward(request, response);

            
        }
        else{
            StringBuilder errorStringBuilder = new StringBuilder();
            for(ConstraintViolation<UserProfile> constraintViolation : violations){
            LOGGER.log(Level.SEVERE,constraintViolation.getMessage().toUpperCase());
            errorStringBuilder.append(constraintViolation.getMessage().toUpperCase());
            errorStringBuilder.append("  ");
            }
            request.setAttribute("error", errorStringBuilder);
            
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/error.jsp");
            requestDispatcher.forward(request, response);
        }
        } catch (ServletException | IOException | NumberFormatException servletException) {
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
