package id.buildindo.desabangkit.android.ui.pages

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.core.utils.uriToFile
import id.buildindo.desabangkit.android.databinding.ActivityMainBinding
import id.buildindo.desabangkit.android.ui.pages.auth.LoginActivity
import id.buildindo.desabangkit.android.ui.viewmodel.DatastoreViewModel
import java.io.File
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var _viewModelDataStore : DatastoreViewModel

    private var getFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        _viewModelDataStore = ViewModelProvider(this,)[DatastoreViewModel::class.java]


        binding.btnGallery.setOnClickListener {
            startGallery()
        }

        binding.btnUpload.setOnClickListener {
//            uploadImage()
        }

        binding.btnLogout.setOnClickListener {
            _viewModelDataStore.saveLoginState(false)
            _viewModelDataStore.saveBearerToken("")
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

//    private fun uploadImage() {
//        if (getFile != null) {
//            val file = reduceFileImage(getFile as File)
//            val requestImageFile = file.asRequestBody("file".toMediaTypeOrNull())
//            val imageMultiPart: MultipartBody.Part = MultipartBody.Part.createFormData(
//                "file",
//                file.name,
//                requestImageFile,
//            )
//            viewModel.addPhoto(imageMultiPart)
//
//        }
//    }


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