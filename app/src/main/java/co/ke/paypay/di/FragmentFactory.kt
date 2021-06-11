package co.ke.paypay.di

import androidx.fragment.app.Fragment
import javax.inject.Inject
import androidx.fragment.app.FragmentFactory

class FragmentFactory
@Inject
constructor(

) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {

            // Add more options later
           else-> super.instantiate(classLoader, className)
        }
    }
}