package com.github.lcdsmao.pagingcarousel

import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
  public override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val pagingController = TestController()
    val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.adapter = pagingController.adapter

    val viewModel = ViewModelProviders.of(this).get(ActivityViewModel::class.java)
    viewModel.pagedList1.observe(this, Observer {
      pagingController.submitHorizontalList1(it)
    })
    viewModel.pagedList2.observe(this, Observer {
      pagingController.submitHorizontalList2(it)
    })
    viewModel.pagedList3.observe(this, Observer {
      pagingController.submitVerticalList(it)
    })
  }
}

class ActivityViewModel(app: Application) : AndroidViewModel(app) {

  private val pagedConfig = PagedList.Config.Builder()
    .setPageSize(20)
    .build()

  private val boundaryCallback = object : PagedList.BoundaryCallback<User>() {
    override fun onZeroItemsLoaded() {
      GlobalScope.launch {
        val users = (1..40).map {
          User(it)
        }
        db.runInTransaction {
          db.userDao().insertAll(users)
        }
      }
    }

    override fun onItemAtEndLoaded(itemAtEnd: User) {
      val id = itemAtEnd.uid
      GlobalScope.launch {
        val user = (id..(id + 20)).map {
          User(it)
        }
        db.runInTransaction {
          db.userDao().insertAll(user)
        }
      }
    }
  }

  val db by lazy {
    Room.inMemoryDatabaseBuilder(app, PagingDatabase::class.java).build()
  }
  val pagedList1: LiveData<PagedList<User>> by lazy {
    createLivePagedList()
  }

  val pagedList2: LiveData<PagedList<User>> by lazy {
    createLivePagedList()
  }

  val pagedList3: LiveData<PagedList<User>> by lazy {
    createLivePagedList()
  }

  private fun createLivePagedList(): LiveData<PagedList<User>> {
    return LivePagedListBuilder(db.userDao().dataSource, pagedConfig)
      .setBoundaryCallback(boundaryCallback)
      .build()
  }
}

