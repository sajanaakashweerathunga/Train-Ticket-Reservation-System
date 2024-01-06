package com.example.ead_app;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiService {
    private static final String BASE_URL = "https://ead-assignment-train.azurewebsites.net/api/Traveler";
    private static final String BASE_URL_Reservation = "hhttps://ead-assignment-train.azurewebsites.net/api/Reservation";

    public static String fetchDataFromApi() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(BASE_URL)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                // Handle error
                return null;
            }
        } catch (IOException e) {
            // Handle network or other exceptions
            e.printStackTrace();
            return null;
        }
    }

    public static boolean createData(String jsonPayload) {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(MediaType.get("application/json"), jsonPayload);

        Request request = new Request.Builder()
                .url(BASE_URL)
                .post(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateData(String id, String jsonPayload) {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(MediaType.get("application/json"), jsonPayload);

        Request request = new Request.Builder()
                .url(BASE_URL + "/" + id) // Append the record ID to the URL
                .put(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteData(String id) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(BASE_URL + "/" + id) // Append the record ID to the URL
                .delete()
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String fetchReservationFromApi() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(BASE_URL_Reservation)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                // Handle error
                return null;
            }
        } catch (IOException e) {
            // Handle network or other exceptions
            e.printStackTrace();
            return null;
        }
    }

    public static boolean updateReservation(String id, String jsonPayload) {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(MediaType.get("application/json"), jsonPayload);

        Request request = new Request.Builder()
                .url(BASE_URL_Reservation + "/" + id)
                .put(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean createReservation(String jsonPayload) {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(MediaType.get("application/json"), jsonPayload);

        Request request = new Request.Builder()
                .url(BASE_URL_Reservation)
                .post(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
