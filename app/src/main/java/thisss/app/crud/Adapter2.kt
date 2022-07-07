package thisss.app.crud

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import thisss.app.crud.Model.AppModel
import thisss.app.crud.databinding.Recycler2Binding
import thisss.app.crud.databinding.RecyclerBinding

class Adapter2(private var items: List<AppModel>) :
    RecyclerView.Adapter<Adapter2.ViewHolder>() {

    class ViewHolder(binding: Recycler2Binding) :
        RecyclerView.ViewHolder(binding.root) {
        var bindings: Recycler2Binding = binding
        fun bindIdea(dataPor: AppModel) {
            itemView.apply {
                bindings.appName.text = dataPor.packageName
                bindings.status.text = dataPor.status
                bindings.url.text = dataPor.url
                bindings.date.text = dataPor.date
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recycler2, parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items[position]
        holder.bindIdea(data)

    }

    override fun getItemCount(): Int {
        return items.size
    }
}
