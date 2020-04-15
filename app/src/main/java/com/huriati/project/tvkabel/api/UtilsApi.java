package com.huriati.project.tvkabel.api;

public class UtilsApi {

      // 10.0.2.2 ini adalah localhost.
      public static final String BASE_URL_API = "http://192.168.43.216/tvkabel/backend/web/api/";


    // Mendeklarasikan Interface BaseApiService
    public static BaseApiService getAPIService(String token){
        return RetrofitClient.getClient(BASE_URL_API, token).create(BaseApiService.class);
    }
    public static BaseApiService getAPIService2(){
        return RetrofitClient.getClient2(BASE_URL_API).create(BaseApiService.class);
    }
}
