
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import ipvc.gymbuddy.app.R

open class Modal(private val layoutId: Int, private var arguments: Bundle? = null) : DialogFragment() {

    private var title: String? = null
    private var contentFragmentClassName: String? = null

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_CONTENT_FRAGMENT_CLASS_NAME = "contentFragmentClassName"

        fun newInstance(title: String, contentFragmentClassName: String, arguments: Bundle? = null): Modal {
            val fragment = Modal(R.layout.modal, arguments)
            val args = Bundle()
            args.putString(ARG_TITLE, title)
            args.putString(ARG_CONTENT_FRAGMENT_CLASS_NAME, contentFragmentClassName)
            args.putAll(arguments)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_TITLE)
            contentFragmentClassName = it.getString(ARG_CONTENT_FRAGMENT_CLASS_NAME)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titleTextView = view.findViewById<TextView>(R.id.modal_title)
        titleTextView.text = title
        titleTextView.visibility = if (!title.isNullOrEmpty()) View.VISIBLE else View.GONE

        contentFragmentClassName?.let {
            val contentFragment = createFragment(it)
            contentFragment.arguments = arguments
            childFragmentManager.beginTransaction().replace(R.id.modal_content, contentFragment).commit()
        }
    }


    private fun createFragment(className: String): Fragment {
        return childFragmentManager.fragmentFactory.instantiate(requireActivity().classLoader, className)
    }
}
