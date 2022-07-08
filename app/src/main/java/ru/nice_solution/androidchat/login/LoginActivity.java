package ru.nice_solution.androidchat.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.nice_solution.androidchat.R;
import ru.nice_solution.androidchat.contactlist.ContactListActivity;

public class LoginActivity extends AppCompatActivity implements LoginView {
    @Bind(R.id.TxtEmail)
    EditText inputEmail;
    @Bind(R.id.TxtPassword)
    EditText TxtPassword;
    @Bind(R.id.wrapperPassword)
    TextInputLayout wrapperPassword;
    @Bind(R.id.btnSignIn)
    Button btnSignIn;
    @Bind(R.id.btnSignUp)
    Button btnSignUp;
    @Bind(R.id.LayoutButton)
    LinearLayout LayoutButton;
    @Bind(R.id.ProgressBar)
    android.widget.ProgressBar ProgressBar;
    @Bind(R.id.LayoutMainContainer)
    RelativeLayout container;


    private LoginPresenter loginPresenter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);  /*Va después del setContentView. Inicializa Butterknife */

        loginPresenter = new LoginPresenterImpl(this);
        loginPresenter.onCreate();
        loginPresenter.checkForAuthenticationUser();

    }

    @Override
    protected void onDestroy() {
        loginPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void enableInputs() {
        setInputs(true);
    }

    @Override
    public void disableInputs() {
        setInputs(false);
    }

    @Override
    public void showProgress() {
        ProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        ProgressBar.setVisibility(View.GONE);
    }

    @OnClick(R.id.btnSignUp)
    public void handleSignUp() {
        loginPresenter.registerNewUser(inputEmail.getText().toString(), TxtPassword.getText().toString());
    }


    @OnClick(R.id.btnSignIn)
    @Override
    public void handleSignIn() {
        loginPresenter.validateLogin(inputEmail.getText().toString(), TxtPassword.getText().toString());
    }

    @Override
    public void navigateToMainScreen() {
        startActivity(new Intent(this, ContactListActivity.class));
    }

    @Override
    public void loginError(String error) {
        TxtPassword.setText("");
        String msgError = String.format(getString(R.string.login_error_message_signin));
        TxtPassword.setError(msgError);

    }

    @Override
    public void newUserSuccess() {
        Snackbar.make(container, R.string.login_notice_message_signup, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void newUserError(String error) {
        TxtPassword.setText("");
        String msgError = String.format(getString(R.string.login_error_message_signup));
        TxtPassword.setError(msgError);
    }

    private void setInputs(boolean enabled) {
        inputEmail.setEnabled(enabled);
        TxtPassword.setEnabled(enabled);
        btnSignIn.setEnabled(enabled);
        btnSignUp.setEnabled(enabled);


    }


}
