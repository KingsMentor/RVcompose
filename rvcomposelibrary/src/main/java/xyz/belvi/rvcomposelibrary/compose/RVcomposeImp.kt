package xyz.belvi.rvcomposelibrary.compose

import xyz.belvi.rvcomposelibrary.adapter.UIComposeAdapter

class RVcomposeImp internal constructor(val uiComposeAdapter: UIComposeAdapter) : RVcomposeHandler {
    override fun getAdapter(): UIComposeAdapter {
        return uiComposeAdapter
    }
}