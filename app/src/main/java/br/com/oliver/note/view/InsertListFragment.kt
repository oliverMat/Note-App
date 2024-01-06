package br.com.oliver.note.view

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.oliver.note.R
import br.com.oliver.note.databinding.FragmentListInsertBinding
import br.com.oliver.note.model.ListModel
import br.com.oliver.note.viewmodel.ListViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

class InsertListFragment(private val viewModel: ListViewModel) :
    BottomSheetDialogFragment() {

    private var _binding: FragmentListInsertBinding? = null
    private val binding get() = _binding!!

    private var nameList: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListInsertBinding.inflate(inflater, container, false)

        if (arguments != null) {
            nameList = requireArguments().getString(TAG).toString()
        }

        if (nameList.isNotEmpty()) {
            binding.editTextAddTab.setText(nameList)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /******************* methods *******************/

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

            if (nameList.isNotEmpty()) {
                viewModel.rename(nameList, binding.editTextAddTab.text.toString())
            } else {
                viewModel.insert(ListModel(binding.editTextAddTab.text.toString()))
            }

            dismiss()

        } else {
            Snackbar.make(binding.root, R.string.field_empty, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    companion object {
        const val TAG = "insertCategoryFragment"

        fun newInstance(nameList: String, viewModel: ListViewModel): InsertListFragment {
            val fragment = InsertListFragment(viewModel)
            val args = Bundle()
            args.putString(TAG, nameList)
            fragment.arguments = args
            return fragment
        }
    }
}