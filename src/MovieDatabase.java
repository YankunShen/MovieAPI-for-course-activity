import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// Java class that will host the URI path "/"
@Path("/movie")
public class MovieDatabase {

    @GET
    @Produces("text/html")
    // This method will return a simple message of type HTML
    public String displayMessage(){
        return "Air Quality app is running well";
    }

    //getting popular movies' id
    @GET
    @Path("/trending")
    @Produces("text/json")
    public String getTrending(@Context HttpServletResponse servletResponse){
        Client client = ClientBuilder.newClient();
        try{
            WebTarget myResource = client.target("http://api.themoviedb.org/3/movie/popular?api_key=4e4a72aef853a31adaf80ef8267b37f4&language=en-US&page=1");
            Response response = myResource.request().get();
            JsonObject root = response.readEntity(JsonObject.class);
            StringBuilder sb = new StringBuilder();
            JsonArray array = root.getJsonArray("results");
            for(int i=0; i<array.size(); i++){
                JsonObject a = array.getJsonObject(i);
                JsonNumber num = a.getJsonNumber("id");
                String id = num.toString();
                sb.append(id);
                sb.append('\n');
            }
            //Set header status to success
            servletResponse.setHeader("status", "success");
            return sb.toString();
        }
        catch (Exception e){
            servletResponse.setHeader("status", "failure");
            return null;
        }
    }

    //get movie detailed information based on id
    @GET
    @Path("/info/{id}")
    @Produces("text/json")
    public JsonObject getInfo(@PathParam("id")String id, @Context HttpServletResponse servletResponse){
        Client client = ClientBuilder.newClient();
        try{
            WebTarget myResource = client.target("http://api.themoviedb.org/3/movie/" +
                    id + "?api_key=4e4a72aef853a31adaf80ef8267b37f4&language=en-US");
            Response response = myResource.request().get();
            JsonObject root = response.readEntity(JsonObject.class);
            //Set header status to success
            servletResponse.setHeader("status", "success");
            return root;
        }
        catch (Exception e){
            servletResponse.setHeader("status", "failure");
            return null;
        }
    }

    //get movie's cast's id based on movie's id
    @GET
    @Path("/cast/{id}")
    @Produces("text/json")
    public String getCast(@PathParam("id")String id, @Context HttpServletResponse servletResponse){
        Client client = ClientBuilder.newClient();
        try{
            WebTarget myResource = client.target("http://api.themoviedb.org/3/movie/" +
                    id + "/credits?api_key=4e4a72aef853a31adaf80ef8267b37f4&language=en-US");
            Response response = myResource.request().get();
            JsonObject root = response.readEntity(JsonObject.class);
            StringBuilder sb = new StringBuilder();
            JsonArray array = root.getJsonArray("cast");
            for(int i=0; i<array.size(); i++){
                JsonObject a = array.getJsonObject(i);
                JsonNumber num = a.getJsonNumber("id");
                String actor_id = num.toString();
                sb.append(actor_id);
                sb.append('\n');
            }
            //Set header status to success
            servletResponse.setHeader("status", "success");
            return sb.toString();
        }
        catch (Exception e){
            servletResponse.setHeader("status", "failure");
            return null;
        }
    }

    //get actor's  information based on actor id
    @GET
    @Path("/cast/info/{id}")
    @Produces("text/json")
    public JsonObject getActor(@PathParam("id")String id, @Context HttpServletResponse servletResponse){
        Client client = ClientBuilder.newClient();
        try{
            WebTarget myResource = client.target("http://api.themoviedb.org/3/person/" +
                    id + "?api_key=4e4a72aef853a31adaf80ef8267b37f4&language=en-US");
            Response response = myResource.request().get();
            JsonObject root = response.readEntity(JsonObject.class);
            //Set header status to success
            servletResponse.setHeader("status", "success");
            return root;
        }
        catch (Exception e){
            servletResponse.setHeader("status", "failure");
            return null;
        }
    }

    //get Movie's IMDB id based on movie title
    @GET
    @Path("/id/{title}")
    @Produces("text/json")
    public String getIMDBid(@PathParam("title")String title, @Context HttpServletResponse servletResponse){
        Client client = ClientBuilder.newClient();
        try{
            WebTarget myResource = client.target("http://www.omdbapi.com/?" +
                    "t=" + title + "&apikey=f871e8f8");
            Response response = myResource.request().get();
            JsonObject root = response.readEntity(JsonObject.class);
            String imdbID = root.getString("imdbID");
            //Set header status to success
            servletResponse.setHeader("status", "success");
            return imdbID;
        }
        catch (Exception e){
            servletResponse.setHeader("status", "success");
            return null;
        }
    }

    //This method will get information of the movie based on the title
    @GET
    @Path("/{title}")
    @Produces("text/json")
    public JsonObject getAllInfo(@PathParam("title")String title, @Context HttpServletResponse servletResponse){
        Client client = ClientBuilder.newClient();
        try{
            WebTarget myResource = client.target("http://www.omdbapi.com/?" +
                    "t=" + title + "&apikey=f871e8f8");
            Response response = myResource.request().get();
            JsonObject root = response.readEntity(JsonObject.class);
            //Set header status to success
            servletResponse.setHeader("status", "success");
            return root;
        }
        catch (Exception e){
            servletResponse.setHeader("status", "failure");
            return null;
        }

    }

    //get movie's IMDB rating score based on imdb_id
    @GET
    @Path("/Ratings/{id}")
    @Produces("text/json")
    public String getScore(@PathParam("id")String id, @Context HttpServletResponse servletResponse){
        Client client = ClientBuilder.newClient();
        try{
            WebTarget myResource = client.target("http://www.omdbapi.com/?" +
                    "i=" + id + "&apikey=f871e8f8");
            Response response = myResource.request().get();
            JsonObject root = response.readEntity(JsonObject.class);
            String imdbRating = root.getString("imdbRating");
            //Set header status to success
            servletResponse.setHeader("status", "success");
            return imdbRating;
        }
        catch (Exception e){
            servletResponse.setHeader("status", "success");
            return null;
        }
    }
}
