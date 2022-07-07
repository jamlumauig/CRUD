package thisss.app.crud

import android.view.View

interface StringOnClick {
    fun onAdapterClick(positon: Int)
    fun onItemLongClick(position: Int, v: View?)
}