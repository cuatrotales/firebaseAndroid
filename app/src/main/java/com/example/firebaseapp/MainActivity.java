package com.example.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String codigo, nombre, ciudad, cantidad;
    EditText jetcodigo, jetnombre, jetciudad, jetcantidad;
    CheckBox jcbactivo;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jetcantidad=findViewById(R.id.etcantidad);
        jetciudad=findViewById(R.id.etciudad);
        jetcodigo=findViewById(R.id.etcodigo);
        jetnombre=findViewById(R.id.etnombre);
        jcbactivo=findViewById(R.id.cbactivo);
    }

    public void Adicionar(View v){
        codigo=jetcodigo.getText().toString();
        nombre=jetnombre.getText().toString();
        ciudad=jetciudad.getText().toString();
        cantidad=jetcantidad.getText().toString();
        if (codigo.isEmpty() || nombre.isEmpty() || ciudad.isEmpty() || cantidad.isEmpty()){
            Toast.makeText(this, "Todos los datos son requeridos", Toast.LENGTH_SHORT).show();
            jetcodigo.requestFocus();
        }else {
            // Create a new user with a first and last name
            Map<String, Object> user = new HashMap<>();
            user.put("codigo", codigo);
            user.put("nombre", nombre);
            user.put("ciudad", ciudad);
            user.put("cantidad", cantidad);
            user.put("activo", "si");

            // Add a new document with a generated ID
            db.collection("factura")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(MainActivity.this, "Datos guardados", Toast.LENGTH_SHORT).show();
                            LimpiarCampos();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Error guardando datos", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    private void LimpiarCampos(){
        jetcodigo.setText("");
        jetnombre.setText("");
        jetciudad.setText("");
        jetcantidad.setText("");
        jetcodigo.requestFocus();
    }
}