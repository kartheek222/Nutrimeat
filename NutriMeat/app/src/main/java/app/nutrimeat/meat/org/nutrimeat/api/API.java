package app.nutrimeat.meat.org.nutrimeat.api;

import java.util.List;

import app.nutrimeat.meat.org.nutrimeat.Account.Orders_Items_Response;
import app.nutrimeat.meat.org.nutrimeat.Account.Orders_Model;
import app.nutrimeat.meat.org.nutrimeat.Home.CheckAreaReponse;
import app.nutrimeat.meat.org.nutrimeat.Home.StatsResponseModel;
import app.nutrimeat.meat.org.nutrimeat.ProductDetails.Products_Details_Model;
import app.nutrimeat.meat.org.nutrimeat.Recipies.Recipies_Details_Model;
import app.nutrimeat.meat.org.nutrimeat.Recipies.Recipies_Model;
import app.nutrimeat.meat.org.nutrimeat.WatsCooking.WatsCooking_Model;
import app.nutrimeat.meat.org.nutrimeat.product.Product_Model;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface API {
    @POST("main/register/format/json")
    Call<ServerResponse> getrespone(@Body Request request);

    @POST("key/login/format/json")
    Call<ServerResponse> getlogin_response(@Body LoginRequest loginRequest);

    @POST("main/change_password_email/format/json")
    Call<ServerResponse> getForgot_response(@Body ForgotpasswordRequest forgotpasswordRequest);

    @POST("main/send_sms/format/json")
    Call<ServerResponse> sendSms(@Body SendSMS sendSMS);

    @POST("main/verify_sms/format/json")
    Call<ServerResponse> verifysms(@Body VerifySMS verifySMS);

    @POST("main/email_check")
    Call<ServerResponse> checkuser(@Body CheckUser checkUser);

    @POST("main/delivery_service/format/json")
    Call<CheckAreaReponse> checkarea(@Body CheckArea area);

    @Headers("X-API-KEY:80w0g4o84wsc4gsc804c08scs00w8co4wscg848c")
    @GET("main/products/format/json")
    Call<List<Product_Model>> products(@Query("cat") String cat);

    @Headers("X-API-KEY:80w0g4o84wsc4gsc804c08scs00w8co4wscg848c")
    @GET("main/products/id/{pid}/format/json")
    Call<Products_Details_Model> products_details(@Path("pid") String pid);

    @Headers("X-API-KEY:80w0g4o84wsc4gsc804c08scs00w8co4wscg848c")
    @GET("main/whats_cooking/format/json")
    Call<List<WatsCooking_Model>> watscooking();

    @Headers("X-API-KEY:80w0g4o84wsc4gsc804c08scs00w8co4wscg848c")
    @GET("main/recipes/format/json")
    Call<List<Recipies_Model>> recipies();

    @Headers("X-API-KEY:80w0g4o84wsc4gsc804c08scs00w8co4wscg848c")
    @GET("main/recipes/id/{rid}/format/json")
    Call<List<Recipies_Details_Model>> recipie_details(@Path("rid") String rid);

    @Headers("X-API-KEY:80w0g4o84wsc4gsc804c08scs00w8co4wscg848c")
    @GET("main/orders/format/json")
    Call<List<Orders_Model>> ordered_products(@Query("identity") String identity);

    @Headers("X-API-KEY:80w0g4o84wsc4gsc804c08scs00w8co4wscg848c")
    @GET("main/orders/format/json")
    Call<Orders_Items_Response> ordered_item_details(@Query("identity") String identity, @Query("order_no") String order_no);

    @GET("main/stats")
    Call<StatsResponseModel> getStats();

}
