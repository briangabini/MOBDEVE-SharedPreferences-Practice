package com.mobdeve.tighee.contactsapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.tighee.contactsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MAIN_ACTIVITY"
        private const val CONTACT_STRINGS_KEY = "CONTACT_STRINGS_KEY"
    }

    private lateinit var viewBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.addBtn.setOnClickListener(View.OnClickListener {
            val name = viewBinding.nameEtv.text.toString()
            val number = viewBinding.numberEtv.text.toString()

            if (name != "" || number != "") {
                Log.d(TAG, "onClick: added properly")
                val temp = "${viewBinding.contactsStringHolderTv.text}$name : $number\n"
                viewBinding.contactsStringHolderTv.setText(temp)

                viewBinding.nameEtv.setText("")
                viewBinding.numberEtv.setText("")
            } else {
                Log.d(TAG, "onClick: not added properly")
            }
        })
    }

    override fun onStart() {
        super.onStart()

        val sp: SharedPreferences = getPreferences(Context.MODE_PRIVATE)
        var displayString = sp.getString(CONTACT_STRINGS_KEY, "")

        if (!displayString.equals("")) {
            displayString = displayString!!.trim() + '\n'
        }

        viewBinding.contactsStringHolderTv.text = displayString
        Log.d(TAG, "read data:\n|" + viewBinding.contactsStringHolderTv.text.toString() + '|')
    }

    override fun onPause() {
        super.onPause()

        val editor: SharedPreferences.Editor = getPreferences(Context.MODE_PRIVATE).edit()  // get the SharedPreferences associated with this activity

        editor.putString(CONTACT_STRINGS_KEY, viewBinding.contactsStringHolderTv.text.toString())  // write the data to the SharedPreferences
        editor.apply()

        Log.d(TAG, "onPause: data written.")
        Log.d(TAG, "data:\n" + viewBinding.contactsStringHolderTv.text.toString() + '|')
    }
}