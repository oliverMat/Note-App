package br.com.oliver.note.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.oliver.note.databinding.FragmentTaskInsertOrUpdateBinding
import br.com.oliver.note.model.ListModel
import br.com.oliver.note.model.TaskModel
import br.com.oliver.note.viewmodel.TaskViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class InsertOrUpdateTaskFragment(private val taskViewModel: TaskViewModel) : BottomSheetDialogFragment() {

    private var _binding: FragmentTaskInsertOrUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskInsertOrUpdateBinding.inflate(inflater, container, false)

        initOnClick()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /******************* methods *******************/

    private fun initOnClick() {
        binding.btSaveTask.setOnClickListener {

            taskViewModel.insert(TaskModel(title = binding.eTAddTask.text.toString()))
        }
    }

    companion object {
        const val TAG = "taskFragment"

        fun newInstance(
            listModel: ListModel,
            taskViewModel: TaskViewModel,
        ): InsertOrUpdateTaskFragment {
            val fragment = InsertOrUpdateTaskFragment(taskViewModel)
            val args = Bundle()
            args.putSerializable(TAG, listModel)
            fragment.arguments = args
            return fragment
        }
    }
}
