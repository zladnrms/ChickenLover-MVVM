package defy.tech.chickenlover.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import defy.tech.chickenlover.model.UserRepository
import defy.tech.chickenlover.model.data.UploadImageItem
import defy.tech.chickenlover.network.RetrofitInterface
import defy.tech.chickenlover.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class WriteViewModel(application: Application) : DisposableAndroidViewModel(application) {
    private val repository by lazy {
        UserRepository(application)
    }

    private val api by lazy {
        RetrofitInterface.create()
    }

    val board_types = listOf("자유게시판","실시간정보")

    private val _selected_type = MutableLiveData<String>().apply { value = "free" }
    val selected_type: LiveData<String> get() = _selected_type

    private val _navigateToFinishCall = SingleLiveEvent<Boolean>()
    val navigateToFinishCall: LiveData<Boolean> get() = _navigateToFinishCall

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

    fun write(title: String, content: String) {
        val fileList = mutableListOf<MultipartBody.Part>()

        uploadImageList.value?.apply {
            for(item in this)
            {
                val file = File(item.path)
                val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                val filePart = MultipartBody.Part.createFormData("uploadfile[]", file.name, requestFile)
                fileList.add(filePart)
            }
        }

        addDisposable(repository.getUserInfo().toSingle()
            .flatMap { user ->
                // add another part within the multipart request
                val m_type = RequestBody.create(MediaType.parse("multipart/form-data"), selected_type.value)
                val m_hashed_key = RequestBody.create(MediaType.parse("multipart/form-data"), user.hashed_key)
                val m_title = RequestBody.create(MediaType.parse("multipart/form-data"), title)
                val m_content = RequestBody.create(MediaType.parse("multipart/form-data"), content)
                api.writeBoardArticle(m_type, m_hashed_key, m_title, m_content, fileList)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                when(response.result)
                {
                    "success"->
                    {
                        _navigateToFinishCall.call()
                    }
                    "exceed_size"-> Log.d("허허","허허")
                    "file_not_found"-> Log.d("허허","허허")
                    "not_allowed_ext"-> Log.d("허허","허허")
                }
            })
    }
}
