package com.example.empowerize_project.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.empowerize_project.R
import com.example.empowerize_project.adapters.ComAdapter
import com.example.empowerize_project.models.CompanyModel
import com.google.firebase.database.*

class Fetching : AppCompatActivity() {

    private lateinit var displayCompany: RecyclerView
    private lateinit var loadingData: TextView
    private lateinit var comList: ArrayList<CompanyModel>
    private lateinit var dbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fetching)

        displayCompany = findViewById(R.id.displayCompany)
        displayCompany.layoutManager = LinearLayoutManager(this)
        displayCompany.setHasFixedSize(true)
        loadingData = findViewById(R.id.loadingData)

        comList = arrayListOf<CompanyModel>()

        getCompanyData()

        supportActionBar?.hide();
    }

    private fun getCompanyData(){
        displayCompany.visibility = View.GONE
        loadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Company")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                comList.clear()
                if(snapshot.exists()){
                    for(comSnap in snapshot.children){
                        val comData = comSnap.getValue(CompanyModel::class.java)
                        comList.add(comData!!)
                    }
                    val mAdapter = ComAdapter(comList)
                    displayCompany.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : ComAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@Fetching, CompanyDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("comId", comList[position].comId)
                            intent.putExtra("comName", comList[position].comName)
                            intent.putExtra("businessReg", comList[position].businessReg)
                            intent.putExtra("addressCom", comList[position].addressCom)
                            intent.putExtra("gmailCom", comList[position].gmailCom)
                            startActivity(intent)
                        }

                    })

                    displayCompany.visibility = View.VISIBLE
                    loadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}