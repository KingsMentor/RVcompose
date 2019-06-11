package xyz.belvi.rvcompose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import xyz.belvi.rvcompose.exts.observe
import xyz.belvi.rvcompose.exts.withViewModel
import xyz.belvi.rvcompose.factory.uiFieldsModel.*
import xyz.belvi.rvcompose.vms.MainVM
import xyz.belvi.rvcompose.vms.MainVMFactory
import xyz.belvi.rvcomposelibrary.compose
import xyz.belvi.rvcomposelibrary.models.rvField
import java.util.*

class MainActivity : AppCompatActivity(), LifecycleOwner {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val rv = recycler.compose {
            withLayoutManager(LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false))
            withField<InputField> {
                hint = "Customer Email"
                key = "email"
                required = true
                validation = { Patterns.EMAIL_ADDRESS.matcher(this.text).matches() }

            }
            withField<InvoiceDateField> {
                hint = "Invoice Date"
                date = Calendar.getInstance()
            }

            withField<SpinnerField> {
                items = mutableListOf("Pay with Cash", "Pay with Card", "Pay some other time")
            }

            withField<InvoiceReceiptField> {
                totalDue = 0.0
            }

            withField<ActionField> {
                text = "Create Invoice"
            }

            fieldClicked { uiComposeAdapter, field, position ->

            }
        }


        withViewModel<MainVM>(MainVMFactory) {
            productUiPage()
            observe(uiFields) { fields ->
                fields?.let {
                    rv.getAdapter().updateFields(it)
                }
            }
        }


    }


}
