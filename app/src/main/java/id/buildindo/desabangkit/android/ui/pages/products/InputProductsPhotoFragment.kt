package id.buildindo.desabangkit.android.ui.pages.products

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.R
import id.buildindo.desabangkit.android.core.domain.model.bundle.register.InputProductData
import id.buildindo.desabangkit.android.core.utils.*
import id.buildindo.desabangkit.android.core.utils.SendBundleData.getBundleExtra
import id.buildindo.desabangkit.android.core.utils.SendBundleData.sendBundleExtra
import id.buildindo.desabangkit.android.databinding.FragmentInputProductsPhotoBinding
import id.buildindo.desabangkit.android.ui.pages.CameraActivity
import id.buildindo.desabangkit.android.ui.viewmodel.ProductsViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@AndroidEntryPoint
class InputProductsPhotoFragment : Fragment() {

    private var _getFile: File? = null
    private lateinit var _binding: FragmentInputProductsPhotoBinding
    private val _productsViewModel: ProductsViewModel by viewModels()
    private var _imageUrl = ""
    private var _bundle = Bundle()
    private var _inputProductData = InputProductData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentInputProductsPhotoBinding.inflate(inflater, container, false)
        _inputProductData = arguments?.getBundleExtra<InputProductData>(Constant.GetIntentType.INPUT_PRODUCT_DATA) ?: InputProductData()
        observeLiveData()
        checkCameraPermission()
        initializeBinding()
        return _binding.root
    }

    private fun initializeBinding() {
        _binding.apply {
            btnUploadPhoto.setOnClickListener { startGallery() }
            btnTakePhoto.setOnClickListener { startCameraX() }
            btnNext.setOnClickListener {
                if (_inputProductData.isPreorder == true){
                    _bundle.sendBundleExtra(
                        Constant.GetIntentType.INPUT_PRODUCT_DATA,
                        InputProductData(
                            productName = _inputProductData.productName,
                            productCategory = _inputProductData.productCategory,
                            isPreorder = _inputProductData.isPreorder,
                            productPhoto = _imageUrl
                        )
                    )
                }else{
                    _bundle.sendBundleExtra(
                        Constant.GetIntentType.INPUT_PRODUCT_DATA,
                        InputProductData(
                            productPhoto = _imageUrl
                        )
                    )
                }

                Navigation.movePagesFragment(
                    requireParentFragment(),
                    R.id.action_inputProductsPhotoFragment_to_inputProductsDetailsFragment,
                    _bundle
                )
            }
        }
    }

    private fun allPermissionGranted() = REQUIRED_PERMISSIONS_CAMERA.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkCameraPermission() {
        if (!allPermissionGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS_CAMERA,
                REQUEST_CODE_CAMERA
            )
        }
    }

    private fun startCameraX() {
        val intent = Intent(requireContext(), CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun observeLiveData() {
        _productsViewModel.uploadPhoto.observe(viewLifecycleOwner) {
            ViewVisibility.showLoading(false, _binding.progressBar)
            if (it.result != null) {
                _imageUrl = it.result
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, requireContext())

            _getFile = myFile

            uploadProductsPhoto(_getFile)
            _binding.ivProductsPhoto.setImageURI(selectedImg)
        }
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            _getFile = myFile
            val result = rotateBitmap(
                BitmapFactory.decodeFile(_getFile?.path),
                isBackCamera
            )

            uploadProductsPhoto(_getFile)
            _binding.ivProductsPhoto.setImageBitmap(result)
        }
    }

    private fun uploadProductsPhoto(photo: File?) {
        val file = reduceFileImage(photo as File)
        val requestImageFile = file.asRequestBody("file".toMediaTypeOrNull())
        val imageMultiPart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "file",
            file.name,
            requestImageFile,
        )

        _productsViewModel.uploadPhoto(imageMultiPart)
        ViewVisibility.showLoading(true, _binding.progressBar)
    }

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS_CAMERA = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_CAMERA = 10
    }
}