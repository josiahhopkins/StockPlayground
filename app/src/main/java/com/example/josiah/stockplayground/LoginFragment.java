package com.example.josiah.stockplayground;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URLEncoder;


/**
 * This fragment allows a user to log in.
 * @author Josiah
 */
public class LoginFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String LOGIN_URL = "http://cssgate.insttech.washington.edu/~josiah3/PHP_Code/PHP%20Code/login.php?";

    private String mParam1;
    private String mParam2;

    private loginListener mListener;
    private EditText mUsernameLoginEdit;
    private EditText mPasswordLoginEdit;


    public LoginFragment() {
        // Required empty public constructor
    }

    public interface loginListener {
        void login(String url);
    }

    public interface registerListener {
        void goToRegister();
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        Button loginButton = (Button) view.findViewById(R.id.login_button);
        Button registerButton = (Button) view.findViewById(R.id.register_button);
        mPasswordLoginEdit = (EditText) view.findViewById(R.id.password_login_edit);
        mUsernameLoginEdit = (EditText) view.findViewById((R.id.username_login_edit));
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = buildLoginURL(v);
                mListener.login(url);
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                if(v.getId() == R.id.register_button){
                    getFragmentManager().beginTransaction().replace(R.id.login_page_container, new RegisterFragment()).addToBackStack(null).commit();
                }
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof loginListener) {
            mListener = (loginListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Builds a URL with which the user logs in
     * @param v: the view originally clicked
     * @return: the URL with which a user can log in.
     */
    private String buildLoginURL(View v) {
        StringBuilder sb = new StringBuilder(LOGIN_URL);
        try {
            String user = mUsernameLoginEdit.getText().toString();
            sb.append("username=");
            sb.append(user);
            String password = mPasswordLoginEdit.getText().toString();
            sb.append("&password=");
            sb.append(URLEncoder.encode(password, "UTF-8"));
            Log.i("Login", sb.toString());
        } catch (Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return sb.toString();
    }
}
