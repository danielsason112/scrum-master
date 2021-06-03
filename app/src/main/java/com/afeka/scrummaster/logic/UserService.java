package com.afeka.scrummaster.logic;

import android.content.Context;

import com.afeka.scrummaster.interfaces.ResponseListener;
import com.afeka.scrummaster.layout.CreateUser;
import com.afeka.scrummaster.layout.User;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final String BASE_URL = "http://192.168.1.152:8081/users";
    private final String TAG = "UserService";
    private static UserService instance;
    private RequestQueue requestQueue;
    private static Context ctx;
    private static User currentUser = null;

    private UserService(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized UserService getInstance(Context context) {
        if (instance == null) {
            instance = new UserService(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public User currentUser() {
        return currentUser;
    }

    public void findAll(ResponseListener<List<User>> listener) {
        JsonArrayRequest request = new JsonArrayRequest(
                BASE_URL,
                new UserListResponseListener(listener),
                error -> listener.onError(error)
        );
        getRequestQueue().add(request);
    }

    public void getUserById(String email, ResponseListener<User> listener) throws JSONException {
        JsonObjectRequest request = new JsonObjectRequest(
                BASE_URL + "/" + email,
                null,
                response -> {
                    try {
                        User user = toUser(response);
                        currentUser = user;
                        listener.onRes(user);
                    } catch (IOException e) {
                        listener.onError(e);
                    }

                },
                error -> listener.onError(error)
        );
        getRequestQueue().add(request);
    }

    public void login(String email, String password, ResponseListener<User> listener) throws JSONException {
        JsonObjectRequest request = new JsonObjectRequest(
                BASE_URL + "/login",
                new JSONObject().put("email", email).put("password", password),
                response -> {
                    try {
                        User user = toUser(response);
                        currentUser = user;
                        listener.onRes(user);
                    } catch (IOException e) {
                        listener.onError(e);
                    }

                },
                error -> listener.onError(error)
        );
        getRequestQueue().add(request);
    }

    private User toUser(JSONObject object) throws IOException {
        return new ObjectMapper().readValue(object.toString(), User.class);
    }

    private List<User> toUserList(JSONArray array) throws Exception {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            users.add(this.toUser(array.getJSONObject(i)));
        }
        return users;
    }

    public void createUser(CreateUser createUser, ResponseListener<User> listener) throws IOException, JSONException {
        JsonObjectRequest request = new JsonObjectRequest(
                BASE_URL,
                new JSONObject(new ObjectMapper().writeValueAsString(createUser)),
                response -> {
                    try {
                        User user = toUser(response);
                        currentUser = user;
                        listener.onRes(user);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                },
                error -> listener.onError(error)
        );
        getRequestQueue().add(request);
    }

    public void logout() {
        currentUser = null;
    }

    public class UserListResponseListener implements Response.Listener<JSONArray> {
        private ResponseListener listener;

        public UserListResponseListener(ResponseListener listener) {
            this.listener = listener;
        }

        @Override
        public void onResponse(JSONArray response) {
            try {
                listener.onRes(toUserList(response));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
