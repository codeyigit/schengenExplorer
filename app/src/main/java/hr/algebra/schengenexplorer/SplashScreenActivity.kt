package hr.algebra.schengenexplorer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import hr.algebra.schengenexplorer.api.SchengenFetcher
import hr.algebra.schengenexplorer.api.SchengenWorker
import hr.algebra.schengenexplorer.databinding.ActivitySplashScreenBinding
import hr.algebra.schengenexplorer.framework.applyAnimation
import hr.algebra.schengenexplorer.framework.callDelayed
import hr.algebra.schengenexplorer.framework.getBooleanPreference
import hr.algebra.schengenexplorer.framework.isOnline
import hr.algebra.schengenexplorer.framework.startActivity

private const val DELAY = 3000L
const val DATA_IMPORTED="hr.algebra.schengenexplorer.data_imported"

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        startAnimations()
        redirect()
    }

    private fun redirect() {

        //if data is imported then croos over to the HostActivity
        if(getBooleanPreference(DATA_IMPORTED)) {
            callDelayed(DELAY){
                startActivity<HostActivity>()
            }
        }else{
            //if I am online
            if(isOnline()){
                WorkManager.getInstance(this).apply {
                    enqueueUniqueWork(
                        DATA_IMPORTED,
                        ExistingWorkPolicy.KEEP,
                        OneTimeWorkRequest.Companion.from(SchengenWorker::class.java)
                    )
                }

            }else{
                binding.tvSplash.text= getString(R.string.no_internet)
                callDelayed(DELAY){
                    finish()
                }
            }
        }
    }



    private fun startAnimations() {
        binding.tvSplash.applyAnimation(R.anim.blink)
        binding.ivSplash.applyAnimation(R.anim.scale_up)
    }
}