package com.github.app.repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.R

class RepoPlaceholderFragment : Fragment() {

    companion object {
        private const val ARG_TITLE = "title"

        fun newInstance(title: String): RepoPlaceholderFragment {
            val fragment = RepoPlaceholderFragment()
            val args = Bundle()
            args.putString(ARG_TITLE, title)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(android.R.layout.simple_list_item_1, container, false)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = "Content for: ${arguments?.getString(ARG_TITLE)}"
        return view
    }
}
