package kz.maxwell.atom.ui.advices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_advices.*
import kz.maxwell.atom.AdviceRecyclerViewAdapter
import kz.maxwell.atom.DataSource
import kz.maxwell.atom.R
import kz.maxwell.atom.TopSpacingItemDecoration
import kz.maxwell.atom.models.Advice

class AdvicesFragment : Fragment() {
    private lateinit var db: FirebaseFirestore
    private lateinit var adviceAdapter: AdviceRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_advices, container, false)
        db = Firebase.firestore

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        addDataSet()
    }

    private fun addDataSet() {
        val data = DataSource.createDataSetAdvice()
        adviceAdapter.submitList(data)
    }

    private fun initRecyclerView() {
        newAdvicesRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            val topSpacingItemDecoration = TopSpacingItemDecoration(5)
            addItemDecoration(topSpacingItemDecoration)
            adviceAdapter = AdviceRecyclerViewAdapter()
            adapter = adviceAdapter
        }
    }
}