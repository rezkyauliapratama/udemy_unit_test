package android.rezkyauliapratama.com.automationtest.example4

import android.rezkyauliapratama.com.automationtest.example4.authtoken.AuthTokenCache
import android.rezkyauliapratama.com.automationtest.example4.eventbus.EventBusPoster
import android.rezkyauliapratama.com.automationtest.example4.eventbus.LoggedInEvent
import android.rezkyauliapratama.com.automationtest.example4.networking.LoginHttpEndpointSync
import android.rezkyauliapratama.com.automationtest.example4.networking.NetworkErrorException
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.*
import java.util.*
import java.util.logging.LoggingPermission

class LoginUseCaseSyncMockTest{

    val USERNAME = "username"
    val PASSWORD = "password"
    val AUTH_TOKEN = "authToken"

    lateinit var mLoginHttpEndpointSyncMock: LoginHttpEndpointSync
    lateinit var mAuthTokenCacheMock: AuthTokenCache
    lateinit var mEventBusPosterMock: EventBusPoster
    lateinit var SUT: LoginUseCaseSync

    @Before
    @Throws(Exception::class)
    fun setup() {

        mLoginHttpEndpointSyncMock = Mockito.mock(LoginHttpEndpointSync::class.java)
        mAuthTokenCacheMock = Mockito.mock(AuthTokenCache::class.java)
        mEventBusPosterMock = Mockito.mock(EventBusPoster::class.java)


        SUT = LoginUseCaseSync(mLoginHttpEndpointSyncMock, mAuthTokenCacheMock, mEventBusPosterMock)

        success()
    }



    @Test
    @Throws(Exception::class)
    fun loginSync_success_usernameAndPasswordPassedToEndpoint() {
        val ac : ArgumentCaptor<String> = ArgumentCaptor.forClass(String::class.java)
        SUT.loginSync(USERNAME,PASSWORD)
        verify(mLoginHttpEndpointSyncMock, times(1)).loginSync(ac.capture(),ac.capture())
        val captures : List<String> = ac.allValues
        assertThat(captures[0],`is`(USERNAME))
        assertThat(captures[1],`is`(PASSWORD))
    }

    @Test
    @Throws(Exception::class)
    fun loginSync_success_authTokenCached() {
        val ac : ArgumentCaptor<String> = ArgumentCaptor.forClass(String::class.java)
        SUT.loginSync(USERNAME,PASSWORD)
        verify(mAuthTokenCacheMock).cacheAuthToken(ac.capture())
        assertThat(ac.value, `is`(AUTH_TOKEN))
    }

    @Test
    @Throws(Exception::class)
    fun loginSync_generalError_authTokenNotCached() {
        generalError()
        SUT.loginSync(USERNAME,PASSWORD)
        //make sure mAuthTokenCache not have interaction when loginSync method call
        verifyNoMoreInteractions(mAuthTokenCacheMock)
    }

    @Test
    @Throws(Exception::class)
    fun loginSync_authError_authTokenNotCached() {
        authError()
        SUT.loginSync(USERNAME,PASSWORD)
        //make sure mAuthTokenCache not have interaction when loginSync method call
        verifyNoMoreInteractions(mAuthTokenCacheMock)
    }


    @Test
    @Throws(Exception::class)
    fun loginSync_serverError_authTokenNotCached() {
        serverError()
        SUT.loginSync(USERNAME,PASSWORD)
        //make sure mAuthTokenCache not have interaction when loginSync method call
        verifyNoMoreInteractions(mAuthTokenCacheMock)
    }



    @Test
    @Throws(Exception::class)
    fun loginSync_success_loggedInEventPosted() {
        val ac : ArgumentCaptor<Any> = ArgumentCaptor.forClass(Any::class.java)
        SUT.loginSync(USERNAME,PASSWORD)
        verify(mEventBusPosterMock).postEvent(ac.capture())
        assertThat(ac.value, `is`(instanceOf(LoggedInEvent::class.java)))
    }

    @Test
    @Throws(Exception::class)
    fun loginSync_generalError_noInteractionWithEventBusPoster() {

    }

    @Test
    @Throws(Exception::class)
    fun loginSync_authError_noInteractionWithEventBusPoster() {

    }

    @Test
    @Throws(Exception::class)
    fun loginSync_serverError_noInteractionWithEventBusPoster() {

    }

    @Test
    @Throws(Exception::class)
    fun loginSync_success_successReturned() {
        val result : LoginUseCaseSync.UseCaseResult =  SUT.loginSync(USERNAME,PASSWORD)
        assertThat(result, `is`(LoginUseCaseSync.UseCaseResult.SUCCESS))
    }

    @Test
    @Throws(Exception::class)
    fun loginSync_serverError_failureReturned() {
        serverError()
        val result : LoginUseCaseSync.UseCaseResult =  SUT.loginSync(USERNAME,PASSWORD)
        assertThat(result, `is`(LoginUseCaseSync.UseCaseResult.FAILURE))
    }

    @Test
    @Throws(Exception::class)
    fun loginSync_authError_failureReturned() {

    }

    @Test
    @Throws(Exception::class)
    fun loginSync_generalError_failureReturned() {

    }

    @Test
    @Throws(Exception::class)
    fun loginSync_networkError_networkErrorReturned() {
        networkError()
        val result : LoginUseCaseSync.UseCaseResult = SUT.loginSync(USERNAME,PASSWORD)
        assertThat(result, `is`(LoginUseCaseSync.UseCaseResult.NETWORK_ERROR))


    }

    private fun networkError() {
        doThrow(NetworkErrorException()).`when`(mLoginHttpEndpointSyncMock.loginSync(any(String::class.java),any(String::class.java)))

    }

    private fun success() {
        `when`(mLoginHttpEndpointSyncMock.loginSync(any(String::class.java),any(String::class.java)))
                .thenReturn(LoginHttpEndpointSync.EndpointResult(LoginHttpEndpointSync.EndpointResultStatus.SUCCESS,AUTH_TOKEN))

    }


    private fun generalError() {
        `when`(mLoginHttpEndpointSyncMock.loginSync(any(String::class.java), any(String::class.java)))
                .thenReturn(LoginHttpEndpointSync.EndpointResult(LoginHttpEndpointSync.EndpointResultStatus.GENERAL_ERROR,""))
    }


    private fun authError() {
        `when`(mLoginHttpEndpointSyncMock.loginSync(any(String::class.java), any(String::class.java)))
                .thenReturn(LoginHttpEndpointSync.EndpointResult(LoginHttpEndpointSync.EndpointResultStatus.AUTH_ERROR,""))
    }

    private fun serverError() {
        `when`(mLoginHttpEndpointSyncMock.loginSync(any(String::class.java), any(String::class.java)))
                .thenReturn(LoginHttpEndpointSync.EndpointResult(LoginHttpEndpointSync.EndpointResultStatus.SERVER_ERROR,""))
    }
}