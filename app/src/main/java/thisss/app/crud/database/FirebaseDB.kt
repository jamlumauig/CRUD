package thisss.app.crud.database

import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import com.google.gson.Gson
import thisss.app.crud.Model.AppModel
import java.text.FieldPosition

class FirebaseDB {
    lateinit var databaseReference: DatabaseReference

    fun getDatabase(): DatabaseReference {
        val database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("Talowns")
        return databaseReference
    }

    fun add(listModel: AppModel): Task<Void> {
        return databaseReference.push().setValue(listModel)
    }

    fun update(key : String, hashMap: HashMap<String, Any>): Task<Void> {
        return databaseReference.child(key).updateChildren(hashMap)
    }
    fun remove(key : String) : Task<Void> {
        return databaseReference.child(key).removeValue()

    }
}