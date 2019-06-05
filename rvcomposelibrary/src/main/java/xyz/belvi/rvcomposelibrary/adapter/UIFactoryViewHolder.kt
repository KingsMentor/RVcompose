package co.tractionapp.traction.factory.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import xyz.belvi.rvcomposelibrary.Field
import xyz.belvi.rvcomposelibrary.UIField

open class UIFactoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(uiModel: Field, factoryEventListener: (field: Field) -> Unit) {
        uiModel.bind(itemView,factoryEventListener)
    }

}