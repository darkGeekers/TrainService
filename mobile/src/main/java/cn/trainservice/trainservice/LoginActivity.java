package cn.trainservice.trainservice;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.request.AbstractRequest;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;
import com.litesuits.http.utils.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.trainservice.trainservice.journey.view.TicketInfo;
import cn.trainservice.trainservice.view.CameraView;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {


    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */


    // UI references.
    private AutoCompleteTextView mIDCardNumberView;
    private EditText mTicketNumberView;
    private View mProgressView;
    private View mLoginFormView;
    private CameraView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mIDCardNumberView = (AutoCompleteTextView) findViewById(R.id.IDCardNumber);


        mTicketNumberView = (EditText) findViewById(R.id.ticketNumber);
        mTicketNumberView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        BootstrapButton mEmailSignInButton = (BootstrapButton) findViewById(R.id.sign_in_button);
        mEmailSignInButton.setBootstrapBrand(DefaultBootstrapBrand.INFO);

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

         surfaceView=(CameraView)findViewById(R.id.surfaceView);
        surfaceView.bindActivity(this);
        surfaceView.startCamera();
    }




    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {


        // Reset errors.
        mIDCardNumberView.setError(null);
        mTicketNumberView.setError(null);

        // Store values at the time of the login attempt.
        String email = mIDCardNumberView.getText().toString();
        String password = mTicketNumberView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mTicketNumberView.setError(getString(R.string.error_invalid_ticketNumber));
            focusView = mTicketNumberView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mIDCardNumberView.setError(getString(R.string.error_field_required));
            focusView = mIDCardNumberView;
            cancel = true;
        } else if (!isIDValid(email)) {
            mIDCardNumberView.setError(getString(R.string.error_invalid_id));
            focusView = mIDCardNumberView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
       Log.d("data","post");
            mProgressView.requestFocus();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    String uploadUrl = TrainServiceApplication.user_Login();
//
//                    HttpURLConnection connection = null;
//                    DataOutputStream out = null;
//                    try {
//                        Log.d("data","posting");
//                        URL url = new URL(uploadUrl);
//                        connection = (HttpURLConnection) url.openConnection();
//                        connection.setConnectTimeout(10 * 1000);
//                        connection.setRequestMethod("POST");
//                        connection.setUseCaches(false);
//                        connection.setRequestProperty("Accept", "text/html,application/xhtml+xml," +
//                                "application/xml;q=0.9,*/*;q=0.8");
//                        connection.addRequestProperty("User-Agent", "Mozilla / 5.0 (Windows NT 6.3;" +
//                                "WOW64)AppleWebKit / 537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 " +
//                                "Safari/537.36 SE 2.X MetaSr 1.0");
//                        connection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//                        connection.addRequestProperty("Accept-Language", "zh-CN");
//                        connection.addRequestProperty("Charset", "UTF-8");
//                        connection.addRequestProperty("Connection", "Keep-Alive");
//                        connection.setDoInput(true);
//                        connection.setDoOutput(true);
//                        connection.connect();
//                        out = new DataOutputStream(connection.getOutputStream());
//                        String str = "user_id" + mIDCardNumberView.getText().toString() + "&check_num" + mTicketNumberView.getText().toString();
//                        out.writeBytes(str);
//                        out.close();
//                        String response = "";
//                        int responsenum = connection.getResponseCode();
//                        if (responsenum == 200) {
//                            DataInputStream input = new DataInputStream(connection.getInputStream());
//                            String line = "";
//                            while ((line = input.readLine()) != null) {
//                                response = response + line;
//                            }
//                            Log.d("data", response);
//                            input.close();
//                        } else {
//                            response = null;
//                        }
//                        System.out.print(response);
//
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } finally {
//                        if (connection != null)
//                            connection.disconnect();
//                    }
//
//                }
                    final StringRequest postRequest = new StringRequest(uploadUrl)
                            .setMethod(HttpMethods.Post)
                            .setHttpListener(new HttpListener<String>(true, false, true) {
                                @Override
                                public void onStart(AbstractRequest<String> request) {
                                    super.onStart(request);
                                    showProgress(true);
                                }

                                @Override
                                public void onUploading(AbstractRequest<String> request, long total, long len) {
                                    showProgress(true);
                                }

                                @Override
                                public void onEnd(Response<String> response) {
                                    showProgress(false);
                                    if (response.isConnectSuccess()) {
                                        boolean success = false;
                                        String jsonstr=response.getResult();
                                        try{
                                            JSONObject js= new JSONObject(jsonstr);
                                            if(js.has("result")){
                                                success=js.getBoolean("result");
                                                int code = (success == false ? 0 : 1);
                                                JSONObject jso=js.getJSONObject("info");
                                                String user_ID=jso.getString("User_ID");
                                                String  userName=jso.getString("User_Name");
                                                String train_Name=jso.getString("Train_Name");
                                                String startname=jso.getString("startname");
                                                String endname=jso.getString("User_ID");

                                                TrainServiceApplication.setTickt(
                                                        new TicketInfo(LoginActivity.this,train_Name,userName,user_ID,startname,endname));


                                                responseLoginResult(code);
                                                Log.d("data", response.getResult());

                                            }




                                        }catch (JSONException e){

                                        }
                                        } else {
                                        HttpUtil.showTips(LoginActivity.this, "Upload Failure", response.getException() + "");
                                    }

                                }
                            });
                    LinkedList<NameValuePair> pList = new LinkedList<>();
                    pList.add(new NameValuePair("user_id",mIDCardNumberView.getText().toString() ));
                    pList.add(new NameValuePair("check_num", mTicketNumberView.getText().toString()));
                    postRequest.setHttpBody(new UrlEncodedFormBody(pList));
                    TrainServiceApplication.getLiteHttp(LoginActivity.this).executeAsync(postRequest);
                }
            }).start();


        }
    }
    public void responseLoginResult(final int code) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (code) {
                    case 0:
                        showProgress(false);
                        mTicketNumberView.setError(getString(R.string.error_incorrect_ticketNumber));
                        mTicketNumberView.requestFocus();
                        break;
                    case 1:
                        //
                        TrainServiceApplication.hasLogin = true;
                        finish();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        showProgress(false);
                        break;
                    case 2:
                        Toast.makeText(LoginActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
                        break;
                }
                showProgress(false);
            }
        });

    }


    private boolean isIDValid(String ID) {
        //TODO: Replace this with your own logic
        return ID.length() == 18;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() == 5;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade_in-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);

        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mIDCardNumberView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }
}

