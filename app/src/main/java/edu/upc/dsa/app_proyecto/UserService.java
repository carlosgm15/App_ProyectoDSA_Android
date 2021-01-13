package edu.upc.dsa.app_proyecto;

import Models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface UserService {

    @POST("usuario/adduser/")
    Call<User> addUser(@Body User u);
    @POST("usuario/logginuser/")
    Call<User> logginUser(@Body User u);

}
