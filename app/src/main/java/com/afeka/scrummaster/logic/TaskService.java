package com.afeka.scrummaster.logic;

import android.content.Context;

import com.afeka.scrummaster.ResponseListener;
import com.afeka.scrummaster.layout.Task;
import com.afeka.scrummaster.layout.User;
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

public class TaskService {
    private final String BASE_URL = "http://192.168.1.152:8080/tasks";
    private final String TAG = "TaskService";
    private static TaskService instance;
    private RequestQueue requestQueue;
    private static Context ctx;
    private static User currentUser = null;

    private TaskService(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized TaskService getInstance(Context context) {
        if (instance == null) {
            instance = new TaskService(context);
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

    public void findAll(ResponseListener<List<Task>> listener) {
        JsonArrayRequest request = new JsonArrayRequest(
                BASE_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<Task> tasks = toTaskList(response);
                            listener.onRes(tasks);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error);
                    }
                }
        );
        getRequestQueue().add(request);
    }



    private Task toTask(JSONObject object) throws IOException {
        return new ObjectMapper().readValue(object.toString(), Task.class);
    }

    private List<Task> toTaskList(JSONArray array) throws Exception {
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            tasks.add(this.toTask(array.getJSONObject(i)));
        }
        return tasks;
    }

    public void createTask(Task task, ResponseListener<Task> listener) throws IOException, JSONException {
        JsonObjectRequest request = new JsonObjectRequest(
                BASE_URL,
                new JSONObject(new ObjectMapper().writeValueAsString(task)),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Task t = toTask(response);
                            listener.onRes(t);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error);
                    }
                }
        );
        getRequestQueue().add(request);
    }
}
