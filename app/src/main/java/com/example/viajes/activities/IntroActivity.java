package com.example.viajes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.viajes.databinding.ActivityIntroBinding;

public class IntroActivity extends AppCompatActivity {
    private ActivityIntroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginBtn.setOnClickListener(v -> loginUser());
        
        // El botón de registro ahora mostrará un mensaje o lo podemos ocultar
        binding.registerBtn.setOnClickListener(v -> 
            Toast.makeText(IntroActivity.this, "Erregistroa desgaituta dago", Toast.LENGTH_SHORT).show()
        );
    }

    private void loginUser() {
        String user = binding.emailEt.getText().toString().trim();
        String password = binding.passwordEt.getText().toString().trim();

        if (user.equals("jaime") && password.equals("jaime")) {
            startActivity(new Intent(IntroActivity.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(IntroActivity.this, "Erabiltzaile edo pasahitz okerra", Toast.LENGTH_SHORT).show();
            if (user.isEmpty()) binding.emailEt.setError("Eman erabiltzailea");
            if (password.isEmpty()) binding.passwordEt.setError("Eman pasahitza");
        }
    }
}