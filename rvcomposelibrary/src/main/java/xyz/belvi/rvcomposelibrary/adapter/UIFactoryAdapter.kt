package xyz.belvi.rvcomposelibrary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import co.tractionapp.traction.factory.adapter.UIFactoryViewHolder
import xyz.belvi.rvcomposelibrary.Field

open class UIFactoryAdapter(
    private val displayedFields: MutableList<Field>,
    private var factoryEventListener: (field: Field) -> Unit
) : RecyclerView.Adapter<UIFactoryViewHolder>(), Filterable {



    private val completeFields: MutableList<Field> = arrayListOf()

    override fun getFilter(): Filter {
        return FilterFields()
    }


    override fun getItemViewType(position: Int): Int {
        return displayedFields[position].layout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UIFactoryViewHolder {
        return UIFactoryViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))
    }

    override fun getItemCount(): Int {
        return displayedFields.size
    }

    override fun onBindViewHolder(holder: UIFactoryViewHolder, position: Int) {
        holder.bind(displayedFields[position], factoryEventListener)
    }


    fun setEventAdapter(factoryEventListener: (field: Field) -> Unit) {
        this.factoryEventListener = factoryEventListener
    }

    fun updateProduct(uiModels: MutableList<Field>) {
        this.completeFields.clear()
        this.displayedFields.clear()
        this.completeFields.addAll(uiModels)
        this.displayedFields.addAll(uiModels)
        notifyDataSetChanged()
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
        val field = displayedFields.find { it.key == key }
        return field?.let { displayedFields.indexOf(field) } ?: kotlin.run { -1 }
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