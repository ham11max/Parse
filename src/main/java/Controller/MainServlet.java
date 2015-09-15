package Controller;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;
import org.json.simple.JSONObject;

@WebServlet("/")
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException{
        String link1 = "http://servis.e-otg.com/wp-json/posts?filter[name]=%D0%B1%D0%BB%D0%BE%D0%B33"; //just a string
/*
        URL url = new URL(link);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();
*/

       // String link = URLEncoder.encode(link1, "UTF-8");

        // read from the URL
        BufferedReader reader = null;
        try {
            URL url = new URL(link1);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            String result = buffer.toString();

            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode taskIdsjsonNode = mapper.readTree(result);
                for (JsonNode next : taskIdsjsonNode) {
                    String res = next.get("content").toString();
                    String results =  res.substring(1,res.length()-1);
                    results = results.replaceAll("\\\\n","");
                    req.setAttribute("name", results );
                }
            } catch ( Exception e) {
                e.printStackTrace();
            }
        } finally {
            if (reader != null)
                reader.close();
        }


        req.getRequestDispatcher("home.jsp").forward(req, resp);

    }

}