package id.buildindo.desabangkit.android.ui.pages.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.R
import id.buildindo.desabangkit.android.databinding.FragmentOnboardingBinding
import id.buildindo.desabangkit.android.ui.adapter.OnBoardingAdapter
import id.buildindo.desabangkit.android.ui.pages.auth.LoginActivity
import id.buildindo.desabangkit.android.ui.viewmodel.DatastoreViewModel
import timber.log.Timber

@AndroidEntryPoint
class OnBoardingFragment : Fragment() {

    private lateinit var _viewModelDataStore: DatastoreViewModel

    private lateinit var _binding: FragmentOnboardingBinding


    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.onBackPressedDispatcher?.addCallback(
            this, // lifecycle owner
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Back is pressed... Finishing the activity
                    activity?.finish()
                }
            })
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        initializeViewModel()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentList = arrayListOf(
            ScreenOneFragment(),
            ScreenTwoFragment(),
            ScreenThreeFragment()
        )

        val adapter = OnBoardingAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        _binding.apply {
            viewPager2.adapter = adapter

            TabLayoutMediator(tabLayout, viewPager2) { _, _ ->

            }.attach()
        }

        initializeViewPager()

    }

    private fun initializeViewPager() {
        with(_binding) {
            viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    if (position == 2) {
                        Timber.d("cek status onboard")
                        btnNext.text = getString(R.string.end_title)
                        btnNext.setOnClickListener {
                            _viewModelDataStore.saveOnBoardingState(true)
                            moveToLoginActivity()
                        }
                    } else {
                        btnNext.text = getString(R.string.next)
                        btnNext.setOnClickListener {
                            viewPager2.currentItem += 1
                        }
                    }
                    super.onPageSelected(position)
                }
            })

            Timber.d("cek status ${viewPager2.currentItem}")
        }
    }

    private fun moveToLoginActivity() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun initializeViewModel() {
        _viewModelDataStore = ViewModelProvider(this)[DatastoreViewModel::class.java]
    }

}