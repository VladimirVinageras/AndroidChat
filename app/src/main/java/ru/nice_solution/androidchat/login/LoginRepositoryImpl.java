package ru.nice_solution.androidchat.login;

import android.util.Log;

import ru.nice_solution.androidchat.domain.FirebaseHelper;
import ru.nice_solution.androidchat.lib.EventBus;
import ru.nice_solution.androidchat.lib.GreenRobotEventBus;
import ru.nice_solution.androidchat.login.events.LoginEvent;

/**
 * Created by Madriguera on 11/06/2016.
 */
public class LoginRepositoryImpl implements LoginRepository{
    private FirebaseHelper helper;

    public LoginRepositoryImpl() {
        this.helper = FirebaseHelper.getInstance();
    }

    @Override
    public void SignUp(String mail, String password) {
        postEvent(LoginEvent.onSignUpSuccess);
    }

    @Override
    public void SignIn(String mail, String password) {

        postEvent(LoginEvent.onSignInSuccess);
    }

    @Override
    public void checkSession() {
        postEvent(LoginEvent.onFailedToRecoverSession);
    }

    private void postEvent(int type, String errorMessage){
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEventType(type);
        if(errorMessage != null){
            loginEvent.setErrorMessage(errorMessage);
        }
        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(loginEvent);
    }

    private void postEvent(int type){
        postEvent(type, null);
    }

}
