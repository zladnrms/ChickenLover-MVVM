package defy.tech.chickenlover.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import defy.tech.chickenlover.R
import defy.tech.chickenlover.adapter.ArticleListAdapter
import defy.tech.chickenlover.databinding.BoardFragmentBinding
import defy.tech.chickenlover.model.data.ArticleListItem
import defy.tech.chickenlover.viewmodel.BoardViewModel
import kotlinx.android.synthetic.main.board_fragment.*

class BoardFragment : Fragment() {

    private lateinit var binding : BoardFragmentBinding
    private lateinit var boardViewModel: BoardViewModel
    private lateinit var articleListAdapter: ArticleListAdapter

    companion object {
        fun newInstance() = BoardFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding =  DataBindingUtil.inflate<BoardFragmentBinding>(inflater, R.layout.board_fragment, container, false)
        binding = dataBinding
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        boardViewModel = ViewModelProviders.of(this).get(BoardViewModel::class.java)
        binding.viewModel = boardViewModel
        binding.lifecycleOwner = this

        articleListAdapter = ArticleListAdapter { articleItem ->
            openArticleActivity(articleItem)
        }
        articleList.apply {
            adapter = articleListAdapter
            hasFixedSize()
        }

        boardViewModel.navigateToActivityCall.observe(this, Observer {
            openWriteActivity()
        })

        boardViewModel.articleList.observe(this, Observer {
            articleListAdapter.setList(it)
        })

        boardViewModel.setRecyclerViewScrollListener(articleList)

        boardViewModel.getArticleList()

        setSpinner()
    }

    private fun setSpinner() {
        spinner_caterogy.setItems(boardViewModel.board_types)
        spinner_caterogy.setOnItemSelectedListener { view, position, id, item ->
            boardViewModel.selectType(item.toString())
        }
    }

    private fun openWriteActivity() {
        startActivity(WriteActivity.starterIntent(activity as MainActivity))
    }

    private fun openArticleActivity(articleListItem: ArticleListItem) {
        startActivity(ArticleActivity.starterIntent(activity as MainActivity, articleListItem))
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
