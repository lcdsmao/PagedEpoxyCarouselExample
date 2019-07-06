package com.github.lcdsmao.pagingcarousel

import androidx.paging.PagedList
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController

class TestController : PagedListEpoxyController<User>() {

  private val controller1 = CarouselModel.CarouselController()
  private val controller2 = CarouselModel.CarouselController()

  override fun buildItemModel(currentPosition: Int, item: User?): EpoxyModel<*> {
    return if (item == null) {
      PagingVerticalViewModel_()
        .id(-currentPosition)
        .name("loading $currentPosition")
    } else {
      PagingVerticalViewModel_()
        .id(item.uid)
        .name("${item.uid}: ${item.firstName} / ${item.lastName}")
    }
  }

  override fun addModels(models: List<EpoxyModel<*>>) {

    carousel {
      id("carousel_1")
      controller(controller1)
    }

    carousel {
      id("carousel_2")
      controller(controller2)
    }

    pagingVerticalView {
      id("header")
      name("showing ${models.size} items")
    }
    super.addModels(models)
  }

  init {
    isDebugLoggingEnabled = true
  }

  override fun onExceptionSwallowed(exception: RuntimeException) {
    throw exception
  }

  fun submitVerticalList(pagedList: PagedList<User>) {
    submitList(pagedList)
  }

  fun submitHorizontalList1(pagedList: PagedList<User>) {
    controller1.submitList(pagedList)
  }

  fun submitHorizontalList2(pagedList: PagedList<User>) {
    controller2.submitList(pagedList)
  }
}
