package com.example.lockapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import java.util.concurrent.Executor;

public class LockScreenActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);

        authenticateUser();
    }

    private void authenticateUser() {
        BiometricManager biometricManager = BiometricManager.from(this);
        int canAuthenticate = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG | BiometricManager.Authenticators.DEVICE_CREDENTIAL);
        Log.d("LockScreenActivity", "canAuthenticate result: " + canAuthenticate);

        if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
            Executor executor = ContextCompat.getMainExecutor(this);
            BiometricPrompt.AuthenticationCallback callback = new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    Log.e("LockScreenActivity", "Authentication error: " + errString);
                    setResult(RESULT_CANCELED);
                    finish();
                }

                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    Log.d("LockScreenActivity", "Authentication succeeded");
                    setResult(RESULT_OK);
                    finish();
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    Log.d("LockScreenActivity", "Authentication failed");
                    Toast.makeText(LockScreenActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                }
            };

            BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, callback);

            BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Biometric Authentication")
                    .setSubtitle("Authenticate to unlock the app")
                    .setDescription("Use your biometric credential to unlock this app.")
                    .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG | BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                    .build();

            biometricPrompt.authenticate(promptInfo);
        } else {
            Log.e("LockScreenActivity", "Biometric authentication not supported: " + canAuthenticate);
            Toast.makeText(this, "Biometric authentication not supported", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED);
            finish();
        }
    }
}
