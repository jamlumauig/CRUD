package thisss.app.crud

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import thisss.app.crud.Model.AppModel
import thisss.app.crud.database.FirebaseDB
import thisss.app.crud.databinding.FragmentFirstBinding

class FirstFragment : Fragment(), StringOnClick {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private var notesModel = ArrayList<AppModel>()

    var fb = FirebaseDB()
    var gson = Gson()
    lateinit var arrayAdapter: Adapter

    var dataKeys = ""
    var counter = 0

    lateinit var item: DataSnapshot

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        getStart()
        // start()
        return binding.root

    }

    private fun start() {
        fb.getDatabase().addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged", "SetJavaScriptEnabled")
            override fun onDataChange(snapshot: DataSnapshot) {

                for (ds in snapshot.children) {
                    val pn = ds.child("PackageName").getValue(String::class.java)
                    val url = ds.child("URL").getValue(String::class.java)
                    Log.d("TAG", "$pn / $url")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })
    }

    private fun getStart() {
        notesModel.clear()
        fb.getDatabase().addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                item = snapshot
                for (item in snapshot.children) {
                    val data: AppModel? = item.getValue(AppModel::class.java)
                    if (data != null) {
                        data.appTitle = item.child("AppTitle").getValue(String::class.java)
                        data.packageName = item.child("PackageName").getValue(String::class.java)
                        data.date = item.child("Date").getValue(String::class.java)
                        data.status = item.child("STATUS").getValue(String::class.java)
                        data.url = item.child("URL").getValue(String::class.java)
                        notesModel.add(data)
                        Log.d("GetData: ", gson.toJson(data).toString())

                        arrayAdapter = Adapter(notesModel, this@FirstFragment)
                        binding.rview.adapter = arrayAdapter
                        binding.rview.layoutManager = LinearLayoutManager(
                            context, LinearLayoutManager.VERTICAL,
                            false
                        )
                        arrayAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAdapterClick(positon: Int) {

        fb.getDatabase().addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children) {
                    if (counter == positon) {
                        dataKeys = item.key!!
                        break
                    }
                    counter++
                }
                val b = Bundle()
                b.putString("btn", dataKeys)
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, b)
                Toast.makeText(context, dataKeys, Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onItemLongClick(position: Int, v: View?) {
        TODO("Not yet implemented")
    }


}