# RVcompose

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/ef7adb1d9be44ed0848cd9f7a06d43f5)](https://app.codacy.com/app/KingsMentor/RVcompose?utm_source=github.com&utm_medium=referral&utm_content=KingsMentor/RVcompose&utm_campaign=Badge_Grade_Dashboard)
[![Download](https://api.bintray.com/packages/kingsmentor/maven/rvcomposelibrary/images/download.svg) ](https://bintray.com/kingsmentor/maven/rvcomposelibrary/_latestVersion)

*RVcompose*: an easy-to-use, extensible Kotlin DSL for building dynamic reusable UI components with RecycerView

<img src="https://raw.githubusercontent.com/KingsMentor/RVcompose/master/arts/rvcompose.png" width="100%" />

---


## Gradle Dependency

Add this to your module's `build.gradle` file:

```gradle
dependencies {

  implementation 'xyz.belvi:rvcompose:rvcomposelibrary:1.0.1'
}
```

---

## The Basics

**First**, a layout:

```xml
<LinearLayout ...>

  <TextView 
     android:id="@+id/text_name"
     ... />    
     
  <TextView 
     android:id="@+id/text_age"
     ... />
     
</LinearLayout>
```

**Second**, a View Holder:

```kotlin
data class InputField(
    var hint: String = "",
    var text: String = ""
) :
    Field( R.layout.item_input) {


    override fun getValue(): String {
        return text
    }

    override fun bind(
        itemView: View,
        uiComposeAdapter: UIComposeAdapter,
        position: Int,
        event: (field: Field) -> Unit
    ) {
        itemView.item_field.setText(text)
        itemView.item_field.hint = hint

    }

    override fun hasValidData(): Boolean {
        return validation?.invoke() ?: kotlin.run { if (required) !text.isBlank() else true }
    }

}
```

**Finally**, you can begin using the DSL API:

```kotlin
val rv = recycler.compose {
            withLayoutManager(LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false))
            withField<InputField> {
                hint = "Customer Email"
                key = "email"
                required = true
                validation = { Patterns.EMAIL_ADDRESS.matcher(this.text).matches() }

            }
            fieldEvent { uiComposeAdapter, field, position ->

            }
        }
```

There are two main components of RVcompose:

The Models that describe how your views should be displayed in the RecyclerView.
The Adapter where the models are used to describe what items to show , with what data and interaction between models.

**Creating Models**

Models are created by extending `Field`

```kotlin
data class NoteField(
    override val layout: Int = R.layout.item_input_note,
    var hint: String="",
    var text: String = ""
) :
    Field() {
    override fun getValue(): String {
    
    }

    override fun bind(
        itemView: View,
        uiComposeAdapter: UIComposeAdapter,
        position: Int,
        event: (field: Field) -> Unit
    ) {
   

    }

    override fun hasValidData(): Boolean {
        
    }
}
```

## Understanding the fields in a Model

`layout`  The layout to be inflated. It is the only required field when `Fieid` is extended

```kotlin
data class AdditemField(
    var text: String = ""
) :
    Field(R.layout.item_invoice_add_new_item)
```
this example shows executing email validation on InputField.

`Validation` This is important when building Forms or if you need to validated entry in the model. 

```kotlin
rvField<InputField> {
                hint = "Customer Email"
                validation = { android.util.Patterns.EMAIL_ADDRESS.matcher(this.text).matches() }
            }
```
this example shows executing email validation on InputField.

`Key` for referencing a model from the adapter. 

```kotlin
rvField<InputField> {
                hint = "Customer Email"
                key = "EMAIL_KEY"
                validation = { android.util.Patterns.EMAIL_ADDRESS.matcher(this.text).matches() }
            }
```

`required` for marking a field as required. validation is invoked ony if required is true

```kotlin
rvField<InputField> {
                hint = "Customer Email"
                required = true
                key = "EMAIL_KEY"
                validation = { android.util.Patterns.EMAIL_ADDRESS.matcher(this.text).matches() }
            }
```

`errormessage` message to be displayed when validate fails.

```kotlin
rvField<InputField>
    {
        hint = "Customer Email"
        required = true
        key = "EMAIL_KEY"
        validation = {
            val isEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(this.text).matches()
            errorMessage = if (isEmail) "" else "enter a valid email address"
            isEmail
        }
    }
```


`datasource` Datasource of this model. You can choose to pass some data as paramerter to your model. A datasource can be an database object or any object that has information for updating your view

```kotlin
rvField<InputField>
    {
        hint = "Customer Email"
        required = true
        datasource = Person()
        key = "EMAIL_KEY"
        validation = {
            val isEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(this.text).matches()
            errorMessage = if (isEmail) "" else "enter a valid email address"
            isEmail
        }
    }
```



`matchSearch()` Function for adding your model to a search result. `UIComposeAdapter` has model search implementation. To ensure your a model is considered while performing a search, override this function.

```kotlin
override fun matchSearch(text: String): Boolean {
        return this.text.contains(text)
    }
```


`getValue(): Any` Override the method to set the value the model should return.

```kotlin
data class InvoiceDateField(
    var hint: String ="",
    var date: Calendar = Calendar.getInstance()
) :
    Field( R.layout.item_invoice_date) {

    @SuppressLint("SimpleDateFormat")
    override fun getValue(): String {
        return SimpleDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'Z'").format(Date(date.timeInMillis))
    }
```


These fields are the basic requirements. You can extend `Field` and build upon the implmentation

---

## Building Models

Models are built in 2 ways when setting up rvCompose. 
### 1. withField

Used this when initialising rvCompose. This usecase applies if views are statics or known during compile time. 

```kotlin

 val rv = recycler.compose {
            withLayoutManager(LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false))
            withField<InputField> {
                hint = "Customer Email"
                key = "EMAIL_KEY"
                required = true
                validation = { Patterns.EMAIL_ADDRESS.matcher(this.text).matches() }
            }
            withField<InputField> {
                hint = "Customer Name"
                key = "NAME_KEY"
                required = true
                validation = { this.text.isNotBlank()}
            }
            fieldClicked { uiComposeAdapter, field, position ->

            }
        }

```

### 2. rvField

There are cases that requires you to build your models on runtime and update the views. In cases like this, I suggest building a factory to handle this:

```kotlin
object CustomerFactory {
    fun sampleUI(): MutableList<Field> {
        return mutableListOf<Field>().withFields {

            this += rvField<InputField> {
                hint = "Customer Email"
                key = "email"
                required = true
                validation = { android.util.Patterns.EMAIL_ADDRESS.matcher(this.text).matches() }
            }
            this += rvField<InvoiceDateField> {
                hint = "Invoice Date"
                date = Calendar.getInstance()

            }
            this += rvField<SpinnerField> {
                items = mutableListOf("Pay with Cash", "Pay with Card", "Pay some other time")
            }
            this += rvField<InvoiceDateField> {
                hint = "Due Date"
                date = Calendar.getInstance()

            }
            this += rvField<AdditemField> {
                text = "Add Item"
            }

            this += rvField<InvoiceReceiptField> {
                totalDue = 0.0
            }
            this += rvField<NoteField> {
               hint="additional information for customer"
            }
            this+= rvField<ActionField> {
                text = "Create Invoice"
            }

        }


    }


}
```

this factory returns a list of models `MutableList<Field>` the ui can be updated with :

```kotlin
rv.getAdapter().updateFields(CustomerFactory.sampleUI())
```


---

## Interaction between Models and Updating Models. 

interaction between Models can happen in `bind()`. This provide `UIComposeAdapter` that you can use to retrieve a model by key or index, update the model and reflect changes on view by any of RecyclerView notifyAdapter functions. 

```kotlin
override fun bind(
        itemView: View,
        uiComposeAdapter: UIComposeAdapter,
        position: Int,
        event: (field: Field) -> Unit
    ) {

        itemView.btn_action_field.text = text
        itemView.btn_action_field.setOnClickListener {
            (uiComposeAdapter.fieldWithKey("email") as? InputField)?.let {
                it.text = "button clicked"
                uiComposeAdapter.notifyItemChanged(uiComposeAdapter.fieldIndexWithKey(key))
            }
            
        }

    }
```


---

## Handling Event 

To handle event on `fieldClicked`, a Model needs to trigger event on `OnClick` of the inflated view. 

```kotlin
override fun bind(
        itemView: View,
        uiComposeAdapter: UIComposeAdapter,
        position: Int,
        event: (field: Field) -> Unit
    ) {
        itemView.btn_action_field.text = text
        itemView.btn_action_field.setOnClickListener {
          // you can also perform actions here
          event(this)
        }

    }
```

You can also handle event for all Models in `fieldEvent`

```kotlin
val rv = recycler.compose {
            withLayoutManager(LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false))
            fieldEvent { uiComposeAdapter, field, position ->
                // perform action 
            }
        }
```


## UICompose Functions

UIComposeAdapter provides functions for interacting and updating the models. 

`fieldWithKey()`  retrieve a Model from the adapter with a key.

`fieldIndex()`  retrieve a Model from the adapter with an index (if the index is known)

`fieldIndexWithKey()`  retrieve a Model Index from the adapter with Key

`isFormValid()`  Run validation check on Models in the adapter

`formWarning()`  returns an error message for field with failed validation

`formData()`  Returns Hashmap<key,value> for models in the adapter.

```kotlin
 val rv = recycler.compose {
            withLayoutManager(LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false))
            fieldEvent { uiComposeAdapter, field, position ->
                if (field is ActionField && uiComposeAdapter.isFormValid()) {
                    (uiComposeAdapter.fieldWithKey("email") as InputField).let {
                        it.text = "example@example.com"
                        uiComposeAdapter.notifyItemChanged(uiComposeAdapter.fieldIndexWithKey(key = it.key))
                    }

                } else {
                    Toast.makeText(this@MainActivity, uiComposeAdapter.formWarning(), Toast.LENGTH_LONG).show()
                }
            }
        }
```

## Applications Build with RVCompose
[Traction App](https://play.google.com/store/apps/details?id=co.tractionapp.traction)


# Contribution

Pull requests are welcome! We'd love help improving this library. Feel free to browse through open issues to look for things that need work. If you have a feature request or bug, please open a new issue so we can track it.
