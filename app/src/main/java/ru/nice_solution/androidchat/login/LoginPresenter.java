package ru.nice_solution.androidchat.login;

import ru.nice_solution.androidchat.login.events.LoginEvent;

/**
 * Created by Madriguera on 10/06/2016.
 */
public interface LoginPresenter {
 void onDestroy();
    void onCreate();
    void checkForAuthenticationUser();
    void validateLogin(String email, String password);
    void registerNewUser(String email, String password);
    void onEventMainThread(LoginEvent event);
}
