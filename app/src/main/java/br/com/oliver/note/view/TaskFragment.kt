package br.com.oliver.note.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import br.com.oliver.note.Application
import br.com.oliver.note.databinding.FragmentTaskBinding
import br.com.oliver.note.model.ListModel
import br.com.oliver.note.view.adapters.TaskListAdapter
import br.com.oliver.note.viewmodel.TaskViewModel
import br.com.oliver.note.viewmodel.TaskViewModelFactory


class TaskFragment : Fragment() {

    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!

    private var model: ListModel? = null

    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((requireActivity().application as Application).taskRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)

        if (arguments != null) {
            model = requireArguments().getSerializable(TAG) as ListModel?
        }

        initRecyclerView()

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

    private fun initRecyclerView() {
        val gridLayoutManager = GridLayoutManager(activity, 2)
        gridLayoutManager.spanCount = 2

        binding.layoutRecyclerview.recyclerViewLayout.layoutManager = gridLayoutManager
        val adapter = TaskListAdapter()
        binding.layoutRecyclerview.recyclerViewLayout.adapter = adapter



        taskViewModel.allTaskById(model!!.id).observe(requireActivity()) { task ->
            when (task.isEmpty()) {
                true -> binding.viewFlipperTask.showNext()
                else -> {
                    if (binding.viewFlipperTask.displayedChild == LAYOUT_EMPTY) {
                        binding.viewFlipperTask.showNext()
                    }
                }
            }
            adapter.submitList(task)
        }
    }


    companion object {
        private const val TAG = "taskFragment"
        private const val LAYOUT_EMPTY = 1
        fun newInstance(listModel: ListModel): TaskFragment {
            val fragment = TaskFragment()
            val args = Bundle()
            args.putSerializable(TAG, listModel)
            fragment.arguments = args
            return fragment
        }
    }
}