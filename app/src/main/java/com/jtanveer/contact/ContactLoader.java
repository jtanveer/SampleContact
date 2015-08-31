/**
 *
 */
package com.jtanveer.contact;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

/**
 * @author Jamael Tanveer
 */
public class ContactLoader extends AsyncTaskLoader<ArrayList<Bundle>> {

    ContactObserver<ArrayList<Bundle>> mObserver;
    ArrayList<Bundle> mData;

    public ContactLoader(Context context) {
        super(context);
    }

    @Override
    public ArrayList<Bundle> loadInBackground() {
        ArrayList<Bundle> data = new ArrayList<>();
        Cursor cursor = getContext().getContentResolver().query(ContactsContract.Data.CONTENT_URI,
                null,
                ContactsContract.Data.HAS_PHONE_NUMBER + "!=0 AND (" + ContactsContract.Data.MIMETYPE + "=? OR " + ContactsContract.Data.MIMETYPE + "=?)",
                new String[]{ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE}, ContactsContract.Contacts.DISPLAY_NAME + " ASC");
        while (cursor.moveToNext()) {
            Bundle row = new Bundle();
            row.putString("name", cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME)));
            row.putString("phone", cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DATA1)));
            row.putString("photo", cursor.getString(cursor.getColumnIndex(ContactsContract.Data.PHOTO_URI)));
            data.add(row);
        }
        cursor.close();

        return data;
    }

    @Override
    public void deliverResult(ArrayList<Bundle> data) {
        if (isReset())
            return;

        ArrayList<Bundle> oldData = mData;
        mData = data;

        if (isStarted())
            super.deliverResult(data);

        if (oldData != null) {
            oldData = null;
        }
    }

    @Override
    public void onStartLoading() {
        if (mData != null)
            deliverResult(mData);

        if (mObserver == null) {
            mObserver = new ContactObserver<>(this);
            getContext().getContentResolver().registerContentObserver(ContactsContract.Contacts.CONTENT_URI, true, mObserver);
        }

        if (takeContentChanged() || mData == null) {
            forceLoad();
        }
    }

    @Override
    public void onStopLoading() {
        cancelLoad();
    }

    @Override
    public void onReset() {
        onStopLoading();

        if (mData != null)
            mData = null;

        if (mObserver != null) {
            getContext().getContentResolver().unregisterContentObserver(mObserver);
            mObserver = null;
        }
    }

    @Override
    public void onCanceled(ArrayList<Bundle> data) {
        super.onCanceled(data);
    }

}
