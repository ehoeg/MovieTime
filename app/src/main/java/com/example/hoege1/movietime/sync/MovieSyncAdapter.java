package com.example.hoege1.movietime.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

/**
 * Created by hoege1 on 12/6/17.
 */

public class MovieSyncAdapter extends AbstractThreadedSyncAdapter
{
    public MovieSyncAdapter(Context context, boolean autoInitialize)
    {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult)
    {

    }
}
