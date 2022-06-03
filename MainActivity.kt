package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                // remove the item from the list
                listOfTasks.removeAt(position)

                // notify the adapter and update it
                adapter.notifyDataSetChanged()

                saveItems()
            }

        }

        loadItems()

        //Lok up the recycler view in the layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)

        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter

        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        //set up the button and input field for adding tasks

        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        //Get Reference
        findViewById<Button>(R.id.button).setOnClickListener {
            //grab text from the inout field
            val userInputtedTask = inputTextField.text.toString()

            //add the string to the list of tasks(listOfTasks)
            listOfTasks.add(userInputtedTask)

            //notify the adapter and update it
            adapter.notifyItemInserted(listOfTasks.size-1)

            //Clear out the inout field
            inputTextField.setText("")

            saveItems()
        }
    }

    // save the data that the user has inputted


    // writing and reading form the file


    // a method that get the data file we need
    fun getDataFile() : File {

        return File(filesDir, "data.txt")
    }

    // load the items by reading every line in the file
    fun loadItems(){
        try{
            listOfTasks = org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }

    //save all of the items into the data file
    fun saveItems(){
        try{
            org.apache.commons.io.FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch(ioException: IOException){
            ioException.printStackTrace()
        }

    }
}