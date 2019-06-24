package xyz.belvi.rvcompose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
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

class MainActivity : AppCompatActivity(), LifecycleOwner {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

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

