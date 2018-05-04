package edu.upc;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App
{
    //public static final String API_URL = "https://api.github.com";
    public static final String API_URL = "http://localhost:8080/myapp/";

    //public static class Contributor {
    public static class Login{
        public final String username;
        //public final int contributions;
        public final String password;

        //public Contributor(String login, int contributions, String avatar_url) {
        public Login(String username, String password) {
            this.username = username;
            //this.contributions = contributions;
            this.password = password;
        }
    }

    public static class Usuario{
        public final String nombre;
        public final String password;
        public final int vida;
        public final int ataque;
        public final int defensa;
        public final int resistencia;

        public Usuario(String nombre, String password, int vida, int ataque, int defensa, int resistencia){
            this.nombre = nombre;
            this.password= password;
            this.vida= vida;
            this.ataque = ataque;
            this.defensa= defensa;
            this.resistencia=resistencia;
        }
    }
    public static class Objeto{
        public final int id;
        public final String nombre;
        public final String tipo;
        public final String descripcion;
        public final int valor;
        public final int coste;

        public Objeto( int id,String nombre, String tipo, String descripcion, int valor, int coste){
            this.nombre = nombre;
            this.descripcion= descripcion;
            this.tipo= tipo;
            this.id = id;
            this.valor= valor;
            this.coste=coste;
        }
    }

    public interface Rest {
        /*
        @GET("/repos/{owner}/{repo}/contributors")
        Call<List<Contributor>> contributors(
                @Path("owner") String owner,
                @Path("repo") String repo);
        */
    }
    public interface ServiciosRetrofit {
        @GET("json/consultarUsuarioDAO/{user}")
        Call<Usuario> consultarUsuario(@Path("user") String nombre);

        @GET("json/consultarObjetoDAO/{obj}")
        Call<Objeto> consultarObjeto (@Path("obj") int nomObj);

        @POST("/login")
        Call<String> login (@Body ArrayList<String> lista);
    }

    public static void main( String[] args ) throws IOException
    {

        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //para el login
        ArrayList<String > lista = new ArrayList<String>();
        lista.add("David");
        lista.add("xxx");
        // Create an instance of our GitHub API interface.
        //Rest github = retrofit.create(Rest.class);
        ServiciosRetrofit servicios = retrofit.create(ServiciosRetrofit.class);

        // Create a call instance for looking up Retrofit contributors.
        //Call<List<Contributor>> call = github.contributors("RouteInjector", "route-injector");
        Call<Usuario> call1 = servicios.consultarUsuario("David");
        Call<Objeto> call2 = servicios.consultarObjeto(1);
        Call<String> call3 = servicios.login(lista);

        // Fetch and print a list of the contributors to the library.
        //List<Contributor> contributors = call.execute().body();
        Usuario user = call1.execute().body();
        Objeto obj = call2.execute().body();
        String  resp1 = call3.execute().body();

        boolean resp =true;
        if(resp){
            System.out.println(user.nombre);
            System.out.println(user.password);
            System.out.println(user.ataque);
            System.out.println(user.defensa);
            System.out.println(user.resistencia);
            System.out.println("------Objeto------");
            System.out.println(obj.nombre);
            System.out.println(obj.descripcion);
            System.out.println("-------POST LOGin-------");
            System.out.println(resp1);
        }
        //for (Contributor contributor: contributors) {
        /*
        for (Follower follower : follow) {
            System.out.println(follower.login + " " + follower.avatar_url);
            //System.out.println(contributor.login + " (" + contributor.contributions + ") "+ contributor.avatar_url);
            //System.out.println(contributor.login + ( + contributor.)
        }
        */
    }
}