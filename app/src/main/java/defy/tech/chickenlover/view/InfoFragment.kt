package defy.tech.chickenlover.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import defy.tech.chickenlover.R
import defy.tech.chickenlover.viewmodel.InfoViewModel

class InfoFragment : Fragment() {

    companion object {
        fun newInstance() = InfoFragment()
    }

    private lateinit var infoViewModel: InfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.info_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        infoViewModel = ViewModelProviders.of(this).get(InfoViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onPause() {
        super.onPause()
        activity?.overridePendingTransition(0, 0)
    }

    override fun onResume() {
        activity?.overridePendingTransition(0,0)
        super.onResume()
    }
}
