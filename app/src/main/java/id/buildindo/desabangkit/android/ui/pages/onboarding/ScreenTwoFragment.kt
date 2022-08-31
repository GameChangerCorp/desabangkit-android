package id.buildindo.desabangkit.android.ui.pages.onboarding

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.R
import id.buildindo.desabangkit.android.databinding.FragmentScreenOneBinding

@AndroidEntryPoint
class ScreenTwoFragment : Fragment() {
    private lateinit var _binding : FragmentScreenOneBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScreenOneBinding.inflate(layoutInflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding.apply {
            onboardingTitle.text = getString(R.string.onboarding_title_2)
            onboardingSubtitle.text = getString(R.string.onboarding_subtitle_2)
            ltImage.setAnimation(R.raw.grocery)
            ltImage.repeatCount = ValueAnimator.INFINITE
            ltImage.playAnimation()
        }
    }
}