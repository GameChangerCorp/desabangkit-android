package id.buildindo.desabangkit.android

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.core.data.remote.response.products.PostProductRequest
import id.buildindo.desabangkit.android.core.utils.*
import id.buildindo.desabangkit.android.databinding.FragmentInputProductsBinding
import id.buildindo.desabangkit.android.ui.viewmodel.DatastoreViewModel
import id.buildindo.desabangkit.android.ui.viewmodel.ProductsViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@AndroidEntryPoint
class InputProductsFragment : Fragment() {

    private var _getFile: File? = null
    private lateinit var _binding: FragmentInputProductsBinding
    private lateinit var _viewModelDataStore: DatastoreViewModel
    private val _productsViewModel: ProductsViewModel by viewModels()
    private var _imageUrl = ""
    private var _name = ""
    private var _price = 0
    private var _categories = ""
    private var _roles = ""
    private var _userId = ""
    private var _isPreorder = false
    private var _openFrom = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentInputProductsBinding.inflate(layoutInflater, container, false)
        _viewModelDataStore = ViewModelProvider(this)[DatastoreViewModel::class.java]
        observeDataStore()
        observeData()
        return _binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getBundleData()

        _binding.btnUploadPhoto.setOnClickListener {
            startGallery()
        }

        _binding.btnUploadData.setOnClickListener {
            uploadData()
        }
    }

    private fun uploadData() {
        val name = _binding.edtName.text.toString().trim()
        val category = _binding.edtCategory.text.toString().trim()
        val quantity = _binding.edtQuantity.text.toString().toInt()
        val price = _binding.edtPriceExpected.text.toString().toInt()
        val deliveryOption = _binding.edtDeliveryOption.text.toString().trim()
        val address = _binding.edtAddress.text.toString().trim()
        val cooperationId = "6309dd650b3a4f92a4c9f3c9"

        if (_roles == Constant.Roles.CUSTOMER) {
            _productsViewModel.postProduct(
                preorder = true,
                PostProductRequest(
                    photo_url = _imageUrl,
                    name = name,
                    category = category,
                    quantity = quantity,
                    price_expected = price,
                    deliver_option = deliveryOption,
                    useraddress = address,
                    userid = _userId,
                    cooperationId = cooperationId
                )
            )
        } else {
            _productsViewModel.postProduct(
                preorder = null,
                PostProductRequest(
                    photo_url = _imageUrl,
                    name = name,
                    category = category,
                    quantity = quantity,
                    price_expected = price,
                    deliver_option = deliveryOption,
                    useraddress = address,
                    userid = _userId,
                    cooperationId = cooperationId
                )
            )
        }
    }

    private fun getBundleData() {
        _name = arguments?.getString("productName").toString()
        _categories = arguments?.getString("productCategory").toString()
        _price = arguments?.getInt("productPrice", 0)!!
        _openFrom = arguments?.getString(Constant.BundleName.OPEN_PAGES_FROM) ?: ""
    }


    private fun observeData() {
        _productsViewModel.postProduct.observe(viewLifecycleOwner) {
            if (it != null) {
                Snackbar.make(_binding.root, it.messages.toString(), Snackbar.LENGTH_SHORT).show()
                when(_roles){
                    Constant.Roles.CUSTOMER -> Navigation.movePagesFragment(
                        requireParentFragment(),
                        R.id.action_inputProductsFragment_to_navigation_transaction
                    )
                    Constant.Roles.PPN -> Navigation.movePagesFragment(
                        requireParentFragment(),
                        R.id.action_inputProductsFragment2_to_navigation_transaction
                    )
                }
            }
        }

        _productsViewModel.uploadPhoto.observe(viewLifecycleOwner) {
            if (it.result != null) {
                _imageUrl = it.result
            }
        }
    }

    private fun observeDataStore() {
        _viewModelDataStore.getUserRoles().observe(viewLifecycleOwner) { roles ->
            _roles = roles
        }

        _viewModelDataStore.getUserId().observe(viewLifecycleOwner) { id ->
            _userId = id
        }
    }

//    private fun startCameraX() {
//        val intent = Intent(requireContext(), CameraActivity::class.java)
//        launcherIntentCameraX.launch(intent)
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
            val myFile = uriToFile(selectedImg, requireContext())

            _getFile = myFile
            _binding.ivPhoto.setImageURI(selectedImg)


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
            _binding.ivPhoto.setImageBitmap(result)
        }
    }


    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS_CAMERA = arrayOf(Manifest.permission.CAMERA)
        private val REQUIRED_PERMISSIONS_LOCATION =
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        private const val REQUEST_CODE_CAMERA = 10
        private const val REQUEST_CODE_LOCATION = 11
    }
}