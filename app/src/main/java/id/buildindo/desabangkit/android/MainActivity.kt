package id.buildindo.desabangkit.android

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import id.buildindo.desabangkit.android.databinding.ActivityMainBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: PhotoViewModel

    companion object{
        lateinit var appContext : Context
    }

    private var getFile: File? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //TODO : INFO - Need create ApplicationController
        appContext = applicationContext

        viewModel = ViewModelProvider(this)[PhotoViewModel::class.java]

        binding.btnGallery.setOnClickListener {
            startGallery()
        }

        binding.btnUpload.setOnClickListener {
            uploadImage()
        }

        viewModel.addStory.observe(this) {
            if (it.error == true){
                Toast.makeText(this, "Error Ges ya Ges", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this, PhotoResultActivity::class.java)
                Toast.makeText(this, "Success Upload", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }
        }


    }

    private fun uploadImage() {
        if (getFile != null) {
            val file = reduceFileImage(getFile as File)
            val requestImageFile = file.asRequestBody("file".toMediaTypeOrNull())
            val imageMultiPart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "file",
                file.name,
                requestImageFile,
            )
            viewModel.addPhoto(imageMultiPart)

        }
    }


    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@MainActivity)

            getFile = myFile
            binding.photo.setImageURI(selectedImg)
        }
    }

}