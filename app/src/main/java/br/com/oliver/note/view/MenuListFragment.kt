package br.com.oliver.note.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.oliver.note.databinding.FragmentListMenuBinding
import br.com.oliver.note.model.ListModel
import br.com.oliver.note.viewmodel.ListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MenuListFragment(private val viewModel: ListViewModel) :
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
            viewModel.delete(model)
            dismiss()
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
            InsertOrUpdateListFragment.newInstance(model, viewModel)
        bottomSheet.show(requireActivity().supportFragmentManager, InsertOrUpdateListFragment.TAG)
    }

    companion object {
        const val TAG = "menuCategoryFragment"
        const val ALPHA_NUM = 0.3F

        fun newInstance(listModel: ListModel, viewModel: ListViewModel): MenuListFragment {
            val fragment = MenuListFragment(viewModel)
            val args = Bundle()
            args.putSerializable(TAG, listModel)
            fragment.arguments = args
            return fragment
        }
    }
}