package kz.maxwell.atom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.purchase_list_item.view.*
import kz.maxwell.atom.models.Purchase

class PurchaseRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Purchase> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PurchaseViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.purchase_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PurchaseViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    fun submitList(purchaseList: List<Purchase>) {
        items = purchaseList
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class PurchaseViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val purchaseImage: ImageView = itemView.purchaseImageView
        private val companyName: TextView = itemView.companyNameTextView
        private val purchaseTitle: TextView = itemView.purchaseTitleTextView
        private val purchasePrice: TextView = itemView.purchasePriceTextView

        fun bind(purchase: Purchase) {
            companyName.text = purchase.company
            purchaseTitle.text = purchase.title
            purchasePrice.text = purchase.price.toString()

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(purchase.image)
                .into(purchaseImage)
        }
    }
}