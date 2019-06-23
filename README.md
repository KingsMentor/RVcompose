# RVcompose

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/ef7adb1d9be44ed0848cd9f7a06d43f5)](https://app.codacy.com/app/KingsMentor/RVcompose?utm_source=github.com&utm_medium=referral&utm_content=KingsMentor/RVcompose&utm_campaign=Badge_Grade_Dashboard)
[ ![Download](https://api.bintray.com/packages/kingsmentor/maven/rvcomposelibrary/images/download.svg) ](https://bintray.com/kingsmentor/maven/rvcomposelibrary/_latestVersion)

*RVcompose*: an easy-to-use, extensible Kotlin DSL for building dynamic reusable UI components with RecycerView

<img src="https://raw.githubusercontent.com/KingsMentor/RVcompose/master/arts/rvcompose.png" width="100%" />

---


# Core

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
            fieldClicked { uiComposeAdapter, field, position ->

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

---


**layout** 
The layout to be inflated. It is the only required field when `Fieid` is extended

```kotlin
data class AdditemField(
    var text: String = ""
) :
    Field(R.layout.item_invoice_add_new_item)
```
this example shows executing email validation on InputField.

**Validation** 
This is important when building Forms or if you need to validated entry in the model. 

```kotlin
rvField<InputField> {
                hint = "Customer Email"
                validation = { android.util.Patterns.EMAIL_ADDRESS.matcher(this.text).matches() }
            }
```
this example shows executing email validation on InputField.

**Key** 
for referencing a model from the adapter. 

```kotlin
rvField<InputField> {
                hint = "Customer Email"
                key = "EMAIL_KEY"
                validation = { android.util.Patterns.EMAIL_ADDRESS.matcher(this.text).matches() }
            }
```

**required**
for marking a field as required. validation is invoked ony if required is true

```kotlin
rvField<InputField> {
                hint = "Customer Email"
                required = true
                key = "EMAIL_KEY"
                validation = { android.util.Patterns.EMAIL_ADDRESS.matcher(this.text).matches() }
            }
```

**errormessage**
message to be displayed when validate fails.

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


**datasource**
Datasource of this model. You can choose to pass some data as paramerter to your model. A datasource can be an database object or any object that has information for updating your view

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
