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

    private var nameList: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListMenuBinding.inflate(inflater, container, false)

        if (arguments != null) {
            nameList = requireArguments().getString(TAG).toString()
        }

        binding.btRenameList.setOnClickListener {
            goToInsert()
            dismiss()
        }

        binding.btDeleteList.setOnClickListener {
            viewModel.delete(ListModel(nameList))
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
        val bottomSheet: InsertListFragment =
            InsertListFragment.newInstance(nameList, viewModel)
        bottomSheet.show(requireActivity().supportFragmentManager, InsertListFragment.TAG)
    }

    companion object {
        const val TAG = "menuCategoryFragment"

        fun newInstance(nameList: String, viewModel: ListViewModel): MenuListFragment {
            val fragment = MenuListFragment(viewModel)
            val args = Bundle()
            args.putString(TAG, nameList)
            fragment.arguments = args
            return fragment
        }
    }
}