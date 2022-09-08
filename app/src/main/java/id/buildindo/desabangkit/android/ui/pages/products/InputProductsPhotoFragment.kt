package id.buildindo.desabangkit.android.ui.pages.products

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.R
import id.buildindo.desabangkit.android.core.utils.reduceFileImage
import id.buildindo.desabangkit.android.core.utils.uriToFile
import id.buildindo.desabangkit.android.databinding.FragmentInputProductsPhotoBinding
import id.buildindo.desabangkit.android.ui.viewmodel.DatastoreViewModel
import id.buildindo.desabangkit.android.ui.viewmodel.ProductsViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@AndroidEntryPoint
class InputProductsPhotoFragment : Fragment() {

    private var _getFile: File? = null
    private lateinit var _binding: FragmentInputProductsPhotoBinding
    private lateinit var _viewModelDataStore: DatastoreViewModel
    private val _productsViewModel: ProductsViewModel by viewModels()
    private var _imageUrl = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentInputProductsPhotoBinding.inflate(inflater, container, false)
        _viewModelDataStore = ViewModelProvider(this)[DatastoreViewModel::class.java]
        return _binding.root
    }


    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun observeLiveData() {

    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, requireContext())

            _getFile = myFile
            _binding.ivProductsPhoto.setImageURI(selectedImg)

            val file = reduceFileImage(_getFile as File)
            val requestImageFile = file.asRequestBody("file".toMediaTypeOrNull())
            val imageMultiPart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "file",
                file.name,
                requestImageFile,
            )

            _productsViewModel.uploadPhoto(imageMultiPart)
        }
    }
}