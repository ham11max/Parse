package Controller;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONValue;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Комп2 on 15.09.2015.
 */
@WebServlet("/json")
public class ParseWithJson extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String link = "http://servis.e-otg.com/wp-json/posts?filter[name]=%D0%B1%D0%BB%D0%BE%D0%B33";
        BufferedReader reader = null;
        try {
            URL url = new URL(link);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            String result = buffer.toString();
            JSONArray array = new JSONArray(result);
            JSONObject object = array.getJSONObject(0);
            request.setAttribute("name", object.get("content"));

            request.getRequestDispatcher("parser.jsp").forward(request, response);
        }catch (JSONException e){

        }finally {
            if (reader != null)
                reader.close();
        }


    }
}
