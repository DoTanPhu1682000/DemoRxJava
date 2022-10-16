package com.dotanphu.demorxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dotanphu.demorxjava.adapter.ItemsAdapter
import com.dotanphu.demorxjava.databinding.ActivityMainBinding
import com.dotanphu.demorxjava.model.Item
import com.dotanphu.demorxjava.model.ListItems
import com.dotanphu.demorxjava.network.ItemsClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

private const val TAG = "ActivityMain"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ItemsAdapter
    private var itemsList = arrayListOf<Item>()
    private val compositeDisposable = CompositeDisposable()

    private var mItems: ListItems? = null
     
    private val randomInteger = (1..120).shuffled().first()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRV()
        getAllItemsWithRx()
    }

    private fun getAllItemsWithRx() {
        compositeDisposable.add(ItemsClient.invoke().getAllItemsWithRx()
            .concatMapIterable {t: ListItems->
                t.items
            }
            .filter{
                it.has_issues
            }
            .map {
                it.age = (0..120).random()
                it
            }
            .sorted { o1, o2 ->
                Integer.compare(o1.age, o2.age)
            }
            .groupBy {
                val ageGroupBy: Int = it.age
                if (ageGroupBy < 40) {
                    1
                    println("Result: 1 $ageGroupBy")
                } else if (ageGroupBy < 81) {
                    2
                    println("Result: 2 $ageGroupBy")
                } else {
                    3
                    println("Result: 3 $ageGroupBy")
                }
            }
            .concatMapSingle {
                it.toList()
            }
//            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                itemsList.addAll(it)
                adapter.notifyDataSetChanged()
            },{

            })
        )
    }

    private fun setupRV() {
        adapter = ItemsAdapter(itemsList)
        binding.rvMain.adapter = adapter
        binding.rvMain.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

}
