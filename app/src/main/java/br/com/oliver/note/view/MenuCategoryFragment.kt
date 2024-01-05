package br.com.oliver.note.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.oliver.note.databinding.FragmentCategoryInsertBinding
import br.com.oliver.note.databinding.FragmentCategoryMenuBinding
import br.com.oliver.note.model.Category
import br.com.oliver.note.viewmodel.CategoryViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MenuCategoryFragment(private val viewModel: CategoryViewModel) :
    BottomSheetDialogFragment() {

    private var _binding: FragmentCategoryMenuBinding? = null
    private val binding get() = _binding!!

    private var nomeTable: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryMenuBinding.inflate(inflater, container, false)

        if (arguments != null) {
            nomeTable = requireArguments().getString(TAG).toString()
        }

        binding.btRenameList.setOnClickListener {
            goToInsert()
            dismiss()
        }

        binding.btDeleteList.setOnClickListener {
            viewModel.delete(Category(nomeTable))
            dismiss()
        }

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

    private fun goToInsert() {
        val bottomSheet: InsertCategoryFragment =
            InsertCategoryFragment.newInstance(nomeTable, viewModel)
        bottomSheet.show(requireActivity().supportFragmentManager, InsertCategoryFragment.TAG)
    }

    companion object {
        const val TAG = "menuCategoryFragment"

        fun newInstance(nameTable: String, viewModel: CategoryViewModel): MenuCategoryFragment {
            val fragment = MenuCategoryFragment(viewModel)
            val args = Bundle()
            args.putString(TAG, nameTable)
            fragment.arguments = args
            return fragment
        }
    }
}