package carlos.robert.ejemplosharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText txtNombre;
    private EditText txtEdad;
    private Button btnGuardar;
    private Button btnBorrarTodo;
    private ImageButton btnBorrarNombre;
    private ImageButton btnBorrarEdad;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarVista();

        sp = getSharedPreferences(Constantes.PERSONA, MODE_PRIVATE); //privado (sólo para esta app)

        rellenarDatos();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtNombre.getText().toString().isEmpty() || txtEdad.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Faltan DATOS", Toast.LENGTH_SHORT).show();
                } else {
                    String nombre = txtNombre.getText().toString();
                    int edad = Integer.parseInt(txtEdad.getText().toString());

                    SharedPreferences.Editor editor = sp.edit(); //editar fichero
                    editor.putString(Constantes.NOMBRE, nombre); //metemos constante nombre
                    editor.putInt(Constantes.EDAD, edad); //metemos constante edad

                    //SIEMPRE QUE HAGAMOS ALGÚN CAMBIO O MODIFICACIÓN
                    //commit deja parado el hilo de ejecución hasta que acabe (syncrono)
                    //apply se ejecuta en segundo plano (asyncrono)
                    editor.apply();
                }
            }
        });

        btnBorrarTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.apply();
                txtNombre.setText("");
                txtEdad.setText("");
            }
        });

        btnBorrarNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.remove(Constantes.NOMBRE);
                editor.apply();
                txtNombre.setText("");
            }
        });

        btnBorrarEdad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.remove(Constantes.EDAD);
                editor.apply();
                txtEdad.setText("");
            }
        });
    }

    private void rellenarDatos() {
        String nombre = sp.getString(Constantes.NOMBRE, "");
        int edad = sp.getInt(Constantes.EDAD, -1);

        if (!nombre.isEmpty()) {
            txtNombre.setText(nombre);
        }
        if (edad != -1) {
            txtEdad.setText(String.valueOf(edad));
        }
    }

    private void inicializarVista() {
        txtNombre = findViewById(R.id.txtNombreMain);
        txtEdad = findViewById(R.id.txtEdadMain);
        btnGuardar = findViewById(R.id.btnGuardarMain);
        btnBorrarTodo = findViewById(R.id.btnBorrarTodoMain);
        btnBorrarNombre = findViewById(R.id.btnBorrarNombreMain);
        btnBorrarEdad = findViewById(R.id.btnBorrarEdadMain);
    }
}