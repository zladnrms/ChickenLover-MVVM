package defy.tech.chickenlover.network

import com.google.gson.GsonBuilder
import defy.tech.chickenlover.BuildConfig
import defy.tech.chickenlover.network.data.*
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface RetrofitInterface {

    companion object {
        fun create(): RetrofitInterface {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor { chain -> chain.proceed(chain.request()) }
                .addInterceptor(interceptor)
                .build()

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.SERVER_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(RetrofitInterface::class.java)
        }
    }

    /* Of User (include Guest) */
    @FormUrlEncoded
    @POST("/chickenlover/member/join_as_guest.php")
    fun joinAsGuest(@Field("mobile") mobile: String): Single<JoinResponse>

    @FormUrlEncoded
    @POST("/chickenlover/member/login_as_guest.php")
    fun loginAsGuest(@Field("mobile") mobile: String, @Field("hashed_value") hashed_value: String?): Single<LoginGuestResponse>

    @FormUrlEncoded
    @POST("/chickenlover/member/join_as_normal.php")
    fun joinAsNormal(@Field("mobile") mobile: String, @Field("login_type") login_type: Int, @Field("id") id: String, @Field("password") password: String, @Field("pre_name") pre_name: String?, @Field("next_name") next_name: String): Single<JoinResponse>

    @FormUrlEncoded
    @POST("/chickenlover/member/login_as_auto.php")
    fun loginAsAuto(@Field("mobile") mobile: String, @Field("login_type") login_type: Int, @Field("hashed_value") hashed_value: String?): Single<LoginGuestResponse>

    @FormUrlEncoded
    @POST("/chickenlover/member/login_as_normal.php")
    fun loginAsMember(@Field("mobile") mobile: String, @Field("login_type") login_type: Int, @Field("id") id: String?, @Field("password") password: String?): Single<LoginMemberResponse>

    /* Of Chicken Select Contents */
    @FormUrlEncoded
    @POST("/chickenlover/mobile/info/get_chicken_info.php")
    fun getChickenInfo(@Field("way") way: String, @Field("brand") brand: String?, @Field("type") type: String?): Single<RandomChickenResponse>


    /* Of Board */
    @FormUrlEncoded
    @POST("/chickenlover/mobile/board/article/get_board_article_list.php")
    fun getBoardArticleList(@Field("type") type: String?, @Field("index") index: Int, @Field("limit") limit: Int): Single<BoardArticleListResponse>

    @FormUrlEncoded
    @POST("/chickenlover/mobile/board/article/get_board_article.php")
    fun getBoardArticle(@Field("type") type: String, @Field("a_id") a_id: Int?, @Field("title") title: String?): Single<BoardArticleResponse>

    @FormUrlEncoded
    @POST("/chickenlover/mobile/board/comment/get_board_comment.php")
    fun getBoardComment(@Field("type") type: String, @Field("c_id") comment_id: Int?): Single<BoardCommentResponse>

    @Multipart
    @POST("/chickenlover/mobile/board/article/write_board_article.php")
    fun writeBoardArticle(@Part("type") type: RequestBody, @Part("name") name: RequestBody, @Part("title") title: RequestBody, @Part("content") content: RequestBody, @Part parts : List<MultipartBody.Part>): Observable<WriteArticleResponse>

    @FormUrlEncoded
    @POST("/chickenlover/mobile/board/comment/write_board_comment.php")
    fun writeBoardComment(@Field("type") type: String, @Field("a_id") a_id: Int?, @Field("name") name: String?, @Field("content") content: String?): Single<WriteCommentResponse>
}