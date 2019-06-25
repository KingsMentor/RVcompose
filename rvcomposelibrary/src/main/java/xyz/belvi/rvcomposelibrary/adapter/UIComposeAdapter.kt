package xyz.belvi.rvcomposelibrary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import xyz.belvi.rvcomposelibrary.models.Field

open class UIComposeAdapter(
    private val displayedFields: MutableList<Field>,
    private var event: (uiComposeAdapter: UIComposeAdapter, field: Field, position: Int) -> Unit
) : RecyclerView.Adapter<UIComposeViewHolder>(), Filterable {

    private var search = ""
    private var formWarning = ""

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
        holder.bind(displayedFields[position], this, position) {
            event(this, it, position)
        }
    }

    /**
     * Update the models in this adapter
     */
    fun updateFields(uiModels: MutableList<Field>) {
        this.completeFields.clear()
        this.completeFields.addAll(uiModels)
        filter.filter(search)
    }

    /**
     * retrieve models in this adapter
     */
    fun models(): MutableList<Field> {
        return displayedFields
    }

    /**
     * return a @Field with @key in @UIComposeAdapter
     */
    fun fieldWithKey(key: String): Field? {
        return displayedFields.find { it.key == key }
    }

    /**
     * return the position of @field in this adapter @UIComposeAdapter
     */
    fun fieldIndex(field: Field): Int {
        return displayedFields.indexOfFirst { field.key == it.key }
    }

    /**
     * return a model with key @key
     */
    fun fieldIndexWithKey(key: String): Int {
        return displayedFields.indexOfFirst { it.key == key }
    }

    /**
     * Perform validation check on all models
     */
    fun isFormValid(): Boolean {
        formWarning = ""
        displayedFields.forEach {
            if (!it.hasValidData()) {
                formWarning = it.errorMessage
                return false
            }

        }
        return true
    }

    /**
     * error message of model with failed validation. This returns empty if @isFormValid() has not been called at least one.
     * To ensure this returns the right message, call @isFormValid before calling this method
     */
    fun formWarning(): String {
        return formWarning
    }

    /**
     * returns a map of keys and value of the models
     */

    fun formData(): HashMap<String, Any> {
        return completeFields.associateTo(hashMapOf()) { it.key to it.getValue() }
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