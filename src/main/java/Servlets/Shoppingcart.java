/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Connector.DataAccessObject;
import Constructors.Bottom;
import Constructors.LineItem;
import Constructors.Topping;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author adams
 */
@WebServlet(name = "Shoppingcart", urlPatterns =
{
    "/Shoppingcart"
})
public class Shoppingcart extends HttpServlet
{

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
            throws ServletException, IOException, Exception
    {
        try (PrintWriter out = response.getWriter())
        {
            DataAccessObject DAO = new DataAccessObject();
            List<Bottom> bottomList = DAO.getBottoms();
            List<Topping> toppingList = DAO.getToppings();
            request.getSession().setAttribute("bottomList", bottomList);
            request.getSession().setAttribute("toppingList", toppingList);
            ArrayList<LineItem> itemList = (ArrayList<LineItem>) request.getSession().getAttribute("itemList");
            if (itemList == null)
            {
                itemList = new ArrayList();
                request.getSession().setAttribute("itemList", itemList);
            }

            response.setContentType("text/html;charset=UTF-8");
            if (request.getParameter("add") != null)
            {
                String top = request.getParameter("Toppings");
                String bot = request.getParameter("Bottoms");
                Topping selectTop = DAO.getTopping(top);
                Bottom selectBot = DAO.getBottom(bot);
                int totalPrice = (selectTop.getPrice() + selectBot.getPrice());
                LineItem item = new LineItem(0, selectTop.getName() + " " + selectBot.getName(), 0, totalPrice, selectBot.getId(), selectTop.getId(), 0);
                itemList.add(item);
            }
            if (request.getParameter("Cancel") != null)
            {
                itemList.clear();
            }
            request.getSession().setAttribute("itemList", itemList);
            String nextJSP = "/Shop.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            dispatcher.forward(request, response);

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        try
        {
            processRequest(request, response);
        } catch (Exception ex)
        {
            Logger.getLogger(Shoppingcart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        try
        {
            processRequest(request, response);
        } catch (Exception ex)
        {
            Logger.getLogger(Shoppingcart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

}
