package com.example.hoege1.movietime.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by hoege1 on 12/6/17.
 */

public class MovieAuthenticatorService extends Service
{
    // Instance field that stores the authenticator object
    private MovieAuthenticator mAuthenticator;

    @Override
    public void onCreate()
    {
        // Create a new authenticator object
        mAuthenticator = new MovieAuthenticator(this);
    }

    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent)
    {
        return mAuthenticator.getIBinder();
    }
}
