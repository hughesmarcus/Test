package cvdevelopers.takehome.dagger

import cvdevelopers.takehome.ClientFragment
import cvdevelopers.takehome.MainActivity
import cvdevelopers.takehome.LuminaryTakeHomeApplication
import dagger.Component
import javax.inject.Singleton

/**
 * Created by CamiloVega on 10/7/17.
 */
@Singleton
@Component(
    modules = arrayOf(
        ApplicationModule::class,
        NetworkClientModule::class,
        DatabaseModule::class
    )
)
interface ApplicationComponent {
    fun inject(app: LuminaryTakeHomeApplication)
    fun inject(activity: MainActivity)
    fun inject(fragment: ClientFragment)
}