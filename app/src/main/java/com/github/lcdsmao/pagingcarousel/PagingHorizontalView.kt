package com.github.lcdsmao.pagingcarousel

import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp

@ModelView(autoLayout = ModelView.Size.WRAP_WIDTH_WRAP_HEIGHT)
class PagingHorizontalView(context: Context) : AppCompatTextView(context) {

  @TextProp
  fun name(name: CharSequence) {
    text = name
  }
}
