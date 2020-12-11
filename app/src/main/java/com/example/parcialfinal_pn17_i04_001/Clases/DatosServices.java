package com.example.parcialfinal_pn17_i04_001.Clases;

import com.example.parcialfinal_pn17_i04_001.BaseDatos.RespuestaDatos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DatosServices {
    //...api/Libros?id=
    @GET("Get_Parcial")
    Call<RespuestaDatos> Get_Parcial(@Query("codigo") String codigo);
    //users/list"
    //Call<RespuestaDatos> getLibros(@Query("IdLibro") int IdLibro);


    /*
    @POST("AddLibros")
    Call<RespuestaDatos> AddLibros(@Field("Titulo") String Titulo,
                                    @Field("Autor") String Autor,
                                    @Field("Categoria") String Categoria,
                                    @Field("year") int Anio,
                                    @Field("Editorial") String Editorial);

     */
}
