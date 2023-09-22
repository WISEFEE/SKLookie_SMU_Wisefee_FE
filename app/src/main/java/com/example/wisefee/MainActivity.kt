package com.example.wisefee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.wisefee.Mypage.MyPageActivity
import com.example.wisefee.Return.ReturnTumblerActivity
import com.example.wisefee.SearchingStores.SearchingStores
import com.example.wisefee.databinding.ActivityMainBinding
import com.example.wisefee.databinding.ItemNoticeBinding

class MainActivity : AppCompatActivity() {

    lateinit var handler: Handler
    lateinit var handlerThread: HandlerThread
    private lateinit var binding: ActivityMainBinding

    // 나중에 서버에서 받을 것. 목데이터
    private val noticeList = listOf(
        "\uD83D\uDE18\uD83D\uDCE2 오늘같이 무더운 날, 수박주스는 어떠세요?",
        "공지사항2",
        "공지사항3",
        // ... 공지사항 리스트 데이터 추가 ...
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = NoticeAdapter(noticeList)
        binding.eventViewPager.adapter = adapter


        handlerThread = HandlerThread("NoticeThread")
        handlerThread.start()
        handler = Handler(handlerThread.looper)

        handler.postDelayed(object : Runnable {
            override fun run() {
                val currentPosition = binding.eventViewPager.currentItem
                if (currentPosition < adapter.itemCount - 1) {
                    binding.eventViewPager.currentItem = currentPosition + 1
                } else {
                    binding.eventViewPager.currentItem = 0
                }
                handler.postDelayed(this, 5000) // 5초마다 공지사항 전환 (조절 가능)
            }
        }, 5000) // 5초 후에 실행 시작 (조절 가능)

        binding.home.setColorFilter(ContextCompat.getColor(this, R.color.selection_color))
        binding.rental.setOnClickListener { startActivity(Intent(this, SearchingStores::class.java)) }
        binding.returnTumbler.setOnClickListener { startActivity(Intent(this, ReturnTumblerActivity::class.java)) }
        binding.mypage.setOnClickListener { startActivity(Intent(this, MyPageActivity::class.java)) }

        // 매번 보여줄 건지, 1번만 보여줄건지
//        initView()
    }

    override fun onDestroy() {

        super.onDestroy()
        // 핸들러 종료
        handler.removeCallbacksAndMessages(null)
        handlerThread.quit()
    }
}


class NoticeAdapter(private val noticeList: List<String>) : RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder>() {

    inner class NoticeViewHolder(private val binding: ItemNoticeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val noticeTextView = binding.noticeTextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNoticeBinding.inflate(inflater, parent, false)
        return NoticeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        val notice = noticeList[position]
        holder.noticeTextView.text = notice
    }

    override fun getItemCount(): Int {
        return noticeList.size
    }
}