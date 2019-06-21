package defy.tech.chickenlover.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import defy.tech.chickenlover.model.data.UploadImageData
import java.io.File

class WriteViewModel : DisposableViewModel() {

    val board_types = listOf("자유게시판","실시간정보")

    private val _selected_type = MutableLiveData<String>().apply { value = "free" }
    val selected_type: LiveData<String> get() = _selected_type

    val uploadImageList = MutableLiveData<ArrayList<UploadImageData>>().apply { value = ArrayList() }

    fun selectType(type: String) {
        _selected_type.value = type
    }

    fun addItem(item: UploadImageData) {
        uploadImageList.value?.add(item)
        uploadImageList.value = uploadImageList.value
    }

    fun deleteItem(item: UploadImageData) {
        uploadImageList.value?.remove(item)
        uploadImageList.value = uploadImageList.value
    }

    fun getFileName(fileStr : String,isExtension : Boolean) : String? {
        var fileName : String? = null
        if(isExtension)
        {
            fileName = fileStr.substring(fileStr.lastIndexOf("/"),fileStr.lastIndexOf("."))
        }else{
            fileName = fileStr.substring(fileStr.lastIndexOf("/")+1)
        }
        return fileName
    }

    fun imgPathToBitmap(path : String) : Bitmap {
        val file = File(path)
        return BitmapFactory.decodeFile(file.absolutePath)
    }
}
