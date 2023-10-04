package com.example.empowerize_project.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.empowerize_project.R
import com.example.empowerize_project.models.CompanyModel
import com.google.firebase.database.FirebaseDatabase

class CompanyDetailsActivity : AppCompatActivity() {
    private lateinit var tvEmpId: TextView
    private lateinit var tvEmpName: TextView
    private lateinit var tvEmpAge: TextView
    private lateinit var tvEmpSalary: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.company_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("comId").toString(),
                intent.getStringExtra("comName").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("comId").toString()
            )
        }

        supportActionBar?.hide();
    }

    private fun initView() {
        tvEmpId = findViewById(R.id.tvEmpId)
        tvEmpName = findViewById(R.id.tvEmpName)
        tvEmpAge = findViewById(R.id.tvEmpAge)
        tvEmpSalary = findViewById(R.id.tvEmpSalary)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)

    }

    private fun setValuesToViews() {
        tvEmpId.text = intent.getStringExtra("comName")
        tvEmpName.text = intent.getStringExtra("businessReg")
        tvEmpAge.text = intent.getStringExtra("addressCom")
        tvEmpSalary.text = intent.getStringExtra("gmailCom")
    }

    private fun openUpdateDialog(
        comId: String,
        comName : String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_details, null)

        mDialog.setView(mDialogView)

        val uComName = mDialogView.findViewById<EditText>(R.id.uComName)
        val uBusinessReg = mDialogView.findViewById<EditText>(R.id.uBusinessReg)
        val uAddress = mDialogView.findViewById<EditText>(R.id.uAddress)
        val uGmail = mDialogView.findViewById<EditText>(R.id.uGmail)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        uComName.setText(intent.getStringExtra("comName").toString())
        uBusinessReg.setText(intent.getStringExtra("businessReg").toString())
        uAddress.setText(intent.getStringExtra("addressCom").toString())
        uGmail.setText(intent.getStringExtra("gmailCom").toString())

        mDialog.setTitle("Updating $comName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateComData(
                comId,
                uComName.text.toString(),
                uBusinessReg.text.toString(),
                uAddress.text.toString(),
                uGmail.text.toString()
            )

            Toast.makeText(applicationContext, "Company Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvEmpId.text = uComName.text.toString()
            tvEmpName.text = uBusinessReg.text.toString()
            tvEmpAge.text = uAddress.text.toString()
            tvEmpSalary.text = uGmail.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateComData(
        id: String,
        name: String,
        register: String,
        address: String,
        gmail: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Company").child(id)
        val comInfo = CompanyModel(id, name, register, address, gmail)
        dbRef.setValue(comInfo)
    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Company").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Company data deleted successfully", Toast.LENGTH_LONG).show()

            val intent = Intent(this, Fetching::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }
}