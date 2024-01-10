package br.com.oliver.note.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.oliver.note.R
import br.com.oliver.note.databinding.FragmentListMenuBinding
import br.com.oliver.note.model.ListModel
import br.com.oliver.note.viewmodel.ListViewModel
import br.com.oliver.note.viewmodel.TaskViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.runBlocking

class MenuListFragment(
    private val listViewModel: ListViewModel,
    private val taskViewModel: TaskViewModel
) :
    BottomSheetDialogFragment() {

    private var _binding: FragmentListMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var model: ListModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListMenuBinding.inflate(inflater, container, false)

        if (arguments != null) {
            model = requireArguments().getSerializable(TAG) as ListModel
        }

        initOnClick()
        verifyIsMain(model.main)

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
        binding.btRenameList.setOnClickListener {
            goToInsert()
            dismiss()
        }

        binding.btDeleteList.setOnClickListener {

            runBlocking {
                if (taskViewModel.existsListId(model.id).await()) {
                    showAlertDialog()
                    dismiss()
                } else {
                    listViewModel.delete(model)
                }
            }
        }
    }

    private fun verifyIsMain(isMain: Boolean) {
        if (isMain) {
            binding.btDeleteList.isEnabled = false
            binding.btDeleteList.alpha = ALPHA_NUM
        }
    }

    private fun goToInsert() {
        val bottomSheet: InsertOrUpdateListFragment =
            InsertOrUpdateListFragment.newInstance(model, listViewModel)
        bottomSheet.show(requireActivity().supportFragmentManager, InsertOrUpdateListFragment.TAG)
    }

    private fun showAlertDialog() {
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle(R.string.delete_this_list)
            .setMessage(R.string.permanently_deleted_message)
            .setOnDismissListener {
            }
            .setPositiveButton(R.string.delete) { dialog, _ ->
                taskViewModel.deleteByListId(model.id)
                listViewModel.delete(model)
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .create().apply {
                show()
            }
    }

    companion object {
        const val TAG = "menuCategoryFragment"
        const val ALPHA_NUM = 0.3F

        fun newInstance(
            listModel: ListModel,
            listViewModel: ListViewModel,
            taskViewModel: TaskViewModel
        ): MenuListFragment {
            val fragment = MenuListFragment(listViewModel, taskViewModel)
            val args = Bundle()
            args.putSerializable(TAG, listModel)
            fragment.arguments = args
            return fragment
        }
    }
}