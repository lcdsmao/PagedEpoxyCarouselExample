package com.github.lcdsmao.pagingcarousel

import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class PagingVerticalView(context: Context) : AppCompatTextView(context) {

  @TextProp
  fun name(name: CharSequence) {
    text = name
  }
}
