package xyz.belvi.rvcompose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import xyz.belvi.rvcompose.Factory.Samp
import xyz.belvi.rvcomposelibrary.compose.compose
import xyz.belvi.rvcomposelibrary.models.Field
import xyz.belvi.rvcomposelibrary.models.rvField

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rv = recycler.compose {
            withLayoutManager(LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false))
            fieldClicked { uiComposeAdapter, field, position ->
            }
        }

        rv.getAdapter().updateFields(mutableListOf<Field>().apply { add(rvField(Samp()) { errorMessage = "" }) })

    }


}
