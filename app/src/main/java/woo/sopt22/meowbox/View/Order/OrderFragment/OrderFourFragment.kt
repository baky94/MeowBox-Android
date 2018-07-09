package woo.sopt22.meowbox.View.Order.OrderFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RadioButton
import kotlinx.android.synthetic.main.order_four_fragment.*
import kotlinx.android.synthetic.main.order_four_fragment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import woo.sopt22.meowbox.ApplicationController
import woo.sopt22.meowbox.Model.Base.BaseModel
import woo.sopt22.meowbox.Model.Order.OrderData
import woo.sopt22.meowbox.Network.NetworkService
import woo.sopt22.meowbox.R
import woo.sopt22.meowbox.Util.SharedPreference
import woo.sopt22.meowbox.Util.ToastMaker
import woo.sopt22.meowbox.View.Order.OrderFirstActivity
import woo.sopt22.meowbox.View.Order.OrderFragmentWithOutCatInfo.WithOutCatInfoFive
import woo.sopt22.meowbox.View.Order.OrderThirdActivity

class OrderFourFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!){
            order_pay_previous->{
                (OrderFirstActivity.mContext as OrderFirstActivity).replaceFragment(OrderThirdFragment())
            }
            order_pay_next->{
                ToastMaker.makeLongToast(context, radio_button.text.trim().toString())
                Log.v("48",radio_button.text.toString())
                postOrder()
            }
        }
    }

    var radio_id : Int=0
    lateinit var radio_button : RadioButton
    lateinit var box_type : String
    lateinit var orderData: OrderData
    lateinit var networkService: NetworkService
    lateinit var price : String
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.order_four_fragment, container, false)
        networkService = ApplicationController.instance!!.networkService
        SharedPreference.instance!!.load(context!!)

        activity!!.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        view.order_pay_previous.setOnClickListener(this)
        view.order_pay_next.setOnClickListener(this)
        price = SharedPreference.instance!!.getPrefStringData("price")!!
        view.order_four_total_charge.text = price
        box_type = SharedPreference.instance!!.getPrefStringData("box_type")!!

        radio_id = view.order_four_pay_method.checkedRadioButtonId

        radio_button = view.findViewById<View>(radio_id!!) as RadioButton
        radio_button.setOnClickListener(this)
        return view
    }

    fun postOrder(){
        orderData = OrderData(SharedPreference.instance!!.getPrefStringData("cat_name")!!, order_four_address_one.text.toString()+order_four_address_two.text.toString(), order_four_phone_number.text.toString(), box_type,
                price, order_four_email.text.toString(),radio_button.text.toString())
        val orderRespone = networkService.postOrder(SharedPreference.instance!!.getPrefStringData("token")!!,orderData)
        orderRespone.enqueue(object : Callback<BaseModel> {
            override fun onFailure(call: Call<BaseModel>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<BaseModel>?, response: Response<BaseModel>?) {
                if(response!!.isSuccessful){
                    Log.v("412",response!!.message())
                    (OrderFirstActivity.mContext as OrderFirstActivity).replaceFragment(OrderFiveFragment())
                }
            }

        })
    }
}