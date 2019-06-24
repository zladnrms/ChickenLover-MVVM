package defy.tech.chickenlover.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import defy.tech.chickenlover.model.data.UploadImageItem
import java.io.File

class WriteViewModel : DisposableViewModel() {

    val board_types = listOf("자유게시판","실시간정보")

    private val _selected_type = MutableLiveData<String>().apply { value = "free" }
    val selected_type: LiveData<String> get() = _selected_type

    val uploadImageList = MutableLiveData<ArrayList<UploadImageItem>>().apply { value = ArrayList() }

    fun selectType(type: String) {
        _selected_type.value = type
    }

    fun addItem(item: UploadImageItem) {
        uploadImageList.value?.add(item)
        uploadImageList.value = uploadImageList.value
    }

    fun deleteItem(item: UploadImageItem) {
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
