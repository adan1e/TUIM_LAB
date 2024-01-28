package pack.sor.carnote;
import java.util.List;

import pack.sor.carnote.model.AutoData;
import pack.sor.carnote.model.CollisionRecord;
import pack.sor.carnote.model.RepairRecord;
import pack.sor.carnote.model.TankUpRecord;
import pack.sor.carnote.model.UserData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitAPI {

    @POST("/api/Repair/")
        //on below line we are creating a method to post our data.
    Call<RepairRecord> createPost(@Body RepairRecord repairRecord);

    @POST("/api/Car/")
    Call<AutoData> createPost(@Body AutoData autoData);

    @POST("/api/User/")
    Call<UserData> createPost(@Body UserData userData);

    @POST("/api/Collision/")
    Call<CollisionRecord> createPost(@Body CollisionRecord collisionRecord);

    @POST("/api/TankUp/")
    Call<TankUpRecord> createPost(@Body TankUpRecord tankUpRecord);

    @GET("/api/User/")
    Call<List<UserData>> getUsers();
}

