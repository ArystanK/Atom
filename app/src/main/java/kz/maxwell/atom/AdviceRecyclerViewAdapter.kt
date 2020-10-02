package kz.maxwell.atom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.advice_list_item.view.*
import kz.maxwell.atom.models.Advice

class AdviceRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Advice> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AdviseViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.advice_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AdviseViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(list: List<Advice>) {
        items = list
    }

    class AdviseViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val adviceTitle: TextView = itemView.adviceTitleTextView
        private val adviceImage: ImageView = itemView.adviceImageView
        private val adviceBody: TextView = itemView.adviceBodyTextView

        fun bind(advice: Advice) {
            adviceTitle.text = advice.title
            adviceBody.text = advice.body

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(advice.image)
                .into(adviceImage)
        }
    }
}