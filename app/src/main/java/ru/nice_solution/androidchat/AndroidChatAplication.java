package ru.nice_solution.androidchat;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Madriguera on 08/06/2016.
 */
 //hereda de Application
public class AndroidChatAplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        setupFirebase();/*Inicializamos Firebase*/
    }

    //este método inicializa Firebase
    private void setupFirebase() {
        Firebase.setAndroidContext(this);                          /*Inicializa Firebase*/
        Firebase.getDefaultConfig().setPersistenceEnabled(true);  /*Permite uso offline*/
    }
}
