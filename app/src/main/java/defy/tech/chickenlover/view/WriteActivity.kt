package defy.tech.chickenlover.view

import android.Manifest
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.werb.pickphotoview.PickPhotoView
import com.werb.pickphotoview.util.PickConfig
import defy.tech.chickenlover.R
import defy.tech.chickenlover.adapter.WriteImageListAdapter
import defy.tech.chickenlover.model.data.UploadImageData
import defy.tech.chickenlover.viewmodel.WriteViewModel
import kotlinx.android.synthetic.main.activity_write.*
import permissions.dispatcher.*

@RuntimePermissions
class WriteActivity : AppCompatActivity() {

    private lateinit var writeViewModel: WriteViewModel
    private lateinit var writeImageListAdapter: WriteImageListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp)
        toolbar.setNavigationOnClickListener { v -> finish() }

        writeViewModel = ViewModelProviders.of(this).get(WriteViewModel::class.java)

        writeImageListAdapter = WriteImageListAdapter { item ->
            writeViewModel.deleteItem(item)
        }
        imageUploadList.apply {
            adapter = writeImageListAdapter
            hasFixedSize()
        }

        writeViewModel.uploadImageList.observe(this, Observer {
            writeImageListAdapter.setList(it)
        })

        iv_upload.setOnClickListener {
            openGalaryWithPermissionCheck()
        }

        setSpinner()
    }

    private fun setSpinner() {
        spinner_caterogy.setItems(writeViewModel.board_types)
        spinner_caterogy.setOnItemSelectedListener { view, position, id, item ->
            writeViewModel.selectType(item.toString())
        }
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    fun openGalary() {
        PickPhotoView.Builder(this)
            .setPickPhotoSize(4)                  // select image size
            .setClickSelectable(true)             // click one image immediately close and return image
            .setShowCamera(true)                  // is show camera
            .setSpanCount(4)                      // span count
            .setLightStatusBar(true)              // lightStatusBar used in Android M or higher
            .setStatusBarColor(R.color.pick_white)     // statusBar color
            .setToolbarColor(R.color.pick_white)       // toolbar color
            .setToolbarTextColor(R.color.pick_black)   // toolbar text color
            .setSelectIconColor(R.color.pick_black)     // select icon color
            .setShowGif(true)                    // is show gif
            .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == 0) { return }
        data ?: return

        if (requestCode == PickConfig.PICK_PHOTO_DATA) {
            val select_img_paths: ArrayList<String> =
                data.getSerializableExtra(PickConfig.INTENT_IMG_LIST_SELECT) as ArrayList<String>

            Log.d("onACtivityResult", "${select_img_paths}")

            select_img_paths.let {
                for ((index, item) in it.withIndex()) {
                    addImageItemToList(index, item)
                }
            }
        }
    }

    private fun addImageItemToList(index: Int, item: String) {
        val bitmap = writeViewModel.imgPathToBitmap(item)
        val data = UploadImageData(index, writeViewModel.getFileName(item, true), item, bitmap)
        writeViewModel.addItem(data)
    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    fun showRationaleForLocation(request: PermissionRequest) {
        AlertDialog.Builder(this)
            .setMessage("디바이스 검색을 위해 권한을 허용해 주시기 바랍니다.")
            .setPositiveButton("확인") { dialogInterface, button ->
                request.proceed()
            }
            .setNegativeButton("취소") { dialogInterface, button ->
                request.cancel()
            }
            .setCancelable(false)
            .show()
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    fun showDeniedForLocation() {
        Toast.makeText(this, "권한을 허용해 주세요.", Toast.LENGTH_SHORT).show()
        finish()
    }

    @OnNeverAskAgain(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    fun showNeverAskForLocation() {
        Toast.makeText(this, "권한 허용을 해주지 않으신다면, 서비스 이용이 불가합니다.", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated method
        onRequestPermissionsResult(requestCode, grantResults)
    }

    companion object {
        fun starterIntent(context: Context): Intent {
            return Intent(context, WriteActivity::class.java)
        }
    }
}
