package xyz.belvi.rvcompose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import xyz.belvi.rvcompose.exts.observe
import xyz.belvi.rvcompose.exts.withViewModel
import xyz.belvi.rvcompose.factory.UIFieldsModel.InputField
import xyz.belvi.rvcompose.factory.UIFieldsModel.LabelInfo
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
