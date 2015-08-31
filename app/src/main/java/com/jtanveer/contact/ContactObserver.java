/**
 * 
 */
package com.jtanveer.contact;

import android.annotation.TargetApi;
import android.database.ContentObserver;
import android.net.Uri;
import android.support.v4.content.Loader;
import android.util.Log;

/**
 * @author Jamael Tanveer
 * 
 */
public class ContactObserver<T> extends ContentObserver {

	Loader<T> mLoader;

	public ContactObserver(Loader<T> loader) {
		super(null);
		mLoader = loader;
	}

	@Override
	public boolean deliverSelfNotifications() {
		return true;
	}

	@Override
	public void onChange(boolean selfChange) {
		handleChange(selfChange, null);
	}

	@TargetApi(16)
	@Override
	public void onChange(boolean selfChange, Uri uri) {
		handleChange(selfChange, uri);
	}

	private void handleChange(boolean selfChange, Uri uri) {
		if (BuildConfig.DEBUG) {
			Log.d("change", "notified " + (uri != null ? uri.toString() : "unknown"));
		}
		mLoader.onContentChanged();
	}

}
