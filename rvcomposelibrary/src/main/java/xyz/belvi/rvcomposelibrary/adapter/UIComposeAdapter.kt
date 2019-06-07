package xyz.belvi.rvcomposelibrary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import co.tractionapp.traction.factory.adapter.UIComposeViewHolder
import xyz.belvi.rvcomposelibrary.models.Field

open class UIComposeAdapter(
    private val displayedFields: MutableList<Field>,
    private var event: (uiComposeAdapter: UIComposeAdapter,field: Field, position: Int) -> Unit
) : RecyclerView.Adapter<UIComposeViewHolder>(), Filterable {

    private var search = ""

    private val completeFields: MutableList<Field> = arrayListOf()

    override fun getFilter(): Filter {
        return FilterFields()
    }


    override fun getItemViewType(position: Int): Int {
        return displayedFields[position].layout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UIComposeViewHolder {
        return UIComposeViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))
    }

    override fun getItemCount(): Int {
        return displayedFields.size
    }

    override fun onBindViewHolder(holder: UIComposeViewHolder, position: Int) {
        holder.bind(displayedFields[position], this, position, event)
    }

    fun updateFields(uiModels: MutableList<Field>) {
        this.completeFields.clear()
        this.completeFields.addAll(uiModels)
        filter.filter(search)
    }

    fun items(): MutableList<Field> {
        return displayedFields
    }

    fun fieldWithKey(key: String): Field? {
        return displayedFields.find { it.key == key }
    }

    fun fieldIndex(field: Field): Int {
        return displayedFields.indexOfFirst { field.key == it.key }
    }

    fun fieldIndexWithKey(key: String): Int {
        return displayedFields.indexOfFirst { it.key == key }
    }

    fun isFormValid(): Boolean {
        var result = true
        displayedFields.forEach {
            result = result && it.hasValidData()
        }
        return result
    }


    fun formWarning(): String {
        displayedFields.forEach {
            if (!it.hasValidData())
                return it.errorMessage
        }
        return ""
    }


    inner class FilterFields : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            return performSearch(constraint)
        }


        private fun performSearch(constraint: CharSequence?): FilterResults {
            search = constraint?.toString() ?: ""
            val filterResult = FilterResults()

            val result = constraint?.toString()?.let { text ->
                if (text.isBlank()) completeFields else completeFields.filter {
                    it.matchSearch(text)
                }
            } ?: kotlin.run { completeFields }

            filterResult.count = result.size
            filterResult.values = result

            return filterResult
        }


        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            displayedFields.clear()
            displayedFields.addAll(results?.values as? MutableList<Field> ?: kotlin.run { mutableListOf<Field>() })
            notifyDataSetChanged()
        }
    }


}