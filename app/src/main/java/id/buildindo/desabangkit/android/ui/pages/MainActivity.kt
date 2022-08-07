package id.buildindo.desabangkit.android.ui.pages

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import id.buildindo.desabangkit.android.core.data.local.datastore.DataStorePreference
import id.buildindo.desabangkit.android.ui.viewmodel.PhotoViewModel
import id.buildindo.desabangkit.android.databinding.ActivityMainBinding
import id.buildindo.desabangkit.android.reduceFileImage
import id.buildindo.desabangkit.android.ui.viewmodel.DatastoreViewModel
import id.buildindo.desabangkit.android.ui.viewmodel.PreferenceViewModelFactory
import id.buildindo.desabangkit.android.uriToFile
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: PhotoViewModel
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var _viewModelDataStore : DatastoreViewModel


    private var getFile: File? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[PhotoViewModel::class.java]
        val pref = DataStorePreference.getInstance(dataStore)
        _viewModelDataStore = ViewModelProvider(this, PreferenceViewModelFactory(pref))[DatastoreViewModel::class.java]


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

        binding.btnLogout.setOnClickListener {
            _viewModelDataStore.saveLoginState(false)
            _viewModelDataStore.saveBearerToken("")
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
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