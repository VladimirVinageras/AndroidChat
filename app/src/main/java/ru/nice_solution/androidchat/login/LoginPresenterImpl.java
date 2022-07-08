package ru.nice_solution.androidchat.login;

import android.util.Log;

import ru.nice_solution.androidchat.lib.EventBus;
import ru.nice_solution.androidchat.lib.GreenRobotEventBus;
import ru.nice_solution.androidchat.login.events.LoginEvent;

/**
 * Created by Madriguera on 11/06/2016.
 */
public class LoginPresenterImpl implements LoginPresenter {
    private EventBus eventBus;
    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {

        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImpl();
        this.eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void onDestroy() {

        loginView = null;
        eventBus.unregister(this);
    }

    @Override
    public void onCreate() {
        eventBus.register(this);

    }

    @Override
    public void checkForAuthenticationUser() {
        if (loginView != null) {
            loginView.disableInputs();
            loginView.showProgress();
        }
        loginInteractor.checkSession();
    }

    @Override
    public void validateLogin(String email, String password) {
        if (loginView != null) {
            loginView.disableInputs();
            loginView.showProgress();
        }
        loginInteractor.doSignIn(email, password);
    }

    @Override
    public void registerNewUser(String email, String password) {
        if (loginView != null) {
            loginView.disableInputs();
            loginView.showProgress();
        }
        loginInteractor.doSignUp(email, password);
    }

    @Override
    public void onEventMainThread(LoginEvent event) {
        switch (event.getEventType()){
            case LoginEvent.onSignInSuccess:
                onSignInSuccess();
                break;
            case LoginEvent.onSignUpSuccess:
                onSignUpSuccess();
                break;
            case LoginEvent.onSignInError:
                onSignInError(event.getErrorMessage());
                break;
            case LoginEvent.onSignUpError:
                onSignUpError(event.getErrorMessage());
                break;
            case LoginEvent.onFailedToRecoverSession:
                onFailedToRecoverySession();
                break;
        }
    }

    private void onFailedToRecoverySession() {
        if (loginView != null) {
            loginView.enableInputs();
            loginView.hideProgress();
        }
        Log.e("LoginPresenterImpl","onFailedToRecoverySession");
    }

    private void onSignInSuccess() {
        if (loginView != null) {
            loginView.navigateToMainScreen();
        }
    }

    private void onSignUpSuccess() {
        if (loginView != null) {
            loginView.newUserSuccess();

        }
    }

    private void onSignInError(String error) {
        if (loginView != null) {
            loginView.enableInputs();
            loginView.hideProgress();
            loginView.loginError(error);
        }
    }

    private void onSignUpError(String error) {
        if (loginView != null) {
            loginView.enableInputs();
            loginView.hideProgress();
            loginView.newUserError(error);


        }

    }
}
