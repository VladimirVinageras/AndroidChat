package ru.nice_solution.androidchat.login;

/**
 * Created by Madriguera on 11/06/2016.
 */
public class LoginInteractorImpl implements LoginInteractor {
private  LoginRepository loginRepository;

    public LoginInteractorImpl() {
        loginRepository = new LoginRepositoryImpl();
            }

    @Override
    public void checkSession() {
     loginRepository.checkSession();
    }

    @Override
    public void doSignUp(String email, String password) {
        loginRepository.SignUp(email, password);

    }

    @Override
    public void doSignIn(String email, String password) {
        loginRepository.SignIn(email, password);

    }
}
