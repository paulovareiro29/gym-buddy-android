package ipvc.gymbuddy.app.models

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import ipvc.gymbuddy.app.R

class Modal : DialogFragment() {

    private var title: String? = null

    companion object {
        private const val ARG_LAYOUT_RES_ID = "layoutResId"

        fun newInstance(layoutResId: Int): Modal {
            val fragment = Modal()
            val args = Bundle()
            args.putInt(ARG_LAYOUT_RES_ID, layoutResId)
            fragment.arguments = args
            return fragment
        }
    }

    fun setTitle(title: String) {
        this.title = title
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.modal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titleTextView = view.findViewById<TextView>(R.id.modal_title)

        if (!title.isNullOrEmpty()) {
            titleTextView.text = title
            titleTextView.visibility = View.VISIBLE
        }

        val layoutResId = arguments?.getInt(ARG_LAYOUT_RES_ID) ?: 0
        val content = layoutInflater.inflate(layoutResId, view.findViewById(R.id.modal_content), false)
        view.findViewById<ViewGroup>(R.id.modal_content).addView(content)
    }
}
