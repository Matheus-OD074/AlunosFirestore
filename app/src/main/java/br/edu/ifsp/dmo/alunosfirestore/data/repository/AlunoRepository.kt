package br.edu.ifsp.dmo.alunosfirestore.data.repository


import android.util.Log
import br.edu.ifsp.dmo.alunosfirestore.data.model.Aluno
import com.google.firebase.Firebase

class AlunoRepository {
    companion object{
        const val TAG = "DMO"
        const val COLLECTION = "alunos"
        const val ATTR_NAME = "nome"
        const val ATTR_YEAR = "nascimento"
        const val ATTR_ID = "prontuario"
    }
    private val database = Firebase.firestore

    fun findAll(callback: (List<Aluno>) -> Unit){
        database.collecion(COLLECTION)
            .orderBy(ATTR_NAME, Query.Direction.ASCENDING)
            .addSnapShotListener{ querySnapshot, exception ->
                if (exception != null){
                    Log.e(TAG, "Listen fail.")
                    callback(emptyList())
                    return@addSnapShotListener
                }

                if (querySnapshot != null){
                    val list = querySnapshot.toObjects(Aluno::class.java)
                    callback(list)
                }else{
                    callback(emptyList())
                }

            }
    }

    fun insert(aluno: Aluno, callback: (Boolean) -> Unit){
        database.collection(COLLECTION)
            .add(aluno)
            .addOnSuccessListener{
                callback(true)
            }
            .addOnFailureListener{
                callback(false)
            }
    }

}
