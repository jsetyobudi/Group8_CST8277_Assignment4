package com.enterpriseandroid.restfulcontacts;

import android.app.Application;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
/*
 * SpritesApplication Class
 * Set up for the Sprite App. Taken from class notes by Stanley Pieda and eidted slightly by Johan
 * @author Johan Setyobudi
 * Last Updated: Dec 06, 2016
 */
public class SpritesApplication extends Application
    implements SharedPreferences.OnSharedPreferenceChangeListener
{
    private static final String TAG = "APP";

    private static final String EMULATOR_HOST_IP ="localhost"; //edit this to your ip // "192.168.0.195";";
    private static final String APPENGINE_HOST = "your_app_id_here.appspot.com";
    private static final String LOCAL_APPENGINE_HOST = EMULATOR_HOST_IP;

    private static final String SPRING_SERVICE =  "/SpriteEE-war/webresources"; //change to project name
    private static final String SPRING_SYNC_SERVICE = "/springSyncServiceContacts";
    private static final String AWS_SERVICE = "/awsServiceContacts";
    private static final String APP_ENGINE_SERVICE = ""; // no app context for appspot.com

    private static final String TEST_PORT = "8080"; //also port if different
    private static final String HTTP_DEFAULT_PORT = ""; // will be 80

    private static final String SPRITES =  "/business.sprite"; //change if needed

    private static final String HTTP = "http";
    private static final String HTTPS = "https";

    private static final String PROTOCOL = HTTP;
    private static final String HOST = EMULATOR_HOST_IP;
    private static final String PORT = TEST_PORT;
    private static final String SERVICE = SPRING_SERVICE;


    // Warning: whatever value you use here will become *persistent*
    // on first run, when it gets saved as a preference below in
    // getApiUri.
    private static final String DEFAULT_API_ROOT =
            PROTOCOL + "://" + HOST + ":" + PORT + SERVICE + SPRITES;

    private String keyApiRoot;
    private Uri apiRootUri;

    public SpritesApplication() {
        /**
         * Disable the persistent connection since We cannot get it work using HttpURLConnection.
         */
        System.setProperty("http.keepAlive", "false");

    }

    /*
     * onCreate method, setting up client
     */
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) { Log.d(TAG, "Application up!"); }

        keyApiRoot = getString(R.string.prefs_url_key);

        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this);
    }
    /*
     * onSharedPreferenceChanged method, synchronized
     */
    @Override
    public synchronized void onSharedPreferenceChanged(
        SharedPreferences prefs,
        String key)
    {
        apiRootUri = null;
    }
    /*
     * getApiURI, getting api uri
     */
    public Uri getApiUri() {
        synchronized (this) {
            if (null == apiRootUri) {
               // Using a preference allows setting configuration of this
               // value - but it also makes the value persistent.  Please
               // keep this in mind if you can the url using the variables
               // above.
               apiRootUri = Uri.parse(
                   PreferenceManager.getDefaultSharedPreferences(this)
                       .getString(keyApiRoot, DEFAULT_API_ROOT));
            }
            return apiRootUri;
        }
    }
}
