package com.example.parcialfinal_pn17_i04_001;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parcialfinal_pn17_i04_001.BaseDatos.RespuestaDatos;
import com.example.parcialfinal_pn17_i04_001.Clases.Datos;
import com.example.parcialfinal_pn17_i04_001.Clases.DatosServices;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Principal extends AppCompatActivity {
    private Retrofit retrofit;
    private final int REQUEST_CODE_PERMISO_ESCRITURA_EXTERNA=100;
    private double valorA;
    private double valorB;
    private double valorC;
    private Toolbar toolbar;
    private TextView lblRespuesta1;
    private TextView lblRespuesta2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ImageView imgContenedor=findViewById(R.id.imgPrincipal);
        String imagen="https://e7.pngegg.com/pngimages/207/423/png-clipart-quadratic-equation-quadratic-formula-quadratic-function-formula-s-angle-text.png";
        Picasso.get().load(imagen).error(R.mipmap.ic_launcher_round).fit().centerInside().into((ImageView) imgContenedor);
        final TextView txbA=findViewById(R.id.txbA);
        final TextView txbB=findViewById(R.id.txbB);
        final TextView txbC=findViewById(R.id.txbC);
        this.lblRespuesta1=findViewById(R.id.lblRespuesta1);
        this.lblRespuesta2=findViewById(R.id.lblRespuesta2);

        Button btnCalcular=findViewById(R.id.btnCalcular);
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ValidarCampos(new TextView []{txbA,txbB,txbC})){
                    valorA=Double.parseDouble(txbA.getText().toString());
                    valorB=Double.parseDouble(txbB.getText().toString());
                    valorC=Double.parseDouble(txbC.getText().toString());
                    EnviarData();
                }
            }
        });
        //String url="https://em012020.000webhostapp.com/index.php/EFinal/Get_Parcial?codigo=pn17-i04-001";
        String url="https://em012020.000webhostapp.com/index.php/EFinal/";
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Jorge Alberto Perez");
        retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //ValidarPermisos();
        CargarDatosServicio();
    }

    private void ValidarPermisos(){
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            Toast.makeText(this,"Permisos por medio de manifest",Toast.LENGTH_SHORT).show();
        }else{
            //Verificamos si tenemos permisos de escritura
            int permisoLectura=checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permisoLectura!= PackageManager.PERMISSION_GRANTED){
                //Solicitamos el permiso al usuario
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISO_ESCRITURA_EXTERNA);
            }else{
                Toast.makeText(this,"Permisos otorgados", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void CargarDatosServicio(){
        DatosServices servicio=retrofit.create(DatosServices.class);
        Call<RespuestaDatos> respuestaDatos=servicio.Get_Parcial("pn17-i04-001");
        respuestaDatos.enqueue(new Callback<RespuestaDatos>() {
            @Override
            public void onResponse(Call<RespuestaDatos> call, Response<RespuestaDatos> response) {
                if(response.isSuccessful()){
                    RespuestaDatos respuesta=response.body();
                    ArrayList<Datos>listaDatos=respuesta.getData();
                    //CargarDatos(listaDatos);
                    //CargarDatos(listaDatos);
                }else{
                    Toast.makeText(Principal.this, "No fue posible obtener los datos: "+response.code(), Toast.LENGTH_SHORT).show();
                    //Log.d("ERR_SERVICES");
                }
            }
            @Override
            public void onFailure(Call<RespuestaDatos> call, Throwable t) {
                Toast.makeText(Principal.this,"Error al obtener el servicio",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void CargarDatos(ArrayList<Datos> listaDatos) {
        if(listaDatos.size()==1){
            List<String> libros=new ArrayList<String>();
            for(Datos dato:listaDatos){
                libros.add(dato.getDatos()+" "+dato.getDescripcion());
                Toast.makeText(Principal.this,dato.getDatos()+" "+dato.getDescripcion(),Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(Principal.this,"Vacio",Toast.LENGTH_SHORT).show();
        }
    }

    private void EnviarData(){
        final ProgressBar progressBar=findViewById(R.id.pbCargando);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            public void run() {
                int progreso = 0;
                while (progreso < 100){
                    //simular proceso
                    try{
                        Thread.sleep(40);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    progreso++;

                    //actualizar progresbar
                    final int finalProgreso = progreso;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(finalProgreso);
                        }
                    });
                }
                CalcularDatos();
            }

        }).start();
    }
    private boolean ValidarCampos(TextView lst[]){
        boolean resultado=true;
        for(TextView campo:lst){
            if(campo.getText().toString().equals("")){
                resultado=false;
                campo.setError("Campo obligatorio !");
            }
        }
        return resultado;
    }

    private void CalcularDatos(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                double potencia = Math.pow(valorB,2) - (4 * valorA *valorC);
                double x1 = (-valorB - Math.sqrt(potencia)/ 2*valorA);
                double x2=(-valorB + Math.sqrt(potencia)/ 2*valorA);
                BigDecimal bd1 = new BigDecimal(x1);//Redondeamos a dos decimales
                bd1 = bd1.setScale(2, RoundingMode.HALF_UP);
                BigDecimal bd2 = new BigDecimal(x2);//Redondeamos a dos decimales
                bd2 = bd2.setScale(2, RoundingMode.HALF_UP);
                lblRespuesta1.setText("Respuesta 1: "+Double.toString(bd1.doubleValue()));
                lblRespuesta2.setText("Respuesta 2: "+Double.toString(bd2.doubleValue()));
            }
        });
    }
}

