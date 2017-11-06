package main.java;

import main.java.reviews.Reviews;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet(urlPatterns="/reviews",name = "ReviewsServlet")
public class ReviewsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



        //Rating and Review Part
        Reviews.evaluateAverageRating();
        request.setAttribute("reviews", Reviews.reviews);
        request.setAttribute("average_rating_on_reviews", Reviews.getAverageRating());

        response.setContentType("text/html");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Reviews.jsp");
        dispatcher.forward(request, response);

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Rating and Review Part
        String textField = request.getParameter("review_text");
        String name = request.getParameter("name");
        int givenRating = Integer.parseInt(request.getParameter("star"));
        if((!Objects.equals(textField, "")) && (!Objects.equals(name, "")) && (givenRating <5 && givenRating>1)){
            Reviews.reviews.add(new Reviews.Review(name,textField,givenRating));

        }
        doGet(request, response);
    }


}
