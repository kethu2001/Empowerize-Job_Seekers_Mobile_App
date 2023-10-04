package com.example.empowerize_project.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.empowerize_project.models.CompanyModel
import com.example.empowerize_project.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Insertion: AppCompatActivity() {

    private lateinit var emComName: EditText
    private lateinit var emBusinessReg: EditText
    private lateinit var emAddress: EditText
    private lateinit var emGmail: EditText
    private lateinit var emPassword: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        emComName = findViewById(R.id.emComName)
        emBusinessReg = findViewById(R.id.emBusinessReg)
        emAddress = findViewById(R.id.emAddress)
        emGmail = findViewById(R.id.emGmail)
        emPassword = findViewById(R.id.emPassword)
        btnSaveData  = findViewById(R.id.btnSaveData)

        dbRef = FirebaseDatabase.getInstance().getReference("Company")

        btnSaveData.setOnClickListener {
            saveCompanyData()
        }

        supportActionBar?.hide();

    }

    private fun saveCompanyData() {
        //getting values
        val comName = emComName.text.toString()
        val businessReg = emBusinessReg.text.toString()
        val addressCom = emAddress.text.toString()
        val gmailCom = emGmail.text.toString()
        val passwordCom = emPassword.text.toString()

        if (comName.isEmpty()){
            emComName.error = "Please enter company name"
        }
        if (businessReg.isEmpty()){
            emBusinessReg.error = "Please enter business registration"
        }
        if (addressCom.isEmpty()){
            emAddress.error = "Please enter Address"
        }
        if (gmailCom.isEmpty()){
            emGmail.error = "Please enter gmail"
        }
        if (passwordCom.isEmpty()){
            emPassword.error = "Please enter password"
        } else if (passwordCom.length < 8) { // minimum password length is 8 characters
            emPassword.error = "Password should have at least 8 characters"
        } else if (!passwordCom.matches(Regex(".*\\d.*"))) { // password should contain at least one digit
            emPassword.error = "Password should contain at least one digit"
        } else if (!passwordCom.matches(Regex(".*[A-Z].*"))) { // password should contain at least one uppercase letter
            emPassword.error = "Password should contain at least one uppercase letter"
        } else {
            val comId = dbRef.push().key!!

            val company = CompanyModel(comId, comName, businessReg, addressCom, gmailCom, passwordCom)

            dbRef.child(comId).setValue(company)
                .addOnCompleteListener{
                    Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                    emComName.text.clear()
                    emBusinessReg.text.clear()
                    emAddress.text.clear()
                    emGmail.text.clear()
                    emPassword.text.clear()

                }.addOnFailureListener{ err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
        }
    }

}