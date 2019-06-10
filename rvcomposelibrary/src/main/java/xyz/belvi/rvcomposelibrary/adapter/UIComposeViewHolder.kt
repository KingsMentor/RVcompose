package co.tractionapp.traction.factory.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import xyz.belvi.rvcomposelibrary.adapter.UIComposeAdapter
import xyz.belvi.rvcomposelibrary.models.Field

open class UIComposeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(
        uiModel: Field,
        uiComposeAdapter: UIComposeAdapter,
        positon: Int,
        factoryEventListener: (field: Field) -> Unit
    ) {
        uiModel.bind(itemView, uiComposeAdapter, positon, factoryEventListener)
    }

}