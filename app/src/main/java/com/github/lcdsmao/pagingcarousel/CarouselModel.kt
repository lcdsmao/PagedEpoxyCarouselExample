package com.github.lcdsmao.pagingcarousel

import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.airbnb.epoxy.paging.PagedListEpoxyController

@EpoxyModelClass(layout = R.layout.model_carousel)
abstract class CarouselModel : EpoxyModelWithHolder<CarouselModel.CarouselHolder>() {

  @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
  lateinit var controller: CarouselController

  override fun bind(holder: CarouselHolder) {
    holder.carousel.setController(controller)
  }

  override fun unbind(holder: CarouselHolder) {
    holder.carousel.clear()
  }

  override fun shouldSaveViewState(): Boolean {
    return true
  }

  class CarouselHolder : KotlinEpoxyHolder() {
    val carousel by bind<Carousel>(R.id.carousel)
  }

  class CarouselController : PagedListEpoxyController<User>() {

    override fun buildItemModel(currentPosition: Int, item: User?): EpoxyModel<*> {
      return if (item == null) {
        PagingHorizontalViewModel_()
          .id(-currentPosition)
          .name("loading $currentPosition")
      } else {
        PagingHorizontalViewModel_()
          .id(item.uid)
          .name("${item.uid}: ${item.firstName} / ${item.lastName}")
      }
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
      pagingHorizontalView {
        id("header")
        name("showing ${models.size} items")
      }
      super.addModels(models)
    }
  }
}
