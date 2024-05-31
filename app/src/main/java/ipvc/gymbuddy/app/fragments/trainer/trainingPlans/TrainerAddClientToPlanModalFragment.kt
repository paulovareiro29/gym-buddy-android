package ipvc.gymbuddy.app.fragments.trainer.trainingPlans

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import ipvc.gymbuddy.app.R

class TrainerAddClientToPlanModalFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_trainer_add_client_to_plan_modal,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val autoCompleteTextView = view.findViewById<AutoCompleteTextView>(R.id.client)
        val clients = arrayOf("Cliente 1", "Cliente 2", "Cliente 3")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, clients)
        autoCompleteTextView.setAdapter(adapter)

        val editTextStartDate = view.findViewById<EditText>(R.id.edit_text_start_date)
        val editTextEndDate = view.findViewById<EditText>(R.id.edit_text_end_date)

        editTextStartDate.setOnClickListener {
            // Handle start date selection
        }

        editTextEndDate.setOnClickListener {
            // Handle end date selection
        }

        val cancelButton = view.findViewById<Button>(R.id.cancel_button)
        val submitButton = view.findViewById<Button>(R.id.submit_button)

        cancelButton.setOnClickListener {
            // Handle cancel action
        }

        submitButton.setOnClickListener {
            // Handle submit action
        }
    }
}
