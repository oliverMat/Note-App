package br.com.oliver.note.view

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.oliver.note.R
import br.com.oliver.note.databinding.FragmentListInsertOrUpdateBinding
import br.com.oliver.note.model.ListModel
import br.com.oliver.note.viewmodel.ListViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

class InsertOrUpdateListFragment(private val viewModel: ListViewModel) :
    BottomSheetDialogFragment() {

    private var _binding: FragmentListInsertOrUpdateBinding? = null
    private val binding get() = _binding!!

    private var model: ListModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListInsertOrUpdateBinding.inflate(inflater, container, false)

        if (arguments != null) {
            model = requireArguments().getSerializable(TAG) as ListModel?
        }

        if (model != null) {
            binding.editTextAddTab.setText(model!!.name)
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
        when (binding.editTextAddTab.text.trim().isNotEmpty()) {
            true -> {
                when (model != null) {
                    true -> {
                        model!!.name = binding.editTextAddTab.text.toString()
                        viewModel.update(model!!)
                    }

                    false -> {
                        viewModel.insert(
                            ListModel(
                                name = binding.editTextAddTab.text.toString(),
                                main = false
                            )
                        )
                    }
                }
                dismiss()
            }
            false -> {
                Snackbar.make(binding.root, R.string.field_empty, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        }
    }

    companion object {
        const val TAG = "insertCategoryFragment"

        fun newInstance(
            listModel: ListModel?,
            viewModel: ListViewModel
        ): InsertOrUpdateListFragment {
            val fragment = InsertOrUpdateListFragment(viewModel)
            val args = Bundle()
            args.putSerializable(TAG, listModel)
            fragment.arguments = args
            return fragment
        }
    }
}