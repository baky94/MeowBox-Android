package woo.sopt22.meowbox.Network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*
import woo.sopt22.meowbox.Model.Base.BaseModel
import woo.sopt22.meowbox.Model.Login.LoginResponse
import woo.sopt22.meowbox.Model.Login.LoginUser
import woo.sopt22.meowbox.Model.MyAccountSetting.MyAccountSettingGet
import woo.sopt22.meowbox.Model.MyPageMain.MyPageYes
import woo.sopt22.meowbox.Model.Order.OrderData
import woo.sopt22.meowbox.Model.Order.OrderHistory.OrderHistory
import woo.sopt22.meowbox.Model.Order.OrderHistoryDetail.OrderHistoryDetail
import woo.sopt22.meowbox.Model.QnA.QnAResponse
import woo.sopt22.meowbox.Model.RegisterCat.CatIndex
import woo.sopt22.meowbox.Model.SignUp.SignUpUser
import woo.sopt22.meowbox.Model.RegisterCat.CatInformation
import woo.sopt22.meowbox.Model.Suggest.MeowBoxSuggest

interface NetworkService {

    // 1. 회원가입 - 0
    @POST("user/signup")
    fun postSignUp(
            @Body singUser: SignUpUser
    ) : Call<LoginResponse>

    // 2. 로그인 - 0
    @POST("user/signin")
    fun postSignIn(
            @Body loginUser: LoginUser
    ) : Call<LoginResponse>


    // 3. 고양이 등록 - 0
    //@FormUrlEncoded
    @POST("user/cat_signup")
    fun registerCat(
            @Header("authorization") authorization : String,
            @Body catInformation: CatInformation
    ) : Call<CatIndex>

    // 4. 회원탈퇴 - 0
    @HTTP(method = "DELETE", path = "user/account", hasBody = false)
    //@DELETE("user/account")
    fun deleteUser(
            @Header("authorization") authorization : String
    ):   Call<BaseModel>

    // 5. 주문 내역 - 0
    @GET("order/order_list/")
    fun getOrderHistory(
            @Header("authorization") authorization : String
    ) : Call<OrderHistory>

    // 6. 미유박스에 제안하기 - 0
    @POST("mypage/feedback")
    fun postSuggest(
            @Header("authorization") authorization : String,
            @Body meowBoxSuggest: MeowBoxSuggest
    ) : Call<BaseModel>



    //7. 마이페이지-1
    @GET("mypage/mypageinfo/")
    fun getMyPageYes(
            @Header("authorization") authorization: String
    ) : Call<MyPageYes>


    // 8. Q&A 질문 불러오기 - 때려박기
    @GET("mypage/qna")
    fun getQnA(
            @Header("authorization") authorization : String
    ) : Call<QnAResponse>


    //?.get계정설정화면
    @GET("mypage/account/{user_idx}")
    fun getMyAccountSetting(
            @Header("authorization") authorization: String,
            @Path("user_idx") user_idx: String
    ) : Call<MyAccountSettingGet>

    // 10. 주문 페이지 - 0
    @POST("order/order_page")
    fun postOrder(
            @Header("authorization") authorization : String,
            @Body orderData: OrderData
    ) : Call<BaseModel>

    // 11. 주문내역 상세보기
    @POST("order/order_detail")
    fun postOrderDetail(
            @Header("authorization") authorization : String
    ) : Call<OrderHistoryDetail>


    // 12. 정기권 취소
    @HTTP(method = "DELETE", path = "order/order_list{order_idx}", hasBody = false)
    fun deleteSeasonTicket(

    ) : Call<BaseModel>


}