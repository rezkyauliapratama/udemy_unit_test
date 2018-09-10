package android.rezkyauliapratama.com.automationtest.example4;

import android.rezkyauliapratama.com.automationtest.example4.authtoken.AuthTokenCache;
import android.rezkyauliapratama.com.automationtest.example4.eventbus.EventBusPoster;
import android.rezkyauliapratama.com.automationtest.example4.networking.LoginHttpEndpointSync;
import android.rezkyauliapratama.com.automationtest.example4.networking.NetworkErrorException;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class LoginUseCaseSyncTest {

    private static String USERNAME = "username";
    private static String PASSWORD = "password";
    private static String AUTH_TOKEN = "authToken";

    LoginHttpEndPointSyncTd mLoginHttpEndPointSyncTd;
    AuthTokenCacheTd mAuthTokenCacheTd;

    private LoginUseCaseSync SUT;

    @Before
    public void setUp() throws Exception {
        mLoginHttpEndPointSyncTd = new LoginHttpEndPointSyncTd();
        mAuthTokenCacheTd = new AuthTokenCacheTd();
        SUT = new LoginUseCaseSync(mLoginHttpEndPointSyncTd, mAuthTokenCacheTd, new EventBusPosterTd());
    }


    // username and password passed to the endpoint
    @Test
    public void loginSync_success_usernameAndPasswordPassedToEndPoint() {
        SUT.loginSync(USERNAME,PASSWORD);
        assertThat(mLoginHttpEndPointSyncTd.mUsername, is(USERNAME));
        assertThat(mLoginHttpEndPointSyncTd.mPassword,is(PASSWORD));
    }


    // if login succeeds - user's auth token must be created
    @Test
    public void loginSync_success_authTokenCached() {
        SUT.loginSync(USERNAME,PASSWORD);
        assertThat(mAuthTokenCacheTd.mAuthToken,is(AUTH_TOKEN));
    }


    // if login fails - auth token is not changed
    @Test
    public void loginSync_generalError_authTokenNotCached() {
        mLoginHttpEndPointSyncTd.isGeneralError = true;

        SUT.loginSync(USERNAME,PASSWORD);

        assertThat(mAuthTokenCacheTd.getAuthToken(),is(""));
    }

    @Test
    public void loginSync_authError_authTokenNotCached() {
        mLoginHttpEndPointSyncTd.isAuthError = true;
        assertThat(mAuthTokenCacheTd.getAuthToken(), is(""));
    }

    @Test
    public void loginSync_serverError_authTokenNotCached() {
        mLoginHttpEndPointSyncTd.isServerError= true;
        assertThat(mAuthTokenCacheTd.getAuthToken(), is(""));
    }

    // if login succeeds - login event posted to event bus
    

    // if login fails - no login event posted
    // if login succeeds - success returned
    // fails - fail returned
    // network - network error returned

    //helper class
    private static class LoginHttpEndPointSyncTd implements LoginHttpEndpointSync {

        public String mUsername;
        private String mPassword;
        public boolean isGeneralError;
        public boolean isAuthError;
        public boolean isServerError;

        @Override
        public EndpointResult loginSync(String username, String password) throws NetworkErrorException {
            mUsername = username;
            mPassword = password;
            if (isGeneralError){
                return new EndpointResult(EndpointResultStatus.GENERAL_ERROR,"");
            }else if(isAuthError){
                return new EndpointResult(EndpointResultStatus.AUTH_ERROR,"");
            }else if(isServerError){
                return new EndpointResult(EndpointResultStatus.SERVER_ERROR,"");
            }else{
                return new EndpointResult(EndpointResultStatus.SUCCESS,AUTH_TOKEN);
            }
        }
    }

    private static class AuthTokenCacheTd implements AuthTokenCache {
        String mAuthToken = "";

        @Override
        public void cacheAuthToken(String authToken) {
            mAuthToken = authToken;
        }

        @Override
        public String getAuthToken() {
            return mAuthToken;
        }
    }

    private static class EventBusPosterTd implements EventBusPoster{

        @Override
        public void postEvent(Object event) {

        }
    }
}