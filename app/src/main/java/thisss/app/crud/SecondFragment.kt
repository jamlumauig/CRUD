package thisss.app.crud

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import thisss.app.crud.Model.AppModel
import thisss.app.crud.database.FirebaseDB
import thisss.app.crud.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    var fb = FirebaseDB()
    var gson = Gson()
    var fromFirstFragment: String? = null
    private var notesModel = ArrayList<AppModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        fromFirstFragment = arguments?.getString("btn")

        return binding.root
    }

    fun get() {
        fb.getDatabase().addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children) {

                    Toast.makeText(context,"GG  $fromFirstFragment", Toast.LENGTH_SHORT).show()

                        val packageName = binding.appName.editableText.toString()
                        val status = binding.status.editableText.toString()
                        val url = binding.url.editableText.toString()
                        val date = binding.date.editableText.toString()

                        val hashMap: HashMap<String, Any> = HashMap()
                        hashMap["AppTitle"] = packageName
                        hashMap["Date"] = date
                        hashMap["PackageName"] = packageName
                        hashMap["STATUS"] = status
                        hashMap["URL"] = url

                        Log.d("UPDATE1 ", "$packageName $status $url $date")

                        binding.save.setOnClickListener {
                            fromFirstFragment?.let { it1 ->
                                fb.update(it1, hashMap).addOnSuccessListener {
                                    Toast.makeText(context, hashMap.toString(), Toast.LENGTH_SHORT)
                                        .show()

                                }.addOnFailureListener {
                                    Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show()
                                }
                            }

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
    }