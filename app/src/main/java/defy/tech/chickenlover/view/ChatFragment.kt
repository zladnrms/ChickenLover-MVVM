package defy.tech.chickenlover.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import defy.tech.chickenlover.R
import defy.tech.chickenlover.databinding.ChatFragmentBinding
import defy.tech.chickenlover.viewmodel.ChatViewModel

class ChatFragment : Fragment() {

    private lateinit var binding : ChatFragmentBinding
    private lateinit var chatViewModel: ChatViewModel

    companion object {
        fun newInstance() = ChatFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.chat_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        chatViewModel = ViewModelProviders.of(this).get(ChatViewModel::class.java)
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
