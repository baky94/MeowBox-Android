package woo.sopt22.meowbox.View.MyPage.OrderHistory

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_order_history.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import woo.sopt22.meowbox.ApplicationController
import woo.sopt22.meowbox.Model.Order.OrderHistory.OrderHistory
import woo.sopt22.meowbox.Model.Order.OrderHistory.ticketData
import woo.sopt22.meowbox.Network.NetworkService
import woo.sopt22.meowbox.R
import woo.sopt22.meowbox.Util.SharedPreference
import woo.sopt22.meowbox.View.MyPage.OrderHistory.Adapter.OrderHistoryItemAdapter
import woo.sopt22.meowbox.View.MyPage.OrderHistory.OrderHistoryDetail.OrderHistoryDetailActivity
import woo.sopt22.meowbox.View.MyPage.OrderHistory.OrderHistoryDetail.OrderHistoryDetailHeaderActivity

class OrderHistoryActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!) {
            order_history_x_btn -> {
                finish()
            }
            history_ticket_image -> {
                val intent = Intent(this, OrderHistoryDetailHeaderActivity::class.java)
                intent.putExtra("term", history_payment_date.text.toString())
                intent.putExtra("name", history_payment_name.text.toString())
                startActivity(intent)
            }
            v!! -> {
                val order_idx: Int = order_history_rv.getChildAdapterPosition(v!!)
                val intent = Intent(this, OrderHistoryDetailActivity::class.java)
                intent.putExtra("order_idx", order_idx)
                intent.putExtra("term", ticketed_items[order_idx].term)
                intent.putExtra("product", ticketed_items[order_idx].product)
                startActivity(intent)
            }

        }
    }


    lateinit var ticketed_items: ArrayList<ticketData>
    //lateinit var order_header_history_items : ArrayList<>
    lateinit var networkService: NetworkService
    //lateinit var orderHistoryAdapter : OrderHistoryAdapter
    lateinit var orderHistoryItemAdapter: OrderHistoryItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)


        order_history_x_btn.setOnClickListener(this)
        history_ticket_image.setOnClickListener(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            window.statusBarColor = Color.BLACK

        networkService = ApplicationController.instance!!.networkService
        SharedPreference.instance!!.load(this)

        /*     if(SharedPreference.instance!!.getPrefStringData("flag") == "-1"){
            hidden_layout.visibility = View.VISIBLE
        } else{
            hidden_layout.visibility = View.GONE
        }*/

        getOrderHistroy()

    }

    fun getOrderHistroy() {
        Log.v("09", "09")
        val orderHistoryResponse = networkService.getOrderHistory(SharedPreference.instance!!.getPrefStringData("token")!!)
        Log.v("19", "19")
        orderHistoryResponse.enqueue(object : Callback<OrderHistory> {
            override fun onFailure(call: Call<OrderHistory>?, t: Throwable?) {
                Log.v("order", t!!.message)

            }

            override fun onResponse(call: Call<OrderHistory>?, response: Response<OrderHistory>?) {
                if (response!!.isSuccessful) {
                    if (response!!.body()!!.result!!.ticket.idx == null && response!!.body()!!.result!!.ticketed.size == 0) {
                        Log.v("799", response!!.body()!!.result!!.ticket.toString())
                        Log.v("799", response!!.body()!!.result!!.ticketed.size.toString())
                        //order_history_layout.visibility = View.GONE
                        hidden_layout1.visibility = View.VISIBLE
                        history_nested.visibility = View.GONE

                    }
                     /*   if (response!!.body()!!.result!!.ticketed.size == 0) {
                            Log.v("899","여ㅑ기인가?")
                            hidden_layout1.visibility = View.VISIBLE
                            hidden_layout2.visibility = View.GONE

                        }*/ else {
                            hidden_layout1.visibility = View.GONE
                            history_nested.visibility = View.VISIBLE
                            Log.v("999", response!!.body()!!.result!!.ticketed.toString())
                            ticketed_items = response!!.body()!!.result!!.ticketed
                            //SharedPreference.instance!!.setPrefData("idx", response!!.body()!!.result.ticketed)
                            history_payment_date.text = response!!.body()!!.result!!.ticket.term
                            history_payment_name.text = response!!.body()!!.result!!.ticket.product
                            orderHistoryItemAdapter = OrderHistoryItemAdapter(ticketed_items, this@OrderHistoryActivity)
                            orderHistoryItemAdapter.setOnItemClickListener(this@OrderHistoryActivity)
                            order_history_rv.layoutManager = LinearLayoutManager(this@OrderHistoryActivity)
                            order_history_rv.adapter = orderHistoryItemAdapter
                            Log.v("299", response!!.body()!!.result!!.ticketed.toString())
                        }
                    }


                }


        })

    }
}