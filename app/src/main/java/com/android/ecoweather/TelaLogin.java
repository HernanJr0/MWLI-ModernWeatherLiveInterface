package com.android.ecoweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class TelaLogin extends AppCompatActivity {

    private TextView textTelaCriar;
    private EditText editTextEmail, editTextSenha;
    private Button buttonEntrar;
    private ProgressBar progressBar;
    private ImageView buttonVerSenha;
    String erroPreencher = "Preencha todos os campos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);

        IniciarComponentes();

        textTelaCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(TelaLogin.this,
                        TelaCadastro.class);
                startActivity(it);
            }
        });

        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String senha = editTextSenha.getText().toString();

                if(email.isEmpty() || senha.isEmpty()){
                    Toast.makeText(TelaLogin.this, erroPreencher, Toast.LENGTH_SHORT).show();
                }else {
                    AutenticarUsuario(v);
                }
            }
        });

        buttonVerSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonVerSenha.getContentDescription().toString().equals("exibir")) {
                    editTextSenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    buttonVerSenha.setContentDescription("esconder");
                }else {
                    editTextSenha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    buttonVerSenha.setContentDescription("exibir");
                }
                editTextSenha.setSelection(editTextSenha.getText().length());
            }
        });
    }

    private void AutenticarUsuario(View v) {
        String email = editTextEmail.getText().toString();
        String senha = editTextSenha.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    progressBar.setVisibility(View.VISIBLE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            TelaPrincipal();
                        }
                    }, 3000);
                }else {
                    String erro;
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthInvalidCredentialsException | FirebaseAuthInvalidUserException e) {
                        erro = "E-mail ou senha incorreta";
                    } catch (Exception e){
                        erro = "Erro ao efeturar login";
                    }
                    Toast.makeText(TelaLogin.this, erro, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();

        if(usuarioAtual != null) {
            TelaPrincipal();
        }
    }

    private void TelaPrincipal() {
        Intent it = new Intent(TelaLogin.this, MainActivity.class);
        startActivity(it);
        finish();
    }

    private void IniciarComponentes() {
        textTelaCriar = findViewById(R.id.textTelaCriar);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSenha = findViewById(R.id.editTextSenha);
        buttonEntrar = findViewById(R.id.buttonEntrar);
        progressBar = findViewById(R.id.progressBar);
        buttonVerSenha = findViewById(R.id.buttonVerSenha);
    }
}