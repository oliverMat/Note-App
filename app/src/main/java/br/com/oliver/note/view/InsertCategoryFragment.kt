package br.com.oliver.note.view

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.oliver.note.R
import br.com.oliver.note.databinding.FragmentCategoryInsertBinding
import br.com.oliver.note.model.Category
import br.com.oliver.note.viewmodel.CategoryViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

class InsertCategoryFragment(private val viewModel: CategoryViewModel) :
    BottomSheetDialogFragment() {

    private lateinit var binding: FragmentCategoryInsertBinding

    private var nomeTable: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryInsertBinding.inflate(inflater, container, false)

        if (arguments != null) {
            nomeTable = requireArguments().getString(TAG).toString()
        }

        if (nomeTable.isNotEmpty()) {
            binding.editTextAddTab.hint = nomeTable
        }

        initOnClick()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomSheetBehavior = BottomSheetBehavior.from<View>(view.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        val coordinatorLayout = binding.insertFragment
        coordinatorLayout.minimumHeight = Resources.getSystem().displayMetrics.heightPixels
    }

    private fun initOnClick() {
        binding.buttonCloseTab.setOnClickListener {
            dismiss()
        }

        binding.buttonAddTab.setOnClickListener {
            addTabBottomSheet()
        }
    }

    private fun addTabBottomSheet() {

        if (binding.editTextAddTab.text.trim().isNotEmpty()) {

            if (nomeTable.isNotEmpty()) {
                viewModel.rename(nomeTable, binding.editTextAddTab.text.toString())
            } else {
                viewModel.insert(Category(binding.editTextAddTab.text.toString()))
            }

            dismiss()

        } else {
            Snackbar.make(binding.root, R.string.field_empty, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    companion object {
        const val TAG = "insertCategoryFragment"

        fun newInstance(nameTable: String, viewModel: CategoryViewModel): InsertCategoryFragment {
            val fragment = InsertCategoryFragment(viewModel)
            val args = Bundle()
            args.putString(TAG, nameTable)
            fragment.arguments = args
            return fragment
        }
    }
}