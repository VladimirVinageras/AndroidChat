package ru.nice_solution.androidchat.login;

/**
 * Created by Madriguera on 10/06/2016.
 */
public interface LoginRepository {
    void SignUp(String mail, String password);
    void SignIn(String mail, String password);
    void checkSession();
}
