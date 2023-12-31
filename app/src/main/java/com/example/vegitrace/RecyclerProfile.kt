package com.example.vegitrace


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.vegitrace.model.Recycler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import androidx.appcompat.app.AlertDialog

class RecyclerProfile : AppCompatActivity() {
    private lateinit var nameTextView: TextView
    private lateinit var addressTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var nicTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var databaseReference: DatabaseReference
    private lateinit var editProfileButton: Button
    private lateinit var logoutbtn: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_profile)

        // Initialize the TextView elements
        nameTextView = findViewById(R.id.nameTextView)
        addressTextView = findViewById(R.id.addressTextView)
        phoneTextView = findViewById(R.id.phoneTextView)
        nicTextView = findViewById(R.id.nicTextView)
        emailTextView = findViewById(R.id.emailTextView)
        editProfileButton = findViewById(R.id.recyclerEditPro)
        logoutbtn = findViewById(R.id.recyclerLogout)

        // Set an OnClickListener for the "recyclerEditPro" button
        editProfileButton.setOnClickListener {
            // Create an Intent to navigate to the RecyclerProfileEdit activity
            val intent = Intent(this, RecyclerProfileEdit::class.java)
            startActivity(intent)
        }

        logoutbtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Logout")
            builder.setMessage("Are you sure you want to log out?")

            builder.setPositiveButton("Yes") { _, _ ->
                val intent = Intent(this, Welcome::class.java)
                startActivity(intent)
                finish()
            }

            builder.setNegativeButton("No") { _, _ ->
            }

            builder.show()
        }


        // Initialize Firebase Database reference
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().reference.child("Recyclers").child(userId ?: "")

        // Read data from the database and update TextViews
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val recycler = dataSnapshot.getValue(Recycler::class.java)

                    if (recycler != null) {
                        // Update the TextViews with retrieved data
                        nameTextView.text = "${recycler.name}"
                        addressTextView.text = "${recycler.address}"
                        phoneTextView.text = "${recycler.phone}"
                        nicTextView.text = "${recycler.nic}"
                        emailTextView.text = "${recycler.email}"
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors during data retrieval
            }
        })

        val historyButton = findViewById<ImageView>(R.id.navBookingUnClick)
        val wastageMainButton = findViewById<ImageView>(R.id.navHomeUnClick)
        val recyclerProfileButton = findViewById<ImageView>(R.id.navProfileUnClick)

        wastageMainButton.setOnClickListener {
            val intent = Intent(this, WastageMain::class.java)
            startActivity(intent)
        }
        historyButton.setOnClickListener {
            val intent = Intent(this, RecyclerRemoveList::class.java)
            startActivity(intent)
        }
        recyclerProfileButton.setOnClickListener {
            val intent = Intent(this, RecyclerProfile::class.java)
            startActivity(intent)
        }
    }
}
