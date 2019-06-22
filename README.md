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
    override val layout: Int = R.layout.item_input,
    var hint: String = "",
    var text: String = ""
) :
    Field() {


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


