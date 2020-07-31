package cvdevelopers.takehome

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cvdevelopers.githubstalker.R

class MainActivity : AppCompatActivity() {
    private val BACK_STACK_ROOT_TAG = "root_fragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_fragment_container, ClientFragment.newInstance())
                .addToBackStack(BACK_STACK_ROOT_TAG)
                .commit()
    }

}

