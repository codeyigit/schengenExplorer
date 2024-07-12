package hr.algebra.schengenexplorer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import hr.algebra.schengenexplorer.framework.setBooleanPreference
import hr.algebra.schengenexplorer.framework.startActivity

class SchengenReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        context.setBooleanPreference(DATA_IMPORTED)
        context.startActivity<HostActivity>()
    }
}