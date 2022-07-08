package ru.nice_solution.androidchat.login;

/**
 * Created by Madriguera on 10/06/2016.
 */
public interface LoginInteractor {
    void checkSession();
    void doSignUp(String email, String password);
    void doSignIn(String email, String password);
}
