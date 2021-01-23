package edu.upc.dsa.app_proyecto;

import java.util.List;

import Models.Objetos;
import Models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface UserService {

    @POST("usuario/adduser/")
    Call<User> addUser(@Body User u);
    @POST("usuario/logginuser/")
    Call<User> logginUser(@Body User u);
    @GET("usuario/gettuser/{id}")
    Call<User> gettuser(@Path("id") String id);

    @GET("Objetos/getobjetos")
    Call<List<Objetos>> getobjetos();


    @POST("objetos/addObjeto")
    Call<Objetos> addObjeto(@Body Objetos obj);
    @GET("objetos/{objectName}")
    Call<Objetos> getobjeto(@Body Objetos obj);
    @GET("objetos/{objectName}/{nameUser}")
    Call<Objetos> getobjetoUser(@Body Objetos obj);

}
