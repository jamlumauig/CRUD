package thisss.app.crud

import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import thisss.app.crud.Model.AppModel
import thisss.app.crud.databinding.RecyclerBinding

class Adapter(private var items: List<AppModel>, var stringOnClick: StringOnClick) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    class ViewHolder(
        binding: RecyclerBinding,
        var LongClick: StringOnClick,
        var clickData: StringOnClick
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnLongClickListener, View.OnClickListener {
        var bindings: RecyclerBinding = binding
        fun bindIdea(dataPor: AppModel) {

            itemView.apply {
                bindings.appName.text = dataPor.packageName
                bindings.status.text = dataPor.status
                bindings.url.text = dataPor.url
                bindings.date.text = dataPor.date

               // setOnClickListener(this@ViewHolder)
                bindings.edit.setOnClickListener(this@ViewHolder)
                setOnLongClickListener(this@ViewHolder)
            }
        }

        override fun onClick(v: View?) {
            clickData.onAdapterClick(adapterPosition)
        }

        override fun onLongClick(p0: View?): Boolean {
            LongClick.onItemLongClick(adapterPosition, p0)
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recycler, parent, false
            ), stringOnClick,stringOnClick
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items[position]
        holder.bindIdea(data)

    }

    override fun getItemCount(): Int {
        return items.size
    }
}
